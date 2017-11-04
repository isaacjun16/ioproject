var letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
	'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

function globalLoading(mode){
	switch(mode){
		case 0:
			$("#GlobalLoadingHolder").css("display", "none");
		break;
		case 1:
			$("#GlobalLoadingHolder").css("display", "block");
		break;
		default:
		break;
	}
}

$(document).ready(function() {
	$('.limpiarAreaTrabajo').on('click', function(e) {
		$('#FormTransport')[0].reset();
		$('#TablaCostosDiv, #TablaResultado').html("");
		
	});
	
	$('#FormTransport').validator().on('submit', function(e) {
		e.preventDefault();
		
		var submitForm = true;
		if($('#TablaCostosDiv').has('input.precioCero').length != 0) {
			submitForm = confirm("No ha ingresado todos los costos de la tabla, desea enviarlo asi?");
		}
		
		var emptyValue = false;
		$('#TablaCostosDiv input.precioIngresado').each(function(index) {
			if($(this).val() == "") emptyValue = true;
		});
		
		if(emptyValue){
			submitForm = false;
			alert("Existen campos de Precio vacios");
		}
		
		if(submitForm){
			var oferta = JSON.stringify($('#Oferta').val().split(","));
			var demanda = JSON.stringify($('#Demanda').val().split(","));
			var metodo = $('#Metodo').val();
			var precios = JSON.stringify($("#TablaCostosDiv :input").serializeArray());
			
			$.ajax({
				type: "POST",
				url: "resolverproblema",
				dataType: "html",
				data: { 
					oferta: oferta, 
					demanda: demanda,
					metodo: metodo,
					precios: precios
				},
				success: function(data){
					globalLoading(0);
					console.log(data);
					$("#TablaResultado").html(data);
				},
				error: function(error){
					globalLoading(0);
					console.log(error);
				},
			    complete: function(xhr, textStatus) {
			    	
			    }
			});
		}
		
	});
	
	$('#FormTransport input[type="text"]').on('change', function(e) {
		if($('#FormTransport').has('.has-error input[type="text"]').length == 0) {
			if($('#Oferta').val() !== "" && $('#Demanda').val() != "") {
				document.getElementById('TablaCostosDiv').innerHTML = "";
				var oferta = $('#Oferta').val().split(",");
				var demanda = $('#Demanda').val().split(",");
				
				var ofertatotal = 0;
				$.each(oferta, function(index, value) {
					ofertatotal += parseInt(value);
				});
				
				var demandatotal = 0;
				$.each(demanda, function(index, value) {
					demandatotal += parseInt(value);
				});
				
				var cuadreOferta = false; 
				var cuadreDemanda = false;
				if(ofertatotal > demandatotal) {
					demanda.push(ofertatotal - demandatotal);
					$('#Demanda').val($('#Demanda').val() + ',' + (ofertatotal - demandatotal));
					cuadreDemanda = true;
				} else if(ofertatotal < demandatotal) {
					oferta.push(demandatotal - ofertatotal);
					$('#Oferta').val($('#Oferta').val()+ ',' + (demandatotal - ofertatotal));
					cuadreOferta = true;
				}
				
				var table = document.createElement('TABLE');
				table.className = 'table table-bordered';
				
				var thead = document.createElement('THEAD');
				
				var tbody = document.createElement('TBODY');
				
				var tfoot = document.createElement('TFOOT');
				
				var trHead = document.createElement('TR');
				var thHeadEmpty = document.createElement('TH');
				thHeadEmpty.setAttribute("style", "width: 15%;");
				trHead.appendChild(thHeadEmpty);
				var trFoot = document.createElement('TR');
				var thTituloDemanda = document.createElement('TH');
				thTituloDemanda.innerHTML = "Demanda";
				trFoot.appendChild(thTituloDemanda);
				for(var col = 0; col < demanda.length; col++){
					var thHead = document.createElement('TH');
					thHead.innerHTML = letters[col];
					trHead.appendChild(thHead);
					
					var thFoot = document.createElement('TH');
					thFoot.innerHTML = demanda[col];
					trFoot.appendChild(thFoot);
				}
				var thTituloOferta = document.createElement('TH');
				thTituloOferta.innerHTML = "Oferta";
				thTituloOferta.setAttribute("style", "width: 16%;");
				trHead.appendChild(thTituloOferta);
				thead.appendChild(trHead);
				trFoot.appendChild(document.createElement('TH'));
				tfoot.appendChild(trFoot);
				
				for(var row = 0; row < oferta.length; row++){
					var trBody = document.createElement('TR');
					var thBody = document.createElement('TH');
					thBody.innerHTML = "Proveedor " + (row + 1);
					trBody.appendChild(thBody);
					for(var col = 0; col < demanda.length; col++){
						var tdBody = document.createElement('TD');
						if((cuadreDemanda && (col + 1) == demanda.length) ||
								(cuadreOferta && (row + 1 == oferta.length))) {
							tdBody.innerHTML = '<input type="number" name="precio[' + row + '][' + col + ']" value="0" readonly class="readonly"/>';
						} else {
							tdBody.innerHTML = '<input type="number" name="precio[' + row + '][' + col + ']" value="0" min="0" class="precioCero" onchange="this.className = \'precioIngresado\'"/>';
						}
						
						trBody.appendChild(tdBody);
					}
					var thBody2 = document.createElement('TH');
					thBody2.innerHTML = oferta[row];
					trBody.appendChild(thBody2);
					tbody.appendChild(trBody);
				}

				table.appendChild(thead);
				table.appendChild(tbody);
				table.appendChild(tfoot);
				document.getElementById('TablaCostosDiv').appendChild(table);
			}
		}
	});
});