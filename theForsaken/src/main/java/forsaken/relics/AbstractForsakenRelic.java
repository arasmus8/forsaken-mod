package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import forsaken.util.ActionUnit;

import static forsaken.TheForsakenMod.*;

public abstract class AbstractForsakenRelic extends CustomRelic implements ActionUnit {
    public AbstractCard.CardColor color;

    public AbstractForsakenRelic(String id, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(id, tier, sfx, null);
    }

    public AbstractForsakenRelic(String id, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(id, "", tier, sfx);

        this.color = color;

        String imgName = getBaseImagePath();
        System.out.println(imgName);

        loadImages(imgName);
        if (img == null || outlineImg == null) {
            loadImages("default.png");
        }
    }

    protected String getBaseImagePath() {
        String id = relicId.replaceFirst(getModID() + ":", "");
        return id + ".png";
    }

    protected void loadImages(String imgName) {
        img = ImageMaster.loadImage(relicResourcePath(imgName));
        outlineImg = ImageMaster.loadImage(relicOutlineResourcePath(imgName));
    }

    protected int getDamage() {
        return 0;
    }

    protected int getBlock() {
        return 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public DamageInfo makeDamageInfo(int amount, DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, amount, type);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAction(m, makeDamageInfo(getDamage(), DamageInfo.DamageType.NORMAL), fx));
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(getDamage()), DamageInfo.DamageType.NORMAL, fx));
    }

    @Override
    public void gainBlock() {
        qAction(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, getBlock()));
    }
}
