package com.example.mediacommunity.config;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] newData;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        try {
            Safelist safelist = Safelist.basic();
            safelist.addAttributes(":all", "class");

            InputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            String cleanBody = Jsoup.clean(messageBody, "", safelist, new Document.OutputSettings().prettyPrint(false));
            String quotReplacedBody = cleanBody.replaceAll("\"\\\\&quot;", "\'");
            quotReplacedBody = quotReplacedBody.replaceAll("\\\\&quot;\"", "\'");

            newData = quotReplacedBody.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw e;
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
