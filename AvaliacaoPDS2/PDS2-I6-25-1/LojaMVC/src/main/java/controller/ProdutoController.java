package controller;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;
import util.AlertaUtil;

public class ProdutoController {

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtQuantidadeDeEstoque;

    @FXML
    private TextField txtValor;

    @FXML
    void btnSalvaProduto(ActionEvent event) throws SQLException {
        String descricao = txtDescricao.getText();
        String quantidadeStr = txtQuantidadeDeEstoque.getText();
        String valorStr = txtValor.getText();

        if (descricao.isEmpty() || quantidadeStr.isEmpty() || valorStr.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        int quantidade;
        double valor;

        try {
            quantidade = Integer.parseInt(quantidadeStr);
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            AlertaUtil.mostrarErro("Erro", "Quantidade deve ser um número inteiro e valor um número decimal.");
            return;
        }

        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setQuantidadeEstoque(quantidade);
        produto.setValor(valor); // <-- aqui precisa aceitar double

        new ProdutoDAO().inserirProduto(produto);

        AlertaUtil.mostrarInformacao("Sucesso", "Produto salvo com sucesso!");

        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}
