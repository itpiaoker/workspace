<%@ page language="java" pageEncoding="UTF-8"%>  
  
<script type="text/javascript">  

</script>  
  
<div style="color:red; font-size:22px;">${message_login}</div>  
  
<form action="<%=request.getContextPath()%>/login/login" method="POST">
    姓名：<input type="text" name="username"/><br/>  
    密码：<input type="text" name="password"/><br/>  
    <input type="submit" value="确认"/>  
</form> 