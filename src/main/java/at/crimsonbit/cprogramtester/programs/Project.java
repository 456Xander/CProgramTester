package at.crimsonbit.cprogramtester.programs;

import java.io.File;

public class Project {
	private File folder;
	private String mainFile;

	public Project(File folder) {
		this.folder = folder;
	}

	public Project(String folder) {
		this.folder = new File(folder);
	}

	public File getFolder() {
		return folder;
	}
}
