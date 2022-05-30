package org.geon.mreview.repository;

import org.geon.mreview.entity.Member;
import org.geon.mreview.entity.Movie;
import org.geon.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // @EntityGraph는 attributePaths 속성과 type속성을 가지고 있다.
    // attributePaths : 로딩 설정을 변경하고 싶은 속성의 이름을 배열로 명시한다.
    // FATCH 속성 값은 attributePaths에 명시한 속성을 EAGER로 처리하고 나머지는 LAZY로 처리한다.
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 3개의 데이터를 삭제한다고 하면 review 테이블에서 3번 반복된 후에 m_member 테이블을 삭제한다.
    // 이러한 비효율을 막기 위해서 @Query where절을 이용하는것이 더 나은방법이다.
    // update나 delete는 @Modifing이 반드시 필요하다
    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(@Param("member") Member member);
}
