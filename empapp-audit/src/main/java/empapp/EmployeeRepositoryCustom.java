package empapp;

import java.util.List;

public interface EmployeeRepositoryCustom {

    List<EmployeeHistoryDto> findEmployeeHistoryById(long id);
}
