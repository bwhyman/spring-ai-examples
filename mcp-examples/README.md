# MCP-Examples

### Introductions

LLM/MCP/spring-ai/mcp-server/mcp-client/spring-ai-alibaba

MCP使用http+sse通信，sse的长连接是个缺陷，spring-ai与阿里提出Stramable HTTP实现方案，但当前没有其他厂商跟进。

### Tech Stacks
spring-boot: 3.5.0  
- spring-boot-starter-webflux

spring-ai: 1.0.0  
- spring-ai-starter-mcp-server-webflux
- spring-ai-starter-mcp-client-webflux  

spring-ai-alibaba-starter-dashscope: 1.0.0.2

### spring-ai-mcp

官网以1.0M6版本展示的示例代码，`spring-ai-mcp-server-webflux-spring-boot-starter`已在1.0正式版改为`spring-ai-starter-mcp-server-webflux`，致使项目错误。

### MCP

了解MCP后，参照网上的实现示例，发现与理解的MCP架构不符，跑通后证明自己的理解是对。

**mcp-server**

引入的`spring-ai-starter-mcp-server-webflux`可直接按web启动mcp-sever服务。  
通过自然语言(提示词)描述功能函数，自动注册并暴露一个统一接口供外部mcp-client使用。  
网上的例子在mcp-server也引入大模型依赖是无用的，其本身并不依赖大模型，解析由mcp-client完成。

**mcp-client**

引入的`spring-ai-starter-mcp-client-webflux`用于连接mcp-server，本身并不包含web服务，mcp-client欲作为web服务供外部访问， 需单独引入web依赖。  
引入实现了mcp协议的大模型库，最好支持spring-ai。  
示例使用`spring-ai-alibaba-starter-dashscope`阿里大模型。  
配置需要访问的mcp-server地址，自动连接mcp-server获取服务列表；通过大模型解析自然语言信息，例如`今天北京天气怎么样？`，
结合mcp-server提供的服务，决定调用的函数及参数；获取mcp-server返回结果，再次生成自然语言，返回请求者。   
必须声明注入mcp-server的回调，否则依赖的模型直接解析，不会实际调用mcp-server。

**vs microservice**

微服务。通过暴露`明确的的接口地址`，对外提供服务。请求者必须严格按照接口规范，获取服务。  

mcp服务，包含mcp-server/mcp-client。mcp-server通过暴露`提示词描述的功能函数`，对mcp-client提供服务。
请求者无需了解服务的接口规范，通过mcp-client集成的大模型，解析自然语言确定应访问的mcp-server功能，获取服务。与微服务相似，mcp服务也可以彼此互交。

### Testings

启动mcp-server。  
启动mcp-client，/api/weathermessage接口接收数据；/test/http/包含http请求测试脚本。

### Update

#### 2025.06.20

整理父/子模块依赖管理。

#### 2025.06.18

模型参数改为配置声明，移除代码层面的耦合，更利于底层实现模型的插拔。