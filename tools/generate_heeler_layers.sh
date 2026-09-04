#!/usr/bin/env bash
set -euo pipefail

out="src/main/resources/assets/workingdogs/textures/entity/working_dog/heeler"
mkdir -p "$out"

transparent() {
    convert -size 64x32 xc:none "$1"
}

# Facial masks. The two eye regions are independent so LEFT + RIGHT can resolve
# to a double mask without needing a unique combined genome texture.
transparent "$out/mask_left.png"
convert "$out/mask_left.png" -fill '#15191fff' \
    -draw 'rectangle 4,4 6,9 rectangle 1,4 3,9' "$out/mask_left.png"

transparent "$out/mask_right.png"
convert "$out/mask_right.png" -fill '#15191fff' \
    -draw 'rectangle 7,4 9,9 rectangle 10,4 13,9' "$out/mask_right.png"

convert "$out/mask_left.png" "$out/mask_right.png" \
    -background none -compose over -composite "$out/mask_double.png"

# Solid body patches on the head, shoulder, flank, and tail UV islands.
transparent "$out/patch_small.png"
convert "$out/patch_small.png" -fill '#171b21ff' \
    -draw 'rectangle 24,4 29,8 rectangle 24,17 29,21' "$out/patch_small.png"

transparent "$out/patch_large.png"
convert "$out/patch_large.png" -fill '#14181eff' \
    -draw 'rectangle 21,3 32,11 rectangle 18,17 31,27 rectangle 32,18 40,26' \
    "$out/patch_large.png"

# White forehead markings.
transparent "$out/white_bentley.png"
convert "$out/white_bentley.png" -fill '#e8edf0ff' \
    -draw 'rectangle 6,4 7,5 point 5,4 point 8,4' "$out/white_bentley.png"

transparent "$out/white_blaze.png"
convert "$out/white_blaze.png" -fill '#e8edf0ff' \
    -draw 'rectangle 6,3 7,9 rectangle 5,4 8,6' "$out/white_blaze.png"

# Ticking is intentionally deterministic: the phenotype controls density while
# each overlay remains stable between frames and game launches.
transparent "$out/ticking_light.png"
convert "$out/ticking_light.png" -fill '#d5dce4aa' \
    -draw 'point 2,6 point 5,3 point 8,8 point 22,4 point 27,7 point 33,5 point 38,8 point 44,6 point 4,20 point 20,18 point 25,24 point 31,19 point 36,25 point 40,20' \
    "$out/ticking_light.png"

convert "$out/ticking_light.png" -fill '#414b59bb' \
    -draw 'point 1,8 point 4,5 point 9,6 point 12,8 point 23,6 point 29,3 point 34,8 point 40,5 point 3,23 point 7,27 point 19,22 point 27,17 point 34,23 point 39,27' \
    "$out/ticking_medium.png"

convert "$out/ticking_medium.png" -fill '#c4ced9bb' \
    -draw 'point 2,4 point 3,9 point 5,7 point 7,2 point 8,5 point 11,6 point 13,4 point 22,2 point 24,9 point 26,5 point 28,10 point 31,6 point 35,3 point 37,9 point 41,4 point 45,8 point 1,19 point 5,25 point 7,21 point 19,17 point 21,26 point 24,20 point 29,25 point 32,17 point 36,21 point 39,24' \
    "$out/ticking_heavy.png"

# Tan is restricted to the throat/chest and shared lower-leg/paw UV. It must
# never touch the head's eye pixels; heeler eyes remain black.
for level in light normal rich; do
    case "$level" in
        light)  color='#b9875877' ;;
        normal) color='#c98d4fbb' ;;
        rich)   color='#e19a43ee' ;;
    esac
    transparent "$out/tan_${level}.png"
    convert "$out/tan_${level}.png" -fill "$color" \
        -draw 'rectangle 21,7 27,11 rectangle 18,19 23,23 rectangle 0,24 7,27' \
        "$out/tan_${level}.png"
done

identify "$out"/*.png
