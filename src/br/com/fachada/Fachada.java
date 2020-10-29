package br.com.fachada;

import java.util.List;

import br.com.bussiness.BusinessEmprestimo;
import br.com.bussiness.BusinessLivro;
import br.com.bussiness.BusinessReserva;
import br.com.bussiness.BusinessUsuarioAdm;
import br.com.bussiness.BusinessUsuarioComum;
import br.com.exception.BusinessException;
import br.com.model.Emprestimo;
import br.com.model.Livro;
import br.com.model.Reserva;
import br.com.model.UsuarioAdm;
import br.com.model.UsuarioComum;

public class Fachada implements IFachada {

	private BusinessLivro businessLivro = new BusinessLivro();
	private BusinessUsuarioAdm businessUsuarioAdm = new BusinessUsuarioAdm();
	private BusinessUsuarioComum businessUsuarioComum = new BusinessUsuarioComum();
	private BusinessEmprestimo businessEmprestimo = new BusinessEmprestimo();
	private BusinessReserva businessReserva = new BusinessReserva();

	@Override
	public void salvarLivro(Livro livro) throws BusinessException {

		businessLivro.salvar(livro);
	}

	@Override
	public void deletarLivroPorId(Livro livro) throws BusinessException {
		businessLivro.deletarPorId(livro);

	}

	@Override
	public List<Livro> getAllLivro() {

		return this.businessLivro.getAll();
	}

	@Override
	public void cadastrarUsuarioComum(UsuarioComum usuarioComum) throws BusinessException {

		this.businessUsuarioComum.salvar(usuarioComum);
	}

	@Override
	public void deletarUsuarioComumPorId(UsuarioComum usuarioComum) throws BusinessException {
		businessUsuarioComum.deletarPorId(usuarioComum);

	}

	@Override
	public List<UsuarioComum> getAllUsuarioComum() {

		return businessUsuarioComum.getAll();
	}

	@Override
	public void cadastarUsuarioAdm(UsuarioAdm usuarioAdm) throws BusinessException {

		this.businessUsuarioAdm.salvar(usuarioAdm);
	}

	@Override
	public void deletarUsuarioAdmPorId(UsuarioAdm usuarioAdm) throws BusinessException {
		businessUsuarioAdm.deletarPorId(usuarioAdm);

	}

	@Override
	public List<UsuarioAdm> getAllUsuarioAdm() {
		return businessUsuarioAdm.getAll();
	}
	
	@Override
	public UsuarioAdm buscarPorLogin(String login, String senha) throws BusinessException {		
		return businessUsuarioAdm.buscarPorLogin(login, senha);
	}

	@Override
	public void cadastrarEmprestimo(Emprestimo emprestimo) throws BusinessException {
		this.businessEmprestimo.salvarOuEditar(emprestimo);
	}

	@Override
	public void deletarEmprestimoPorId(Emprestimo emprestimo) throws BusinessException {
		businessEmprestimo.deletarPorId(emprestimo);

	}

	@Override
	public List<Emprestimo> getAllEmprestimo() {

		return businessEmprestimo.getAll();
	}

	@Override
	public void cadastrarReserva(Reserva reserva) throws BusinessException {
		businessReserva.salvarOuEditar(reserva);

	}

	@Override
	public void deletarReservaPorId(Reserva reserva) throws BusinessException {
		businessReserva.deletarPorId(reserva);

	}

	@Override
	public List<Reserva> getAllReserva() {
		
		return businessReserva.getAll();
	}

	

}
