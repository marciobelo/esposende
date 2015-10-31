package esposende.navegacao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
	/*
		Ou chromedriver em alguma pasta no path do sistema operacional.
		chromedriver específico para o sistema operacional deve ser baixado em
		https://code.google.com/p/chromedriver/downloads/list
	 */
	public WebDriver getWebDriver() {
		//System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		return webDriver;
	}
}
