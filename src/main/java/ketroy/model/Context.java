package ketroy.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Context {

	private boolean running = true;
	private WebDriver driver = new FirefoxDriver();
	private List<String> oldPaths = new ArrayList<String>();
	private List<String> newPaths = new ArrayList<String>();
	private List<String> titles = new ArrayList<String>();

	private String url;
    private String host;
    
	public Context(List<String> newPaths, List<String> oldPaths) {
		this.newPaths = newPaths;
		this.oldPaths = oldPaths;
		
		if (newPaths != null && !newPaths.isEmpty()) {
			try {
				host = new URL(newPaths.get(0)).getHost();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			url = newPaths.get(0);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getOldPaths() {
		return oldPaths;
	}

	public void setOldPaths(List<String> oldPaths) {
		this.oldPaths = oldPaths;
	}

	public List<String> getNewPaths() {
		return newPaths;
	}

	public void setNewPaths(List<String> newPaths) {
		this.newPaths = newPaths;
	}
	
	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
}
