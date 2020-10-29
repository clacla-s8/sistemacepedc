package br.com.bussiness;

import java.util.List;

import br.com.exception.BusinessException;

import br.com.model.UsuarioAdm;

public interface IBusinessUsuarioAdm {
	public void salvar(UsuarioAdm usuarioAdm) throws BusinessException;
	
	public void deletarPorId(UsuarioAdm usuarioAdm) throws BusinessException;

	public List<UsuarioAdm> getAll();
	
	public UsuarioAdm buscarPorLogin(String login, String senha) throws BusinessException;
}
