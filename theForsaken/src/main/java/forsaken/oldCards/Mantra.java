package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.MantraPower;

public class Mantra extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Mantra.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC_AMT = 1;

    public Mantra() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.tags.add(CustomTags.WORD_CARD);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MantraPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}