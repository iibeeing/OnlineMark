<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.infrastructure.project.common.extension.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
   <meta charset="utf-8" />
   <title>招考网阅 | ${requestScope.permissionMenu.subName}</title>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <meta name="MobileOptimized" content="320">
   
   <%@ include file="../shared/importCss.jsp"%>
   <!-- BEGIN PAGE LEVEL STYLES -->
   <link rel="stylesheet" href="<c:url value='/plugins/data-tables/DT_bootstrap.css'/>" type="text/css"/>
   <link rel="stylesheet" href="<c:url value='/plugins/bootstrap-fileupload/bootstrap-fileupload.css'/>" type="text/css"/>
   
   <!-- END PAGE LEVEL STYLES -->
   
   <%@ include file="../shared/importJs.jsp"%>
   <!-- BEGIN PAGE LEVEL SCRIPTS -->
   <script type="text/javascript" src="<c:url value='/plugins/data-tables/jquery.dataTables.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/plugins/data-tables/DT_bootstrap.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/plugins/uniform/jquery.uniform.min.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/js/jquery.toolbarlite.js?ver=10'/>"></script> 
   <script type="text/javascript" src="<c:url value='/js/app.js'/>"></script> 
   <script type="text/javascript" src="<c:url value='/js/jquery.tableManaged.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/plugins/bootstrap-fileupload/bootstrap-fileupload.js'/>"></script>
   <!-- END PAGE LEVEL SCRIPTS -->

   <link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">
   
   <%@ include file="../shared/pageHeader.jsp"%>
   
   <div class="clearfix"></div>
   <!-- BEGIN CONTAINER -->
   <div class="page-container">
      
      <%@ include file="../shared/sidebarMenu.jsp"%>
      
      <!-- BEGIN PAGE -->  
      <div class="page-content">
         
         <%@ include file="../shared/styleSet.jsp"%>
                    
         <!-- BEGIN PAGE HEADER-->   
         <div class="row">
            <div class="col-md-12">
               <!-- BEGIN PAGE TITLE & BREADCRUMB-->
               <h3 class="page-title">
                  Form Controls <small>form controls and more</small>
               </h3>
               <ul class="page-breadcrumb breadcrumb">
                  <li>
                     <i class="icon-home"></i>
                     <a href="<c:url value='/home/index'/>">首页</a> 
                     <i class="icon-angle-right"></i>
                  </li>
                  <li>
                     <span>${requestScope.permissionMenu.rootName}</span>
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><span>${requestScope.permissionMenu.subName}</span></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         <!-- BEGIN PAGE CONTENT-->
         <div class="row">
            <div class="col-md-12">
            
				<div class="portlet box light-grey">
				   <div class="portlet-title">
					  <div class="caption"><i class="icon-search"></i>数据检索</div>
				   </div>
				   <div class="portlet-body form">
					  <!-- BEGIN FORM-->
					  <form:form modelAttribute="searchModel" class="form-horizontal" method="GET">
						 <div class="form-body">
							<div class="row">
							   <div class="col-md-6">
								  <div class="form-group">
									 <label class="control-label col-md-3">试卷名称</label>
									 <div class="col-md-9">
										<form:input path="name" class="form-control placeholder-no-fix" autocomplete="off" placeholder="考试名称"/>
									 </div>
								  </div>
							   </div>
							   <!--/span-->
							   <div class="col-md-6">
								  <div class="form-group">
									 <label class="control-label col-md-3">是否可用</label>
									 <div class="col-md-9">
										<form:input path="enable" class="form-control placeholder-no-fix" autocomplete="off" placeholder="请输入true 或 false"/>
									 </div>
								  </div>
							   </div>
							   <!--/span-->
							</div>
							
							<div class="row">
							   <div class="col-md-6">
								  <div class="form-group">
									 <label class="control-label col-md-3">科目名称</label>
									 <%-- <div class="col-md-9">
										<form:input path="name" class="form-control placeholder-no-fix" autocomplete="off" placeholder="考试名称"/>
									 </div> --%>
									 <div class="col-md-9">
		                                 <form:select path="subjectId" class="form-control">  
							                <option value="">请选择</option>  
							                <form:options items="${selectDataSource}"/>  
							           	 </form:select>
	                              	 </div>
								  </div>
							   </div>
							</div>
							
							
							
						 </div>
						 <div class="form-actions">
							<div class="row">
							   <div class="col-md-12">
								  <div class="col-md-offset-5">
									 <button type="submit" class="btn btn-success">搜索</button>                            
								  </div>
							   </div>
							</div>
						 </div>
					  </form:form>
					  <!-- END FORM-->                
				   </div>
				</div>
               
               <!-- BEGIN EXAMPLE TABLE PORTLET-->
               <div class="portlet box light-grey">
                  <div class="portlet-title">
                     <div class="caption"><i class="icon-table"></i>${requestScope.permissionMenu.curName}</div>
                  </div>
                  <div class="portlet-body">
                     <div class="table-toolbar"></div>
                     <div class="dataTables_wrapper form-inline" role="grid">
	                     <div class="table-scrollable">
		                     <table class="table table-striped table-bordered table-hover" id="data-table">
		                        <thead>
		                           <tr>
		                              <th class="table-checkbox"><input type="checkbox" class="group-checkable"/></th>
		                              <th >试卷名称</th>
		                              <th >是否可用</th>
		                              <th >状态</th>
		                              <th >所属科目</th>
		                              <th >所属考试</th>
		                              <th >上传时间</th>
		                           </tr>
		                        </thead>
		                        <tbody>
		                        	<c:forEach items="${contentModel.items}" var="item">
							        <tr class="odd gradeX">
							        	<td class="check_cell">
									        <input type="checkbox" class="checkboxes" name="Id" value="${ item.id }" />
									    </td>
							            <td>${ item.name }</td>
							            <td>${ item.enable }</td>
							            <td>${ item.flag }</td>
							            <td>${ item.subject.name }</td>
							            <td>${ item.subject.project.name }</td>
							            <td>${ item.uploadTime.getTime().toLocaleString() }</td>
							        </tr>
							        </c:forEach>
		                        </tbody>
		                     </table>
	                     </div>
	                     <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/paper/list"/>
	       				 </c:import>
       				 </div>
                  </div>
               </div>
               <!-- END EXAMPLE TABLE PORTLET-->
            </div>
         </div>
         <!-- END PAGE CONTENT-->    
      </div>
      <!-- END PAGE -->  
   </div>
   <!-- END CONTAINER -->
   <%@ include file="../shared/pageFooter.jsp"%>
     
   <script type="text/javascript">
   	  $(function() {   
         App.init();
         $("#data-table").tableManaged();
         $(".table-toolbar").toolbarLite({
             items: [
				 { link: true, display: "上传", css: "icon-upload", showIcon: true, url: "<%=UrlHelper.resolveWithReturnUrl("/paper/toupload", request.getAttribute("requestUrl"), request.getAttribute("requestQuery"), pageContext)%>" },
				 { splitter: true }, 
                 { link: true, display: "启用", css: "icon-ok", showIcon: true, url: "<%=UrlHelper.resolveWithReturnUrl("/paper/enable/{0}", request.getAttribute("requestUrl"), request.getAttribute("requestQuery"), pageContext)%>",
                	 selector: "#data-table .checkboxes", mustSelect: "请先选择数据！", singleSelect: "该操作只支持单选！"},
                 { splitter: true }, 
                 { link: true, display: "禁用", css: "icon-remove", showIcon: true, url: "<%=UrlHelper.resolveWithReturnUrl("/paper/disable/{0}", request.getAttribute("requestUrl"), request.getAttribute("requestQuery"), pageContext)%>", 
                 	 selector: "#data-table .checkboxes", mustSelect: "请先选择数据！", singleSelect: "该操作只支持单选！"},
                 { splitter: true }, 
                 { link: true, display: "删除", css: "icon-trash", showIcon: true, url: "<%=UrlHelper.resolveWithReturnUrl("/paper/delete/{0}", request.getAttribute("requestUrl"), request.getAttribute("requestQuery"), pageContext)%>", 
                   	 selector: "#data-table .checkboxes", mustSelect: "请先选择数据！", singleSelect: "该操作只支持单选！", confirm: "确认删除所选数据吗？"}
             ]
         });
      });
   	  
   </script>
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>