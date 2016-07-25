<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Enter a feeding</title>
</head>
<body>
<h1>Enter a feeding</h1>
<html:form action="/processFeeding">
  <div class="formRow">Feeding Date: <input type="text" name="date"/></div>
  <div class="formRow">Feeding Time: <input type="text" name="time"/></div>
  <div class="formRow">Quantity: <input type="text" name="quantity"/></div>
  <input type="submit"/>
</html:form>
</body>
</html>
