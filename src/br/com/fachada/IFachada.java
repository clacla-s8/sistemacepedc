package br.com.fachada;

import java.util.List;

import br.com.exception.BusinessException;
import br.com.model.Emprestimo;
import br.com.model.Livro;
import br.com.model.Reserva;
import br.com.model.UsuarioAdm;
import br.com.model.UsuarioComum;

public interface IFachada {
	
	//Livro
	public void salvarLivro(Livro livro) throws BusinessException;

	public void deletarLivroPorId(Livro livro) throws BusinessException;

	public List<Livro> getAllLivro();
	
	//UsuarioComum
	public void cadastrarUsuarioComum(UsuarioComum usuarioComum) throws BusinessException;

	public void deletarUsuarioComumPorId(UsuarioComum usuarioComum) throws BusinessException;

	public List<UsuarioComum> getAllUsuarioComum();
	
	//UsuarioAdm

	public void cadastarUsuarioAdm(UsuarioAdm usuarioAdm) throws BusinessException;

	public void deletarUsuarioAdmPorId(UsuarioAdm usuarioAdm) throws BusinessException;

	public List<UsuarioAdm> getAllUsuarioAdm();
	
	public UsuarioAdm buscarPorLogin(String login, String senha) throws BusinessException;
	
	//Emprestimo

	public void cadastrarEmprestimo(Emprestimo emprestimo) throws BusinessException;

	public void deletarEmprestimoPorId(Emprestimo emprestimo) throws BusinessException;

	public List<Emprestimo> getAllEmprestimo();
	
	//Reserva

	public void cadastrarReserva(Reserva reserva) throws BusinessException;

	public void deletarReservaPorId(Reserva reserva) throws BusinessException;

	public List<Reserva> getAllReserva();
}
