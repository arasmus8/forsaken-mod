package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.TranquilityPower;

public class Tranquility extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Tranquility.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int MAGIC = 1;

    private static final int INCREASE_AMT = 1;

    public Tranquility() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}