package my.bunin.trade.merchant.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

  Merchant findMerchantByNo(String no);
}
