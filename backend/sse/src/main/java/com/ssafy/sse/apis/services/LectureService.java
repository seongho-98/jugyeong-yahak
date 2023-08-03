package com.ssafy.sse.apis.services;

import com.ssafy.sse.apis.repositories.EmitterRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class LectureService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    private SseEmitter createEmitter(Long classId, String streamId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(classId, streamId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(classId, streamId));
        emitter.onTimeout(() -> emitterRepository.deleteById(classId, streamId));

        return emitter;
    }

    public SseEmitter subscribe(Long classId, String streamId) {
        SseEmitter emitter = createEmitter(classId, streamId);

        sendToClients(classId, streamId, "connect", "EventStream Created. [class=" + classId + "]");
        return emitter;
    }

    public void controlMic(Long classId, String streamId, Object event) {
        sendToClient(classId, streamId, "mic", event);
    }

    public void moveMousePointer(Long classId, String streamId, Object event) {
        sendToClients(classId, streamId, "mouse", event);
    }

    public void convertPage(Long classId, String streamId, Object event) {
        sendToClients(classId, streamId, "page", event);
    }

    private void sendToClients(Long classId, String streamId, String name, Object data) {
        List<SseEmitter> emitters = emitterRepository.get(classId);
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                                       .name(name)
                                       .data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(classId, streamId);
                emitter.completeWithError(exception);
            }
        });
    }

    private void sendToClient(Long classId, String streamId, String name, Object data) {
        SseEmitter emitter = emitterRepository.get(classId, streamId);
        try {
            emitter.send(SseEmitter.event()
                                   .name(name)
                                   .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(classId, streamId);
            emitter.completeWithError(exception);
        }
    }
}