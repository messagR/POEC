package prime.filedownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "fileDownloadView")
@RequestScoped
public class FileDownloadView {

	private StreamedContent file;

	public FileDownloadView() throws FileNotFoundException {
		InputStream stream = new FileInputStream(new File("C:/Users/PC/Downloads/homme.jpg"));
		this.file = new DefaultStreamedContent(stream, "image/jpg", "monImage.jpg");
	}

	public StreamedContent getFile() {
		return this.file;
	}

}
