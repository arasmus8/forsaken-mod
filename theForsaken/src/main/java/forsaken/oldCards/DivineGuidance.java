package forsaken.oldCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.DivineGuidanceAction;
import forsaken.characters.TheForsaken;

public class DivineGuidance extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(DivineGuidance.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;
    private static final int CARDS_TO_CHOOSE = 1;

    public DivineGuidance() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CardTags.HEALING);
        this.baseMagicNumber = CARDS_TO_CHOOSE;
        this.magicNumber = CARDS_TO_CHOOSE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DivineGuidanceAction(magicNumber, upgraded));
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