package gt.edu.umg.io.dto;

import java.util.List;

public class TransporteRequest {

	private List<Integer> oferta;
	private List<Integer> demanda;
	private Double[][] precios;
	
	public List<Integer> getOferta() {
		return oferta;
	}
	public void setOferta(List<Integer> oferta) {
		this.oferta = oferta;
	}
	public List<Integer> getDemanda() {
		return demanda;
	}
	public void setDemanda(List<Integer> demanda) {
		this.demanda = demanda;
	}
	public Double[][] getPrecios() {
		return precios;
	}
	public void setPrecios(Double[][] precios) {
		this.precios = precios;
	}
}
