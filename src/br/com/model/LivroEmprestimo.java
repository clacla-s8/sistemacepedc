package br.com.model;

import java.util.List;

public class LivroEmprestimo {
	
	private Integer id;
	private Livro livro;
	private Emprestimo emprestimo;
	private int idEemprestimo;
	private int idLivro;
	
	
	private List<Livro> livros;
	

	public LivroEmprestimo() {
		
	}
	
	
	
	public LivroEmprestimo(List<Livro> livros, int idEemprestimo) {
		super();
		this.idEemprestimo = idEemprestimo;
		this.livros = livros;
	}

	

	public LivroEmprestimo(Livro livro, Emprestimo emprestimo) {
		super();
		this.livro = livro;
		this.emprestimo = emprestimo;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	


	public int getIdEemprestimo() {
		return idEemprestimo;
	}

	public void setIdEemprestimo(int idEemprestimo) {
		this.idEemprestimo = idEemprestimo;
	}
	
	

	public List<Livro> getLivros() {
		return livros;
	}



	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	
	


	public int getIdLivro() {
		return idLivro;
	}



	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}



	@Override
	public String toString() {
		return "LivroEmprestimo [id=" + id + ", livro=" + livro.getNomeLivro() + ", emprestimo=" + emprestimo.getId() + "]";
	}



	
	
	
}
