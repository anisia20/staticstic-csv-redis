package staticstic.scouter.sample.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHooker extends Thread {
	Daemonx process = null;
	long max_waiting_time = 0L;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public ShutdownHooker(Daemonx proc, int max_waiting_seconds) {
		this.process = proc;
		this.max_waiting_time = (long) (max_waiting_seconds * 1000);
		if (this.max_waiting_time < 0L) {
			this.max_waiting_time = 10000L;
		}
	}

	public void run() {
		try {
			logger.debug("{} running", getClass().getSimpleName());
			
			long e = System.currentTimeMillis();
			this.process.nextStop();

			while (this.process != null && this.process.isFinish()==false) {
				Util.sleep(1000);
				if (System.currentTimeMillis() - e > this.max_waiting_time) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}