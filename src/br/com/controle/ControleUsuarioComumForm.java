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
import br.com.model.UsuarioComum;
import br.com.model.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControleUsuarioComumForm implements Initializable {

	private UsuarioComum usuarioComum;

	private Fachada fachada;

	private List<AtualizarDadosOuvinte> atualizarDadosOuvintes = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private Label labelErrorNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private Label labelErrorCpf;

	@FXML
	private TextField txtTelefone;

	@FXML
	private Label labelErrorTelefone;

	@FXML
	private TextField txtEndereco;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	public void setUsuarioComum(UsuarioComum usuarioComum) {
		this.usuarioComum = usuarioComum;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	public void inscreverAtualizarDadosOuvinte(AtualizarDadosOuvinte ouvinte) {
		atualizarDadosOuvintes.add(ouvinte);
	}

	public void onBtnSalvarAction(ActionEvent event) {
		// Se a dependencia nao foi injetada, lança a exceção para avisar
		if (usuarioComum == null) {
			throw new IllegalStateException("usuarioComum null");
		}

		if (fachada == null) {
			throw new IllegalStateException("fachada null");
		}

		try {
			usuarioComum = getFormData();
			fachada.cadastrarUsuarioComum(usuarioComum);
			notificacaoAtualizarDadosOuvintes();
			Utils.currentStage(event).close();
		} catch (ValidacaoException e) {
			setErrorMessages(e.getErrors());
		} catch (BusinessException e) {
			Alerts.mostrarAlert("Erro ao salvar usuario", null, e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

	}

	private void notificacaoAtualizarDadosOuvintes() {
		for (AtualizarDadosOuvinte ouvinte : atualizarDadosOuvintes) {
			ouvinte.onDadosAtualizados();
		}

	}

	private UsuarioComum getFormData() {
		UsuarioComum obj = new UsuarioComum();

		ValidacaoException exception = new ValidacaoException("Erro de validação");

		if (usuarioComum.getId() != null) {
			obj.setId(Utils.tryParseToInt(txtId.getText()));

		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "preencha este campo");
		}
		obj.setNome(txtNome.getText());

		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "preencha este campo");
		}

//		if (txtCpf.getText() == usuarioComum.getCpf()) {
//			exception.addError("cpf", "cpf já existe");
//		}
		obj.setCpf(txtCpf.getText());

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "preencha este campo");
		}
		obj.setTelefone(txtTelefone.getText());

		obj.setEndereco(txtEndereco.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet(); // set p percorrer o map

		labelErrorNome.setText((fields.contains("nome") ? errors.get("nome") : ""));

		labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
		
		//labelErrorCpf.setText((fields.contains(usuarioComum.getCpf()) ? errors.get("cpf") : ""));

		labelErrorTelefone.setText((fields.contains("telefone") ? errors.get("telefone") : ""));
		
		

	}

	public void onBtnCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void updateFormData() {

		if (usuarioComum == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(usuarioComum.getId()));
		txtNome.setText(usuarioComum.getNome());
		txtCpf.setText(usuarioComum.getCpf());
		txtTelefone.setText(usuarioComum.getTelefone());
		txtEndereco.setText(usuarioComum.getEndereco());

	}

}
