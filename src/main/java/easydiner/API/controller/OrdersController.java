package easydiner.API.controller;

import easydiner.API.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import easydiner.API.requests.AddOrderRequest;
import easydiner.API.requests.UpdateOrderRequest;
import easydiner.API.responses.AddOrderResponse;
import easydiner.API.responses.GetOrderResponse;
import easydiner.API.responses.GetOrdersResponse;
import easydiner.API.responses.UpdateOrderResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Orders API", description = "Operations related to orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(summary = "Add a new order", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new order"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("orders")
    public ResponseEntity<AddOrderResponse> addOrder(
            @RequestBody @Valid AddOrderRequest orderRequest) {
        log.info("Inside addOrder for {}", orderRequest);
        AddOrderResponse orderResponse = ordersService.addOrder(orderRequest);
        return ResponseEntity.ok().body(orderResponse);
    }

    @Operation(summary = "Get details of a specific order", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order details"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("orders/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrderResponse(
            @PathVariable("orderId") int orderId) {
        log.info("Get Order API for id: {}", orderId);
        GetOrderResponse orderResponse = ordersService.getOrderResponse(orderId);
        return ResponseEntity.ok().body(orderResponse);
    }

    @Operation(summary = "Update an existing order", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the order"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("orders/{orderId}")
    public ResponseEntity<UpdateOrderResponse> updateOrderRequest(
            @PathVariable("orderId") int orderId,
            @RequestBody @Valid UpdateOrderRequest updateRequest) {
        log.info("Update Order API for id: {}", orderId);
        UpdateOrderResponse orderResponse = ordersService.updateOrderResponse(updateRequest, orderId);
        return ResponseEntity.ok().body(orderResponse);
    }

    @Operation(summary = "Get details of all orders", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved orders"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    @GetMapping("/orders")
    public ResponseEntity<GetOrdersResponse> getOrdersResponse() {
        log.info("Get Orders API");
        GetOrdersResponse ordersResponses = ordersService.getOrdersResponse();
        return ResponseEntity.ok().body(ordersResponses);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> deleteOrderResponse(@PathVariable("orderId") int id){
        log.info("deleting order with id {}",id);
        ordersService.deleteOrder(id);
        return ResponseEntity.ok().body("Order Deleted Successfully");
    }
}
