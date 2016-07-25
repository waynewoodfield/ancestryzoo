<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% try { %>
<html>
<head>
	<title><tiles:get name="title"/></title>
</head>
<body>
<table border="0" width="100%">
  <tr>
    <td colspan="2"><h1><tiles:get name="title"/></h1></td>
  </tr>
  <tr>
    <td width="20%"><tiles:get name="nav"/></td>
    <td width="80%"><tiles:get name="content"/></td>
  </tr>
</table>
</body>
</html>
<% } catch (Exception e) { e.printStackTrace(); } %>