package com.crazyfish.kamal.test.testsolrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by kamal on 15/9/8.
 */
public class  TestSolrj{


    public static void main(String args[]) throws IOException, SolrServerException {
//        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
//        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        String urlString = "http://localhost:8983/solr/gettingstarted";
        SolrClient solr = new HttpSolrClient(urlString);
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "5521992");
        document.addField("name", "Gouda cheese wheel2");
        document.addField("price", "49.992");
        UpdateResponse response = solr.add(document);

        solr.commit();

        SolrQuery query = new SolrQuery();
        query.setQuery("Gouda cheese");
        //query.setRequestHandler("/spellCheckCompRH");
        QueryResponse resp = solr.query(query);
        SolrDocumentList list = resp.getResults();
        System.out.println("list:" + list);
    }
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    }

