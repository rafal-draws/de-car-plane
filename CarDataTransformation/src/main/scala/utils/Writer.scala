package utils

import org.apache.spark.sql.{AnalysisException, Dataset, Row, SparkSession}

class Writer (spark: SparkSession){

  def writeSample(data: Dataset[Row], limit: Int): Unit = {
    data.limit(limit).write.parquet("src/main/scala/resources/sample.parquet")
  }

  def writeRaw(data: Dataset[Row]): Unit = {
    data.write.parquet("src/main/scala/resources/raw.parquet")
  }

  def writeByManufacturer(data: Dataset[Row], manufacturer: String): Unit = {
    try {
      data.write.parquet(s"src/main/scala/resources/cars/$manufacturer/data.parquet")
    } catch {
      case e: AnalysisException => println("File already exists!")
    }
    }
}
