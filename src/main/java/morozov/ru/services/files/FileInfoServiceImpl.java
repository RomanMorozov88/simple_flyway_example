package morozov.ru.services.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import morozov.ru.models.FileInfo;

@Service
public class FileInfoServiceImpl implements FileInfoService {
	
	private FileInfoDao fileInfoDao;
	private PlatformTransactionManager transactionManager;
	private final String filesLocation = "resource/files";
	
	@Autowired
	public FileInfoServiceImpl(FileInfoDao fileInfoDao, PlatformTransactionManager transactionManager) {
		super();
		this.fileInfoDao = fileInfoDao;
		this.transactionManager = transactionManager;
	}

	@SuppressWarnings("finally")
	@Override
	public int saveFileInfo(FileInfo fileInfo, MultipartFile firstFile, MultipartFile secondFile) {
		Integer idResult = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	    	  Path location = Paths.get(filesLocation);
	          this.checkFolder(location.toString());
	          String filePath = this.saveFile(firstFile, location);
	            if (filePath != null) {
	                fileInfo.setFirstFilePath(filePath);
	            }
	            filePath = this.saveFile(secondFile, location);
	            if (filePath != null) {
	                fileInfo.setSecondFilePath(filePath);
	            }
	    	  idResult = fileInfoDao.save(fileInfo);
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          idResult = -101;
	          transactionManager.rollback(status);
	      } finally {
	    	  return idResult;
	      }
	}

	@Override
	public FileInfo getFileInfoById(int idFileInfo) {
		return fileInfoDao.getFileInfoById(idFileInfo);
	}

	@Override
	public List<FileInfo> getFileInfos() {
		return fileInfoDao.getFileInfos();
	}

	@Override
	public void deleteFileInfo(int idFileInfo) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
		try {
			FileInfo fileInfo = fileInfoDao.getFileInfoById(idFileInfo);
			this.deleteFile(fileInfo.getFirstFilePath());
			this.deleteFile(fileInfo.getSecondFilePath());
			fileInfoDao.deleteFileInfo(idFileInfo);
			transactionManager.commit(status);
		} catch (IOException e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}
		
	}
	
	private void checkFolder(String pathFolder) {
        File folder = new File(pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
	
	private String saveFile(MultipartFile file, Path location) throws Exception {
        String result = null;
        if (file != null && !file.isEmpty()) {
            result = this.storeFile(file, location);
        }
        return result;
    }
	
	private String storeFile(
            MultipartFile file,
            Path location
    ) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            throw new Exception("Failed to store empty file " + fileName);
        }
        if (fileName.contains("..")) {
            throw new Exception(
                    "Cannot store file with relative path outside current directory "
                            + fileName);
        }
        Path path = location.resolve(fileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return path.toString();
    }
	
	private void deleteFile(String path) throws IOException {
        Files.delete(Paths.get(path));
    }

}
