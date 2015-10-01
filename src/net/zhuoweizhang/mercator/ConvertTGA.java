package net.zhuoweizhang.mercator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ConvertTGA {
    private ConvertTGA() {
    }

    public static void tgaToPng(File input, File output) throws IOException {
        Bitmap inBmp = UnstitchTGA.readTGA(input);
        FileOutputStream fos = new FileOutputStream(output);
        inBmp.compress(CompressFormat.PNG, 100, fos);
        fos.close();
    }

    public static void pngToTga(File input, File output) throws IOException {
        RestitchTGA.writeTGA(BitmapFactory.decodeFile(input.getAbsolutePath()), output);
    }
}
