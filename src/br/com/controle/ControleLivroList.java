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
import br.com.model.Livro;
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

public class ControleLivroList implements Initializable, AtualizarDadosOuvinte {

	private Fachada fachada;
	private ObservableList<Livro> obsList;

	@FXML
	private TableView<Livro> tableViewLivro;

	@FXML
	private TableColumn<Livro, Integer> tableColumnId;

	@FXML
	private TableColumn<Livro, String> tableColumnNome;

	@FXML
	private TableColumn<Livro, String> tableColumnEditora;

	@FXML
	private TableColumn<Livro, String> tableColumnAutor1;

	@FXML
	private TableColumn<Livro, String> tableColumnAutor2;
	@FXML
	private TableColumn<Livro, Integer> tableColumnQuant;

	@FXML
	private TableColumn<Livro, Livro> tableColumnEDIT;

	@FXML
	private TableColumn<Livro, Livro> tableColumnDELETE;

	@FXML
	private Button btnNovo;

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	@FXML
	public void onBtnNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Livro obj = new Livro();
		createDialogForm(obj, "/br/com/view/LivroForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeLivro"));
		tableColumnEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
		tableColumnAutor1.setCellValueFactory(new PropertyValueFactory<>("autor1"));
		tableColumnAutor2.setCellValueFactory(new PropertyValueFactory<>("autor2"));
		tableColumnQuant.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLivro.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (fachada == null) {
			throw new IllegalStateException("Fachada null (injete dependencia)");
		}

		List<Livro> list = fachada.getAllLivro();
		obsList = FXCollections.observableArrayList(list);
		tableViewLivro.setItems(obsList);
		initEditButtons();
		initDeleteButtons();
	}

	private void createDialogForm(Livro obj, String strNome, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(strNome));
			Pane pane = loader.load();

			ControleLivroForm controller = loader.getController();
			controller.setLivro(obj);
			controller.setFachada(new Fachada());
			controller.inscreverAtualizarDadosOuvinte(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do livro");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Livro, Livro>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Livro obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/view/LivroForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initDeleteButtons() {
		tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDELETE.setCellFactory(param -> new TableCell<Livro, Livro>() {
			private final Button button = new Button("deletar");

			@Override
			protected void updateItem(Livro obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> deletarLivro(obj));
			}

		});
	}

	private void deletarLivro(Livro obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmacao("Confirmação", "Tem certeza que deseja deletar ?");

		if (result.get() == ButtonType.OK) {

			try {
				fachada.deletarLivroPorId(obj);
				updateTableView();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		}
	}

}
