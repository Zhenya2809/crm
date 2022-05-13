package com.evgeniy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Data
@AllArgsConstructor
public class InlineButton {
    private String text;
    private String url;

}
