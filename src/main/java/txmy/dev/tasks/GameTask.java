package txmy.dev.tasks;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import txmy.dev.HungerGames;
import txmy.dev.event.GameEndEvent;
import txmy.dev.game.Game;

@RequiredArgsConstructor
public class GameTask extends BukkitRunnable {

    private final Game game;

    @Override
    public void run() {
        if(game.getState() != Game.State.GAME) {
            Bukkit.getLogger().severe("Game " + game.getId() + " is trying to run the game task but it isn't running, cancelling it's own task.");

            this.cancel();
            return;
        }

        game.setGameTime(game.getGameTime() + 1);

        if(game.getGameTime() >= 30 * 60) {
            GameEndEvent event = new GameEndEvent(game, GameEndEvent.EndReason.TIME_UP);
            event.call();
        }
    }

    public void call() {
        runTaskTimer(HungerGames.getInstance(), 0L, 20L);
    }

}
