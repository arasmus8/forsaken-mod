package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

import java.util.List;
import java.util.stream.Collectors;

public class UpgradedMantraPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(UpgradedMantraPower.class.getSimpleName());

    private final List<AbstractCard> allUnplayableCards;

    public UpgradedMantraPower() {
        super(POWER_ID, AbstractDungeon.player, -1);
        type = PowerType.BUFF;

        amount = 1;
        allUnplayableCards = CardLibrary.getAllCards().stream()
                .filter(card -> card.cost == -2 && card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS && card.color.equals(TheForsaken.Enums.COLOR_GOLD))
                .map(AbstractCard::makeCopy)
                .collect(Collectors.toList());

        loadRegion("mantra");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
            return;
        }
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for (int i = 0; i < amount; i++) {
            AbstractCard randomUnplayableCard = allUnplayableCards.get(AbstractDungeon.cardRandomRng.random(allUnplayableCards.size() - 1)).makeCopy();
            randomUnplayableCard.upgrade();
            topDeck(randomUnplayableCard);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new UpgradedMantraPower();
    }
}
