package org.geon.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.geon.mreview.entity.Movie;
import org.geon.mreview.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
