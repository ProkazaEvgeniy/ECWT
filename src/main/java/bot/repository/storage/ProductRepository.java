package bot.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import bot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findByPhoto(String photo);
}
