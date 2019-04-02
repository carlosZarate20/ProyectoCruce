package com.bbva.cruce.CruceOfertas.model;

public class CargaOfertas {

	private boolean ok;
	private String tipDoi; 
	private String numDoi;
	private String detalleError;
	private int index;
	
//	public CargaOfertas() {
//		
//	}
//	
//	public CargaOfertas(String name, String tipDoi, String numDoi, int index) {
//		super();
//		this.name = name;
//		this.tipDoi = tipDoi;
//		this.numDoi = numDoi;
//		this.index = index;
//	}
	
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	public String getTipDoi() {
		return tipDoi;
	}
	
	public void setTipDoi(String tipDoi) {
		this.tipDoi = tipDoi;
	}
	
	public String getNumDoi() {
		return numDoi;
	}
	
	public void setNumDoi(String numDoi) {
		this.numDoi = numDoi;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
}
