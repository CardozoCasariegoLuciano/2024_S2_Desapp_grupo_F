package unq.cryptoexchange.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOperations {
    private LocalDateTime requestTime;
    private Double usTotal;
    private Double argTotal;
    private List<UserSingleOperationDto> operations;

    public UserOperations(double usTotal, double argTotal, List<UserSingleOperationDto> operations){
        this.requestTime = LocalDateTime.now();
        this.operations = operations;
        this.usTotal = usTotal;
        this.argTotal = argTotal;
    }
}

