package com.ssafy.http.apis.classes.controllers;

import com.ssafy.http.apis.classes.entities.ClassEntity;
import com.ssafy.http.apis.classes.responses.ClassDetailResponse;
import com.ssafy.http.apis.classes.services.ClassService;
import com.ssafy.http.apis.members.services.MemberService;
import com.ssafy.http.security.utils.SecurityUtil;
import com.ssafy.http.support.codes.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.http.support.utils.ApiResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassPrivateController {

    private ClassService classService;
    private MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<?> getClassList() {

        Long governmentId = memberService.getGovernmentId(SecurityUtil.getLoginUserId());

        List<ClassDetailResponse> classDetailResponses = classService.getClassList(governmentId);

        return createSuccessResponse(SuccessCode.SELECT_SUCCESS, "교사가 속한 지자체의 반을 전체조회 하였습니다.",
                classDetailResponses);
    }


}