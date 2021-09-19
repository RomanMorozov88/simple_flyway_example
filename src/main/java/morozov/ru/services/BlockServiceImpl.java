package morozov.ru.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import morozov.ru.enums.TypeEnum;
import morozov.ru.models.Block;
import morozov.ru.models.LocalDateBlock;
import morozov.ru.models.TextBlock;

/**
 * Methods from this servise are callint from PackService 
 * and have not transaction wrapper - to avoid mistakes and conflicts 
 * with transactions commits
 * @author morozov
 *
 */
@Service("without_transactional")
public class BlockServiceImpl implements BlockService {
	
	private BlockDao blockDao;
	
	@Autowired
	public BlockServiceImpl(BlockDao blockDao) {
		this.blockDao = blockDao;
	}

	@Override
	public Integer saveBlock(Block block) {
		Integer idBlock = null;
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
		return idBlock;
	}
	
	@Override
	public void updateBlock(Block block) {
		blockDao.updateBlock(block);
		this.updateTypedBlock(block);
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
		return this.combineBlocks(blockDao.getBlocksByIdPack(idPack));
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
			LocalDate ld = ((Date) rawBlock.get("first_date")).toLocalDate();
			((LocalDateBlock) result).setFirstDate(ld);
			ld = ((Date) rawBlock.get("second_date")).toLocalDate();
			((LocalDateBlock) result).setSecondDate(ld);
			result.setTypeCode(typeCode);
		}
		return result;
	}

}
