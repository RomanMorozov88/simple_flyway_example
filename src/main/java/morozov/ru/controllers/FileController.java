package morozov.ru.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import morozov.ru.models.FileInfo;

public class FileController {
	
	 @PostMapping("/saveFiles")
	    public ResponseEntity<?> saveBanner(
	            @RequestPart("fileInfo") FileInfo fileInfo,
	            @RequestPart("firstFile") MultipartFile firstFile,
	            @RequestPart("secondFile") MultipartFile msecondFile
	    ) {
	        int idBanner = 0;
	        return ResponseEntity.ok().body("{\"idBanner\":\"" + idBanner + "\"}");
	    }

}
