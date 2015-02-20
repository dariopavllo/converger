package org.converger.userinterface.gui.dialog;

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
public class OpenDialog {
	
	private final JFileChooser fileChooser;
	private final JFrame parent;
	
	/**
	 * Creates the save dialog.
	 * @param parentFrame the parent of the dialog.
	 */
	public OpenDialog(final JFrame parentFrame) {
		this.parent = parentFrame;
	    this.fileChooser = new JFileChooser();
	    this.fileChooser.setAcceptAllFileFilterUsed(false);
	    this.fileChooser.setFileFilter(new FileNameExtensionFilter("Converger documents", "conv"));
	    
	}
	
	/**
	 * Return an Optional<String> representing the path chosen by the user. If the user 
	 * closes the dialog return an Optional.empty.
	 * @return the path chosen by the user.
	 */
	public Optional<String> getPath() {
		if (this.fileChooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
	        return Optional.of(this.fileChooser.getSelectedFile().getAbsolutePath());
	    } else {
	    	return Optional.empty();
	    }
	}
}