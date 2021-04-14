package staticstic.scouter.sample.stat.client.module;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.common.util.Daemonx;
import staticstic.scouter.sample.common.util.Time;
import staticstic.scouter.sample.common.util.Util;
import staticstic.scouter.sample.stat.client.components.QueueCmd;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;

/**
 * life cycle 형식의 쓰레드 구성
 * @author csm
 *
 */
@Log4j2
public class Sample extends Daemonx {

	/**
	 * 리소스 매니저
	 */
	ResourcesManager mResourcesManager;
	
	/**
	 * 메모리 큐
	 */
	QueueCmd mQueueCmd;
	
	Time time = new Time("yyyyMMdd");
	
	/**
	 * 검색조건
	 */
	Map<String, String> map = new HashMap<String, String>();
	
	public Sample(ResourcesManager resourcesManager) {
		super();
		this.mResourcesManager = resourcesManager;
		this.mQueueCmd = resourcesManager.getQueueCmd();
		
		setName(this.getClass().getSimpleName());
		log.info("{} start", getName());
		nextInit();
	}
	
	@Override
	public void init() {
		nextExecute();
		log.info("{} ready", getName());
	}
	
	@Override
	public void fail() {
		log.info("{} abnormal", getName());
		Util.sleep(10 * 1000);
		nextInit();
	}
	
	/**
	 * 업무처리
	 */
	@Override
	public void execute() {
		nextSleep();
	} 
	
	@Override
	public void sleep() {
		Util.sleep(10 * 1000);
		nextExecute();
	} 
	
	@Override
	public void stop() {
		log.info("{} stop", getName());
	}
	
}
