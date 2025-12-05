package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.actions.XCostAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.characters.TheForsaken;
import forsaken.powers.SunlightPower;

import java.util.Optional;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class DarkCloud extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DarkCloud.class.getSimpleName());

    public DarkCloud() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TheForsaken.Enums.COLOR_GOLD, "DarkBarrier");
        cardsToPreview = new DarkFog();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BiFunction<Integer, Boolean, Boolean> action = (x, isUpgraded) -> {
            if (isUpgraded) {
                // trigger Sunlight x times
                for (int i = 0; i < x; i++) {
                    qAction(new FunctionalAction(firstUpdate -> {
                        Optional<AbstractPower> maybeSunlight = Optional.ofNullable(p.getPower(SunlightPower.POWER_ID));
                        maybeSunlight.ifPresent(AbstractPower::onSpecificTrigger);
                        return true;
                    }));
                }
            }
            shuffleIn(new DarkFog(), x);
            return true;
        };
        qAction(new XCostAction<>(this, action, upgraded));
    }
}