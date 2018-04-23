package my.bunin.core;

public enum BankAcronym {

  ABC("中国农业银行"),
  BOC("中国银行"),
  BOCOM("交通银行"),
  CCB("中国建设银行"),
  ICBC("中国工商银行"),
  CMB("招商银行"),
  CIB("兴业银行"),
  CMBC("中国民生银行"),
  CGB("广州发展银行"),
  PAB("平安银行"),

  SPDB("上海浦东发展银行"),
  HXB("华夏银行"),
  BOB("北京银行"),
  BOSC("上海银行"),
  HFB("恒丰银行"),
  PSBC("中国邮政储蓄银行"),
  CEB("中国光大银行"),
  CNCB("中信银行"),
  CZB("浙商银行"),
  CBHB("渤海银行"),
  HSB("徽商银行"),
  SRCB("上海农村商业银行");

  private String name;

  BankAcronym(String name) {
    this.name = name;
  }
}
