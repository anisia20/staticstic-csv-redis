package staticstic.scouter.sample.stat;

import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.extern.log4j.Log4j2;
import staticstic.scouter.sample.stat.client.components.ResourcesManager;
import staticstic.scouter.sample.stat.client.scheduler.Scheduler;
import staticstic.scouter.sample.stat.client.utils.JasyptShell;

@SpringBootApplication
@EnableScheduling
@Log4j2
@EnableEncryptableProperties
@ComponentScans(value = { 
		@ComponentScan("staticstic.scouter.sample"),
	})
public class AgentAplication {
	@Autowired
	ResourcesManager mResourcesManager;
	
	@Autowired
	Scheduler mScheduler;
	
	@Autowired
	ApplicationArguments  applicationArguments;
	
	final String sysNm = "Test";
	
	public static void main(String[] args) throws FileNotFoundException {
		//암복호화 쉘
		if("encdes".equalsIgnoreCase(System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME))) {
			JasyptShell.main(args);
			return;
		}
		
//		String path = System.getProperty("user.dir");
//		System.out.println("Working Directory = " + path);
	      
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "local");
		SpringApplication.run(AgentAplication.class, args);
	}

	@PostConstruct
	public void onStartup() {
		log.info("################ {} System-up start ################", sysNm);
		
		log.info("################ {} System-up complete ################", sysNm);
	}

	@PreDestroy
	public void onExit() {
		log.info("################ {} System down start ################", sysNm);
		try {
           //mScheduler.shutdown();
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("################ {} System down end ################", sysNm);
	}
	
}
