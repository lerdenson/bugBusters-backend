package hakaton.hakaton_spring_boot.controller.dto;

import java.util.List;

public record StartRequestDto (List<String> themes, String difficulty){
}
