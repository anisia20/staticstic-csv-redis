package staticstic.scouter.sample.stat.client.utils;

import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.code.ResultCode;
import staticstic.scouter.sample.stat.client.model.vo.Result;


@Log4j2
@Configuration
public class ValidUtil {
	
	static Validator validator;
	
    @Bean
    public static void getValidator() {
    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	public static boolean isValid(Object source, Result result) {
		Set<ConstraintViolation<Object>> violations = validator.validate(source);
		for (ConstraintViolation<Object> violation : violations) {
			result.onFail(ResultCode.getResultCd(violation.getMessage()));
			return false;
		}
		return true;
	}

	public static boolean isValidChar(String regexp, String col) {
		try {
			if (SmchoUtil.isNullOrEmpty(col)) return false;
 			return (Pattern.compile(regexp).matcher(col).find());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}


	public static boolean isValidByteSize(int maxSize, String col) {
		try {
			if (SmchoUtil.isNullOrEmpty(col)) return false;
			return (col.getBytes().length <= maxSize);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public static boolean isValidIPAddr( String ip, String[] pattern )   
	{
		boolean isIpOk = false;
		for (int i=0; i < pattern.length; i++) {
			String p = pattern[i].trim().replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
			if ("".equals(p)) continue;
			if ( ip.matches( p ) == true ) { isIpOk = true; break; } 
		}
		
		return isIpOk;
	}
	
	public static boolean isValidExt( String ext, String[] pattern )   
	{
		boolean isOk = false;
		for (int i=0; i < pattern.length; i++) {
			String p = pattern[i].trim().replaceAll("\\*", ".*");
			if ("".equals(p)) continue;
			if ( ext.matches( p ) == true ) { isOk = true; break; } 
		}
		
		return isOk;
	}
	

}
