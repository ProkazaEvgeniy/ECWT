package bot.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import bot.entity.Cocktail;
import bot.repository.search.CocktailSearchRepository;
import bot.service.AdminService;

@Service
public class ElasticSearchIndexingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchIndexingService.class);
	
	@Value("${index.all.during.startup}")
	private boolean indexAllDuringStartup;
	
	@Autowired
	private CocktailSearchRepository cocktailSearchRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;
	
	@PostConstruct
	private void postConstruct(){
		if(indexAllDuringStartup) {
			LOGGER.info("Detected index all command");
			LOGGER.info("Clear old index");
			elasticsearchOperations.deleteIndex(Cocktail.class);
			LOGGER.info("Start index of Cocktails");
			for(Cocktail c : adminService.findAllForIndexing()){
				cocktailSearchRepository.save(c);
				LOGGER.info("Successful indexing of Cocktail: " + c.getName());
			}
			LOGGER.info("Finish index of Cocktails");
		}
		else{
			LOGGER.info("indexAllDuringStartup is disabled");
		}
	}
}
