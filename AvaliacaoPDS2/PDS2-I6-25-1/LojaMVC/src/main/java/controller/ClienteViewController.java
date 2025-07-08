package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class ClienteViewController {

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

        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }

}