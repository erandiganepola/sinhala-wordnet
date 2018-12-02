package org.sinhala.wordnet.wordnetDB.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import static org.sinhala.wordnet.css.common.Constants.Properties.DB_HOST;
import static org.sinhala.wordnet.css.common.Constants.Properties.DB_NAME;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    private Mongo mongo;

    @Override
    public String getDatabaseName() {
        return System.getProperty(DB_NAME, "WordNet");
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        //mongo =new MongoClient("192.248.15.236");
        String host = System.getProperty(DB_HOST, "localhost");
        mongo = new MongoClient(host);
        return mongo;
    }

    public void close() {
        mongo.close();
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }
}