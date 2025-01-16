package com.onebook.frontapi.service.point;

import com.onebook.frontapi.common.CommonResponse;
import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.feign.point.PointFeignClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PointService {

    private final PointFeignClient pointFeign;

    public PointService(PointFeignClient pointFeign) {
        this.pointFeign = pointFeign;
    }

    public List<MemberPointResponse> getMemberPointHistories() {
        try {
            // FeignClient 호출
            CommonResponse<List<MemberPointResponse>> response = pointFeign.getUserPointHistories();

            // 정상적으로 반환된 포인트 내역을 반환
            if (response == null || response.getResult() == null) {
                throw new IllegalStateException("잘못된 상태: 포인트 내역을 찾을 수 없음");
            }

            return response.getResult();
        } catch (FeignException.Unauthorized ex) {
            // Unauthorized (로그인 필요)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.", ex);
        } catch (FeignException.BadRequest ex) {
            // BadRequest (잘못된 요청)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", ex);
        } catch (FeignException.NotFound ex) {
            // NotFound (리소스를 찾을 수 없음)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "포인트 내역을 찾을 수 없습니다.", ex);
        } catch (FeignException.InternalServerError ex) {
            // Internal Server Error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", ex);
        } catch (IllegalStateException ex) {
            // 잘못된 상태에 대한 예외 처리
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다.", ex);
        } catch (Exception ex) {
            // 그 외의 모든 예외 처리
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.", ex);
        }
    }

    // Optional<ByteBuffer>를 byte[]로 변환하는 메서드
    private byte[] getResponseBodyAsByteArray(Optional<ByteBuffer> responseBody) {
        if (responseBody.isPresent()) {
            ByteBuffer byteBuffer = responseBody.get();
            byte[] byteArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byteArray);  // ByteBuffer를 byte[]로 변환
            return byteArray;
        }
        return new byte[0];  // 빈 바디일 경우 빈 배열 반환
    }
}
