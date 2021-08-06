package cssSup;

import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class TransitionsDefault {
	public static void clickButton(Button button) {
	   FillTransition fillTransition = new FillTransition();
	   fillTransition.setFromValue(Color.RED);
	   fillTransition.setToValue(Color.DARKGREEN);
	   fillTransition.setDuration(Duration.millis(100));
	   fillTransition.setShape((Shape)((Node)button));
	   fillTransition.setAutoReverse(true);
	   fillTransition.setCycleCount(2);
	}
}
