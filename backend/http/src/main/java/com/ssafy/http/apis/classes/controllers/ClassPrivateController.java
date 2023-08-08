package com.ssafy.http.apis.classes.controllers;

import static com.ssafy.http.support.utils.ApiResponseUtil.createErrorResponse;
import static com.ssafy.http.support.utils.ApiResponseUtil.createSuccessResponse;

import com.ssafy.http.apis.classes.request.ClassRequest;
import com.ssafy.http.apis.classes.responses.ClassDetailResponse;
import com.ssafy.http.apis.classes.services.ClassService;
import com.ssafy.http.apis.members.services.MemberService;
import com.ssafy.http.security.utils.SecurityUtil;
import com.ssafy.http.support.codes.ErrorCode;
import com.ssafy.http.support.codes.SuccessCode;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classes") // -> api/v1/private/classes
@RequiredArgsConstructor
public class ClassPrivateController {

  private final ClassService classService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<?> registClasses(@RequestBody ClassRequest classRequest) {

    classService.registClass(classRequest, SecurityUtil.getLoginUserId());

    return createSuccessResponse(SuccessCode.INSERT_SUCCESS, "반 등록에 성공하였습니다.");
  }


  @GetMapping("/list") //교사가 속한 반 전체 조회
  public ResponseEntity<?> getGovernmentClasses() {

    //Long governmentId = memberService.getGovernmentId(SecurityUtil.getLoginUserId());

    List<ClassDetailResponse> classDetailResponses = classService.getClassList(
        SecurityUtil.getLoginUserId());

    return createSuccessResponse(SuccessCode.SELECT_SUCCESS, "지자체가 만든 반을 전체조회 하였습니다.",
        classDetailResponses);
  }

  @DeleteMapping("/one")
  public ResponseEntity<?> deleteClass(@RequestBody Map<String, Long> requestData) {

    if (requestData.get("id") == null) {
      return createErrorResponse(ErrorCode.REQUEST_BODY_MISSING_ERROR, "방의 id 값을 포함하지 않았습니다");
    }

    classService.deleteOne(requestData.get("id"));

    return createSuccessResponse(SuccessCode.DELETE_SUCCESS,
        requestData.get("id") + " 반을 삭제하였습니다.",
        classService.getClassList(SecurityUtil.getLoginUserId())
    );
  }

  @PutMapping("/one")
  public ResponseEntity<?> putClass(@RequestBody ClassRequest classrequest) {
    //SecurityUtil.getLoginUserId()
    classService.updateClass(classrequest, 4L);

    System.out.println("여기까지 오나?");

    //204라서 response가 안보임
    return createSuccessResponse(SuccessCode.UPDATE_SUCCESS,
        " 반을 수정하였습니다.",
        classService.getClassList(SecurityUtil.getLoginUserId()) //상세 보기로 변경할 예정?
    );
  }

  @PostMapping("/one")
  public ResponseEntity<?> getClassDetails(@RequestBody Map<String, Long> requestData) {
    if (requestData.get("id") == null) {
      return createErrorResponse(ErrorCode.REQUEST_BODY_MISSING_ERROR, "방의 id 값을 포함하지 않았습니다");
    }

    ClassDetailResponse classDetailResponse = classService.selectOne(requestData.get("id"));

    return createSuccessResponse(SuccessCode.SELECT_SUCCESS,
        classDetailResponse.getId() + " 반을 상세 조회하였습니다.",
        classDetailResponse);
  }

}
