package morozov.ru.services.packs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import morozov.ru.models.Block;
import morozov.ru.models.LocalDateBlock;
import morozov.ru.models.Pack;
import morozov.ru.models.TextBlock;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PackServiceImplTest {
	
	@Autowired
	private PackService packService;
	@MockBean
	private PackDao packDao;
	@MockBean(name = "without_transactional")
	private BlockService blockService;
	@MockBean
	private PlatformTransactionManager transactionManager;
	
	@Test
    public void savePack() {
		Pack pack = this.createPack();
		
		Mockito.doReturn(1)
        .when(packDao)
        .savePack(pack);
		
		Mockito.doReturn(null)
        .when(transactionManager)
        .getTransaction(ArgumentMatchers.any(DefaultTransactionDefinition.class));
		
		Integer result = packService.savePack(pack);
		
		Mockito.verify(packDao, Mockito.times(1))
		.savePack(ArgumentMatchers.any(Pack.class));
		Mockito.verify(blockService, Mockito.times(2))
		.saveBlock(ArgumentMatchers.any(Block.class));
		Mockito.verify(transactionManager, Mockito.times(1))
		.commit(null);
		
		assertEquals(Integer.valueOf(1), result);
    }
	
	private Pack createPack() {
		Pack pack = new Pack();
		Block fBlock = new TextBlock("TB-Name", 1, "Test Text.");
		Block sBlock = new LocalDateBlock(
				"LDB-name", 2, LocalDate.now(), LocalDate.now().plusDays(10)
				);
		pack.setBlocks(Arrays.asList(fBlock, sBlock));
		return pack;
	}

}
