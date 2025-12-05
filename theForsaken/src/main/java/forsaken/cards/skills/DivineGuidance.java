package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.characters.TheForsaken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class DivineGuidance extends AbstractForsakenCard {
    private static final Logger logger = LogManager.getLogger(DivineGuidance.class);
    public static final String ID = TheForsakenMod.makeID(DivineGuidance.class.getSimpleName());

    public DivineGuidance() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup unplayableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        unplayableCards.group.addAll(CardLibrary.cards.values().stream()
                .filter(card -> card.color == TheForsaken.Enums.COLOR_GOLD)
                .filter(AbstractForsakenCard::isUnplayable)
                .collect(Collectors.toList()));
        logger.info("Unplayable cards from library: {}", unplayableCards);
        p.hand.group.forEach(card -> {
            if (card == this) return;
            qAction(new ExhaustSpecificCardAction(card, p.hand, true));
            AbstractCard randomCard = unplayableCards.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();
            if (upgraded) {
                randomCard.upgrade();
            }
            makeInHand(randomCard);
        });
    }
}