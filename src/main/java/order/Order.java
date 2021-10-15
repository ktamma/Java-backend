package order;

import item.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private long id;
    @EqualsAndHashCode.Exclude
    private String orderNumber;
    @EqualsAndHashCode.Exclude
    private List<Item> orderRows;

    public void addItem(Item item){
        if (item != null){
            orderRows.add(item);
        }
    }
}
