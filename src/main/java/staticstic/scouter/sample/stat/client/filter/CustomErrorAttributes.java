package staticstic.scouter.sample.stat.client.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import lombok.extern.log4j.Log4j2;


/**
 * <pre>
 * Exception 예외 처리
 */
@Log4j2
@Component
public class CustomErrorAttributes<T extends Throwable> extends DefaultErrorAttributes {
	
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest,
			boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new HashMap<String, Object>();
		Throwable e = getError(webRequest);

		log.error("LK={}, errmsg={}, url={}, attributes={}", 
				webRequest.getHeader("logkey")!=null?webRequest.getHeader("logkey"):"",
				e.getMessage(), webRequest.getContextPath(), webRequest.getAttributeNames(10));
		
		HttpStatus errorStatus = determineHttpStatus(e);
		errorAttributes.put("rslt", "9000");
		errorAttributes.put("rslt_desc", "기타오류, S="+errorStatus.value()+", M="+e.getMessage());
		
		return errorAttributes;
	}
	
	private HttpStatus determineHttpStatus(Throwable error) {
		if (error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getStatus();
		}
		else if (error instanceof ServerWebInputException) {
			return ((ServerWebInputException) error).getStatus();
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}