package forsaken.potions;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import forsaken.TheForsakenMod;

public class BottledPlaguePotion extends AbstractPotion implements CustomSavable<Integer> {
    public static final String POTION_ID = TheForsakenMod.makeID(BottledPlaguePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    private int poisonAmt = 6;

    public static final Color LIQUID_COLOR = new Color(0x3C5E1CA0);
    public static final Color HYBRID_COLOR = new Color(0xA3CA7DB4);
    public static final Color SPOTS_COLOR = new Color(0xFFC2DDC8);

    public BottledPlaguePotion(int poisonAmt) {
        super(
                potionStrings.NAME,
                POTION_ID,
                PotionRarity.RARE,
                PotionSize.SPHERE,
                PotionColor.POISON
        );

        this.poisonAmt = poisonAmt;

        isThrown = true;
        targetRequired = true;
        initializeData();
    }

    public BottledPlaguePotion() {
        this(6);
    }

    @Override
    public void initializeData() {
        potency = getPotency();

        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];

        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.POISON.NAMES[0]), GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && target != null) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(target, p, new PoisonPower(target, p, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledPlaguePotion(poisonAmt);
    }

    @Override
    public int getPotency(int ascLevel) {
        return poisonAmt;
    }

    @Override
    public Integer onSave() {
        return poisonAmt;
    }

    @Override
    public void onLoad(Integer value) {
        poisonAmt = value;
        initializeData();
    }
}
