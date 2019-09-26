package com.shang.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;


/**
 * <p>json 校验测试类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-08-29 10:21
 */

public class JsonValidateUtilTest {


    @Test
    public void validate() {
        //json数据文件
        String dataFilePath = "/Users/shangjiapeng/idea-projects/travelplan/src/main/resources/json/每日日程.json";
        //这个是设定的标准的json schema
        String filePath = "/Users/shangjiapeng/idea-projects/travelplan/src/main/resources/json/RouteBookDaySchema.json";
        try {
//            //读取文件
//            String encoding = "UTF-8";
//            File file = new File(dataFilePath);
//            Long filelength = file.length();
//            byte[] filecontent = new byte[filelength.intValue()];
//            FileInputStream inputStream = new FileInputStream(file);
//            int read = inputStream.read(filecontent);
//            String dataString = new String(filecontent, encoding);
//            inputStream.close();
//            //处理
//            JsonNode dataNode = JsonLoader.fromString(dataString);

            //读取data
            JsonNode dataNode = new JsonNodeReader().fromReader(new FileReader(dataFilePath));
            //读取schema
            JsonNode schemaNode = new JsonNodeReader().fromReader(new FileReader(filePath));
            JsonSchema jsonSchema = JsonSchemaFactory.byDefault().getJsonSchema(schemaNode);
            ProcessingReport report = jsonSchema.validate(dataNode);
            System.out.println("校验的结果为:" + report.isSuccess());
            System.out.println("校验的结果为:" + report);
        } catch (ProcessingException | IOException e) {
            e.printStackTrace();
        }

    }
}