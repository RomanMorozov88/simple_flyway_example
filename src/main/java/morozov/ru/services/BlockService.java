package morozov.ru.services;

import java.util.List;

import morozov.ru.models.Block;

public interface BlockService {
	
	public Integer saveBlock(Block block);
	
	public void updateBlock(Block block);
	
	public void deleteBlock(int idBlock);
	
	public List<Block> getBlocksByIdPack(int idPack);

}
