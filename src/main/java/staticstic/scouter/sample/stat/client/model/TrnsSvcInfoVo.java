package staticstic.scouter.sample.stat.client.model;

import javax.validation.constraints.NotNull;

import lombok.Data;
import staticstic.scouter.sample.stat.client.model.vo.CommonVo;


//@Getter
//@Setter
//@ToString

@Data
public class TrnsSvcInfoVo extends CommonVo{
	
		/** 서비스 수집일자 */
		@NotNull 
		private String svcTgDate;
				
		/** 서비스 명 */
		@NotNull
		private String svcNm;
		
		
		/** 서비스 URL */
		private String svcUrl;
		
		
		/** 서비스 총 건수 */
		private String svcCnt;
		
		/** 서비스 에러 건수 */
		private String svcErrCnt;
		
		/**  서비스 총 지연 */
		private String svcTotalElap;
		
		
		/** 서비스 평균 지연 */
		private String svcAvgElap;
		
		/** 등록 일시 */
		private String svcRgDt;
		
		
	

}
