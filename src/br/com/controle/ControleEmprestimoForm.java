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
import br.com.model.Emprestimo;
import br.com.model.Livro;
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

public class ControleEmprestimoForm implements Initializable {

	private Emprestimo emprestimo;

	private Fachada fachada;

	private List<AtualizarDadosOuvinte> atualizarDadosOuvintes = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private ComboBox<UsuarioComum> comboBoxUsuario;

	@FXML
	private ComboBox<Livro> comboBoxLivro;

	@FXML
	private ComboBox<Livro> comboBoxLivro2;

	@FXML
	private DatePicker dpDataEmprestimo;

	@FXML
	private DatePicker dpDataDevolucao;

	@FXML
	private Label labelErrorDataEmprestimo;

	@FXML
	private Label labelErrorDataDevolucao;

	@FXML
	private Label labelErrorUsuario;

	@FXML
	private Label labelErrorLivro;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	private ObservableList<UsuarioComum> usuarioObsList;

	private ObservableList<Livro> livroObsList;	

	public void onBtnSalvarAction(ActionEvent event) {

		try {
			emprestimo = getFormData();
			fachada.cadastrarEmprestimo(emprestimo);
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

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	private Emprestimo getFormData() {

		Emprestimo obj = new Emprestimo();
		
		if (emprestimo.getId() != null) {
			obj.setId(Utils.tryParseToInt(txtId.getText()));
		}

		ValidacaoException exception = new ValidacaoException("Erro ao validar");

		if (dpDataEmprestimo.getValue() == null) {
			exception.addError("dataEmprestimo", "Preencha este campo");
		} else {
			Instant instant = Instant.from(dpDataEmprestimo.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataEmprestimo(Date.from(instant));
		}

		if (dpDataDevolucao.getValue() == null) {
			exception.addError("dataDevolucao", "Preencha este campo");
		} else {
			Instant instant = Instant.from(dpDataDevolucao.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataDevolucao(Date.from(instant));
		}

		obj.setUsuarioComum(comboBoxUsuario.getValue());
	
		
		
		obj.getLivros().add(comboBoxLivro.getValue());
		obj.getLivros().add(comboBoxLivro2.getValue());
	

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorDataEmprestimo.setText((fields.contains("dataEmprestimo") ? errors.get("dataEmprestimo") : ""));
		labelErrorDataDevolucao.setText((fields.contains("dataDevolucao") ? errors.get("dataDevolucao") : ""));

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

		if (emprestimo == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(emprestimo.getId()));
		if (emprestimo.getDataEmprestimo() != null && emprestimo.getDataDevolucao() != null) {
			dpDataEmprestimo
					.setValue(emprestimo.getDataEmprestimo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			dpDataDevolucao
					.setValue(emprestimo.getDataDevolucao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		}

		if (emprestimo.getUsuarioComum() == null && emprestimo.getLivro() == null) {
			comboBoxUsuario.getSelectionModel().selectFirst();
			comboBoxLivro.getSelectionModel().selectFirst();
			comboBoxLivro2.getSelectionModel().selectFirst();
		} else {			
			comboBoxUsuario.setValue(emprestimo.getUsuarioComum());
			comboBoxLivro.setValue(emprestimo.getLivro());
			comboBoxLivro2.setValue(emprestimo.getLivro());
			

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
		comboBoxLivro2.setItems(livroObsList);

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
		comboBoxLivro2.setCellFactory(factory);
		comboBoxLivro2.setButtonCell(factory.call(null));
	}

}
