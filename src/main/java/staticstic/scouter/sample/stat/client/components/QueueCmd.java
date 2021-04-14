package staticstic.scouter.sample.stat.client.components;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class QueueCmd {
	public static Map<String,Queue> quemap = new LinkedHashMap<String, Queue>();
	
	/**
	 * 큐 등록
	 * @param key : 큐이름
	 * @param data : 데이터
	 * @return boolean
	 */
	public boolean push(String key, Object data) {
		Queue<Object> que = quemap.containsKey(key)?quemap.get(key):new LinkedList<>();
		boolean result = que.add(data);
		quemap.put(key, que);
		return result;
	}
	
	/**
	 * 큐 데이터 pop
	 * @param key : 큐이름
	 * @return Object
	 */
	public Object pop(String key) {
		return isEmpty(key)?null:(Object) quemap.get(key).poll();
	}
	
	/**
	 * 큐 데이터 존재 여부 확인
	 * @param key : 큐이름
	 * @return boolean
	 */
	public boolean isEmpty(String key) {
		return quemap.containsKey(key)?quemap.get(key).isEmpty():true;
	}
	
	/**
	 * 큐 삭제
	 * @param key : 큐이름
	 */
	public void remove(String key) {
		quemap.remove(key);
	}
	
	/**
	 * 큐 사이즈 조회
	 * @param key : 큐 이름
	 * @return int
	 */
	public int size(String key) {
		return isEmpty(key)? 0 : quemap.get(key).size();
	}
	
}
