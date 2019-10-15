package empapp;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private TaskScheduler taskScheduler;

    private MailService mailService;

    public EmployeeService(EmployeeRepository employeeRepository , MailService mailService, TaskScheduler taskScheduler) {
        this.employeeRepository = employeeRepository;
        this.taskScheduler = taskScheduler;
        this.mailService = mailService;
    }

    // */10 * * * * ?

    @Scheduled(cron = "*/10 * * * * ?")
    public void logCount() {
        log.info("Number of emploees:" + employeeRepository.findAll().size());
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(command.getName());
        ModelMapper modelMapper = new ModelMapper();
        if (command.getAddresses() != null) {
            employee.addAddresses(command.getAddresses().stream().map(a -> modelMapper.map(a, Address.class)).collect(Collectors.toList()));
        }
        employeeRepository.save(employee);

        taskScheduler.schedule(() -> mailService.sendMail(command.getName()),
                Instant.from(LocalDateTime.now().plus(5, ChronoUnit.SECONDS)));

        return modelMapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> listEmployees() {
        ModelMapper modelMapper = new ModelMapper();
        return employeeRepository.findAllWithAddresses().stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto findEmployeeById(long id) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeRepository.findByIdWithAddresses(id)
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id)),
                EmployeeDto.class);
    }

    @Transactional
    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employeeToModify = employeeRepository.getOne(id);
        employeeToModify.setName(command.getName());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeToModify, EmployeeDto.class);
    }

    public EmployeeDto deleteEmployee(long id) {
        var employee = employeeRepository.findByIdWithAddresses(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
