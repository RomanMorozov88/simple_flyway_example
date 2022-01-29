package morozov.ru.services.files;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import morozov.ru.models.FileInfo;

@Service
public class FileInfoServiceImpl implements FileInfoService {
	
	private FileStoreService fileStoreService;
	private FileInfoDao fileInfoDao;
	
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public FileInfoServiceImpl(
			FileStoreService fileStoreService,
			FileInfoDao fileInfoDao, 
			PlatformTransactionManager transactionManager
			) {
		this.fileStoreService = fileStoreService;
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
	    	  fileInfo.setFirstFilePath(fileStoreService.saveFile(firstFile));
	    	  fileInfo.setSecondFilePath(fileStoreService.saveFile(secondFile));
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
			fileStoreService.deleteFile(fileInfo.getFirstFilePath());
			fileStoreService.deleteFile(fileInfo.getSecondFilePath());
			fileInfoDao.deleteFileInfo(idFileInfo);
			transactionManager.commit(status);
		} catch (IOException e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}
		
	}

}
