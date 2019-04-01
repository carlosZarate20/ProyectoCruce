package com.bbva.cruce.CruceDatos.service;

import java.util.Map;

import com.bbva.cruce.CruceDatos.model.AutenticacionBean;
import com.bbva.cruce.CruceDatos.model.AuthResponse;

public interface AuthLDAPWs {
	AuthResponse authenticate(AutenticacionBean autenticacionBean, Map<String, String> parameters) throws Exception;
}
