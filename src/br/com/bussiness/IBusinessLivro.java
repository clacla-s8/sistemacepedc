package br.com.bussiness;

import java.util.List;

import br.com.exception.BusinessException;
import br.com.model.Livro;

public interface IBusinessLivro {
	public void salvar( Livro livro) throws BusinessException;
	public void deletarPorId(Livro livro) throws BusinessException;
	public List<Livro> getAll();

}
