package staticstic.scouter.sample.stat.client.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SmchoUtil {
	
    public static Properties loadYamlIntoProperties(String clazzFile) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new ClassPathResource(clazzFile));
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
	
	
	public static Map<String, String> getParams(Enumeration<String> paramName, Map<String, String[]> paramMap) {
		HashMap result = new HashMap();

		while (paramName.hasMoreElements()) {
			String pName = (String) paramName.nextElement();
			result.put(pName, ((String[]) paramMap.get(pName))[0]);
		}

		return result;
	}
	
	// String, Array, Collection, Map
	public static boolean isNullOrEmpty(Object obj) {
		return ObjectUtils.isEmpty(obj);
	}
	
	public static String nullToDefault(String str, String def) {
		if (isNullOrEmpty(str)) return def;
		else return str;
	}
	
	public static String nvlchk(String s) {
		if (isNullOrEmpty(s)) return "";
		else return s;
	}

	public static Long nvlchk(Long l) {
		if (isNullOrEmpty(l)) return 0L;
		else return l;
	}

	public static Integer nvlchk(Integer i) {
		if (isNullOrEmpty(i)) return 0;
		else return i;
	}

	public static String nvlchk(String s, String s2) {
		if (isNullOrEmpty(s)) return s2;
		else return s;
	}

	public static Long nvlchk(Long l, Long l2) {
		if (isNullOrEmpty(l)) return l2;
		else return l;
	}

	public static Integer nvlchk(Integer i, Integer i2) {
		if (isNullOrEmpty(i)) return i2;
		else return i;
	}

	public static byte[] serialize(Object obj) {
		if (isNullOrEmpty(obj)) return null;
		return SerializationUtils.serialize(obj);
	}
	
	public static Object deserialize(byte[] bytes) {
		if (isNullOrEmpty(bytes)) return null;
		return SerializationUtils.deserialize(bytes);
	}
	
	public static Object deepCopy(Object obj) {
		return deserialize(serialize(obj));
	}
	
	public static String matchTxt(String txt, String regex) {
		if (isNullOrEmpty(txt)) return null;
		if (isNullOrEmpty(regex)) return null;
		
		Matcher m = Pattern.compile(regex).matcher(txt);
		while (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	public static boolean isDigit(String digit) {
		if (digit == null) {
			return false;
		}
		char chars[] = digit.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}

		return true;
	}
	
	public static long toMilli(String timeStamp) {
		String[] df = new String[] {
				"EEE, dd MMM yyyy HH:mm:ss Z", 
				"EEE, dd MMM yyyy HH:mm:ss", 
				"EEE, MMM dd HH:mm:ss z yyyy", 
				"yyyy-MM-dd HH:mm:ss:SSS +0900", 
				"yyyy-MM-dd HH:mm:ss.SSS", 
				"yyyy-MM-dd HH:mm:ss", 
				"yyyy/MM/dd'T'HH:mm:ss", 
				"yyyy-MM-dd'T'HH:mm:ss",
				"yyyy-MM-dd'T'HH:mm:ss'+09:00'",
				"yyyy/MM/dd'T'HH:mm:ss'+09:00'",
				"yyyyMMddHHmmss", 
			}; 

		try {
//			Util.llog("s={}", s);
			if (timeStamp == null) return -2;
			return DateUtils.parseDate(timeStamp, Locale.ENGLISH, df).getTime();
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static String encPhone( String orgPhone )   
	{
		String phone = getNumbers(orgPhone);
		String encPhone = phone;
		int len = phone.length();
		
		if (len > 8) {
			encPhone = StringUtils.overlay(phone, "****", len-8, len-4);
		}
		else if (len > 4) {
			encPhone = StringUtils.overlay(phone, "****", 0, 4);
		}
//		Util.llog("{} -> {} -> {}", orgPhone, phone, encPhone);

		return encPhone;
	}
	
	public static String getNumbers(String data) {
		if (data == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < data.length(); ++i) {
				if (Character.isDigit(data.charAt(i))) {
					sb.append(data.charAt(i));
				}
			}

			return sb.toString();
		}
	}
	
	public static String getRemoteIpAddr(HttpServletRequest request) {
    	
		String ip = "";
		if (SmchoUtil.isNullOrEmpty(request.getRemoteAddr()) == false) {
			ip = request.getRemoteAddr();
		}
		
		Enumeration<String> xForwardedValues = request.getHeaders("X-Forwarded-For");
		if (SmchoUtil.isNullOrEmpty(xForwardedValues)) {
			return ip;
		}
		
		while(xForwardedValues.hasMoreElements()) {
			String curip = xForwardedValues.nextElement();
			if (xForwardedValues.hasMoreElements()) {
				log.warn("Multiple X-Forwarded-For headers found, discarding all");
				return ip;
			}
			ip = curip;
		}

		return ip;
	}
	
	
	public static String getUri(Map<String, String> msg) {
		String rtnVal = "";
		Set<String> keys = msg.keySet();
		for(String key : keys) {
			Object obj=msg.get(key);
			if ("".equals(rtnVal)==false) rtnVal += "&";
			rtnVal += key+"="+obj.toString();
		}
		return rtnVal;
	}
	
//	public static void reqStopNWait(List<Statex> chkLst) {
//		if (chkLst == null || chkLst.size() <= 0) return;
//		
//		for(Statex one : chkLst) {
//			if(one != null && one.getStatus() != Statex.STOP && one.getStatus() != Statex.REQUEST_STOP)
//				one.setStatus(Statex.REQUEST_STOP);
//		}
//
//		boolean isAllStop = false;
//		long chkStartDT = System.currentTimeMillis();
//		while(isAllStop==false) {
//			log.info("Shutdown waiting.. {}", (System.currentTimeMillis()-chkStartDT)/1000);
//			Util.sleep(1000);
//			
//			// 20초동안 종료되지 않으면 포기
//			if (System.currentTimeMillis()-chkStartDT > 20000) break;
//			
//			// 종료 체크
//			for(Statex one : chkLst) {
//				if (one != null && one.getStatus() != Statex.STOP) isAllStop = false;
//			}
//			if (isAllStop == false) continue;
//			
//			log.info("Module is all shutdown.");
//		}
//	}
//	
//	public static boolean isRunning( Statex statex, long sleepTimeMilli, long hangTimeMilli) {
//
//		if ( statex == null || statex.getStatus() == Statex.STOP )  return false;
//		
//		if (statex.getStatus() != Statex.NORMAL) return true;
//		
//		if (System.currentTimeMillis() - statex.getLastLoopTime() > hangTimeMilli) {
//			log.error("processs hang. NM={}. status change to stop", statex.getName());
//			statex.setStatus(Statex.STOP);
//			return false;
//		}
//		
//		if (sleepTimeMilli > 0 && System.currentTimeMillis() - statex.getLastLoopTime() > sleepTimeMilli) {
//			log.warn("{} is sleep too long.. requestStop", statex.getName());
//			statex.setStatus(Statex.REQUEST_STOP);
//			return false;
//		}
//		
//		log.debug("{} is only one running.", statex.getName());
//		return true;
//	}
//	
//	public static boolean isRunning( Statex statex) {
//		return isRunning(statex, 15000, 60000);
//	}
//
//	public static void traceRequest(String uri, String method, String params, HttpHeaders httpHeaders) { 
//		StringBuilder reqLog = new StringBuilder(); 
//		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
//			.append(", Method : ").append(method)
//			.append(", Headers : ").append(httpHeaders.toString()); 
//		log.debug(reqLog.toString());
//		
//		reqLog.setLength(0);
//		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
//			.append(", Method : ").append(method)
//			.append(", Params : ").append(params); 
//		log.info(reqLog.toString()); 
//
//	}
	
	public static void traceRequest(String uri, String method, String key, String params, HttpHeaders httpHeaders) { 
		StringBuilder reqLog = new StringBuilder(); 
		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
			.append(", Method : ").append(method)
			.append(", ").append(key)
			.append(", Headers : ").append(httpHeaders.toString()); 
		log.debug(reqLog.toString()); 

		reqLog.setLength(0);
		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
			.append(", Method : ").append(method)
			.append(", ").append(key)
			.append(", Params : ").append(params); 
		log.info(reqLog.toString()); 
	}
	
	public static void traceRequest(String uri, String method, HttpEntity<?> httpEntity) { 
		StringBuilder reqLog = new StringBuilder(); 
		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
			.append(", Method : ").append(method)
			.append(", HttpEntity : ").append(httpEntity.toString()); 
		log.debug(reqLog.toString()); 

		reqLog.setLength(0);
		reqLog.append("[REQUEST] ") .append("Uri : ").append(uri)
			.append(", Method : ").append(method)
			.append(", HttpEntity : ").append(httpEntity.toString()); 
		log.info(reqLog.toString()); 
	}
	
	public static void traceResponse(String uri, ResponseEntity<String> entity) {
		try {
			StringBuilder resLog = new StringBuilder(); 
			resLog.append("[RESPONSE] ")
				.append("Uri : ").append(uri)
				.append(", Response Body : ").append(entity.getBody()); 
			log.info(resLog.toString()); 
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}
	
	public static void traceResponse(String uri, String key, ResponseEntity<String> entity) { 
		try {
			StringBuilder resLog = new StringBuilder(); 
			resLog.append("[RESPONSE] "+key+" ")
				.append("Uri : ").append(uri)
				.append(", Response Body : ").append(entity.getBody()); 
			log.info(resLog.toString()); 
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}
	
	public static ResponseEntity<String> traceExchangeRestTemplate(RestTemplate restTemplate, String url, HttpMethod method,
			@Nullable HttpEntity<?> httpEntity) throws RestClientException { 
		
		SmchoUtil.traceRequest(url, method.name(), httpEntity);
		ResponseEntity<String> entity = restTemplate.exchange(url, method, httpEntity, String.class);
		SmchoUtil.traceResponse(url, entity);
		return entity;
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
	
	public static boolean isValidUrl( String url, String[] urlLst )   
	{
		boolean isOk = false;
		for (int i=0; i < urlLst.length; i++) {
			String p = urlLst[i].trim().replaceAll("\\*", ".*");
			if ("".equals(p)) continue;
			if ( url.matches( p ) == true ) { isOk = true; break; } 
		}
		
		return isOk;
	}
	
	public static void setStatus(HttpServletResponse response, HttpStatus status) {
		try {
			response.sendError(status.value(), status.getReasonPhrase());
		} catch (IOException e) {
			log.error("status={}, err={}", status, e.getMessage(), e);
		}
	}
	
	public static void setStatus(HttpServletResponse response, HttpStatus status, String msg) {
		try {
			response.sendError(status.value(), msg);
		} catch (IOException e) {
			log.error("status={}, err={}", status, e.getMessage(), e);
		}
	}
	
	
	public static String base64Decode(String encStr) {
		String rslt = null;
		
		try {
			boolean isBase64 = Base64.isBase64(encStr);
			if (isBase64) {
				byte[] bytes = Base64.decodeBase64(encStr);
				rslt = new String(bytes);
			} else {
				rslt = encStr;
			}
		} catch (Exception e) {
			log.error("base64decode error. err={}, encStr={}", e.getMessage(), encStr);
		}
		
		return rslt;
	}
}
