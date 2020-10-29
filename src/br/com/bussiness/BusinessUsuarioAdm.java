package br.com.bussiness;

import java.util.List;

import br.com.dao.DaoUsuarioAdm;
import br.com.exception.BusinessException;
import br.com.exception.DaoException;
import br.com.model.UsuarioAdm;

public class BusinessUsuarioAdm implements IBusinessUsuarioAdm {

	private DaoUsuarioAdm daoUsuarioAdm;

	public BusinessUsuarioAdm() {
		this.daoUsuarioAdm = new DaoUsuarioAdm();
	}

	@Override
	public void salvar(UsuarioAdm usuarioAdm) throws BusinessException {
		try {
			if (usuarioAdm.getId() == null) {
				daoUsuarioAdm.cadastrar(usuarioAdm);

			} else {
				daoUsuarioAdm.editar(usuarioAdm);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void deletarPorId(UsuarioAdm usuarioAdm) throws BusinessException {
		try {
			daoUsuarioAdm.deletarPorId(usuarioAdm.getId());
		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public List<UsuarioAdm> getAll() {

		return daoUsuarioAdm.getAll();
	}

	@Override
	public UsuarioAdm buscarPorLogin(String login, String senha) throws BusinessException {
		try {
			return daoUsuarioAdm.buscarPorLogin(login, senha);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

}
