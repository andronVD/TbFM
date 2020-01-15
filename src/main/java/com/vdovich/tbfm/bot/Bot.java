package com.vdovich.tbfm.bot;/* Created by user on 03.01.20 */

import com.vdovich.tbfm.service.INasaService;
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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
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
            add(new InlineKeyboardButton().setText("Picture of the day").setCallbackData("1"));
        }};
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Mars Rover Photos").setCallbackData("2"));
        }};
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Earth Polychromatic Imaging Camera").setCallbackData("you choose 3rd button"));
        }};
        List<List<InlineKeyboardButton>> rowList = new ArrayList<List<InlineKeyboardButton>>() {{
            add(keyboardButtonsRow1);
            add(keyboardButtonsRow2);
            add(keyboardButtonsRow3);
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
        }        else if (update.getCallbackQuery().getData().equals("1")) {
            try {
                execute(new SendPhoto().setPhoto(service.getPictureOfTheDay()).setChatId(update
                        .getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getCallbackQuery().getData().equals("2")) {
            try {
                execute(new SendPhoto().setPhoto(service.getPictureFromMars()).setChatId(update
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

