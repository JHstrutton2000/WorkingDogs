package com.joshuastrutton.workingdogs;

public record ChihuahuaPhenotype(
        ChihuahuaColorGene color,
        ChihuahuaPatternGene pattern,
        ChihuahuaWhiteGene white,
        ChihuahuaHeadGene head,
        boolean longHaired) {
}
