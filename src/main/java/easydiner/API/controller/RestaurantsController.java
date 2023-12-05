package easydiner.API.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import easydiner.API.requests.AddRestaurantRequest;
import easydiner.API.requests.UpdateRestaurantRequest;
import easydiner.API.responses.AddRestaurantResponse;
import easydiner.API.responses.GetRestaurantResponse;
import easydiner.API.responses.GetRestaurantsResponse;
import easydiner.API.responses.UpdateRestaurantResponse;
import easydiner.API.service.RestaurantsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@AllArgsConstructor
@Tag(name = "Restaurants API", description = "Operations related to restaurants")
public class RestaurantsController {

    private final RestaurantsService restaurantsService;

    @Operation(summary = "Add a new restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new restaurant"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("restaurants")
    public ResponseEntity<AddRestaurantResponse> addRestaurant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Restaurant details", required = true) @Valid @RequestBody AddRestaurantRequest request) {
        log.info("Create restaurant API: {}", request);
        AddRestaurantResponse response = restaurantsService.addRestaurantResponse(request);
        log.info("Inserted AddRestaurantResponse successfully as: {}", response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update an existing restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the restaurant"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PutMapping("restaurants/{id}")
    public ResponseEntity<UpdateRestaurantResponse> updateRestaurant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated restaurant details", required = true) @Valid @RequestBody UpdateRestaurantRequest updateRestaurantRequest,
            @Parameter(description = "ID of the restaurant to be updated", required = true) @PathVariable("id") int id) {
        log.info("Updating restaurant API: {} for ID: {}", updateRestaurantRequest, id);
        UpdateRestaurantResponse restaurantResponse =
                restaurantsService.updateRestaurantResponse(updateRestaurantRequest, id);
        log.info("Returning updated UpdateRestaurantResponse successfully as: {}", restaurantResponse);
        return ResponseEntity.ok().body(restaurantResponse);
    }

    @Operation(summary = "Get details of a specific restaurant", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant details"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("restaurants/{id}")
    public ResponseEntity<GetRestaurantResponse> getRestaurant(
            @Parameter(description = "ID of the restaurant to be retrieved", required = true) @PathVariable("id") int id) {
        log.info("GetRestaurant API for ID: {}", id);
        GetRestaurantResponse getRestaurantResponse = restaurantsService.getRestaurantResponse(id);
        log.info("Returning GetRestaurantResponse successfully as: {}", getRestaurantResponse);
        return ResponseEntity.ok().body(getRestaurantResponse);
    }

    @Operation(summary = "Get details of all restaurants", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurants"),
            @ApiResponse(responseCode = "404", description = "No restaurants found")
    })
    @GetMapping("restaurants")
    public ResponseEntity<GetRestaurantsResponse> getRestaurants() {
        log.info("GetRestaurants API");
        GetRestaurantsResponse restaurantsResponse = restaurantsService.getRestaurantsResponse();
        log.info("Returning GetRestaurants API list as: {}", restaurantsResponse);
        return ResponseEntity.ok().body(restaurantsResponse);
    }

    @Operation(summary = "Delete a restaurant by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the restaurant"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("restaurants/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @Parameter(description = "ID of the restaurant to be deleted", required = true) @PathVariable("id") int id) {
        log.info("DeleteRestaurant API for ID: {}", id);
        restaurantsService.deleteRestaurant(id);
        log.info("Deleted restaurant successfully");

        String message = "Restaurant with ID " + id + " is deleted successfully";

        // Return a response with the message
        return ResponseEntity.ok(message);
    }
}
