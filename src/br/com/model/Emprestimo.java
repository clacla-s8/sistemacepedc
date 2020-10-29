package br.com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprestimo {

	private Integer id;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	private UsuarioComum usuarioComum;
	private Livro livro;
	
	private String nomeLivro;

	
	private List<LivroEmprestimo> livroEmp;
	private List<Livro> livros;
	
	
	public Emprestimo() {
		this.livros = new ArrayList<>();
	}

	public Emprestimo(Date dataEmprestimo, Date dataDevolucao, UsuarioComum usuarioComum, LivroEmprestimo livroEmp) {
		super();
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
		this.usuarioComum = usuarioComum;
		this.livros = new ArrayList<>();
		this.livroEmp = new ArrayList<>();
		
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public UsuarioComum getUsuarioComum() {
		return usuarioComum;
	}

	public void setUsuarioComum(UsuarioComum usuarioComum) {
		this.usuarioComum = usuarioComum;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<LivroEmprestimo> getLivroEmp() {
		return livroEmp;
	}

	public void setLivroEmp(List<LivroEmprestimo> livroEmp) {
		this.livroEmp = livroEmp;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}
	
	
	
}
