package br.com.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.exception.DaoException;

public class SqlConnection {
	private static Connection conexao = null;

	private SqlConnection() {

	}

	public static Connection getInstance() {

		try {
			if (conexao == null || conexao.isClosed()) {
				conexao = DriverManager.getConnection(SqlUtil.URL, SqlUtil.LOGIN, SqlUtil.SENHA);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexao;

	}

	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DaoException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet result) {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				throw new DaoException(e.getMessage());
			}
		}
	}

}
