package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.exception.DaoException;
import br.com.model.UsuarioAdm;

import br.com.sql.SqlConnection;
import br.com.sql.SqlUtil;

public class DaoUsuarioAdm implements IDaoUsuarioAdm {

	private Connection conexao;
	private PreparedStatement statement;
	private ResultSet result;

	public DaoUsuarioAdm() {
		this.conexao = SqlConnection.getInstance();
	}

	@Override
	public UsuarioAdm cadastrar(UsuarioAdm usuarioAdm) throws DaoException {

		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioAdmSQL.INSERT_ALL);
			this.statement.setString(1, usuarioAdm.getNome());
			this.statement.setString(2, usuarioAdm.getCpf());
			this.statement.setString(3, usuarioAdm.getLogin());
			this.statement.setString(4, usuarioAdm.getSenha());
			this.result = this.statement.executeQuery();

			if (result.next()) {
				int id = result.getInt(1);
				usuarioAdm.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CADASTRAR USUARIO - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}

		return usuarioAdm;
	}

	@Override
	public void editar(UsuarioAdm usuarioAdm) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioAdmSQL.UPDATE_ALL);

			this.statement.setString(1, usuarioAdm.getNome());
			this.statement.setString(2, usuarioAdm.getCpf());
			this.statement.setString(3, usuarioAdm.getLogin());
			this.statement.setString(4, usuarioAdm.getSenha());
			statement.setInt(5, usuarioAdm.getId());

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
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioAdmSQL.DELETE_BYID);

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
	public List<UsuarioAdm> getAll() {
		List<UsuarioAdm> list = new ArrayList<>();

		try {
			this.statement = conexao.prepareStatement(SqlUtil.UsuarioAdmSQL.SELECT_ALL);
			result = statement.executeQuery();

			while (result.next()) {
				UsuarioAdm usuarioAdm = new UsuarioAdm();
				usuarioAdm.setId(result.getInt("id"));
				usuarioAdm.setNome(result.getString("nome"));
				usuarioAdm.setCpf(result.getString("cpf"));
				usuarioAdm.setLogin(result.getString("login"));
				list.add(usuarioAdm);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
		return list;
	}

	@Override
	public UsuarioAdm buscarPorLogin(String login, String senha) throws DaoException {
		
		try {
            
            this.statement = conexao.prepareStatement(SqlUtil.UsuarioAdmSQL.SELECT_LOGIN_SENHA);
            statement.setString(1,login);
            statement.setString(2,senha);
            result = statement.executeQuery();
            
            UsuarioAdm usuarioAdm = null;
            if(result.next()) {
            	usuarioAdm = new UsuarioAdm();
            	usuarioAdm.setId(result.getInt("id")); 
            	usuarioAdm.setNome(result.getString("nome"));
				usuarioAdm.setCpf(result.getString("cpf"));
				usuarioAdm.setLogin(result.getString("login"));
            	
            }else {
            	throw new DaoException("Não existe usuarios com esses dados");
            }
            
            return usuarioAdm;	
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("PROBLEMA AO BUSCAR USUARIO - CONTATE O ADM");
        } finally {
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
	}
	
	

}
