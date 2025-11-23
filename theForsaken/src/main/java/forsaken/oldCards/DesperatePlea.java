package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.actions.DesperatePleaAction;
import forsaken.characters.TheForsaken;

public class DesperatePlea extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(DesperatePlea.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static final int STRENGTH_AMT = 4;
    private static final int UPGRADE_STRENGTH_AMT = 2;

    public DesperatePlea() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CustomTags.WORD_CARD);
        baseMagicNumber = STRENGTH_AMT;
        magicNumber = STRENGTH_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DesperatePleaAction(p, magicNumber));
        if (p.isBloodied) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STRENGTH_AMT);
            initializeDescription();
        }
    }
}