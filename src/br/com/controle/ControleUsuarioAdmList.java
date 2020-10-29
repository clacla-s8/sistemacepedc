package br.com.controle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.application.Main;
import br.com.exception.BusinessException;
import br.com.fachada.Fachada;
import br.com.interfaces.AtualizarDadosOuvinte;
import br.com.model.Alerts;
import br.com.model.UsuarioAdm;
import br.com.model.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleUsuarioAdmList implements Initializable, AtualizarDadosOuvinte {

	private Fachada fachada;

	private ObservableList<UsuarioAdm> obsList;

	@FXML
	private TableView<UsuarioAdm> tableViewUsuarioAdm;

	@FXML
	private TableColumn<UsuarioAdm, Integer> tableColumnId;

	@FXML
	private TableColumn<UsuarioAdm, String> tableColumnNome;

	@FXML
	private TableColumn<UsuarioAdm, String> tableColumnCpf;

	@FXML
	private TableColumn<UsuarioAdm, String> tableColumnLogin;

	@FXML
	private TableColumn<UsuarioAdm, UsuarioAdm> tableColumnEDIT;

	@FXML
	private TableColumn<UsuarioAdm, UsuarioAdm> tableColumnDELETE;

	@FXML
	private Button btnNovo;

	@FXML
	public void ondBtnNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		UsuarioAdm obj = new UsuarioAdm();
		createDialogForm(obj, "/br/com/view/UsuarioAdmForm.fxml", parentStage);
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUsuarioAdm.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (fachada == null) {
			throw new IllegalStateException("fachada null");
		}

		List<UsuarioAdm> list = fachada.getAllUsuarioAdm();
		obsList = FXCollections.observableArrayList(list);
		tableViewUsuarioAdm.setItems(obsList);
		initEditButtons();
		initDeleteButtons();
	}

	public void createDialogForm(UsuarioAdm obj, String string, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(string));
			Pane pane = loader.load();

			ControleUsuarioAdmForm controller = loader.getController();
			controller.setUsuarioAdm(obj);
			controller.setFachada(new Fachada());
			controller.inscreverAtualizarDadosOuvinte(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do Usuario");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.mostrarAlert("IO EXCEPCTION", "Erro ao carregar", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDadosAtualizados() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<UsuarioAdm, UsuarioAdm>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(UsuarioAdm obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/view/UsuarioAdmForm.fxml", Utils.currentStage(event)));
			}
		});

	}

	private void initDeleteButtons() {
		tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDELETE.setCellFactory(param -> new TableCell<UsuarioAdm, UsuarioAdm>() {
			private final Button button = new Button("deletar");

			@Override
			protected void updateItem(UsuarioAdm obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> deletarUsuarioAdm(obj));
			}

		});
	}

	private void deletarUsuarioAdm(UsuarioAdm obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmacao("Confirmação", "Tem certeza que deseja deletar ?");

		if (result.get() == ButtonType.OK) {

			try {
				fachada.deletarUsuarioAdmPorId(obj);
				updateTableView();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		}
	}

}
