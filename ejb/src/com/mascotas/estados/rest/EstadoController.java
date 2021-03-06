package com.mascotas.estados.rest;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mascotas.application.dtos.FormError;
import com.mascotas.application.exceptions.BusinessException;
import com.mascotas.estados.EstadoService;
import com.mascotas.estados.dto.EstadoDTO;
import com.mascotas.seguridad.entities.Usuario;

@Stateless
@LocalBean
@Path("/estados")
public class EstadoController {
	@EJB
	EstadoService estadoService;


	@Path("/")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response buscarEstados(@Context HttpServletRequest httpRequest) throws NamingException {
		try {
			return Response.ok().entity(
					estadoService.findForLogin(httpRequest.getUserPrincipal().getName())).build();
		} catch (BusinessException e) {
			e.printStackTrace();
			return Response.status(500).entity(FormError.processError(e)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(FormError.processError(e)).build();
		}
	}


	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})

	public Response guardarEstado(EstadoDTO estado) throws NamingException{
			estadoService.nuevoEstado(estado);
			return Response.ok().build();	
	}
	

	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public Response eliminarEstado(@PathParam("id") Integer id) throws NamingException{
		try{
			estadoService.eliminarEstado(id);
			return Response.ok().build();
		}catch (BusinessException e) {
			e.printStackTrace();
			return Response.status(500).entity(FormError.processError(e)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(FormError.processError(e)).build();
		}
	}
	

}