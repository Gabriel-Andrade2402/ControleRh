package Sup;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TransitionManipulation {

	public static void translateXOfNodeTransition(Node node, double value) {
		TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5),node);
        trans.setToX(value);
        trans.setCycleCount(1);
        trans.play();  
	}
	public static void rotateNode180positive(Node node) {
		RotateTransition trans = new RotateTransition(Duration.seconds(0.5),node);
        trans.setFromAngle(0.0);
        trans.setToAngle(180.0);
        trans.setCycleCount(1);
        trans.play();
	}
	public static void rotateNode180negative(Node node) {
		RotateTransition trans = new RotateTransition(Duration.seconds(0.5),node);
        trans.setFromAngle(0.0);
        trans.setToAngle(-180.0);
        trans.setCycleCount(1);
        trans.play();
	}
	public static void alterImageWithDelay01(ImageView img,String newImage) {
		Timer timer= new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				img.setImage(new Image("File:..\\..\\imagens\\"+newImage));
			}
		},250l);
		
	}


}
