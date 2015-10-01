package net.zhuoweizhang.mercator;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tga.TGAImage;

public final class UnstitchTGA {
    private UnstitchTGA() {
    }

    public static JSONArray readMap(File mapFile) throws IOException, JSONException {
        byte[] inputBytes = new byte[((int) mapFile.length())];
        FileInputStream fis = new FileInputStream(mapFile);
        fis.read(inputBytes);
        fis.close();
        return new JSONArray(new String(inputBytes, Charset.forName("UTF-8")));
    }

    public static JSONArray readMap(InputStream is) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder myStrBuilder = new StringBuilder();
        while (true) {
            String curLine = reader.readLine();
            if (curLine != null) {
                myStrBuilder.append(curLine);
            } else {
                reader.close();
                return new JSONArray(myStrBuilder.toString());
            }
        }
    }

    public static void unstitchTGA(File inputFile, File mapFile, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        unstitchTGA(inputFile, readMap(mapFile), outputDir, (Map) nameMap);
    }

    public static Bitmap readTGA(File inputFile) throws IOException {
        TGAImage img = TGAImage.read(new FileInputStream(inputFile));
        Bitmap bmp = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Config.ARGB_8888);
        int[] outArr = new int[(img.getWidth() * img.getHeight())];
        img.getData().order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer myIntBuffer = img.getData().asIntBuffer();
        myIntBuffer.position(0);
        myIntBuffer.get(outArr);
        bmp.setPixels(outArr, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        return bmp;
    }

    public static void unstitchTGA(File inputFile, JSONArray map, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        outputDir.mkdirs();
        unstitch(readTGA(inputFile), map, outputDir, nameMap);
    }

    public static void unstitchPNG(File inputFile, JSONArray map, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        outputDir.mkdirs();
        unstitch(BitmapFactory.decodeFile(inputFile.getAbsolutePath()), map, outputDir, nameMap);
    }

    public static void unstitch(Bitmap bmp, JSONArray map, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        int arrayLength = map.length();
        for (int i = 0; i < arrayLength; i++) {
            unstitchOne(bmp, map.getJSONObject(i), outputDir, nameMap);
        }
    }

    private static void unstitchOne(Bitmap bmp, JSONObject iconInfo, File outputDir, Map<String, String> nameMap) throws IOException, JSONException {
        String rawName = iconInfo.getString("name");
        JSONArray textures = iconInfo.getJSONArray("uvs");
        int texturesLength = textures.length();
        for (int i = 0; i < texturesLength; i++) {
            copyOneIcon(bmp, textures.getJSONArray(i), new File(outputDir, getFilename(rawName, i, texturesLength, nameMap) + ".png"));
        }
    }

    public static String getFilename(String blockName, int number, int texturesLength, Map<String, String> nameMap) {
        if (blockName.length() > 2 && blockName.substring(blockName.length() - 2).equals("_x")) {
            blockName = blockName.substring(0, blockName.length() - 2);
        }
        String fileName = blockName + (number != 0 ? "_" + number : "");
        String altFileName = (String) nameMap.get(fileName);
        if (altFileName != null) {
            return altFileName;
        }
        return fileName;
    }

    private static void copyOneIcon(Bitmap bmp, JSONArray uv, File output) throws IOException, JSONException {
        double x1 = uv.getDouble(0);
        double y1 = uv.getDouble(1);
        double x2 = uv.getDouble(2);
        double y2 = uv.getDouble(3);
        double imgWidth = uv.getDouble(4);
        double imgHeight = uv.getDouble(5);
        int sx = (int) ((imgWidth * x1) + 0.5d);
        int sy = (int) ((imgHeight * y1) + 0.5d);
        Bitmap bitmap = bmp;
        Bitmap out = Bitmap.createBitmap(bitmap, sx, sy, ((int) ((imgWidth * x2) + 0.5d)) - sx, ((int) ((imgHeight * y2) + 0.5d)) - sy);
        FileOutputStream fos = new FileOutputStream(output);
        out.compress(CompressFormat.PNG, 100, fos);
        fos.close();
    }

    public static Map<String, String> loadNameMap(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Map<String, String> retval = new HashMap();
        while (true) {
            String curLine = reader.readLine();
            if (curLine != null) {
                curLine = curLine.trim();
                if (curLine.length() >= 1 && curLine.charAt(0) != '#') {
                    String[] parts = curLine.split(",");
                    if (parts.length != 2) {
                        continue;
                    } else {
                        if (parts[0].endsWith("_0")) {
                            parts[0] = parts[0].substring(0, parts[0].length() - 2);
                        }
                        if (retval.containsKey(parts[0])) {
                            throw new RuntimeException("Duplicate in name map: " + curLine);
                        }
                        retval.put(parts[0], parts[1]);
                    }
                }
            } else {
                reader.close();
                return retval;
            }
        }
    }
}
