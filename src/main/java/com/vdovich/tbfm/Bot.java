package com.vdovich.tbfm;/* Created by user on 03.01.20 */

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    private static String BOT_NAME = "SortingBot";
    private static String API_TOKEN = "908897882:AAHGta7UI5RmcP6w9di-vX465BIne0iTKIo";

    public static SendMessage sendInlineKeyboard(long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Picture of the day").setCallbackData("you choose 1st button"));
        }};
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton>() {{
            add(new InlineKeyboardButton().setText("Mars Rover Photos").setCallbackData("you choose 2nd button"));
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
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update
                        .getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return API_TOKEN;
    }
}

