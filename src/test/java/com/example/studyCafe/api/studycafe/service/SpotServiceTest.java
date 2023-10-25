package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.dto.request.SpotAddRequest;
import com.example.studyCafe.api.studycafe.dto.response.SpotResponse;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpotServiceTest {
    @Autowired
    private SpotService spotService;
    @Autowired
    private SpotRepository spotRepository;

    @BeforeEach
    void clean() {
        spotRepository.deleteAll();
    }

    @Test
    @DisplayName("어드민 - 지점 등록 성공")
    void spotAddSuccess() {
        // given
        SpotAddRequest request = SpotAddRequest.builder()
                .name("창원점")
                .phone("055-111-1111")
                .build();
        // when
        spotService.getAdminSpotAdd(request);

        // then
        Spot spot = spotRepository.findAll().get(0);
        assertEquals("창원점", spot.getName());
        assertEquals("055-111-1111", spot.getPhone());
    }

    @Test
    @DisplayName("어드민 - 지점 삭제 성공")
    void spotDelSuccess() {
        // given
        Spot spot = Spot.builder()
                .name("창원점")
                .phone("055-111-1111")
                .build();
        spotRepository.save(spot);

        // when
        spotService.getAdminSpotDel(1L);

        // then
        assertEquals(0, spotRepository.count());
    }

    @Test
    @DisplayName("지점 리스트 조회 성공")
    void spotListSearchSuccess() {
        // given
        List<Spot> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> Spot.builder()
                        .name(i + "번째 창원점")
                        .phone("055-111-" + i)
                        .build())
                .collect(Collectors.toList());
        spotRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        // when
        Page<SpotResponse> spotList = spotService.getSpotList(pageable);

        // then
        assertEquals(30, spotList.getTotalElements());
        assertEquals(5, spotList.getSize());

        SpotResponse firstSpot = spotList.getContent().get(0);
        assertEquals("1번째 창원점" , firstSpot.getSpotName());
        assertEquals("055-111-1" ,firstSpot.getSpotPhone());
    }

}