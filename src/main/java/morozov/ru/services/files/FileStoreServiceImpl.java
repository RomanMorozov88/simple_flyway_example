package morozov.ru.services.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStoreServiceImpl implements FileStoreService {
	
	private static final String FILESLOCATION = "files";

	@Override
	public String saveFile(MultipartFile file) 
			throws IOException {
		String resultFileName = null;
		if (file != null) {
			File uploadFolder = new File(FILESLOCATION);
			if (!uploadFolder.exists()) {
				uploadFolder.mkdir();
			}
			resultFileName = 
					uploadFolder.getAbsolutePath()
					+ "/"
					+ UUID.randomUUID().toString() 
					+ "." 
					+ file.getOriginalFilename();
			file.transferTo(new File(resultFileName));
		}
		return resultFileName;
	}

	@Override
	public void deleteFile(String filePath) throws IOException {
		Files.delete(Paths.get(filePath));
	}
	
	@Override
	public void deleteFileFolder() {
		File uploadFolder = new File(FILESLOCATION);
		if (uploadFolder.exists()) {
			File[] files = uploadFolder.listFiles();
			for (File f : files) {
				f.delete();
			}
		}
	}

}
