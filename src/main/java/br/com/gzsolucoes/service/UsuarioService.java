package br.com.gzsolucoes.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import br.com.gzsolucoes.enuns.EnumSexo;
import br.com.gzsolucoes.enuns.EnumStatusUsuario;
import br.com.gzsolucoes.model.Usuario;
import br.com.gzsolucoes.repository.UsuarioRepository;

public class UsuarioService {
	
	@EJB
	private UsuarioRepository repository;
	
	@Inject
	private Logger log;
	
	@Inject
	private Validator validator;

	public void salvar(Usuario usuario) {
		if(usuario.getSiglaSexo() != null){
			usuario.setSexo(EnumSexo.MASCULINO);
			if(usuario.getSiglaSexo().equals("F")){
				usuario.setSexo(EnumSexo.FEMININO);
			}
		}
		validarUsuario(usuario);
		repository.salvar(usuario);
		log.info("As iInformacoes do usuario '" + usuario.getNome() + "' foram salvas.");
	}
	
	private void validarUsuario(Usuario usuario) throws ConstraintViolationException, ValidationException {
		
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		
	}

	public void remover(Long id) throws ConstraintViolationException, PersistenceException {
		try {
			repository.remover(repository.obterPorId(id));
		} catch (EJBTransactionRolledbackException e) {
			throw new PersistenceException("Não é possível remover o Usuário.");
		}
	}

	public Usuario obterPorId(Long id) {
		Usuario usuario = repository.obterPorId(id);
		return usuario;
	}

	public List<Usuario> listar() {
		log.info("Listando os Usuarios cadastrados");
		return repository.listar();
	}

	public String marcarUsuarioComoInfectado(Usuario usuario) throws Exception {
		String retorno = "";
		usuario.setStatusUsuario(EnumStatusUsuario.INFECTADO);
		retorno = "O usuário \"" + usuario.getNome().toUpperCase() + "\" foi marcado como INFECTADO com sucesso.";
		this.salvar(usuario);
		return retorno;
	}
	
}
