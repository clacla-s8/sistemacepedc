package br.com.bussiness;

import java.util.List;

import br.com.exception.BusinessException;
import br.com.model.Reserva;

public interface IBusinessReserva {
	public void salvarOuEditar(Reserva emprestimo) throws BusinessException;

	public void deletarPorId(Reserva emprestimo) throws BusinessException;

	public List<Reserva> getAll();
}
