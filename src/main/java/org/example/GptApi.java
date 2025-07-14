package org.example;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.Arrays;

public class GptApi {



    private String[] gpt(String question, int manyQuestion) {

        String[] parts;

        try {
            OkHttpClient client = new OkHttpClient();
            String porompt = "I want you to build me " + manyQuestion + " question/s with 3 answers in order " +
                    "of question with answers after question with answers, in the following format:\n" +
                    "(Question)\n" +
                    "(Answer1)\n" +
                    "(Answer2)\n" +
                    "(Answer3)\n" +
                    "\n" +
                    "Not a test-like question, but a preference questionnaire.\n" +
                    "I want it to be in this format without additions before and after, between each sentence - question or answer there will be a # \n" +
                    "The topic of the question is only about:" + question;
            String url = "https://app.seker.live/fm1/send-message?id=330745084&text=" +
                    URLEncoder.encode(porompt, StandardCharsets.UTF_8);

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                question = jsonObject.getString("extra");
                parts = question.split("#");
                System.out.println(Arrays.toString(parts));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parts;
    }

    public Poll createByGpt(String sub, int sumQuestions){
        String[] parts = gpt(sub, sumQuestions);
        int totalItem = parts.length / 4;
        Poll poll = new Poll(totalItem);

        for (int i = 0; i < totalItem; i++) {
            int index = i * 4;
            String question = parts[index].trim();
            PollItem pollItem = new PollItem(question);

            for (int j = 1; j < 3; j++) {
                pollItem.addAnswer(parts[index + j].trim());
            }
            poll.addQuestion(pollItem);
        }
        return poll;
    }

}
