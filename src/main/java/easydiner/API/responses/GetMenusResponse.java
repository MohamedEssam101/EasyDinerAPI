package easydiner.API.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMenusResponse {

    List<GetMenuResponse> menus;
}
