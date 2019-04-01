package com.bbva.cruce.CruceDatos.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bbva.cruce.CruceDatos.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceDatos.model.CargaCabecera;
import com.bbva.cruce.CruceDatos.model.OfertasAprobadasPlantilla;
import com.bbva.cruce.CruceDatos.model.RespuestaWS;

public interface OfertasAprobadasService {
	
	public ArrayList<OfertasAprobadasPlantilla> cargarArchivo(byte[] archivo);
	
	public List<OfertasAprobadasPlantilla> guardarArchivo(List<AplicacionOfertasBean> listOfertas, String identficador, String fileName, HttpServletRequest request,
			String userName);

	public RespuestaWS<List<OfertasAprobadasPlantilla>> listaOfertasAprobadas(HttpServletRequest  request);
	
	public CargaCabecera getCargaUltima(HttpServletRequest request);
	
	public void deleteDataOfertas(String identOferta);
}
