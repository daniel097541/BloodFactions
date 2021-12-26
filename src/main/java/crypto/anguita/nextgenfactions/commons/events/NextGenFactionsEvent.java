package crypto.anguita.nextgenfactions.commons.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public abstract class NextGenFactionsEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void launch(){
        EventLoop.launchEvent(this);
    }

    @Override
    public @NotNull
    HandlerList getHandlers() {
        return handlers;
    }
}
