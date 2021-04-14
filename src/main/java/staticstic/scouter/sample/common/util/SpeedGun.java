package staticstic.scouter.sample.common.util;

public class SpeedGun {
	long startTime = 0L;
	long stopTime = 0L;

	public void start() {
		this.startTime = System.currentTimeMillis();
	}

	public void reset() {
		this.startTime = 0L;
		this.stopTime = 0L;
	}

	public long getLastStartTime() {
		return this.startTime;
	}

	public long getLastStopTime() {
		return this.stopTime;
	}

	public void stop() {
		this.stopTime = System.currentTimeMillis();
	}

	public long getElapsedTime() {
		return this.stopTime - this.startTime;
	}

	@Deprecated
	public long getRunningTime() {
		return this.stopTime - this.startTime;
	}

	public static void main(String[] args) {
		SpeedGun gun = new SpeedGun();
		gun.start();
		String s = "";

		for (int i = 0; i < 1000000; ++i) {
			s = "Seoul Kangnam-Gu YeokSam-Dong HanLa Classic";
		}

		System.out.println(s);
		gun.stop();
		System.out.println(gun.getRunningTime());
	}
}
