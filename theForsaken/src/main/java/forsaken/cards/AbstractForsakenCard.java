package forsaken.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.characters.TheForsaken;
import forsaken.util.ActionUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static forsaken.TheForsakenMod.cardResourcePath;
import static forsaken.TheForsakenMod.getModID;

public abstract class AbstractForsakenCard extends CustomCard implements ActionUnit {
    protected final String NAME;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;
    protected Integer upgradeDamageBy;
    protected Integer upgradeBlockBy;
    protected Integer upgradeMagicNumberBy;
    protected Integer upgradeCostTo;
    protected boolean unplayedEffect = false;

    public AbstractForsakenCard(final String id,
                                final int cost,
                                final AbstractCard.CardType type,
                                final AbstractCard.CardRarity rarity,
                                final AbstractCard.CardTarget target,
                                final AbstractCard.CardColor color,
                                CardTags... tagsList) {
        super(id, "", getRegionName(id), cost, "", type, color, rarity, target);
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
        if (tagsList != null) {
            tags.addAll(Arrays.asList(tagsList));
        }
    }

    public AbstractForsakenCard(final String id,
                                final int cost,
                                final AbstractCard.CardType type,
                                final AbstractCard.CardRarity rarity,
                                final AbstractCard.CardTarget target
                                ) {
        this(id, cost, type, rarity, target, TheForsaken.Enums.COLOR_GOLD);
    }

    private static String getBaseImagePath(String id) {
        return id.replaceAll(getModID() + ":", "");
    }

    private static CustomCard.RegionName getRegionName(String id) {
        return new CustomCard.RegionName(String.format("%s/%s", getModID(), getBaseImagePath(id)));
    }

    @Override
    public void loadCardImage(String img) {
        TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");
        portrait = cardAtlas.findRegion(img);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(cardResourcePath(String.format("%s_p.png", getBaseImagePath(cardID))));
    }

    @Override
    public void upgrade() {
        if (!upgraded && canUpgrade()) {
            upgradeName();
            if (upgradeDamageBy != null) {
                upgradeDamage(upgradeDamageBy);
            }
            if (upgradeBlockBy != null) {
                upgradeBlock(upgradeBlockBy);
            }
            if (upgradeMagicNumberBy != null) {
                upgradeMagicNumber(upgradeMagicNumberBy);
            }
            if (upgradeCostTo != null) {
                upgradeBaseCost(upgradeCostTo);
            }
            if (UPGRADE_DESCRIPTION != null) {
                rawDescription = UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    // called when cards are added during combat
    public void triggerWhenCardAddedInCombat(AbstractCard c) {
    }

    // called when turn ends while in hand


    // This default behavior will call `use` with `dontTriggerOnUseCard` set to true
    // (the card should be set to 'autoplay' -- bypassing energy use/requirement)
    // -- override to do something special
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (unplayedEffect) {
            dontTriggerOnUseCard = true;
            CardQueueItem item = new CardQueueItem(this, true);
            item.autoplayCard = true;
            item.randomTarget = true;
            AbstractDungeon.actionManager.addCardQueueItem(item);
        }
    }

    // Called when the player takes damage, before block is considered
    // The return value will override the damage taken
    public int triggerOnPlayerDamaged(int damage, DamageInfo info) {
        return damage;
    }

    public DamageInfo makeDamageInfo(int amount, DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, amount, damageTypeForTurn);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAction(m, makeDamageInfo(damage, damageTypeForTurn), fx));
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void gainBlock() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    public static boolean isUnplayable(AbstractCard c) {
        return c.cost == -2;
    }

    public static List<AbstractCard> unplayableCards(CardGroup group) {
        return group.group.stream().filter(AbstractForsakenCard::isUnplayable).collect(Collectors.toList());
    }

    public static List<AbstractCard> unplayableCards(boolean inHand, boolean inDrawPile, boolean inDiscardPile) {
        ArrayList<AbstractCard> combined = new ArrayList<>();
        if (inHand) {
            combined.addAll(unplayableCards(AbstractDungeon.player.hand));
        }
        if (inDrawPile) {
            combined.addAll(unplayableCards(AbstractDungeon.player.drawPile));
        }
        if (inDiscardPile) {
            combined.addAll(unplayableCards(AbstractDungeon.player.discardPile));
        }
        return combined;
    }
}
