package unq.CryptoExchange.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.CryptoExchange.models.Example;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExampleDto {
    //aca la docu
    //https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary

    @NotBlank(message = "El campo nombre es obligatorio")
    @Size(message = "El campo nombre tiene que tener entre 3 y 20 caracteres", max = 20, min = 3)
    private String name;

    @Min(value = 0, message = "la cantidad no puede ser negativa")
    private int quantity = 0;

    public Example toModel(){
        return new Example(null, this.name, this.quantity);
    }
}
