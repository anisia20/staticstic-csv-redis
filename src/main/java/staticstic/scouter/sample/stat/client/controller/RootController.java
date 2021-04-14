package staticstic.scouter.sample.stat.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
// RestController는 JSP 연동 안됨
@Controller
@RequestMapping("/")
@ConditionalOnProperty(name = "test", havingValue = "true", matchIfMissing = false)
public class RootController {
	
	@GetMapping("")
	public String statistic(
			HttpServletRequest request, 
			HttpServletResponse response
		) throws IOException
	{
		return "index";
	}
	
	
	@ApiOperation("API 스팩조회")
	@GetMapping("swagger")
	public void doSwagger(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{ 
		log.info("{} call, parameters={}", request.getRequestURI(), request.getQueryString());
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("/swagger-ui.html#/");
	}

}
