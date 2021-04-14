package staticstic.scouter.sample.stat.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 송신결과
 *
 */
@Data
@ToString
public class ResponseVo {

	@ApiModelProperty(value = "결과코드", required = true, example = "1000(성공), 2xxx(실패)")
	String rslt;
	
	@ApiModelProperty(value = "결과메시지", required = true, example = "OK(성공),Fail....상세이유(실패)")
	String rsltDesc;
}
