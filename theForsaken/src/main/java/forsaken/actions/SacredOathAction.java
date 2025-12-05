package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SacredOathAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int amount;

    public SacredOathAction(int amount) {
        this.actionType = ActionType.WAIT;
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            if (this.p.hasPower("Strength")) {
                int strAmt = this.amount * this.p.getPower("Strength").amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new StrengthPower(this.p, strAmt), strAmt));
            }
            if (this.p.hasPower("Dexterity")) {
                int dexAmt = this.amount * this.p.getPower("Dexterity").amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new DexterityPower(this.p, dexAmt), dexAmt));
            }
        }

        this.tickDuration();
    }
}
