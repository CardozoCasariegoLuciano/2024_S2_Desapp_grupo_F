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
    private double usTotal;
    private double argTotal;
    private List<UserSingleOperationDto> operations;

    public UserOperations(double us_total, double arg_total, List<UserSingleOperationDto> operations){
        this.requestTime = LocalDateTime.now();
        this.operations = operations;
        this.usTotal = us_total;
        this.argTotal = arg_total;
    }
}

