package snc.server.ide.test.Elasticsearch;



import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import snc.server.ide.pojo.Class;
import snc.server.ide.pojo.Commodity;

import snc.server.ide.pojo.Gift;
import snc.server.ide.tttt.pojo.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


public class Elasticsearch5 {
    public static TransportClient client = null;
    public final static String HOST = "127.0.0.1";
    public final static int PORT = 9300;
     InetSocketTransportAddress node =null;
     //创建连接
     @Before
     public void getClient() {
         {
             try {
                 node = new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT);
                 Settings settings = Settings.builder()
                         .put("cluster.name", "elasticsearch")
                         .build();
                 client = new PreBuiltTransportClient(settings);
                 client.addTransportAddress(node);
                 System.out.println("创建成功");
             } catch (UnknownHostException e) {
                 System.out.println("创建数据库中出现错误");
                 e.printStackTrace();
             }
         }

     }
     //创建用户
    public  void createUser(User user){
        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = jsonBuilder();
            //每一个filed里面存的就是列名和列值
            jsonBuild.startObject().field("N", user.getN()).field("B", user.getB()).field("E",user.getE())
                    .field("BPH",user.getBPH()).field("FC",user.getFC()).field("ADD", user.getADD())
                    .field("CM", user.getCM()).field("GM", user.getGM()).field("BC", user.getBC())
                    .field("PWD", "asda").field("HD", "asasd").endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
            IndexResponse response = client.prepareIndex("user", "Userinfo",user.getUid()).setSource(jsonBuild).get();
        client.close();
    }

    //创建礼物
    public  void createGift(Gift gift){
//        Gift gift = new Gift();
//        gift.setG_id("as");
//        gift.setG_stock("ASd");
//        gift.setG_col("asd");
//        gift.setG_stock("as");
//        gift.setG_detaile("AS");
//        gift.setG_image("/home");
        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = jsonBuilder();
            //每一个filed里面存的就是列名和列值
            jsonBuild.startObject().field("g_col", gift.getG_col()).field("mod",gift.getMod())
                    .field("g_price",gift.getG_price()).field("g_size",gift.getG_size()).field("g_stock", gift.getG_stock())
                    .field("g_detaile", gift.getG_detaile()).field("g_sold", gift.getG_sold()).field("g_image", gift.getG_sold()).endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex("gift", "gift",gift.getG_id()).setSource(jsonBuild).get();
        client.close();
    }
    @Test
    public   void createUser1(){

        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = jsonBuilder();
            //每一个filed里面存的就是列名和列值
            jsonBuild.startObject().field("N", "asd").field("B", "asd").field("E","asda")
                    .field("BPH","asd").field("FC","asd").field("ADD", "asda")
                    .field("CM", "Asdasd").field("GM", "asdas").field("BC", "asdas")
                    .field("PWD", "asda").field("HD", "asasd").endObject();


        } catch (IOException e) {
            e.printStackTrace();
        }

            IndexResponse response = client.prepareIndex("user", "Userinfo","xx").setSource(jsonBuild).get();

        client.close();
    }
    //创建商品
    public  void createCart(Commodity commodity){
//        Commodity commodity = new Commodity();
//        commodity.setNum(12);
//        commodity.setPrice(2);
//        commodity.setC_brand("sa");
//        commodity.setC_stock("SAS");
//        commodity.setCol("ass");
//        commodity.setC_details("as");
//        commodity.setC_image(":asda");
//        commodity.setMod("asd");
//        commodity.setSid("1");
//        commodity.setSize("as");
//        commodity.setC_type("ASd");
        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = jsonBuilder();
            //每一个filed里面存的就是列名和列值
            jsonBuild.startObject().field("c_price", commodity.getPrice()).field("c_size",commodity.getSize())
                    .field("c_brand",commodity.getC_brand()).field("c_stock",commodity.getC_stock()).field("c_sold", commodity.getNum())
                    .field("c_col", commodity.getCol()).field("c_mod", commodity.getMod()).field("c_image", commodity.getC_image())
                    .field("c_details", commodity.getC_details()).field("c_type", commodity.getC_type()).endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex("commodity", "commodity",commodity.getSid()).setSource(jsonBuild).get();
        client.close();
    }
    //创建课程
    public  void createCart(Class course){
        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = jsonBuilder();
            //每一个filed里面存的就是列名和列值
            jsonBuild.startObject().field("cls_price", course.getCls_pt()).field("cls_json",course.getCls_json())
                    .endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex("class", "class",course.getCls_id()).setSource(jsonBuild).get();
        client.close();
    }

    //分页,排序
    @Test
    public void testShop()
    {
        String index="product";
        String type="product";
        String pt="20";
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(1).setSize(1)//分页
                .addSort("snum", SortOrder.DESC)//排序
                .get();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();
        System.out.println(total);
        SearchHit[] searchHits = hits.hits();
        for(SearchHit s : searchHits)
        {
            System.out.println(s.getSourceAsString());
            String []logindex=s.getSourceAsString().split(",");

        }
    }
    //商品价格降序查询
    @Test
    public void testjShop()
    {
        String index="product";
        String type="product";

        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
//                .setQuery(QueryBuilders.matchAllQuery()) //查询所有
                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                .setFrom(1).setG_size(1)//分页
                .addSort("pt", SortOrder.DESC)//排序
                .get();

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();
        System.out.println("查询总数据： "+total);
        SearchHit[] searchHits = hits.hits();
        for(SearchHit s : searchHits)
        {
            System.out.println(s.getSourceAsString());
        }
    }
    //删除数据
    @Test
    public void testDelete() {
        DeleteResponse response = client.prepareDelete("user", "Userinfo","AWcdTuLFchA3hQVg6V9F")
                .get();
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(index + " : " + type + ": " + id + ": " + version);
    }
    @Test
    public  void testupsert() throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest("commodity", "commodity", "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("c_sold", 34)
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest("commodity", "commodity", "1")
                .doc(jsonBuilder()
                        .startObject()
                        .field("c_sold", 38)
                        .endObject())
                .upsert(indexRequest); //如果不存在此文档 ，就增加 `indexRequest`
        client.update(updateRequest).get();
    }



    public static void main(String[] args) {

//        createUser();
//        Elasticsearch5 elasticsearch5 = new Elasticsearch5();

//        elasticsearch5.createCart(c);


    }

}
