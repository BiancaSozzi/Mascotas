package com.mascotas.estados.repository;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mascotas.application.repository.Repositorio;
import com.mascotas.estados.entities.Estado;


@Stateless
@LocalBean
public class EstadoRepository implements Repositorio<Integer, Estado> {
	@PersistenceContext(unitName = "MascotasDS")
	private EntityManager entityManager;
	

	@Override
	public void add(Estado newOne) {
		entityManager.persist(newOne);
	}

	@Override
	public void remove(Estado toDelete) {
		entityManager.remove(toDelete);
	}

	@Override
	public Estado get(Integer id) {
		return entityManager.find(Estado.class, id);
	}

	@Override
	public List<Estado> getAll() {
		throw new RuntimeException("No se puede acceder a todos los perfiles.");
	}

	@Override
	public long size() {
		throw new RuntimeException("No se puede acceder a todos los perfiles.");
	}

	public List<Estado> getByUsuario(String login) {
		String q = "SELECT p from " + Estado.class.getName() +
				" p JOIN p.usuario u WHERE  u.login = :login ORDER BY id DESC ";
		TypedQuery<Estado> query = entityManager.createQuery(q, Estado.class);
		query.setParameter("login", login);
		List<Estado> result =  query.getResultList();
		return result;
	}

	public List<Estado> getEstados(){
		String q = "SELECT p from " + Estado.class.getName() + " p";
		TypedQuery<Estado> query = entityManager.createQuery(q, Estado.class);
		List<Estado> result =  query.getResultList();
		return result;
	}
}
