<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="com.infrastructure.project.common.extension.UrlHelper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8" />
<title>招考网阅 | ${requestScope.permissionMenu.subName}</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<%@ include file="../shared/importCss.jsp"%>
<link rel="stylesheet" href="<c:url value='/plugins/data-tables/DT_bootstrap.css'/>" type="text/css" />
<%@ include file="../shared/importJs.jsp"%>
<script type="text/javascript" src="<c:url value='/plugins/data-tables/jquery.dataTables.js'/>"></script>
<script type="text/javascript" src="<c:url value='/plugins/data-tables/DT_bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/plugins/uniform/jquery.uniform.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.toolbarlite.js?ver=10'/>"></script>
<script type="text/javascript" src="<c:url value='/js/app.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.tableManaged.js'/>"></script>
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body class="page-header-fixed">
	<%@ include file="../shared/pageHeader.jsp"%>
	<div class="clearfix"></div>
	<div class="page-container">
		<%@ include file="../shared/sidebarMenu.jsp"%>
		<div class="page-content">
			<%@ include file="../shared/styleSet.jsp"%>
			<div class="row">
				<div class="col-md-12">
					<h3 class="page-title">
						Form Controls <small>form controls and more</small>
					</h3>
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home"></i> <a
							href="<c:url value='/home/index'/>">首页</a> <i
							class="icon-angle-right"></i></li>
						<li><span>${requestScope.permissionMenu.rootName}</span> <i
							class="icon-angle-right"></i></li>
						<li><span>${requestScope.permissionMenu.subName}</span></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-upload"></i>上传文件
							</div>
						</div>

						<div class="portlet-body">
							<form name="userForm" action="upload" method="post" enctype="multipart/form-data">
									<div>
										<div>
									    <fieldset>
									    <div>  
										    <!-- <label>选择文件：</label> -->  
										    <div style="text-align: right;"><a class="btn btn-default" onclick="javascript:document.getElementById('fileToUpload').click();">Browse(点此选择文件)</a></div>
											<div>  
									            <div id="filepath" style="display:none;">
									            <input type="file" name="fileToUpload" id="fileToUpload" onChange="fileSelected();"/>
									            </div>
									            <br />
											</div>  
											<div class="panel panel-default">
											   <div class="panel-heading">
											      <h3 class="panel-title">文件描述</h3>
											   </div>
											   <div class="panel-body">
											      <div id="fileNamediv" ></div>
											      <div id="fileSize" style="line-height:1.5;"></div>
											      <div id="fileType" style="line-height:1.5;"></div> 
											      <div id="startupload"  style="line-height:1.5;display:none;">
											   </div>
											</div>
										</div> 
										<div style="text-align: right ;"><input type="button" onClick="uploadFile()" value="上传" class="btn btn-success"></div>
									    <div id="progressNumber" style="margin-top:5%; width:100%; height:25px; text-align:right; line-height:25px;"></div> 
									    <div id="progressShow"></div>
									</div>
								</fieldset>
							</div>
						</div>	
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
	
	<%@ include file="../shared/pageFooter.jsp"%>
	<script type="text/javascript">
		$(function() {
			App.init();
			$('input[id=lefile]').change(function() {
				var file = document.getElementById('lefile').files[0];
				if (file) {
			      $('#photoCover').val(file.name);
					var info = '<strong>上传文件</strong>: ' + file.name + '<br/><strong>上传类型</strong>: ' + file.type+ '<br/><strong>文件大小</strong>: ' + file.size;
					$("#fileinfo").html(info);
					}
				});
			});
		
		function test(filename){
			var index1=filename.lastIndexOf(".");  
			var index2=filename.length;
			var postf=filename.substring(index1,index2);//后缀名
			return   postf;
		}
		function fileSelected() {
			var file = document.getElementById('fileToUpload').files[0];
		    if (file) {
		    	var fileSize = 0;
		        if (file.size > 1024 * 1024)
		        	fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		        else
		            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
				var ext=test(file.name).toLowerCase();
				var extStr=".xls.chm";
				$("#fileNamediv").html('');
				$("#fileSize").html('');
				$("#fileType").html('');
				$("#fileName").val('');
				$("#startupload").hide();
				$("#progressNumber").html('');
				if(extStr.indexOf(ext)==-1){
					alert("上传文件类型非法(支持："+extStr+")");
				/* }else if(file.size > 1024 * 1024 * 50){
					alert("上传文件大小超出限制(50M)"); */
				}else if(file.size > 1024 * 1024 * 1){
					alert("上传文件大小超出限制(1M)");
				}else{
					$("#fileNamediv").html('<strong>上传文件</strong>: ' + file.name);
					$("#fileSize").html('<strong>文件大小</strong>: ' + fileSize);
					$("#fileType").html('<strong>上传类型</strong>: ' + file.type);
					$("#fileName").val(file.name);
					$("#startupload").show();
					$("#progressNumber").html('');
				}
			}
		}
		function uploadFile() {
			$("#progressNumber").html('');
			$("#progressNumber").show();
			$("#background,#progressBar").show();
		    var fd = new FormData();
		    var file = document.getElementById('fileToUpload').files[0];
		    if($("#fileNamediv").text() == null || $("#fileNamediv").text() == ""){
		    	alert("请重新选择要上传的文件！");
		    	return false;
		    }
		    fd.append("fileToUpload", file);
		    fd.append("remark", $("#remark").val());
		    var xhr = new XMLHttpRequest();
		    xhr.upload.addEventListener("progress", uploadProgress, false);
		    xhr.addEventListener("load", uploadComplete, false);
		    xhr.addEventListener("error", uploadFailed, false);
		    xhr.addEventListener("abort", uploadCanceled, false);
		    //xhr.open("POST", "/OnlineMark/test/upload");
		    //var url = "${ctx}/upload";
		    var url = "${ctx}/paper/upload.do";
		    xhr.open("POST", url);
		    //xhr.open("POST", "D:\pic");
		    alert(url);
		    xhr.send(fd);
		}
		function uploadProgress(evt) {
			if (evt.lengthComputable) {
		    	var percentComplete = Math.round(evt.loaded * 100 / evt.total);
				$("#progressNumber").html('<div class="progress"><div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	style="width: ' + percentComplete.toString()+ '%;"><span class="sr-only">' + percentComplete.toString() + '% 完成（信息）</span>' + percentComplete.toString() + '%</div></div>');
		    }else {
				$("#progressNumber").html('无法计算进度信息');
		    }
		}
		function uploadComplete(evt) {
			if(jQuery.parseJSON(evt.target.responseText).statusCode==DWZ.statusCode.ok){
				alertMsg.info(jQuery.parseJSON(evt.target.responseText).message);
				$("#progressNumber").hide();
				$("#startupload").hide();
			}else{	
				alert(jQuery.parseJSON(evt.target.responseText).message);
				$("#progressNumber").hide();
			}
			
			$("#background,#progressBar").hide();
		}
		function uploadFailed(evt) {
			alert("一个试图上传文件时出现错误.");
		}
		function uploadCanceled(evt) {
			alert("上传已由用户或浏览器取消了连接.");
		}
	</script>
</body>
</html>