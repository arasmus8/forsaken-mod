package forsaken.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import forsaken.TheForsakenMod;
import forsaken.powers.SunlightPower;

public class EstusFlask extends AbstractPotion {
    public static final String POTION_ID = TheForsakenMod.makeID(EstusFlask.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final Color LIQUID_COLOR = new Color(0xF2B93BA0);
    public static final Color HYBRID_COLOR = new Color(0xC24A2AAA);
    public static final Color SPOTS_COLOR = new Color(0xFFF3C4C8);

    public EstusFlask() {
        super(
                potionStrings.NAME,
                POTION_ID,
                PotionRarity.UNCOMMON,
                PotionSize.SPHERE,
                PotionColor.ANCIENT
        );

        isThrown = false;
        initializeData();
    }

    @Override
    public void initializeData() {
        potency = getPotency();

        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && target != null) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(target, p, new SunlightPower(potency), potency));
        }
    }

    @Override
    public int getPotency(int ascLevel) {
        return 4;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EstusFlask();
    }
}
