package AppPackage;

import java.io.File;

import javax.swing.JFileChooser;

public class SelectVideo {

	JFileChooser fc = new JFileChooser();
	private String f;
	
	public void PickFile() throws Exception {
		File dir = new File ("C:/");
		fc.setCurrentDirectory(dir);
		fc.setDialogTitle("Choose Video File");
		if (fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			String file = fc.getSelectedFile().toPath().toUri().toString();
			setFile (file);
		}
	}
	
	private void setFile (String file) {
		this.f = file;
	}
	
	public String getFile () {
		return this.f;
	}
	
	
}
