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
    private LocalDateTime request_time;
    private double us_total;
    private double arg_total;
    private List<UserSingleOperationDto> operations;

    public UserOperations(double us_total, double arg_total, List<UserSingleOperationDto> operations){
        this.request_time = LocalDateTime.now();
        this.operations = operations;
        this.us_total = us_total;
        this.arg_total = arg_total;
    }
}

