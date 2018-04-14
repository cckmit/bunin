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

    public static void main(String[] args) {

//        SparkConf conf = new SparkConf().setAppName("Reconciliation");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        SQLContext sqlContext = new SQLContext(sc);
//
//        JavaRDD<String> list1RDD = sc.textFile("d:/tmp/list1.txt", 2);
//        JavaRDD<String> list2RDD = sc.textFile("d:/tmp/list2.txt", 2);

//        list1RDD.map(it -> it.split(",")).map(is -> new Row(is[0], is[1]))

//        SparkConf sparkConf = new SparkConf().setAppName("Reconciliation Spark").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(sparkConf);
//        JavaRDD<String> list1RDD = sc.textFile("D:/tmp/spark/list1.txt");
//
//        String fieldNames = "orderNo,orderName,status";
//        list1RDD.map(it -> it.split(",")).map(it -> new Row(it[0], it[1], it[2]))
//        ImmutableList.copyOf(fieldNames.split(",")).stream().map(it -> new StructField(it, DataType.class, true, Metadata.empty()))
//        new StructType(new List( fieldNames.split(","))

        SparkSession spark = SparkSession
                .builder()
                .appName("Reconciliation Spark")
                .master("local[*]")
                .getOrCreate();

        JavaRDD<ListRow> list1RowJavaRDD = spark
                .read()
                .textFile("D:/tmp/bigspark/list1.txt")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new ListRow(parts[0], parts[1], parts[2]);
                });

        Dataset<Row> list1RowDF = spark.createDataFrame(list1RowJavaRDD, ListRow.class);
//        list1RowDF.show(200);


        JavaRDD<ListRow> list2RowJavaRDD = spark
                .read()
                .textFile("D:/tmp/bigspark/list2.txt")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new ListRow(parts[0], parts[1], parts[2]);
                });

        Dataset<Row> list2RowDF = spark.createDataFrame(list2RowJavaRDD, ListRow.class);
//        list2RowDF.show(200);

        list1RowDF.createOrReplaceTempView("list1");
        list2RowDF.createOrReplaceTempView("list2");

        // output to file
//        Dataset<ListResult> dataset =
        spark
                .sql("select t1.orderNo as row_no1, t2.orderNo as row_no2, t1.orderName, t1.status as origin_status, t2.status as target_status from list1 t1 left outer join  list2 t2 on t1.orderNo = t2.orderNo")
                .map((MapFunction<Row, String>) row -> {
                    String originNo = row.getAs("row_no1");
                    String targetNo = row.getAs("row_no2");
                    String originStatus = row.getAs("origin_status");
                    String targetStatus = row.getAs("target_status");

                    String orderNo = Optional.ofNullable(originNo).orElse(targetNo);
                    String result;

                    if (Objects.isNull(targetNo) && "S".equals(originStatus)) {
                        result = "inventory losses";
                    } else if (Objects.isNull(originNo) && "S".equals(targetStatus) ) {
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
                                    StringUtils.rightPad(Optional.of(result).orElse(""), 16, " ") ,
                                    StringUtils.leftPad(Optional.ofNullable(orderNo).orElse(""), 10, "0") ,
                                    Optional.ofNullable(originStatus).orElse(""),
                                    Optional.ofNullable(targetStatus).orElse("")

                            );
//                            new ListResult(orderNo, originStatus, targetStatus, result);
                }
//                , Encoders.bean(ListResult.class)
                , Encoders.STRING())
//                .toDF("orderNo", "originStatus", "targetStatus", "result")
//                .sort("result", "orderNo")
//                .map((MapFunction<Row, String>) row -> Joiner
//                        .on(",")
//                        .join(
//                                Optional.ofNullable(row.get(0)).orElse(""),
//                                Optional.ofNullable(row.get(1)).orElse(""),
//                                Optional.ofNullable(row.get(2)).orElse(""),
//                                Optional.ofNullable(row.get(3)).orElse("")
//                        ), Encoders.STRING())
                .sort("value")
                .toJavaRDD()
                .coalesce(16)
                .saveAsTextFile("d:/tmp/spark/result"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));

        // output to db

//        spark
//                .sql("select t1.orderNo as row_no1, t2.orderNo as row_no2, t1.orderName, t1.status as origin_status, t2.status as target_status from list1 t1 left outer join  list2 t2 on t1.orderNo = t2.orderNo")
//                .map((MapFunction<Row, ListResult>) row -> {
//                            String originNo = row.getAs("row_no1");
//                            String targetNo = row.getAs("row_no2");
//                            String originStatus = row.getAs("origin_status");
//                            String targetStatus = row.getAs("target_status");
//
//                            String orderNo = Optional.ofNullable(originNo).orElse(targetNo);
//                            String result;
//
//                            if (Objects.isNull(targetNo) && "S".equals(originStatus)) {
//                                result = "inventory losses";
//                            } else if (Objects.isNull(originNo) && "S".equals(targetStatus) ) {
//                                result = "inventory profit";
//                            } else if (!originStatus.equals(targetStatus) && "F".equals(originStatus)) {
//                                result = "inventory profit";
//                            } else if (!originStatus.equals(targetStatus) && "F".equals(targetStatus)) {
//                                result = "inventory losses";
//                            } else {
//                                result = "balanced";
//                            }
//
////                            return Joiner
////                                    .on(",")
////                                    .join(
////                                            StringUtils.rightPad(Optional.of(result).orElse(""), 16, " ") ,
////                                            StringUtils.leftPad(Optional.ofNullable(orderNo).orElse(""), 10, "0") ,
////                                            Optional.ofNullable(originStatus).orElse(""),
////                                            Optional.ofNullable(targetStatus).orElse("")
////
////                                    );
//                           return new ListResult(orderNo, originStatus, targetStatus, result);
//                        }
//                , Encoders.bean(ListResult.class))
//                .write()
//                .format("jdbc")
//                .option("url", "jdbc:mysql://192.168.100.221:3306/bunin")
//                .option("dbtable","list_row"
//                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
//                .option("user","root")
//                .option("password","root")
//                .save();
////                .toDF("orderNo", "originStatus", "targetStatus", "result")
////                .sort("result", "orderNo")
////                .map((MapFunction<Row, String>) row -> Joiner
////                        .on(",")
////                        .join(
////                                Optional.ofNullable(row.get(0)).orElse(""),
////                                Optional.ofNullable(row.get(1)).orElse(""),
////                                Optional.ofNullable(row.get(2)).orElse(""),
////                                Optional.ofNullable(row.get(3)).orElse("")
////                        ), Encoders.STRING())



        System.out.println("Hi everyone!");

        spark.close();

        spark.sparkContext().stop();

//        Dataset<Row> df = spark.read().text("D:/tmp/spark/list1.txt");
//        df.show();

    }

    public static ListResult handle(Row row) {
        String originNo = row.getAs("row_no1");
        String targetNo = row.getAs("row_no2");
        String originStatus = row.getAs("origin_status");
        String targetStatus = row.getAs("target_status");


        return new ListResult(originNo, originStatus, targetStatus, "");
    }

    public static class ListResult implements Serializable{

        private static final long serialVersionUID = -2351685065791091987L;
        private String orderNo;
        private String originStatus;
        private String targetStatus;
        private String result;

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
