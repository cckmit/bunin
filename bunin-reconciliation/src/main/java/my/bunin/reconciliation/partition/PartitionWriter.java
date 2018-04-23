package my.bunin.reconciliation.partition;

import lombok.extern.slf4j.Slf4j;
import my.bunin.reconciliation.bean.ListResult;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import java.util.List;

@Slf4j
public class PartitionWriter extends JdbcBatchItemWriter<ListResult> {

  @Override
  public void write(List<? extends ListResult> items) throws Exception {
    log.info("writing: {}", items.size());
    super.write(items);
  }
}
