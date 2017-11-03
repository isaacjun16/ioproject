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

public class Voguel {
	
	private static final Logger log = Logger.getLogger(Voguel.class.getName());
	
	public static TransporteResponse resolver(TransporteRequest data) {
		TransporteResponse resultado = new TransporteResponse();
		System.out.println(Arrays.deepToString(data.getPrecios()));
		try {
			List<TransportePasos> pasos = new ArrayList<>();
			int[][] unidadesAsignadas = new int[data.getOferta().size()][data.getDemanda().size()];
			boolean resuelto = false;
			
			//do {
				
				TransportePasos paso = new TransportePasos();
				paso.setTienePenalizacion(true);
				
				//Calculando penalización de filas
				double[] penalizacionesFilas = new double[data.getOferta().size()];
				for(int row = 0; row < data.getOferta().size(); row++) {
					List<Double> fila = Arrays.asList(data.getPrecios()[row].clone());
					Collections.sort(fila);
					double numero1 = fila.get(0);
					double numero2 = fila.get(1);
					
					penalizacionesFilas[row] = numero2 - numero1;
				}
				
				//Calculando penalización de columnas
				double[] penalizacionesColumnas = new double[data.getDemanda().size()];
				for(int col = 0; col < data.getDemanda().size(); col++) {
					List<Double> columna = new ArrayList<>();
					for(int row = 0; row < data.getOferta().size(); row++) {
						columna.add(data.getPrecios()[row][col]);
					}
					Collections.sort(columna);
					double numero1 = columna.get(0);
					double numero2 = columna.get(1);
					
					penalizacionesColumnas[col] = numero2 - numero1;
				}
				
				//Hayando la mayor penalizacion de filas
				int indexPenFila = 0;
				for(int i = 0; i < penalizacionesFilas.length; i++) {
					if(penalizacionesFilas[i] > penalizacionesFilas[indexPenFila]) {
						indexPenFila = i;
					}
				}
				
				//Hayando 
				int indexPenCol = 0;
				
				
				//Armando tabla vista
				List<PreciosFilaDto> preciosFilas = new ArrayList<>();
				for(int row = 0; row < data.getOferta().size(); row++) {
					PreciosFilaDto fila = new PreciosFilaDto();
					List<PrecioCeldaDto> celdas = new ArrayList<>();
					for(int col = 0; col < data.getDemanda().size(); col++) {
						PrecioCeldaDto celda = new PrecioCeldaDto();
						celda.setUnidades(unidadesAsignadas[row][col]);
						celda.setPrecio(data.getPrecios()[row][col]);
						celda.setUnidades(1);
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
				paso.setOferta(data.getOferta());
				paso.setDemanda(data.getDemanda());
				
				
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
				
			//}while(!resuelto);
			
			resultado.setPasos(pasos);
		} catch (Exception e) {
			log.log(Level.INFO, "Fallo resolviendo con le metodo voguel", e);
		}
		
		return resultado;
	}
}
