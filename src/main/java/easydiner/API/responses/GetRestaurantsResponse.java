package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GetRestaurantsResponse {

    private List<GetRestaurantResponse> responseList;
}
