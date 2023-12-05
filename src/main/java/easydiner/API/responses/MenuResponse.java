package easydiner.API.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MenuResponse{

        private int restaurantId;
        private String itemName;
        private String itemDescription;

        private float price;
}
