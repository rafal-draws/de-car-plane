import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import play.api.libs.json._
import java.io.{File, FileWriter}
import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {

  val applicationConf: Config = ConfigFactory.load("application.conf")

  val browser: JsoupBrowser = new JsoupBrowser()
  val pageSource = browser.get(applicationConf.getString("service.url"))

  val rowsHTML = pageSource >> elementList("td") >> text

  val extractedData: List[Map[String, String]] = rowsHTML.grouped(4).toList.map(x =>
    Map(
      "country" -> x.head,
      "gasoline" -> x(1),
      "diesel" -> x(2),
      "lpg" -> x(3)
  ))

  val extractedDataJson = Json.stringify(Json.toJson(extractedData))

  val reportDate = pageSource >> text("h6") replace(" ", "-")

  val fw = new FileWriter(s"fuel-prices-$reportDate.json", true)
  try fw.write(extractedDataJson)
  finally fw.close()

}
