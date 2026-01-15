package forsaken.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import forsaken.TheForsakenMod;
import forsaken.cardmods.BonusDamageMod;

public class Sunblade extends AbstractForsakenRelic {
    public static final String ID = TheForsakenMod.makeID(Sunblade.class.getSimpleName());
    private static final int DAMAGE = 7;
    private boolean usedThisCombat = false;

    public Sunblade() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        usedThisCombat = false;
        pulse = true;
        beginPulse();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, DAMAGE), DAMAGE));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!usedThisCombat && c.type == AbstractCard.CardType.ATTACK) {
            flash();
            BonusDamageMod.applyToCard(c, DAMAGE);
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            usedThisCombat = true;
            grayscale = true;
            pulse = false;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
}
