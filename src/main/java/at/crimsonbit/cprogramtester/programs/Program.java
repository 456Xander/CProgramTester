package at.crimsonbit.cprogramtester.programs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Program {
	
public int compile;
private File programFolder;
private String name;
private String projectFolder;

}

public String programName(String name) {
	BufferedWriter bw;
	File f = new File(projectFolder+"programName.c");
	
	FileWriter fw = new FileWriter(f);
	bw = new BufferedWriter(fw);
	bw.write(name);
}
