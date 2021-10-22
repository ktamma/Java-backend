package item

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Positive


data class Item (
    var itemName: String? = null,
    @field:NotNull @field:Positive
    var quantity: Int = 0,
    @field:NotNull @field:Positive
    var price: Int = 0,
)