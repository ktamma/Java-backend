package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@Embeddable
@Table(name = "order_rows")
public class OrderRow {

    @Column(name = "item_name")
    private String itemName;
    @NotNull
    @Positive
    private int price;
    @NotNull
    @Positive
    private int quantity;
}
