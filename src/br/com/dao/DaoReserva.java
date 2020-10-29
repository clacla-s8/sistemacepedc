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
import br.com.model.Reserva;
import br.com.model.Livro;
import br.com.model.UsuarioComum;
import br.com.sql.SqlConnection;
import br.com.sql.SqlUtil;

public class DaoReserva implements IDaoReserva {

	private Connection conexao;
	private PreparedStatement statement;
	private ResultSet result;

	public DaoReserva() {
		this.conexao = SqlConnection.getInstance();
	}

	@Override
	public Reserva salvar(Reserva reserva) throws DaoException {

		try {
			this.statement = conexao.prepareStatement(SqlUtil.ReservaSQL.INSERT_ALL);
			this.statement.setDate(1, new java.sql.Date(reserva.getDataReserva().getTime()));
			this.statement.setDate(2, new java.sql.Date(reserva.getDataPegar().getTime()));
			this.statement.setInt(3, reserva.getUsuarioComum().getId());
			this.statement.setInt(4, reserva.getLivro().getId());
			this.statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnection.closeStatement(statement);
		}

		return reserva;
	}

	@Override
	public void editar(Reserva reserva) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.ReservaSQL.UPDATE_ALL);

			this.statement.setDate(1, new java.sql.Date(reserva.getDataReserva().getTime()));
			this.statement.setDate(2, new java.sql.Date(reserva.getDataPegar().getTime()));
			this.statement.setInt(3, reserva.getUsuarioComum().getId());
			this.statement.setInt(4, reserva.getLivro().getId());
			this.statement.setInt(5, reserva.getId());

			this.statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO EDITAR RESERVA- CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	@Override
	public void deletarPorId(Integer id) throws DaoException {
		try {
			this.statement = conexao.prepareStatement(SqlUtil.ReservaSQL.DELETE_BYID);

			this.statement.setInt(1, id);
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO DELETAR RESERVA - CONTATAR ADM");
		} finally {
			SqlConnection.closeStatement(statement);
		}

	}

	private Reserva instanciarReserva(ResultSet result, UsuarioComum usuarioComum, Livro livro)
			throws SQLException {
		Reserva res = new Reserva();
		res.setId(result.getInt("id"));
		res.setDataReserva(new java.util.Date(result.getTimestamp("DataReserva").getTime()));
		res.setDataPegar(new java.util.Date(result.getTimestamp("DataPegar").getTime()));
		res.setUsuarioComum(usuarioComum);
		res.setLivro(livro);
		return res;
	}

	private UsuarioComum instanciarUsuarioComum(ResultSet result) throws SQLException {
		UsuarioComum usuarioComum = new UsuarioComum();
		usuarioComum.setId(result.getInt("usuarioComum_id"));
		usuarioComum.setNome(result.getString("nome"));


		return usuarioComum;
	}

	private Livro instanciarLivro(ResultSet result) throws SQLException {
		Livro livro = new Livro();
		livro.setId(result.getInt("livro_id"));
		livro.setNomeLivro(result.getString("nomeLivro"));


		return livro;
	}

	@Override
	public List<Reserva> getAll() {
		List<Reserva> list = new ArrayList<>();
		try {
			this.statement = conexao.prepareStatement(SqlUtil.ReservaSQL.SELECT_ALL);
			result = statement.executeQuery();

			Map<Integer, UsuarioComum> mapUsuario = new HashMap<>();
			Map<Integer, Livro> mapLivro = new HashMap<>();

			while (result.next()) {

				UsuarioComum usuario = mapUsuario.get(result.getInt("usuarioComum_id"));
				Livro livro = mapLivro.get(result.getInt("livro_id"));

				if (usuario == null && livro == null) {
					usuario = instanciarUsuarioComum(result);
					livro = instanciarLivro(result);
					mapUsuario.put(result.getInt("usuarioComum_id"), usuario);
					mapLivro.put(result.getInt("livro_id"), livro);

				}

				Reserva res = instanciarReserva(result, usuario, livro);
				list.add(res);

				
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}finally{
			SqlConnection.closeStatement(statement);
			SqlConnection.closeResultSet(result);
		}
		return list;
	}

}
