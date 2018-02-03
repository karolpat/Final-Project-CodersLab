package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
