package txmy.dev.tasks;

import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import txmy.dev.HungerGames;
import txmy.dev.event.GameStartEvent;
import txmy.dev.game.Game;

@RequiredArgsConstructor
public class GameStartTask extends BukkitRunnable {

    private final Game game;

    @Override
    public void run() {
        game.setStartTime(game.getStartTime() - 1);

        if(game.getStartTime() <= 0) {
            this.cancel();

            GameStartEvent event = new GameStartEvent(game);
            event.call();
        }
    }

    public void call() {
        runTaskTimer(HungerGames.getInstance(), 0L, 20L);
    }

}
