package order;

import item.Item;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private long id;
    @EqualsAndHashCode.Exclude
    @NotNull
    @Size(min = 2)
    private String orderNumber;
    @EqualsAndHashCode.Exclude
    @Valid
    private List<Item> orderRows;

    public void addItem(Item item){
        if (item != null){
            orderRows.add(item);
        }
    }
}
