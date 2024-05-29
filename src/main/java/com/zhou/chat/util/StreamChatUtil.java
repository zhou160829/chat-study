package com.zhou.chat.util;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Arrays;

@Slf4j
public class StreamChatUtil {


    public static void streamCallWithMessage(Generation gen, Message userMsg, HtmlTextArea chatTextArea)
            throws NoApiKeyException, ApiException, InputRequiredException {
        GenerationParam param = buildGenerationParam(userMsg);
        Flowable<GenerationResult> result = gen.streamCall(param);
        result.blockingForEach(message -> chatTextArea.appendHtml(MarkdownUtils.markdownToHtml(message.getOutput().getChoices().get(0).getMessage().getContent())));

    }


    public static void streamCallWithCallback(Generation gen, Message userMsg, HtmlTextArea chatTextArea)
            throws NoApiKeyException, ApiException, InputRequiredException, InterruptedException {
        GenerationParam param = buildGenerationParam(userMsg);

        gen.streamCall(param, new ResultCallback<GenerationResult>() {
            @Override
            public void onEvent(GenerationResult message) {
                SwingUtilities.invokeLater(() -> {
                    chatTextArea.appendHtml(MarkdownUtils.markdownToHtml(message.getOutput().getChoices().get(0).getMessage().getContent()));

                    // 将滚动条滚动到最底部，以便查看最新的消息
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                });

            }

            @Override
            public void onError(Exception err) {
                SwingUtilities.invokeLater(() -> {
                    chatTextArea.appendHtml("Error occurred: " + err.getMessage() + "\n");
                });
            }

            @Override
            public void onComplete() {
                SwingUtilities.invokeLater(() -> {
                    chatTextArea.appendHtml("Conversation completed.\n");
                });
            }
        });

    }

    private static GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .messages(Arrays.asList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .incrementalOutput(true)
                .build();
    }

    public static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }

}
