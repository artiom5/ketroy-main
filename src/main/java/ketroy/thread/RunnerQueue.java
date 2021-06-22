package ketroy.thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ketroy.model.Context;

public class RunnerQueue extends Thread {
	List<String> tmpTitles = new ArrayList<String>();
    private Context context;
    private Random rand = new Random();
	
	public RunnerQueue(List<String> newPaths, List<String> oldPaths) throws IOException {
		context = new Context(newPaths, oldPaths);
    }
    
    public void run() {
        while (context.isRunning() &&
        		context.getUrl().contains("https://w124.zona.plus/")) {
        	getPage();
        	System.out.println("Page");
        	getTitle();
        	System.out.println("title");
        	saveUniqueLinks();
        	System.out.println("Link");
        	getNextUrl();
        	System.out.println("NextUrl");
        }
        quitDriver();
    }

    private void quitDriver() {
    	if (context.getDriver() != null) {
    		context.setTitles(tmpTitles);
    		File myFile = new File("src/main/java/myfile.txt");
    		System.out.println("File created : " + myFile);
    		FileWriter myWriter; 
    	      try {
    	    	myWriter = new FileWriter(myFile);  
				myWriter.write(String.join(" ", tmpTitles));
				myWriter.close();
				System.out.println("File closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	      System.out.println("Thread finished. Closing driver");
    		context.getDriver().quit();
    	}
    }
    
    private void saveUniqueLinks() {
//    	List<WebElement> webElements = context.getDriver().findElements(By.cssSelector("*"));
//
//    	for ( WebElement element : webElements ) {
//    	  String path = element.getAttribute("href");
//    	  if (isUniqueElement(path)) {
//    		  context.getNewPaths().add(path);
//    	  }
//    	}
    	
    	List<WebElement> webElements = context.getDriver().findElements(By.xpath("//body//a"));
    	if (webElements != null && !webElements.isEmpty()) {
    		for (WebElement webElement : webElements) {
    			String path = webElement.getAttribute("href");
    			if (isUniqueElement(path)) {
	        		context.getNewPaths().add(path);
	        	}
    		}
    	}
    }
    
    private void getPage() {
    	context.getDriver().get(context.getUrl());
    }
    
    private boolean isUniqueElement(String path) {
		try {
			if (path != null) {
				URL pathUrl = new URL(path);
				if (pathUrl != null) {
					return pathUrl.getHost() != null && pathUrl.getHost().equals(context.getHost())
						&& !context.getNewPaths().contains(path) && !context.getOldPaths().contains(path);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	
		return false;
    }
    
	private void getNextUrl() {
		if (!context.getOldPaths().contains(context.getUrl())) {
			context.getOldPaths().add(context.getUrl());
		}
		if (!context.getNewPaths().isEmpty()) {
			context.setUrl(getRandomUrl());
			context.getNewPaths().remove(context.getUrl());
    	} else {
    		stopRunning();
    	}
	}
	
	private String getRandomUrl() {
		String url = context.getNewPaths().get(rand.nextInt(context.getNewPaths().size()));
		if (url.contains("https://w124.zona.plus/")) {
			return url;
		} 
		return "https://w124.zona.plus/";
	}
	
	private void stopRunning() {
		context.setRunning(false);
	}
	
	private void getTitle() {
		
//		File myFile = new File("src/main/java/myfile.txt");
//		List<WebElement> webElements = context.getDriver().findElements(By.xpath("//body//a"));
//		if (webElements != null && !webElements.isEmpty()) {
//			for (WebElement webElement : webElements) {
//				String path = webElement.getAttribute("h1");
//				FileUtils.write(myFile, path);
//			}
//		}
		if (context != null && context.getUrl() != null &&
				context.getUrl().contains("https://w124.zona.plus/movies/") && 
				context.getDriver().getPageSource().contains("js-title")) {
		WebElement tmpElement = context.getDriver().findElement(By.className("js-title"));
		System.out.println("tmpElement : " + tmpElement);
		String title = tmpElement.getText();
		System.out.println("title : "  + title);
		tmpTitles.add(title);
		System.out.println("tmpTitles : " + tmpTitles);
		System.out.println("tmpTitles.size : " + tmpTitles.size());
		} else {
			System.out.println("This is not movie page");
		}
	}
}
