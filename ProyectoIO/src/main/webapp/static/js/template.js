var letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

$(document).ready(function() {
	$('#FormTransport').validator().on('submit', function(e) {
		e.preventDefault();
		
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
					cuadreDemanda = true;
				} else if(ofertatotal < demandatotal) {
					oferta.push(demandatotal - ofertatotal);
					cuadreOferta = true;
				}
				
				var table = document.createElement('TABLE');
				table.className = 'table table-bordered';
				
				var thead = document.createElement('THEAD');
				
				var tbody = document.createElement('TBODY');
				
				var tfoot = document.createElement('TFOOT');
				
				var trHead = document.createElement('TR');
				trHead.appendChild(document.createElement('TH'));
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
							tdBody.innerHTML = '<input type="number" name="precio' + row + '[]" value="0" readonly class="readonly"/>';
						} else {
							tdBody.innerHTML = '<input type="number" name="precio' + row + '[]" value="0"/>';
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