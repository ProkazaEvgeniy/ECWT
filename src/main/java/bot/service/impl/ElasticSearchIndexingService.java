package bot.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import bot.entity.Product;
import bot.repository.search.ProductSearchRepository;
import bot.service.AdminService;

@Service
public class ElasticSearchIndexingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchIndexingService.class);
	
	@Value("${index.all.during.startup}")
	private boolean indexAllDuringStartup;
	
	@Autowired
	private ProductSearchRepository productSearchRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;
	
	@PostConstruct
	private void postConstruct(){
		if(indexAllDuringStartup) {
			LOGGER.info("Detected index all command");
			LOGGER.info("Clear old index");
			elasticsearchOperations.deleteIndex(Product.class);
			LOGGER.info("Start index of Products");
			for(Product p : adminService.findAllForIndexing()){
				productSearchRepository.save(p);
				LOGGER.info("Successful indexing of Product: " + p.getName());
			}
			LOGGER.info("************************* Finish index of Products");
		}
		else{
			LOGGER.info("************************* indexAllDuringStartup is disabled");
		}
	}
}
