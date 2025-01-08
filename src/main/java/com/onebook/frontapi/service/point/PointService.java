package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.common.CommonResponse;
import com.onebook.frontapi.feign.point.PointFeignClient;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PointService {

    private final PointFeignClient pointFeign;

    public PointService(PointFeignClient pointFeign) {
        this.pointFeign = pointFeign;
    }

    public List<MemberPointResponse> getMemberPointHistories() {
        try {
            CommonResponse<List<MemberPointResponse>> response = pointFeign.getUserPointHistories();
            return response.getResult();
        } catch (FeignException.Unauthorized ex) {
            throw new RuntimeException("로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.");
        } catch (FeignException.BadRequest ex) {
            throw new RuntimeException("잘못된 요청입니다.");
        } catch (FeignException.NotFound ex) {
            throw new RuntimeException("포인트 내역을 찾을 수 없습니다.");
        } catch (FeignException ex) {
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.");
        }
    }
}
