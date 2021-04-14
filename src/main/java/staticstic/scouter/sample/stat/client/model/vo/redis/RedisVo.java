package staticstic.scouter.sample.stat.client.model.vo.redis;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import staticstic.scouter.sample.stat.client.model.vo.CommonVo;

@Data
@Builder
public class RedisVo extends CommonVo {
	Set keylist;
}
