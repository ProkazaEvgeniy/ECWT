package bot.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import bot.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findByUrl(String text);
}
