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
import br.com.model.UsuarioComum;
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

public class ControleUsuarioComumList implements Initializable, AtualizarDadosOuvinte {

	private Fachada fachada;

	private ObservableList<UsuarioComum> obsList;

	@FXML
	private TableView<UsuarioComum> tableViewUsuarioComum;

	@FXML
	private TableColumn<UsuarioComum, Integer> tableColumnId;

	@FXML
	private TableColumn<UsuarioComum, String> tableColumnNome;

	@FXML
	private TableColumn<UsuarioComum, String> tableColumnCpf;

	@FXML
	private TableColumn<UsuarioComum, String> tableColumnTelefone;

	@FXML
	private TableColumn<UsuarioComum, String> tableColumnEndereco;

	@FXML
	private TableColumn<UsuarioComum, UsuarioComum> tableColumnEDIT;

	@FXML
	private TableColumn<UsuarioComum, UsuarioComum> tableColumnDELETE;

	@FXML
	private Button btnNovo;

	@FXML
	public void onBtnNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		UsuarioComum obj = new UsuarioComum();
		createDialogForm(obj, "/br/com/view/UsuarioComumForm.fxml", parentStage);
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
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUsuarioComum.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (fachada == null) {
			throw new IllegalStateException("Fachada null (injete dependencia)");
		}

		List<UsuarioComum> list = fachada.getAllUsuarioComum();
		obsList = FXCollections.observableArrayList(list);
		tableViewUsuarioComum.setItems(obsList);
		initEditButtons();
		initDeleteButtons();

	}

	private void createDialogForm(UsuarioComum obj, String string, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(string));
			Pane pane = loader.load();

			ControleUsuarioComumForm controller = loader.getController();
			controller.setUsuarioComum(obj);
			controller.setFachada(new Fachada());
			controller.inscreverAtualizarDadosOuvinte(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do usuário");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.mostrarAlert("IO EXCEPCTION", "Erro ao carregar", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}

	}

	@Override
	public void onDadosAtualizados() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<UsuarioComum, UsuarioComum>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(UsuarioComum obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/br/com/view/UsuarioComumForm.fxml",
						Utils.currentStage(event)));
			}
		});

	}

	private void initDeleteButtons() {
		tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDELETE.setCellFactory(param -> new TableCell<UsuarioComum, UsuarioComum>() {
			private final Button button = new Button("deletar");

			@Override
			protected void updateItem(UsuarioComum obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> deletarUsuarioComum(obj));
			}

		});
	}

	private void deletarUsuarioComum(UsuarioComum obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmacao("Confirmação", "Tem certeza que deseja deletar ?");

		if (result.get() == ButtonType.OK) {

			try {
				fachada.deletarUsuarioComumPorId(obj);
				updateTableView();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		}
	}

}
