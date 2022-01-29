package morozov.ru;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import morozov.ru.services.files.FileStoreService;

@Component
public class AppInit {
	
	private FileStoreService fileStoreService;

	@Autowired
	public AppInit(FileStoreService fileStoreService) {
		super();
		this.fileStoreService = fileStoreService;
	}
	
	@PostConstruct
    public void deleteFileFolder() {
        fileStoreService.deleteFileFolder();
    }

}
