package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.coderslab.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String name);
	
	Role findBySubName(String subname);
}
