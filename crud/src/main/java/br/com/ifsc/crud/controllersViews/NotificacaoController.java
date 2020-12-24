package br.com.ifsc.crud.controllersViews;

import br.com.ifsc.crud.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NotificacaoController {

    @FXML
    private Button btnFechar;

    @FXML
    private Label labelContato;

    @FXML
    private TextField textMensagem;
    
    private User userLogado;
    private User contato;
    
    
}
