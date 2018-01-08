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

<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/demo.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery.easyui.min.js"></script>



<script type="text/javascript">
	//自定义全局变量
	var pageSize=10;
	var begin=0;
	var currentPage=1;


	//页面加载  
 	$(document).ready(function(){ 
 		
		$('#dg').datagrid({
			queryParams: {
			    begin: begin,
			    end: pageSize,
			    pageSize: pageSize,
			    currentPage:currentPage
			},
		    url:'${pageContext.request.contextPath}/file/queryAllJsonFile',     
		    columns:[[     
		        {field:'id',title:'id',width:100},
		        {field:'parentPath',title:'path',width:600},
		        {field:'name',title:'name',width:200},
		        {field:'depth',title:'depth',width:100,align:'right'},
		        {field:'size',title:'size',width:100,align:'right'}
		        
		    ]]     
		});
		

	$('#pp').pagination({
		onSelectPage:function(pageNumber, pageSize){
			currentPage=pageNumber;
			$(this).pagination('loading');
			alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
			$(this).pagination('loaded');
			
			$('#dg').datagrid({
				queryParams: {
				    begin: begin,
				    end: pageSize,
				    pageSize: pageSize,
				    currentPage:pageNumber
				},
			    url:'${pageContext.request.contextPath}/file/queryAllJsonFile',     
			    columns:[[     
			        {field:'id',title:'id',width:100},
			        {field:'parentPath',title:'path',width:600},
			        {field:'name',title:'name',width:200},
			        {field:'depth',title:'depth',width:100,align:'right'},
			        {field:'size',title:'size',width:100,align:'right'}
			        
			    ]]     
			});
		}
	});

			



	}); 
	

		
	function submitForm(param){
		$('#ff').form({    
		    url:'${pageContext.request.contextPath}/index/searchIndex',    
		    onSubmit: function(param){   
		    	console.log("param=========="+param);
			    param.begin=begin;
 			    param.end=pageSize;
			    param.pageSize=pageSize;
			    param.currentPage=currentPage;
		        // do some check    
		        // return false to prevent submit;    
		    },    
		    success:function(data){ 
		    	var data = eval('(' + data + ')');     
		    	console.log("data==========="+data);
		    	$("#dg").datagrid("loadData",data);
			       /* $('#dg').datagrid({
					data: [
						{id:'value11', name:'value12'}
					]
				}); */
				

		    }    
		}); 
		$('#ff').submit(); 
		
		
	}
		
		

	

		
	
</script>


</head>

<body class="easyui-layout">
	<div data-options="region:'west',title:'West',split:true" style="width:200px;"></div>
	<div data-options="region:'north',title:'North Title',split:false" style="height:100px;"></div>
    <div class="easyui-layout" data-options="region:'center',title:'center title'" style="background:#eee;">
		<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north',collapsed:false" style="height:100px">
            	<div class="easyui-layout" data-options="fit:true">
	            	<div data-options="region:'north',collapsed:false" style="height:40px">
						<form id="ff" method="post">   
					        <label for="name">Drive:</label>   
					        <input style="width:100px;" class="easyui-validatebox" type="text" name="driver" data-options="required:false" />   
					        <label for="name">ParentPath:</label>   
					        <input class="easyui-validatebox" type="text" name="parentPath" data-options="required:false" />   
					        <label for="name">Name:</label>   
					        <input class="easyui-validatebox" type="text" name="name" data-options="required:false" />   
					        <label for="name">Ext:</label>   
					        <input class="easyui-validatebox" type="text" name="ext" data-options="required:false" /> 
					        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
						</form>
	            	</div>
	            	<div data-options="region:'center',collapsed:false">
	            		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a> -->  
	            		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a> -->  
	            	</div>
	            	<div data-options="region:'south',collapsed:false">
						<div id="pp" class="easyui-pagination" data-options="total:2000,pageSize:10" style="background:#efefef;"></div>  
	            	</div>
            	</div>
            </div>   
	        <div data-options="region:'center'"  style="height:30px">
				<table id="dg"></table>
	        </div>  
        </div> 
    </div>   

<!-- <div id="p" class="easyui-panel" title="My Panel"     
        style="width:500px;height:150px;padding:10px;background:#fafafa;"   
        data-options="iconCls:'icon-save',closable:true,    
                collapsible:true,minimizable:true,maximizable:true">   
    <p>panel content.</p>   
    <p>panel content.</p>   
</div>  -->
<!-- 		<form class="WellForm">
			<div class="item"><label>用户名：</label><input type="text" name="username" /></div>
		    <div class="item"><label>密码：</label><input type="password" name="pwd" /></div>
		    <div class="item"><label>验证码：</label><input type="text" name="code" style="width:60px;" /></div>
		    <div class="item"><label></label><input type="submit" value="提交" /></div>
		</form> -->
<%-- <a id="btn" href="${pageContext.request.contextPath}/file/queryAllJsonFile" class="easyui-linkbutton" data-options="iconCls:'icon-search'">easyui</a>  
 --%>

	
	
	
	<!-- 分页 -->
<!-- 	<div class="pagination">
		<span class="firstPage">&nbsp;</span> <span class="previousPage">&nbsp;</span>
		<span class="currentPage">1</span> <a
			href="javascript: $.pageSkip(2);">2</a> <a
			href="javascript: $.pageSkip(3);">3</a> <a class="nextPage"
			href="javascript: $.pageSkip(2);">&nbsp;</a> <a class="lastPage"
			href="javascript: $.pageSkip(4);">&nbsp;</a> <span class="pageSkip">
			共4页 到第<input id="pageNumber" name="pageNumber" value="1"
			maxlength="9" onpaste="return false;">页
			<button type="submit">&nbsp;</button> </span>
	</div> -->
	
</body>
</html>
