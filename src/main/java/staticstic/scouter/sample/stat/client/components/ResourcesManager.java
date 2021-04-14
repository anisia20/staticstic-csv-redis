package staticstic.scouter.sample.stat.client.components;

import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.common.util.Daemonx;
import staticstic.scouter.sample.common.util.KeyMaker;
import staticstic.scouter.sample.common.util.Time;
import staticstic.scouter.sample.common.util.Util;
import staticstic.scouter.sample.stat.client.config.Def;
import staticstic.scouter.sample.stat.client.model.vo.CommonVo;
import staticstic.scouter.sample.stat.client.model.vo.Result;
import staticstic.scouter.sample.stat.client.utils.ValidUtil;

/**
 * 리소스 매니저
 * @author csm
 *
 */
@Component
@Data
@Log4j2
public class ResourcesManager {
	
	@Autowired
	RedisCmd redisCmd;
	
	public static LinkedHashMap<String, Daemonx> stxMap = new LinkedHashMap<String, Daemonx>();
	protected static Hashtable<String, Object> resources = new Hashtable<String, Object>();

	static long ServNum;
	
	/**
	 * Active Agent 여부 
	 */
	private boolean isActive = false;

	/**
	 * OnOff 여부
	 */
	private boolean isOnOff = false;

	/**
	 * TPS 수치
	 */
	private int maxTPS = 100;
	/**
	 * 통계 TPS 수치 
	 */
	private int sttMaxTPS = 100;

	/**
	 * 수집주기 
	 */
	private int collecCycl = -1;
	
	/**
	 * 현재 TPS 수치
	 */
	private int curTPS = 0;

	/**
	 * 현재 통계 TPS 수치
	 */
	private int curSttTPS = 0;

	
	/**
	 * 모델 맵퍼
	 */
	@Autowired
	ModelMapperx modelMapper;
	
	/**
	 * 트랜젝션 매니저
	 */
	@Autowired
	PlatformTransactionManager transactionManager;

	/**
	 * Json 변경 Cmd
	 */
	@Autowired
	JsonCmd jsonCmd;

	/**
	 * 메모리 큐
	 */
	@Autowired
	QueueCmd queueCmd;

	/**
	 * 송/수신 Webclient
	 */
//	@Autowired
//	WebClient webClient;

	/**
	 * JWT 토큰 관리자
	 */
	@Autowired
	JwtTokenComponent jwtTokenComponent;
	
	/**
	 * enckey key 
	 */
	@Value("${enckey}")
	String enckey;

	/**
	 * jwt token key 
	 */
	@Value("${jwt.tokenKey}")
	String jwtTokenKey;

	/**
	 * jwt token key 
	 */
	@Value("${jwt.dstrTokenKey}")
	String jwtDstrTokenKey;

	/**
	 * 에이전트 ID
	 */
	@Value("${agent.id}")
	private String agentId;

	/**
	 * 전송 서버 HOST
	 */
	@Value("${server.url.host}")
	private String host;

	/**
	 * 전송 서버 PORT
	 */
	@Value("${server.url.port}")
	private String port;

	/**
	 * key 생성
	 * @return
	 */
	@Bean
	@Primary
	public KeyMaker getKeyMaker() {
		try {
			Object obj = resources.get(Def.AGENT_KEY);
			if (obj == null) {
				ServNum++;
				if (ServNum < 0 || ServNum > 99) {
					ServNum = 0;
				}

				KeyMaker km = new KeyMaker((int) ServNum);
				resources.put(Def.AGENT_KEY, km);
				return km;
			} else {
				KeyMaker km = (KeyMaker) obj;
				return km;
			}
		} catch (Exception e) {
			log.error("KeyMaker gathering fail. err={}", e.toString(), e);
			return null;
		}
	}

	/**
	 * 쓰레드 추가
	 * @param stx : 쓰레드
	 * @return
	 */
	public boolean addDaemonx(Daemonx stx) {
		if (stx.getName() == null || stx.getName().isEmpty()) {
			log.error("Daemonx invalid name");
			return false;
		}
		stxMap.put(stx.getName(), stx);
		return true;
	}

	/**
	 * Active 에이전트 여부
	 * @return boolean
	 */
	public boolean isActive() {
		return this.isActive;
	}

	/**
	 * Acitve 에이전트 설정
	 * @param active boolean
	 */
	public synchronized void setActive(boolean active) {
		this.isActive = active;
	}

	/**
	 * 송신ON/OFF 여부
	 * @return boolean
	 */
	public boolean isOn() {
		return this.isOnOff;
	}

	/**
	 * TPS 수치 설정
	 * @param maxTps
	 */
	public synchronized void setMaxTps(int maxTps) {
		this.maxTPS = maxTps;
	}
	
	/**
	 * 통계 TPS 수치 설정
	 * @param sttMaxTps
	 */
	public synchronized void setSttMaxTps(int sttMaxTps) {
		this.sttMaxTPS = sttMaxTps;
	}

	/**
	 * 현재 TPS
	 * @return int
	 */
	public int getCurTps() {
		return curTPS;
	}

	/**
	 * 현재 TPS 설정
	 * @param curTps
	 */
	public synchronized void setCurTps(int curTps) {
		this.curTPS = curTps;
	}

	/**
	 * TPS 수치 확인
	 */
	Time time = new Time("yyyyMMddHHmmss");
	String tpsCheckDT = null;
	public synchronized void add_TPS() {
		String curT = time.toFormat();
		if (curT.equals(tpsCheckDT) == false) {
			tpsCheckDT = curT;
			curTPS = 1;
			return;
		}

		if (curTPS >= maxTPS) {
			while (true) {
				curT = time.toFormat();
				if (curT.equals(tpsCheckDT) == false) {
					tpsCheckDT = curT;
					curTPS = 1;
					return;
				}
				Util.sleep(20);
			}
		}

		curTPS++;
	}


	/**
	 * 입력 DTO validation 처리
	 * 
	 * @param response
	 * @param dto
	 * @param result
	 * @return
	 */
	public boolean validationDto(CommonVo dto, Result result) {
		boolean isFail = false;
		if (ValidUtil.isValid(dto, result) == false) {
			log.warn("validation check faild. err={}", result);
			isFail = true;
		}
		return isFail;
	}

}
