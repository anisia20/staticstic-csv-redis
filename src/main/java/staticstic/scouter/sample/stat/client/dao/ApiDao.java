package staticstic.scouter.sample.stat.client.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.components.SqlSessionx;
import staticstic.scouter.sample.stat.client.model.ApiDto;
import staticstic.scouter.sample.stat.client.model.vo.CommonVo;

@Log4j2
@Repository
public class ApiDao {

	@Autowired
	private SqlSessionx sqlSession;

	@Autowired
	private ResourcesManager mResourcesManager;
	
	/**
	 * api data insert
	 * @param CommonVo : 입력 Vo
	 * @return int insert 결과
	 */
	public int insertMsgApiData(CommonVo vo)  {
		//vo 2 dto
		ApiDto dto = mResourcesManager.getModelMapper().map(vo, ApiDto.class);
		//기준일자 지정
		
		//type : vo 클래스명
		//int n_res = sqlSession.insert("api.insertApi"+vo.getType(), dto); 임시 실제 수행하지 않음
		int n_res = 1;
		if(n_res <= 0) 
			log.warn("Api data Insert Fail K={}, D={}",vo.getJobId(), dto);
		else
			log.info("Api data Insert Sucess K={}, DT={}, SQ={}", vo.getJobId(), dto.getSndDate(), dto.getSeq());
		
		return n_res;
	}
	
	
}
