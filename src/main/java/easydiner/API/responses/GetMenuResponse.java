package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMenuResponse extends MenuResponse{
    private String  image;
    public GetMenuResponse(int restaurantId, String itemName, String itemDescription, float price, String image) {
        super(restaurantId, itemName, itemDescription, price);
        this.image=image;
    }
}
