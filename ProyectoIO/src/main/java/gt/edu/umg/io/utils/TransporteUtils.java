package gt.edu.umg.io.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gt.edu.umg.io.dto.TransporteRequest;

public class TransporteUtils {

	public static TransporteRequest parserSolicitud(String ofertaStr, String demandaStr, String metodo, String preciosStr) {
		TransporteRequest resultado = new TransporteRequest();
		
		JSONArray ofertaArray = new JSONArray(ofertaStr);
		JSONArray demandaArray = new JSONArray(demandaStr);
		JSONArray preciosArray = new JSONArray(preciosStr);
		
		List<Integer> oferta = new ArrayList<>();
		List<Integer> demanda = new ArrayList<>();
		
		for(int i = 0; i < ofertaArray.length(); i++) {
			Integer num = ofertaArray.getInt(i);
			oferta.add(num);
		}
		resultado.setOferta(oferta);
		
		for(int i = 0; i < demandaArray.length(); i++) {
			Integer num = demandaArray.getInt(i);
			demanda.add(num);
		}
		resultado.setDemanda(demanda);
		
		Double[][] precios = new Double[oferta.size()][demanda.size()];
		
		System.out.println(preciosStr);
		int index = 0;
		for(int row = 0; row < oferta.size(); row++) {
			for(int col = 0; col < demanda.size(); col++) {
				JSONObject json = preciosArray.getJSONObject(index++);
				precios[row][col] = json.getDouble("value");
			}
		}
		resultado.setPrecios(precios);
		
		return resultado;
	}
}
