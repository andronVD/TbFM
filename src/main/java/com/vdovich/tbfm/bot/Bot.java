package com.vdovich.tbfm.bot;/* Created by user on 03.01.20 */

import static com.vdovich.tbfm.util.ApiKeyWord.PHOTO_FROM_MARS_ROVER;
import static com.vdovich.tbfm.util.ApiKeyWord.PICTURE_OF_THE_DAY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.vdovich.tbfm.util.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.vdovich.tbfm.service.INasaService;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.api.token}")
    private String telegramApiToken;

    @Autowired
    INasaService service;

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public SendMessage sendInlineKeyboard(long chatId) {
        SendPhoto photo = new SendPhoto().setChatId(chatId).setPhoto("https://telegram-bot-sdk.readme.io/reference#forwardmessage");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Picture of the day").setCallbackData(PICTURE_OF_THE_DAY.toString()));
        }};
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Mars Rover Photos").setCallbackData(PHOTO_FROM_MARS_ROVER.toString()));
        }};

        List<List<InlineKeyboardButton>> rowList = new ArrayList<List<InlineKeyboardButton>>() {{
            add(keyboardButtonsRow1);
            add(keyboardButtonsRow2);
        }};
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage()
                .setChatId(chatId).setText("The data from the NASA").setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/nasa")) {
                try {
                    execute(sendInlineKeyboard(update.getMessage().getChatId()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.getCallbackQuery().getData().equals(PICTURE_OF_THE_DAY.toString())) {
            try {
                Map<JsonProperty, String> result = service.getPictureOfTheDay();
                execute(new SendPhoto().setPhoto(result.get(JsonProperty.URL)).setChatId(update
                        .getCallbackQuery().getMessage().getChatId()).setCaption(result.get(JsonProperty.EXPLANATION)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getCallbackQuery().getData().equals(PHOTO_FROM_MARS_ROVER.toString())) {
            try {
                Map<JsonProperty, String> result = service.getPictureFromMars();
                execute(new SendPhoto().setPhoto(result.get(JsonProperty.IMG_SRC)).setChatId(update
                        .getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return telegramApiToken;
    }
}

