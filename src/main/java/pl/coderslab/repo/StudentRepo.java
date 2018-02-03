package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
