package com.let.config;

import com.let.event.MapleNameResponseEvent;
import com.let.event.ScheduleSelectListenerEvnet;
import com.let.event.SlashComandEvent;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * packageName    : com.let.config
 * fileName       : DiscordBotConfig
 * author         : jun
 * date           : 25. 12. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 10.        jun       최초 생성
 */
@Component
@RequiredArgsConstructor
public class DiscordBotConfig {

    @Value("${discord.bot.token}")
    private String token;
    // ✅ 스프링이 관리하는 이벤트 리스너 빈을 주입받는다
    private final MapleNameResponseEvent mapleNameResponseEvent;
    private final SlashComandEvent slashComandEvent;
    private final ScheduleSelectListenerEvnet scheduleSelectListenerEvnet;

    @Bean //
    public JDA jda() {

        //봇 권한
        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES);

        JDA jda = JDABuilder.createDefault(token,intents)
                    .setActivity(Activity.customStatus("!명령어로 명령어 확인 가능 :) "))
                .addEventListeners(
                        mapleNameResponseEvent,
                        slashComandEvent,
                        scheduleSelectListenerEvnet)
                    .build();
        return jda;
    }

}
