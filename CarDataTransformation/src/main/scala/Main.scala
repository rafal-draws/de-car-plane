import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Dataset, Row, SparkSession}


object Main {

  def main(args: Array[String]): Unit = {

    import utils._

    val spark: SparkSession = SparkSession.builder()
      .appName("CarTransformerData")
      .master("local[*]")
      .getOrCreate()

    val loader: Loader = new Loader(spark)
    val writer: Writer = new Writer(spark)
    val transformer: Transformer = new Transformer(spark)
    val logger: UtilityLogger = new UtilityLogger


    val rawData: Dataset[Row] = loader.readRawDataFromResourcesFolder("csv")
    val castedData: Dataset[Row] = loader.castDataframe(rawData)
    val emptyFilledData: Dataset[Row] = loader.fillEmptyConsumption(castedData)

    val transformed: Dataset[Row] = transformer.transformMPGtoLitresPer100KM(emptyFilledData)


    val carMakerArray: List[String] = transformed.select("make").distinct().rdd.map(f => f.getString(0)).collect().toList

    carMakerArray.foreach(make => {
      logger.logMakerDataframeCreation(make)
      writer.writeByManufacturer(transformed.filter(col("make") === make), make)
    }
    )


  }

}
