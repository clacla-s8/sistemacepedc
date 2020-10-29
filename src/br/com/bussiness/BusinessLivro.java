package br.com.bussiness;

import java.util.List;

import br.com.dao.DaoLivro;
import br.com.exception.BusinessException;
import br.com.exception.DaoException;
import br.com.model.Livro;

public class BusinessLivro implements IBusinessLivro {

	private DaoLivro daoLivro;

	public BusinessLivro() {
		this.daoLivro = new DaoLivro();
	}

	@Override
	public void salvar(Livro livro) throws BusinessException {

		try {

			if (livro.getId() == null) {

				daoLivro.salvar(livro);

			} else {

				daoLivro.editar(livro);
			}

		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}
	
	@Override
	public void deletarPorId(Livro livro) throws BusinessException {
		try {
			daoLivro.deletarPorId(livro.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Livro> getAll() {

		return daoLivro.getAll();
	}


}
