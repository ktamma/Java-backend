package mapper;

import order.Order;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMapper {


    public <T> T parseObj(String input, Class<T> classType) throws IllegalAccessException, InstantiationException {
        Map<String, String> map = getStringStringMap(input);

        Field[] fields = classType.getDeclaredFields();

        T outPutObj = classType.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(int.class)) {
                field.set(outPutObj, Integer.parseInt(map.get(field.getName())));
            } else {
                field.set(outPutObj, map.get(field.getName()));
            }
        }
        return outPutObj;
    }


    public Order parse(String input) {
        Map<String, String> map = getStringStringMap(input);

        Order outPut = new Order(0,
                map.get("orderNumber")
        );
        return outPut;

    }

    private Map<String, String> getStringStringMap(String input) {
        input = input.replace("{", "");
        input = input.replace("}", "");
        String[] pairs = input.split(",");
        Map<String, String> map = new LinkedHashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");

            map.put(key, value);
        }
        return map;
    }

    public String stringify(Order order) {
        /*StringBuilder pair = new StringBuilder("");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("firstName", person.getFirstName());
        map.put("lastName", person.getLastName());
        map.put("age", String.valueOf(person.getAge()));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pair.append(String.format("\"%s\":\"%s\"",
                    entry.getKey(), entry.getValue()));
        }
        return pair.toString();
*/
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", String.valueOf(Long.valueOf(order.id)));
        map.put("orderNumber", order.orderNumber);


        String pairs = map.entrySet()
                .stream()
                .map(e -> String.format("\"%s\":\"%s\"",
                        e.getKey(), e.getValue()))
                .collect(Collectors.joining(","));
        return String.format("{%s}", pairs);

    }
}
