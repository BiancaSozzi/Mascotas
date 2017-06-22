package com.mascotas.mascotas;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mascotas.application.exceptions.BusinessException;
import com.mascotas.application.exceptions.ValidationError;
import com.mascotas.application.utils.StringUtils;
import com.mascotas.mascotas.dto.MascotaDTO;
import com.mascotas.mascotas.entities.Mascota;
import com.mascotas.mascotas.repository.MascotaRepository;
import com.mascotas.perfil.repository.PerfilesRepository;
import com.mascotas.provincias.repository.ProvinciasRepository;
import com.mascotas.seguridad.entities.Usuario;
import com.mascotas.seguridad.repository.UsuariosRepository;

@Stateless
@LocalBean
public class MascotaServiceValidations {
	@EJB
	private UsuariosRepository usuariosRepository;

	@EJB
	private PerfilesRepository perfilRepository;

	@EJB
	private ProvinciasRepository provinciaRepository;

	@EJB
	private MascotaRepository mascotaRepository;

	/**
	 * Valida las mascotas para un login de usuario.
	 * 
	 * @param login
	 * @return
	 */
	public void validarFindForLogin(String login) {
		if (login == null || login.isEmpty()) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "Debe definir el login de usuario."));
		}

		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "El usuario especificado no se encuentra."));
		}
	}

	/**
	 * Valida la operacion de actualizar una masctoa
	 * 
	 * @param login
	 *            Login del usuario dueño de la masctoa
	 * @param mascota
	 *            Mascota a actualizar.
	 * @return
	 */
	public void validarActualizarMascota(String login, MascotaDTO mascota) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		if (login == null || login.isEmpty()) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "Debe definir el login de usuario."));
		}
		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "El usuario especificado no se encuentra."));
		}

		if (mascota == null) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("perfil", "Debe definir el perfil a actualizar."));
		}

		if (mascota.getId() != null) {
			validarFindById(login, mascota.getId());
		}

		try {
			StringUtils.DATE_FORMAT.parse(mascota.getFechaNacimiento());
		} catch (ParseException e) {
			errors.add(new ValidationError("fechaNacimiento", "Debe definir la fecha de nacimiento."));
		}

		if (mascota.getFechaNacimiento() == null) {
			errors.add(new ValidationError("fechaNacimiento", "Debe definir la fecha de nacimiento."));
		}

		if (mascota.getNombre() == null || mascota.getNombre().length() < 3) {
			errors.add(new ValidationError("nombre", "Debe definir el nombre de la mascota."));
		}

		// TODO Auto-generated method stub
		if (errors.size() > 0) {
			throw new BusinessException("Datos de usuario invalidos.", errors);
		}
	}

	/**
	 * Valida que el usuario logueado tenga permisos para acceder a la mascota
	 * requerida
	 * 
	 * @param login
	 * @param id
	 * @return
	 */
	public void validarFindById(String login, Integer id) {
		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "Usuario no logueado."));
		}

		Mascota m = mascotaRepository.get(id);
		if (m == null || m.getUsuario() == null || !m.getUsuario().getLogin().equals(login)) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "La mascota requerida no pertenece al usuario logueado."));
		}
	}

	/**
	 * Valida que el usuario logueado tenga permisos para acceder a la mascota
	 * requerida
	 * 
	 * @param login
	 * @param id
	 * @return
	 */
	public void validarEliminarMascota(String login, Integer id) {
		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "Usuario no logueado."));
		}

		Mascota m = mascotaRepository.get(id);
		if (m == null || m.getUsuario() == null || !m.getUsuario().getLogin().equals(login)) {
			throw new BusinessException("Datos de usuario invalidos.",
					new ValidationError("login", "La mascota requerida no pertenece al usuario logueado."));
		}
	}
}
