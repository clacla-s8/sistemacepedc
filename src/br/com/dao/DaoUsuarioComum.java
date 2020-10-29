package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.exception.DaoException;

import br.com.model.UsuarioComum;
import br.com.sql.SqlConnection;
import br.com.sql.SqlUtil;

public class DaoUsuarioComum implements IDaoUsuarioComum {

	private Connection conexao;
	private PreparedStatement statement;
	private ResultSet result;

	public DaoUsuarioComum() {
		this.conexao = SqlConnection.getInstance();
	}

	@Override
	public UsuarioComum salvar(UsuarioComum usuarioComum) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioComumSQL.INSERT_ALL);
			this.statement.setString(1, usuarioComum.getNome());
			this.statement.setString(2, usuarioComum.getCpf());
			this.statement.setString(3, usuarioComum.getTelefone());
			this.statement.setString(4, usuarioComum.getEndereco());
			this.result = this.statement.executeQuery();

			if (result.next()) {
				int id = result.getInt(1);
				usuarioComum.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CADASTRAR USUARIO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
		return usuarioComum;
	}

	@Override
	public void editar(UsuarioComum usuarioComum) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioComumSQL.UPDATE_ALL);

			this.statement.setString(1, usuarioComum.getNome());
			this.statement.setString(2, usuarioComum.getCpf());
			this.statement.setString(3, usuarioComum.getTelefone());
			this.statement.setString(4, usuarioComum.getEndereco());
			statement.setInt(5, usuarioComum.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO EDITAR USUARIO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);

		}

	}

	@Override
	public void deletarPorId(Integer id) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioComumSQL.DELETE_BYID);

			statement.setInt(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO DELETAR USUARIO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);

		}

	}

	@Override
	public UsuarioComum getPorCpf(String cpf) throws DaoException {
		UsuarioComum usuarioComum = null;
		try {
			this.statement = this.conexao.prepareStatement(SqlUtil.UsuarioComumSQL.SELECT_CPF);
			this.statement.setString(1, cpf);
			this.result = this.statement.executeQuery();
			if (result.next()) {
				usuarioComum = new UsuarioComum();
				usuarioComum.setId(this.result.getInt(1));
				usuarioComum.setNome(this.result.getString(2));
				usuarioComum.setCpf(this.result.getString(3));

			} else {
				return usuarioComum;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Usuário não existe");

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}

		return usuarioComum;

	}

	@Override
	public boolean isCpf(String cpf) throws DaoException {
		try {
			this.statement = this.conexao.prepareStatement(SqlUtil.UsuarioComumSQL.SELECT_CPF);
			this.statement.setString(1, cpf);
			this.result = this.statement.executeQuery();
			return result.next();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Usuário não existe");

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
	}

	@Override
	public List<UsuarioComum> getAll() {
		List<UsuarioComum> list = new ArrayList<>();

		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioComumSQL.SELECT_ALL);
			result = statement.executeQuery();

			while (result.next()) {
				UsuarioComum usuarioComum = new UsuarioComum();
				usuarioComum.setId(result.getInt("id"));
				usuarioComum.setNome(result.getString("nome"));
				usuarioComum.setCpf(result.getString("cpf"));
				usuarioComum.setTelefone(result.getString("telefone"));
				usuarioComum.setEndereco(result.getString("endereco"));
				list.add(usuarioComum);
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
