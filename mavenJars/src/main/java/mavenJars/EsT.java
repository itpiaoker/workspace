package mavenJars;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Map;

public class EsT {

	TransportClient esClient;

	@Before
	public void beffor() throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "zdp_es_cluster").build();
		esClient = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.10.100.71"), 9300));
	}

	@Test
	public void queryMappings() throws Exception {
		ImmutableOpenMap<String, MappingMetaData> mappings = esClient.admin().cluster().prepareState().execute()
				.actionGet().getState().getMetaData().getIndices().get("bank").getMappings();
//		Map mapping = mappings.get("account").getSourceAsMap();
		Map mapping2 = mappings.get("account").get().getSourceAsMap();
		mapping2.get("property");
//				.source().toString();
	}


	@Test
	public void query() throws Exception {
		SearchResponse searchResponse = esClient.prepareSearch("bank")
				.setTypes("account")
				.setSize(10)
				//这个游标维持多长时间
				.setScroll(TimeValue.timeValueMinutes(8))
				.execute().actionGet();
		System.out.println(searchResponse.getHits().getTotalHits());
		System.out.println(searchResponse.getHits().hits());
		for (SearchHit hit : searchResponse.getHits()) {
			Map m1 = hit.sourceAsMap();
			Map m2 = hit.getSource();
			Map m3 = hit.getFields();
			String s = hit.getSourceAsString();
			System.out.println("EsT.query");
		}
	}


	@Test
	public void query2() throws Exception {
//		BoolQueryBuilder bool= QueryBuilders.boolQuery();
		SearchResponse searchResponse = esClient.prepareSearch("bank")
				.setTypes("account")
				.setSize(10)
				//这个游标维持多长时间
				.setScroll(TimeValue.timeValueMinutes(8))
				.execute().actionGet();
		System.out.println(searchResponse.getHits().getTotalHits());
		System.out.println(searchResponse.getHits().hits());
		for (SearchHit hit : searchResponse.getHits()) {
			hit.fields();
			System.out.println(hit.getSourceAsString());
			Map map = hit.getSource();
			System.out.println(hit.getSource());
			Map fields = hit.getFields();
			System.out.println(hit.getFields());
		}
//		while(true){
//			for (SearchHit hit : searchResponse.getHits()) {
//				System.out.println(hit.getSourceAsString());
//			}
//			searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
//					.setScroll(TimeValue.timeValueMinutes(8))
//					.execute().actionGet();
//			if (searchResponse.getHits().getHits().length == 0) {
//				break;
//			}
//		}
	}

	@After
	public void after(){

	}


}
