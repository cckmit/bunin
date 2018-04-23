package reconciliation;

import com.google.common.base.Joiner;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class ReconciliationTest {

  /**
   * reconciliation test.
   */
  public static void main(String[] args) {

    SparkSession spark = SparkSession
        .builder()
        .appName("Reconciliation Spark")
        .master("local[*]")
        .getOrCreate();

    JavaRDD<ListRow> list1RowJavaRdd = spark
        .read()
        .textFile("D:/tmp/bigspark/list1.txt")
        .javaRDD()
        .map(line -> {
          String[] parts = line.split(",");
          return new ListRow(parts[0], parts[1], parts[2]);
        });

    Dataset<Row> list1RowDf = spark.createDataFrame(list1RowJavaRdd, ListRow.class);

    JavaRDD<ListRow> list2RowJavaRdd = spark
        .read()
        .textFile("D:/tmp/bigspark/list2.txt")
        .javaRDD()
        .map(line -> {
          String[] parts = line.split(",");
          return new ListRow(parts[0], parts[1], parts[2]);
        });

    Dataset<Row> list2RowDf = spark.createDataFrame(list2RowJavaRdd, ListRow.class);

    list1RowDf.createOrReplaceTempView("list1");
    list2RowDf.createOrReplaceTempView("list2");

    spark
        .sql("select t1.orderNo as row_no1, t2.orderNo as row_no2, t1.orderName, "
            + "t1.status as origin_status, t2.status as target_status "
            + "from list1 t1 left outer join  list2 t2 on t1.orderNo = t2.orderNo")
        .map((MapFunction<Row, String>) row -> {
          String originNo = row.getAs("row_no1");
          String targetNo = row.getAs("row_no2");
          String originStatus = row.getAs("origin_status");
          String targetStatus = row.getAs("target_status");

          String orderNo = Optional.ofNullable(originNo).orElse(targetNo);
          String result;

          if (Objects.isNull(targetNo) && "S".equals(originStatus)) {
            result = "inventory losses";
          } else if (Objects.isNull(originNo) && "S".equals(targetStatus)) {
            result = "inventory profit";
          } else if (!originStatus.equals(targetStatus) && "F".equals(originStatus)) {
            result = "inventory profit";
          } else if (!originStatus.equals(targetStatus) && "F".equals(targetStatus)) {
            result = "inventory losses";
          } else {
            result = "balanced";
          }

          return Joiner
              .on(",")
              .join(
                  StringUtils.rightPad(Optional.of(result).orElse(""), 16, " "),
                  StringUtils.leftPad(Optional.ofNullable(orderNo).orElse(""), 10, "0"),
                  Optional.ofNullable(originStatus).orElse(""),
                  Optional.ofNullable(targetStatus).orElse("")

              );
        }, Encoders.STRING())
        .sort("value")
        .toJavaRDD()
        .coalesce(16)
        .saveAsTextFile("d:/tmp/spark/result"
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));


    System.out.println("Hi everyone!");

    spark.close();

    spark.sparkContext().stop();

  }

  /**
   * handle row.
   */
  public static ListResult handle(Row row) {
    String originNo = row.getAs("row_no1");
    String originStatus = row.getAs("origin_status");
    String targetStatus = row.getAs("target_status");


    return new ListResult(originNo, originStatus, targetStatus, "");
  }

  public static class ListResult implements Serializable {

    private static final long serialVersionUID = -2351685065791091987L;
    private String orderNo;
    private String originStatus;
    private String targetStatus;
    private String result;

    /**
     * ListResult constructor.
     */
    public ListResult(String orderNo, String originStatus, String targetStatus, String result) {
      this.orderNo = orderNo;
      this.originStatus = originStatus;
      this.targetStatus = targetStatus;
      this.result = result;
    }

    @Override
    public String toString() {
      return Joiner.on(",").join(Optional.ofNullable(orderNo).orElse(""),
          Optional.ofNullable(originStatus).orElse(""),
          Optional.ofNullable(targetStatus).orElse(""),
          Optional.ofNullable(result).orElse(""));
    }

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getOriginStatus() {
      return originStatus;
    }

    public void setOriginStatus(String originStatus) {
      this.originStatus = originStatus;
    }

    public String getTargetStatus() {
      return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
      this.targetStatus = targetStatus;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }
  }

  public static class ListRow {
    private String orderNo;
    private String orderName;
    private String status;

    /**
     * ListRow constructor.
     */
    public ListRow(String orderNo, String orderName, String status) {
      this.orderNo = orderNo;
      this.orderName = orderName;
      this.status = status;
    }

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getOrderName() {
      return orderName;
    }

    public void setOrderName(String orderName) {
      this.orderName = orderName;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}
