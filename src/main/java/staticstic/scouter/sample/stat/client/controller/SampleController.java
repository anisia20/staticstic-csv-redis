package staticstic.scouter.sample.stat.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.common.util.KeyMaker;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.model.vo.Msg;
import staticstic.scouter.sample.stat.client.model.vo.ResponseVo;
import staticstic.scouter.sample.stat.client.service.ApiService;

@Log4j2
@Controller 
@RequestMapping("/api/dstr")
@Api("Test API")
public class SampleController {

	/**
	 * 리소스 매니저
	 */
	@Autowired
	ResourcesManager mResourcesManager;
	
	/**
	 * api 처리 서비스
	 */
	@Autowired
	ApiService mApiService;
	
	@ApiOperation(value="메시지 정보(임의의 데이터로 작성 하였습니다.)", 
			notes="임의의 데이터를 json 형식으로 입력, 결과도 status 200에 결과값으로 리턴 됩니다. \n"
			+ "model anotation으로 validation 체크하여 예외 결과 리턴 됩니다. \n"
			+ "채널아이디 항목을 지우면 예외결과가 리턴 됩니다.", authorizations = { @Authorization(value="jwtToken") })
	@ApiResponses({
		@ApiResponse(code=200, response= ResponseVo.class, message = "요청 결과"),
	})
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/msg")
	public ModelAndView chByMsg(
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestBody
			@ApiParam(name="Msg", value="Msg")
                    Msg data
		) 
	{
		data.setJobId(mResourcesManager.getKeyMaker().nextKey(KeyMaker.TT_CAB));
		log.info("K={} call, parameters={}", data.getJobId(), request.getRequestURI(), request.getQueryString());
		return new ModelAndView("jsonView", mApiService.doMsg(data));
	}
	
}
