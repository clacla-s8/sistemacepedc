package br.com.dao;

import java.util.List;

import br.com.exception.DaoException;
import br.com.model.Reserva;



public interface IDaoReserva {
	public Reserva salvar(Reserva emprestimo) throws DaoException;
	public void editar(Reserva emprestimo) throws DaoException;
	public void deletarPorId(Integer id) throws DaoException;
	public List<Reserva> getAll();

}
