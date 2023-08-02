package com.ssafy.http.apis.openvidu.controllers;

import com.ssafy.http.apis.openvidu.services.OpenviduService;
import com.ssafy.http.security.utils.SecurityUtil;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/openvidu")
public class OpenviduPrivateController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    private OpenviduService openviduService;

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);

        System.out.println(connection.getToken());
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

//    /**
//     * @return The Token associated to the Connection
//     */
//    @PostMapping("/connections")
//    public ResponseEntity<String> createConnection()
//            throws OpenViduJavaClientException, OpenViduHttpException {
//
//        Optional<Long> sessionId = openviduService.getSessionId(SecurityUtil.getLoginUserId());
//
//        Session session = openvidu.getActiveSession(String.valueOf(sessionId));
//        if (session == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        Connection connection = session.createConnection();
//
//        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
//    }

}
