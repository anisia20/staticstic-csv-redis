package staticstic.scouter.sample.stat.client.service.redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.model.ScoutStat;
import staticstic.scouter.sample.stat.client.model.TrnsSvcInfoDto;
import staticstic.scouter.sample.stat.client.model.vo.Result;
import staticstic.scouter.sample.stat.client.model.vo.redis.RedisVo;


@Log4j2
@Service
public class RedisService {

	@Autowired
	ResourcesManager resourcesManager;


	public Result getRedisHash(Object key, Object field) {
		Result result = new Result();
		Map<Object, Object> resultObj = resourcesManager.getRedisCmd().hgetAll((String) field);

		result.onSuccess(resultObj);
		return result;
	}


	// 레디스 키에 해당하는 모든 키 반환
	public Result getRedisHashKeys(String key) {
		Result result = new Result();
		Set resultList = resourcesManager.getRedisCmd().hgetAllField(key);

		RedisVo rv = RedisVo.builder().keylist(resultList).build();
		result.onSuccess(rv);
		return result;
	}

	public Result setRedisHash(String key, String field, Object data) {
		Result result = new Result();
		resourcesManager.getRedisCmd().hput(key, field, data);

		result.onSuccess();
		return result;
	}

	public Result getScoutTotalStat(String redisKey) {
		Result result = new Result();

		// 레디스에 해당하는 전체키 반환
		Map<Object, Object> mp = resourcesManager.getRedisCmd().hgetAll(redisKey);

		log.debug("mp : " + mp.toString());

		result.onSuccess(mp);
		return result;
	}


	public Result getScoutAvgStat() {

		// 조회할 데이터를 임시로 담고
		Result result = new Result();

		//오늘 날짜
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);

		// 앞에서 계산한 오늘 날짜로 부터 3개월 정도 정보 조회
		// 레디스에 해당하는 전체키 반환
		String redisKey = "TRNS_SCV_INFO";


		Map<Object, Object> res = new HashMap<>();
		Map<Object, Object> mp;
		// 전체 키 조회


		List<String> keyList = resourcesManager.getRedisCmd().getKeyList("TRNS_SCV_INFO*");

		Collections.sort(keyList);
		// 각 키에 맞는 데이터 조회 후 계산

		for (String obj : keyList) {

			ScoutStat stat = new ScoutStat();

			mp = resourcesManager.getRedisCmd().hgetAll((obj));
			int tmpcnt = 0;
			mp.forEach(
					(key, value) -> {
						// System.out.print("key: "+ key);
						TrnsSvcInfoDto tmpStat = resourcesManager.getModelMapper().map(value, TrnsSvcInfoDto.class);
						// System.out.println(", Value: "+ tmpStat);

						//log.debug("################################ k {}, d {}",key, tmpStat.toString());
						stat.setTotalTrnCnt(stat.getTotalTrnCnt() + tmpStat.getSvcCnt());
						//	log.debug( "data sum {} : " + stat.getTotalTrnCnt());
						stat.setErrCnt(stat.getErrCnt() + tmpStat.getSvcErrCnt());
						stat.setTotalDlyTime(stat.getTotalDlyTime() + tmpStat.getSvcTotalElap());
						if (tmpStat.getSvcAvgElap() >= 1000) {
							stat.setResDlyCnt(stat.getResDlyCnt() + tmpStat.getSvcCnt());
						}

					}
			);

			System.out.println(" 총 트렌젝션 수 " + stat.getTotalTrnCnt());
			System.out.println(" 에러 수 " + stat.getErrCnt());
			System.out.println(" 총 지연시간 " + stat.getTotalDlyTime());
			System.out.println(" 총 응답지연 건수 " + stat.getResDlyCnt());
			double ttmp = (double) ((double) stat.getResDlyCnt() / stat.getTotalTrnCnt());
			long tmplong = Math.round(ttmp * 100000);
			// 응답 지연 율 계산
			stat.setResDlyRate(tmplong / 1000.0);

			stat.setTgDate(obj);
			stat.setTgDate(stat.getTgDate().substring(14));


			// 에러율
			stat.setErrRate((Math.round((stat.getErrCnt() / (double) (stat.getTotalTrnCnt())) * 100000)) / 1000.0);

			// 평균 처리량
			stat.setTps(((Math.round((double) stat.getTotalTrnCnt() / (24 * 60 * 60)) * 1000)) / 1000.0);

			stat.setAvlAbility(1 - (stat.getSvcUnAvail() / (60 * 60)));

			res.put(obj, stat);

		}


		result.onSuccess(res);

		return result;
	}


	public Result getScoutSvcStat() {

		// 조회할 데이터를 임시로 담고
		Result result = new Result();

		// 앞에서 계산한 오늘 날짜로 부터 3개월 정도 정보 조회
		// 레디스에 해당하는 전체키 반환
		String redisKey = "TRNS_SCV_INFO";


		Map<Object, Object> res = new HashMap<>();
		Map<Object, Object> mp;
		// 전체 키 조회


		List<String> keyList = resourcesManager.getRedisCmd().getKeyList("TRNS_SCV_INFO*");

		Collections.sort(keyList);
		// 각 키에 맞는 데이터 조회 후 계산

		for (String obj : keyList) {


			ScoutStat stat = new ScoutStat();

			mp = resourcesManager.getRedisCmd().hgetAll((obj));

			mp.forEach(
					(key, value) -> {
						// 데이터 읽기
						TrnsSvcInfoDto tmpStat = resourcesManager.getModelMapper().map(value, TrnsSvcInfoDto.class);

					}
			);
		}
		return result;
	}

	public Result getScoutAvgSevStat() throws ParseException {
		// 조회할 데이터를 임시로 담고
		Result result = new Result();

		//오늘 날짜
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);

		// 앞에서 계산한 오늘 날짜로 부터 3개월 정도 정보 조회
		// 레디스에 해당하는 전체키 반환
		String redisKey = "TRNS_SCV_INFO";


		Map<Object, Object> res = new HashMap<>();
		Map<Object, Object> mp;
		// 전체 키 조회


		List<String> tmpkeyList = resourcesManager.getRedisCmd().getKeyList("TRNS_SCV_INFO*");

		Collections.sort(tmpkeyList);
		// 각 키에 맞는 데이터 조회 후 계산

		Collections.reverse(tmpkeyList);

		List<String> keyList = new ArrayList<>();

		for (int i = 0 ; i < 7 ; i++) {
			keyList.add(tmpkeyList.get(i));

		}
		ScoutStat stat = new ScoutStat();

		for (String obj : keyList) {



			mp = resourcesManager.getRedisCmd().hgetAll((obj));
			int tmpcnt = 0;


			mp.forEach(
					(key, value) -> {

						TrnsSvcInfoDto tmpStat = resourcesManager.getModelMapper().map(value, TrnsSvcInfoDto.class);

						stat.setTotalTrnCnt(stat.getTotalTrnCnt() + tmpStat.getSvcCnt());

						stat.setErrCnt(stat.getErrCnt() + tmpStat.getSvcErrCnt());
						stat.setTotalDlyTime(stat.getTotalDlyTime() + tmpStat.getSvcTotalElap());
						if (tmpStat.getSvcAvgElap() >= 1000) {
							stat.setResDlyCnt(stat.getResDlyCnt() + tmpStat.getSvcCnt());
						}

					}
			);

		}

		double ttmp = (double) ((double) stat.getResDlyCnt() / stat.getTotalTrnCnt());
		long tmplong = Math.round(ttmp * 100000);
		// 응답 지연 율 계산
		stat.setResDlyRate(tmplong / 1000.0);

		stat.setTgDate(keyList.get(0).substring(14)  + keyList.get(6).substring(14));
		// 에러율
		stat.setErrRate((Math.round((stat.getErrCnt() / (double) (stat.getTotalTrnCnt())) * 100000)) / 1000.0);

		// 평균 처리량
		stat.setTps(((Math.round((double) stat.getTotalTrnCnt() / ( 7 * 24 * 60 * 60)) * 1000)) / 1000.0);

		stat.setAvlAbility(1 - (stat.getSvcUnAvail() / (60 * 60)));



		res.put("soutSvdt", stat);
		result.onSuccess(res);

		return result;


	}
}