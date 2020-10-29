package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.exception.DaoException;
import br.com.model.Livro;
import br.com.sql.SqlConnection;
import br.com.sql.SqlUtil;

public class DaoLivro implements IDaoLivro {

	private Connection conexao;
	private PreparedStatement statement;
	private ResultSet result;

	public DaoLivro() {
		this.conexao = SqlConnection.getInstance();
	}

	@Override
	public Livro salvar(Livro livro) throws DaoException {

		try {
			this.statement = conexao.prepareStatement(SqlUtil.LivroSQL.INSERT_ALL);
			this.statement.setString(1, livro.getNomeLivro());
			this.statement.setString(2, livro.getEditora());
			this.statement.setString(3, livro.getAutor1());
			this.statement.setString(4, livro.getAutor2());
			this.statement.setInt(5, livro.getQuantidade());
			this.statement.setBoolean(6, livro.isEmprestado());
			this.result = this.statement.executeQuery();

			if (result.next()) {
				int id = result.getInt(1);
				livro.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SALVAR LIVRO - CONTATAR ADM");

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}

		return livro;
	}

	@Override
	public void editar(Livro livro) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.LivroSQL.UPDATE_ALL);

			statement.setString(1, livro.getNomeLivro());
			statement.setString(2, livro.getEditora());
			statement.setString(3, livro.getAutor1());
			statement.setString(4, livro.getAutor2());
			statement.setInt(5, livro.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO EDITAR LIVRO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	@Override
	public void deletarPorId(Integer id) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.LivroSQL.DELETE_BYID);

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO DELETAR LIVRO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	@Override
	public List<Livro> getAll() {

		List<Livro> list = new ArrayList<>();

		try {
			this.statement = conexao.prepareStatement(SqlUtil.LivroSQL.SELECT_ALL);
			result = statement.executeQuery();

			while (result.next()) {
				Livro livro = new Livro();
				livro.setId(result.getInt("id"));
				livro.setNomeLivro(result.getString("nomeLivro"));
				livro.setEditora(result.getString("editora"));
				livro.setAutor1(result.getString("autor1"));
				livro.setAutor2(result.getString("autor2"));
				livro.setQuantidade(result.getInt("quantidade"));
				livro.setEmprestado(result.getBoolean("isEmprestado"));
				list.add(livro);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
		return list;
	}

}
