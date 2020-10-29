package br.com.controle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.application.Main;
import br.com.exception.BusinessException;
import br.com.fachada.Fachada;
import br.com.interfaces.AtualizarDadosOuvinte;
import br.com.model.Alerts;
import br.com.model.Reserva;
import br.com.model.Livro;
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

public class ControleReservaList implements Initializable, AtualizarDadosOuvinte {

	private Fachada fachada;
	private ObservableList<Reserva> obsList;
	
	@FXML
	private TableView<Reserva> tableViewReserva;

	@FXML
	private TableColumn<Reserva, Integer> tableColumnId;

	@FXML
	private TableColumn<Reserva, Date> tableColumnDataReserva;

	@FXML
	private TableColumn<Reserva, Date> tableColumnDataPegar;

	@FXML
	private TableColumn<UsuarioComum, UsuarioComum> tableColumnUsuario;

	@FXML
	private TableColumn<Livro, Livro> tableColumnLivro;

	@FXML
	private TableColumn<Reserva, Reserva> tableColumnEDIT;

	@FXML
	private TableColumn<Reserva, Reserva> tableColumnDELETE;

	@FXML
	private Button btnNovo;

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	@FXML
	public void onBtnNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Reserva obj = new Reserva();
		createDialogForm(obj, "/br/com/view/ReservaForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDataReserva.setCellValueFactory(new PropertyValueFactory<>("dataReserva"));
		Utils.formatTableColumnDate(tableColumnDataReserva, "dd/MM/yyyy");
		tableColumnDataPegar.setCellValueFactory(new PropertyValueFactory<>("dataPegar"));
		Utils.formatTableColumnDate(tableColumnDataPegar, "dd/MM/yyyy");
		tableColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuarioComum"));
		tableColumnLivro.setCellValueFactory(new PropertyValueFactory<>("livro"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewReserva.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (fachada == null) {
			throw new IllegalStateException("Fachada null (injete dependencia)");
		}
		
		List<Reserva> list = fachada.getAllReserva();			
		obsList = FXCollections.observableArrayList(list);
		tableViewReserva.setItems(obsList);
		initEditButtons();
		initDeleteButtons();
	}

	private void createDialogForm(Reserva obj, String strNome, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(strNome));
			Pane pane = loader.load();

			ControleReservaForm controller = loader.getController();
			controller.setReserva(obj);
			controller.setFachada(new Fachada());
			controller.loadObjetosAssociados();
			controller.inscreverAtualizarDadosOuvinte(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do Reserva");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.mostrarAlert("IO EXCEPCTION", "Erro ao carregar", e.getMessage(), AlertType.ERROR);
			
		}

	}

	@Override
	public void onDadosAtualizados() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Reserva, Reserva>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Reserva obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/view/ReservaForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initDeleteButtons() {
		tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnDELETE.setCellFactory(param -> new TableCell<Reserva, Reserva>() {
			private final Button button = new Button("deletar");

			@Override
			protected void updateItem(Reserva obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> deletarReserva(obj));
			}

		});
	}

	private void deletarReserva(Reserva obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmacao("Confirmação", "Tem certeza que deseja deletar ?");

		if (result.get() == ButtonType.OK) {

			try {
				fachada.deletarReservaPorId(obj);
				;
				updateTableView();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

		}
	}

}
