package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.DesperatePleaAction;
import theForsaken.characters.TheForsaken;

import java.util.logging.Level;
import java.util.logging.Logger;

import static theForsaken.TheForsakenMod.makeCardPath;

public class DesperatePlea extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(DesperatePlea.class.getSimpleName());
    public static final String IMG = makeCardPath("DesperatePlea.png");
    // Must have an image with the same NAME as the card in your image folder!


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 3;

    private static final int STRENGTH_AMT = 4;
    private static final int UPGRADE_STRENGTH_AMT = 2;

    // /STAT DECLARATION/


    public DesperatePlea() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = STRENGTH_AMT;
        magicNumber = STRENGTH_AMT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DesperatePleaAction(p, magicNumber));
        if (p.isBloodied) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STRENGTH_AMT);
            initializeDescription();
        }
    }
}