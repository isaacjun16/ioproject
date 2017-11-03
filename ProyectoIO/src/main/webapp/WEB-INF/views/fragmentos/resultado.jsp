<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="func" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="paso" items="${resultado.pasos}">
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
						<tr>
							<th><c:out value="Proveedor ${statusFila.count}"/> </th>
							<c:forEach var="celda" items="${fila.preciosCeldas}">
								<td>
									<c:out value="${celda.unidades}"/>
									<input type="number" value="${celda.precio}" readonly="readonly"/>
								</td>
							</c:forEach>
							<td><c:out value="${paso.oferta[statusFila.index]}"/> </td>
							<c:if test="${fila.tienePenalizacion}">
								<td><c:out value="${fila.penalizacionCelda}"/> </td>
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
								<th><c:out value="${penalizacion}"/> </th>
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