package staticstic.scouter.sample.stat.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HashKeyFieldReq {


    private String redisKey;

    private Object keyList;
}
