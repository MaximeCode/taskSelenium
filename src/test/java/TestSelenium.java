import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.TimeUnit;

public class TestSelenium {

    public WebDriver driver;
    public WebDriverWait wait;
    public WebElement element;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();

        //System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test1() throws InterruptedException {

        driver.get("http://www.rgs.ru");
        driver.findElement(By.linkText("Меню")).click();
        driver.findElement(By.linkText("ДМС")).click();

        String expected = "ДМС — добровольное медицинское страхование";
        element = driver.findElement(By.xpath("//h1"));

        wait = new WebDriverWait(driver, 3);

        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertEquals(expected, element.getText());
        driver.findElement(By.xpath("//a[contains(text(), 'Отправить заявку')]")).click();

        expected = "Заявка на добровольное медицинское страхование";
        element = driver.findElement(By.xpath("//h4/b"));
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertEquals(expected, element.getText());

        driver.findElement(By.name("LastName")).sendKeys("Пупкин");
        driver.findElement(By.name("FirstName")).sendKeys("Василий");
        driver.findElement(By.name("MiddleName")).sendKeys("Иванович");
        Select select = new Select(driver.findElement(By.name("Region")));
        select.selectByVisibleText("Москва");
        driver.findElement(By.xpath("//label[text()='Телефон']/../input")).sendKeys(" \b4951230000");
        driver.findElement(By.name("Email")).sendKeys("qwertyqwerty");
        driver.findElement(By.name("ContactDate")).sendKeys(" \b10.10.2020");
        driver.findElement(By.name("Comment")).sendKeys("Бла-бла-бла");
        driver.findElement(By.xpath("//label[contains(text(), 'согласен')]/../input")).click();

        Assert.assertEquals("Пупкин", driver.findElement(By.name("LastName")).getAttribute("value"));
        Assert.assertEquals("Василий", driver.findElement(By.name("FirstName")).getAttribute("value"));
        Assert.assertEquals("Иванович", driver.findElement(By.name("MiddleName")).getAttribute("value"));
        Assert.assertEquals("Москва", select.getFirstSelectedOption().getText());
        Assert.assertEquals("+7 (495) 123-00-00", driver.findElement(By.xpath("//label[text()='Телефон']/../input"))
                .getAttribute("value"));
        Assert.assertEquals("qwertyqwerty", driver.findElement(By.name("Email")).getAttribute("value"));
        Assert.assertEquals("10.10.2020", driver.findElement(By.name("ContactDate")).getAttribute("value"));
        Assert.assertEquals("Бла-бла-бла", driver.findElement(By.name("Comment")).getAttribute("value"));
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), 'Я согласен')]/../input")).isSelected());

        driver.findElement(By.xpath("//button[contains(text(), 'Отправить')]")).click();

        expected = "Введите адрес электронной почты";
        Assert.assertEquals(expected, driver.findElement(By.xpath("//input[@name='Email']/..//span")).getText());
    }

    @Test
    public void test2() throws InterruptedException {

        driver.get("http://www.sberbank.ru/ru/person");
        driver.findElement(By.xpath("//a[@class='hd-ft-region']")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Нижегородская')]")).click();

        wait = new WebDriverWait(driver, 3);

        element =  driver.findElement(By.xpath("//a[@class='hd-ft-region']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertEquals("Нижегородская область", element.getText());

        ((Locatable) driver.findElement(By.xpath("//footer"))).getCoordinates().inViewPort();

        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_fb']"))
                .isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_tw']"))
                .isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_yt']"))
                .isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_ins']"))
                .isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_vk']"))
                .isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//footer//span[@class='footer__social_logo footer__social_ok']"))
                .isDisplayed());
    }

    @After
    public void after() throws InterruptedException {
        driver.quit();
    }
}
