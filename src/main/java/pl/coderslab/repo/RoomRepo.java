package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Advert;
import pl.coderslab.entity.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {

//	List<Room> findAllByPromoted(boolean promoted);
//
//	List<Room> findAllByOwnerUsername(String username);
//
//	List<Room> findAllByHostUsername(String username);
}
