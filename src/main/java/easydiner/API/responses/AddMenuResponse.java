package easydiner.API.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddMenuResponse extends MenuResponse {

    private byte[] image;
    public AddMenuResponse(int restaurantId, String itemName, String itemDescription, float price, byte[] image) {
        super(restaurantId, itemName, itemDescription, price);
        this.image = image;
    }
}
