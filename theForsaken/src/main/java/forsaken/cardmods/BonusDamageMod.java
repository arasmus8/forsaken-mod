package forsaken.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;

import java.util.ArrayList;
import java.util.Optional;

public class BonusDamageMod extends AbstractCardModifier {

    private static final String ID = TheForsakenMod.makeID(BonusDamageMod.class.getSimpleName());
    private int damage;

    public BonusDamageMod(int damage) {
        super();
        this.damage = damage;
    }

    // TODO: flash the card to indicate damage bonus

    public static BonusDamageMod applyToCard(AbstractCard c, int amount) {
        c.superFlash();
        Optional<BonusDamageMod> current = BonusDamageMod.getForCard(c);
        if (current.isPresent()) {
            BonusDamageMod mod = current.get();
            mod.damage += amount;
            return mod;
        }

        BonusDamageMod mod = new BonusDamageMod(amount);
        CardModifierManager.addModifier(c, mod);
        return mod;
    }

    public static Optional<BonusDamageMod> getForCard(AbstractCard c) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(c, ID);
        if (!modifiers.isEmpty()) {
            BonusDamageMod m =(BonusDamageMod)modifiers.get(0);
            return Optional.of(m);
        }
        return Optional.empty();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + this.damage;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BonusDamageMod(damage);
    }
}
