package bot.repository.search;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import bot.entity.Product;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
	List<Product> findByDescriptionOrName(String description, String name);
}
