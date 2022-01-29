package morozov.ru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import morozov.ru.models.FileInfo;
import morozov.ru.services.files.FileInfoService;

@Controller
@RequestMapping("files")
public class FileInfoController {

	private static final String VIEW = "files";

	private FileInfoService fileInfoService;

	@Autowired
	public FileInfoController(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		modelAndView.addObject("fileInfos", fileInfoService.getFileInfos());
		return modelAndView;
	}

	@PostMapping("/saveFiles")
	public ResponseEntity<?> saveBanner(
			@RequestPart("fileInfo") FileInfo fileInfo,
			@RequestPart("firstFile") MultipartFile firstFile, 
			@RequestPart("secondFile") MultipartFile secondFile
			) {
		int idBanner = fileInfoService.saveFileInfo(fileInfo, firstFile, secondFile);
		return ResponseEntity.ok().body("{\"idBanner\":\"" + idBanner + "\"}");
	}

	@GetMapping("/getInfos")
	public ResponseEntity<?> getInfos() {
		return ResponseEntity.ok().body(fileInfoService.getFileInfos());
	}

	@GetMapping("/getInfos/{idFileInfo}")
	public ResponseEntity<?> getInfosById(
			@PathVariable int idFileInfo
			) {
		return ResponseEntity.ok().body(fileInfoService.getFileInfoById(idFileInfo));
	}

	@GetMapping("/deleteInfo/{idFileInfo}")
	public ResponseEntity<?> deleteInfo(
			@PathVariable int idFileInfo
			) {
		fileInfoService.deleteFileInfo(idFileInfo);
		return ResponseEntity.ok().body("{\"code\":\"success\"}");
	}

}
