package gt.edu.umg.io.dto;

import java.util.List;

public class TransporteResponse {

	private String[] titulos;
	private List<TransportePasos> pasos;

	public String[] getTitulos() {
		return titulos;
	}

	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}

	public List<TransportePasos> getPasos() {
		return pasos;
	}

	public void setPasos(List<TransportePasos> pasos) {
		this.pasos = pasos;
	}
}
