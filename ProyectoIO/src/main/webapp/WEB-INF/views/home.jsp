<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="func" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Problemas de Transporte</title>

<link rel="shortcut icon"
	href="<c:url value='/static/images/favicon.png' />"></link>

<link href="<c:url value='/static/css/ubuntu.bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/font-awesome.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/template.css' />" rel="stylesheet"></link>

<script type="text/javascript" src="<c:url value='/static/js/jquery-3.2.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js/validator.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js/template.js' />"></script>

</head>
<body>
	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Problemas de Transporte</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-2">
			<form id="FormTransport" class="navbar-form navbar-left">
				<div class="form-group">
					<input id="Oferta" name="oferta" type="text" class="form-control" placeholder="Oferta" required="required" pattern="^[0-9]+((,[0-9]+))+?$" title="Oferta"/>
				</div>
				<div class="form-group">
					<input id="Demanda" name="demanda" type="text" class="form-control" placeholder="Demanda" required="required" pattern="^[0-9]+((,[0-9]+))+?$" title="Demanda"/>
				</div>
				<div class="form-group">
					<select class="form-control" required="required">
						<option value="">Elija un metodo de resolución</option>
						<option value="1">Esquina Noroeste</option>
						<option value="2">Costos Mínimos</option>
						<option value="3">Aproximación de Voguel</option>
						<option value="4">Otro</option>
					</select>
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
	</div>
	</nav>
	<div>
		<div class="row">
			<div class="col-md-12">
				<div id="TablaCostosDiv" class="table-responsive">
				</div>
			</div>
		</div>
	</div>
</body>
</html>