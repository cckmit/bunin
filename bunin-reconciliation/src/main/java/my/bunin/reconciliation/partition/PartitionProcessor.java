package my.bunin.reconciliation.partition;

import my.bunin.reconciliation.bean.ListResult;
import org.springframework.batch.item.ItemProcessor;

public class PartitionProcessor implements ItemProcessor<ListResult, ListResult> {
  @Override
  public ListResult process(ListResult item) {
    return item;
  }
}
