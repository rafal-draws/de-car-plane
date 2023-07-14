package utils

import org.apache.log4j.{Level, Logger}

class UtilityLogger {

  private val logger: Logger = Logger.getLogger(getClass.getName)

  def setLoggerLevelWARN(): Unit = logger.setLevel(Level.WARN)
  def logMakerDataframeCreation(manufacturer: String): Unit = logger.info(s"Creating $manufacturer parquet file")

}
