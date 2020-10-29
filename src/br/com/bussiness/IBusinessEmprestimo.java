package br.com.bussiness;

import java.util.List;

import br.com.exception.BusinessException;
import br.com.model.Emprestimo;

public interface IBusinessEmprestimo {
	public void salvarOuEditar(Emprestimo emprestimo) throws BusinessException;

	public void deletarPorId(Emprestimo emprestimo) throws BusinessException;

	public List<Emprestimo> getAll();
}
