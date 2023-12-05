package easydiner.API.mapper;

import easydiner.API.model.RestaurantsEntity;
import easydiner.API.requests.RestaurantRequest;
import easydiner.API.responses.AddRestaurantResponse;
import easydiner.API.responses.GetRestaurantResponse;
import easydiner.API.responses.GetRestaurantsResponse;
import easydiner.API.responses.UpdateRestaurantResponse;

import java.util.List;

public interface RestaurantsMapper {

    RestaurantsEntity mapToRestaurantsEntity(RestaurantRequest request);

    AddRestaurantResponse mapToAddRestaurantResponse(RestaurantsEntity entity);

    UpdateRestaurantResponse mapToUpdateRestaurantResponse(RestaurantsEntity entity);

    GetRestaurantResponse mapToGetRestaurantResponse(RestaurantsEntity entity);


    GetRestaurantsResponse mapToGetRestaurantsResponse(List<RestaurantsEntity> entityList);
}
