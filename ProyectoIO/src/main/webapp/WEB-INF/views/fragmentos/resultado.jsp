<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="paso" items="${resultado.pasos}" varStatus="statusPaso">
	<c:if test="${fn:length(resultado.pasos) gt 1}">
		<c:if test="${statusPaso.first}"><c:out value="<div id='PasosResolucionMetodo' class='collapse'>" escapeXml="false"/> </c:if>
		<c:if test="${statusPaso.last}"><c:out value="</div>" escapeXml="false"/> </c:if>
	</c:if>
	<div class="col-md-12" style="<c:if test="${(fn:length(resultado.pasos) gt 1) and statusPaso.last}">height: 75px;</c:if>">
		<h3 class="<c:if test="${(fn:length(resultado.pasos) gt 1) and statusPaso.last}">pull-left</c:if>">
		<c:choose>
			<c:when test="${statusPaso.last}">
				<c:out value="Resultado"/> 
			</c:when>
			<c:otherwise>
				<c:out value="Paso No. ${statusPaso.count}"/> 
			</c:otherwise>
		</c:choose>
		</h3>
		<c:if test="${(fn:length(resultado.pasos) gt 1) and statusPaso.last}">
			<button type="button" class="btn btn-info pull-right" data-toggle="collapse" data-target="#PasosResolucionMetodo">Mostrar Pasos de Resolución</button>
		</c:if>
	</div>
	<c:if test="${statusPaso.last}">
		<div class="col-md-12">
			<div class="alert alert-success" role="alert">
				<h4>
					<c:set var="total" value="${0}"/>
					<c:set var="cuentaUnidadesAsignadas" value="${0}"/>
					<c:forEach var="up" items="${resultado.unidadesPrecios}" varStatus="statusUP">
						<fmt:formatNumber var="celdaPrecio" type="number" minFractionDigits="0" maxFractionDigits="2" value="${up.precio}" />
						<c:set var="total" value="${total + (up.unidad * up.precio)}"/>
						<c:out value="${up.unidad}(${celdaPrecio})"/>
						<c:if test="${(fn:length(resultado.unidadesPrecios) gt 1) and not statusUP.last}"><c:out value=" + "/> </c:if>
					</c:forEach>
					<c:out value=" = ${total}"/>
				</h4>
			</div>
		</div>
	</c:if>
	<div class="col-md-12">
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th style="width: 15%;"></th>
						<c:forEach var="titulo" items="${paso.demanda}" varStatus="status">
							<th><c:out value="${resultado.titulos[status.index]}"/> </th>
						</c:forEach>
						<th style="width: 8%;"><c:out value="Oferta"/> </th>
						<c:if test="${paso.tienePenalizacion}">
							<th style="width: 8%;"><c:out value="Penalización"/> </th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="fila" items="${paso.preciosFilas}" varStatus="statusFila">
						<tr class="<c:if test="${paso.oferta[statusFila.index] == 0}">info</c:if>">
							<th><c:out value="Proveedor ${statusFila.count}"/> </th>
							<c:forEach var="celda" items="${fila.preciosCeldas}" varStatus="statusCelda">
								<td class="<c:if test="${celda.activa}">unidadAsignada</c:if> <c:if test="${paso.demanda[statusCelda.index] == 0}">colInfo</c:if>">
									<c:if test="${celda.unidades != 0}">
										<c:out value="${celda.unidades}"/>
									</c:if>
									<fmt:formatNumber var="celdaPrecio" type="number" minFractionDigits="0" maxFractionDigits="2" value="${celda.precio}" />
									<input type="number" value="${celdaPrecio}" readonly="readonly"/>
								</td>
							</c:forEach>
							<th><c:out value="${paso.oferta[statusFila.index]}"/> </th>
							<c:if test="${fila.tienePenalizacion}">
								<fmt:formatNumber var="celdaPenalizacion" type="number" minFractionDigits="0" maxFractionDigits="2" value="${fila.penalizacionCelda}" />
								<th>
									<c:choose>
										<c:when test="${fila.penalizacionCelda >= 0}">
											<c:out value="${celdaPenalizacion}"/>
										</c:when>
										<c:otherwise>
											<c:out value="-"/>
										</c:otherwise>
									</c:choose>
								</th>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th><c:out value="Demanda"/> </th>
						<c:forEach var="demanda" items="${paso.demanda}">
							<th><c:out value="${demanda}"/> </th>
						</c:forEach>
						<th></th>
						<c:if test="${paso.tienePenalizacion}">
							<th></th>
						</c:if>
					</tr>
					<c:if test="${paso.tienePenalizacion}">
						<tr>
							<th><c:out value="Penalización"/> </th>
							<c:forEach var="penalizacion" items="${paso.penalizacionesColumna}">
								<fmt:formatNumber var="celdaPenalizacion" type="number" minFractionDigits="0" maxFractionDigits="2" value="${penalizacion}" />
								<th>
								<c:choose>
									<c:when test="${penalizacion >= 0}">
										<c:out value="${celdaPenalizacion}"/> 
									</c:when>
									<c:otherwise>
										<c:out value="-"/>
									</c:otherwise>
								</c:choose>
								</th>
							</c:forEach>
							<th></th>
							<c:if test="${paso.tienePenalizacion}">
								<th></th>
							</c:if>
						</tr>
					</c:if>
				</tfoot>
			</table>
		</div>
	</div>
</c:forEach>