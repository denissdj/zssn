package br.com.gzsolucoes.repository;

import javax.ejb.Stateless;

import br.com.gzsolucoes.model.Usuario;

@Stateless
public class UsuarioRepository extends GenericRepository <Usuario,Long> {
	
}
