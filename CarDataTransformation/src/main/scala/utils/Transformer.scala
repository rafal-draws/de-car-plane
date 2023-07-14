package utils

import org.apache.spark.sql.functions.{call_udf, col}
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import utils.UDFs.MilesPerGallonToLitresPer100KilometreUDF

class Transformer(spark: SparkSession) {


  private val mpgConvertUDF: MilesPerGallonToLitresPer100KilometreUDF = new MilesPerGallonToLitresPer100KilometreUDF()
  spark.udf.register("mpgConvertUDF", mpgConvertUDF, DataTypes.DoubleType)

  def transformMPGtoLitresPer100KM(data: Dataset[Row]): Dataset[Row] = {
    data.createOrReplaceTempView("dataTable")
    val unsorted: Dataset[Row] = data.withColumn(
      "city", call_udf("mpgConvertUDF", col("cityMpg"))
    )
      .withColumn(
        "hwy", call_udf("mpgConvertUDF", col("hwyMpg"))
      )
      .withColumn(
        "combined", call_udf("mpgConvertUDF", col("combinedMpg"))
      )
      .withColumn(
        "cityFuel2", call_udf("mpgConvertUDF", col("cityMpgFuel2"))
      )
      .withColumn(
        "hwyFuel2", call_udf("mpgConvertUDF", col("hwyMpgFuel2"))
      )
      .withColumn(
        "combinedFuel2", call_udf("mpgConvertUDF", col("combinedMpgFuel2"))
      )

    val sorted: Dataset[Row] = unsorted
      .drop("cityMpg",
        "hwyMpg",
        "combinedMpg",
        "cityMpgFuel2",
        "hwyMpgFuel2",
        "combinedMpgFuel2")
      .select("id", "modelYear", "make","model",
        "fuelType", "fueltype2",
        "city", "hwy", "combined",
        "cityFuel2", "hwyFuel2", "combinedFuel2",
        "cylinders", "displacement",
        "drive", "engineDesc", "trans", "class",
        "evCharge120v", "evCharge240v", "evRange",
        "cityRangeEVfuelType1", "cityRangeEVfuelType2",
        "hwyRangeEVfuelType1", "hwyRangeEVfuelType2")

    sorted
  }


}
