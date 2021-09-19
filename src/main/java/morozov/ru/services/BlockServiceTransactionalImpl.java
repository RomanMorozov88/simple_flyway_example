package morozov.ru.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import morozov.ru.enums.TypeEnum;
import morozov.ru.models.Block;
import morozov.ru.models.LocalDateBlock;
import morozov.ru.models.TextBlock;

@Service("transactional")
public class BlockServiceTransactionalImpl implements BlockService {

	private BlockDao blockDao;
	private PlatformTransactionManager transactionManager;

	@Autowired
	public BlockServiceTransactionalImpl(
			BlockDao blockDao, 
			PlatformTransactionManager transactionManager
			) {
		this.blockDao = blockDao;
		this.transactionManager = transactionManager;
	}
	
	@Override
	public Integer saveBlock(Block block) {
		Integer idBlock = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	  		if (block.getClass() == TextBlock.class) {
	  			block.setTypeCode(TypeEnum.TEXT_BLOCK.getCode());
	  			idBlock = blockDao.saveBlock(block);
	  			block.setId(idBlock);
	  			blockDao.saveTextBlock((TextBlock) block);
	  		} else if (block.getClass() == LocalDateBlock.class) {
	  			block.setTypeCode(TypeEnum.LOCAL_DATE_BLOCK.getCode());
	  			idBlock = blockDao.saveBlock(block);
	  			block.setId(idBlock);
	  			blockDao.saveLocalDateBlock((LocalDateBlock) block);
	  		}
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          transactionManager.rollback(status);
	      }
	      return idBlock;
	}

	@Override
	public void updateBlock(Block block) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	      def.setReadOnly(false);
	      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
	      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	      TransactionStatus status = transactionManager.getTransaction(def);
	      try {
	    	  blockDao.updateBlock(block);
	    	  this.updateTypedBlock(block);
	          transactionManager.commit(status);
	      } catch (Exception e) {
	          e.printStackTrace();
	          transactionManager.rollback(status);
	      }
	}
	
	private void updateTypedBlock(Block block) throws ClassCastException {
		if (block.getTypeCode() == TypeEnum.TEXT_BLOCK.getCode()) {
			blockDao.updateTextBlock((TextBlock) block);
		} else if (block.getTypeCode() == TypeEnum.LOCAL_DATE_BLOCK.getCode()) {
			blockDao.updateLocalDateBlock((LocalDateBlock) block);
		}
	}

	@Override
	public void deleteBlock(int idBlock) {
		blockDao.deleteBlock(idBlock);
	}

	@Override
	public List<Block> getBlocksByIdPack(int idPack) {
		List<Block> result = null;
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setReadOnly(false);
      def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus status = transactionManager.getTransaction(def);
      try {
          result = this.combineBlocks(blockDao.getBlocksByIdPack(idPack));
          transactionManager.commit(status);
      } catch (Exception e) {
          e.printStackTrace();
          transactionManager.rollback(status);
      }
		return result;
	}
	
	private List<Block> combineBlocks(List<Map<String, Object>>rawBlocks) {
		List<Block> result = new ArrayList<>();
		for (Map<String, Object> rawBlock : rawBlocks) {
            result.add(this.combineBlock(rawBlock));
        }
		return result;
	}
	
	private Block combineBlock(Map<String, Object> rawBlock) {
		Block result = null;
		Integer typeCode = (Integer) rawBlock.get("type_code");
		if (typeCode == TypeEnum.TEXT_BLOCK.getCode()) {
			result = new TextBlock();
			result.setId((Integer) rawBlock.get("id"));
			result.setIdPack((Integer) rawBlock.get("id_pack"));
			result.setName((String) rawBlock.get("name"));
			((TextBlock) result).setText((String) rawBlock.get("text"));
			result.setTypeCode(typeCode);
		} else if (typeCode == TypeEnum.LOCAL_DATE_BLOCK.getCode()) {
			result = new LocalDateBlock();
			result.setId((Integer) rawBlock.get("id"));
			result.setIdPack((Integer) rawBlock.get("id_pack"));
			result.setName((String) rawBlock.get("name"));
			((LocalDateBlock) result).setFirstDate((LocalDate) rawBlock.get("first_date"));
			((LocalDateBlock) result).setSecondDate((LocalDate) rawBlock.get("second_date"));
			result.setTypeCode(typeCode);
		}
		return result;
	}

}
