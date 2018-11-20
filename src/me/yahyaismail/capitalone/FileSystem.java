package me.yahyaismail.capitalone;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class FileSystem {

	//returns the contents of the file line by line
	public static String[] fileContents(String fileURL) {
		File file = new File(fileURL);
		Charset charset = Charset.forName("utf-8");
		ArrayList<String> contents = new ArrayList<String>();
		try(BufferedReader reader = Files.newBufferedReader(file.toPath(), charset))
		{
			String line = null;
			while ((line = reader.readLine()) != null) {
				contents.add(line);
			}			
		} catch (IOException e){
			System.err.format("IOException: %s%n", e);
		}
		String[] a = new String[contents.size()];
		for (int i = 0; i < a.length; i++){
			a[i] = contents.get(i);
		}
		return a;
	}
	
	//Opens a file browser and returns it's path if it's a valid source file, otherwise returns "Invalid Source File"
	public static String chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return ("Invalid Source File");
		}
		if (validateFile(chooser.getSelectedFile().getName()))
			return chooser.getSelectedFile().getAbsolutePath();
		else
			return ("Invalid Source File");
	}
	
	//helper method to validate if the source file is valid under our existing enums
	private static boolean validateFile(String file){
		file = file.substring(file.lastIndexOf("."));
		for (SourceComments source : SourceComments.values()){
			if (source.getExtension().equals(file))
				return true;
		}
		return false;
	}
	
	//gets the enum source type based on the url extension
	public static SourceComments getSourceCommentType(String fileURL){
		if (!fileURL.contains("."))
			return null;
		fileURL = fileURL.substring(fileURL.lastIndexOf("."));
		for (SourceComments source : SourceComments.values()){
			if (source.getExtension().equals(fileURL))
				return source;
		}
		return null;
	}
	
}
