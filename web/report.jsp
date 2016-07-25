<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.ResultSetMetaData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ResultSet rs = (ResultSet)request.getAttribute("resultSet"); %>

<table border="1">
  <tr>
    <% ResultSetMetaData rsMeta = rs.getMetaData();
    for (int i = 1; i <= rsMeta.getColumnCount(); i++) { %>
    <th><%=rsMeta.getColumnName(i)%></th>
    <% } %>
  </tr>
  <% while (rs.next()) { %>
  <tr>
    <% for (int i = 1; i < rsMeta.getColumnCount(); i++) { %>
    <td><%=rs.getString(i)%></td>
    <% } %>
  </tr>
  <% } %>
</table>
