package mapper;

import order.Order;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return Order.builder()
                .id(0)
                .orderNumber(map.get("orderNumber"))
                .build();

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

        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", String.valueOf(Long.valueOf(order.getId())));
        map.put("orderNumber", order.getOrderNumber());


        String pairs = map.entrySet()
                .stream()
                .map(e -> String.format("\"%s\":\"%s\"",
                        e.getKey(), e.getValue()))
                .collect(Collectors.joining(","));
        return String.format("{%s}", pairs);

    }
}
