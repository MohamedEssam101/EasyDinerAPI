package easydiner.API.mapper;


import easydiner.API.model.RestaurantsEntity;
import easydiner.API.requests.RestaurantRequest;
import easydiner.API.responses.AddRestaurantResponse;
import easydiner.API.responses.GetRestaurantResponse;
import easydiner.API.responses.GetRestaurantsResponse;
import easydiner.API.responses.UpdateRestaurantResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantsMapperImpl implements RestaurantsMapper {
    @Override
    public RestaurantsEntity mapToRestaurantsEntity(RestaurantRequest request) {
        return  new RestaurantsEntity(
                request.getName(),
                request.getDescription(),
                request.getLocation());
    }

    @Override
        public AddRestaurantResponse mapToAddRestaurantResponse(RestaurantsEntity entity) {
        return new AddRestaurantResponse(
                entity.getName(),
                entity.getDescription(),
                entity.getLocation()
        );
    }

    @Override
    public UpdateRestaurantResponse mapToUpdateRestaurantResponse(RestaurantsEntity entity) {

        return new UpdateRestaurantResponse(
                entity.getName(),
                entity.getDescription(),
                entity.getLocation()
        );
    }

    @Override
    public GetRestaurantResponse mapToGetRestaurantResponse(RestaurantsEntity entity) {
        return new GetRestaurantResponse(
                entity.getName(),
                entity.getDescription(),
                entity.getLocation()
        );
    }

    @Override
    public GetRestaurantsResponse mapToGetRestaurantsResponse(List<RestaurantsEntity> entityList) {

        List<GetRestaurantResponse> responseList = entityList.stream()
                .map(entity -> new GetRestaurantResponse(
                        entity.getName(),
                        entity.getDescription(),
                        entity.getLocation())).collect(Collectors.toList());

        return new GetRestaurantsResponse(responseList);
    }
}
