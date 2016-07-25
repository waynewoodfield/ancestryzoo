<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<% Map<Integer, String> animalMap = (Map<Integer, String>)request.getAttribute("animalMap"); %>

<logic:present parameter="success">
  <div style="color: #090;">Your feeding was successfully entered</div>
</logic:present>
<form action="<%=request.getContextPath()%>/processFeeding.do" method="post">
  <div class="formRow">Animal:
    <select name="animalId">
      <logic:iterate id="animalId" name="animals" type="Integer">
        <option value="<%=animalId%>"><%=animalMap.get(animalId)%></option>
      </logic:iterate>
    </select>
  </div>
  <div class="formRow">Feeding Date (yyyy-mm-dd): <input type="text" name="date"/></div>
  <div class="formRow">Feeding Time (hh:mm) (24-hour time): <input type="text" name="time"/></div>
  <div class="formRow">Quantity: <input type="text" name="quantity"/></div>
  <input type="submit"/>
</form>
