package staticstic.scouter.sample.stat.client.code;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @Date    : 2019. 3. 7.
 * @Version : 1.0
 */
@Getter
@AllArgsConstructor
@ToString
public enum ResultCode {
	
	/** 
	 * 코드 설명
	 * 
	 *         내부코드   코드타입               내부코드 설명                                                         클라이언트 코드 설명
	 * R_2000("2000", "ETC",		"기타에러", 									"기타에러"),
	 */
	
	
	/** 
	 * 10XX 성공
	 */
	R_1000("1000", "COMMON",	"성공", 										"성공"),
	/** Fallback 성공 **/
	R_1001("1001", "FB",		"Fallback 성공", 								"Fallback 성공"),


	/** 
	 * 2000 ~ 2799 서버 에러 
	 * 28xx : 연동에러
	 * 29xx : 내부에러
	 */
	/** ETC **/						R_2900("2900", "ETC",		    "기타에러", 									"기타에러"),
	
	/** 파라미터 오류(dstrNm) **/		R_2101("2101", "API_VALID",		"파라미터 오류(dstrNm)", 						"파라미터 오류(dstrNm)"),
	/** 파라미터 오류(sendTime) **/		R_2102("2102", "API_VALID",		"파라미터 오류(sendTime)", 						"파라미터 오류(sendTime)"),
	/** 파라미터 오류(chId) **/			R_2103("2103", "API_VALID",		"파라미터 오류(chId)", 							"파라미터 오류(chId)"),
	
	/** 발송시간 초과 **/				R_2911("2911", "AGENT",		    "발송시간 초과",								"발송시간 초과"),
	/** DB 저장 실패 **/				R_2912("2912", "AGENT",		    "DB 저장 실패",								"DB 저장 실패"),
	/* 490X 재처리 코드 	 */
	/** 시스템 에러 **/					R_4900("4900", "AGENT", 		"시스템 에러", 									"내부 실패 - 재처리 필요"),
	
	/** ETC **/						R_ETC("9000", "ETC", 		    "기타", 										"기타오류"),
	;

	public String rslt;
	public String codeType;
	public String rsltDesc;
	public String cliDesc;
	
	public static HashMap<String, ResultCode> getRsltCode() {
		HashMap<String, ResultCode> codeMap = new HashMap<>();
		for (ResultCode c : values()) {
			codeMap.put(c.rslt, c);
		}
		return codeMap;
	}
	
	public static ResultCode getResultCd(String result) {
		return getResultCd(result, ResultCode.R_ETC);
    }
	
	public static ResultCode getResultCd(String result, ResultCode rcsRsltCd) {
		ResultCode rsltCd = null;
        try {
        	rsltCd = ResultCode.valueOf(result);
        } catch (Exception e) {
        	rsltCd = (rcsRsltCd == null) ? ResultCode.R_ETC : rcsRsltCd;
		}
        return rsltCd;
    }
	
	@Getter
	@AllArgsConstructor
	@ToString
	public enum Prefix {
		
		PREFIX_R("R_"),
		;

		public String key;
	}
}
