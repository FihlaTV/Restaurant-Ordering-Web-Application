package TheApp275Final.term.config;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import TheApp275Final.term.dao.DaemonTaskDao;



public class DaemonTask {
	
	@Autowired
	private DaemonTaskDao  daemonTaskDao;
	
	@Async
	public void doAsyncTask(){
		daemonTaskDao.updateStatusToP();
		daemonTaskDao.updateStatusToR();
		daemonTaskDao.updateStatusToF();
		
	}
	
	@Scheduled(cron="*/10 * * * * ?")
	public void runDaemon(){	
		  doAsyncTask();
		 
		}
		
}
