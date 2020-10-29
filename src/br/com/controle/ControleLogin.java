package br.com.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.application.Main;
import br.com.exception.BusinessException;
import br.com.fachada.Fachada;
import br.com.model.Alerts;
import br.com.model.UsuarioAdm;
import br.com.model.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControleLogin implements Initializable {

	private Fachada fachada;

	private UsuarioAdm usuarioAdm;
	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtSenha;

	@FXML
	private Button btnEntrar;

	@FXML
	private Button btnSair;

	@FXML
	public void onBtnEntrarAction(ActionEvent event) {
		
		if(txtLogin.getText().equals("") || txtSenha.getText().equals("")) {
			Alerts.mostrarAlert("Campo vazio", null,"Digite os dados para realizar o login", AlertType.INFORMATION);
		} else if(efetuarLogin()) {
			Main.changeStage("Menu");
		}
	
	}

	@FXML
	public void onBtnSairAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public boolean efetuarLogin() {
		
		fachada = new Fachada();

		try {

			usuarioAdm = fachada.buscarPorLogin(txtLogin.getText(), txtSenha.getText());

			if (usuarioAdm == null) {
				return false;
			}				

			return true;

		} catch (BusinessException e) {			
			e.printStackTrace();
			Alerts.mostrarAlert("Erro ao logar", null, "Usuario não EXISTE! Tente rever os dados: E-mail/Senha",
					AlertType.ERROR);

			return false;
		}

	}
	
	

}
	

