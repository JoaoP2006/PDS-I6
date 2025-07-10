package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class ClienteViewController implements Initializable {

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, Date> colNascimento;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TableView<Cliente> TabelaCliente; 

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    void onClickSalvar(ActionEvent event) throws SQLException {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String endereco = txtEndereco.getText();
        String dataTexto = txtDataNascimento.getText();

        Date dataNascimento = null;

        if (!dataTexto.isEmpty()) {
            try {
                LocalDate localDate = LocalDate.parse(dataTexto, formatoData);
                dataNascimento = Date.valueOf(localDate);
            } catch (DateTimeParseException e) {
                AlertaUtil.mostrarErro("Data inválida", "Use o formato dd/MM/yyyy.");
                return;
            }
        }

        if (nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setDataNascimento(dataNascimento);

        new ClienteDAO().inserirCliente(cliente);

        AlertaUtil.mostrarInformacao("Sucesso", "Cliente salvo com sucesso!");

        carregarClientes(); 

        limparCampos(); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        carregarClientes();
    }

    private void configurarColunas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
    }

    private void carregarClientes() {
        ClienteDAO dao = new ClienteDAO();
        
        List<Cliente> lista = dao.listarClientes();
        
        ObservableList<Cliente> dados = FXCollections.observableArrayList(lista);
        TabelaCliente.setItems(dados);
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        txtDataNascimento.clear();
    }
}
