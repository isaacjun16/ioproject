package gt.edu.umg.io.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gt.edu.umg.io.dto.PrecioCeldaDto;
import gt.edu.umg.io.dto.PreciosFilaDto;
import gt.edu.umg.io.dto.TransportePasos;
import gt.edu.umg.io.dto.TransporteRequest;
import gt.edu.umg.io.dto.TransporteResponse;
import gt.edu.umg.io.utils.TransporteUtils;

public class Voguel {
	
	private static final Logger log = Logger.getLogger(Voguel.class.getName());
	
	public static TransporteResponse resolver(TransporteRequest data) {
		TransporteResponse resultado = new TransporteResponse();
		
		try {
			List<TransportePasos> pasos = new ArrayList<>();
			int[][] unidadesAsignadas = new int[data.getOferta().size()][data.getDemanda().size()];
			boolean resuelto = false;
			int test = 1;
			System.out.println(Arrays.deepToString(unidadesAsignadas));
			do {
				System.out.println("***** Paso " + test + " *****");
				TransportePasos paso = new TransportePasos();
				paso.setTienePenalizacion(true);
				
				//Calculando penalización de filas
				double[] penalizacionesFilas = new double[data.getOferta().size()];
				for(int row = 0; row < data.getOferta().size(); row++) {
					
					Double[] preciosFila = data.getPrecios()[row].clone();
					for(int i = 0; i < preciosFila.length; i++) {
						if(data.getDemanda().get(i) == 0) {
							preciosFila[i] = Double.MAX_VALUE;
						}
					}
					
					List<Double> fila = Arrays.asList(preciosFila);
					Collections.sort(fila);
					double numero1 = fila.get(0);
					double numero2 = fila.get(1) == Double.MAX_VALUE? fila.get(0): fila.get(1);
					
					if(data.getOferta().get(row) == 0) {
						penalizacionesFilas[row] = -1;
					} else {
						penalizacionesFilas[row] = numero2 - numero1;
					}
				}
				
				//Calculando penalización de columnas
				double[] penalizacionesColumnas = new double[data.getDemanda().size()];
				for(int col = 0; col < data.getDemanda().size(); col++) {
					List<Double> columna = new ArrayList<>();
					for(int row = 0; row < data.getOferta().size(); row++) {
						columna.add(data.getPrecios()[row][col]);
					}
					
					for(int i = 0; i < columna.size(); i++) {
						if(data.getOferta().get(i) == 0) {
							columna.set(i, Double.MAX_VALUE);
						}
					}
					
					Collections.sort(columna);
					double numero1 = columna.get(0);
					double numero2 = columna.get(1) == Double.MAX_VALUE? columna.get(0): columna.get(1);
					
					if(data.getDemanda().get(col) == 0) {
						penalizacionesColumnas[col] = -1;
					} else {
						penalizacionesColumnas[col] = numero2 - numero1;
					}
				}
				
				//Hayando la mayor penalizacion de filas
				int indexPenFila = 0;
				for(int i = 0; i < penalizacionesFilas.length; i++) {
					if(penalizacionesFilas[i] > penalizacionesFilas[indexPenFila]) {
						indexPenFila = i;
					}
				}
				
				//Hayando la mayor penalizacion de columnas
				int indexPenCol = 0;
				for(int i = 0; i < penalizacionesColumnas.length; i++) {
					if(penalizacionesColumnas[i] > penalizacionesColumnas[indexPenCol]) {
						indexPenCol = i;
					}
				}
				
				System.out.println("Oferta: " + Arrays.toString(data.getOferta().toArray()));
				System.out.println("Demanda: " + Arrays.toString(data.getDemanda().toArray()));
				//Hayando la mayor penalizacion
				System.out.println("Hayando la mayor penalizacion");
				if(penalizacionesFilas[indexPenFila] >= penalizacionesColumnas[indexPenCol]) {
					//Penalizacion mas alta esta en fila
					Double[] filaPrecios = data.getPrecios()[indexPenFila].clone();
					for(int i = 0; i < data.getDemanda().size(); i++) {
						if(data.getDemanda().get(i) == 0) {
							filaPrecios[i] = Double.MAX_VALUE;
						}
					}
					
					int indexPrecio = 0;
					for(int i = 0; i < filaPrecios.length; i++) {
						if(filaPrecios[i] < filaPrecios[indexPrecio]) {
							indexPrecio = i;
						}
					}
					System.out.println("Precio mas bajo posicion["+indexPenFila+"]["+indexPrecio+"]: " + data.getPrecios()[indexPenFila][indexPrecio]);
					System.out.println("Asignando unidad");
					//Asignando unidades
					List<Integer> oferta = data.getOferta();
					List<Integer> demanda = data.getDemanda();
					if(data.getDemanda().get(indexPrecio) <= data.getOferta().get(indexPenFila)) {
						//Oferta es mayor o igual que demanda
						unidadesAsignadas[indexPenFila][indexPrecio] = demanda.get(indexPrecio);
						int restante = oferta.get(indexPenFila) - demanda.get(indexPrecio);
						System.out.println(oferta.get(indexPenFila) + " menos " + demanda.get(indexPrecio) + " restan: " + restante);
						oferta.set(indexPenFila, restante);
						demanda.set(indexPrecio, 0);
						
					} else {
						//Demanda es mayor que oferta
						unidadesAsignadas[indexPenFila][indexPrecio] = oferta.get(indexPenFila);
						int restante = demanda.get(indexPrecio) - oferta.get(indexPenFila);
						System.out.println(demanda.get(indexPrecio) + " menos " + oferta.get(indexPenFila) + " restan: " + restante);
						oferta.set(indexPenFila, 0);
						demanda.set(indexPrecio, restante);
						
					}
					data.setOferta(oferta);
					data.setDemanda(demanda);
				} else {
					//Penalizacion mas alta esta en columna
					Double[] columnaPrecios = new Double[data.getOferta().size()];
					for(int i = 0; i < data.getOferta().size(); i++) {
						columnaPrecios[i] = data.getPrecios()[i][indexPenCol];
						if(data.getOferta().get(i) == 0) {
							columnaPrecios[i] = Double.MAX_VALUE;
						}
					}
					
					int indexPrecio = 0;
					for(int i = 0; i < columnaPrecios.length; i++) {
						if(columnaPrecios[i] < columnaPrecios[indexPrecio]) {
							indexPrecio = i;
						}
					}
					
					System.out.println("Precio mas bajo posicion["+indexPrecio+"]["+indexPenCol+"]: " + data.getPrecios()[indexPrecio][indexPenCol]);
					System.out.println("Asignando unidad");
					//Asignando unidades
					List<Integer> oferta = data.getOferta();
					List<Integer> demanda = data.getDemanda();
					if(data.getDemanda().get(indexPenCol) <= data.getOferta().get(indexPrecio)) {
						//Oferta es mayor o igual que demanda
						unidadesAsignadas[indexPrecio][indexPenCol] = demanda.get(indexPenCol);
						int restante = oferta.get(indexPrecio) - demanda.get(indexPenCol);
						System.out.println(oferta.get(indexPrecio) + " menos " + demanda.get(indexPenCol) + " restan: " + restante);
						oferta.set(indexPrecio, restante);
						demanda.set(indexPenCol, 0);
						
					} else {
						//Demanda es mayor que oferta
						unidadesAsignadas[indexPrecio][indexPenCol] = oferta.get(indexPrecio);
						int restante = demanda.get(indexPenCol) - oferta.get(indexPrecio);
						System.out.println(demanda.get(indexPenCol) + " menos " + oferta.get(indexPrecio) + " restan: " + restante);
						oferta.set(indexPrecio, 0);
						demanda.set(indexPenCol, restante);
						
					}
					data.setOferta(oferta);
					data.setDemanda(demanda);
				}
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
					fila.setTienePenalizacion(true);
					fila.setPenalizacionCelda(penalizacionesFilas[row]);
					preciosFilas.add(fila);
				}
				paso.setPreciosFilas(preciosFilas);
				//Asignando penalizaciones de columna a vista
				List<Double> penalizacionesColumna = new ArrayList<>();
				for(double pen: penalizacionesColumnas) {
					penalizacionesColumna.add(pen);
				}
				paso.setPenalizacionesColumna(penalizacionesColumna);
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
			}while(!resuelto && test < 100);
			
			resultado.setUnidadesPrecios(TransporteUtils.generarListaUnidadesPrecios(data.getOferta().size(),
					data.getDemanda().size(), unidadesAsignadas, data.getPrecios()));
			resultado.setPasos(pasos);
		} catch (Exception e) {
			log.log(Level.INFO, "Fallo resolviendo con le metodo voguel", e);
		}
		
		return resultado;
	}
}
