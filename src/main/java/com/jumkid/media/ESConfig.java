package com.jumkid.media;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

/**
 * Created at 17 Sep, 2018$
 *
 * @author chooliyip
 **/
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jumkid.media.repository")
public class ESConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.cluster.name}")
    private String EsClusterName;

    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.builder()
                .put("cluster.name", EsClusterName)
                .build();

        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
        TransportClient client = new PreBuiltTransportClient(esSettings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));

        return client;

    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }

    //Embedded Elasticsearch Server
    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }*/


}
