package br.com.bussiness;

import java.util.List;

import br.com.dao.DaoReserva;
import br.com.exception.BusinessException;
import br.com.exception.DaoException;
import br.com.model.Reserva;

public class BusinessReserva implements IBusinessReserva {

	private DaoReserva daoReserva;

	public BusinessReserva() {
		this.daoReserva = new DaoReserva();
	}

	@Override
	public void salvarOuEditar(Reserva reserva) throws BusinessException {
		try {

			if (reserva.getId() == null) {

				daoReserva.salvar(reserva);

			} else {

				daoReserva.editar(reserva);
			}

		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void deletarPorId(Reserva reserva) throws BusinessException {
		try {
			daoReserva.deletarPorId(reserva.getId());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Reserva> getAll() {
		return daoReserva.getAll();

	}

}
