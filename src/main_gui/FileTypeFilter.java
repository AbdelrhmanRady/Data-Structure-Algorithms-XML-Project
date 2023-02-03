package main_gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter {
	private final String extension;
	private final String description;
	
	public FileTypeFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(description);
	}

	@Override
	public String getDescription() {
		return description + String.format("(*%s)", extension);
	}

}
