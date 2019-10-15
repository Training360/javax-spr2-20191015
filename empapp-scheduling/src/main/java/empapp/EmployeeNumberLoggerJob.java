package empapp;


import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class EmployeeNumberLoggerJob extends QuartzJobBean {

    private EmployeeService employeeService;

    public EmployeeNumberLoggerJob(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
       log.info("Number of employees: " + employeeService.listEmployees().size());
    }
}
