package item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Item {
        private String itemName;
        @NotNull
        @Positive
        private int quantity;
        @NotNull
        @Positive
        private int price;

    }
