package br.com.bussiness;

import java.util.List;

import br.com.dao.DaoEmprestimo;
import br.com.exception.BusinessException;
import br.com.exception.DaoException;
import br.com.model.Emprestimo;

public class BusinessEmprestimo implements IBusinessEmprestimo {

	private DaoEmprestimo daoEmprestimo;

	public BusinessEmprestimo() {
		this.daoEmprestimo = new DaoEmprestimo();
	}

	@Override
	public void salvarOuEditar(Emprestimo emprestimo) throws BusinessException {
		try {

			if (emprestimo.getId() == null) {

				daoEmprestimo.salvar(emprestimo);

			} else {

				daoEmprestimo.editar(emprestimo);
			}

		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void deletarPorId(Emprestimo emprestimo) throws BusinessException {
		try {
			daoEmprestimo.deletarPorId(emprestimo.getId());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Emprestimo> getAll() {
		return daoEmprestimo.getAll();

	}

}
