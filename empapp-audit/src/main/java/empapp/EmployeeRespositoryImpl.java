package empapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private EmployeeHistoryDto toHistoryDto(Object[] resultItem) {
        var modelMapper = new ModelMapper();
        var employee = (Employee) resultItem[0];
        var dto =  modelMapper.map(employee, EmployeeHistoryDto.class);
        var revision = (DefaultRevisionEntity) resultItem[1];
        dto.setRevNumber(revision.getId());
        dto.setModifiedAt(LocalDateTime.ofInstant(revision.getRevisionDate().toInstant(),
                ZoneId.systemDefault()));
        var type = (RevisionType) resultItem[2];
        dto.setType(type.toString());
        return dto;
    }
}
