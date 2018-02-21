package com.serli.oracle.of.bacon.loader.elasticsearch;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;

public class BulkHelper {

	private BulkRequest bulk;
	
	public BulkHelper() {
		this.bulk = new BulkRequest();
	}
	
	public void execute(RestHighLevelClient client) throws IOException{
		client.bulk(bulk);
		try {
			client.bulk(bulk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(int index, String line, String indexSt, String type, String field) {
		bulk.add(new IndexRequest(indexSt, type, type+index)
				.source(XContentType.JSON, field, line));
	}
	
    /*public void add(int index, String line) {
        bulk.add(new IndexRequest("actors", "actor", "actor"+index)
                .source(XContentType.JSON, "name", line));
}*/
	
	public void reset() {
		this.bulk = new BulkRequest();
	}
	
	
}
