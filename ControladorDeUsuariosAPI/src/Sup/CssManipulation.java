package Sup;

import java.util.List;

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
}
