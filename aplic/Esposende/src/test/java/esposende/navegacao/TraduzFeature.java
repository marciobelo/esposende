package esposende.navegacao;

import cucumber.annotation.pt.Dado;
import cucumber.annotation.pt.Entao;
import cucumber.annotation.pt.Quando;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TraduzFeature {

	public static final String RAIZ_SISTEMA = "http://localhost:8080/Esposende/";
	private WebDriver webDriver;

	@Dado("^que estou em (.*)")
	public void que_estou_em(String pagina) {
		webDriver = new WebDriverFactory().getWebDriver();
		webDriver.get(RAIZ_SISTEMA + pagina);
	}

	@Quando("^informo (.*) no campo (.*)")
	public void informo_no_campo(String value, String element) {
		webDriver.findElement(By.id(element)).clear();
		webDriver.findElement(By.id(element)).sendKeys(value);
	}

    @Quando("^coloco uma imagem no campo (.*)")
    public void coloco_uma_imagem_no_campo(String campo){
        webDriver.findElement(By.id(campo)).sendKeys("C:\\TCC\\aplic\\Esposende\\src\\main\\webapp\\resources\\sem_imagem.gif");
    }

	@Quando("^clico em (.*)")
	public void clico_em(String element) {
		webDriver.findElement(By.id(element)).click();
	}

	@Entao("^saio")
	public void saio() {
		webDriver.close();
	}

	@Entao("^devo ver (.*)")
	public void devo_ver(String element) {
		Assert.assertTrue(webDriver.findElement(By.id(element)).isDisplayed());
	}

	@Entao("^devo ser redirecionado para (.*)")
	public void devo_ser_redirecionado_para(String url) {
		Assert.assertEquals(RAIZ_SISTEMA + url, webDriver.getCurrentUrl());
	}

}
