package com.en.abbrevio.service.parser;

import com.en.abbrevio.service.parser.impl.XMLParserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class XMLParserServiceTest {
    @Autowired
    private XMLParserService parserService;

    @Test
    @SneakyThrows
    void testXmlParserService() {
        String content = FileCopyUtils.copyToString(new InputStreamReader
                (new ClassPathResource("request_body_example.xml").getInputStream()));
        List<String> expectedList = Arrays.asList("DIPEA");
        List<String> parseResult = parserService.parse(content);

        long mismatchQuantity = expectedList.stream()
                .filter(el -> !(parseResult.contains(el)))
                .count();
        Assertions.assertIterableEquals(expectedList,parseResult);
        Assertions.assertEquals(mismatchQuantity, 0l);
        Assertions.assertEquals(expectedList.size(), parseResult.size());
    }
}
