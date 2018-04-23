package my.bunin.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class XmlUtils {

  /**
   * convert xml to HashMap.
   */
  public static Map xml2Map(String xml) {
    try {
      Document document = DocumentHelper.parseText(xml);
      return (Map) fromElement(document.getRootElement());
    } catch (DocumentException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }

  }

  private static Object fromElement(Element element) {
    //noinspection unchecked
    List<Element> elements = element.elements();

    if (elements.size() == 0) {
      if (element.isRootElement()) {
        Map<String, Object> map = new HashMap<>();
        map.put(element.getName(), element.getText());
        return map;
      }
      return element.getText();
    } else {
      Map<String, Integer> nameMap = new HashMap<>();
      elements.forEach(e -> {
        if (nameMap.containsKey(e.getName())) {
          Integer value = Optional.of(nameMap.get(e.getName())).orElse(0);
          nameMap.put(e.getName(), value + 1);
        } else {
          nameMap.put(e.getName(), 1);
        }
      });

      if (nameMap.size() == 1 && nameMap.values().iterator().next() > 1) {
        List<Object> list = new ArrayList<>();
        elements.forEach(e -> list.add(fromElement(e)));

        if (element.isRootElement()) {
          Map<String, Object> map = new HashMap<>();
          map.put(nameMap.keySet().iterator().next(), list);
          return map;
        }

        return list;
      }

      Namespace namespace = elements.iterator().next().getNamespace();

      Map<String, Object> elementMap = new HashMap<>();

      nameMap.forEach((name, count) -> {
        //noinspection unchecked
        List<Element> elementList = element.elements(new QName(name, namespace));
        if (elementList.size() > 1) {
          List<Object> list = new ArrayList<>();
          elementList.forEach(it -> list.add(fromElement(it)));
          elementMap.put(name, list);
        } else {
          elementMap.put(name, fromElement(elementList.get(0)));
        }
      });

      return elementMap;

    }
  }

}
