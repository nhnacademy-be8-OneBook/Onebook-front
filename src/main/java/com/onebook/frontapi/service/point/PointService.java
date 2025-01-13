package com.onebook.frontapi.service.point;

import com.onebook.frontapi.common.CommonResponse;
import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.feign.point.PointFeignClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PointService {

    private final PointFeignClient pointFeign;

    public PointService(PointFeignClient pointFeign) {
        this.pointFeign = pointFeign;
    }

    // PointService에서 포인트 내역 가져오기
    public List<MemberPointResponse> getMemberPointHistories() {
        try {
            CommonResponse<List<MemberPointResponse>> response = pointFeign.getUserPointHistories();

            if (response == null || response.getResult() == null) {
                throw new IllegalStateException("잘못된 상태: 포인트 내역을 찾을 수 없음");
            }

            return response.getResult();
        } catch (FeignException.Unauthorized ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.", ex);
        } catch (FeignException.BadRequest ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", ex);
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "포인트 내역을 찾을 수 없습니다.", ex);
        } catch (FeignException.InternalServerError ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.", ex);
        }
    }
}