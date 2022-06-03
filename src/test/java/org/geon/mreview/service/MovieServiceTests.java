package org.geon.mreview.service;

import lombok.extern.log4j.Log4j2;
import org.geon.mreview.dto.PageRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MovieServiceTests {

    @Autowired
    MovieService movieService;

    @Test
    public void testGetList() {
        log.info(movieService.getList(new PageRequestDTO()));
    }
}
