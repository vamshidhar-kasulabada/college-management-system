package  com.vamshidhar.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamshidhar.cms.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
    
}
