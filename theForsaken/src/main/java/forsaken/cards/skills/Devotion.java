package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;

public class Devotion extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Devotion.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public Devotion() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new DrawCardAction(magicNumber));
        qAction(new SelectCardsInHandAction(1, TEXT[0], this::moveAndUpgrade));
    }

    private void moveAndUpgrade(List<AbstractCard> list) {
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractCard c : list) {
            c.upgrade();
            qAction(new FunctionalAction(first -> {
                p.hand.moveToDeck(c, false);
                return true;
            }));
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");
        TEXT = uiStrings.TEXT;
    }
}