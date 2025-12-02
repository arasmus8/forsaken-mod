package forsaken.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.function.BiPredicate;

public class BiFunctionalAction<T> extends AbstractGameAction {
    private final BiPredicate<Boolean, T> actionFunction;
    private final T data;
    private boolean firstUpdate;

    public BiFunctionalAction(float duration, BiPredicate<Boolean, T> actionFunction, T data) {
        this.duration = startDuration = duration;
        this.actionFunction = actionFunction;
        this.data = data;
        firstUpdate = true;
    }

    public BiFunctionalAction(BiPredicate<Boolean, T> actionFunction, T data) {
        this(0f, actionFunction, data);
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F) {
            isDone = actionFunction.test(firstUpdate, data);
            firstUpdate = false;
            if (!isDone) {
                duration = startDuration;
            }
        }
    }
}
