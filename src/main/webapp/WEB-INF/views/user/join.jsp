<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<script src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<body>
	<div class="center-content">
		<a href="${pageContext.request.contextPath}/"><h1 class="logo">JBlog</h1></a>
		<c:import url='/WEB-INF/views/includes/menu.jsp' />
<form:form
modelAttribute="userVo"
id="join-form"
name="joinForm"
method="post"
class="join-form"
action="${pageContext.request.contextPath}/user/join">

			<label class="block-label" for="name">이름</label>
			<form:input path='name' />
			<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0;">
				<form:errors path="name" />
			</p>
			
			<label class="block-label" for="id">아이디</label>
			<form:input path='id' /> 
			
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0;">
				<form:errors path="id" />
			</p>

			<label class="block-label" for="password">패스워드</label>
			<form:password path="password" />
			<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0;">
				<form:errors path="password" />
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
<script>

$('#btn-checkemail').click(function(){
	
	$.ajax({
		url: "${pageContext.servletContext.contextPath}/user/api/checkemail",
		type: "post",
		cache: false,
		dataType: "json",
		data: {
			'id' : $("#id").val()	
		},
		success: function(response) {
			console.log(response);
			if(response.data) {
				//중복될 경우
				alert("중복되는 id가 있습니다.");
				return;
			}
			
			// 중복 없음.
			$('#img-checkemail').show();
			$('#btn-checkemail').hide();
		},
		error: function (request, status, error) {
			console.log(request);
			console.log(status);
			console.log(error);
		}
	});
	
});

</script>
</html>
