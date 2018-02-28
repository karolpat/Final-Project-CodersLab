package pl.coderslab.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.coderslab.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	/** Gives list of ints that are rooms id but only that which are connected to host given by id.
	 * @param id - id of user that represents hosts of rooms which id numbers are in the returned list.
	 * @return
	 */
	@Query(value="select room.id from room join room_host on rooms_as_host_id=room.id where host_id= ?", nativeQuery=true)
	List<BigInteger> findByUser(long id);
}
