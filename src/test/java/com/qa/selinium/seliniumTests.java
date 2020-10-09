package com.qa.selinium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class seliniumTests {

	private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\avini\\git\\ToDoList\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 768));

    }

    @Test
    public void testCreateList() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/toDoList/html/index.html");
        Thread.sleep(1000);

        //make shopping list
        WebElement create = driver.findElement(By.xpath("/html/body/div/button"));
        create.click();
        Thread.sleep(1000);

        WebElement listName = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/form/div/input"));
        listName.sendKeys("Shopping");
        WebElement createList = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/form/button"));
        createList.click();
        Thread.sleep(1000);
        
        WebElement listMadeSuccess = driver.findElement(By.xpath("/html/body/div/table[2]/thead/tr[2]/td[2]"));
        Thread.sleep(1000);
        assertEquals("Shopping", listMadeSuccess.getText());
        Thread.sleep(1000);
        
        //make task
        WebElement create1 = driver.findElement(By.xpath("/html/body/div/button"));
        create1.click();
        Thread.sleep(1000);
        WebElement taskName = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[1]/input"));
        taskName.sendKeys("Eggs");
        WebElement taskType = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[2]/input"));
        taskType.sendKeys("Important");
        WebElement taskTListID = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[3]/input"));
        taskTListID.sendKeys("1");
        WebElement createTask = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/button"));
        createTask.click();
        Thread.sleep(1000);
        WebElement taskMadeSuccess = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[2]"));
        Thread.sleep(1000);
        assertEquals("Eggs", taskMadeSuccess.getText());
        Thread.sleep(500);
        
        // create chocolate
        WebElement createTaskButton1 = driver.findElement(By.xpath("/html/body/div/button"));
        createTaskButton1.click();
        Thread.sleep(500);
        WebElement taskName1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[1]/input"));
        taskName1.sendKeys("Chocolate");
        WebElement taskType1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[2]/input"));
        taskType1.sendKeys("Can wait");
        WebElement taskTListID1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[3]/input"));
        taskTListID1.sendKeys("1");
        WebElement createTask1 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/button"));
        createTask1.click();
        Thread.sleep(1000);
        WebElement taskMadeSuccess1 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[3]/td[2]"));
        Thread.sleep(1000);
        assertEquals("Chocolate", taskMadeSuccess1.getText());
        Thread.sleep(500);
        
        //make reminder list
        WebElement create2 = driver.findElement(By.xpath("/html/body/div/button"));
        create2.click();
        Thread.sleep(1000);

        WebElement listName2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/form/div/input"));
        listName2.sendKeys("Reminders");
        WebElement createList2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/form/button"));
        createList2.click();
        Thread.sleep(1000);
        
        WebElement listMadeSuccess2 = driver.findElement(By.xpath("/html/body/div/table[2]/thead/tr[3]/td[2]"));
        Thread.sleep(500);
        assertEquals("Reminders", listMadeSuccess2.getText());
        Thread.sleep(500);
        
     // create feed dog
        WebElement createTaskButton2 = driver.findElement(By.xpath("/html/body/div/button"));
        createTaskButton2.click();
        Thread.sleep(500);
        WebElement taskName2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[1]/input"));
        taskName2.sendKeys("Feed Dog");
        WebElement taskType2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[2]/input"));
        taskType2.sendKeys("ASAP");
        WebElement taskTListID2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[3]/input"));
        taskTListID2.sendKeys("2");
        WebElement createTask2 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/button"));
        createTask2.click();
        Thread.sleep(1000);
        WebElement taskMadeSuccess2 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[4]/td[2]"));
        Thread.sleep(500);
        assertEquals("Feed Dog", taskMadeSuccess2.getText());
        Thread.sleep(500);
        
     // create empty storage
        WebElement createTaskButton3 = driver.findElement(By.xpath("/html/body/div/button"));
        createTaskButton3.click();
        Thread.sleep(500);
        WebElement taskName3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[1]/input"));
        taskName3.sendKeys("Empty Storage");
        WebElement taskType3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[2]/input"));
        taskType3.sendKeys("Can wait");
        WebElement taskTListID3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[3]/input"));
        taskTListID3.sendKeys("2");
        WebElement createTask3 = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/button"));
        createTask3.click();
        Thread.sleep(500);
        WebElement taskMadeSuccess3 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[5]/td[2]"));
        Thread.sleep(500);
        assertEquals("Empty Storage", taskMadeSuccess3.getText());
        Thread.sleep(500);
        
        // change eggs to medium eggs and make priority
        WebElement viewButton = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[4]/a"));
        viewButton.click();
        Thread.sleep(500);
        WebElement updateTaskName = driver.findElement(By.xpath("/html/body/div/form/div[2]/input"));
        updateTaskName.sendKeys("-Medium");
        WebElement updateTaskType = driver.findElement(By.xpath("/html/body/div/form/div[3]/input"));
        updateTaskType.sendKeys(" **");
        WebElement updateButton = driver.findElement(By.xpath("/html/body/div/form/button"));
        updateButton.click();
        WebElement reviewButton = driver.findElement(By.xpath("/html/body/div/a"));
        reviewButton.click();
        WebElement taskMadeSuccess4 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[2]"));
        Thread.sleep(1000);
        assertEquals("Eggs-Medium", taskMadeSuccess4.getText());
        Thread.sleep(500);
        
        // delete eggs from reminders
        WebElement delButton = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[5]/button"));
        delButton.click();
        WebElement taskMadeSuccess5 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[1]"));
        Thread.sleep(1000);
        assertEquals("2", taskMadeSuccess5.getText());
        Thread.sleep(500);
        
        // delete shopping list
        WebElement delListButton = driver.findElement(By.xpath("/html/body/div/table[2]/thead/tr[2]/td[5]/button"));
        delListButton.click();
        delListButton.click();
        Thread.sleep(500);
        WebElement taskMadeSuccess6 = driver.findElement(By.xpath("/html/body/div/table[1]/thead/tr[2]/td[1]"));
        Thread.sleep(1000);
        assertEquals("3", taskMadeSuccess6.getText());
        Thread.sleep(500);
        
        
        

        
    }
    

    @AfterAll
    public static void tearDown() {
        driver.close();
    }
}
