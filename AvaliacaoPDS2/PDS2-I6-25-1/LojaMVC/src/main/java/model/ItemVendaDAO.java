package model;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ItemVendaDAO {
    private final Connection conn;

    public ItemVendaDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir
    public void inserir(ItemVenda item) throws SQLException {
        String sql = "INSERT INTO item_venda (venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getVendaId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getPrecoUnitario());
            stmt.executeUpdate();
        }
    }

    // Atualizar
    public void atualizar(ItemVenda item) throws SQLException {
        String sql = "UPDATE item_venda SET venda_id=?, produto_id=?, quantidade=?, preco_unitario=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getVendaId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getPrecoUnitario());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
        }
    }

    // Deletar
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM item_venda WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Buscar por ID
    public ItemVenda buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM item_venda WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        }
        return null;
    }

    // Listar todos
    public List<ItemVenda> listarTodos() throws SQLException {
        List<ItemVenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_venda";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    // Mapear ResultSet para objeto
    private ItemVenda mapResultSet(ResultSet rs) throws SQLException {
        ItemVenda item = new ItemVenda();
        item.setId(rs.getInt("id"));
        item.setVendaId(rs.getInt("venda_id"));
        item.setProdutoId(rs.getInt("produto_id"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
        return item;
    }
}
