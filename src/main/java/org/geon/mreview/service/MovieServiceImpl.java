package org.geon.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.geon.mreview.dto.MovieDTO;
import org.geon.mreview.dto.MovieImageDTO;
import org.geon.mreview.dto.PageRequestDTO;
import org.geon.mreview.dto.PageResultDTO;
import org.geon.mreview.entity.Movie;
import org.geon.mreview.entity.MovieImage;
import org.geon.mreview.repository.MovieImageRepository;
import org.geon.mreview.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final MovieImageRepository movieImageRepository;

    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");

        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {

            movieImageRepository.save(movieImage);

        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDTO> fn = (arr -> entityToDTO(
                (Movie) arr[0],
                (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3]
        ));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie)result.get(0)[0]; // Movie Entity는 가장 앞에 존재 - 모든 Row가 동일한 값이다.

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entityToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
