package morozov.ru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import morozov.ru.models.Pack;
import morozov.ru.services.PackService;

@Controller
@RequestMapping("packs")
public class PackController {
	
	private PackService packService;

	@Autowired
	public PackController(PackService packService) {
		this.packService = packService;
	}
	
	@PostMapping("/savePack")
    public ResponseEntity<?> savePack(
            @RequestBody Pack pack
    ) {
		Integer id = packService.savePack(pack);
        return ResponseEntity.ok().body(id);
    }
	
	@PostMapping("/updatePack")
    public ResponseEntity<?> updatePack(
            @RequestBody Pack pack
    ) {
		int id = pack.getId();
		if (id > 0) {
			packService.updatePack(pack);
		}
        return ResponseEntity.ok().body(id);
    }
	
	@GetMapping("/getPacks")
    public ResponseEntity<?> getPacks() {
        return ResponseEntity.ok().body(packService.getPacks());
    }
	
	@GetMapping("/getPack/{idPack}")
    public ResponseEntity<?> getPacks(
    		@PathVariable int idPack
    		) {
        return ResponseEntity.ok().body(packService.getPack(idPack));
    }
	
	@GetMapping("/deletePack/{idPack}")
    public void deletePacks(
    		@PathVariable int idPack
    		) {
        packService.deletePack(idPack);
    }

}
