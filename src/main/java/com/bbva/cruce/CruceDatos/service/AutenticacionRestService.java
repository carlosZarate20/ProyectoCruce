package com.bbva.cruce.CruceDatos.service;

import com.bbva.cruce.CruceDatos.model.AutenticacionBean;
import com.bbva.cruce.CruceDatos.model.RespuestaWS;
import com.bbva.cruce.CruceDatos.model.UsuarioAuthBean;

public interface AutenticacionRestService {
	RespuestaWS<UsuarioAuthBean> authenticate(AutenticacionBean autenticacionBean) throws Exception;
}
