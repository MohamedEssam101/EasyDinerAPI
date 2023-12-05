package easydiner.API.mapper;

import easydiner.API.model.MenuEntity;
import easydiner.API.requests.MenuRequest;
import easydiner.API.responses.AddMenuResponse;
import easydiner.API.responses.GetMenuResponse;
import easydiner.API.responses.GetMenusResponse;
import easydiner.API.responses.UpdateMenuResponse;


import java.util.List;

public interface MenuMapper {

    MenuEntity mapToMenuEntity(MenuRequest menuRequest, int id);
    MenuEntity mapToMenuEntitywithImage(MenuRequest menuRequest, byte[] imageData);
    AddMenuResponse mapToAddMenuResponse(MenuEntity menuEntity);

    UpdateMenuResponse mapToUpdateMenuResponse(MenuEntity menuEntity);
    GetMenuResponse mapToGetMenuResponse(MenuEntity menuEntity);

    GetMenusResponse mapToGetMenusResponse(List<MenuEntity> menuEntityList);
}
