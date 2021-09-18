package morozov.ru.services;

import java.util.List;

import morozov.ru.models.Pack;

public interface PackService {
	
public Integer savePack(Pack pack);
	
	public void updatePack(Pack pack);
	
	public void deletePack(int idPack);
	
	public Pack getPack(int idPack);
	
	public List<Pack> getPacks();

}
