package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;
import forsaken.util.TextureLoader;

import java.util.Objects;
import java.util.logging.Logger;

import static forsaken.TheForsakenMod.relicOutlineResourcePath;
import static forsaken.TheForsakenMod.relicResourcePath;


public class HumbleEgg extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * If you play no attacks, gain 1 strength, If you play no skills, gain 1 dexterity.
     */

    // ID, images, text.
    public static final String ID = TheForsakenMod.makeID(HumbleEgg.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("HumbleEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("HumbleEgg.png"));

    private boolean listen = false;

    private static final Logger log = Logger.getLogger(ID);

    public HumbleEgg() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        log.info("atTurnStart - start listening");
        listen = true;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        log.info("card drawn - listening?" + (listen ? " TRUE" : " FALSE"));
        if (listen && drawnCard.rarity == AbstractCard.CardRarity.COMMON) {
            this.flash();
            log.info("found common card " + drawnCard.name);
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            listen = false;
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.combatRewardScreen.rewards.stream()
                .filter(Objects::nonNull)
                .filter(reward -> reward.cards != null)
                .forEach(reward -> reward.cards.stream()
                        .filter(card -> card.rarity == AbstractCard.CardRarity.COMMON)
                        .forEach(this::onPreviewObtainCard));
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.rarity == AbstractCard.CardRarity.COMMON && !c.upgraded && c.canUpgrade()) {
            c.upgrade();
        }
    }
}