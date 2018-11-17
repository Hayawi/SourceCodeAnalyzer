package me.yahyaismail.capitalone;

import java.io.File;

import javax.swing.JFileChooser;

public class FileSystem {

	public static String[] fileContents(String fileURL) {
		
		return null;
	}
	
	public static String chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}
		return chooser.getSelectedFile().getAbsolutePath();
	}
	
	private static boolean validateFile(String file){
		return false;
	}
	
	public static SourceComments getSourceCommentType(String fileURL){
		
		return null;
	}
	
}
