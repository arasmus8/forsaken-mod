package forsaken.cards.skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.BiFunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.AbstractQuickdrawCard;

@SuppressWarnings("unused")
public class HymnOfRest extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(HymnOfRest.class.getSimpleName());

    public HymnOfRest() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.hand.group.stream()
                .filter(AbstractQuickdrawCard::isQuickdraw)
                .forEach(card -> qAction(new BiFunctionalAction<>(this::shuffleQuickdrawCards, card)));
    }

    private boolean shuffleQuickdrawCards(boolean firstUpdate, AbstractCard card) {
        AbstractDungeon.player.hand.moveToDeck(card, true);
        return true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }
}