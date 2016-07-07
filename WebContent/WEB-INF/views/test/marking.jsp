<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
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
<link rel="stylesheet"
	href="<c:url value='/css/toolbarStyle.css'/>"
	type="text/css" />
<link rel="stylesheet"
	href="<c:url value='/plugins/data-tables/DT_bootstrap.css'/>"
	type="text/css" />
	
<%@ include file="../shared/importJs.jsp"%>

<script type="text/javascript"
	src="<c:url value='/plugins/data-tables/jquery.dataTables.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/plugins/data-tables/DT_bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/plugins/uniform/jquery.uniform.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.toolbarlite.js?ver=10'/>"></script>
<script type="text/javascript" src="<c:url value='/js/app.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.tableManaged.js'/>"></script>
	
	
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
					<!-- <h3 class="page-title">
						Form Controls <small>form controls and more</small>
					</h3> -->
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
			<div class="row"></div>
			
			<nav class="navbar navbar-inverse" role="navigation">
			   <div class="navbar-header">
			      <a class="navbar-brand" href="#">取题</a>
			   </div>
			   <div>
			      <ul class="nav navbar-nav">
			         <li><a href="#">SVN</a></li>
			         <li class="dropdown">
			            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
			               Java <b class="caret"></b>
			            </a>
			            <ul class="dropdown-menu">
			               <li><a href="#">jmeter</a></li>
			               <li><a href="#">EJB</a></li>
			               <li><a href="#">Jasper Report</a></li>
			               <li class="divider"></li>
			               <li><a href="#">分离的链接</a></li>
			               <li class="divider"></li>
			               <li><a href="#">另一个分离的链接</a></li>
			            </ul>
			         </li>
			      </ul>
			   </div>
			   <div>
			      <div>
      <form class="navbar-form navbar-left" role="search">
         <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
         </div>
         <button type="submit" class="btn btn-default">提交</button>
      </form>    
   </div>
			   </div>
			   </nav>
			   
				<div class="panel panel-default">
				   <div class="panel-heading">高数 A面（1-6题）</div>
				   <!-- <div class="panel-body"><img src="http://www.w3school.com.cn/i/eg_tulip.jpg"></img></div> -->
				   <div class="panel-body outer-box">
				     <img src="http://img2.pconline.com.cn/pconline/0710/07/1121418_071010colourback01.jpg"></img>
				     
				     <div id="fixed">
			            <dl>
			            
			            	<dd><div><font color="white">操作</font></div></dd>
			                <!-- <dd><a href="#" class="dh">动画</a></dd>
			                <dd><a href="#" class="pk165">小说</a></dd>
			                <dd><a href="#" class="w267">社区</a></dd> -->
			                <dt>
			                    <a href="javascript:void(0);" class="slide-close"></a>
			                </dt>

			            </dl>
			        </div>
		        	<div id="expand"></div>
				   </div>
				</div>
		</div>
		

		<%@ include file="../shared/pageFooter.jsp"%>
		<script type="text/javascript">
			$(function() {
				App.init();
			});
			
			$(document).ready(function() {
		        $("#fixed dt").click(function() {
		            var _left = $("#fixed").offset().left;
		            console.log(_left);
		            if (_left >= 0) {
		                $("#fixed").animate({
		                    right: -60
		                }, 300, 'swing', function() {
		                    $("#expand").show().fadeIn(500);
		                });
		            }
		        });
		        $("#expand").click(function() {
		        	$("#expand").hide().fadeOut(500);
		            $("#fixed").animate({
		                right: 0
		            }, 300, 'swing', function() {});
		        });
		    });
		</script>
</body>
</html>