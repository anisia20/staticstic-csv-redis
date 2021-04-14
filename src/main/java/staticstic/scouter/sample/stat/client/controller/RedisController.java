package staticstic.scouter.sample.stat.client.controller;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.model.HashKeyFieldReq;
import staticstic.scouter.sample.stat.client.model.vo.Result;
import staticstic.scouter.sample.stat.client.service.redis.RedisService;

@Log4j2
@Controller 
@RequestMapping("/redis")
public class RedisController {

	/**
	 * 리소스 매니저
	 */
	@Autowired
	ResourcesManager mResourcesManager;
	
	/**
	 * api 처리 서비스
	 */
	@Autowired
	RedisService redisService;
	
	
	@GetMapping("/redisHashList/{rediskey}")
	public ModelAndView redisHashList(
			@PathVariable("rediskey") String rediskey,
			HttpServletRequest request, HttpServletResponse response
			) {
		Result result = redisService.getRedisHashKeys(rediskey);
		return new ModelAndView("jsonView", result);
	}
	
	@PostMapping("/getRedisHash")
	@ResponseBody
	public ModelAndView redisHashList(
			@RequestBody HashKeyFieldReq data,
			HttpServletRequest request, HttpServletResponse response
			) {
		Result result = redisService.getRedisHash(data.getRedisKey(), data.getKeyList()); //.get("redis_key"),data.get("key_list"));
		return new ModelAndView("jsonView", result);
	}
	
	@PostMapping("/setRedisHash/{rediskey}/{field}")
	public ModelAndView setRedisHash(
			@PathVariable("rediskey") String rediskey,
			@PathVariable("field") String field,
			@RequestBody Map data,
			HttpServletRequest request, HttpServletResponse response
			) {
		Result result = redisService.setRedisHash(rediskey, field, data.get("data"));
		return new ModelAndView("jsonView", result);
	}


	/**
	 *  스카우터 전체 통계
	 * 	설명 : 기존 액셀  모양과 같은 화면에 뿌려주는 api
	 *
	 * 	@param dtime
	 *
	 *
	 */

	@GetMapping("/scoutTotalStat")
	public ModelAndView scoutTotalStat(
			//@PathVariable("redisKey") String redisKey,
			HttpServletRequest request, HttpServletResponse response
	) {
		String redisKey = "TRNS_SCV_INFO";
		Result result = redisService.getScoutTotalStat(redisKey); //redisService.getRedisHashKeys(redisKey);
		return new ModelAndView("jsonView", result);
	}


	/**
	 *
	 * 스카우터 최근 7 통계 (평균)
	 * 계산하여 표현하기
	 *
	 * 내용 : redis 에 있는 날짜별 데이터를 읽고, 응답지연 율, 에러율, 평균처리량, 가용율, 총 트렌젝션 수, 에러수 총 지연시간, 응답지연 건수를 반환
	 */
	@GetMapping("/scoutAvgSevStat")
	public ModelAndView scoutAvgSevStat(
			HttpServletRequest request, HttpServletResponse response
	) throws ParseException {

		Result result = redisService.getScoutAvgSevStat();

		return new ModelAndView("jsonView", result);
	}


	/**
	 *
	 * 스카우터 날짜별 통계 (평균)
	 * 계산하여 표현하기
	 *
	 * 내용 : redis 에 있는 날짜별 데이터를 읽고, 응답지연 율, 에러율, 평균처리량, 가용율, 총 트렌젝션 수, 에러수 총 지연시간, 응답지연 건수를 반환
	 */
	@GetMapping("/scoutAvgStat")
	public ModelAndView scoutAvgStat(
			HttpServletRequest request, HttpServletResponse response
	) {

		Result result = redisService.getScoutAvgStat();

		return new ModelAndView("jsonView", result);
	}

	/**
	 *
	 * 스카우터 서비스 별 통계
	 * 계산하여 표현하기
	 *
	 * 내용 : redis 에 있는 서비스 별 데이터를 읽고, 응답지연 율, 에러율, 평균처리량, 가용율, 총 트렌젝션 수, 에러수 총 지연시간, 응답지연 건수를 반환
	 */
	@GetMapping("/scoutSvcStat")
	public ModelAndView scoutSvcStat(
			HttpServletRequest request, HttpServletResponse response
	) {

		Result result = redisService.getScoutSvcStat();

		return new ModelAndView("jsonView", result);
	}

}
