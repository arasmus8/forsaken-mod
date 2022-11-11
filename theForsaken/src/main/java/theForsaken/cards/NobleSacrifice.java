package theForsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.NobleSacrificeAction;
import theForsaken.characters.TheForsaken;

public class NobleSacrifice extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(NobleSacrifice.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public NobleSacrifice() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int extraDraw = 0;
        if (upgraded) {
            extraDraw += 1;
        }
        AbstractDungeon.actionManager.addToBottom(new NobleSacrificeAction(extraDraw));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}