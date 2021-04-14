package staticstic.scouter.sample.stat.client.service.statex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.common.util.Daemonx;
import staticstic.scouter.sample.common.util.Util;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.config.Def;
import staticstic.scouter.sample.stat.client.module.Sample;

@Log4j2
@Service
public class DaemonxService {
	
	/**
	 * 리소스 매니저
	 */
	@Autowired
    ResourcesManager mResourcesManager;

	long hangtimeout = 60 * 1000;
	
	/**
	 * 쓰레드 추가시 항목 추가 
	 * @param name
	 * @return
	 */
	public Daemonx getInstance(String name) {
		Daemonx st = null;
		if(name.contains(Def.T_Sample)) st = new Sample(mResourcesManager);
		
		return st;
	}
	
	/**
	 * 쓰레드 추가시 startCheckDaemonx 항목 추가
	 * @return
	 */
	public int checkDaemonx() {
		// 비정상 쓰레드 점검
		for(String stxName : ResourcesManager.stxMap.keySet()) {
			Daemonx stx = ResourcesManager.stxMap.get(stxName);
			if(System.currentTimeMillis() - stx.getLastCheckTime() > hangtimeout) {
				stx.nextStop();
			}
		}
		
		// 필수 쓰레드 확인
		startCheckDaemonx(Def.T_Sample);//수집
		
		return 1;
	}
	
	/**
	 * 쓰레드 중지
	 * @return int
	 */
	public int stopDaemonx() {
		if (ResourcesManager.stxMap == null || ResourcesManager.stxMap.size() <= 0) return 0;

		for(Daemonx stx : ResourcesManager.stxMap.values()) {
			stx.nextStop();
		}
		
		boolean isAllStop = false;
		long chkStartDT = System.currentTimeMillis();
		while(isAllStop==false) {
			log.info("Shutdown waiting.. {}", (System.currentTimeMillis()-chkStartDT)/1000);
			Util.sleep(1000);
			
			// 20초동안 종료되지 않으면 포기
			if (System.currentTimeMillis()-chkStartDT > 20000) break;
			
			// 종료 체크
			isAllStop = true;
			for(Daemonx one : ResourcesManager.stxMap.values()) {
				if (one != null && one.getStatus() != Daemonx.Status.STOP) isAllStop = false;
			}
			
			if (isAllStop) log.info("Module is all shutdown.");
		}
		return 1;
	}
	
	/**
	 * 필수 쓰레드 체크
	 * @param tn : 쓰레드 명
	 */
	private void startCheckDaemonx(String tn) {
		startCheckDaemonx(tn, 1);
	}
	
	/**
	 * 필수 쓰레드 체크
	 * @param tn : 쓰레드 명
	 * @param count : 갯수
	 */
	private void startCheckDaemonx(String tn, int count) {
		for(int i = 0 ; i < count; i++) {
			String tn2 = tn+"_"+i;

			Daemonx stx = ResourcesManager.stxMap.get(tn2); 
			if (stx != null && stx.getStatus() != Daemonx.Status.STOP )
				continue;
			
			stx = getInstance(tn2);
			if(stx == null) {
				log.error("thread name is unknown. TN={}", tn2);
				return;
			}
			stx.setName(tn2);
			mResourcesManager.addDaemonx(stx);
			new Thread(stx).start();
		}
	}
	
}
