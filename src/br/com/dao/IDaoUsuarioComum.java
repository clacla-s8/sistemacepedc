package br.com.dao;

import java.util.List;

import br.com.exception.DaoException;
import br.com.model.UsuarioComum;


public interface IDaoUsuarioComum {
	public UsuarioComum salvar(UsuarioComum usuarioComum) throws DaoException;
	public void editar(UsuarioComum usuarioComum) throws DaoException;
	public void deletarPorId(Integer id) throws DaoException;
	public UsuarioComum getPorCpf(String cpf) throws DaoException;
	public boolean isCpf(String cpf) throws DaoException;
	public List<UsuarioComum> getAll();
}
