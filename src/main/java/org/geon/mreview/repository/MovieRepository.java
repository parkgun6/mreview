package org.geon.mreview.repository;

import org.geon.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left join MovieImage mi on mi.movie = m " +
            // 영화 이미지 중 가장 나중에 추가된 이미지를 가져오는 쿼리
            "and mi.inum = (select max(mi2.inum) from MovieImage mi2 where mi2.movie = m) " +
            "left join Review r on r.movie = m " +
            "group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left join MovieImage mi on mi.movie = m " +
            "left join Review r on r.movie = m " +
            "where m.mno = :mno " +
            "group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}
