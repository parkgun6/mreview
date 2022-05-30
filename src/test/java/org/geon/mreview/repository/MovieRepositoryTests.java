package org.geon.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.geon.mreview.entity.Movie;
import org.geon.mreview.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieImageRepository movieImageRepository;

    @Test
    public void insertMovies() {
        IntStream.rangeClosed(1,100).forEach(i -> {

            Movie movie = Movie.builder().title("Movie...." + i).build();

            log.info("-----------------------------------------------");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1; // 1,2,3,4

            for(int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .build();
                movieImageRepository.save(movieImage);
            }
            log.info("-----------------------------------------------");
        });
    }

    @Test
    public void testListPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            log.info(Arrays.toString(objects));
        }
    }

    @Test
    public void testGetMovieWithAll() {
        Long mno = 199L;

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

//        log.info(result);

        for (Object[] arr : result) {
            log.info(Arrays.toString(arr));
        }
    }
}
