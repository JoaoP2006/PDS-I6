package controller;

import dal.ConexaoBD;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.LoginDAO;
import model.Usuario;
import util.AlertaUtil;

public class LoginController {

    private Stage stageLogin;
    private final LoginDAO dao = new LoginDAO();
    private Usuario user;

    @FXML
    private ImageView imgBancoOnline;
    
    @FXML
    private Button bntFechar;

    @FXML
    private Button bntLogar;

    @FXML
    private Label lblDB;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtUsuario;

    @FXML
    void bntFecharClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void bntLogarClick(ActionEvent event) throws IOException, SQLException {
        processarLogin();
    }

    public void setStage(Stage stage) {
        this.stageLogin = stage;
    }

    public void verificarBanco() {
       if(dao.bancoOnline()){
           File arquivo = new File("src/main/resources/icones/dbok.png");
           Image imagem = new Image(arquivo.toURI().toString());
           imgBancoOnline.setImage(imagem);
       } else {
           File arquivo = new File("src/main/resources/icones/dberror.png");
           Image imagem = new Image(arquivo.toURI().toString());
           imgBancoOnline.setImage(imagem);
       }
    }

    public void abrirJanela() {
        bntLogar.setDefaultButton(true);
        verificarBanco();
    }

    public void processarLogin() throws IOException, SQLException {
        if (!dao.bancoOnline()) {
            AlertaUtil.mostrarErro("Erro", "Banco de dados desconectado!");
        } else if (txtUsuario.getText() != null && !txtUsuario.getText().isEmpty() 
                && txtSenha.getText() != null && !txtSenha.getText().isEmpty()) {
            user = dao.autenticar(txtUsuario.getText(), txtSenha.getText());
            if (user != null) {
                AlertaUtil.mostrarInformacao("Informação", "Bem vindo " + user.getNome() + ", acesso liberado!");
                if (stageLogin != null) {
                    stageLogin.close();
                }
                abrirTelaPrincipal(user.getNome(), user.getPerfil());
            } else {
                AlertaUtil.mostrarErro("Erro", "Usuário e senha inválidos!");
            }
        } else {
            AlertaUtil.mostrarErro("Erro", "Verifique as informações!");
        }
    }

    private void abrirTelaPrincipal(String nomeUsuario, String perfil) throws MalformedURLException, IOException {
        URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaPrincipal = new Stage();
        PrincipalController pc = loader.getController();

        pc.setStage(telaPrincipal);

        telaPrincipal.setOnShown(evento -> {
            pc.ajustarElementosJanela(nomeUsuario, perfil);
        });

        Scene scene = new Scene(root);
        Image icone = new Image(getClass().getResourceAsStream("/icones/loja.png"));
        telaPrincipal.getIcons().add(icone);

        telaPrincipal.setTitle("Tela principal do Sistema");
        telaPrincipal.setScene(scene);
        telaPrincipal.show();
    }
}
