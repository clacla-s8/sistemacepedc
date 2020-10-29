package br.com.controle;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.model.Reserva;
import br.com.model.UsuarioComum;
import br.com.model.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class ControleReservaForm implements Initializable {

	private Reserva reserva;


	private Fachada fachada;

	private List<AtualizarDadosOuvinte> atualizarDadosOuvintes = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtUsuario;

	@FXML
	private TextField txtLivro;

	@FXML
	private ComboBox<UsuarioComum> comboBoxUsuario;

	@FXML
	private ComboBox<Livro> comboBoxLivro;

	@FXML
	private DatePicker dpDataReserva;

	@FXML
	private DatePicker dpDataPegar;

	@FXML
	private Label labelErrorDataReserva;

	@FXML
	private Label labelErrorDataPegar;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	private ObservableList<UsuarioComum> usuarioObsList;

	private ObservableList<Livro> livroObsList;

	public void onBtnSalvarAction(ActionEvent event) {

		try {
			reserva = getFormData();
			fachada.cadastrarReserva(reserva);
			notificacaoAtualizarDadosOuvintes();
			Utils.currentStage(event).close();
		} catch (ValidacaoException e) {
			setErrorMessages(e.getErrors());
		} catch (BusinessException e) {
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
		for (AtualizarDadosOuvinte ouvinte : atualizarDadosOuvintes) {
			ouvinte.onDadosAtualizados();
		}

	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	private Reserva getFormData() {

		Reserva obj = new Reserva();

		if (reserva.getId() != null) {
			obj.setId(Utils.tryParseToInt(txtId.getText()));
		}

		ValidacaoException exception = new ValidacaoException("Erro ao validar");

		if (dpDataReserva.getValue() == null) {
			exception.addError("dataReserva", "Preencha este campo");
		} else {
			Instant instant = Instant.from(dpDataReserva.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataReserva(Date.from(instant));
		}

		if (dpDataPegar.getValue() == null) {
			exception.addError("dataPegar", "Preencha este campo");
		} else {
			Instant instant = Instant.from(dpDataPegar.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataPegar(Date.from(instant));
		}
		
		obj.setUsuarioComum(comboBoxUsuario.getValue());
		obj.setLivro(comboBoxLivro.getValue());
		

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorDataReserva.setText((fields.contains("dataReserva") ? errors.get("dataReserva") : ""));
		labelErrorDataPegar.setText((fields.contains("dataPegar") ? errors.get("dataPegar") : ""));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();
	}

	private void initializeNodes() {
		initializeComboBoxUsuario();
		initializeComboBoxLivro();
		
	}

	public void updateFormData() {

		if (reserva == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(reserva.getId()));
		if (reserva.getDataReserva() != null && reserva.getDataPegar() != null) {
			dpDataReserva
					.setValue(reserva.getDataReserva().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			dpDataPegar
					.setValue(reserva.getDataPegar().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		}
		
		if(reserva.getUsuarioComum() == null && reserva.getLivro() == null) {
			comboBoxUsuario.getSelectionModel().selectFirst();
			comboBoxLivro.getSelectionModel().selectFirst();
		}else {
			comboBoxUsuario.setValue(reserva.getUsuarioComum());
			comboBoxLivro.setValue(reserva.getLivro());
			
		}
		
	}

	public void loadObjetosAssociados() {
		if (fachada == null) {
			throw new IllegalStateException("fachada null");
		}

		List<UsuarioComum> usuarioList = fachada.getAllUsuarioComum();
		usuarioObsList = FXCollections.observableArrayList(usuarioList);
		comboBoxUsuario.setItems(usuarioObsList);
		List<Livro> livroList = fachada.getAllLivro();
		livroObsList = FXCollections.observableArrayList(livroList);
		comboBoxLivro.setItems(livroObsList);

	}

	private void initializeComboBoxUsuario() {
		Callback<ListView<UsuarioComum>, ListCell<UsuarioComum>> factory = lv -> new ListCell<UsuarioComum>() {
			@Override
			protected void updateItem(UsuarioComum item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxUsuario.setCellFactory(factory);
		comboBoxUsuario.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxLivro() {
		Callback<ListView<Livro>, ListCell<Livro>> factory = lv -> new ListCell<Livro>() {
			@Override
			protected void updateItem(Livro item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomeLivro());
			}
		};
		comboBoxLivro.setCellFactory(factory);
		comboBoxLivro.setButtonCell(factory.call(null));
	}

}
