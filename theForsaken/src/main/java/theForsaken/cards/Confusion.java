package theForsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import theForsaken.TheForsakenMod;
import theForsaken.actions.ConfusionAction;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class Confusion extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(Confusion.class.getSimpleName());
    public static final String IMG = makeCardPath("Confusion.png");
    // Must have an image with the same NAME as the card in your image folder!.
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESC = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESC = CARD_STRINGS.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 1;
    // /STAT DECLARATION/

    public Confusion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ConfusionAction(m));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse && m != null) {
            if (m.intent == Intent.ATTACK || m.intent == Intent.ATTACK_BUFF || m.intent == Intent.ATTACK_DEBUFF || m.intent == Intent.ATTACK_DEFEND) {
                return true;
            } else {
                this.cantUseMessage = EXTENDED_DESC[0];
                return false;
            }
        }
        return canUse;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESC;
            initializeDescription();
        }
    }
}