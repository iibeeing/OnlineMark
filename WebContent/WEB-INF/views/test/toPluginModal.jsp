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
<!-- 					<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-upload"></i>模态框
							</div>
						</div>

						<div class="portlet-body">
							<h2>创建模态框（Modal）</h2>
							按钮触发模态框
							<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>
							
							模态框（Modal）
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
							   <div class="modal-dialog">
							      <div class="modal-content">
							         <div class="modal-header">
							            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							            <h4 class="modal-title" id="myModalLabel">请输入模态框标题</h4>
							         </div>
							         <div class="modal-body">
							         	<div class="form-group">
							         		<label for="name">文本框</label>
							         		<textarea class="form-control" rows="4"></textarea>
							         	</div>
							         <div class="modal-footer">
							            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
							            <button type="button" class="btn btn-primary"> 提交更改 </button>
							         </div>
							      </div>/.modal-content
							</div>/.modal
							</div>
				</div>
			</div> -->
			
			
								<div class="portlet box light-grey">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-upload"></i>模态框
							</div>
						</div>

						<div class="portlet-body">
							<!-- <h2>创建模态框（Modal）</h2> -->
							<!-- 按钮触发模态框 -->
							<button class="btn btn-default" data-toggle="modal" data-target="#myModal">开始演示模态框</button>
							
							<!-- 模态框（Modal） -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
							   <div class="modal-dialog">
							      <div class="modal-content">
							         <div class="modal-header">
							            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							            <h4 class="modal-title" id="myModalLabel">请输入模态框标题</h4>
							         </div>
							         <div class="modal-body">
							         	<div class="form-group">
							         		<!-- <label for="name">文本框</label> -->
							         		<textarea class="form-control" rows="4"></textarea>
							         	</div>
							         <div class="modal-footer">
							            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
							            <button type="button" class="btn btn-primary" id="submit"> 提交更改 </button>
							         </div>
							      </div><!-- /.modal-content -->
							</div><!-- /.modal -->
							</div>
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
			$("#submit").click(function(){
				var textarea = $("textarea").val();
				//alert(textarea);
				var url = "${ctx}/test/savePluginModalInfo";
	             $.ajax({
	                 async: false,
	                 cache: false,
	                 type: 'POST',
	                 dataType: "json",
	                 url: url,
	                 data: {
	                	 textarea:textarea
	                 },
	                 error: function(){
	                     alert('请求失败');
	                 },
	                 success: function(data){
	                	 alert(data.msg);
	                	 location.reload();
	                 }
	             });
			});
		});
	</script>
</body>
</html>