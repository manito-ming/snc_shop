package snc.boot.util.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import snc.server.ide.pojo.Commodity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Test {
    @org.junit.Test
    public void stest() {
        try {
            ElasticsearchService service = new ElasticsearchService("elasticsearch", "127.0.0.1", 9300);
            ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
            constructor.mutilsearch(new ESQueryBuilders().term("c_type","电脑"));
            constructor.setSize(15);  //查询返回条数，最大 10000
            constructor.setAsc("c_price");
            List<Map<String, Object>> list = service.search("commodity", "commodity", constructor);
            System.out.println(list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
