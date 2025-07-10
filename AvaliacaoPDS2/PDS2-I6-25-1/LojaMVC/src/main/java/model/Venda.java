package model;

import java.sql.Date;
import java.math.BigDecimal;

public class Venda {
    private int id;
    private Date dataCompra;
    private BigDecimal valorTotal;
    private int idCliente;

    public Venda() {
    }

    public Venda(int id, Date dataCompra, BigDecimal valorTotal, int idCliente) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }

    public Venda(Date dataCompra, BigDecimal valorTotal, int idCliente) {
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
