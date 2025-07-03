package model;

import java.sql.Date;

public class Venda {
    
      private int id;
    private Date Data_compra;
    private long Valor_total;
    private String Cliente_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_compra() {
        return Data_compra;
    }

    public void setData_compra(Date Data_compra) {
        this.Data_compra = Data_compra;
    }

    public long getValor_total() {
        return Valor_total;
    }

    public void setValor_total(long Valor_total) {
        this.Valor_total = Valor_total;
    }

    public String getCliente_id() {
        return Cliente_id;
    }

    public void setCliente_id(String Cliente_id) {
        this.Cliente_id = Cliente_id;
    }

}