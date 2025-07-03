
package controller;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VendaController {
    
    Stage stageVendaView;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<?> tabelaUsuarios;

    @FXML
    void TableViewClick(MouseEvent event) {

    }

    @FXML
    void btnFecharClick(ActionEvent event) {
     stageVendaView.close();
    }
     void setStage(Stage telaVendaView) {
        this.stageVendaView = telaVendaView;
    }

    

}
