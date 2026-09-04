package com.joshuastrutton.workingdogs;

import com.joshuastrutton.workingdogs.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import org.jetbrains.annotations.Nullable;

public class WorkingDog extends Wolf {
    private static final int MIN_SHED_TICKS = 20 * 60 * 5;
    private static final int SHED_VARIANCE_TICKS = 20 * 60 * 5;

    private int shedCooldown;
    private static final EntityDataAccessor<Integer> BREED =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GENE_FROM_MOTHER =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GENE_FROM_FATHER =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WORK_MODE =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MASK_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MASK_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKING_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TICKING_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATCH_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATCH_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WHITE_MARK_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WHITE_MARK_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAN_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAN_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> TAIL_DOCKED =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COAT_LENGTH_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COAT_LENGTH_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_COLOR_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_COLOR_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_PATTERN_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_PATTERN_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_WHITE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_WHITE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_HEAD_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHIHUAHUA_HEAD_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COAT_TEXTURE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COAT_TEXTURE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SIZE_GENE_A =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SIZE_GENE_B =
            SynchedEntityData.defineId(WorkingDog.class, EntityDataSerializers.INT);

    public WorkingDog(EntityType<? extends Wolf> type, Level level) {
        super(type, level);
        resetShedCooldown();
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide
                || isBaby()
                || getBreed() != DogBreed.AUSTRALIAN_CATTLE_DOG) {
            return;
        }

        if (--shedCooldown <= 0) {
            spawnAtLocation(getCoatVariant() == CoatVariant.RED_HEELER
                    ? ModItems.RED_HEELER_FUR.get()
                    : ModItems.BLUE_HEELER_FUR.get());
            resetShedCooldown();
        }
    }

    private void resetShedCooldown() {
        shedCooldown = MIN_SHED_TICKS + getRandom().nextInt(SHED_VARIANCE_TICKS + 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Wolf.createAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.31);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BREED, DogBreed.AUSTRALIAN_CATTLE_DOG.ordinal());
        builder.define(GENE_FROM_MOTHER, CoatGene.DARK.ordinal());
        builder.define(GENE_FROM_FATHER, CoatGene.LIGHT.ordinal());
        builder.define(WORK_MODE, WorkMode.COMPANION.ordinal());
        builder.define(MASK_GENE_A, MaskGene.NONE.ordinal());
        builder.define(MASK_GENE_B, MaskGene.NONE.ordinal());
        builder.define(TICKING_GENE_A, TickingGene.MEDIUM.ordinal());
        builder.define(TICKING_GENE_B, TickingGene.MEDIUM.ordinal());
        builder.define(PATCH_GENE_A, PatchGene.NONE.ordinal());
        builder.define(PATCH_GENE_B, PatchGene.NONE.ordinal());
        builder.define(WHITE_MARK_GENE_A, WhiteMarkGene.BENTLEY.ordinal());
        builder.define(WHITE_MARK_GENE_B, WhiteMarkGene.NONE.ordinal());
        builder.define(TAN_GENE_A, TanGene.NORMAL.ordinal());
        builder.define(TAN_GENE_B, TanGene.NORMAL.ordinal());
        builder.define(TAIL_DOCKED, false);
        builder.define(COAT_LENGTH_GENE_A, CoatLengthGene.SHORT.ordinal());
        builder.define(COAT_LENGTH_GENE_B, CoatLengthGene.SHORT.ordinal());
        builder.define(CHIHUAHUA_COLOR_A, ChihuahuaColorGene.FAWN.ordinal());
        builder.define(CHIHUAHUA_COLOR_B, ChihuahuaColorGene.FAWN.ordinal());
        builder.define(CHIHUAHUA_PATTERN_A, ChihuahuaPatternGene.SOLID.ordinal());
        builder.define(CHIHUAHUA_PATTERN_B, ChihuahuaPatternGene.SOLID.ordinal());
        builder.define(CHIHUAHUA_WHITE_A, ChihuahuaWhiteGene.NONE.ordinal());
        builder.define(CHIHUAHUA_WHITE_B, ChihuahuaWhiteGene.NONE.ordinal());
        builder.define(CHIHUAHUA_HEAD_A, ChihuahuaHeadGene.APPLE.ordinal());
        builder.define(CHIHUAHUA_HEAD_B, ChihuahuaHeadGene.APPLE.ordinal());
        builder.define(COAT_TEXTURE_A, CoatTextureGene.STRAIGHT.ordinal());
        builder.define(COAT_TEXTURE_B, CoatTextureGene.STRAIGHT.ordinal());
        builder.define(SIZE_GENE_A, SizeGene.STANDARD.ordinal());
        builder.define(SIZE_GENE_B, SizeGene.STANDARD.ordinal());
    }

    public DogBreed getBreed() {
        return DogBreed.byId(entityData.get(BREED));
    }

    public void setBreed(DogBreed breed) {
        entityData.set(BREED, breed.ordinal());
        refreshDimensions();
        applyBreedAttributes();
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        return dimensions.scale(BreedBodyProfile.of(getBreed(), hasFullCoat()).scale() * getSizeScale());
    }

    public WorkMode getWorkMode() {
        return WorkMode.byId(entityData.get(WORK_MODE));
    }

    public void setWorkMode(WorkMode mode) {
        entityData.set(WORK_MODE, mode.ordinal());
    }

    public CoatGene getMaternalGene() {
        return CoatGene.byId(entityData.get(GENE_FROM_MOTHER));
    }

    public CoatGene getPaternalGene() {
        return CoatGene.byId(entityData.get(GENE_FROM_FATHER));
    }

    public void setGenes(CoatGene maternal, CoatGene paternal) {
        entityData.set(GENE_FROM_MOTHER, maternal.ordinal());
        entityData.set(GENE_FROM_FATHER, paternal.ordinal());
    }

    public MaskGene getMaskGeneA() { return MaskGene.byId(entityData.get(MASK_GENE_A)); }
    public MaskGene getMaskGeneB() { return MaskGene.byId(entityData.get(MASK_GENE_B)); }
    public TickingGene getTickingGeneA() { return TickingGene.byId(entityData.get(TICKING_GENE_A)); }
    public TickingGene getTickingGeneB() { return TickingGene.byId(entityData.get(TICKING_GENE_B)); }
    public PatchGene getPatchGeneA() { return PatchGene.byId(entityData.get(PATCH_GENE_A)); }
    public PatchGene getPatchGeneB() { return PatchGene.byId(entityData.get(PATCH_GENE_B)); }
    public WhiteMarkGene getWhiteMarkGeneA() { return WhiteMarkGene.byId(entityData.get(WHITE_MARK_GENE_A)); }
    public WhiteMarkGene getWhiteMarkGeneB() { return WhiteMarkGene.byId(entityData.get(WHITE_MARK_GENE_B)); }
    public TanGene getTanGeneA() { return TanGene.byId(entityData.get(TAN_GENE_A)); }
    public TanGene getTanGeneB() { return TanGene.byId(entityData.get(TAN_GENE_B)); }
    public boolean isTailDocked() { return entityData.get(TAIL_DOCKED); }
    public void setTailDocked(boolean docked) { entityData.set(TAIL_DOCKED, docked); }
    public CoatLengthGene getCoatLengthGeneA() { return CoatLengthGene.byId(entityData.get(COAT_LENGTH_GENE_A)); }
    public CoatLengthGene getCoatLengthGeneB() { return CoatLengthGene.byId(entityData.get(COAT_LENGTH_GENE_B)); }
    public void setCoatLengthGenes(CoatLengthGene first, CoatLengthGene second) {
        entityData.set(COAT_LENGTH_GENE_A, first.ordinal());
        entityData.set(COAT_LENGTH_GENE_B, second.ordinal());
    }

    /** Long hair is recessive: a Chihuahua must inherit LONG from both parents. */
    public boolean isLongHaired() {
        boolean inheritedLong = getCoatLengthGeneA() == CoatLengthGene.LONG
                && getCoatLengthGeneB() == CoatLengthGene.LONG;
        return switch (getBreed()) {
            case CHIHUAHUA, DACHSHUND, GERMAN_SHEPHERD -> inheritedLong;
            case GOLDEN_RETRIEVER, CAVALIER_KING_CHARLES_SPANIEL,
                    YORKSHIRE_TERRIER, AUSTRALIAN_SHEPHERD, POMERANIAN,
                    BERNESE_MOUNTAIN_DOG, BORDER_COLLIE -> true;
            default -> false;
        };
    }

    public ChihuahuaColorGene getChihuahuaColorA() { return ChihuahuaColorGene.byId(entityData.get(CHIHUAHUA_COLOR_A)); }
    public ChihuahuaColorGene getChihuahuaColorB() { return ChihuahuaColorGene.byId(entityData.get(CHIHUAHUA_COLOR_B)); }
    public ChihuahuaPatternGene getChihuahuaPatternA() { return ChihuahuaPatternGene.byId(entityData.get(CHIHUAHUA_PATTERN_A)); }
    public ChihuahuaPatternGene getChihuahuaPatternB() { return ChihuahuaPatternGene.byId(entityData.get(CHIHUAHUA_PATTERN_B)); }
    public ChihuahuaWhiteGene getChihuahuaWhiteA() { return ChihuahuaWhiteGene.byId(entityData.get(CHIHUAHUA_WHITE_A)); }
    public ChihuahuaWhiteGene getChihuahuaWhiteB() { return ChihuahuaWhiteGene.byId(entityData.get(CHIHUAHUA_WHITE_B)); }
    public ChihuahuaHeadGene getChihuahuaHeadA() { return ChihuahuaHeadGene.byId(entityData.get(CHIHUAHUA_HEAD_A)); }
    public ChihuahuaHeadGene getChihuahuaHeadB() { return ChihuahuaHeadGene.byId(entityData.get(CHIHUAHUA_HEAD_B)); }

    public void setChihuahuaGenes(ChihuahuaColorGene colorA, ChihuahuaColorGene colorB,
                                  ChihuahuaPatternGene patternA, ChihuahuaPatternGene patternB,
                                  ChihuahuaWhiteGene whiteA, ChihuahuaWhiteGene whiteB,
                                  ChihuahuaHeadGene headA, ChihuahuaHeadGene headB) {
        entityData.set(CHIHUAHUA_COLOR_A, colorA.ordinal());
        entityData.set(CHIHUAHUA_COLOR_B, colorB.ordinal());
        entityData.set(CHIHUAHUA_PATTERN_A, patternA.ordinal());
        entityData.set(CHIHUAHUA_PATTERN_B, patternB.ordinal());
        entityData.set(CHIHUAHUA_WHITE_A, whiteA.ordinal());
        entityData.set(CHIHUAHUA_WHITE_B, whiteB.ordinal());
        entityData.set(CHIHUAHUA_HEAD_A, headA.ordinal());
        entityData.set(CHIHUAHUA_HEAD_B, headB.ordinal());
    }

    public ChihuahuaPhenotype getChihuahuaPhenotype() {
        ChihuahuaColorGene color = max(getChihuahuaColorA(), getChihuahuaColorB());
        ChihuahuaPatternGene pattern = max(getChihuahuaPatternA(), getChihuahuaPatternB());
        ChihuahuaWhiteGene white = max(getChihuahuaWhiteA(), getChihuahuaWhiteB());

        // Shared loci are expressed through breed-appropriate ranges.
        switch (getBreed()) {
            case GERMAN_SHEPHERD -> {
                if (color == ChihuahuaColorGene.WHITE || color == ChihuahuaColorGene.CREAM) {
                    color = ChihuahuaColorGene.BLACK;
                }
                if (pattern != ChihuahuaPatternGene.TAN_POINTS
                        && pattern != ChihuahuaPatternGene.SABLE) {
                    pattern = ChihuahuaPatternGene.TAN_POINTS;
                }
                white = ChihuahuaWhiteGene.NONE;
            }
            case GOLDEN_RETRIEVER -> {
                if (color != ChihuahuaColorGene.CREAM && color != ChihuahuaColorGene.FAWN
                        && color != ChihuahuaColorGene.RED) color = ChihuahuaColorGene.FAWN;
                pattern = ChihuahuaPatternGene.SOLID;
                white = ChihuahuaWhiteGene.NONE;
            }
            case LABRADOR_RETRIEVER -> {
                if (color != ChihuahuaColorGene.BLACK && color != ChihuahuaColorGene.CHOCOLATE
                        && color != ChihuahuaColorGene.CREAM) color = ChihuahuaColorGene.BLACK;
                pattern = ChihuahuaPatternGene.SOLID;
                white = ChihuahuaWhiteGene.NONE;
            }
            case BORDER_COLLIE -> {
                if (color == ChihuahuaColorGene.WHITE || color == ChihuahuaColorGene.CREAM
                        || color == ChihuahuaColorGene.FAWN || color == ChihuahuaColorGene.RED) {
                    color = ChihuahuaColorGene.BLACK;
                }
                if (pattern == ChihuahuaPatternGene.BRINDLE) pattern = ChihuahuaPatternGene.SOLID;
                if (white == ChihuahuaWhiteGene.NONE) white = ChihuahuaWhiteGene.IRISH;
            }
            case ROTTWEILER, DOBERMAN_PINSCHER -> {
                if (color == ChihuahuaColorGene.WHITE || color == ChihuahuaColorGene.CREAM
                        || color == ChihuahuaColorGene.FAWN || color == ChihuahuaColorGene.RED) {
                    color = ChihuahuaColorGene.BLACK;
                }
                pattern = ChihuahuaPatternGene.TAN_POINTS;
                white = ChihuahuaWhiteGene.NONE;
            }
            case BERNESE_MOUNTAIN_DOG -> {
                color = ChihuahuaColorGene.BLACK;
                pattern = ChihuahuaPatternGene.TAN_POINTS;
                white = ChihuahuaWhiteGene.IRISH;
            }
            case YORKSHIRE_TERRIER -> {
                color = ChihuahuaColorGene.BLUE;
                pattern = ChihuahuaPatternGene.TAN_POINTS;
                white = ChihuahuaWhiteGene.NONE;
            }
            case BEAGLE, CAVALIER_KING_CHARLES_SPANIEL, PEMBROKE_WELSH_CORGI -> {
                if (pattern == ChihuahuaPatternGene.MERLE || pattern == ChihuahuaPatternGene.BRINDLE) {
                    pattern = ChihuahuaPatternGene.SABLE;
                }
                if (white == ChihuahuaWhiteGene.NONE) white = ChihuahuaWhiteGene.CHEST;
            }
            case POODLE -> pattern = ChihuahuaPatternGene.SOLID;
            case POMERANIAN -> {
                if (pattern == ChihuahuaPatternGene.MERLE) pattern = ChihuahuaPatternGene.SABLE;
            }
            case FRENCH_BULLDOG, BULLDOG, BOXER, CANE_CORSO -> {
                if (pattern == ChihuahuaPatternGene.MERLE) pattern = ChihuahuaPatternGene.BRINDLE;
            }
            default -> { }
        }

        return new ChihuahuaPhenotype(color, pattern, white,
                max(getChihuahuaHeadA(), getChihuahuaHeadB()), isLongHaired());
    }

    public CoatTextureGene getCoatTextureA() { return CoatTextureGene.byId(entityData.get(COAT_TEXTURE_A)); }
    public CoatTextureGene getCoatTextureB() { return CoatTextureGene.byId(entityData.get(COAT_TEXTURE_B)); }
    public SizeGene getSizeGeneA() { return SizeGene.byId(entityData.get(SIZE_GENE_A)); }
    public SizeGene getSizeGeneB() { return SizeGene.byId(entityData.get(SIZE_GENE_B)); }
    public CoatTextureGene getCoatTexture() {
        CoatTextureGene inherited = max(getCoatTextureA(), getCoatTextureB());
        return switch (getBreed()) {
            case POODLE -> CoatTextureGene.CURLY;
            case MINIATURE_SCHNAUZER, YORKSHIRE_TERRIER -> CoatTextureGene.WIRE;
            case GOLDEN_RETRIEVER, CAVALIER_KING_CHARLES_SPANIEL,
                    AUSTRALIAN_SHEPHERD, POMERANIAN, BERNESE_MOUNTAIN_DOG -> CoatTextureGene.WAVY;
            case LABRADOR_RETRIEVER, FRENCH_BULLDOG, BEAGLE, ROTTWEILER,
                    GERMAN_SHORTHAIRED_POINTER, BULLDOG, CANE_CORSO,
                    DOBERMAN_PINSCHER, PEMBROKE_WELSH_CORGI, BOXER,
                    AUSTRALIAN_CATTLE_DOG -> CoatTextureGene.STRAIGHT;
            default -> inherited;
        };
    }
    public float getSizeScale() { return max(getSizeGeneA(), getSizeGeneB()).scale(); }
    public boolean hasFullCoat() {
        if (isLongHaired()) return true;
        return switch (getBreed()) {
            case POODLE, MINIATURE_SCHNAUZER, POMERANIAN, YORKSHIRE_TERRIER -> true;
            default -> getCoatTexture() != CoatTextureGene.STRAIGHT;
        };
    }
    public void setStructureGenes(CoatTextureGene textureA, CoatTextureGene textureB,
                                  SizeGene sizeA, SizeGene sizeB) {
        entityData.set(COAT_TEXTURE_A, textureA.ordinal());
        entityData.set(COAT_TEXTURE_B, textureB.ordinal());
        entityData.set(SIZE_GENE_A, sizeA.ordinal());
        entityData.set(SIZE_GENE_B, sizeB.ordinal());
        refreshDimensions();
        applyBreedAttributes();
    }

    private void applyBreedAttributes() {
        float scale = BreedBodyProfile.of(getBreed(), hasFullCoat()).scale() * getSizeScale();
        double health;
        double damage;
        double speed;
        if (scale < 0.62F) {
            health = 10.0; damage = 2.0; speed = 0.34;
        } else if (scale < 0.78F) {
            health = 14.0; damage = 3.0; speed = 0.33;
        } else if (scale < 1.0F) {
            health = 22.0; damage = 5.0; speed = 0.31;
        } else {
            health = getBreed() == DogBreed.CANE_CORSO ? 30.0 : 28.0;
            damage = getBreed() == DogBreed.CANE_CORSO ? 7.0 : 6.0;
            speed = 0.29;
        }
        if (getAttribute(Attributes.MAX_HEALTH) != null) getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
        if (getAttribute(Attributes.ATTACK_DAMAGE) != null) getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        if (getAttribute(Attributes.MOVEMENT_SPEED) != null) getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        if (getHealth() > health) setHealth((float) health);
    }

    public boolean usesGeneralCoatGenetics() {
        return getBreed() != DogBreed.AUSTRALIAN_CATTLE_DOG;
    }

    public void setMarkingGenes(MaskGene maskA, MaskGene maskB,
                                TickingGene tickingA, TickingGene tickingB,
                                PatchGene patchA, PatchGene patchB,
                                WhiteMarkGene whiteA, WhiteMarkGene whiteB,
                                TanGene tanA, TanGene tanB) {
        entityData.set(MASK_GENE_A, maskA.ordinal());
        entityData.set(MASK_GENE_B, maskB.ordinal());
        entityData.set(TICKING_GENE_A, tickingA.ordinal());
        entityData.set(TICKING_GENE_B, tickingB.ordinal());
        entityData.set(PATCH_GENE_A, patchA.ordinal());
        entityData.set(PATCH_GENE_B, patchB.ordinal());
        entityData.set(WHITE_MARK_GENE_A, whiteA.ordinal());
        entityData.set(WHITE_MARK_GENE_B, whiteB.ordinal());
        entityData.set(TAN_GENE_A, tanA.ordinal());
        entityData.set(TAN_GENE_B, tanB.ordinal());
    }

    public HeelerPhenotype getHeelerPhenotype() {
        return new HeelerPhenotype(
                getCoatVariant(),
                resolveMask(getMaskGeneA(), getMaskGeneB()),
                max(getTickingGeneA(), getTickingGeneB()),
                max(getPatchGeneA(), getPatchGeneB()),
                max(getWhiteMarkGeneA(), getWhiteMarkGeneB()),
                max(getTanGeneA(), getTanGeneB())
        );
    }

    private static MaskPattern resolveMask(MaskGene first, MaskGene second) {
        boolean left = first == MaskGene.LEFT || first == MaskGene.BOTH
                || second == MaskGene.LEFT || second == MaskGene.BOTH;
        boolean right = first == MaskGene.RIGHT || first == MaskGene.BOTH
                || second == MaskGene.RIGHT || second == MaskGene.BOTH;
        if (left && right) return MaskPattern.DOUBLE;
        if (left) return MaskPattern.LEFT;
        if (right) return MaskPattern.RIGHT;
        return MaskPattern.NONE;
    }

    private static <T extends Enum<T>> T max(T first, T second) {
        return first.ordinal() >= second.ordinal() ? first : second;
    }

    public CoatVariant getCoatVariant() {
        CoatGene first = getMaternalGene();
        CoatGene second = getPaternalGene();
        return switch (getBreed()) {
            case AUSTRALIAN_CATTLE_DOG -> first == CoatGene.RED && second == CoatGene.RED
                    ? CoatVariant.RED_HEELER : CoatVariant.BLUE_HEELER;
            case GERMAN_SHEPHERD -> CoatVariant.BLACK_AND_TAN;
            case GOLDEN_RETRIEVER -> CoatVariant.GOLDEN;
            case LABRADOR_RETRIEVER -> {
                if (first == CoatGene.RED && second == CoatGene.RED) yield CoatVariant.CHOCOLATE_LAB;
                if (first == CoatGene.LIGHT && second == CoatGene.LIGHT) yield CoatVariant.YELLOW_LAB;
                yield CoatVariant.BLACK_LAB;
            }
            case BORDER_COLLIE -> CoatVariant.BLACK_AND_WHITE;
            case CHIHUAHUA -> isLongHaired()
                    ? CoatVariant.CHIHUAHUA_LONG_HAIR
                    : CoatVariant.CHIHUAHUA_SHORT_HAIR;
            case FRENCH_BULLDOG, BULLDOG, BEAGLE, GERMAN_SHORTHAIRED_POINTER,
                    AUSTRALIAN_SHEPHERD, BERNESE_MOUNTAIN_DOG -> CoatVariant.BLACK_AND_WHITE;
            case DACHSHUND, CAVALIER_KING_CHARLES_SPANIEL, BOXER -> CoatVariant.CHOCOLATE_LAB;
            case POODLE, POMERANIAN -> CoatVariant.GOLDEN;
            case ROTTWEILER, DOBERMAN_PINSCHER -> CoatVariant.BLACK_AND_TAN;
            case CANE_CORSO -> CoatVariant.BLACK_LAB;
            case YORKSHIRE_TERRIER, MINIATURE_SCHNAUZER -> CoatVariant.BLUE_HEELER;
            case PEMBROKE_WELSH_CORGI -> CoatVariant.RED_HEELER;
        };
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);
        RandomSource random = getRandom();
        setBreed(DogBreed.values()[random.nextInt(DogBreed.values().length)]);
        setGenes(randomGene(random), randomGene(random));
        setCoatLengthGenes(randomCoatLengthGene(random), randomCoatLengthGene(random));
        setChihuahuaGenes(
                randomEnum(ChihuahuaColorGene.values(), random), randomEnum(ChihuahuaColorGene.values(), random),
                randomChihuahuaPattern(random), randomChihuahuaPattern(random),
                randomChihuahuaWhite(random), randomChihuahuaWhite(random),
                randomEnum(ChihuahuaHeadGene.values(), random), randomEnum(ChihuahuaHeadGene.values(), random));
        setStructureGenes(
                randomEnum(CoatTextureGene.values(), random), randomEnum(CoatTextureGene.values(), random),
                randomEnum(SizeGene.values(), random), randomEnum(SizeGene.values(), random));
        setMarkingGenes(
                randomMaskGene(random), randomMaskGene(random),
                randomEnum(TickingGene.values(), random), randomEnum(TickingGene.values(), random),
                randomPatchGene(random), randomPatchGene(random),
                randomWhiteMarkGene(random), randomWhiteMarkGene(random),
                randomEnum(TanGene.values(), random), randomEnum(TanGene.values(), random)
        );
        return result;
    }

    private static CoatGene randomGene(RandomSource random) {
        // Red is recessive and less common in the initial wild population.
        int roll = random.nextInt(10);
        if (roll < 2) return CoatGene.RED;
        return roll < 6 ? CoatGene.DARK : CoatGene.LIGHT;
    }

    private static CoatLengthGene randomCoatLengthGene(RandomSource random) {
        // A 50/50 allele pool makes roughly one quarter of random Chihuahuas long-haired.
        return random.nextBoolean() ? CoatLengthGene.SHORT : CoatLengthGene.LONG;
    }

    private static ChihuahuaPatternGene randomChihuahuaPattern(RandomSource random) {
        int roll = random.nextInt(20);
        if (roll < 9) return ChihuahuaPatternGene.SOLID;
        if (roll < 14) return ChihuahuaPatternGene.TAN_POINTS;
        if (roll < 17) return ChihuahuaPatternGene.SABLE;
        if (roll < 19) return ChihuahuaPatternGene.BRINDLE;
        return ChihuahuaPatternGene.MERLE;
    }

    private static ChihuahuaWhiteGene randomChihuahuaWhite(RandomSource random) {
        int roll = random.nextInt(10);
        if (roll < 5) return ChihuahuaWhiteGene.NONE;
        if (roll < 8) return ChihuahuaWhiteGene.CHEST;
        if (roll < 9) return ChihuahuaWhiteGene.IRISH;
        return ChihuahuaWhiteGene.PIEBALD;
    }

    private static MaskGene randomMaskGene(RandomSource random) {
        int roll = random.nextInt(10);
        if (roll < 5) return MaskGene.NONE;
        if (roll < 7) return MaskGene.LEFT;
        if (roll < 9) return MaskGene.RIGHT;
        return MaskGene.BOTH;
    }

    private static PatchGene randomPatchGene(RandomSource random) {
        int roll = random.nextInt(10);
        return roll < 6 ? PatchGene.NONE : roll < 9 ? PatchGene.SMALL : PatchGene.LARGE;
    }

    private static WhiteMarkGene randomWhiteMarkGene(RandomSource random) {
        int roll = random.nextInt(10);
        return roll < 4 ? WhiteMarkGene.NONE : roll < 9 ? WhiteMarkGene.BENTLEY : WhiteMarkGene.BLAZE;
    }

    private static <T> T randomEnum(T[] values, RandomSource random) {
        return values[random.nextInt(values.length)];
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (getBreed() == DogBreed.AUSTRALIAN_CATTLE_DOG
                && isTame()
                && isOwnedBy(player)
                && player.getItemInHand(hand).is(Items.SHEARS)
                && !isTailDocked()) {
            if (!level().isClientSide) {
                setTailDocked(true);
                level().playSound(null, blockPosition(),
                        net.minecraft.sounds.SoundEvents.SHEEP_SHEAR,
                        net.minecraft.sounds.SoundSource.PLAYERS,
                        1.0F, 1.0F);
                player.displayClientMessage(
                        Component.translatable("message.workingdogs.tail_docked", getName()),
                        true);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (isTame() && isOwnedBy(player) && player.getItemInHand(hand).is(Items.STICK)) {
            if (!level().isClientSide) {
                WorkMode next = getWorkMode().next();
                setWorkMode(next);
                player.displayClientMessage(
                        Component.translatable("message.workingdogs.work_mode", getName(),
                                Component.translatable("work_mode.workingdogs." + next.name().toLowerCase())),
                        true
                );
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (getWorkMode() == WorkMode.GUARD) {
            return super.wantsToAttack(target, owner);
        }
        return getWorkMode() == WorkMode.COMPANION && super.wantsToAttack(target, owner);
    }

    @Nullable
    @Override
    public Wolf getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        WorkingDog puppy = (WorkingDog) getType().create(level);
        if (puppy == null) return null;

        WorkingDog other = otherParent instanceof WorkingDog dog ? dog : this;
        RandomSource random = getRandom();
        DogBreed puppyBreed = random.nextBoolean() ? getBreed() : other.getBreed();
        puppy.setBreed(puppyBreed);
        puppy.setGenes(randomGene(this, random), randomGene(other, random));
        puppy.setCoatLengthGenes(
                randomCoatLengthGene(this, random),
                randomCoatLengthGene(other, random));
        puppy.setChihuahuaGenes(
                inheritColor(this, random), inheritColor(other, random),
                inheritPattern(this, random), inheritPattern(other, random),
                inheritWhite(this, random), inheritWhite(other, random),
                inheritHead(this, random), inheritHead(other, random));
        puppy.setStructureGenes(
                inheritTexture(this, random), inheritTexture(other, random),
                inheritSize(this, random), inheritSize(other, random));
        puppy.setMarkingGenes(
                randomMaskGene(this, random), randomMaskGene(other, random),
                randomTickingGene(this, random), randomTickingGene(other, random),
                randomPatchGene(this, random), randomPatchGene(other, random),
                randomWhiteMarkGene(this, random), randomWhiteMarkGene(other, random),
                randomTanGene(this, random), randomTanGene(other, random)
        );

        if (isTame()) {
            puppy.setOwnerUUID(getOwnerUUID());
            puppy.setTame(true, true);
        }
        return puppy;
    }

    private static CoatGene randomGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getMaternalGene() : parent.getPaternalGene();
    }

    private static CoatLengthGene randomCoatLengthGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean()
                ? parent.getCoatLengthGeneA()
                : parent.getCoatLengthGeneB();
    }
    private static ChihuahuaColorGene inheritColor(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getChihuahuaColorA() : parent.getChihuahuaColorB();
    }
    private static ChihuahuaPatternGene inheritPattern(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getChihuahuaPatternA() : parent.getChihuahuaPatternB();
    }
    private static ChihuahuaWhiteGene inheritWhite(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getChihuahuaWhiteA() : parent.getChihuahuaWhiteB();
    }
    private static ChihuahuaHeadGene inheritHead(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getChihuahuaHeadA() : parent.getChihuahuaHeadB();
    }
    private static CoatTextureGene inheritTexture(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getCoatTextureA() : parent.getCoatTextureB();
    }
    private static SizeGene inheritSize(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getSizeGeneA() : parent.getSizeGeneB();
    }

    private static MaskGene randomMaskGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getMaskGeneA() : parent.getMaskGeneB();
    }

    private static TickingGene randomTickingGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getTickingGeneA() : parent.getTickingGeneB();
    }

    private static PatchGene randomPatchGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getPatchGeneA() : parent.getPatchGeneB();
    }

    private static WhiteMarkGene randomWhiteMarkGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getWhiteMarkGeneA() : parent.getWhiteMarkGeneB();
    }

    private static TanGene randomTanGene(WorkingDog parent, RandomSource random) {
        return random.nextBoolean() ? parent.getTanGeneA() : parent.getTanGeneB();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("DogBreed", getBreed().name());
        tag.putString("MaternalCoatGene", getMaternalGene().name());
        tag.putString("PaternalCoatGene", getPaternalGene().name());
        tag.putString("WorkMode", getWorkMode().name());
        tag.putString("MaskGeneA", getMaskGeneA().name());
        tag.putString("MaskGeneB", getMaskGeneB().name());
        tag.putString("TickingGeneA", getTickingGeneA().name());
        tag.putString("TickingGeneB", getTickingGeneB().name());
        tag.putString("PatchGeneA", getPatchGeneA().name());
        tag.putString("PatchGeneB", getPatchGeneB().name());
        tag.putString("WhiteMarkGeneA", getWhiteMarkGeneA().name());
        tag.putString("WhiteMarkGeneB", getWhiteMarkGeneB().name());
        tag.putString("TanGeneA", getTanGeneA().name());
        tag.putString("TanGeneB", getTanGeneB().name());
        tag.putBoolean("TailDocked", isTailDocked());
        tag.putString("CoatLengthGeneA", getCoatLengthGeneA().name());
        tag.putString("CoatLengthGeneB", getCoatLengthGeneB().name());
        tag.putString("CoatColorGeneA", getChihuahuaColorA().name());
        tag.putString("CoatColorGeneB", getChihuahuaColorB().name());
        tag.putString("CoatPatternGeneA", getChihuahuaPatternA().name());
        tag.putString("CoatPatternGeneB", getChihuahuaPatternB().name());
        tag.putString("WhiteSpottingGeneA", getChihuahuaWhiteA().name());
        tag.putString("WhiteSpottingGeneB", getChihuahuaWhiteB().name());
        tag.putString("ChihuahuaHeadGeneA", getChihuahuaHeadA().name());
        tag.putString("ChihuahuaHeadGeneB", getChihuahuaHeadB().name());
        tag.putString("CoatTextureGeneA", getCoatTextureA().name());
        tag.putString("CoatTextureGeneB", getCoatTextureB().name());
        tag.putString("SizeGeneA", getSizeGeneA().name());
        tag.putString("SizeGeneB", getSizeGeneB().name());
        tag.putInt("ShedCooldown", shedCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setBreed(readEnum(tag, "DogBreed", DogBreed.class, getBreed()));
        setGenes(
                readEnum(tag, "MaternalCoatGene", CoatGene.class, getMaternalGene()),
                readEnum(tag, "PaternalCoatGene", CoatGene.class, getPaternalGene())
        );
        setWorkMode(readEnum(tag, "WorkMode", WorkMode.class, getWorkMode()));
        setMarkingGenes(
                readEnum(tag, "MaskGeneA", MaskGene.class, getMaskGeneA()),
                readEnum(tag, "MaskGeneB", MaskGene.class, getMaskGeneB()),
                readEnum(tag, "TickingGeneA", TickingGene.class, getTickingGeneA()),
                readEnum(tag, "TickingGeneB", TickingGene.class, getTickingGeneB()),
                readEnum(tag, "PatchGeneA", PatchGene.class, getPatchGeneA()),
                readEnum(tag, "PatchGeneB", PatchGene.class, getPatchGeneB()),
                readEnum(tag, "WhiteMarkGeneA", WhiteMarkGene.class, getWhiteMarkGeneA()),
                readEnum(tag, "WhiteMarkGeneB", WhiteMarkGene.class, getWhiteMarkGeneB()),
                readEnum(tag, "TanGeneA", TanGene.class, getTanGeneA()),
                readEnum(tag, "TanGeneB", TanGene.class, getTanGeneB())
        );
        if (tag.contains("TailDocked")) {
            setTailDocked(tag.getBoolean("TailDocked"));
        }
        setCoatLengthGenes(
                readEnum(tag, "CoatLengthGeneA", CoatLengthGene.class, getCoatLengthGeneA()),
                readEnum(tag, "CoatLengthGeneB", CoatLengthGene.class, getCoatLengthGeneB()));
        setChihuahuaGenes(
                readEnumAlias(tag, "CoatColorGeneA", "ChihuahuaColorGeneA", ChihuahuaColorGene.class, getChihuahuaColorA()),
                readEnumAlias(tag, "CoatColorGeneB", "ChihuahuaColorGeneB", ChihuahuaColorGene.class, getChihuahuaColorB()),
                readEnumAlias(tag, "CoatPatternGeneA", "ChihuahuaPatternGeneA", ChihuahuaPatternGene.class, getChihuahuaPatternA()),
                readEnumAlias(tag, "CoatPatternGeneB", "ChihuahuaPatternGeneB", ChihuahuaPatternGene.class, getChihuahuaPatternB()),
                readEnumAlias(tag, "WhiteSpottingGeneA", "ChihuahuaWhiteGeneA", ChihuahuaWhiteGene.class, getChihuahuaWhiteA()),
                readEnumAlias(tag, "WhiteSpottingGeneB", "ChihuahuaWhiteGeneB", ChihuahuaWhiteGene.class, getChihuahuaWhiteB()),
                readEnum(tag, "ChihuahuaHeadGeneA", ChihuahuaHeadGene.class, getChihuahuaHeadA()),
                readEnum(tag, "ChihuahuaHeadGeneB", ChihuahuaHeadGene.class, getChihuahuaHeadB()));
        setStructureGenes(
                readEnum(tag, "CoatTextureGeneA", CoatTextureGene.class, getCoatTextureA()),
                readEnum(tag, "CoatTextureGeneB", CoatTextureGene.class, getCoatTextureB()),
                readEnum(tag, "SizeGeneA", SizeGene.class, getSizeGeneA()),
                readEnum(tag, "SizeGeneB", SizeGene.class, getSizeGeneB()));
        if (tag.contains("ShedCooldown")) {
            shedCooldown = Math.max(1, tag.getInt("ShedCooldown"));
        }
    }

    private static <T extends Enum<T>> T readEnum(CompoundTag tag, String key, Class<T> type, T fallback) {
        try {
            return Enum.valueOf(type, tag.getString(key));
        } catch (IllegalArgumentException exception) {
            return fallback;
        }
    }

    private static <T extends Enum<T>> T readEnumAlias(
            CompoundTag tag, String key, String legacyKey, Class<T> type, T fallback) {
        return readEnum(tag, tag.contains(key) ? key : legacyKey, type, fallback);
    }
}
