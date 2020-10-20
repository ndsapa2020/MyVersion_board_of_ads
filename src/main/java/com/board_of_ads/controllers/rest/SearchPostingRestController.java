package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.service.interfaces.SearchPostingSevice;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
@Slf4j
public class SearchPostingRestController {

    private final SearchPostingSevice searchPostingSevice;

    @GetMapping
    public Response<List<PostingDto>> findAllPostings(@RequestParam(name="catSel") String categorySelect,
                                                      @RequestParam(name="citSel",required = false)String citySelect,
                                                      @RequestParam(name="searchT",required = false) String searchText,
                                                      @RequestParam(name="phOpt",required = false) String photoOption) {
        log.info("Use this default logger");
        var postings = searchPostingSevice
                .searchPostings(categorySelect, citySelect, searchText, photoOption);
        return (postings != null)
                ? Response.ok(postings)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }
}
