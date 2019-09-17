package com.shang.demo.elasticsearch;

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
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-01 17:06
 * @Version 1.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
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
        //设置source源
        searchRequest.source(searchSourceBuilder);
        //执行查询操作
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

    /**
     * 分页查询
     * from:表示起始文档的下标，从0开始。 size:查询的文档数量。
     * 发送:post http://localhost:9200/xc_course/doc/_search
     *  {
     *  "from" : 0, "size" : 1, "query": {
     *  "match_all": {}
     *  },
     *  "_source" : ["name","studymodel"]
     *  }
     */
    @Test
    public void testSelectPage() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //分页查询,设置起始下标,从0开始
        searchSourceBuilder.from(0);
        //每页显示的个数
        searchSourceBuilder.size(10);
        //Source源字段过滤
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        //设置source源
        searchRequest.source(searchSourceBuilder);
        //执行查询操作
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }

    }

    /**
     * 精确查询,TermQuery 在搜索时会整体匹配关键字,不再将关键字分词
     * post http://localhost:9200/xc_course/doc/_search
     * {
     *      "query": {
     *         "term" : {
     *             "name": "spring"
     *         }
     *           },
     *           "_source" : ["name","studymodel"]
     * }
     */
    @Test
    public void testTermQuery() throws IOException {
        //查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //查询文档的类型
        searchRequest.types("doc");
        //查询调价构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置精确查询的关键字
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "文档");
        searchSourceBuilder.query(termQueryBuilder);
        //设置source源字段过滤条件
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        //把构造器对象放置进,查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        //使用内置的客户端对象,执行查询操作
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }
    /**
     * 根据id精确匹配
     * post: http://127.0.0.1:9200/xc_course/doc/_search
     * {
     *       "query": {
     *           "ids" : {
     *               "type" : "doc",
     *               "values" : ["3", "4", "100"]
     *           }
     * } }
     */
    @Test
    public void testQueryById() throws IOException {
        //查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //查询文档的类型
        searchRequest.types("doc");
        //查询调价构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"10000", "1314"};
        List<String> idList = Arrays.asList(ids);
        //设置精确查询的关键字
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("_id", idList);
        searchSourceBuilder.query(termsQueryBuilder);
        //设置source源字段过滤条件
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        //把构造器对象放置进,查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        //使用内置的客户端对象,执行查询操作
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }

    /**
     * 全文检索 matchQuery ,原理是:先将搜索的字符串分词,在使用各个分词,从索引库中搜索
     * post http://localhost:9200/xc_course/doc/_search
     * {
     * "query": {
     *        "match" : {
     *            "description" : {
     *             "query" : "keyWords",
     *             "operator" : "or"
     *            }
     * } }
     * }
     * query:搜索的关键字，对于英文关键字如果有多个单词则中间要用半角逗号分隔，而对于中文关键字中间可以用 逗号分隔也可以不用。
     * operator:or 表示 只要有一个词在文档中出现则就符合条件，and表示每个词都在文档中出现则才符合条件。 上边的搜索的执行过程是:
     * 上边使用的operator = or表示只要有一个词匹配上就得分，如果实现三个词至少有两个词匹配如何实现? 使用minimum_should_match可以指定文档匹配词的占比:
     */
    @Test
    public void testMatchQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});
        //匹配关键字
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "使用es添加文档").operator(Operator.OR);
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }
    @Test
    public void testMatchQuery2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});
        //匹配关键字,设置匹配占比80%
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "使用es添加文档").minimumShouldMatch("80%");
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }

    /**
     * 多字段匹配查询
     * termQuery和matchQuery一次只能匹配一个Field，而multiQuery，一次可以匹配多个字段
     * post http://localhost:9200/xc_course/doc/_search
     *  {
     * "query": {
     *    "multi_match" : {
     *         "query" : "keyWords",
     *         "minimum_should_match": "50%",
     *         "fields": [ "name", "description" ]
     *    } }
     * }
     * 匹配多个字段时可以提升字段的boost(权重)来提高得分
     * “name^10” 表示权重提升10倍，执行上边的查询，发现name中包括spring关键字的文档排在前边
     */
    @Test
    public void testMultiMatchQuery() throws IOException {
        //资源查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //设置要查询的资源的类型
        searchRequest.types("doc");
        //资源查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置需要过滤的字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description","timestamp"},new String[]{});
        //匹配条件构造器:匹配关键字,设置匹配占比50%
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("添加文档", "name", "description").minimumShouldMatch("50%");
        //把name字段的权重提升10倍
        multiMatchQueryBuilder.field("name",10);
        //把匹配条件构造器,放进资源查询构造器中
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //再把资源查询构造器,放进资源查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        //使用restHighLevelClient 客户端对象,执行search()操作,得到response对象
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //链式编程,拿到response对象中的结果结果集
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        //编辑结果集,展示结果
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }

    /**
     * 布尔查询 对应于Lucene的BooleanQuery查询，实现将多个查询组合起来
     * BoolQuery，将搜索关键字分词，拿分词去索引库搜索
     * 三个参数:
     * must:文档必须匹配must所包括的查询条件，相当于 “AND”
     * should:文档应该匹配should所包括的查询条件其 中的一个或多个，相当于 "OR"
     * must_not:文档不能匹配must_not所包括的该查询条件，相当于“NOT”
     * POST http://localhost:9200/xc_course/doc/_search
     */
    @Test
    public void testBooleanQuery() throws IOException{
        //资源查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //设置要查询的资源的类型
        searchRequest.types("doc");
        //资源查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置需要过滤的字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description","timestamp"},new String[]{});
        //multiQuery
        String keyWords="添加文档";
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "name", "description").minimumShouldMatch("50%");
        //把name字段的权重提升10倍
        multiMatchQueryBuilder.field("name",10);
        //termQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "20190725");
        //booleanQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        //设置搜索条件构造器
        searchSourceBuilder.query(boolQueryBuilder);
        //再把资源查询构造器,放进资源查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    /**
     * 过滤器:filter
     * 过虑是针对搜索的结果进行过虑，过虑器主要判断的是文档是否匹配，不去计算和判断文档的匹配度得分，所以过
     * 虑器性能比查询要高，且方便缓存，推荐尽量使用过虑器去实现查询或者过虑器和查询共同使用。
     * 过虑器在布尔查询中使用，下边是在搜索结果的基础上进行过虑
     * range:范围过虑
     * term:字段匹配过滤
     */
    @Test
    public void testFilter() throws IOException {
        //资源查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //设置要查询的资源的类型
        searchRequest.types("doc");
        //资源查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置需要过滤的字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description","timestamp","price"},new String[]{});
        //multiQuery
        String keyWords="添加文档";
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "name", "description").minimumShouldMatch("50%");
        //把name字段的权重提升10倍
        multiMatchQueryBuilder.field("name",10);
        //termQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "20190725");
        //booleanQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        //过滤
        boolQueryBuilder.filter(termQueryBuilder);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(3.0).lte(10.0);
        boolQueryBuilder.filter(rangeQueryBuilder);
        //设置搜索条件构造器
        searchSourceBuilder.query(boolQueryBuilder);
        //再把资源查询构造器,放进资源查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        System.err.println(searchRequest);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    /**
     * 排序 查询
     * 可以在字段上添加一个或多个排序，支持在keyword、date、float等类型上添加，text类型的字段上不允许添加排序。
     * 发送 POST http://localhost:9200/xc_course/doc/_search
     */
    @Test
    public void testSortQuery() throws IOException {
        //资源查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //设置要查询的资源的类型
        searchRequest.types("doc");
        //资源查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置需要过滤的字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description","timestamp","price"},new String[]{});
        //multiQuery
        String keyWords="添加文档";
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "name", "description").minimumShouldMatch("50%");
        //把name字段的权重提升10倍
        multiMatchQueryBuilder.field("name",10);
        //termQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "20190725");
        //booleanQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        //过滤
        boolQueryBuilder.filter(termQueryBuilder);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(3.0).lte(10.0);
        boolQueryBuilder.filter(rangeQueryBuilder);
        //设置搜索条件构造器
        searchSourceBuilder.query(boolQueryBuilder);
        //排序
        FieldSortBuilder fieldSortBuilder = new FieldSortBuilder("price").order(SortOrder.DESC);
        searchSourceBuilder.sort(fieldSortBuilder);
        //再把资源查询构造器,放进资源查询请求对象中去
        searchRequest.source(searchSourceBuilder);
        System.err.println("String:"+searchRequest.toString());
        System.err.println("source:"+searchRequest.source());

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("searchHit:"+searchHit);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println("sourceAsMap:"+sourceAsMap);
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println("sourceAsString:"+sourceAsString);
        }
    }

    /**
     * 高亮显示
     * 高亮显示可以将搜索结果一个或多个字突出显示，以便向用户展示匹配关键字的位置。
     * Post: http://127.0.0.1:9200/xc_course/doc/_search
     */
     @Test
     public void testHighLight() throws IOException{
         //资源查询请求对象
         SearchRequest searchRequest = new SearchRequest("xc_course");
         //设置要查询的资源的类型
         searchRequest.types("doc");
         //资源查询构造器
         SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
         //设置需要过滤的字段
         searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description","timestamp","price"},new String[]{});
         //multiQuery
         String keyWords="添加文档";
         MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyWords, "name", "description").minimumShouldMatch("50%");
         //把name字段的权重提升10倍
         multiMatchQueryBuilder.field("name",10);
         //termQuery
         TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "20190725");
         //booleanQuery
         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
         boolQueryBuilder.must(multiMatchQueryBuilder);
         //过滤
         boolQueryBuilder.filter(termQueryBuilder);
         RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(3.0).lte(10.0);
         boolQueryBuilder.filter(rangeQueryBuilder);
         //设置搜索条件构造器
         searchSourceBuilder.query(boolQueryBuilder);
         //排序
         FieldSortBuilder fieldSortBuilder = new FieldSortBuilder("price").order(SortOrder.DESC);
         searchSourceBuilder.sort(fieldSortBuilder);
         //高亮显示
         HighlightBuilder highlightBuilder = new HighlightBuilder();
         highlightBuilder.preTags("<tag>");//设置前缀
         highlightBuilder.postTags("</tag>");//设置后缀
         //设置高亮字段
         highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
         highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
         searchSourceBuilder.highlighter(highlightBuilder);

         //再把资源查询构造器,放进资源查询请求对象中去
         searchRequest.source(searchSourceBuilder);

         SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
         SearchHit[] searchHits = searchResponse.getHits().getHits();
         for (SearchHit searchHit : searchHits) {
             System.out.println(searchHit);
             Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
             //名称
             String name = (String) sourceAsMap.get("name");
             //去除高亮的字段
             Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
             if (highlightFields!=null){
                 HighlightField nameField = highlightFields.get("name");
                 if (nameField!=null){
                     Text[] fragments = nameField.getFragments();
                     StringBuffer stringBuffer = new StringBuffer();
                     for (Text fragment : fragments) {
                         stringBuffer.append(fragment.string());
                     }
                     name=stringBuffer.toString();
                     System.out.println(name);
                 }
             }

             System.out.println(sourceAsMap);
         }
     }



}





