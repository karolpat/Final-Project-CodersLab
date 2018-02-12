package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
