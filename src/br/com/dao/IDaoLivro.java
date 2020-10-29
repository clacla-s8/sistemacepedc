package br.com.dao;

import java.util.List;

import br.com.exception.DaoException;
import br.com.model.Livro;

public interface IDaoLivro {
	public Livro salvar(Livro livro) throws DaoException;
	public void editar(Livro livro) throws DaoException;
	public void deletarPorId(Integer id) throws DaoException;
	public List<Livro> getAll();

}
