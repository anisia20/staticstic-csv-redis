package staticstic.scouter.sample.stat.client.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CommonVo {
	/** API KEY */
	@JsonIgnore
	String jobId;
}
