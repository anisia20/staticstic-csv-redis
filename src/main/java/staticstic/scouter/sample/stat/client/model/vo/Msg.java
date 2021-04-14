package staticstic.scouter.sample.stat.client.model.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Msg extends CommonMsg {
	
	@ApiModelProperty(value = "채널아이디", required = true, example="sms")
	@NotEmpty(message="R_2103")
	String chId;
	
	@ApiModelProperty(value = "성공건수", required = true, example="100")
	@Positive(message="R_2106")
	int sucessCnt;
	
	@ApiModelProperty(value = "실패건수", required = true, example="1")
	@Positive(message="R_2104")
	int failCnt;
	
	@ApiModelProperty(value = "에러코드 개수 1", required = true, example="0")
	int errCodeCnt1;
	
	@ApiModelProperty(value = "에러코드 개수 2", required = true, example="0")
	int errCodeCnt2;
	
	@ApiModelProperty(value = "에러코드 개수 3", required = true, example="0")
	int errCodeCnt3;
	
	@ApiModelProperty(value = "에러코드 개수 4", required = true, example="0")
	int errCodeCnt4;
	
	@ApiModelProperty(value = "에러코드 개수 5", required = true, example="0")
	int errCodeCnt5;
}
