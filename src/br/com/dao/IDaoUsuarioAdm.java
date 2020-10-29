package br.com.dao;

import java.util.List;

import br.com.exception.DaoException;
import br.com.model.UsuarioAdm;



public interface IDaoUsuarioAdm {
	
	public UsuarioAdm cadastrar(UsuarioAdm usuarioAdm) throws DaoException;
	public void editar(UsuarioAdm usuarioAdm) throws DaoException;
	public void deletarPorId(Integer id) throws DaoException;
	public List<UsuarioAdm> getAll();
	public UsuarioAdm buscarPorLogin(String login,String senha)throws DaoException;
}
