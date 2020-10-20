package com.board_of_ads.repository;

import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    Posting findPostingByTitle(String title);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.meetingAddress) from Posting p where p.city = :city")
    List<PostingDto> findPostingByCity(@Param("city") City city);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.meetingAddress) from Posting p ")
    List<PostingDto> findAllPostings();
}
