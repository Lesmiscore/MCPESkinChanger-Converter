package net.zhuoweizhang.mercator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tga.TGAImage;

public final class RestitchTGA {
    private static int[] cachedColorsArray;

    private RestitchTGA() {
    }

    public static List<String> restitchTGA(File inputDir, JSONArray map, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        List<String> missingFiles = new ArrayList();
        Bitmap outBmp = restitch(inputDir, map, nameMap, missingFiles);
        outputDir.mkdirs();
        writeTGA(outBmp, new File(outputDir, "terrain-atlas.tga"));
        for (int mipLevel = 0; mipLevel < 4; mipLevel++) {
            int mipDivisor = 2 << mipLevel;
            writeTGA(Bitmap.createScaledBitmap(outBmp, outBmp.getWidth() / mipDivisor, outBmp.getHeight() / mipDivisor, false), new File(outputDir, "terrain-atlas_mip" + mipLevel + ".tga"));
        }
        return missingFiles;
    }

    public static void writeTGA(Bitmap outBmp, File outputFile) throws IOException {
        ByteBuffer data = ByteBuffer.allocate((outBmp.getWidth() * outBmp.getHeight()) * 4);
        outBmp.copyPixelsToBuffer(data);
        invertBuffer(data, outBmp.getWidth(), outBmp.getHeight());
        TGAImage.swapBGR(data.array(), outBmp.getWidth() * 4, outBmp.getHeight(), 4);
        TGAImage.createFromData(outBmp.getWidth(), outBmp.getHeight(), true, false, data).write(outputFile);
    }

    public static List<String> restitchPNG(File inputDir, JSONArray map, File outputFile, Map<String, String> nameMap) throws IOException, JSONException {
        List<String> missingFiles = new ArrayList();
        Bitmap outBmp = restitch(inputDir, map, nameMap, missingFiles);
        FileOutputStream fos = new FileOutputStream(outputFile);
        outBmp.compress(CompressFormat.PNG, 100, fos);
        fos.close();
        return missingFiles;
    }

    private static void invertBuffer(ByteBuffer buf, int width, int height) {
        byte[] rowBuffer = new byte[((width * 4) * 2)];
        int stride = width * 4;
        for (int y = 0; y < height / 2; y++) {
            buf.position(y * stride);
            buf.get(rowBuffer, 0, stride);
            buf.position(((height - y) - 1) * stride);
            buf.get(rowBuffer, stride, stride);
            buf.position(((height - y) - 1) * stride);
            buf.put(rowBuffer, 0, stride);
            buf.position(y * stride);
            buf.put(rowBuffer, stride, stride);
        }
        buf.rewind();
    }

    public static Bitmap restitch(File inputDir, JSONArray map, Map<String, String> nameMap, List<String> missingFiles) throws IOException, JSONException {
        Bitmap outBmp = null;
        int arrayLength = map.length();
        for (int i = 0; i < arrayLength; i++) {
            JSONObject iconInfo = map.getJSONObject(i);
            if (outBmp == null) {
                outBmp = makeBmp(iconInfo);
            }
            stitchOneItem(inputDir, iconInfo, outBmp, nameMap, missingFiles);
        }
        cachedColorsArray = null;
        return outBmp;
    }

    private static void stitchOneItem(File inputDir, JSONObject iconInfo, Bitmap outBmp, Map<String, String> nameMap, List<String> missingFiles) throws IOException, JSONException {
        String rawName = iconInfo.getString("name");
        JSONArray textures = iconInfo.getJSONArray("uvs");
        int texturesLength = textures.length();
        for (int i = 0; i < texturesLength; i++) {
            stitchOneIcon(new File(inputDir, UnstitchTGA.getFilename(rawName, i, texturesLength, nameMap) + ".png"), textures.getJSONArray(i), outBmp, missingFiles);
        }
    }

    private static void stitchOneIcon(File inputFile, JSONArray uv, Bitmap outBmp, List<String> missingFiles) throws IOException, JSONException {
        if (inputFile.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(inputFile.getAbsolutePath());
            if (bmp == null) {
                missingFiles.add(inputFile.getName());
                return;
            }
            double x1 = uv.getDouble(0);
            double y1 = uv.getDouble(1);
            double x2 = uv.getDouble(2);
            double y2 = uv.getDouble(3);
            double imgWidth = uv.getDouble(4);
            double imgHeight = uv.getDouble(5);
            int sx = (int) ((imgWidth * x1) + 0.5d);
            int sy = (int) ((imgHeight * y1) + 0.5d);
            int width = ((int) ((imgWidth * x2) + 0.5d)) - sx;
            int height = ((int) ((imgHeight * y2) + 0.5d)) - sy;
            int supposedArrayLength = width * height;
            if (cachedColorsArray == null || cachedColorsArray.length != supposedArrayLength) {
                cachedColorsArray = new int[supposedArrayLength];
            }
            bmp.getPixels(cachedColorsArray, 0, width, 0, 0, width, height);
            outBmp.setPixels(cachedColorsArray, 0, width, sx, sy, width, height);
            return;
        }
        missingFiles.add(inputFile.getName());
    }

    private static Bitmap makeBmp(JSONObject iconInfo) throws JSONException {
        JSONArray uv = iconInfo.getJSONArray("uvs").getJSONArray(0);
        return Bitmap.createBitmap((int) uv.getDouble(4), (int) uv.getDouble(5), Config.ARGB_8888);
    }
}
