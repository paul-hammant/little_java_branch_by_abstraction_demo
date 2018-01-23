package com.mycompany;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * @author Paul Hammant DevOps, (c) 2018
 */
public class Release3 implements ReleaseToggles {
    @Override
    public Object getChangingHairColor() {
        List<String> colors = asList("Blonde", "Brown", "Black", "Red");
        return colors.get(new Random().nextInt(colors.size()));
    }
}
