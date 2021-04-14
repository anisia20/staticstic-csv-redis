package staticstic.scouter.sample.stat.client.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.JsonCmd;
import staticstic.scouter.sample.stat.client.components.JwtTokenComponent;
import staticstic.scouter.sample.stat.client.components.QueueCmd;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.service.statex.DaemonxService;

/**
 * 
 *
 * @author : smcho
 * @Date : 2019. 12. 24.
 */
@Component
@Log4j2
public class Scheduler {

	/**
	 * 리소스 매니저
	 */
	@Autowired
    ResourcesManager mResourcesManager;

	/**
	 * 쓰레드 관리 서비스
	 */
	@Autowired
	DaemonxService mDaemonxService;

	/**
	 * JWT 토큰 관리
	 */
	@Autowired
    JwtTokenComponent jwtTokenComponent;

	@Autowired
    JsonCmd jsonCmd;

	@Autowired
    QueueCmd mQueueCmd;


	/**
	 * 쓰레드 관리
	 *  - 5초마다
	 */
	@Scheduled(fixedDelay = 5000)
	public void doStatex() {
		if (isShutdown)
			return;

		mDaemonxService.checkDaemonx();
	}

	/**
	 * JWT 토큰 갱신
	 *  - 5초마다
	 */
	@Scheduled(fixedDelay = 5000)
	public void refreshJwtToken() {
		if (isShutdown)
			return;

		jwtTokenComponent.refreshJwtToken(5);
	}

	boolean isShutdown = false;
	public void shutdown() {
		// shutdown 처리
		isShutdown = true;
		mDaemonxService.stopDaemonx();

	}

}
