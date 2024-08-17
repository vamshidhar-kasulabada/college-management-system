package  com.vamshidhar.cms.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vamshidhar.cms.dto.IdsDTO;
import com.vamshidhar.cms.dto.projections.*;
import com.vamshidhar.cms.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {

    Set<StudentProjection> findByNameContains(String name);

    
    @Query("SELECT s FROM StudentEntity s")
    Set<StudentProjection> findAllStudentProjections(Sort sort);

    @Query("SELECT s FROM StudentEntity s order by id")
    Set<StudentProjection> findAllStudentProjections();


    Set<StudentProjection> findByIdIn(Set<Long> studentIds);


    @Query("SELECT s FROM StudentEntity s where s.id=:id")
    Optional<StudentProjection> findByIdProjection(Long id);
}
