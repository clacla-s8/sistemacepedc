package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.exception.DaoException;
import br.com.model.Emprestimo;
import br.com.model.Livro;
import br.com.model.LivroEmprestimo;
import br.com.model.UsuarioComum;
import br.com.sql.SqlConnection;
import br.com.sql.SqlUtil;

public class DaoEmprestimo implements IDaoEmprestimo {

	private Connection conexao;
	private PreparedStatement statement;
	private ResultSet result;

	public DaoEmprestimo() {
		this.conexao = SqlConnection.getInstance();
	}

	@Override
	public Emprestimo salvar(Emprestimo emprestimo) throws DaoException {

		try {
			this.statement = conexao.prepareStatement(SqlUtil.EmprestimoSQL.INSERT_ALL);
			this.statement.setDate(1, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
			this.statement.setDate(2, new java.sql.Date(emprestimo.getDataDevolucao().getTime()));
			this.statement.setInt(3, emprestimo.getUsuarioComum().getId());

			this.result = this.statement.executeQuery();

			this.result.next();
			emprestimo.setId(result.getInt(1));
			this.salvarEmprestimoLivro(emprestimo.getLivros(), emprestimo.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnection.closeStatement(statement);
		}

		return emprestimo;
	}

	@Override
	public void editar(Emprestimo emprestimo) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.EmprestimoSQL.UPDATE_ALL);

			this.statement.setDate(1, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
			this.statement.setDate(2, new java.sql.Date(emprestimo.getDataDevolucao().getTime()));
			this.statement.setInt(3, emprestimo.getUsuarioComum().getId());
			this.statement.setInt(4, emprestimo.getId());

			this.statement.executeUpdate();

			this.editarEmprestimoLivro(emprestimo.getLivros(), emprestimo.getId(), emprestimo);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO EDITAR EMPRESTIMO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	@Override
	public void deletarPorId(Integer id) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.EmprestimoSQL.DELETE_BYID);

			this.statement.setInt(1, id);
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO DELETAR EMPRESTIMO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	private Emprestimo instanciarEmprestimo(ResultSet result, UsuarioComum usuarioComum, LivroEmprestimo livroEmp)
			throws SQLException {
		Emprestimo emp = new Emprestimo();
		emp.setId(result.getInt("id"));
		emp.setDataDevolucao(new java.util.Date(result.getTimestamp("DataDevolucao").getTime()));
		emp.setDataEmprestimo(new java.util.Date(result.getTimestamp("DataEmprestimo").getTime()));
		emp.setUsuarioComum(usuarioComum);
		emp.setNomeLivro(result.getString("nomeLivro"));
		return emp;
	}

	private UsuarioComum instanciarUsuarioComum(ResultSet result) throws SQLException {
		UsuarioComum usuarioComum = new UsuarioComum();
		usuarioComum.setId(result.getInt("usuarioComum_id"));
		usuarioComum.setNome(result.getString("nome"));

		return usuarioComum;
	}

	private LivroEmprestimo instanciarLivroEmp(ResultSet result) throws SQLException {
		LivroEmprestimo livroEmp = new LivroEmprestimo();
		livroEmp.setId(result.getInt("id"));
		livroEmp.setIdLivro(result.getInt("livro_id"));
		livroEmp.setIdEemprestimo(result.getInt("emprestimo_id"));

		return livroEmp;
	}

	@Override
	public List<Emprestimo> getAll() {
		List<Emprestimo> list = new ArrayList<>();

		try {
			this.statement = conexao.prepareStatement(SqlUtil.EmprestimoSQL.SELECT_ALL);
			result = statement.executeQuery();

			Map<Integer, UsuarioComum> mapUsuario = new HashMap<>();
			Map<Integer, LivroEmprestimo> mapLivroEmp = new HashMap<>();

			while (result.next()) {

				UsuarioComum usuario = mapUsuario.get(result.getInt("usuarioComum_id"));
				LivroEmprestimo livroEmp = mapLivroEmp.get(result.getInt("livro_id"));

				if (usuario == null && livroEmp == null) {
					usuario = instanciarUsuarioComum(result);
					livroEmp = instanciarLivroEmp(result);
					mapUsuario.put(result.getInt("usuarioComum_id"), usuario);
					mapLivroEmp.put(result.getInt("livro_id"), livroEmp);

				}

				Emprestimo emp = instanciarEmprestimo(result, usuario, livroEmp);
				list.add(emp);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
		return list;
	}

	private void salvarEmprestimoLivro(List<Livro> livros, int idemprestimo) throws DaoException {

		try {
			for (Livro livro : livros) {
				this.statement = conexao.prepareStatement(SqlUtil.Livro_EmprestimoSQL.INSERT_ALL);
				this.statement.setInt(1, livro.getId());
				this.statement.setInt(2, idemprestimo);
				this.statement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Problema de conexão, contactar o admin");

		}
	}

	private void editarEmprestimoLivro(List<Livro> livros, int idemprestimo, Emprestimo emprestimo)
			throws DaoException {
		// (List<Livro> livros, int idemprestimo, Emprestimo emprestimo)
		// LivroEmprestimo livroEmp = new LivroEmprestimo();
		try {
			for (Livro livro : livros) {
				this.statement = conexao.prepareStatement(SqlUtil.Livro_EmprestimoSQL.UPDATE_ALL);
				this.statement.setInt(1, livro.getId());
				this.statement.setInt(2, idemprestimo);
				this.statement.setInt(3, emprestimo.getId());
				this.statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Problema de conexão, contactar o admin");

		}
	}

	private List<Livro> getLivosIdEmprestimo(int idEmprestimo) throws DaoException {

		List<Livro> livros = new ArrayList<>();
		try {
			this.statement = this.conexao.prepareStatement(SqlUtil.Livro_EmprestimoSQL.SELECT_POR_ID_LIVRO);
			this.statement.setInt(1, idEmprestimo);
			this.result = this.statement.executeQuery();
			while (result.next()) {
				Livro livro = new Livro();
				livro.setId(this.result.getInt(1));
				livros.add(livro);
			}
			return livros;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erro de Conexão");

		}

	}

}
