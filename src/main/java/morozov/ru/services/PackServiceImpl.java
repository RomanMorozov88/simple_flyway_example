package morozov.ru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import morozov.ru.models.Block;
import morozov.ru.models.Pack;

@Service
public class PackServiceImpl implements PackService {
	
	private PackDao packDao;
	private BlockService blockService;
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public PackServiceImpl(
			PackDao packDao, 
			@Qualifier ("without_transactional") BlockService blockService, 
			PlatformTransactionManager transactionManager
			) {
		super();
		this.packDao = packDao;
		this.blockService = blockService;
		this.transactionManager = transactionManager;
	}

	@SuppressWarnings("finally")
	@Override
	public Integer savePack(Pack pack) {
		Integer idPack = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	          idPack = packDao.savePack(pack);
	          this.saveBlocks(idPack, pack.getBlocks());
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          idPack = -101;
	          transactionManager.rollback(status);
	      } finally {
	    	  return idPack;
	      }
	}
	
	private void saveBlocks(int idPack, List<Block> blocks) {
		for (Block b : blocks) {
			b.setIdPack(idPack);
			blockService.saveBlock(b);
		}
	}

	@Override
	public void updatePack(Pack pack) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	          packDao.updatePack(pack);
	          this.updateBlocks(pack.getId(), pack.getBlocks());
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          transactionManager.rollback(status);
	      }
	}
	
	private void updateBlocks(int idPack, List<Block> blocks) {
		for (Block b : blocks) {
			if (b.getId() > 0) {
				blockService.updateBlock(b);
			} else if (b.getId() == 0) {
				b.setIdPack(idPack);
				blockService.saveBlock(b);
			}
		}
	}

	@Override
	public void deletePack(int idPack) {
		packDao.deletePack(idPack);
	}

	@SuppressWarnings("finally")
	@Override
	public Pack getPack(int idPack) {
		Pack result = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	          result = packDao.getPackById(idPack);
	          result.setBlocks(blockService.getBlocksByIdPack(idPack));
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          result = null;
	          transactionManager.rollback(status);
	      } finally {
	    	  return result;
	      }
	}

	@SuppressWarnings("finally")
	@Override
	public List<Pack> getPacks() {
		List<Pack> resultList = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	          resultList = packDao.getPacks();
	          for (Pack p : resultList) {
	        	  p.setBlocks(blockService.getBlocksByIdPack(p.getId()));
	          }
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          resultList = null;
	          transactionManager.rollback(status);
	      } finally {
	    	  return resultList;
	      }
	}
	
	
	
}
