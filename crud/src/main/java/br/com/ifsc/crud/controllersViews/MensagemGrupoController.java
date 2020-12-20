package br.com.ifsc.crud.controllersViews;

import br.com.ifsc.crud.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MensagemGrupoController {
	
    @FXML
    private TextArea txtAreaMensagem;

    @FXML
    private TextField textMensagemUsuario;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnSair;

    @FXML
    private TextField textUsuario;

    @FXML
    private ListView<User> listContatos;


}
