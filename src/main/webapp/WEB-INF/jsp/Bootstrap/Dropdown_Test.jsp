<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<link rel="stylesheet" href="css/dropdown.css">
	<title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>


	<div class="wrapper">
<%--		<div class="d-flex" id="wrapper">--%>
<%--			<div class="bg-light border-right" id="sidebar-warpper">--%>
<%--				<div class="sidebar-heading">Start Bootstrap</div>--%>
<%--				<div class="list-group list-group-flush">--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Dashboard</a>--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Shortcuts</a>--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Overview</a>--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Events</a>--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Profile</a>--%>
<%--					<a class="list-group-item list-group-item-action bg-light" href="#!">Status</a>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--		</div>--%>

	<ul class="nav flex-column">
		<li class="nav-item">
			<a class="nav-link active" href="#">Active</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#">Link</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#">Link</a>
		</li>
		<li class="nav-item">
			<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		</li>
	</ul>
	<br>
	<br>

	<ul class="nav nav-pills flex-column">
		<li class="nav-item">
			<a class="nav-link" href="#">Active</a>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Dropdown</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#">Action</a>
				<a class="dropdown-item" href="#">Another action</a>
				<a class="dropdown-item" href="#">Something else here</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="#">Separated link</a>
			</div>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#">Link</a>
		</li>
		<li class="nav-item">
			<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		</li>
	</ul>
	<br>
	<br>

	<div class="row">
		<div class="col-1 align-content-center" >
			<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
				<a class="nav-link" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true">Home</a>
				<a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">Profile</a>
				<a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">Messages</a>
				<a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">Settings</a>
			</div>
		</div>
		<br>
		<br>

		<div class="row">
			<ul class="nav nav-pills flex-column">
				<li class="nav-item">
					<a class="nav-link" href="#">Active</a>
				</li>
				<li class="nav-item dropdown">
					<button class="nav-link dropdown-toggle" data-toggle="dropdown"  href="#" role="button" aria-haspopup="true" aria-expanded="false">Dropdown</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="#">Action</a>
						<a class="dropdown-item" href="#">Another action</a>
						<a class="dropdown-item" href="#">Something else here</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">Separated link</a>
					</div>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Link</a>
				</li>
				<li class="nav-item">
					<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
				</li>
			</ul>
		</div>
<%--		<div class="col-9">--%>
<%--			<div class="tab-content" id="v-pills-tabContent">--%>
<%--				<div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">asdfas</div>--%>
<%--				<div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">asdfas</div>--%>
<%--				<div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">asdfas</div>--%>
<%--				<div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">asdfasd</div>--%>
<%--			</div>--%>
<%--		</div>--%>
	</div>

<%--		<nav class="navbar navbar-expand-md navbar-light bg-light">--%>
<%--			<div class="container-fluid">--%>
<%--				<a class="navbar-brand" href="#">Navbar</a>--%>
<%--			</div>--%>
<%--		</nav>--%>
	</div>

	<!--Navbar-->
	<nav class="navbar navbar-toggleable-md navbar-dark bg-primary">
		<div class="container">
			<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav1" aria-controls="navbarNav1" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a class="navbar-brand" href="#">
				<strong>Navbar</strong>
			</a>
			<div class="collapse navbar-collapse" id="navbarNav1">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active">
						<a class="nav-link">Home <span class="sr-only">(current)</span></a>
					</li>
					<li class="nav-item">
						<a class="nav-link">Features</a>
					</li>
					<li class="nav-item">
						<a class="nav-link">Pricing</a>
					</li>
					<li class="nav-item dropdown btn-group">
						<a class="nav-link dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
						<div class="dropdown-menu dropdown" aria-labelledby="dropdownMenu1">

							<div class="container">

								<div class="row">

									<div class="col-md-2 offset-md-1">
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
									</div>

									<div class="col-md-2">
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
									</div>

									<div class="col-md-2">
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
									</div>

									<div class="col-md-2">
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
									</div>

									<div class="col-md-2">
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
										<a class="dropdown-item" href="#">Link</a>
									</div>

								</div>

							</div>

						</div>
					</li>
				</ul>
				<form class="form-inline waves-effect waves-light">
					<input class="form-control" type="text" placeholder="Search">
				</form>
			</div>
		</div>
	</nav>

	<!--  부트스트랩 js 사용 -->
	<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
</body>
<%--<%@ include file="layout/footer.jsp" %>--%>
</html>
