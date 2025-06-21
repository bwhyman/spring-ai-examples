package org.example.ragexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class VectorTest {
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private VectorStore vectorStore;

    @Test
    void test() {
        var text = "你好，世界";
        EmbeddingResponse resp = embeddingModel.call(new EmbeddingRequest(List.of(text), null));
        String model = resp.getMetadata().getModel();
        log.info("model: {}", model);
        float[] output = resp.getResult().getOutput();
        log.debug("{}", output.length);
        String vectors = Arrays.toString(output);
        log.debug("{}", vectors.length());
        log.debug("{}", vectors);
    }

    //
    @Test
    void test2() {
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();
        store.add(List.of(new Document("你好，世界")));
        store.add(List.of(new Document("管理员可以通过该模块添加新用户")));
        store.add(List.of(new Document("管理员功能模块，添加用户功能")));
        store.add(List.of(new Document("管理员功能模块，更改用户权限")));

        SearchRequest req = SearchRequest.builder()
                .query("如何添加用户")
                .topK(5)                     // 最多返回 5 条最相关结果
                .similarityThreshold(0.1) // 只返回相似度 的结果
                .build();

        store.similaritySearch(req)
                .forEach(doc -> {
            log.debug("{}, score: {}", doc.getText(), doc.getScore());
        });
    }


    // inset data
    @Test
    void addDocument() {
        vectorStore.add(List.of(new Document("你好，世界")));
        vectorStore.add(List.of(new Document("管理员可以通过该模块添加新用户")));
        vectorStore.add(List.of(new Document("管理员功能模块，添加用户功能")));
        vectorStore.add(List.of(new Document("管理员功能模块，更改用户权限")));
    }
    @Test
    void test4() {
        SearchRequest req = SearchRequest.builder()
                .query("如何添加用户？")
                .topK(5)                     // 最多返回 5 条最相关结果
                .similarityThreshold(0.1) // 只返回相似度 的结果
                .build();

        vectorStore.similaritySearch(req)
                .forEach(doc -> {
                    log.debug("{}, score: {}", doc.getText(), doc.getScore());
                });
    }
    @Test
    void addDocument2() {
        vectorStore.add(List.of(new Document("苹果手机")));
        vectorStore.add(List.of(new Document("苹果正品手机")));
        vectorStore.add(List.of(new Document("iPhone手机")));
        vectorStore.add(List.of(new Document("Apple手机")));
        vectorStore.add(List.of(new Document("iPhone 16 Pro Max 旗舰新品")));
        vectorStore.add(List.of(new Document("小米手机")));
        vectorStore.add(List.of(new Document("OPPO手机")));
        vectorStore.add(List.of(new Document("华为手机")));
    }

    @Test
    void test5() {
        SearchRequest req = SearchRequest.builder()
                .query("苹果 手机")
                .topK(50)                     // 最多返回 5 条最相关结果
                .similarityThreshold(0.1) // 只返回相似度 的结果
                .build();

        vectorStore.similaritySearch(req)
                .forEach(doc -> {
                    log.debug("{}, score: {}", doc.getText(), doc.getScore());
                });
    }

    @Test
    void test6() {
        // var str = "公司的年假有几天？";
        // var str = "我离职要交接什么？";
        //var str = "可以请几天病假呢？";
        var str = "我有张发票已经搁置40天了，还能报销么？";
        SearchRequest req = SearchRequest.builder()
                .query(str)
                .topK(50)
                .similarityThreshold(0.1)
                .build();

        vectorStore.similaritySearch(req)
                .forEach(doc -> {
                    log.debug("{}, score: {}", doc.getText(), doc.getScore());
                });
    }
}
