package at.crimsonbit.cprogramtester.programs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Program {
	
public int compile;
private File programFolder;
private String name;
private String projectFolder;



public String programName(String name) {
	BufferedWriter bw;
	File f = new File(projectFolder+"programName.c");
	
	FileWriter fw = null;
	try {
		fw = new FileWriter(f);
	} catch (IOException e) {
		e.printStackTrace();
	}
	bw = new BufferedWriter(fw);
	try {
		bw.write(name);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return name;
}
}