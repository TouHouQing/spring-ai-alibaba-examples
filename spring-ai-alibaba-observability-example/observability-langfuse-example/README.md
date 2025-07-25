# Spring AI Alibaba Observability with Langfuse Example

This example demonstrates how to integrate Spring AI Alibaba with Langfuse for comprehensive observability and monitoring of AI applications.

## Overview

This project showcases the integration of:
- **Spring AI Alibaba**: For AI model interactions (Chat, Embedding, Image generation, Tool calling)
- **Langfuse**: For AI observability, tracing, and analytics
- **OpenTelemetry**: For telemetry data collection and export

## Features

- Chat model interactions with DashScope (Qwen)
- Embedding model for text embeddings
- Image generation capabilities
- Tool calling with weather services
- Complete observability with Langfuse integration
- OpenTelemetry tracing and metrics

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Langfuse account (cloud or self-hosted)
- DashScope API key
- Weather API key (optional, for tool calling)

## Setup

### 1. Langfuse Configuration

#### Option A: Using Langfuse Cloud
1. Sign up at [https://cloud.langfuse.com](https://cloud.langfuse.com)
2. Create a new project
3. Navigate to **Settings** → **API Keys**
4. Generate a new API key pair (Public Key and Secret Key)
5. Encode your credentials in Base64:
   ```bash
   echo -n "public_key:secret_key" | base64
   ``` 
   ```Windows PowerShell
   [System.Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("public_key:secret_key"))
   ```
   
#### Option B: Using Self-hosted Langfuse
1. Deploy Langfuse using Docker:
   ```bash
   docker compose up -d
   ```
2. Access Langfuse at `http://localhost:3000`
3. Create your project and generate API keys
4. Update the endpoint in `application.yml`

### 2. Environment Variables

Set the following environment variables:

```bash
# Required: DashScope API Key
export AI_DASHSCOPE_API_KEY=your_dashscope_api_key

# Optional: Weather API Key (for tool calling)
export WEATHER_API_KEY=your_weather_api_key
```

### 3. Application Configuration

Update `src/main/resources/application.yml`:

```yaml
otel:
  exporter:
    otlp:
      endpoint: "https://cloud.langfuse.com/api/public/otel"
      headers:
        Authorization: "Basic YOUR_BASE64_ENCODED_CREDENTIALS"
```

Replace `YOUR_BASE64_ENCODED_CREDENTIALS` with your Base64 encoded `public_key:secret_key`.

## Running the Application

### 1. Compile and Run
```bash
mvn clean compile
mvn spring-boot:run
```

### 2. Test the Endpoints

#### Chat Model
```bash
curl "http://localhost:8080/observability/chat?prompt=Hello, how are you?"
```

#### Embedding Model
```bash
curl "http://localhost:8080/observability/embedding"
```

#### Image Generation
```bash
curl "http://localhost:8080/observability/image/generate" --output generated_image.png
```

#### Tool Calling (Weather)
```bash
curl "http://localhost:8080/observability/tools?prompt=What's the weather like in Beijing?"
```

## Troubleshooting

### Common Issues

1. **"Name for argument not specified" Error**
   - Ensure Maven compiler plugin includes `-parameters` flag
   - Check that the compiler plugin is properly inherited

2. **Authentication Failed**
   - Verify your Langfuse API keys are correct
   - Ensure Base64 encoding is done correctly
   - Check if the endpoint URL is correct

3. **No Traces in Langfuse**
   - Verify OpenTelemetry configuration
   - Check application logs for export errors
   - Ensure sampling is set to 1.0 for testing

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    io.opentelemetry: DEBUG
    org.springframework.ai: DEBUG
```

## Best Practices

1. **Production Configuration**
   - Use environment variables for sensitive data
   - Implement proper sampling strategies
   - Monitor resource usage

2. **Security**
   - Never commit API keys to version control
   - Use secure secret management
   - Implement proper access controls

3. **Performance**
   - Adjust sampling rates based on traffic
   - Monitor export performance
   - Use async exporters for high-throughput applications

## References

- [Spring AI Alibaba Documentation](https://github.com/alibaba/spring-ai-alibaba)
- [Langfuse Documentation](https://langfuse.com/docs)
- [OpenTelemetry Java Documentation](https://opentelemetry.io/docs/instrumentation/java/)
- [DashScope API Documentation](https://help.aliyun.com/zh/dashscope/)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](../../LICENSE) file for details.