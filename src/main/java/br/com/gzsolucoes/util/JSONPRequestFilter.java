package br.com.gzsolucoes.util;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/services/*")
public class JSONPRequestFilter implements Filter {

	@Inject
	private Logger log;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    	if (!(request instanceof HttpServletRequest)) {
	        throw new ServletException("Only HttpServletRequest requests are supported");
	    }
    	
    	final HttpServletRequest httpRequest = (HttpServletRequest) request;
    	
    	log.info("Recurso Solicitado: \"" + httpRequest.getPathInfo() + "\"");
    	log.info("Informacoes: "
    			+ ", Metodo = " + httpRequest.getMethod()
    			+ ", IP = " + httpRequest.getRemoteAddr() 
    			+ ", Local = " + httpRequest.getLocale());
    }
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing needed
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
