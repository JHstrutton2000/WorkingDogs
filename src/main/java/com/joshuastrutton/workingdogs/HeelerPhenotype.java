package com.joshuastrutton.workingdogs;

/** The visible result produced by a dog's inherited allele pairs. */
public record HeelerPhenotype(
        CoatVariant color,
        MaskPattern mask,
        TickingGene ticking,
        PatchGene patches,
        WhiteMarkGene whiteMark,
        TanGene tan
) {}
