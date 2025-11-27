package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

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
        this(Settings.ACTION_DUR_XFAST, actionFunction, data);
    }

    @Override
    public void update() {
        isDone = actionFunction.test(firstUpdate, data);
        firstUpdate = false;
    }
}
