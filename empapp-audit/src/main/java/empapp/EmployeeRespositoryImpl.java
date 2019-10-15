package empapp;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeRespositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EmployeeHistoryDto> findEmployeeHistoryById(long id) {
        AuditReader reader = AuditReaderFactory.get(em);
        List<Object[]> result = reader.createQuery()
                .forRevisionsOfEntity(Employee.class, false, true)
                .add(AuditEntity.id().eq(id)).getResultList();


    }
}
