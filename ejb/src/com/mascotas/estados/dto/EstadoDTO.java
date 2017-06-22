package com.mascotas.estados.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.mascotas.application.utils.StringUtils;
import com.mascotas.estados.entities.Estado;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String detalle;
	private String usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetalle(){
		return detalle;
	}

	public void setDetalle(String detalle){
		this.detalle=detalle;
	}

	public String getUsuario(){
		return usuario;
	}

	public void setUsuario(String usuario){
		this.usuario = usuario;
	}


	public static class Factory {
		public static EstadoDTO get(Estado estado) {
			EstadoDTO result = new EstadoDTO();
			result.id = estado.getId();
			result.detalle = estado.getDetalle();
			result.usuario=estado.getUsuario().getLogin();
			return result;
		}

		public static List<EstadoDTO> get(Collection<Estado> estados) {
			ArrayList<EstadoDTO> result = new ArrayList<EstadoDTO>();
			for (Estado p : estados) {
				result.add(get(p));
			}
			return result;
		}

	}
}