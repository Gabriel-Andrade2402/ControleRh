package ui;



import java.io.File;
import java.io.InputStream;
import java.util.List;

import Entidades.Funcionario;
import database.Querys;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class controlUserController {
	@FXML
	private ScrollPane scrollListSp;
	@FXML
	private VBox listFuncionariosVbox;
	@FXML
	private HBox layoutControllerBottomHbox;
	@FXML
	private AnchorPane layoutVisualizationRightAnchorPane;
	@FXML
	private TextField nomeTf;
	@FXML
	private ImageView imageFuncionarioIv;
	@FXML
	private ImageView imageconfirmarIv;
	@FXML
	private ImageView imageCancelarIv;
	@FXML
	private ImageView imageFecharIv;
	@FXML
	private TextField sobrenomeTf;
	@FXML
	private TextField telefoneTf;
	@FXML
	private TextField emailTf;
	@FXML
	private Button AdicionarBt;
	@FXML
	private Button DeletarBt;
	@FXML
	private Button EditarBt;
	
	
	//Ultimo funcionario ou atual selecionado
	private Funcionario funcSelected;
	private Button buttonSelected;
	
	
	
	//M�todos de inser��o,dele��o e edi��o
	@FXML
	public void confirmButton() {
		switch(buttonSelected.getText())
		{
		case "Adicionar":
			if(alertFill()) {
				Funcionario func= new Funcionario(
						0,
						nomeTf.getText(),
						sobrenomeTf.getText(),
						telefoneTf.getText(),
						emailTf.getText(),
						null
						);
				Funcionario novo=inserirFunc(func);
			}
		break;
		case "Editar":
			Funcionario func= new Funcionario(
					0,
					nomeTf.getText(),
					sobrenomeTf.getText(),
					telefoneTf.getText(),
					emailTf.getText(),
					null
					);
			atualizaFunc(func);
			break;
		case "Deletar":
			break;
		}
		
	}
	@FXML
	public void buttonAdicionar() {
		OffFillLastButton();
		abrirLayoutVisualization();
		clearTextFields();
		buttonSelected=AdicionarBt;
		AdicionarBt.getStyleClass().remove("buttonController");
		AdicionarBt.getStyleClass().add("buttonControllerSelected");
	}
	@FXML
	public void buttonEditar() {
		OffFillLastButton();
		if(checkFuncSelected()){
			nomeTf.setEditable(true);
			sobrenomeTf.setEditable(true);
			emailTf.setEditable(true);
			telefoneTf.setEditable(true);
			buttonSelected=EditarBt;
			EditarBt.getStyleClass().remove("buttonController");
			EditarBt.getStyleClass().add("buttonControllerSelected");
		}
	}
	@FXML
	public void buttonDeletar() {
		OffFillLastButton();
		if(checkFuncSelected()) {
			deletarFunc();
		}
	}
	//Fim metodos --------------------------
	
	
	
	
	
	//Metodo para selecionar um funcionario da lista
	public void selectFunc(Funcionario func) {
		abrirLayoutVisualization();
		funcSelected=func;
		nomeTf.setText(func.getNome());
		nomeTf.setEditable(false);
		sobrenomeTf.setText(func.getSobrenome());
		sobrenomeTf.setEditable(false);
		telefoneTf.setText(func.getTelefone());
		telefoneTf.setEditable(false);
		emailTf.setText(func.getEmail());
		emailTf.setEditable(false);
		//imageFuncionarioIv.setStyle("-fx-image:url(\""+func.getPathImage()+"\");");
		imageFuncionarioIv.setImage(new Image("file:..\\..\\imagens\\"+func.getPathImage()));
	}
	
	//inserir novo funcionario, atualizar ou deletar
	public Funcionario inserirFunc(Funcionario func) {
		updateVboxInsert(func);
		return Querys.addFuncionario(func);
		
	}
	public void atualizaFunc(Funcionario func) {
		func.setId(funcSelected.getId());
		Querys.atualizarFuncionario(func);
		updateTfs(func);
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Atualiza��o");
		alert.setContentText("Os dados do funcionario foram atualizados");
		alert.show();
	}
	public void deletarFunc() {
		Querys.deletarFuncionario(funcSelected);
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Deletado");
		alert.setContentText("O funcionario n�o faz mais parte do banco");
		alert.show();
		updateVboxDelete(funcSelected);
		clearTextFields();
		fecharLayoutVisualization();
	}
	
	
	
	//Metodo de inicializa��o de lista de funcionarios
	@FXML
	public void fillList() {
		if(listFuncionariosVbox==null) {
			InsertVbox();
			List<Funcionario>list=Querys.returnAll();
			for(Funcionario func:list) {
				Button bt= new Button();
				bt.setText(func.getEmail());
				bt.setPrefWidth(825);
				bt.setPrefHeight(20);
				bt.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					   public void handle(MouseEvent event) {
					      selectFunc(Querys.findByEmail(event.toString().split("'")[1]));
					   } 
					}));
				bt.getStyleClass().add("objectList");
				//Ordem dos insets � Bottom rigth left top
				listFuncionariosVbox.setMargin(bt,new Insets(10,0,0,2));
				listFuncionariosVbox.getChildren().add(bt);
			}
		}
	}
	//M�todo que inicializa o Vbox dentro do ScrollPane
	public void InsertVbox() {
		if(listFuncionariosVbox==null) {
			listFuncionariosVbox= new VBox();
			scrollListSp.setContent(listFuncionariosVbox);
			initializeCssForImagens();
		}
		listFuncionariosVbox.styleProperty().set("-fx-background-color:#363636;");
		listFuncionariosVbox.getParent().styleProperty().set("-fx-background-color:#363636;");
	}
	//M�todo que seleciona arquivo
	@FXML
	public File selectYourFile() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File file=chooser.showOpenDialog(null);
		String path;
		if(file!= null) {
		path = file.getPath();
		} else {
		//default return value
		path = null;
		}
		return file;
	}
	//Fechar e Abrir layoutVisualizationRightAnchorPane
	public void fecharLayoutVisualization() {
		if(layoutVisualizationRightAnchorPane.getOpacity()==1) {
			listFuncionariosVbox.setPrefWidth(830);
			layoutControllerBottomHbox.setPrefWidth(900);
			AdicionarBt.setPrefHeight(100);
			AdicionarBt.setPrefWidth(200);
			DeletarBt.setPrefHeight(100);
			DeletarBt.setPrefWidth(200);
			EditarBt.setPrefHeight(100);
			EditarBt.setPrefWidth(200);
			layoutControllerBottomHbox.setMargin(AdicionarBt,new Insets(45,0,20,70));
			layoutControllerBottomHbox.setMargin(DeletarBt,new Insets(45,0,20,70));
			layoutControllerBottomHbox.setMargin(EditarBt,new Insets(45,0,20,70));
			layoutVisualizationRightAnchorPane.setOpacity(0);
			layoutVisualizationRightAnchorPane.setDisable(true);
			clearTextFields();
			OffFillLastButton();
		}
	}
	@FXML
	public void abrirLayoutVisualization() {
		listFuncionariosVbox.setPrefWidth(498);
		layoutControllerBottomHbox.setPrefWidth(500);
		AdicionarBt.setPrefHeight(70);
		AdicionarBt.setPrefWidth(130);
		DeletarBt.setPrefHeight(70);
		DeletarBt.setPrefWidth(130);
		EditarBt.setPrefHeight(70);
		EditarBt.setPrefWidth(130);
		layoutControllerBottomHbox.setMargin(AdicionarBt,new Insets(45,0,20,30));
		layoutControllerBottomHbox.setMargin(DeletarBt,new Insets(45,0,20,30));
		layoutControllerBottomHbox.setMargin(EditarBt,new Insets(45,0,20,30));
		layoutVisualizationRightAnchorPane.setOpacity(1);
		layoutVisualizationRightAnchorPane.setDisable(false);
		/*for(Node n:listFuncionariosVbox.getChildren()) {
			Button bt=(Button)n;
			bt.setLayoutX(400);
		}*/	
	}
	
	/*metodos auxiliar para
	  -resetar os dados antigos dos textFields,
	  -Iniciar css das imagens,
	  -alertar se n�o existir usuario selecionado
	  -alertar confirma��o
	  -preencha todos campos
	  -atualiza VBox inserindo 1
	  -desligar preenchimento do ultimo botao
	  -atualiza vbox deletando 1
	*/
	public void updateVboxDelete(Funcionario func) {
		for(Node node:listFuncionariosVbox.getChildren()) {
			Button button=(Button)node;
			if(button.getText().equals(func.getEmail())) {
				listFuncionariosVbox.getChildren().remove(node);
			}
		}
	}
	public void updateTfs(Funcionario func) {
		nomeTf.setText(func.getNome());
		emailTf.setText(func.getEmail());
		telefoneTf.setText(func.getTelefone());
		emailTf.setText(func.getEmail());
	}
	public void updateVboxInsert(Funcionario func) {
		Button bt= new Button();
		bt.setText(func.getEmail());
		bt.setPrefWidth(825);
		bt.setPrefHeight(25);
		bt.setOnMouseClicked((new EventHandler<MouseEvent>() { 
			   public void handle(MouseEvent event) {
			      selectFunc(Querys.findByEmail(event.toString().split("'")[1]));
			   } 
			}));
		bt.getStyleClass().add("objectList");
		//Ordem dos insets � Bottom rigth left top
		listFuncionariosVbox.setMargin(bt,new Insets(10,0,0,2));
		listFuncionariosVbox.getChildren().add(bt);
	}
	public void OffFillLastButton() {
		if(buttonSelected!=null) {
			switch(buttonSelected.getText()) {
				case "Adicionar":
					AdicionarBt.getStyleClass().remove("buttonControllerSelected");
					AdicionarBt.getStyleClass().add("buttonController");
					break;
				case "Deletar":
					DeletarBt.getStyleClass().remove("buttonControllerSelected");
					DeletarBt.getStyleClass().add("buttonController");
					break;
				case "Editar":
					EditarBt.getStyleClass().remove("buttonControllerSelected");
					EditarBt.getStyleClass().add("buttonController");
					break;
			}
		}
	}
	public boolean alertFill() {
		if(nomeTf.getText()==""||nomeTf.getText()==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro nos campos");
			alert.setHeaderText("Preencha todos campos");
			alert.show();
			return false;
		}
		if(sobrenomeTf.getText()==""||sobrenomeTf.getText()==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro nos campos");
			alert.setHeaderText("Preencha todos campos");
			alert.show();
			return false;
		}
		if(telefoneTf.getText()==""||telefoneTf.getText()==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro nos campos");
			alert.setHeaderText("Preencha todos campos");
			alert.show();
			return false;
		}
		if(emailTf.getText()==""||emailTf.getText()==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro nos campos");
			alert.setHeaderText("Preencha todos campos");
			alert.show();
			return false;
		}
		
		return true;
	}
	public void alertConfirm() {}
	public boolean checkFuncSelected() {
		if(funcSelected==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro de sele��o");
			alert.setHeaderText("Selecione pelo menos um funcionario para continuar");
			alert.show();
			return false;
		}
		return true;
	}
	public void clearTextFields() {
		nomeTf.setText("");
		nomeTf.setEditable(true);
		sobrenomeTf.setText("");
		sobrenomeTf.setEditable(true);
		telefoneTf.setText("");
		telefoneTf.setEditable(true);
		emailTf.setText("");
		emailTf.setEditable(true);
	}
	public void initializeCssForImagens() {
		imageconfirmarIv.getStyleClass().add("imageDefault");
		imageCancelarIv.getStyleClass().add("imageDefault");
		imageFecharIv.getStyleClass().add("imageDefault");
	}
}
