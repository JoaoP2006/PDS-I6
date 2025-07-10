package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (descricao, valor, quantidade_estoque) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValor());
            stmt.setInt(3, produto.getQuantidadeEstoque());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id")); // se sua tabela tiver campo id
                produto.setDescricao(rs.getString("descricao"));
                produto.setValor(rs.getDouble("valor"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }
    
    public void deletarProduto(int id) {
    String sql = "DELETE FROM produto WHERE id = ?";

    try (Connection conn = ConexaoBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Nenhum produto encontrado com o id: " + id);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao deletar produto: " + e.getMessage());
    }
}
    public void atualizarProduto(Produto produto) {
    String sql = "UPDATE produto SET descricao = ?, valor = ?, quantidade_estoque = ? WHERE id = ?";

    try (Connection conn = ConexaoBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, produto.getDescricao());
        stmt.setDouble(2, produto.getValor());
        stmt.setInt(3, produto.getQuantidadeEstoque());
        stmt.setInt(4, produto.getId());

        int linhasAtualizadas = stmt.executeUpdate();

        if (linhasAtualizadas > 0) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Nenhum produto encontrado com o id: " + produto.getId());
        }

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar produto: " + e.getMessage());
    }
}


   
    
}
