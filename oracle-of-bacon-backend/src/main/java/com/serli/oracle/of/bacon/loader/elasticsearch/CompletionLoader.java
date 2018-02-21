package com.serli.oracle.of.bacon.loader.elasticsearch;

import com.serli.oracle.of.bacon.repository.ElasticSearchRepository;
import com.serli.oracle.of.bacon.loader.elasticsearch.BulkHelper;

import org.elasticsearch.client.RestHighLevelClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;



public class CompletionLoader {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws IOException, InterruptedException {
    		if (args.length != 1) {
            System.err.println("Expecting 1 arguments, actual : " + args.length);
            System.err.println("Usage : completion-loader <actors file path>");
            System.exit(-1);
        }
        String inputFilePath = args[0];
        RestHighLevelClient client = ElasticSearchRepository.createClient();
       
        BulkHelper bulker = new BulkHelper();
    
        int bulkSize = 100000;
        
		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
		    bufferedReader
		            .lines()
		            .skip(1)
		            .forEach(line -> {
                       // System.out.println(line);
                        line.substring(1, line.length()-1);
			            count.incrementAndGet();
		                bulker.add(count.get(), line, "actors","actor","name");
			           // bulker.add(count.get(), line);
			            
		                if (count.get() % bulkSize ==0)  { 
                            try {
								bulker.execute(client);
								bulker.reset();
							} catch (IOException e) {
								e.printStackTrace();
							}   
		                }
		            });
		}
		bulker.execute(client);

        System.out.println("Inserted total of " + count.get() + " actors");

        client.close();
    }
}
