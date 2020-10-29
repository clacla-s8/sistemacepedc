package br.com.controle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.exception.BusinessException;
import br.com.exception.ValidacaoException;
import br.com.fachada.Fachada;
import br.com.interfaces.AtualizarDadosOuvinte;
import br.com.model.Alerts;
import br.com.model.Livro;
import br.com.model.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControleLivroForm implements Initializable {

	private Livro livro;

	private Fachada fachada;
	
	private List<AtualizarDadosOuvinte> atualizarDadosOuvintes = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorNome;

	@FXML
	private TextField txtEditora;

	@FXML
	private TextField txtAutor1;

	@FXML
	private TextField txtAutor2;
	
	@FXML
	private TextField txtQuant;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;
	
	

	public void onBtnSalvarAction(ActionEvent event) {

		try {
			livro = getFormData();
			fachada.salvarLivro(livro);			
			notificacaoAtualizarDadosOuvintes();
			Utils.currentStage(event).close();
		} 
		catch (ValidacaoException e) {
			setErrorMessages(e.getErrors());
		}
		catch (BusinessException e) {
			Alerts.mostrarAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

	}
	
	public void inscreverAtualizarDadosOuvinte(AtualizarDadosOuvinte ouvinte) {
		atualizarDadosOuvintes.add(ouvinte);
	}

	public void onBtnCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private void notificacaoAtualizarDadosOuvintes() {
		for(AtualizarDadosOuvinte ouvinte : atualizarDadosOuvintes ) {
			ouvinte.onDadosAtualizados();
		}
		
	}


	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}
	

	private Livro getFormData() {
		
		Livro obj = new Livro();
		
		if (livro.getId() != null) {
			obj.setId(Utils.tryParseToInt(txtId.getText()));
		}
		
		ValidacaoException exception = new ValidacaoException("Erro ao validar");
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "preencha este campo");
		}
		
		obj.setNomeLivro(txtNome.getText());
		obj.setEditora(txtEditora.getText());
		obj.setAutor1(txtAutor1.getText());
		obj.setAutor2(txtAutor2.getText());
		obj.setQuantidade(Utils.tryParseToInt(txtQuant.getText()));
		
		if(exception.getErrors().size()>0) {
			throw exception;
		}
		

		
		return obj;
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void updateFormData() {

		if (livro == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(livro.getId()));
		txtNome.setText(livro.getNomeLivro());
		txtEditora.setText(livro.getEditora());
		txtAutor1.setText(livro.getAutor1());
		txtAutor2.setText(livro.getAutor2());
		txtQuant.setText(Integer.toString(livro.getQuantidade()));
	}
	
	

}


