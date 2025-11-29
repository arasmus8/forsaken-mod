package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.BiFunction;

public class XCostAction<T> extends AbstractGameAction {
    protected int baseValue;
    protected boolean freeToPlayOnce;
    private boolean firstUpdate = true;

    protected int effect;

    private final BiFunction<Integer, T, Boolean> xActionUpdate;
    private final T options;

    /**
     * @param card          The card played. Usually should simply be "this".
     * @param xActionUpdate A BiFunction that receives an integer for the energy amount (includes Chem X) and any number of integer parameters in the form of an array. The return value of this function is isDone.
     * @param options       an object that will be passed to the update function
     */
    public XCostAction(AbstractCard card, BiFunction<Integer, T, Boolean> xActionUpdate, T options) {
        this.baseValue = card.energyOnUse;
        this.freeToPlayOnce = card.freeToPlayOnce;
        this.xActionUpdate = xActionUpdate;

        this.options = options;
    }

    @Override
    public void update() {
        if (firstUpdate) {
            effect = EnergyPanel.totalCount;
            if (this.baseValue != -1) {
                effect = this.baseValue;
            }

            if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
                effect += 2;
                AbstractDungeon.player.getRelic(ChemicalX.ID).flash();
            }

            isDone = xActionUpdate.apply(effect, options) || duration < 0.0f;
            firstUpdate = false;

            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        } else {
            isDone = xActionUpdate.apply(effect, options) || duration < 0.0f;
        }
    }
}
