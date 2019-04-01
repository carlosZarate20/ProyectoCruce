package com.bbva.cruce.CruceDatos.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class SessionFilter implements Filter{
	private static final Logger LOGGER = LogManager.getLogger(SessionFilter.class);
	
	private static final String ACCESS_DENIED_PAGE = "/error/accessDenied";
	private static final String RESOURCES = "/resources/";
	private static final String LOGIN_PAGE = "/loginBBVA";
	private static final String LOGIN_ACTION = "/login";
	private static final String LOGOUT_ACTION = "/logout";
	private static final String CARGA_OFERTAS = "/carga/";
	
	private boolean isPageExcluded(String path) {
		return path.equals(ACCESS_DENIED_PAGE)
				|| path.equals(LOGIN_PAGE)
				|| path.equals(LOGIN_ACTION)
				|| path.equals(LOGOUT_ACTION)
				|| path.startsWith(RESOURCES)
				|| path.startsWith(CARGA_OFERTAS);
	}
	private boolean isSessionInvalid(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session == null) {
			LOGGER.info("Sesion invalida");
			return true;
		}
		String username = (String) session.getAttribute("username");

		boolean sessionInvalid = username == null;
		if (sessionInvalid) {
			LOGGER.info("Sesion invalida");
		}
		return sessionInvalid;
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		String xRequestWith = request.getHeader("X-Requested-With");
		LOGGER.trace("xRequestWith: " + xRequestWith);

		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			return true;
		}
		return false;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(StandardCharsets.UTF_8.name());
		if((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			
			String pageUri = httpServletRequest.getRequestURI();
			String path = getPath(httpServletRequest);
			
			LOGGER.debug("pageUri: {}", pageUri);
			LOGGER.debug("Path: {}", path);
			
			if (isPageExcluded(path)) {
				chain.doFilter(request, response);
				return;
			}
			
			if (isSessionInvalid(httpServletRequest)) {
				LOGGER.info("Acceso denegado!...");

				if (isAjaxRequest(httpServletRequest)) {
					String jsonError = "{ \"hasError\": true, \"message\" : \"Acceso Denegado\" }";
					httpServletResponse.setHeader("Cache-Control", "no-cache");
					httpServletResponse.setCharacterEncoding("UTF-8");
					httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
					httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
					PrintWriter pw = response.getWriter();
					pw.println(jsonError);
					pw.flush();
					return;
				}

				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	private static final String getPath(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
	}
}
