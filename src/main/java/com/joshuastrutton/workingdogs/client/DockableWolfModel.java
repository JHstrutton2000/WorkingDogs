package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.BreedBodyProfile;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Wolf;

/** Vanilla wolf geometry with controlled access to the tail root. */
public final class DockableWolfModel extends WolfModel<Wolf> {
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart realHead;
    private final ModelPart body;
    private final ModelPart upperBody;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public DockableWolfModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.realHead = head.getChild("real_head");
        this.body = root.getChild("body");
        this.upperBody = root.getChild("upper_body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
    }

    public void setTailDocked(boolean docked) {
        tail.visible = !docked;
    }

    public void setBreedShape(BreedBodyProfile profile, boolean appleHead) {
        resetScale(head, realHead, body, upperBody, tail,
                rightHindLeg, leftHindLeg, rightFrontLeg, leftFrontLeg);
        realHead.xScale = profile.headWidth();
        realHead.zScale = profile.headDepth();
        realHead.yScale = appleHead ? 1.12F : 1.0F;
        // Vanilla bakes both ears into real_head rather than exposing named
        // ModelPart children. Apply upright-ear emphasis to the whole head
        // assembly without attempting unsafe child lookups.
        if (profile.earSize() > 1.0F) {
            head.yScale = 1.0F + (profile.earSize() - 1.0F) * 0.35F;
            head.xScale = 1.0F + (profile.earSize() - 1.0F) * 0.18F;
        }
        body.xScale = profile.bodyWidth();
        body.zScale = profile.bodyDepth();
        upperBody.xScale = profile.bodyWidth();
        upperBody.zScale = profile.bodyDepth();
        rightHindLeg.yScale = leftHindLeg.yScale = profile.legLength();
        rightFrontLeg.yScale = leftFrontLeg.yScale = profile.legLength();
        tail.xScale = tail.zScale = profile.tailThickness();
        if (profile.fullCoat()) {
            upperBody.xScale *= 1.16F;
            upperBody.yScale = 1.14F;
            body.xScale *= 1.10F;
            tail.yScale = 1.20F;
        }
    }

    private static void resetScale(ModelPart... parts) {
        for (ModelPart part : parts) {
            part.xScale = 1.0F;
            part.yScale = 1.0F;
            part.zScale = 1.0F;
        }
    }
}
