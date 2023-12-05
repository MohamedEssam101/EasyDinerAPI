package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class UpdateMenuResponse extends MenuResponse{

    private byte[] image;
    public UpdateMenuResponse(int restaurantId, String itemName, String itemDescription, float price, byte[] image) {
        super(restaurantId, itemName, itemDescription, price);
        this.image=image;
    }
}
