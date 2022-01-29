package morozov.ru.services.files;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {
	
	String saveFile(MultipartFile file) throws IOException;
	void deleteFile(String filePath) throws IOException;
	void deleteFileFolder();

}
