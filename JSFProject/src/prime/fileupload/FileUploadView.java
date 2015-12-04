/**
 * test
 */
package prime.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadView")
@RequestScoped
public class FileUploadView {

	UploadedFile file;

	public UploadedFile getFile() {
		return this.file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		FacesMessage message = null;
		if (this.file != null) {
			File fileToWrite = new File("C:/Users/PC/Downloads/" + this.file.getFileName());
			try (FileOutputStream fileOutputStream = new FileOutputStream(fileToWrite)){
				byte[] contents = this.file.getContents();
				fileOutputStream.write(contents);
				fileOutputStream.flush();
			} catch (IOException e) {
				FacesException fEx = new FacesException(e);
				throw fEx;
			}
			message = new FacesMessage("Successful", this.file.getFileName() + " is uploaded.");
		} else {
			message = new FacesMessage("Unsuccessful", "this file is not uploaded.");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void upload2(FileUploadEvent e) {
		FacesMessage message = null;
		UploadedFile file = e.getFile();
		if (file != null) {
			File fileToWrite = new File("C:/Users/PC/Downloads/" + file.getFileName());
			try (FileOutputStream fileOutputStream = new FileOutputStream(fileToWrite)) {
				byte[] contents = file.getContents();
				fileOutputStream.write(contents);
				fileOutputStream.flush();
			} catch (IOException ex) {
				FacesException fEx = new FacesException(ex);
				throw fEx;
			}
			message = new FacesMessage("Successful", this.file.getFileName() + " is uploaded.");
		} else {
			message = new FacesMessage("Unsuccessful", "this file is not uploaded.");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
