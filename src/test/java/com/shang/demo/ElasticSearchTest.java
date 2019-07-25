package com.shang.demo;

import com.shang.demo.domain.User;
import com.shang.demo.mapper.UserMapper;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 17:06
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void TestSelect() {
        System.out.println(("-------selectAll method test-------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    /**
     * 创建es索引库
     * 发送:put http://localhost:9200/索引库名称/类型名称/_mapping
     */
    @Test
    public void testCreateESIndex() throws IOException {
        //创建索引请求对象,并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
        //设置映射
        createIndexRequest.mapping("doc", "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"description\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"pic\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"index\": false\n" +
                "\t\t},\n" +
                "\t\t\"studymodel\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = restHighLevelClient.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest,RequestOptions.DEFAULT);
        //得到响应结果
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    //删除索引库
    @Test
    public void testDeleteESIndex() throws IOException {
        //删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("xc_course");
        //删除索引
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        //得到删除索引的响应结果
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        System.out.println(acknowledged);

    }


    /**
     * 测试向索引中添加文档
     * 格式如下: PUT /{index}/{type}/{id} { "field": "value", ... } 如果不指定id，ES会自动生成。
     */
    @Test
    public void testAddDoc() throws IOException {
        //准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "测试添加文档");
        jsonMap.put("description", "使用Java-client实现向es指定的索引库中添加文档");
        jsonMap.put("studymodel", "20190725");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonMap.put("timestamp", dateFormat.format(new Date()));
        jsonMap.put("price", 5.6f);
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest("xc_course", "doc", "10000");
        //指定索引内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
        //获取响应
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
    }

    /**
     * 查询文档
     * 格式如下: GET /{index}/{type}/{id}
     */
    @Test
    public void testSearchDoc() throws IOException {
        //get请求对象
        GetRequest getRequest = new GetRequest("xc_course", "doc", "10000");
        GetResponse getResponse = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
        boolean exists = getResponse.isExists();
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
    }

    /**
     * 更新文档
     * ES更新文档的顺序是:先检索到文档、将原来的文档标记为删除、创建新文档、删除旧文档，创建新文档就会重建 索引。
     * 1 完全替换
     * Post:http://localhost:9200/xc_test/doc/3
     * 2 局部更新
     * post: http://localhost:9200/xc_test/doc/3/_update
     */
    @Test
    public void testUpdateDoc() throws IOException {
        //更新索引请求对象
        UpdateRequest updateRequest = new UpdateRequest("xc_course", "doc", "10000");
        //条件map
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "修改添加的测试文档");
        updateRequest.doc(jsonMap);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        RestStatus status = updateResponse.status();
        DocWriteResponse.Result result = updateResponse.getResult();
        System.out.println(status);
        System.out.println(result);
    }

    /**
     * 删除文档
     * 根据id删除，格式如下:
     * DELETE /{index}/{type}/{id}
     * 搜索匹配删除，将搜索出来的记录删除，格式如下:
     * POST /{index}/{type}/_delete_by_query
     * { "query":{
     * "term":{
     * "studymodel":"201001"
     * }
     * }
     * }
     */
    @Test
    public void testDeleteDoc() throws IOException {
        //删除文档id
        String id = "10000";
        //删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest("xc_course", "doc", id);
        //响应对象
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        //获取响应结果
        DocWriteResponse.Result result = deleteResponse.getResult();
        System.out.println(result);
    }

    /**
     * 简单搜索
     * 简单搜索就是通过URL进行查询,以get请求的方式 ../_search?q=name:"名字"
     * DSL搜索(Domain Specific Language)基于json的搜索方式,在搜索的时候传入特定的json格式的数据,来完成不同的搜索需求(建议使用)
     * 查询所有的文档  http://localhost:9200/_search
     * 查询指定索引库指定类型下的文档 (通常使用的是这种方式)   http://localhost:9200/xc_course/doc/_search
     * {
     *    "query": {
     *      "match_all": {}
     *    },
     *    "_source" : ["name","studymodel"]
     * }
     * _source:source源过滤设置,指定结果中所包含的字段有哪些
     */

    //搜索type下的所有的记录
    @Test
    public void testSearchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //设置查询内容的type
        searchRequest.types("doc");
        //资源搜索构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //匹配所有
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置source源字段过滤条件
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            String timestamp = (String) sourceAsMap.get("timestamp");
            System.err.println("index:"+index+"\ntype:"+type+"\nid:"+id+"\nscore:"+score+"\nsourceAsString:"+sourceAsString+"\nname:"+name+"\nstudymodel:"+studymodel+"\ndescription:"+description+"\ntimestamp:"+timestamp);
        }




    }


}





