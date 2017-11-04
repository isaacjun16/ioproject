package gt.edu.umg.io.dto;

import java.util.List;

public class TransporteResponse {

	private String[] titulos;
	private List<TransportePasos> pasos;
	private List<UnidadPrecioDto> unidadesPrecios;

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

	public List<UnidadPrecioDto> getUnidadesPrecios() {
		return unidadesPrecios;
	}

	public void setUnidadesPrecios(List<UnidadPrecioDto> unidadesPrecios) {
		this.unidadesPrecios = unidadesPrecios;
	}
}
