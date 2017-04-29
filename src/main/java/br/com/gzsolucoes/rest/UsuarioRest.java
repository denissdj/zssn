package br.com.gzsolucoes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.com.gzsolucoes.enuns.EnumMensagem;
import br.com.gzsolucoes.model.Usuario;
import br.com.gzsolucoes.service.UsuarioService;

@RequestScoped
@Path("/usuario")
public class UsuarioRest extends GenericRest {

	@Inject
	private UsuarioService service;
	
	@Context
	private SecurityContext	SecurityContext;
	
	@Context
	private HttpServletRequest	httpServletRequest;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvar(Usuario usuario) {
        try {
            this.service.salvar(usuario);
            this.getResponseObj().put("msg", "As informações do usuario \"" + usuario.getNome().toUpperCase() + "\" foram salvas com sucesso.");
            this.setBuilder(Response.status(Response.Status.OK).entity(this.getResponseObj()));
            
        } catch (ConstraintViolationException ce) {
        	this.getResponseObj().put("msg", EnumMensagem.MSG_001.getDescricao());
    		this.setBuilder(Response.status(Response.Status.CONFLICT).entity(this.getResponseObj()));
    		
        } catch (ValidationException e) {
            this.getResponseObj().put("msg", e.getMessage());
            this.setBuilder(Response.status(Response.Status.CONFLICT).entity(this.getResponseObj()));
            
        } catch (Exception e) {
        	this.getResponseObj().put("msg", e.getMessage());
             this.setBuilder(Response.status(Response.Status.BAD_REQUEST).entity(this.getResponseObj()));
        }

        return this.getBuilder().build();
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
        	List<Usuario> usuarios = this.service.listar();
            this.getResponseObj().put("usuarios", usuarios);
            this.setBuilder(Response.status(Response.Status.OK).entity(this.getResponseObj()));
            
        } catch (Exception e) {
            this.getResponseObj().put("msg", e.getMessage());
            this.setBuilder(Response.status(Response.Status.BAD_REQUEST).entity(this.getResponseObj()));
        }
        
        return this.getBuilder().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPorId(@PathParam("id") Long id) {
        try {
        	Usuario entidade = this.service.obterPorId(id);
	        if (entidade == null) {
	        	this.getResponseObj().put("msg", EnumMensagem.MSG_002.getDescricao());
	        	this.setBuilder(Response.status(Response.Status.CONFLICT).entity(this.getResponseObj()));
	        	return this.getBuilder().build();
	        }
            this.setBuilder(Response.status(Response.Status.OK).entity(entidade));
            
        } catch (Exception e) {
            this.getResponseObj().put("msg", e.getMessage());
            this.setBuilder(Response.status(Response.Status.BAD_REQUEST).entity(this.getResponseObj()));
        }
        
        return this.getBuilder().build();
    }
    
    @DELETE
	@Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerUsuario(@PathParam("id") Long id) {
        try {
        	this.service.remover(id);
            this.getResponseObj().put("msg", EnumMensagem.MSG_003.getDescricao());
            this.setBuilder(Response.status(Response.Status.OK).entity(this.getResponseObj()));
            
        } catch (Exception e) {
        	if(e.getCause().toString().contains("ConstraintViolationException")){
        		this.getResponseObj().put("msg", EnumMensagem.MSG_004.getDescricao());
                this.setBuilder(Response.status(Response.Status.CONFLICT).entity(this.getResponseObj()));
        	} else {
        		this.getResponseObj().put("msg", e.getMessage());
                this.setBuilder(Response.status(Response.Status.BAD_REQUEST).entity(this.getResponseObj()));
        	}
        }
        
        return this.getBuilder().build();
    }
	
	@PUT
    @Path("/marcarUsuarioComoInfectado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response marcarUsuarioComoInfectado (Usuario usuario) {
        try {
        	String msg = this.service.marcarUsuarioComoInfectado(usuario);
            this.getResponseObj().put("msg", msg);
            this.setBuilder(Response.status(Response.Status.OK).entity(this.getResponseObj()));
            
        } catch (Exception e) {
            this.getResponseObj().put("msg", e.getMessage());
            this.setBuilder(Response.status(Response.Status.BAD_REQUEST).entity(this.getResponseObj()));
        }

        return this.getBuilder().build();
    }
	
}
