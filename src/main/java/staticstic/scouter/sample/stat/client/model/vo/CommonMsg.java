package staticstic.scouter.sample.stat.client.model.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonMsg extends CommonVo {
	@ApiModelProperty(value = "분배기명", required = true, example="분배기1")
	@NotEmpty(message="R_2101")
	String dstrNm;
	
	@ApiModelProperty(value = "발송시간(YYYYMMDDHH24MI)", required = true, example="202006221212")
	@NotEmpty(message="R_2102")
	@Pattern(regexp= "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])([1-9]|[01][0-9]|2[0-3])([0-5][0-9])", message="R_2102")
	String sendTime;
	
	@ApiModelProperty(value = "예비 1", required = true, example="")
	String rsvFld1;
	
	@ApiModelProperty(value = "예비 2", required = true, example="")
	String rsvFld2;
	
	@ApiModelProperty(value = "예비 3", required = true, example="")
	String rsvFld3;
	
	@ApiModelProperty(value = "예비 4", required = true, example="")
	String rsvFld4;
	
	@ApiModelProperty(value = "예비 5", required = true, example="")
	String rsvFld5;
	
	@ApiModelProperty(value = "예비 6", required = true, example="")
	String rsvFld6;
	
	
}
