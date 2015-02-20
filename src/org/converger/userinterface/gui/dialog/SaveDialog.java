package org.converger.userinterface.gui.dialog;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represents a save dialog, when the user can choose the location where the file
 * will be saved.
 * @author Gabriele Graffieti
 *
 */
public class SaveDialog {
	
	private final JFileChooser fileChooser;
	private final JFrame parent;
	
	/**
	 * Creates the save dialog.
	 * @param parentFrame the parent of the dialog.
	 */
	public SaveDialog(final JFrame parentFrame) {
		this.parent = parentFrame;
	    this.fileChooser = new JFileChooser();
	    this.fileChooser.setAcceptAllFileFilterUsed(false);
	    this.fileChooser.setFileFilter(new FileNameExtensionFilter("Converger documents", "conv"));
	    this.fileChooser.setSelectedFile(new File("Converger1.conv"));
	    
	}
	
	/**
	 * Return an Optional<String> representing the path chosen by the user. If the user 
	 * closes the dialog return an Optional.empty.
	 * @return the path chosen by the user.
	 */
	public Optional<String> getPath() {
		if (this.fileChooser.showSaveDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
			String fileName = this.fileChooser.getSelectedFile().getAbsolutePath();
			if (!fileName.endsWith(".conv")) {
			    fileName += ".conv";  //NOPMD
			}
	        return Optional.of(fileName);
	    } else {
	    	return Optional.empty();
	    }
	}
}
