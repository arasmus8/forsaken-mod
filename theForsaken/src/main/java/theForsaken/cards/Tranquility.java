package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.TranquilityPower;

import static theForsaken.TheForsakenMod.makeCardPath;

public class Tranquility extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(Tranquility.class.getSimpleName());
    public static final String IMG = makeCardPath("Tranquility.png");
    // Must have an image with the same NAME as the card in your image folder!

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int MAGIC = 1;

    private static final int INCREASE_AMT = 1;

    // /STAT DECLARATION/


    public Tranquility() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.misc = MAGIC;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.misc;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new IncreaseMiscAction(this.uuid, this.misc, INCREASE_AMT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber));
        if (!m.hasPower(ArtifactPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TranquilityPower(m, magicNumber), magicNumber));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}