package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class VendaDAO {

    public int inserirVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (data_compra, valor_total, cliente_id) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, venda.getDataCompra());
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setInt(3, venda.getIdCliente());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir venda, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    venda.setId(idGerado);
                    System.out.println("Venda inserida com sucesso! ID gerado: " + idGerado);
                    return idGerado;
                } else {
                    throw new SQLException("Falha ao obter o ID da venda.");
                }
            }
        }
    }

    public void listarVendas() throws SQLException {
        String sql = "SELECT * FROM venda";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Date dataCompra = rs.getDate("data_compra");
                BigDecimal valor = rs.getBigDecimal("valor_total");
                int idCliente = rs.getInt("cliente_id");

                System.out.println("ID: " + id + "\nData de compra: " + dataCompra + "\nValor total: " + valor +
                        "\nID do cliente: " + idCliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }
    }
}
