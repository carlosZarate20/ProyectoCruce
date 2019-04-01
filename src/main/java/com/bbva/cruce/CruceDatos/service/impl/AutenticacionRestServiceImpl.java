package com.bbva.cruce.CruceDatos.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbva.cruce.CruceDatos.enums.AuthLDAPResult;
import com.bbva.cruce.CruceDatos.model.AutenticacionBean;
import com.bbva.cruce.CruceDatos.model.AuthResponse;
import com.bbva.cruce.CruceDatos.model.RespuestaWS;
import com.bbva.cruce.CruceDatos.model.UsuarioAuthBean;
import com.bbva.cruce.CruceDatos.service.AutenticacionRestService;
import com.bbva.cruce.CruceDatos.service.AuthLDAPWs;
import com.bbva.cruce.CruceDatos.util.PropertyUtil;

@Service
public class AutenticacionRestServiceImpl implements AutenticacionRestService {

	@Autowired
	private AuthLDAPWs authLDAPWs;
	
	@Autowired
	private PropertyUtil propertyUtil;
	
	@Override
	public RespuestaWS<UsuarioAuthBean> authenticate(AutenticacionBean autenticacionBean) throws Exception {
		
		RespuestaWS<UsuarioAuthBean> responseUser = new RespuestaWS<>();
		Map<String, String> parameters = new HashMap<>();
		
		parameters.put("ws.auth.ldap.service.url", propertyUtil.getString("ws.auth.ldap.service.url"));
		parameters.put("ws.cruceOfertas.application.code", propertyUtil.getString("ws.cruceOfertas.application.code"));
		
		AuthResponse authResponse = authLDAPWs.authenticate(autenticacionBean, parameters);
		
		UsuarioAuthBean response = new UsuarioAuthBean();
		
		if(authResponse.getResultCode().startsWith("9")) {
			throw new ServiceException(authResponse.getResultMessage());
		}
		
		if(!authResponse.getResultCode().equals(AuthLDAPResult.SUCCESSFUL.getCode())) {
			response.setAuthenticated(false);
			response.setMessage(authResponse.getResultMessage());
			responseUser.setObjetoRespuesta(response);
			responseUser.setEstado(-1);
			return responseUser;
		}	
		
		response.setAuthenticated(true);
		response.setMessage(authResponse.getResultMessage());
		responseUser.setEstado(1);
		return responseUser;
	}

}
