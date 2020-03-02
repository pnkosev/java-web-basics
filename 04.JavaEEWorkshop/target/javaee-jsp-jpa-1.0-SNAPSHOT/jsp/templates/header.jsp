<%--
  Created by IntelliJ IDEA.
  User: CDA1
  Date: 3/2/2020
  Time: 9:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-background">
    <a class="nav-link text-white active h5" href="/index">Home</a>
    <div class="collapse navbar-collapse d-flex justify-content-end">
        <ul class="navbar-nav row">
            <% if (request.getSession().getAttribute("username") == null) { %>
            <li class="nav-item col-md-4">
                <a class="nav-link text-white active font-weight-bold" href="/users/login">Login</a>
            </li>
            <li class="nav-item col-md-4">
                <a class="nav-link text-white active font-weight-bold" href="/users/register">Register</a>
            </li>
            <% } else {%>
            <li class="nav-item col-md-4">
                <a class="nav-link text-white active font-weight-bold" href="/users/logout">Logout</a>
            </li>
            <% }%>
        </ul>
    </div>
</nav>
