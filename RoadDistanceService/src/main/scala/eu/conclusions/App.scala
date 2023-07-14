package eu.conclusions
import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import org.openqa.selenium.chrome.ChromeDriver

/**
 * @author rafal-draws
 */
object App {
  
  def main(args : Array[String]): Unit = {
    require(args.length == 4)

    import eu.conclusions.Settings._

    val browser: Browser = JsoupBrowser()
    val driver: ChromeDriver = driverSetup()

    val urlWithArgs: String = s"$BASE_URL${args(0)}%2C${args(1)}%3B${args(2)}%2C${args(3)}"

    driver.get(urlWithArgs)

    Thread.sleep(3000)

    val driverSource: String = driver.getPageSource
    val source: browser.DocumentType = browser.parseString(driverSource)

    driver.quit()

    val infoParagraph: String = source >> element("#sidebar_content") >> element("p") >> text

    val kmDistance: String = infoParagraph.split("\\.")(0).substring(10)
    val driveTime: String = infoParagraph.split("\\.")(1).substring(6)

    println(s"distance in km = $kmDistance\ndrive time in hours = $driveTime")



  }

}
