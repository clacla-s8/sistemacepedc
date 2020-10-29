package br.com.bussiness;

import java.util.List;

import br.com.exception.BusinessException;

import br.com.model.UsuarioComum;

public interface IBusinessUsuarioComum {
	
	public void salvar(UsuarioComum usuarioComum) throws BusinessException;

	public void deletarPorId(UsuarioComum usuarioComum) throws BusinessException;

	public UsuarioComum getPorCpf(String cpf) throws BusinessException;

	public boolean isCpf(String cpf) throws BusinessException;

	public List<UsuarioComum> getAll();

}
