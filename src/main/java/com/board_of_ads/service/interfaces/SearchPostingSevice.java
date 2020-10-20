package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.dto.PostingDto;

import java.util.List;

public interface SearchPostingSevice {
    List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption);
}
