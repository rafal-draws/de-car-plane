package utils.UDFs

import org.apache.spark.SparkException
import org.apache.spark.sql.api.java.UDF1

 class MilesPerGallonToLitresPer100KilometreUDF extends UDF1[Double, Double]{

  private val mileInKm: Double = 1.609344
  private val galInLitre: Double = 3.78541178

  private val mpgToLp100km: Double = (galInLitre / mileInKm) * 100

  override def call(mpg: Double): Double = {
    try{
     BigDecimal(mpgToLp100km / mpg).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    } catch {
     case e: NumberFormatException => 0
    }

  }


}
