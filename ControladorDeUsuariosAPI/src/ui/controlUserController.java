package ui;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Entidades.Funcionario;
import Sup.CssManipulation;
import Sup.FileManipulation;
import Sup.TransitionManipulation;
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
import model.databaseManipulation;

public class controlUserController {
	@FXML
	private ScrollPane scrollListSp;
	@FXML
	private VBox listFuncionariosVbox;
	@FXML
	private HBox layoutControllerBottomHbox;
	@FXML
	private AnchorPane parent;
	@FXML
	private AnchorPane layoutVisualizationRightAnchorPane;
	@FXML
	private AnchorPane layoutAlterModeAndColors;
	@FXML
	private ImageView imageFuncionarioIv;
	@FXML
	private ImageView imageconfirmarIv;
	@FXML
	private ImageView imageCancelarIv;
	@FXML
	private ImageView imageFecharIv;
	@FXML
	private ImageView buttonModoEscuro;
	@FXML
	private TextField sobrenomeTf;
	@FXML
	private TextField telefoneTf;
	@FXML
	private TextField nomeTf;
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
	
	
	
	//M?todos de inser??o,dele??o e edi??o
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
						imageFuncionarioIv.getId()
						);
				Funcionario novo=inserirFunc(func);
				funcSelected=novo;
				alertConfirm();
			}
		break;
		case "Editar":
			Funcionario func= new Funcionario(
					0,
					nomeTf.getText(),
					sobrenomeTf.getText(),
					telefoneTf.getText(),
					emailTf.getText(),
					imageFuncionarioIv.getId()
					);
			atualizaFunc(func);
			break;
		case "Deletar":
			break;
		}
		
	}
	@FXML
	public void buttonAdicionar() {
		cleanFillLastButton();
		abrirLayoutVisualization();
		clearTextFields();
		buttonSelected=AdicionarBt;
		CssManipulation.removeClassInNode(AdicionarBt,"buttonController");
		CssManipulation.addClassInNode(AdicionarBt,"buttonControllerSelected");
		imageFuncionarioIv.setOnMouseClicked((new EventHandler<MouseEvent>() { 
			   public void handle(MouseEvent event) {
				   File file=FileManipulation.selectYourFile();
				   if(file!=null) {
					   imageFuncionarioIv.setImage(new Image("File:..\\..\\imagens\\"+file.getName()));
					   imageFuncionarioIv.setId(file.getName());
				   }
				   
			   } 
			}));
		CssManipulation.addClassInNode(imageFuncionarioIv, "imageDefault");
	}
	@FXML
	public void buttonEditar() {
		cleanFillLastButton();
		if(checkFuncSelected()){
			nomeTf.setEditable(true);
			sobrenomeTf.setEditable(true);
			emailTf.setEditable(true);
			telefoneTf.setEditable(true);
			buttonSelected=EditarBt;
			CssManipulation.removeClassInNode(EditarBt,"buttonController");
			CssManipulation.addClassInNode(EditarBt,"buttonControllerSelected");
			imageFuncionarioIv.setOnMouseClicked((new EventHandler<MouseEvent>() { 
				   public void handle(MouseEvent event) {
					   File file=FileManipulation.selectYourFile();
					   if(file!=null) {
						   imageFuncionarioIv.setImage(new Image("File:..\\..\\imagens\\"+file.getName()));
						   imageFuncionarioIv.setId(file.getName());
					   }
					   
				   } 
		    }));
			CssManipulation.addClassInNode(imageFuncionarioIv, "imageDefault");
		}else {
			fecharLayoutVisualization();
		}
	}
	@FXML
	public void buttonDeletar() {
		cleanFillLastButton();
		if(checkFuncSelected()) {
			deletarFunc();
		}
	}
	//Fim metodos --------------------------
	
	
	
	
	
	//Metodo para selecionar um funcionario da lista
	public void selectFunc(Funcionario func) {
		abrirLayoutVisualization();
		funcSelected=func;
		if(buttonSelected!=null) {if(!buttonSelected.getText().equals("Editar")) {
			nomeTf.setEditable(false);	
			sobrenomeTf.setEditable(false);
			telefoneTf.setEditable(false);
			emailTf.setEditable(false);
			imageFuncionarioIv.setOnMouseClicked(null);
			CssManipulation.addClassInNode(imageFuncionarioIv,"imageDefault");
		}if(buttonSelected.getText().equals("Adicionar")){
			CssManipulation.removeClassInNode(AdicionarBt,"buttonControllerSelected");
			CssManipulation.addClassInNode(AdicionarBt,"buttonController");
			}
		}else {
			nomeTf.setEditable(false);	
			sobrenomeTf.setEditable(false);
			telefoneTf.setEditable(false);
			emailTf.setEditable(false);
			imageFuncionarioIv.setOnMouseClicked(null);
		}
		nomeTf.setText(func.getNome());
		sobrenomeTf.setText(func.getSobrenome());
		telefoneTf.setText(func.getTelefone());
		emailTf.setText(func.getEmail());
		imageFuncionarioIv.setImage(new Image("file:..\\..\\imagens\\"+func.getPathImage()));
		imageFuncionarioIv.setId(func.getPathImage());
	}
	
	//inserir novo funcionario, atualizar ou deletar
	public Funcionario inserirFunc(Funcionario func) {
		updateVboxInsert(func);
		return databaseManipulation.addFuncionario(func);
		
	}
	public void atualizaFunc(Funcionario func) {
		func.setId(funcSelected.getId());
		databaseManipulation.updateFuncionario(func);
		updateTfs(func);
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Atualiza??o");
		alert.setContentText("Os dados do funcionario foram atualizados");
		alert.show();
	}
	public void deletarFunc() {
		databaseManipulation.removeFuncionario(funcSelected);
		Alert alert= new Alert(AlertType.INFORMATION);
		alert.setTitle("Deletado");
		alert.setContentText("O funcionario n?o faz mais parte do banco");
		alert.show();
		clearTextFields();
		fecharLayoutVisualization();
		updateVboxDelete(funcSelected);
	}
	
	
	
	//Metodo de inicializa??o de lista de funcionarios
	@FXML
	public void fillList() {
		if(listFuncionariosVbox==null) {
			InsertVbox();
			List<Funcionario>list=databaseManipulation.returnAll();
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
				CssManipulation.addClassInNode(bt,"objectListSun");
				//Ordem dos insets ? Bottom rigth left top
				listFuncionariosVbox.setMargin(bt,new Insets(10,0,0,2));
				listFuncionariosVbox.getChildren().add(bt);
			}
		}
	}
	//M?todo que inicializa o Vbox dentro do ScrollPane
	public void InsertVbox() {
		if(listFuncionariosVbox==null) {
			listFuncionariosVbox= new VBox();
			scrollListSp.setContent(listFuncionariosVbox);
			initializeCssForImagens();
		}
		listFuncionariosVbox.styleProperty().set("-fx-background-color:#FFFAFA;");
		listFuncionariosVbox.getParent().styleProperty().set("-fx-background-color:#FFFAFA;");
	}

	
	//Fechar e Abrir layoutVisualizationRightAnchorPane
	public void fecharLayoutVisualization() {
		if(layoutVisualizationRightAnchorPane.getOpacity()==1) {
			listFuncionariosVbox.setPrefWidth(825);
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
			funcSelected=null;
			clearTextFields();
			cleanFillLastButton();
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
	
	//Transi??es
	@FXML
	public void transition() {
		if(buttonModoEscuro.getTranslateX()==45) {
			//Muda para o modo claro
			TransitionManipulation.translateXOfNodeTransition(buttonModoEscuro,0);
			TransitionManipulation.rotateNode180negative(buttonModoEscuro);	
			TransitionManipulation.alterImageWithDelay01(buttonModoEscuro,"iconeTrocarModo-lua.png");
			CssManipulation.alterFill(layoutControllerBottomHbox,"#6495ED");
			CssManipulation.alterFill(scrollListSp,"#FFFAFA");
			CssManipulation.alterFill(layoutVisualizationRightAnchorPane,"#FFFAFA");
			CssManipulation.alterFill(parent,"#FFFAFA");
			CssManipulation.alterFill(listFuncionariosVbox,"#FFFAFA");
			CssManipulation.removeClassArrayNode(listFuncionariosVbox.getChildren(),"objectListMoon");
			CssManipulation.addClassArrayNode(listFuncionariosVbox.getChildren(),"objectListSun");
		}if(buttonModoEscuro.getTranslateX()==0) {
			//Muda para o modo escuro
			TransitionManipulation.translateXOfNodeTransition(buttonModoEscuro,45);
			TransitionManipulation.rotateNode180positive(buttonModoEscuro);
			TransitionManipulation.alterImageWithDelay01(buttonModoEscuro,"iconeTrocarModo-sol.png");
			CssManipulation.alterFill(layoutControllerBottomHbox,"#4F4F4F");
			CssManipulation.alterFill(scrollListSp,"#4F4F4F");
			CssManipulation.alterFill(layoutVisualizationRightAnchorPane,"#4F4F4F");
			CssManipulation.alterFill(parent,"#4F4F4F");
			CssManipulation.alterFill(listFuncionariosVbox,"#4F4F4F");
			CssManipulation.removeClassArrayNode(listFuncionariosVbox.getChildren(),"objectListSun");
			CssManipulation.addClassArrayNode(listFuncionariosVbox.getChildren(),"objectListMoon");
		}
	}
	
	/*metodos auxiliar para
	  -resetar os dados antigos dos textFields,
	  -Iniciar css das imagens,
	  -alertar se n?o existir usuario selecionado
	  -alertar confirma??o
	  -preencha todos campos
	  -atualiza VBox inserindo 1
	  -desligar preenchimento do ultimo botao
	  -atualiza vbox deletando 1
	  -tira a edi??o da imagem do funcionario
	*/
	public void updateVboxDelete(Funcionario func) {
		for(Node node:listFuncionariosVbox.getChildren()) {
			Button button=(Button)node;
			System.out.println(button.getText());
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
		CssManipulation.addClassInNode(bt, "objectListSun");
		//Ordem dos insets ? Bottom rigth left top
		listFuncionariosVbox.setMargin(bt,new Insets(10,0,0,2));
		listFuncionariosVbox.getChildren().add(bt);
	}
	public void cleanFillLastButton() {
		if(buttonSelected!=null) {
			switch(buttonSelected.getText()) {
				case "Adicionar":
					CssManipulation.removeClassInNode(AdicionarBt,"buttonControllerSelected");
					CssManipulation.addClassInNode(AdicionarBt, "buttonController");
					break;
				case "Deletar":
					CssManipulation.removeClassInNode(DeletarBt,"buttonControllerSelected");
					CssManipulation.addClassInNode(DeletarBt, "buttonController");
					break;
				case "Editar":
					CssManipulation.removeClassInNode(EditarBt,"buttonControllerSelected");
					CssManipulation.addClassInNode(EditarBt, "buttonController");
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
	public void alertConfirm() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sucesso");
		alert.setContentText("Opera??o realizada com sucesso");
		alert.show();
	}
	public boolean checkFuncSelected() {
		if(funcSelected==null) {
			Alert alert= new Alert(AlertType.WARNING);
			alert.setTitle("Erro de sele??o");
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
		imageFuncionarioIv.setImage(new Image("File:..\\..\\imagens\\ImageDefault.png"));
	}
	public void clearEditingImage() {}
	public void initializeCssForImagens() {
		List<Node> listNode=new ArrayList<Node>();
		listNode.add(imageconfirmarIv);
		listNode.add(imageCancelarIv);
		listNode.add(imageFecharIv);
		CssManipulation.addClassInArrayOfNodes(listNode, "imageDefault");
	}
}
