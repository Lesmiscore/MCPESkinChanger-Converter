package net.zhuoweizhang.mercator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GenerateMissing {
    private GenerateMissing() {
    }

    public static void generateMissingTextures(File inputDir) throws IOException {
        generateSheepTextures(inputDir, inputDir);
        generateGrassTextures(inputDir, inputDir);
    }

    private static void generateSheepTextures(File inputDir, File outputDir) throws IOException {
        Bitmap whiteSheep = getWhiteSheep(inputDir);
        if (whiteSheep != null) {
            int sheepWidth = whiteSheep.getWidth();
            int sheepHeight = whiteSheep.getHeight();
            int[] allPixels = new int[(sheepWidth * sheepHeight)];
            for (int i = 0; i < 16; i++) {
                File file = new File(inputDir, "mob/sheep_ " + i + ".png");
                if (!file.exists()) {
                    whiteSheep.getPixels(allPixels, 0, sheepWidth, 0, 0, sheepWidth, sheepHeight);
                    for (int y = sheepHeight / 2; y < sheepHeight; y++) {
                        for (int x = 0; x < sheepWidth; x++) {
                            int c = allPixels[(y * sheepWidth) + x];
                            int r = c & 255;
                            int g = (c >> 8) & 255;
                            int b = (c >> 16) & 255;
                            int a = (c >> 24) & 255;
                            if (a != 0) {
                                int br = 16711680 & 255;
                                int bg = 65280 & 255;
                                int bb = 255 & 255;
                                allPixels[(y * sheepWidth) + x] = (((((((g + 0) * 127) + g) / 255) << 8) | ((((r + 0) * 127) + r) / 255)) | (((((b + 0) * 127) + b) / 255) << 16)) | (a << 24);
                            }
                        }
                    }
                    Bitmap coloured = Bitmap.createBitmap(sheepWidth, sheepHeight, Config.ARGB_8888);
                    coloured.setPixels(allPixels, 0, sheepWidth, 0, 0, sheepWidth, sheepHeight);
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    coloured.compress(CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();
                }
            }
        }
    }

    private static Bitmap getWhiteSheep(File inputDir) {
        File alreadyThere = new File(inputDir, "mob/sheep_0.png");
        if (alreadyThere.exists()) {
            return BitmapFactory.decodeFile(alreadyThere.getAbsolutePath());
        }
        File topHalf = new File(inputDir, "mob/sheep.png");
        if (!topHalf.exists()) {
            topHalf = new File(inputDir, "entity/sheep/sheep.png");
        }
        File bottomHalf = new File(inputDir, "mob/sheep_fur.png");
        if (!bottomHalf.exists()) {
            bottomHalf = new File(inputDir, "entity/sheep/sheep_fur.png");
        }
        if (!topHalf.exists() || !bottomHalf.exists()) {
            return null;
        }
        Bitmap topHalfBitmap = BitmapFactory.decodeFile(topHalf.getAbsolutePath());
        Bitmap bottomHalfBitmap = BitmapFactory.decodeFile(bottomHalf.getAbsolutePath());
        Bitmap finalBitmap = Bitmap.createBitmap(topHalfBitmap.getWidth(), topHalfBitmap.getHeight() * 2, Config.ARGB_8888);
        int[] pixels = new int[(topHalfBitmap.getWidth() * topHalfBitmap.getHeight())];
        topHalfBitmap.getPixels(pixels, 0, topHalfBitmap.getWidth(), 0, 0, topHalfBitmap.getWidth(), topHalfBitmap.getHeight());
        finalBitmap.setPixels(pixels, 0, topHalfBitmap.getWidth(), 0, 0, topHalfBitmap.getWidth(), topHalfBitmap.getHeight());
        bottomHalfBitmap.getPixels(pixels, 0, topHalfBitmap.getWidth(), 0, 0, topHalfBitmap.getWidth(), topHalfBitmap.getHeight());
        finalBitmap.setPixels(pixels, 0, topHalfBitmap.getWidth(), 0, topHalfBitmap.getHeight(), topHalfBitmap.getWidth(), topHalfBitmap.getHeight());
        return finalBitmap;
    }

    private static void generateGrassTextures(File inputDir, File outputDir) throws IOException {
    }
}
