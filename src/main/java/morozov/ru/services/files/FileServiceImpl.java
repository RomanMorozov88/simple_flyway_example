package morozov.ru.services.files;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import morozov.ru.models.FileInfo;

public class FileServiceImpl implements FileService {

	@Override
	public int saveBanner(FileInfo fileInfo, MultipartFile firstFile, MultipartFile secondFile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FileInfo getFileInfoById(int idFileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileInfo> getFileInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFileInfo(int idFileInfo) {
		// TODO Auto-generated method stub
		
	}

}
