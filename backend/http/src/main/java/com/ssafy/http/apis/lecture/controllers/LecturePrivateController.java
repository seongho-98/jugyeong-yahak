package com.ssafy.http.apis.lecture.controllers;

import com.ssafy.http.apis.lecture.requests.ControlMicRequest;
import com.ssafy.http.apis.lecture.requests.ConvertPageRequest;
import com.ssafy.http.apis.lecture.requests.MoveMouseRequest;
import com.ssafy.http.apis.lecture.services.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/lecture")
@RequiredArgsConstructor
public class LecturePrivateController {

    private final LectureService lectureService;

    @PostMapping("/convert/page")
    public void convertPage(@RequestBody ConvertPageRequest convertPageRequest) {
        lectureService.convertPage(convertPageRequest);
    }

    @PostMapping("/mouse/pointer")
    public void moveMouseCursor(@RequestBody MoveMouseRequest moveMouseRequest) {
        lectureService.moveMousePointer(moveMouseRequest);
    }

    @PostMapping("/mic/control")
    public void controlMic(@RequestBody ControlMicRequest controlMicRequest) {
        lectureService.controlMic(controlMicRequest);
    }

}
