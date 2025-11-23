package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.WordsOfMightPower;

public class WordsOfMight extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(WordsOfMight.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int POWER = 1;
    private static final int UPGRADE_POWER = 1;

    public WordsOfMight() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CustomTags.WORD_CARD);
        baseMagicNumber = POWER;
        magicNumber = POWER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WordsOfMightPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_POWER);
            initializeDescription();
        }
    }
}