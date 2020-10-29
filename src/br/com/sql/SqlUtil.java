package br.com.sql;

public class SqlUtil {

	public final static String URL = "jdbc:postgresql://localhost:5432/sistemacepedc";
	public final static String LOGIN = "postgres";
	public final static String SENHA = "1234";
	public final static String COL_ID = "id";

	public static class LivroSQL {
		public final static String NAME_TABLE = "livro";
		public final static String COL_NOMELIVRO = "nomeLivro";
		public final static String COL_EDITORA = "editora";
		public final static String COL_AUTOR1 = "autor1";
		public final static String COL_AUTOR2 = "autor2";
		public final static String COL_QUANTIDADE = "quantidade";
		public final static String COL_isEMPRESTADO = "isEmprestado";
		

		public final static String INSERT_ALL = "" + "INSERT INTO " + NAME_TABLE + "(" + COL_NOMELIVRO + ","
				+ COL_EDITORA + "," + COL_AUTOR1 + "," + COL_AUTOR2 + "," + COL_QUANTIDADE+ "," + COL_isEMPRESTADO +")" + "VALUES(?,?,?,?,?,?)  RETURNING id";

		public final static String SELECT_ALL = "" + "SELECT * FROM " + NAME_TABLE + " ORDER BY nomeLivro";

		public static final String UPDATE_ALL = "UPDATE LIVRO SET NOMELIVRO = ?, EDITORA= ?, AUTOR1 = ?, AUTOR2 = ? WHERE ID=?";

		public static final String DELETE_BYID = "DELETE FROM LIVRO WHERE ID = ? ";
	}

	public static class UsuarioComumSQL {
		public final static String NAME_TABLE = "usuarioComum";
		public final static String COL_NOME = "nome";
		public final static String COL_CPF = "cpf";
		public final static String COL_TELEFONE = "telefone";
		public final static String COL_ENDERECO = "endereco";

		public final static String INSERT_ALL = "" + "INSERT INTO " + NAME_TABLE + "(" + COL_NOME + "," + COL_CPF + ","
				+ COL_TELEFONE + "," + COL_ENDERECO + ")" + "VALUES(?,?,?,?)  RETURNING id";

		public final static String SELECT_CPF = "" + "SELECT * FROM " + NAME_TABLE + " userC WHERE userC.cpf = ?";

		public final static String SELECT_ALL = "" + "SELECT * FROM " + NAME_TABLE + " ORDER BY nome";

		public static final String UPDATE_ALL = "UPDATE usuarioComum SET nome = ?, cpf= ?, telefone = ?, endereco = ? WHERE ID=?";

		public static final String DELETE_BYID = "DELETE FROM usuarioComum WHERE ID = ? ";

	}

	public static class UsuarioAdmSQL {
		public final static String NAME_TABLE = "usuarioAdm";
		public final static String COL_NOME = "nome";
		public final static String COL_CPF = "cpf";
		public final static String COL_LOGIN = "login";
		public final static String COL_SENHA = "senha";

		public final static String INSERT_ALL = "" + "INSERT INTO " + NAME_TABLE + "(" + COL_NOME + "," + COL_CPF + ","
				+ COL_LOGIN + "," + COL_SENHA + ")" + "VALUES(?,?,?,?)  RETURNING id";

		public final static String SELECT_ALL = "" + "SELECT * FROM " + NAME_TABLE + " ORDER BY nome";
		
		public static final String SELECT_LOGIN_SENHA = "SELECT * FROM " + NAME_TABLE + " WHERE login = ? AND senha = ?";

		public static final String UPDATE_ALL = "UPDATE usuarioAdm SET nome = ?, cpf= ?, login = ?, senha = ? WHERE ID=?";

		public static final String DELETE_BYID = "DELETE FROM usuarioAdm WHERE ID = ? ";

	}

	public static class EmprestimoSQL {
		public final static String NAME_TABLE = "emprestimo";
		public final static String COL_DATAEMPRESTIMO = "dataEmprestimo";
		public final static String COL_DATADEVOLUCAO = "dataDevolucao";
		public final static String COL_USUARIOCOMUM_ID = "usuarioComum_id";
		

		public final static String INSERT_ALL = "" + "INSERT INTO " + NAME_TABLE + "(" + COL_DATAEMPRESTIMO + ","
				+ COL_DATADEVOLUCAO + "," + COL_USUARIOCOMUM_ID + ")"+ "VALUES(?,?,?) RETURNING id";

		public final static String SELECT_ALL = "select e.*, uc.nome, le.livro_id , le.emprestimo_id , l.nomeLivro "+
				"from emprestimo e "+
				"inner join usuarioComum uc "+
				"on e.usuarioComum_id = uc.id "+
				"inner join livro_emprestimo le "+
				"on e.id = le.emprestimo_id " +
				"inner join livro l " +
				"on le.livro_id = l.id";
				
		
		public final static String SELECT_POR_ID_LIVRO = ""
				+ "SELECT * FROM "+ NAME_TABLE;
		

		public static final String UPDATE_ALL = "UPDATE emprestimo SET dataEmprestimo = ?, dataDevolucao = ?, usuarioComum_id = ? where id = ?";

		public static final String DELETE_BYID = "DELETE FROM emprestimo WHERE ID = ? ";

	}
	
	public static class Livro_EmprestimoSQL{
		public final static String NAME_TABLE = "livro_emprestimo";
		public final static String COL_LIVRO_ID = "livro_id";
		public final static String COL_EMPRESTIMO_ID = "emprestimo_id";
		
		public final static String INSERT_ALL ="" + "INSERT INTO " + NAME_TABLE + "(" + COL_LIVRO_ID + ","
				+ COL_EMPRESTIMO_ID + ")" + "VALUES(?,?) RETURNING id";
		
		public final static String SELECT_POR_ID_LIVRO = ""
				+ "SELECT * FROM "+ NAME_TABLE+
				" l WHERE l. "+COL_LIVRO_ID+" = ?";
		
		public static final String UPDATE_ALL = "UPDATE livro_emprestimo SET livro_id = ?, emprestimo_id = ? where id = ?";
	}
	

	public static class ReservaSQL {

		public final static String NAME_TABLE = "reserva";
		public final static String COL_DATARESERVA = "dataReserva";
		public final static String COL_DATAPEGAR = "dataPegar";	
		public final static String COL_USUARIOCOMUM_ID = "usuarioComum_id";
		public final static String COL_LIVRO_ID = "livro_id";

		public final static String INSERT_ALL = "" + "INSERT INTO " + NAME_TABLE + "(" + COL_DATARESERVA + ","
				+ COL_DATAPEGAR + "," + COL_USUARIOCOMUM_ID + "," + COL_LIVRO_ID + ")"
				+ "VALUES(?,?,?,?) RETURNING id";
		
		public final static String SELECT_ALL = "SELECT r.*, uc.nome, l.nomeLivro " +
				"FROM reserva r " +
				"INNER JOIN usuarioComum uc " +
				"ON r.usuarioComum_id = uc.id " +
				"INNER JOIN livro l " +
				"ON r.livro_id = l.id ";

		public static final String UPDATE_ALL = "UPDATE reserva SET dataReserva = ?, dataPegar= ?, usuarioComum_id = ?, livro_id = ?  WHERE id=?";

		public static final String DELETE_BYID = "DELETE FROM reserva WHERE ID = ? ";

	}

}
