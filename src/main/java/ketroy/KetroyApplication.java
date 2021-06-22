package ketroy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ketroy.model.Context;
import ketroy.thread.RunnerQueue;

public class KetroyApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		
		List<String> newPaths = Collections.synchronizedList(new ArrayList<String>());
		List<String> oldPaths = Collections.synchronizedList(new ArrayList<String>());
		
		newPaths.add("https://w124.zona.plus/");
		try {
			
				RunnerQueue runnerQueue = new RunnerQueue(newPaths, oldPaths);
				runnerQueue.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
