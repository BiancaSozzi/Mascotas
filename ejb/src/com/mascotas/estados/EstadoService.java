package com.mascotas.estados;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.mascotas.application.exceptions.BusinessException;
import com.mascotas.application.exceptions.ValidationError;
import com.mascotas.application.utils.StringUtils;
import com.mascotas.estados.dto.EstadoDTO;
import com.mascotas.estados.entities.Estado;
import com.mascotas.estados.repository.EstadoRepository;
import com.mascotas.seguridad.entities.Usuario;
import com.mascotas.seguridad.repository.UsuariosRepository;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)

public class EstadoService {
	@EJB 
	UsuariosRepository usuarioRepository;

	@EJB
	private EstadoRepository estadoRepository;


	public List<EstadoDTO> findForLogin(String login) throws BusinessException {
		List<Estado> estados = estadoRepository.getByUsuario(login);

		return EstadoDTO.Factory.get(estados);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void nuevoEstado(EstadoDTO dtoEstado) throws BusinessException{
		Estado estado = new Estado();
		estado.setDetalle(dtoEstado.getDetalle());
		Usuario usuario = usuarioRepository.get(dtoEstado.getUsuario());
		estado.setUsuario(usuario);	
		try{

			SimpleDateFormat hora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			estado.setFecha(StringUtils.DATE_FORMAT.parse(hora.format(new Date())));
		}catch (Exception e){}
		estadoRepository.add(estado);
	}

	public void eliminarEstado(Integer id){
		estadoRepository.remove(estadoRepository.get(id));
	}

}
	
