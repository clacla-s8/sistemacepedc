package br.com.controle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import br.com.application.Main;
import br.com.fachada.Fachada;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ControleMenu implements Initializable {

	@FXML
	private MenuItem menuItemAdministrador;

	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemLivro;

	@FXML
	private MenuItem menuItemNovo;

	@FXML
	private MenuItem menuItemNova;

	@FXML
	public void onMenuItemAdministradorAction() {
		loadView("/br/com/view/UsuarioAdmList.fxml", (ControleUsuarioAdmList controller) -> {
			controller.setFachada(new Fachada());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemUsuarioAction() {
		loadView("/br/com/view/UsuarioComumList.fxml", (ControleUsuarioComumList controller) -> {
			controller.setFachada(new Fachada());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemLivroAction() {
		loadView("/br/com/view/LivroList.fxml", (ControleLivroList controller) -> {
			controller.setFachada(new Fachada());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemNovoAction() {
		loadView("/br/com/view/EmprestimoList.fxml", (ControleEmprestimoList controller) -> {
			controller.setFachada(new Fachada());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemNovaAction() {
		loadView("/br/com/view/ReservaList.fxml", (ControleReservaList controller) -> {
			controller.setFachada(new Fachada());
			controller.updateTableView();
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	private synchronized <T> void loadView(String nomeTela, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeTela));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

			T controller = loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
