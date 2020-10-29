package br.com.model;

public class Livro {
	
	private Integer id;
	private String nomeLivro;
	private String editora;	
	private String autor1;
	private String autor2;	
	private int quantidade;
	private boolean isEmprestado;

	
	public Livro() {
		
	}	
	
	public Livro(String nomeLivro, String editora, String autor1, String autor2, int quantidade, boolean isEmprestado) {
		super();
		this.nomeLivro = nomeLivro;
		this.editora = editora;
		this.autor1 = autor1;
		this.autor2 = autor2;
		this.quantidade = quantidade;
		this.isEmprestado = isEmprestado;
	}



	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeLivro() {
		return nomeLivro;
	}
	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public String getAutor1() {
		return autor1;
	}
	public void setAutor1(String autor1) {
		this.autor1 = autor1;
	}
	public String getAutor2() {
		return autor2;
	}
	public void setAutor2(String autor2) {
		this.autor2 = autor2;
	}	
	
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isEmprestado() {
		return isEmprestado;
	}

	public void setEmprestado(boolean isEmprestado) {
		this.isEmprestado = isEmprestado;
	}

	@Override
	public String toString() {
		return nomeLivro + "  [" + id + "]";
	}
	

}
