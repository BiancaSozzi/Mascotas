package com.mascotas.perfil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mascotas.application.exceptions.BusinessException;
import com.mascotas.application.exceptions.ValidationError;
import com.mascotas.perfil.dto.ActualizarPerfilDTO;
import com.mascotas.perfil.repository.PerfilesRepository;
import com.mascotas.provincias.entites.Provincia;
import com.mascotas.provincias.repository.ProvinciasRepository;
import com.mascotas.seguridad.entities.Usuario;
import com.mascotas.seguridad.repository.UsuariosRepository;

@Stateless
@LocalBean
public class PerfilServiceValidations {
	@EJB
	private UsuariosRepository usuariosRepository;

	@EJB
	private PerfilesRepository perfilRepository;

	@EJB
	private ProvinciasRepository provinciaRepository;

	public void validarFindForLogin(String login) {
		if (login == null || login.isEmpty()) {
			throw new BusinessException("Datos de perfil invalidos.",
					new ValidationError("login", "Debe definir el login de usuario."));
		}

		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de perfil invalidos.",
					new ValidationError("login", "El usuario especificado no se encuentra."));
		}
	}

	/**
	 * Valida la operacion de actualizar perfil
	 * 
	 * @param login
	 *            Login del usuario dueño del perfil
	 * @param perfil
	 *            Perfil a actualizar.
	 * @return
	 */
	public void validarActualizarPerfil(String login, ActualizarPerfilDTO perfil) {

		List<ValidationError> errors = new ArrayList<ValidationError>();

		if (login == null || login.isEmpty()) {
			throw new BusinessException("Datos de perfil invalidos.",
					new ValidationError("login", "Debe definir el login de usuario."));
		}
		Usuario usuario = usuariosRepository.get(login);
		if (usuario == null) {
			throw new BusinessException("Datos de perfil invalidos.",
					new ValidationError("login", "El usuario especificado no se encuentra."));
		}

		if (perfil == null) {
			throw new BusinessException("Datos de perfil invalidos.",
					new ValidationError("perfil", "Debe definir el perfil a actualizar."));
		}

		if (perfil.getProvincia() != null && perfil.getProvincia() > 0) {
			Provincia provincia = provinciaRepository.get(perfil.getProvincia());
			if (provincia == null) {
				throw new BusinessException("Datos de perfil invalidos.",
						new ValidationError("provincia", "La provincia proporcionada es incorrecta."));
			}
		}

		if (perfil.getNombre().isEmpty()) {
			errors.add(new ValidationError("nombre", "Tu nombre es requerido."));
		}

		if (errors.size() > 0) {
			throw new BusinessException("Datos de perfil invalidos.", errors);
		}
	}
}
