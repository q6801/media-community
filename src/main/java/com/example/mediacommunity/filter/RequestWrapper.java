package com.example.mediacommunity.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] newData;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            Map<String, String> requestMessageMap = changeMessageBodyToMap(messageBody, objectMapper);
            xssDefence(requestMessageMap);
            newData = objectMapper.writeValueAsBytes(requestMessageMap);
        } catch (IOException e) {
            throw e;
        }
    }

    private Map<String, String> changeMessageBodyToMap(String messageBody, ObjectMapper objectMapper) throws JsonProcessingException {
        Map<String, String> map;
        if (messageBody.isBlank()) {
            map = new HashMap<>();
        } else {
            map = objectMapper.readValue(messageBody, Map.class);
        }
        return map;
    }

    private void xssDefence(Map<String, String> map) {
        Safelist safelist = Safelist.relaxed();
        safelist.addAttributes(":all", "class");

        for(String key : map.keySet()) {
            if (map.get(key) instanceof String) {
                String value = map.get(key);
                if (key.equals("content")) {
                    String cleanBody = Jsoup.clean(value, "", safelist, new Document.OutputSettings().prettyPrint(false));
                    String quotReplacedBody = cleanBody.replaceAll("\"\\\\&quot;", "\'");
                    quotReplacedBody = quotReplacedBody.replaceAll("\\\\&quot;\"", "\'");
                    map.put(key, quotReplacedBody);
                } else {
                    String htmlEscapedValue = HtmlUtils.htmlEscape(value);
                    map.put(key, htmlEscapedValue);
                }
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(newData);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bis.available() == 0;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
