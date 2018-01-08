<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'file_list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/demo.css">--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/zrender.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>


</head>

<body>
<div id="chartmain1" style="width:600px; height: 400px;"></div>
	<script type="text/javascript">
		<%--var error = ${error}--%>
		<%--console.log(${error});--%>

		//初始化echarts实例
		var myChart = echarts.init(document.getElementById('chartmain1'));
//		myChart.setOption({
//			series : [
//				{
//					name: '访问来源',
//					type: 'pie',
//					radius: '55%',
//					data:[
//						{value:235, name:'视频广告'},
//						{value:274, name:'联盟广告'},
//						{value:310, name:'邮件营销'},
//						{value:335, name:'直接访问'},
//						{value:400, name:'搜索引擎'}
//					]
//				}
//			]
//		})

		$.ajax({
			type: "POST",
			url: "http://localhost:8080/index/showLine.shtml",
			data: "name=John&location=Boston",
			success: function(msg){
				console.log(msg);
				console.log(jQuery.parseJSON(msg));
				myChart.setOption(jQuery.parseJSON(msg));
			}
		});



	</script>


	
	
	
</body>
</html>
