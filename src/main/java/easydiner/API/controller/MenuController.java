package easydiner.API.controller;

import easydiner.API.requests.AddMenuRequest;
import easydiner.API.requests.UpdateMenuRequest;
import easydiner.API.responses.AddMenuResponse;
import easydiner.API.responses.GetMenuResponse;
import easydiner.API.responses.GetMenusResponse;
import easydiner.API.responses.UpdateMenuResponse;
import easydiner.API.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Tag(name = "Menu API", description = "Operations related to menu items")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Add a new menu item", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new menu item"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("menus")
    public ResponseEntity<AddMenuResponse> addMenuItem(
            @Parameter(description = "Menu details", required = true) @Valid @ModelAttribute AddMenuRequest menuRequest,
            @Parameter(description = "Image file", required = true) @RequestParam("file") MultipartFile file) throws IOException {
        String fileContent = file.getContentType();
        byte[] imageData = file.getBytes();
        AddMenuResponse menuResponse = menuService.addMenuResponse(menuRequest, imageData);
        return ResponseEntity.ok().body(menuResponse);
    }

    @Operation(summary = "Update an existing menu item", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the menu item"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @PutMapping("menus/{menuId}")
    public ResponseEntity<UpdateMenuResponse> updateMenu(
            @Parameter(description = "Menu details", required = true) @Valid @RequestBody UpdateMenuRequest updateMenuRequest,
            @Parameter(description = "ID of the menu item to be updated", required = true) @PathVariable("menuId") int id) {
        UpdateMenuResponse menuResponse = menuService.updateMenuResponse(updateMenuRequest, id);
        return ResponseEntity.ok().body(menuResponse);
    }

    @Operation(summary = "Get details of a menu item", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved menu item details"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @GetMapping("menus/{menuId}")
    public ResponseEntity<GetMenuResponse> getMenuResponse(
            @Parameter(description = "ID of the menu item to be retrieved", required = true) @PathVariable("menuId") int id) {
        GetMenuResponse menuResponse = menuService.getMenuResponse(id);
        return ResponseEntity.ok().body(menuResponse);
    }

    @Operation(summary = "Get details of all menu items", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved menu items"),
            @ApiResponse(responseCode = "404", description = "No menu items found")
    })
    @GetMapping("menus")
    public ResponseEntity<GetMenusResponse> getMenusResponse() {
        GetMenusResponse menusResponse = menuService.getMenusResponse();
        return ResponseEntity.ok().body(menusResponse);
    }

    @Operation(summary = "Delete a menu item by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the menu item"),
            @ApiResponse(responseCode = "404", description = "Menu item not found")
    })
    @DeleteMapping("menus/{id}")
    public ResponseEntity<String> deleteMenuItem(
            @Parameter(description = "ID of the menu item to be deleted", required = true) @PathVariable("id") int id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok().body("Menu item with ID " + id + " is deleted successfully");
    }
}
