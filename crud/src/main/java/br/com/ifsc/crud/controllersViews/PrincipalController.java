package br.com.ifsc.crud.controllersViews;

import br.com.ifsc.crud.entities.User1;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class PrincipalController {
	
    @FXML
    private Button btnGrupo;

    @FXML
    private ListView<User1> listViewGrupo;

    @FXML
    private ListView<User1> listViewContatos;

}
