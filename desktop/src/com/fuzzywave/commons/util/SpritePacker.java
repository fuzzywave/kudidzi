package com.fuzzywave.commons.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.File;

public class SpritePacker {

    public static void packTexture(String inputDir,
                                   String outputDir,
                                   String packFileName) {
        File f = new File(outputDir + "/" + packFileName);
        if (f.exists()) {
            f.delete();
        }

        TexturePacker.process(inputDir, outputDir, packFileName);
    }
}
