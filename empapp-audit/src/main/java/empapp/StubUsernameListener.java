package empapp;

import org.hibernate.envers.RevisionListener;

public class StubUsernameListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        EmployeeRevisionEntity employeeRevisionEntity = (EmployeeRevisionEntity) revisionEntity;
        employeeRevisionEntity.setUsername("admin");
    }
}
