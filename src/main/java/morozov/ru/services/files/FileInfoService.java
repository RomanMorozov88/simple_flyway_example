package morozov.ru.services.files;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import morozov.ru.models.FileInfo;

public interface FileInfoService {
	
	int saveFileInfo(FileInfo fileInfo, MultipartFile firstFile, MultipartFile secondFile);
	
	FileInfo getFileInfoById(int idFileInfo);
	
	List<FileInfo> getFileInfos();
	
	void deleteFileInfo(int idFileInfo);

}
