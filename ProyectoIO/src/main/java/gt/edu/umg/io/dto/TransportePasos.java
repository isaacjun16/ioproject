package gt.edu.umg.io.dto;

import java.util.List;

public class TransportePasos {
	
	private List<Integer> oferta;
	private List<Integer> demanda;
	private List<PreciosFilaDto> preciosFilas;
	private List<Double> penalizacionesColumna;
	private boolean tienePenalizacion;
	
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
	public List<PreciosFilaDto> getPreciosFilas() {
		return preciosFilas;
	}
	public void setPreciosFilas(List<PreciosFilaDto> preciosFilas) {
		this.preciosFilas = preciosFilas;
	}
	public List<Double> getPenalizacionesColumna() {
		return penalizacionesColumna;
	}
	public void setPenalizacionesColumna(List<Double> penalizacionesColumna) {
		this.penalizacionesColumna = penalizacionesColumna;
	}
	public boolean getTienePenalizacion() {
		return tienePenalizacion;
	}
	public void setTienePenalizacion(boolean tienePenalizacion) {
		this.tienePenalizacion = tienePenalizacion;
	}

}
