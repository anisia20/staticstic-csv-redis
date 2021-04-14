package staticstic.scouter.sample.stat.client.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Json {
	public static String getJSON(Map<?, ?> map) throws Exception {
		if (map == null) {
			return null;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, map);
			String rtn = String.valueOf(sw);
			sw.close();
			return rtn;
		}
	}

	/**
	 * 문자열 jsonnode 변환
	 * 
	 * @param strJson
	 * @return
	 */
	public static JsonNode stringToJson(String strJson) {
		// String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
		ObjectMapper mapper = new ObjectMapper();

		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(strJson);
		} catch (Exception e) {
			// log.error("stringToJson Exception : "+e.toString());
		}
		return actualObj;
	}

	/**
	 * json 문자열 변환
	 * 
	 * @param json
	 * @return
	 */
	public static String jsonToString(JsonNode json) {

		ObjectMapper mapper = new ObjectMapper();

		String actualString = null;
		try {
			actualString = mapper.writeValueAsString(json);
		} catch (Exception e) {
			log.error("jsonTostring Exception : " + e.toString());
		}
		return actualString;
	}

	/**
	 * 문자열 맵 변환
	 * 
	 * @param strJson
	 * @return
	 */
	public static HashMap<String, String> stringToHashmap(String strJson) {
		return getHashMap(stringToJson(strJson));
	}

	/**
	 * json 값 반환
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getText(JsonNode json, String key) {
		if (json != null && json.has(key))
			return json.get(key).textValue();
		else
			return "";
	}

	/**
	 * json 맵 반환
	 * 
	 * @param json
	 * @return
	 */
	public static HashMap<String, String> getHashMap(JsonNode json) {

		HashMap<String, String> hm = new HashMap<String, String>();

		if (!(json instanceof ObjectNode)) {
			return null;
		}

		ObjectNode objNode = (ObjectNode) json;
		Iterator<Map.Entry<String, JsonNode>> fields = objNode.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			JsonNode value = entry.getValue();
			hm.put(entry.getKey(), value.isTextual() ? value.textValue() : value.asText());
		}
		return hm;
	}

	/**
	 * json 맵 float 반환
	 * 
	 * @param json
	 * @return
	 */
	public static HashMap<String, Float> getHashMapFloat(JsonNode json) {

		HashMap<String, Float> hm = new HashMap<String, Float>();

		if (!(json instanceof ObjectNode)) {
			return null;
		}

		ObjectNode objNode = (ObjectNode) json;
		Iterator<Map.Entry<String, JsonNode>> fields = objNode.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			JsonNode value = entry.getValue();
			hm.put(entry.getKey(), Float.parseFloat(value.isTextual() ? value.textValue() : value.asText()));
		}
		return hm;
	}

	/**
	 * json 맵 float 반환
	 * 
	 * @param strJson
	 * @return
	 */
	public static HashMap<String, Float> stringToHashMapFloat(String strJson) {
		return getHashMapFloat(stringToJson(strJson));
	}

	/**
	 * json 값 설정
	 * 
	 * @param json
	 * @param key
	 * @param value
	 */
	public static void setValue(JsonNode json, String key, String value) {
		((ObjectNode) json).put(key, value);
	}

	/**
	 * DB 큐에 넣기 위한 json 문자열 반환
	 * 
	 * @param mid
	 * @param crudType
	 * @param jnode
	 * @return
	 */
	public static String makeMybatisJson(String mid, String crudType, JsonNode jnode) {
		Json.setValue(jnode, "mybatis-id", mid);
		Json.setValue(jnode, "mybatis-type", crudType);
		return Json.jsonToString(jnode);
	}

	/**
	 * DB 큐에 넣기 위한 json 문자열 반환
	 * 
	 * @param mid
	 * @param crudType
	 * @param hm
	 * @return
	 */
	public static Map makeMybatisJson(String mid, String crudType, HashMap<String, String> hm) {
		hm.put("mybatis-id", mid);
		hm.put("mybatis-type", crudType);
		return hm;
	}

	/**
	 * 맵 json문자열 변환
	 * 
	 * @param hm
	 * @return
	 */
	public static String hashmapToJsonString(HashMap<String, String> hm) {
		String rslt = "";
		try {
			rslt = new ObjectMapper().writeValueAsString(hm);
		} catch (Exception e) {
			log.warn("Json convert error. e={}", e.getMessage(), e);
			rslt = "";
		}
		return rslt;
	}

	/**
	 * 맵 json 변환
	 * 
	 * @param hm
	 * @return
	 */
	public static JsonNode hashmapToJsonNode(HashMap<String, String> hm) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.convertValue(hm, JsonNode.class);
		return jsonNode;
	}
}
