<%--标签 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %> <%--可以使用EL 表达式--%>
<%@ page trimDirectiveWhitespaces="true" %> <%--jsp输出的html时去除多余的空行--%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %> <%--将标签库描述符文件引用到该页面中--%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %><%--简化jsp字符串操作--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%--用来格式化和显示文本、日期、时间、数字。--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %><%--用于在JSP/GSP页面进行权限控制--%>


<%--basePath
    第一个是得到请求头
    第二个返回发送请求的服务器的主机名
    第三个返回发送请求的端口号
    http://localhost:8080
    第四个返回url相对路径
     --%>
<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<%--静态文件目录 --%>
<c:set var="path" value="${base}" />
<%--项目路径 --%>
<c:set var="staticPath" value="${base}" />

