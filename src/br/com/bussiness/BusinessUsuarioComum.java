package br.com.bussiness;

import java.util.List;

import br.com.dao.DaoUsuarioComum;
import br.com.exception.BusinessException;
import br.com.exception.DaoException;
import br.com.model.UsuarioComum;

public class BusinessUsuarioComum implements IBusinessUsuarioComum {

	private DaoUsuarioComum daoUsuarioComum;

	public BusinessUsuarioComum() {
		this.daoUsuarioComum = new DaoUsuarioComum();
	}

	@Override
	public void salvar(UsuarioComum usuarioComum) throws BusinessException {

		try {
			if (usuarioComum.getId() == null && !isCpf(usuarioComum.getCpf())) {

				daoUsuarioComum.salvar(usuarioComum);

			} else {
				daoUsuarioComum.editar(usuarioComum);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void deletarPorId(UsuarioComum usuarioComum) throws BusinessException {
		try {
			daoUsuarioComum.deletarPorId(usuarioComum.getId());
		} catch (DaoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<UsuarioComum> getAll() {
		
		return daoUsuarioComum.getAll();
	}

	@Override
	public UsuarioComum getPorCpf(String cpf) throws BusinessException {
		try {
			return this.daoUsuarioComum.getPorCpf(cpf);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public boolean isCpf(String cpf) throws BusinessException {
		try {
			return this.daoUsuarioComum.isCpf(cpf);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

}
