package gt.edu.umg.io.dto;

import java.util.List;

public class PreciosFilaDto {

	private List<PrecioCeldaDto> preciosCeldas;
	private Double penalizacionCelda;
	private boolean tienePenalizacion;

	public List<PrecioCeldaDto> getPreciosCeldas() {
		return preciosCeldas;
	}

	public void setPreciosCeldas(List<PrecioCeldaDto> preciosCeldas) {
		this.preciosCeldas = preciosCeldas;
	}

	public Double getPenalizacionCelda() {
		return penalizacionCelda;
	}

	public void setPenalizacionCelda(Double penalizacionCelda) {
		this.penalizacionCelda = penalizacionCelda;
	}

	public boolean getTienePenalizacion() {
		return tienePenalizacion;
	}

	public void setTienePenalizacion(boolean tienePenalizacion) {
		this.tienePenalizacion = tienePenalizacion;
	}
}
