package controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Produto;
import model.ProdutoDAO;
import util.AlertaUtil;

public class ProdutoController implements Initializable {

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir; 

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtQuantidadeDeEstoque;

    @FXML
    private TextField txtValor;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, Double> colValor;

    @FXML
    private TableColumn<Produto, Integer> colQuantidade;

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
        produto.setValor(valor);

        new ProdutoDAO().inserirProduto(produto);

        AlertaUtil.mostrarInformacao("Sucesso", "Produto salvo com sucesso!");

        listarProdutos();

        txtDescricao.clear();
        txtQuantidadeDeEstoque.clear();
        txtValor.clear();
    }

    // Novo método para excluir produto selecionado
    @FXML
void btnExcluirProduto(ActionEvent event) throws SQLException {
    Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

    if (produtoSelecionado == null) {
        AlertaUtil.mostrarErro("Erro", "Selecione um produto para excluir.");
        return;
    }

    Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Confirmação",
            "Deseja realmente excluir o produto: " + produtoSelecionado.getDescricao() + "?");

    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
        new ProdutoDAO().deletarProduto(produtoSelecionado.getId());

        AlertaUtil.mostrarInformacao("Sucesso", "Produto excluído com sucesso!");

        listarProdutos();

        tabelaProdutos.getSelectionModel().clearSelection();
        txtDescricao.clear();
        txtQuantidadeDeEstoque.clear();
        txtValor.clear();
    }
}


@FXML
void btnVenderProduto(ActionEvent event) {
    Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

    if (produtoSelecionado == null) {
        AlertaUtil.mostrarErro("Erro", "Selecione um produto para vender.");
        return;
    }

    TextInputDialog dialog = new TextInputDialog("1");
    dialog.setTitle("Venda de Produto");
    dialog.setHeaderText("Venda de Produto: " + produtoSelecionado.getDescricao());
    dialog.setContentText("Quantidade:");

    Optional<String> resultado = dialog.showAndWait();
    if (!resultado.isPresent()) return;

    int quantidade;
    try {
        quantidade = Integer.parseInt(resultado.get());
        if (quantidade <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
        AlertaUtil.mostrarErro("Erro", "Quantidade inválida.");
        return;
    }

    if (quantidade > produtoSelecionado.getQuantidadeEstoque()) {
        AlertaUtil.mostrarErro("Erro", "Estoque insuficiente.");
        return;
    }

    try {
        // Reduz estoque
        produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidade);
        new ProdutoDAO().atualizarProduto(produtoSelecionado);

        // Cria conexão e passa para ItemVendaDAO
        try (Connection conn = dal.ConexaoBD.conectar()) {
            model.ItemVendaDAO itemVendaDAO = new model.ItemVendaDAO(conn);

            model.ItemVenda item = new model.ItemVenda();
            item.setVendaId(1); 
            item.setProdutoId(produtoSelecionado.getId());
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(BigDecimal.valueOf(produtoSelecionado.getValor()));

            itemVendaDAO.inserir(item);
        }

        AlertaUtil.mostrarInformacao("Sucesso", "Venda registrada com sucesso!");
        listarProdutos();

    } catch (SQLException e) {
        e.printStackTrace();
        AlertaUtil.mostrarErro("Erro", "Erro ao realizar a venda.");
    }
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        listarProdutos();

        btnExcluir.setDisable(true);

        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnExcluir.setDisable(newSelection == null);
        });
    }

    private void listarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.listarProdutos();
        ObservableList<Produto> dados = FXCollections.observableArrayList(lista);
        tabelaProdutos.setItems(dados);
    }
    
}
