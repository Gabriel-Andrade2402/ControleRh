package Sup;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class CssManipulation {
	public static void addClassInNode(Node node, String classCss) {
		node.getStyleClass().add(classCss);
	}
	public static void addClassInArrayOfNodes(List<Node> listNode,String classCss) {
		for(Node node:listNode) {
			node.getStyleClass().add(classCss);
		}
	}
	public static void removeClassInNode(Node node, String classCss) {
		node.getStyleClass().remove(classCss);
	}
	public static void removeClassInArrayOfNodes(List<Node> listNode,String classCss) {
		for(Node node:listNode) {
			node.getStyleClass().remove(classCss);
		}
	}
	public static void addClassArrayNode(ObservableList<Node> list,String newClass) {
		for(Node node:list) {
			node.getStyleClass().add(newClass);
		}
	}
	public static void removeClassArrayNode(ObservableList<Node> list,String oldClass) {
		for(Node node:list) {
			node.getStyleClass().remove(oldClass);
		}
	}
	public static void alterFill(Node node,String colornew) {
		node.styleProperty().set("-fx-background-color:"+colornew+";");
	}
}
