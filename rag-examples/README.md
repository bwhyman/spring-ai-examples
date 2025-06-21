# RAG Examples

### Introductions

RAG/embedding/vector/

### Tech Stacks
spring-boot: 3.5.0

spring-ai: 1.0.0
- spring-ai-starter-vector-store-qdrant
- spring-ai-advisors-vector-store

spring-ai-alibaba-starter-dashscope: 1.0.0.2

### Vector Database

向量数据库选用qdrant。支持web ui，支持meta数据过滤。  

### Embedding Model

选用阿里`text-embedding-v4`向量模型，1024维度。不显式声明默认使用`text-embedding-v1`，1536维度。

`如何添加用户？`，余弦相似度：  
- 管理员可以通过该模块添加新用户，score: 0.82；  
- 管理员功能模块，添加用户功能, score: 0.65；  
- 管理员功能模块，更改用户权限, score: 0.54；  
- 你好，世界, score: 0.29；

`苹果 手机`，余弦相似度：`手机`影响较高
- Apple手机, score: 0.81
- 苹果手机, score: 0.81
- 苹果正品手机, score: 0.80
- iPhone手机, score: 0.74
- 华为手机, score: 0.64
- OPPO手机, score: 0.63
- 小米手机, score: 0.61
- iPhone 16 Pro Max 旗舰新品, score: 0.47
- 你好，世界, score: 0.40

### spring-ai Advisors

spring-ai QuestionAnswerAdvisor类，封装了RAG流程。默认检索相似度>0.8的前6项，可以基于实际需求调整配置。

- 调用向量模型，获取问题向量数据
- 调用向量数据库，检索并召回数据
- 调用大模型，整合润色语言
- 响应结果

QuestionAnswerAdvisor包含默认Prompt模板，PromptTemplate类可以自定义模板。

### Testings

resources/handbook.txt，模拟一个简单的员工手册。  
InitService类，启动时读取员工手册，并插入向量数据库，基于meta判断避免重复提交。  
HandbookService类，接收提问，基于RAG响应结果。参数：0.4相似度，3项。     
HandbookServiceTest测试类。  

`我有张发票已经搁置40天了，还能报销么？`，响应结果：  
`根据报销流程中的规定，“超过一个月未报销的发票将不予受理。” 如果您的发票已经搁置了40天，即超过了1个月的时间，按照该规定，这张发票可能无法再进行报销。建议您尽快咨询财务部门以确认具体细节。`