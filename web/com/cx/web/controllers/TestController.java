package com.cx.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;



import com.cx.web.auth.AuthPassport;
import com.cx.web.utils.AjaxDone;
import com.cx.web.utils.Json;
import com.infrastructure.project.common.exception.EntityOperateException;
import com.infrastructure.project.common.exception.ValidatException;

@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController {

	@AuthPassport
	@RequestMapping(value = "/toupload", method = {RequestMethod.GET})
	public String toUpload(HttpServletRequest request, Model model){
        return "test/toupload";
	}
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String>  iter = multiRequest.getFileNames();
			while(iter.hasNext()){
					MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					String fileName = "tempUpload-" + file.getOriginalFilename();
					String path = "D:/" + fileName;
					//String path = request.getContextPath() + fileName;
					//System.out.print(path);
					//System.out.print(request.getServletPath() + " --- " + request.getSession().getServletContext().getRealPath(request.getRequestURI()));
					File localFile = new File(path);
					file.transferTo(localFile);
				}
			}
		}
		return "/home/success";
	}
	
	@AuthPassport
	@RequestMapping(value = "/toImportExcel", method = {RequestMethod.GET})
	public String toImportExcel(HttpServletRequest request, Model model){
        return "test/toImportExcel";
	}
	
	@RequestMapping("/importExcel")
	public String importExcel(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String>  iter = multiRequest.getFileNames();
			while(iter.hasNext()){
					MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					String fileName = "tempUpload-" + file.getOriginalFilename();
					String path = "D:/" + fileName;
					File localFile = new File(path);
					file.transferTo(localFile);
					
					FileInputStream fis = new FileInputStream(path);
					POIFSFileSystem fs = new POIFSFileSystem(fis);
					HSSFWorkbook wb = new HSSFWorkbook(fs);
					// 只获取第0 Sheet的数据
					HSSFSheet sheet = wb.getSheetAt(0);
					int i = 0,rowNum = sheet.getLastRowNum(),successNum = 0, insertSuccessNum = 0;
					String SFZH = "",poiShow="",returnMsg="";
					// 获取总行数(这里应该限制行数，这些身份证号都保存在一个String里，如果太长会超过String长度从而导致程序错误)
					boolean err = false;
					String sfzhs = "";
					while (i < rowNum + 1) {
						// System.out.println("===========读取数据===========");
						HSSFRow row = sheet.getRow(i);
						if (null != row) {
							// i=0 表示是表头
							if (i == 0) {
								// 取两列，SFZH
								for (int k = 0; k < 1; k++) {
									HSSFCell cell = row.getCell(k);
									if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
										SFZH = cell.getStringCellValue();
									} else {
										k = row.getLastCellNum() + 1;
										i = rowNum + 1;
										err = true;
										poiShow = "第" + k + "列字段验证异常";
									}
									break;
								}
								if (err == false) {
									if (!SFZH.equalsIgnoreCase("SFZH")) {
										i = rowNum + 1;
										poiShow = "SFZH字段未获取到信息";
									}
								}
								// 非表头，是数据
							} else {
								try {
									// int colNumber = row.getLastCellNum();
									// SFZH列
									HSSFCell cell = row.getCell(0);
									try {
										if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
											SFZH = cell.getStringCellValue();
										} else if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
											SFZH = cell.getNumericCellValue() + "";
										} else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
											SFZH = "";
										} else if (HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
											SFZH = cell.getBooleanCellValue() + "";
										} else if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
											SFZH = cell.getCellFormula() + "";
										} else {
											SFZH = "";
										}
									} catch (Exception e) {
										SFZH = "";
									}
									if(sfzhs != null && !sfzhs.contains(SFZH)){
										sfzhs += SFZH;
										sfzhs += ",";
									}
								} catch (Exception e) {
								} finally {
									SFZH = null;
								}
							}
						}
						i = i + 1;
					}

					if (sfzhs != null && !"".equals(sfzhs)) {
						sfzhs = sfzhs.substring(0, sfzhs.length() - 1);
						// 数据库插入数据
						String TempYxdm = (String) request.getSession().getAttribute("yxdm");
						TempYxdm = TempYxdm == null ? "" : TempYxdm.trim();
						try {
							if (!sfzhs.equals("")) {
								System.out.println("sfzhs--------------------------------- 保存--" + sfzhs);
							} else {
								System.out.println("sfzhs--------------------------------- 无数据");
							}
						} catch (Exception e) {
							poiShow = poiShow == null ? "" : poiShow.trim();
							if (poiShow.length() == 0) {
								poiShow = sfzhs + "失败,原因：" + e.getMessage() + ".";
							} else {
								poiShow = poiShow + "<br>" + sfzhs + "失败,原因："+ e.getMessage() + ".";
							}
						}
					}
					String statusCode = "200";
					if (poiShow == null) {
						statusCode = "200";
					} else {
						if (err) {
							returnMsg = poiShow;
						} else {
							if (successNum == 0) {
								returnMsg = poiShow + "<br>成功导入[" + insertSuccessNum + "]条数据.";
							} else if (successNum > 0 && insertSuccessNum > 0) {
								returnMsg = poiShow + "<br>已存在[" + successNum + "]条数据.<br>导入成功[" + insertSuccessNum + "]条数据.";
							} else if (successNum > 0 && insertSuccessNum == 0) {
								returnMsg = poiShow + "<br>已存在[" + successNum + "]条数据.";
							}
						}
						statusCode = "300";
					}
					response.setContentType("text/html; charset=UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					PrintWriter out = response.getWriter();
					AjaxDone ajax = new AjaxDone();
					ajax.setStatusCode(statusCode);
					ajax.setMessage(returnMsg);
					out.write(Json.getJson(ajax));
					out.close();
					return "/home/success";
				}
			}
		}
		return "/home/success";
	}
	
	@AuthPassport
	@RequestMapping(value = "/toPluginModal", method = {RequestMethod.GET})
	public String toPluginModal(HttpServletRequest request, Model model){
        return "test/toPluginModal";
	}
	
    //通过准考证号获取考生信息
	@RequestMapping("/savePluginModalInfo")
	@ResponseBody
	public Map<String,Object> savePluginModalInfo(HttpServletRequest request) throws NumberFormatException, EntityOperateException, ValidatException {
		String textarea = request.getParameter("textarea").trim();
		System.out.println("textarea is " + textarea);
		//accountService.delete(Integer.valueOf(textarea));
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msg", "操作成功");
		return map;
	}
	
	@AuthPassport
	@RequestMapping(value = "/marking", method = {RequestMethod.GET})
	public String marking(HttpServletRequest request, Model model){
        return "test/marking";
	}
}
