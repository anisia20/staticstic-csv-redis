package staticstic.scouter.sample.stat.client.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.service.ApiService;

@Log4j2
@Controller 
@RequestMapping("/front/scouter")
public class ScouterController {

	/**
	 * 리소스 매니저
	 */
	@Autowired
	ResourcesManager mResourcesManager;
	
	/**
	 * api 처리 서비스
	 */
	@Autowired
	ApiService mApiService;
	
	
	@GetMapping("uploadcsv")
	public String uploadcsv(
			HttpServletRequest request, 
			HttpServletResponse response
		) throws IOException
	{
		return "contents/scouter/uploadcsv";
	}
	
	/**
	 * 
	 * 파일 선택 서비스 
	 * @throws IOException 
	 * @throws Exception 
	 */
	
	@PostMapping("/file/upload")
	//public String FileUploadController(@RequestParam("file") MultipartFile file) throws Exception {
	public ResponseEntity FileUploadController( HttpServletRequest req, HttpServletResponse res) throws IOException {	
	
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;

        Map<String, MultipartFile> files = multiRequest.getFileMap();
		
        int n_res = mApiService.TrnsInfo2Redis(files);
		
        ResponseEntity result = null;
		if( n_res < 0) result = new ResponseEntity(HttpStatus.BAD_REQUEST);
        
		return result;
	}
	
}
