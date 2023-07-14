package utils

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

class Loader(spark: SparkSession) {

  def readRawDataFromResourcesFolder(mode: String): Dataset[Row] = {
    require(mode.eq("csv") || mode.eq("parquet"), "undefined data source filetype")

    val rawDF: Dataset[Row] = if (mode == "csv")
      spark.read.options(Map(
      "header" -> "true",
      "delimiter" -> ",",
      //    "emptyValue" -> "null"
    )).csv("src/main/scala/resources/raw.csv") else spark.read.parquet("src/main/scala/resources/raw.parquet")


    rawDF
  }

  def castDataframe(data: Dataset[Row]): Dataset[Row] = {
    val castedData = data.select(
      col("ID").cast("integer").as("id"),
      col("Model Year").cast("integer").as("modelYear"),
      col("Make").cast("string").as("make"),
      col("Model").cast("string").as("model"),
      col("Estimated Annual Petrolum Consumption (Barrels)").cast("double").as("barrelsConsumption"),
      col("Fuel Type 1").cast("string").as("fuelType"),
      col("Fuel Type 2").cast("string").as("fuelType2"),
      col("City MPG (Fuel Type 1)").cast("double").as("cityMpg"),
      col("Highway MPG (Fuel Type 1)").cast("double").as("hwyMpg"),
      col("Combined MPG (Fuel Type 1)").cast("double").as("combinedMpg"),
      col("City MPG (Fuel Type 2)").cast("double").as("cityMpgFuel2"),
      col("Highway MPG (Fuel Type 2)").cast("double").as("hwyMpgFuel2"),
      col("Combined MPG (Fuel Type 2)").cast("double").as("combinedMpgFuel2"),
      col("Engine Cylinders").cast("integer").as("cylinders"),
      col("Engine Displacement").cast("integer").as("displacement"),
      col("Drive").cast("string").as("drive"),
      col("Engine Description").cast("string").as("engineDesc"),
      col("Transmission").cast("string").as("trans"),
      col("Vehicle Class").cast("string").as("class"),
      col("Time to Charge EV (hours at 120v)").cast("double").as("evCharge120v"),
      col("Time to Charge EV (hours at 240v)").cast("double").as("evCharge240v"),
      col("Range (for EV)").cast("double").as("evRange"),
      col("City Range (for EV - Fuel Type 1)").cast("double").as("cityRangeEVfuelType1"),
      col("City Range (for EV - Fuel Type 2)").cast("double").as("cityRangeEVfuelType2"),
      col("Hwy Range (for EV - Fuel Type 1)").cast("double").as("hwyRangeEVfuelType1"),
      col("Hwy Range (for EV - Fuel Type 2)").cast("double").as("hwyRangeEVfuelType2")
    )
    castedData
  }

  def fillEmptyConsumption(data: Dataset[Row]): Dataset[Row] ={
    data.na.fill(0, Array("cityMpg"))
      .na.fill(0, Array("hwyMpg"))
      .na.fill(0, Array("combinedMpg"))
      .na.fill(0, Array("cityMpgFuel2"))
      .na.fill(0, Array("hwyMpgFuel2"))
      .na.fill(0, Array("combinedMpgFuel2"))
  }


}
