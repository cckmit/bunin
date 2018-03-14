package my.bunin.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;

import java.util.*;

@Slf4j
public class XmlUtils {

    public static Map xml2Map(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            return (Map)fromElement(document.getRootElement());
        } catch (DocumentException e) {
            throw new RuntimeException(e.getMessage(), e);
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
                    Integer v = Optional.of(nameMap.get(e.getName())).orElse(0);
                    nameMap.put(e.getName(), v + 1);
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

            nameMap.forEach((n, i) -> {
                //noinspection unchecked
                List<Element> elementList = element.elements(new QName(n, namespace));
                if (elementList.size() > 1) {
                    List<Object> list = new ArrayList<>();
                    elementList.forEach(e -> list.add(fromElement(e)));
                    elementMap.put(n, list);
                } else {
                    elementMap.put(n, fromElement(elementList.get(0)));
                }
            });

            return elementMap;

        }
    }

}
