package br.com.model;

import java.util.Date;

public class Reserva {
	private Integer id;
	private Date dataReserva;
	private Date dataPegar;
	
	private UsuarioComum usuarioComum;
	private Livro livro;
	
	public Reserva() {
		
	}
	
	public Reserva(Integer id, Date dataReserva, Date dataPegar, UsuarioComum usuarioComum, Livro livro) {
		super();
		
		this.dataReserva = dataReserva;
		this.dataPegar = dataPegar;
		this.usuarioComum = usuarioComum;
		this.livro = livro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public Date getDataPegar() {
		return dataPegar;
	}

	public void setDataPegar(Date dataPegar) {
		this.dataPegar = dataPegar;
	}

	public UsuarioComum getUsuarioComum() {
		return usuarioComum;
	}

	public void setUsuarioComum(UsuarioComum usuarioComum) {
		this.usuarioComum = usuarioComum;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	
	

	
	
}
