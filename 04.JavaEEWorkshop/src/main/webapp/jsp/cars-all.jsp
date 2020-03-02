<%--
  Created by IntelliJ IDEA.
  User: CDA1
  Date: 2/27/2020
  Time: 2:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="domain.model.view.CarViewModel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="templates/head.jsp" />
</head>
<body>
<div class="container-fluid">
    <c:import url="templates/header.jsp" />
    <h2 class="text-center text-white mt-5">West Compass Offers</h2>
    <hr style="width: 50%"/>
    <div class='row mb-4 d-flex justify-content-around'>
        <% for (CarViewModel car : (List<CarViewModel>) request.getAttribute("carViewModels")) {%>
        <div class="col-md-4 d-flex flex-column bg-text mb-3">
            <h2>Owner: <%= car.getUserUsername() %></h2>
            <h2>Brand: <%= car.getBrand() %></h2>
            <h4>Model: <%= car.getModel() %></h4>
            <h4>Year: <%= car.getYear() %></h4>
            <h4>Engine: <%= car.getEngine().toString().toLowerCase() %></h4>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
