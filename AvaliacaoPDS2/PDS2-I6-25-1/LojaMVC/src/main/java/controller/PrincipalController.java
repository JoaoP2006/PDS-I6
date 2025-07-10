package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.AlertaUtil;

public class PrincipalController {

    private Stage stagePrincipal;

    @FXML
    private Label lblUsuario;

    @FXML
    private MenuItem menuCadastroUsuarios;

    @FXML
    private MenuItem menuCadastroProduto;

    @FXML
    private MenuItem menuCadastroCliente;

    @FXML
    private MenuItem menuFechar;

    @FXML
    private MenuItem menuRelatorioUsuarios;

    @FXML
    private MenuItem menuRelatorioVendas;

    public void setStage(Stage stage) {
        this.stagePrincipal = stage;
    }

    @FXML
    void menuCadastroUsuariosClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Listagem de Usuários");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void menuCadastroProdutoClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ProdutoView.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Produto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void CadastrarClienteClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ClienteView.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Cliente");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void menuFecharClick(ActionEvent event) {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção", "Tem certeza que deseja fechar a aplicação?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void menuRelatorioUsuariosClick(ActionEvent event) throws IOException {
        // Implemente a abertura do relatório de usuários se desejar
    }

    @FXML
    void menuRelatorioVendasClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/VendaView.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Relatório de Vendas");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void ajustarElementosJanela(String usuario, String perfil) {
        lblUsuario.setText(usuario);
        if (!"admin".equalsIgnoreCase(perfil)) {
            menuRelatorioUsuarios.setDisable(true);
            menuRelatorioVendas.setDisable(true);
        }
    }
}
