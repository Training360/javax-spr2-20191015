package empapp;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Flushable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<EmployeeDto> createEmployee(Mono<CreateEmployeeCommand> command) {
        return command
                .map(this::toEntity)
                .flatMap(e -> employeeRepository.save(e))
                .map(this::toDto);
    }

    public Employee toEntity(CreateEmployeeCommand command) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(command, Employee.class);
    }

    public Flux<EmployeeDto> listEmployees() {
        return employeeRepository.findAll().map(this::toDto);
    }

    public EmployeeDto toDto(Employee employee) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public Mono<EmployeeDto> findEmployeeById(String id) {
        return employeeRepository.findById(id)
                .map(this::toDto);
    }

    @Transactional
    public Mono<EmployeeDto> updateEmployee(String id, Mono<UpdateEmployeeCommand> command) {

        return employeeRepository.findById(id)
                .zipWith(command)
                .doOnNext(t -> t.getT1().setName(t.getT2().getName()))
                .map(t -> t.getT1())
                .doOnNext(e ->employeeRepository.save(e))
                .map(this::toDto);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
