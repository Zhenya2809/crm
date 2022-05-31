package com.evgeniy.telegram.inline;

import com.evgeniy.entity.Doctor;
import com.evgeniy.service.DoctorService;
import com.evgeniy.telegram.MyAppBot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class InlineTelegramBot {
    @Autowired
    private DoctorService doctorService;
    private static final Integer CACHETIME = 1;

    public void handleIncomingInlineQuery(InlineQuery inlineQuery, MyAppBot myAppBot) {
        String query = inlineQuery.getQuery();
        System.out.println("Searching: " + query);
        try {
            myAppBot.execute(convertResultsToResponse(inlineQuery));
        } catch (Throwable e) {
            log.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

    private AnswerInlineQuery convertResultsToResponse(InlineQuery inlineQuery) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getId());
        System.out.println("answerID=" + inlineQuery.getId());
        answerInlineQuery.setCacheTime(CACHETIME);
        answerInlineQuery.setIsPersonal(true);
        answerInlineQuery.setResults(convertResults(inlineQuery.getQuery()));
        return answerInlineQuery;
    }

    private List<InlineQueryResult> convertResults(String query) {

        String[] array = query.split(",");
        String speciality = query;
        String fio = "";

        if (array.length == 2) {
            speciality = array[0];
            fio = array[1];
        }

        List<Doctor> doctorsList = doctorService.searchDoctor(speciality.toLowerCase(Locale.ROOT), fio.toLowerCase(Locale.ROOT)).stream().toList();
        List<InlineQueryResult> results = new ArrayList<>();

        for (Doctor doctor : doctorsList) {

            InlineQueryResultArticle article = new InlineQueryResultArticle();
            InputTextMessageContent messageContent = new InputTextMessageContent();

            messageContent.setDisableWebPagePreview(true);
            messageContent.setParseMode(ParseMode.MARKDOWN);
            messageContent.setMessageText(doctor.getAbout());
            article.setInputMessageContent(messageContent);
            article.setId(doctor.getId().toString());
            article.setTitle(doctor.getSpeciality());
            article.setDescription(doctor.getFio());
            article.setThumbUrl(doctor.getPhoto());
            results.add(article);

        }

        return results;
    }
}
