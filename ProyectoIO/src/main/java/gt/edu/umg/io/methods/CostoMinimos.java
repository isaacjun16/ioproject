package gt.edu.umg.io.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gt.edu.umg.io.dto.PrecioCeldaDto;
import gt.edu.umg.io.dto.PreciosFilaDto;
import gt.edu.umg.io.dto.TransportePasos;
import gt.edu.umg.io.dto.TransporteRequest;
import gt.edu.umg.io.dto.TransporteResponse;
import gt.edu.umg.io.utils.TransporteUtils;

public class CostoMinimos {
	
	private static final Logger log = Logger.getLogger(CostoMinimos.class.getName());
			
	public static TransporteResponse resolver(TransporteRequest data) {
		TransporteResponse resultado = new TransporteResponse();
		
		try {
			List<TransportePasos> pasos = new ArrayList<>();
			int[][] unidadesAsignadas = new int[data.getOferta().size()][data.getDemanda().size()];
			boolean resuelto = false;
			int test = 1;
			boolean primerIteracion = true;
			
			do {
				System.out.println("***** Paso " + test + " *****");
				TransportePasos paso = new TransportePasos();
				paso.setTienePenalizacion(false);
				
				//Buscando el costo minimo
				int preciosDisponibles = 0;
				int x = 0,y = 0;
				
				if(!primerIteracion) {
					for(int row = 0; row < data.getOferta().size(); row++) {
						boolean encontroEsquina = false;
						for(int col = 0; col < data.getDemanda().size(); col++) {
							if(data.getOferta().get(row) != 0 &&
									data.getDemanda().get(col) != 0) {
								x = row;
								y = col;
								encontroEsquina = true;
								break;
							}
						}
						if(encontroEsquina) break;
					}
				}
				
				for(int row = 0; row < data.getOferta().size(); row++) {
					for(int col = 0; col < data.getDemanda().size(); col++) {
						if(data.getPrecios()[row][col] < data.getPrecios()[x][y] &&
								data.getOferta().get(row) != 0 &&
								data.getDemanda().get(col) != 0) {
							x = row;
							y = col;
						}
						
						if(data.getOferta().get(row) != 0 &&
								data.getDemanda().get(col) != 0) {
							preciosDisponibles++;
						}
					}
				}
				
				if(preciosDisponibles == 1) {
					for(int row = 0; row < data.getOferta().size(); row++) {
						for(int col = 0; col < data.getDemanda().size(); col++) {
							if(data.getOferta().get(row) != 0 &&
									data.getDemanda().get(col) != 0) {
								x = row;
								y = col;
							}
						}
					}
				}
				
				System.out.println("Costo minimo en[" + x + "][" + y + "]: " + data.getPrecios()[x][y]);
				
				//Asignando unidades
				List<Integer> oferta = data.getOferta();
				List<Integer> demanda = data.getDemanda();
				if(data.getDemanda().get(y) <= data.getOferta().get(x)) {
					//Oferta es mayor o igual que demanda
					unidadesAsignadas[x][y] = demanda.get(y);
					int restante = oferta.get(x) - demanda.get(y);
					System.out.println(oferta.get(x) + " menos " + demanda.get(y) + " restan: " + restante);
					oferta.set(x, restante);
					demanda.set(y, 0);
					
				} else {
					//Demanda es mayor que oferta
					unidadesAsignadas[x][y] = oferta.get(x);
					int restante = demanda.get(y) - oferta.get(x);
					System.out.println(demanda.get(y) + " menos " + oferta.get(x) + " restan: " + restante);
					oferta.set(x, 0);
					demanda.set(y, restante);
					
				}
				data.setOferta(oferta);
				data.setDemanda(demanda);
				
				System.out.println("Oferta: " + Arrays.toString(data.getOferta().toArray()));
				System.out.println("Demanda: " + Arrays.toString(data.getDemanda().toArray()));
				System.out.println(Arrays.deepToString(unidadesAsignadas));
				
				//Armando tabla vista
				List<PreciosFilaDto> preciosFilas = new ArrayList<>();
				for(int row = 0; row < data.getOferta().size(); row++) {
					PreciosFilaDto fila = new PreciosFilaDto();
					List<PrecioCeldaDto> celdas = new ArrayList<>();
					for(int col = 0; col < data.getDemanda().size(); col++) {
						PrecioCeldaDto celda = new PrecioCeldaDto();
						celda.setUnidades(unidadesAsignadas[row][col]);
						celda.setPrecio(data.getPrecios()[row][col]);
						celda.setActiva(unidadesAsignadas[row][col] > 0);
						celdas.add(celda);
					}
					fila.setPreciosCeldas(celdas);
					fila.setTienePenalizacion(false);
					preciosFilas.add(fila);
				}
				paso.setPreciosFilas(preciosFilas);
				paso.setOferta(TransporteUtils.clonearLista(data.getOferta()));
				paso.setDemanda(TransporteUtils.clonearLista(data.getDemanda()));
				
				
				pasos.add(paso);
			
				int sumaOferta = 0;
				for(int i = 0; i < data.getOferta().size(); i++) {
					sumaOferta += data.getOferta().get(i);
				}
				
				int sumaDemanda = 0;
				for(int i = 0; i < data.getDemanda().size(); i++) {
					sumaDemanda += data.getDemanda().get(i);
				}
				
				if(sumaOferta <= 0 && sumaDemanda <= 0) {
					resuelto = true;
				}
				test++;
				primerIteracion = false;
			} while(!resuelto && test < 100);
			
			System.out.println("Unidades asignadas: ");
			System.out.println(Arrays.deepToString(unidadesAsignadas));
			
			resultado.setUnidadesPrecios(TransporteUtils.generarListaUnidadesPrecios(data.getOferta().size(),
					data.getDemanda().size(), unidadesAsignadas, data.getPrecios()));
			System.out.println("Total UnidadesPrecios: " + resultado.getUnidadesPrecios().size());
			resultado.setPasos(pasos);
		} catch (Exception e) {
			log.log(Level.INFO, "Fallo resolviendo con le metodo voguel", e);
		}
		
		return resultado;
	}

}
