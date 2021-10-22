package order

import item.Item
import lombok.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class Order(
        var id: Long = 0,

        @field:EqualsAndHashCode.Exclude
        @field:NotNull
        @field:Size(min = 2)
        var orderNumber: String? = null,

        @field:EqualsAndHashCode.Exclude
        @field:Valid
        var orderRows:  MutableList<Item>? = null,
)
