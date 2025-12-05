package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

@SuppressWarnings("unused")
public class Backlash extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Backlash.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int MAGIC = 5;
    private static final int UPGRADED_MAGIC = 2;

    public Backlash() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlameBarrierPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlameBarrierEffect(p.drawX, p.drawY)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}