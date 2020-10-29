package br.com.dao;

import java.util.List;

import br.com.exception.DaoException;
import br.com.model.Emprestimo;



public interface IDaoEmprestimo {
	public Emprestimo salvar(Emprestimo emprestimo) throws DaoException;
	public void editar(Emprestimo emprestimo) throws DaoException;
	public void deletarPorId(Integer id) throws DaoException;
	public List<Emprestimo> getAll();
	

}
