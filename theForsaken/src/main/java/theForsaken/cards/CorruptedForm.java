package theForsaken.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.CorruptedFormPower;

public class CorruptedForm extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CorruptedForm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static final int REDUCE_BY = 3;
    private static final int UPGRADE_REDUCE_BY = -1;

    public CorruptedForm() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, BaseModCardTags.FORM);
        baseMagicNumber = REDUCE_BY;
        magicNumber = REDUCE_BY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CorruptedFormPower(p, this.magicNumber, this.upgraded)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REDUCE_BY);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}