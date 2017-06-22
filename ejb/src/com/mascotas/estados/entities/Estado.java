package com.mascotas.estados.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mascotas.seguridad.entities.Usuario;

@Entity(name="estado")
public class Estado implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String detalle;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	private java.util.Date fecha;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getDetalle(){
		return detalle;
	}

	public void setDetalle(String detalle){
		this.detalle=detalle;
	}

	public Usuario getUsuario(){
		return usuario;
	}

	public void setUsuario(Usuario usuario){
		this.usuario=usuario;
	}

	public java.util.Date getFecha(){
		return fecha;
	}

	public void setFecha(java.util.Date fecha){
		this.fecha = fecha;
	}
}
