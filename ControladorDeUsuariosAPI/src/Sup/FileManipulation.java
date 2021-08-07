package Sup;

import java.io.File;

import javafx.stage.FileChooser;

public class FileManipulation {
	public static File selectYourFile() {
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
}
