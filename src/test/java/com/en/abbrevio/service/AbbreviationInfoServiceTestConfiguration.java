package com.en.abbrevio.service;

import com.en.abbrevio.model.AbbreviationInfo;
import com.en.abbrevio.repository.AbbreviationInfoRepository;
import com.en.abbrevio.service.parser.ParserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestConfiguration
public class AbbreviationInfoServiceTestConfiguration {
    @Autowired
    ParserService<String, String> parserService;
    @MockBean
    AbbreviationInfoRepository abbreviationInfoRepository;

    @Bean
    AbbreviationInfoService moleculeService() {
        when(abbreviationInfoRepository.getByName("AA")).thenReturn(Optional.of(AbbreviationInfo.builder()
                .id(2L)
                .name("AA")
                .em(60.05)
                .mw(60.05)
                .inchi("1S/C2H4O2/c1-2(3)4/h1H3,(H,3,4)\n")
                .smiles("CC(O)=O")
                .formula("C<sub>2</sub>H<sub>4</sub>O<sub>2</sub>")
                .build()));
        when(abbreviationInfoRepository.getByName("DIPEA")).thenReturn(Optional.of(AbbreviationInfo.builder()
                .id(1L)
                .name("DIPEA")
                .em(129.1517496151)
                .mw(129.2470000014)
                .inchi("1S/C8H19N/c1-6-9(7(2)3)8(4)5/h7-8H,6H2,1-5H3")
                .smiles("CCN(C(C)C)C(C)C")
                .formula("C<sub>8</sub>H<sub>19</sub>N")
                .build()));
        when(abbreviationInfoRepository.getByName("FGR")).thenReturn(Optional.of(AbbreviationInfo.builder()
                .id(3L)
                .name("FGR")
                .em(129.1517496151)
                .mw(129.2470000014)
                .inchi("\"1S/C8H19N/c1-6-9(7(2)3)8(4)5/h7-8H,6H2,1-5H3\"")
                .smiles("\"CCN(C(C)C)C(C)C\"")
                .formula("\"C<sub>8</sub>H<sub>19</sub>N\"")
                .build()));
        return new AbbreviationInfoService(parserService, abbreviationInfoRepository);
    }
}
