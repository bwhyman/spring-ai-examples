package org.example.ragexamples.service;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitService {
    private final VectorStore vectorStore;
    @Value("classpath:handbook.txt")
    private Resource handbook;

    // 启动时，将手册插入qdrant数据库
    @EventListener(ApplicationReadyEvent.class)
    public void initHandbook() throws Exception {
        String script = handbook.getContentAsString(Charset.defaultCharset());
        String firstLine = script.split("\\R", 2)[0];
        // 取第一个模块名称，判断数据库是否已经存在
        SearchRequest request = SearchRequest.builder()
                .filterExpression(new FilterExpressionBuilder()
                        .eq("title", firstLine)
                        .build())
                .query("")
                .build();
        List<Document> documents = Optional.ofNullable(vectorStore.similaritySearch(request))
                .orElse(List.of());
        if (!documents.isEmpty()) {
            return;
        }
        Optional<QdrantClient> nativeClient = vectorStore.getNativeClient();
        if (nativeClient.isEmpty()) {
            return;
        }
        // 为`title`字段添加索引
        nativeClient.get()
                .createPayloadIndexAsync(
                "my-vectors",
                "title",
                Collections.PayloadSchemaType.Keyword,
                null,
                true,
                null,
                null)
                .get();
        //
        String[] split = script.split("\\r?\\n\\s*\\r?\\n");
        List<Document> docs = new ArrayList<>();
        for (String info : split) {
            var title = info.split("\\R", 2)[0];
            var doc = new Document(info, Map.of("title", title));
            docs.add(doc);
        }
        vectorStore.add(docs);
    }
}

