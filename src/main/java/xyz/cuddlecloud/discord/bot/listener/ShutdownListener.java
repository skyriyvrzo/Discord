package xyz.cuddlecloud.discord.bot.listener;

import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class ShutdownListener extends ListenerAdapter {

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        System.out.println(event.getCloseCode());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
