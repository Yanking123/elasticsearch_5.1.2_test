package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * Hello world!
 *
 */
public class SatisfyTest extends EsJdbcDaoSupport {

	public  static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		SatisfyTest st =new SatisfyTest();
//		st.updateIndex();
//		st.createSDRIndex();
//		st.testDelete();
//		st.putSDR();
//		st.testJDBC();
//		st.testDelete();
		st.updateSDR();
		st.getHealth();
//		st.testDeleteIndex();
	}

	public  void createSDRIndex() throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder()
				.startObject()
				.startObject("settings")
				.field("number_of_shards", 5)// ���÷�Ƭ����
				.field("number_of_replicas", 0)// ���ø�������
				.endObject()
					.startObject("mappings")
						.startObject("TEST3")// type����
								.startObject("properties") // �����������ĵ������ԡ�
							.startObject("QS_ENTERPRISE_ID").field("type", "keyword").endObject()
							.startObject("QS_SESSION_ID").field("type", "keyword").endObject()
							.startObject("QS_ID").field("type", "keyword").endObject()
								.endObject()
						.endObject()
					.endObject()
					.endObject();

		TransportClient client =EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate("test3")// index����
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println("Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
		client.close();
	}

	public void updateIndex(){
		TransportClient client =EsConnectionFactory.createEsClient();
		client.admin().indices().preparePutMapping("test3")   
        .setType("TEST3")                                
        .setSource("{\n" +                              
                "  \"properties\": {\n" +
                "    \"QS_SESSION_ID\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}")
        .get();
	}
	
	
	
	public  void putSDR() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("QS_ID", "20160829152817");
		json.put("QS_ENTERPRISE_ID", "ctdiznh1b3rnbg");
		json.put("QS_SESSION_ID", "fhnxzt13015-hui3");
		insert("test3", "TEST3", json);
		System.out.println("insert success.");
	}
	
	public  void  updateSDR() {
//		Map<String, Object> json = new HashMap<String, Object>();
//		json.put("SS_TIME", "20160829152817");
//		json.put("SS_ENTERPRISE_ID", "ctdiznh1b3rnbg");
//		json.put("SS_SESSION_ID", "fhnxzt13015");
//		json.put("SS_AGENT_ID", "ctdiznh1b3raget#mcs_cd5752786745fe80b3f7ced4de648d0a");
//		json.put("SS_SATISFY_SCORE", 1);
//		json.put("SS_APP_NAME", "aa588");
//		json.put("SS_VISITOR_ID", "ctdiznh1b3rhui#aa588_web3178352725333775");
//		json.put("SS_WORKGROUP_ID", "ctdiznh1b3shzxhui#service5752786745fe80b3f7ced4de648d0a");
////		insert("st_agent_satisfy_sdr", "ST_AGENT_SATISFY_SDR", json);
		SatisfySDREntity s=new SatisfySDREntity();
//		s.setSsTime("20160829152817");
//		s.setSsEnterpriseId("ctdiznh1b3rnbg");
//		s.setSsAgentId("ctdiznh1b3raget#mcs_cd5752786745fe80b3f7ced4de648d0a");
//		s.setSsAppName("aa588");
//		s.setSsVisitorId("ctdiznh1b3rhui#aa588_web3178352725333775");
//		s.setChannelNo("2001");
//		s.setWorkgroupId("wenhuicshizu");
//		s.setSsSatisfyScore(90);
//		s.setSsSessionId("fhnxzt13015-wenhui");
		update("test3", "TEST3", s);
//		System.out.println("update success.");
	}
    public  void testJDBC() throws Exception {
    	String sql="SELECT * from test3 group by QS_SESSION_ID";
    	ResultSet rs=query(sql,"test3");
  try{
	  while (rs.next()) {
    	  System.out.println( rs.getString("QS_SESSION_ID") );
//          System.out.println( rs.getString("QS_ENTERPRISE_ID") );
     
    }
  }catch(SQLException e ){
		 String error=e.getCause().toString();
		 if(error.indexOf("IndexOutOfBoundsException")>0){
				System.out.println("data is null");
		 }
  
  }
      
    }
    public  void testDelete() throws Exception {
    	System.out.println(delete ("test3","fhnxzt13015-hui"));
    }
    
  public void testDeleteIndex(){
		TransportClient client =EsConnectionFactory.createEsClient();
	      DeleteIndexResponse dResponse = client.admin().indices().prepareDelete("test2")
              .execute().actionGet();
	      System.out.println(dResponse.isAcknowledged());
  }
}
