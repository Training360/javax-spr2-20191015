package empapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHistoryDto {

    private Long id;

    private String name;

    private List<AddressDto> addresses;

    private LocalDateTime modifiedAt;

    private String type;

    private int revNumber;
}
