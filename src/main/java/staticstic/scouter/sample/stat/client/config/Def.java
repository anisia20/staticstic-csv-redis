package staticstic.scouter.sample.stat.client.config;

public class Def {

	//Que name
	public static final String Q_SendMsg = "Q_SENDMSG";
	
	public static final String AGENT_KEY = "seckey";
	
	
	// Thread name
	public static final String T_Sample = "Sample";
	
	public static final String Q_Sample = "qSample";
	
	public static final Integer TS_DefaultMilli = 100;
	public static final Integer TS_DefaultFinalMilli = 1 * 1000;
	public static final Integer TS_DefaultTransErrorMilli = 100;
	public static final Integer TS_DefaultSleepMilli = 3000;
	public static final Integer TS_queueFullMilli = 10 * 1000;
	
	public static final String STATUS_NONE = "N";
	public static final String STATUS_SEND = "S";
	public static final String STATUS_FAIL = "F";
	public static final String STATUS_COMPLETE = "C";
	public static final String STATUS_SEND_OFF = "O";
	
	public static final String RSLT_REPUSH_FAIL = "REPUSH";
	public static final String TPS_BY_MSG = "distributeTPSList";
			
}
