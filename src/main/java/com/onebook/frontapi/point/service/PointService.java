package com.onebook.frontapi.point.service;

import com.onebook.frontapi.point.adapter.PointAdapter;
import com.onebook.frontapi.point.dto.CommonResponse;
import com.onebook.frontapi.point.dto.MemberPointResponse;
import com.onebook.frontapi.point.exception.LoginRequiredException;
import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {

    private final PointAdapter pointAdapter;

    public List<MemberPointResponse> getMemberPointHistories() {
        CommonResponse<List<MemberPointResponse>> userPointHistories;
        try {
            userPointHistories = pointAdapter.getMemberPointHistories();
        } catch (FeignException.Unauthorized | FeignException.BadRequest ex) {
            throw new LoginRequiredException("나의 포인트 내역 조회에 접근하나 로그인 되어있지 않음");
        } catch (FeignException.NotFound ex) {
            throw new LoginRequiredException("로그인 한 아이디로 포인트를 조회할 수 없음");
        }

        return userPointHistories.getResult();
    }
}
