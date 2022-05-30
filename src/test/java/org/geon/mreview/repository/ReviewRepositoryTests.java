package org.geon.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.geon.mreview.entity.Member;
import org.geon.mreview.entity.Movie;
import org.geon.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertReviews() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Long mno = (long)(Math.random() * 200) + 1;

            Long mid = (long)(Math.random() * 200) + 1;

            Member member = Member.builder().mid(mid).build();
            Movie movie = Movie.builder().mno(mno).build();

            Review movieReview =  Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌은..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }

    @Test
    public void testGetMovieReviews() {
        Long mno = 199L;

        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            log.info(movieReview.getReviewnum());
            log.info(movieReview.getGrade());
            log.info(movieReview.getText());
            log.info(movieReview.getMember().getEmail());
            log.info("-------------------------------------");
        });
    }


}
