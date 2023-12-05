package easydiner.API.mapper;

import easydiner.API.model.MenuEntity;
import easydiner.API.model.RestaurantsEntity;
import easydiner.API.repository.RestaurantsRepository;
import easydiner.API.requests.MenuRequest;
import easydiner.API.responses.AddMenuResponse;
import easydiner.API.responses.GetMenuResponse;
import easydiner.API.responses.GetMenusResponse;
import easydiner.API.responses.UpdateMenuResponse;
import lombok.AllArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Component
public class MenuMapperImpl implements MenuMapper{



    private  final RestaurantsRepository restaurantsRepository;
    @Override
    public MenuEntity mapToMenuEntity(MenuRequest menuRequest, int id) {
        RestaurantsEntity restaurant = restaurantsRepository.findById(menuRequest.getRestaurantId());

        return new MenuEntity(
                id,
                restaurant,
                menuRequest.getItemName(),
                menuRequest.getItemDescription(),
                menuRequest.getPrice()
        );
    }

    @Override
    public MenuEntity mapToMenuEntitywithImage(MenuRequest menuRequest, byte[] imageData) {
        RestaurantsEntity restaurant = restaurantsRepository.findById(menuRequest.getRestaurantId());

        return new MenuEntity(
                restaurant,
                menuRequest.getItemName(),
                menuRequest.getItemDescription(),
                menuRequest.getPrice(),
                imageData
        );
    }

    @Override
    public AddMenuResponse mapToAddMenuResponse(MenuEntity menuEntity) {
        return new AddMenuResponse(
                menuEntity.getRestaurantId().getRestaurantId(),
                menuEntity.getName(),
                menuEntity.getDescription(),
                menuEntity.getPrice(),
                menuEntity.getMenu_image()
        );
    }

    @Override
    public UpdateMenuResponse mapToUpdateMenuResponse(MenuEntity menuEntity) {
        return new UpdateMenuResponse(
                menuEntity.getRestaurantId().getRestaurantId(),
                menuEntity.getName(),
                menuEntity.getDescription(),
                menuEntity.getPrice(),
                menuEntity.getMenu_image()
        );
    }

    @Override
    public GetMenuResponse mapToGetMenuResponse(MenuEntity menuEntity) {
        Tika tika = new Tika();
        String mimeType = tika.detect(menuEntity.getMenu_image());
        String base64Image = Base64.getEncoder().encodeToString(menuEntity.getMenu_image());
        String dataUri = "data:" + mimeType + ";base64," + base64Image;
        return new GetMenuResponse(
                menuEntity.getRestaurantId().getRestaurantId(),
                menuEntity.getName(),
                menuEntity.getDescription(),
                menuEntity.getPrice(),
                dataUri
        );
    }

    @Override
    public GetMenusResponse mapToGetMenusResponse(List<MenuEntity> menuEntityList) {
        Tika tika = new Tika();
        List<GetMenuResponse> menuResponseList = menuEntityList.stream()
                .map(menuEntity -> {
                    String dataUri = null;
                    if (menuEntity.getMenu_image() != null) {
                        String mimeType = tika.detect(menuEntity.getMenu_image());
                        String base64Image = Base64.getEncoder().encodeToString(menuEntity.getMenu_image());
                        dataUri = "data:" + mimeType + ";base64," + base64Image;
                    }

                    return new GetMenuResponse(
                            menuEntity.getRestaurantId().getRestaurantId(),
                            menuEntity.getName(),
                            menuEntity.getDescription(),
                            menuEntity.getPrice(),
                            dataUri
                    );
                })
                .collect(Collectors.toList());

        return new GetMenusResponse(menuResponseList);
    }


}
