package forsaken;

import basemod.*;
import basemod.abstracts.CustomCard;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.AbstractQuickdrawCard;
import forsaken.cards.Smite;
import forsaken.characters.TheForsaken;
import forsaken.events.PlagueDoctorEvent;
import forsaken.oldCards.AbstractOldForsakenCard;
import forsaken.potions.FearPotion;
import forsaken.powers.HymnOfRestPower;
import forsaken.relics.*;
import forsaken.util.AssetLoader;
import forsaken.util.TextureHelper;
import forsaken.variables.SacrificeSoulVariable;
import forsaken.variables.UnplayedCardsVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@SuppressWarnings("unused")
@SpireInitializer
public class TheForsakenMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnCardUseSubscriber,
        OnPlayerLoseBlockSubscriber,
        OnStartBattleSubscriber,
        OnPlayerTurnStartPostDrawSubscriber,
        PostInitializeSubscriber,
        PostDrawSubscriber {

    private static final Logger logger = LogManager.getLogger(TheForsakenMod.class.getName());

    public static final String IMAGE_PATH = "forsakenResources/images";
    private static final String modID = "forsaken";

    private static final Properties defaultConfigSettings = new Properties();
    private static final String ALL_RELICS_ARE_CHAR_SPECIFIC = "allRelicsCharSpecific";
    private static final String ENABLE_OLD_CARD_LIST = "enableOldCardList";
    private static boolean allRelicsCharSpecific = true;
    public static boolean enableOldCardList = false;

    public static final Color FORSAKEN_GOLD = CardHelper.getColor(227, 203, 43);

    static {
        defaultConfigSettings.setProperty(ALL_RELICS_ARE_CHAR_SPECIFIC, "TRUE");
        defaultConfigSettings.setProperty(ENABLE_OLD_CARD_LIST, "FALSE");
    }

    public static String assetPath(String path) {
        return "forsakenAssets/" + path;
    }

    public static AssetLoader assets = new AssetLoader();

    // =============== MAKE IMAGE PATHS =================

    public static String imageResourcePath(String path) {
        return modID + "Resources/images/" + path;
    }

    public static String cardResourcePath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static String relicResourcePath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String relicOutlineResourcePath(String resourcePath) {
        return modID + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String powerResourcePath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String eventResourcePath(String resourcePath) {
        return modID + "Resources/images/events/" + resourcePath;
    }

    public TheForsakenMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheForsaken.Enums.COLOR_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                imageResourcePath("512/bg_attack.png"),
                imageResourcePath("512/bg_skill.png"),
                imageResourcePath("512/bg_power.png"),
                imageResourcePath("512/card_orb.png"),
                imageResourcePath("1024/bg_attack.png"),
                imageResourcePath("1024/bg_skill.png"),
                imageResourcePath("1024/bg_power.png"),
                imageResourcePath("1024/card_orb.png"),
                imageResourcePath("512/card_small_orb.png"));

        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        try {
            SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", defaultConfigSettings);
            config.load();
            allRelicsCharSpecific = config.getBool(ALL_RELICS_ARE_CHAR_SPECIFIC);
            enableOldCardList = config.getBool(ENABLE_OLD_CARD_LIST);
        } catch (Exception e) {
            logger.error("problem loading SpireConfig", e);
        }
    }

    public static String getModID() {
        return modID;
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static String makeOldID(String idText) {
        return modID + ":oldcards:" + idText;
    }

    public static void initialize() {
        TheForsakenMod forsakenMod = new TheForsakenMod();
    }

    private void saveSettings() {
        try {
            // set the settings based on local variables and save them
            SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", defaultConfigSettings);
            config.load();
            config.setBool(ALL_RELICS_ARE_CHAR_SPECIFIC, allRelicsCharSpecific);
            config.setBool(ENABLE_OLD_CARD_LIST, enableOldCardList);
            config.save();
        } catch (Exception e) {
            logger.error("problem saving SpireConfig", e);
        }
    }

    @Override
    public void receivePostInitialize() {
        // Load the Mod Badge
        Texture badgeTexture = TextureHelper.getTexture(IMAGE_PATH + "/Badge.png");

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Settings"));
        ModLabeledToggleButton relicsCharacterOnlyButton = new ModLabeledToggleButton(uiStrings.TEXT[0],
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                allRelicsCharSpecific,
                settingsPanel,
                (label) -> {
                },
                (button) -> {
                    allRelicsCharSpecific = button.enabled;
                    saveSettings();
                });

        settingsPanel.addUIElement(relicsCharacterOnlyButton);

        ModLabeledToggleButton enableOldCardsButton = new ModLabeledToggleButton(uiStrings.TEXT[1], 350.0f, 650.0f,
                Settings.CREAM_COLOR, FontHelper.charDescFont, enableOldCardList, settingsPanel,
                (label) -> {},
                (button) -> {
                    enableOldCardList = button.enabled;
                    saveSettings();
                });

        settingsPanel.addUIElement(enableOldCardsButton);

        BaseMod.registerModBadge(badgeTexture, "The Forsaken", "NotInTheFace", "", settingsPanel);


        BaseMod.addEvent(PlagueDoctorEvent.ID, PlagueDoctorEvent.class);
        logger.info("Done loading badge Image and mod options");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheForsaken("the Forsaken", TheForsaken.Enums.THE_FORSAKEN),
                imageResourcePath("charSelect/ForsakenButton.png"),
                imageResourcePath("charSelect/ForsakenPortraitBG.png"),
                TheForsaken.Enums.THE_FORSAKEN);

        receiveEditPotions();
    }


    private void receiveEditPotions() {
        BaseMod.addPotion( FearPotion.class, FearPotion.LIQUID_COLOR, FearPotion.HYBRID_COLOR, FearPotion.SPOTS_COLOR, FearPotion.POTION_ID, TheForsaken.Enums.THE_FORSAKEN );
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new ArmorOfThorns(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new JudgementScales(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new PlagueMask(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new ScalesOfTruth(), TheForsaken.Enums.COLOR_GOLD);

        if (allRelicsCharSpecific) {
            BaseMod.addRelicToCustomPool(new Gavel(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new HumbleEgg(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new Kindling(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new Lifeblossom(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new ScaryMask(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new PaleLantern(), TheForsaken.Enums.COLOR_GOLD);
        } else {
            // This adds a relic to the Shared pool. Every character can find this relic.
            BaseMod.addRelic(new Gavel(), RelicType.SHARED);
            BaseMod.addRelic(new HumbleEgg(), RelicType.SHARED);
            BaseMod.addRelic(new Kindling(), RelicType.SHARED);
            BaseMod.addRelic(new Lifeblossom(), RelicType.SHARED);
            BaseMod.addRelic(new ScaryMask(), RelicType.SHARED);
            BaseMod.addRelic(new PaleLantern(), RelicType.SHARED);
        }

        // Mark relics as seen
        UnlockTracker.markRelicAsSeen(ArmorOfThorns.ID);
        UnlockTracker.markRelicAsSeen(JudgementScales.ID);
        UnlockTracker.markRelicAsSeen(PlagueMask.ID);
        UnlockTracker.markRelicAsSeen(ScalesOfTruth.ID);
        UnlockTracker.markRelicAsSeen(Gavel.ID);
        UnlockTracker.markRelicAsSeen(HumbleEgg.ID);
        UnlockTracker.markRelicAsSeen(Kindling.ID);
        UnlockTracker.markRelicAsSeen(Lifeblossom.ID);
        UnlockTracker.markRelicAsSeen(PaleLantern.ID);
        UnlockTracker.markRelicAsSeen(ScaryMask.ID);
        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");

        TextureAtlas myCardAtlas = assets.loadAtlas(assetPath("images/cards/cards.atlas"));
        for (TextureAtlas.AtlasRegion region : myCardAtlas.getRegions()) {
            cardAtlas.addRegion(modID + "/" + region.name, region);
        }

        BaseMod.addDynamicVariable(new UnplayedCardsVariable());

        logger.info("Adding cards");
        if (enableOldCardList) {
            BaseMod.addDynamicVariable(new SacrificeSoulVariable());
            new AutoAdd(modID)
                    .packageFilter(AbstractOldForsakenCard.class)
                    .any(CustomCard.class, (info, card) -> {
                       logger.info("AutoAdd found card: {} :: info: {}", card, info);
                    });
            new AutoAdd(modID)
                    .packageFilter(AbstractOldForsakenCard.class)
                    .setDefaultSeen(true)
                    .cards();
            return;
        }
        new AutoAdd(modID)
                .packageFilter(AbstractForsakenCard.class)
                .setDefaultSeen(true)
                .cards();

        BaseMod.addCard(new Smite());
    }

    @Override
    public void receiveEditStrings() {
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        logger.info("Loading strings for language: {}", lang);

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Card-Strings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-OldCard-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Character-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + lang + "/TheForsakenMod-Ui-Strings.json");

        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal(modID + "Resources/localization/" + lang + "/TheForsakenMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static ArrayList<UUID> usedCards = new ArrayList<>();
    public static int cardsUsedThisTurn = 0;
    public static int quickdrawTriggeredAt = 0;

    @Override
    public void receiveCardUsed(AbstractCard card) {
        UUID uuid = card.uuid;
        if (!usedCards.contains(uuid)) {
            usedCards.add(uuid);
        }
        cardsUsedThisTurn += 1;
        if (cardsUsedThisTurn >= quickdrawTriggeredAt + 3) {
            // Draw the next quickdraw card
            Optional<AbstractCard> nextQuickdrawCard = AbstractDungeon.player.drawPile.group.stream()
                    .filter(AbstractQuickdrawCard::isQuickdraw)
                    .limit(1)
                    .findFirst();
            nextQuickdrawCard.ifPresent(c -> {
                AbstractPlayer p = AbstractDungeon.player;
                p.drawPile.removeCard(c);
                p.drawPile.addToTop(c);
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
                quickdrawTriggeredAt = cardsUsedThisTurn;
            });
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        usedCards.clear();
        cardsUsedThisTurn = quickdrawTriggeredAt = 0;
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        // reset the quickdraw trigger on turn start
        cardsUsedThisTurn = quickdrawTriggeredAt = 0;
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        UUID uuid = abstractCard.uuid;
        usedCards.remove(uuid);
    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(HymnOfRestPower.POWER_ID)) {
            HymnOfRestPower p = (HymnOfRestPower) AbstractDungeon.player.getPower(HymnOfRestPower.POWER_ID);
            return p.modifiedBlock(i);
        }
        return i;
    }
}