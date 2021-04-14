package staticstic.scouter.sample.stat.client.model;

import lombok.Data;
import lombok.ToString;

/**
 * api 통합 Dto
 *
 */
@Data
@ToString
public class ApiDto {
	/** 기준일자*/
	String sndDate;
	
	/** SEQ */
	long seq;
	
	/** 분배기명"*/
	String dstrNm;
	
	/** 채널아이디"*/
	String chId;
	
	/** 캠페인코드"*/
	String campCd;
	
	/** 캠페인명"*/
	String campNm;
	
	/** 발송시간(분)"*/
	String sendTime;
	
	/** 성공건수"*/
	int sucessCnt;
	
	/** 실패건수"*/
	int failCnt;
	
	/** 에러코드 개수 1"*/
	int errCodeCnt1;
	
	/** 에러코드 개수 2"*/
	int errCodeCnt2;
	
	/** 에러코드 개수 3"*/
	int errCodeCnt3;
	
	/** 에러코드 개수 4"*/
	int errCodeCnt4;
	
	/** 에러코드 개수 5"*/
	int errCodeCnt5;
	
	/** 예비 1"*/
	String rsvFld1;
	
	/** 예비 2"*/
	String rsvFld2;
	
	/** 예비 3"*/
	String rsvFld3;
	
	/** 예비 4"*/
	String rsvFld4;
	
	/** 예비 5"*/
	String rsvFld5;
	
	/** 예비 6"*/
	String rsvFld6;
	
	/** 중계사아이디"*/
	String rlcomId;
	
	/** 분배율"*/
	String dstrRate;
	
	/** 등록 일자(일)"*/
	String regDate;
	
	/** 분배기 tps"*/
	int dstrTps;
	
	/** 통지코드"*/
	String ntfyCd;
	
	/** 통지명"*/
	String ntfyNm;
	
	/** 발송 상태 */
	String sendStatus;
	
	/** 시스템 구분 코드 */
	String sysDivCd;
	
	/** 오류코드 */
	String errCd;
	
	/** 오류내용 */
	String errSbst;
	
	/** 오류 발생시간(초) */
	String errOccDt;
	
	/** 분배기 큐명 */
	String dstrQueueNm;
	
	/** 대기 건수 */
	int waitCnt;
	
	/** 수집 시간 */
	String colecTime;
	
	/** 수집 일자 */
	String colecDate;
	
}

