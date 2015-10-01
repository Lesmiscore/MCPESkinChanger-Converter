package net.zhuoweizhang.mercator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class UnstitchLegacy {

    public static class LegacyCoord {
        public String name;
        public int x;
        public int y;

        public LegacyCoord(int x, int y, String name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }

    private UnstitchLegacy() {
    }

    public static List<LegacyCoord> loadLegacyCoord(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<LegacyCoord> retval = new ArrayList();
        while (true) {
            String curLine = reader.readLine();
            if (curLine != null) {
                curLine = curLine.trim();
                if (curLine.length() >= 1 && curLine.charAt(0) != '#') {
                    String[] parts = curLine.split(" - ");
                    if (parts.length == 2) {
                        String[] frontCoords = parts[0].split(",");
                        retval.add(new LegacyCoord(Integer.parseInt(frontCoords[0]), Integer.parseInt(frontCoords[1]), parts[1]));
                    }
                }
            } else {
                reader.close();
                return retval;
            }
        }
    }

    public static void unstitchLegacy(File inputFile, List<LegacyCoord> map, File outputFolder) throws IOException {
        outputFolder.mkdirs();
        unstitchLegacy(BitmapFactory.decodeFile(inputFile.getAbsolutePath()), (List) map, outputFolder);
    }

    public static void unstitchLegacy(Bitmap inputBitmap, List<LegacyCoord> map, File outputFolder) throws IOException {
        for (LegacyCoord l : map) {
            unstitchOneIcon(inputBitmap, l, outputFolder);
        }
    }

    private static void unstitchOneIcon(Bitmap inputBitmap, LegacyCoord coord, File outputFolder) throws IOException {
        Bitmap out = Bitmap.createBitmap(inputBitmap, coord.x * (inputBitmap.getWidth() / 16), coord.y * (inputBitmap.getHeight() / 16), inputBitmap.getWidth() / 16, inputBitmap.getHeight() / 16);
        FileOutputStream fos = new FileOutputStream(new File(outputFolder, coord.name + ".png"));
        out.compress(CompressFormat.PNG, 100, fos);
        fos.close();
    }
}
