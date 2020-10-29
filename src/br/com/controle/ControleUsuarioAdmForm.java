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
import br.com.model.UsuarioAdm;
import br.com.model.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControleUsuarioAdmForm implements Initializable {

	private UsuarioAdm usuarioAdm;

	private Fachada fachada;

	private List<AtualizarDadosOuvinte> atualizarDadosOuvintes = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtSenha;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorCpf;

	@FXML
	private Label labelErrorLogin;

	@FXML
	private Label labelErrorSenha;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	public void setUsuarioAdm(UsuarioAdm usuarioAdm) {
		this.usuarioAdm = usuarioAdm;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	public void inscreverAtualizarDadosOuvinte(AtualizarDadosOuvinte ouvinte) {
		atualizarDadosOuvintes.add(ouvinte);
	}

	@FXML
	public void onBtnSalvarAction(ActionEvent event) {
		if (usuarioAdm == null) {
			throw new IllegalStateException("usuarioAdm null");
		}

		if (fachada == null) {
			throw new IllegalStateException("fachada null");
		}
		try {
			usuarioAdm = getFormData();
			fachada.cadastarUsuarioAdm(usuarioAdm);
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

	private UsuarioAdm getFormData() {
		UsuarioAdm obj = new UsuarioAdm();

		if (usuarioAdm.getId() != null) {
			obj.setId(Utils.tryParseToInt(txtId.getText()));
		}

		ValidacaoException exception = new ValidacaoException("Erro de validação");

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "preencha este campo");
		}
		obj.setNome(txtNome.getText());

		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "preencha este campo");
		}
		obj.setCpf(txtCpf.getText());

		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {
			exception.addError("login", "preencha este campo");
		}
		obj.setLogin(txtLogin.getText());

		if (txtSenha.getText() == null || txtSenha.getText().trim().equals("")) {
			exception.addError("senha", "preencha este campo");
		}
		obj.setSenha(txtSenha.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet(); // set p percorrer o map

		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		} else {
			labelErrorNome.setText(errors.get(""));
		}

		if (fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		} else {
			labelErrorCpf.setText(errors.get(""));
		}

		if (fields.contains("login")) {
			labelErrorLogin.setText(errors.get("login"));
		} else {
			labelErrorLogin.setText(errors.get(""));
		}

		if (fields.contains("senha")) {
			labelErrorSenha.setText(errors.get("senha"));
		} else {
			labelErrorSenha.setText(errors.get(""));
		}

	}

	@FXML
	public void onBtnCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();

	}

	private void initializeNodes() {
		// TODO Auto-generated method stub

	}

	public void updateFormData() {
		if (usuarioAdm == null) {
			throw new IllegalStateException("usuarioAdm null");
		}
		txtId.setText(String.valueOf(usuarioAdm.getId()));
		txtNome.setText(usuarioAdm.getNome());
		txtCpf.setText(usuarioAdm.getCpf());
		txtLogin.setText(usuarioAdm.getLogin());
		txtSenha.setText(usuarioAdm.getSenha());
	}

}
