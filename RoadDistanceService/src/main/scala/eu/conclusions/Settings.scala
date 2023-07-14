package eu.conclusions

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}


object Settings {

  val BASE_URL = "https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route="

  def driverSetup(): ChromeDriver = {

    WebDriverManager.chromedriver().setup()

    val chromeOptions = new ChromeOptions()
    chromeOptions.addArguments("--no-sandbox")
    chromeOptions.addArguments("--headless")
    chromeOptions.addArguments("disable-gpu")

    new ChromeDriver(chromeOptions)
  }

}
