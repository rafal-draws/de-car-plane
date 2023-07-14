# Data Engineering and Data Mining with Scala
Jobs used in Airplane Car comparison engine

## Car Data Transformation
Spark job prepared for local development. 
Uses Loader class to load predefined Car Data dataset for cars ranging from 1984 to 2023, either csv or parquet. Then, using Loader class, it casts the Dataframe for optimalization, and fills empty conspumption data (due to EVs and other types of fuel)
Then, it uses Transformer class to transform MPG to L/100 KM with the use of Spark's UDF functionality. 
At last, the job extracts all Car Manufacturers unique labels, and maps Writer class function to write the data by manufacturer, creating a target folder under
**src/main/scala/resources/cars/$manufacturer/data.parquet**. 
The point of saving data like this is to provide a readable structure for HDFS file system for future analysis.

## Fuel Data Scraper

Uses ruippeixotog.scalascraper to extract fuel prices data from URL, saves it in a List[Map[String, String]] Format and extracts it as a .json file.

## Road Distance Service

Program written in Scala, using Maven to build (due to ChromeDriver's WebDriver Manager provided by Bonigarcia for Maven exclusively).
Uses ChromeDriver in headless state to gather road distance between latitude and longtitude of two points.
