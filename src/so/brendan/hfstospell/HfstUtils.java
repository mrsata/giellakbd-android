package so.brendan.hfstospell;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fi.helsinki.hfst.StringWeightPair;
import fi.helsinki.hfst.StringWeightPairVector;
import fi.helsinki.hfst.ZHfstOspeller;

final public class HfstUtils {
    private static final String TAG = HfstUtils.class.getSimpleName();

    private HfstUtils() {}

    private static Context mCtx;

    private static final String ACCEPTOR = "acceptor.default.hfst";
    private static final String ERRMODEL = "errmodel.default.hfst";

    static {
        System.loadLibrary("lzma");
        System.loadLibrary("archive");
        System.loadLibrary("stlport_shared");
        System.loadLibrary("hfstospell");
    }

    public static void init(Context ctx) {
        mCtx = ctx;
        test();
    }

    private static void test() {
        ZHfstOspeller speller = getSpeller("se");
        StringWeightPairVector vec = speller.suggest("nuvviDspeller");

        for (int i = 0; i < vec.size(); ++i) {
            Log.d(TAG, String.format("%s: %s", vec.get(i).getFirst(), vec.get(i).getSecond()));
        }
    }

    public static void loadNativeLibrary() {
        // Ensures the static initializer is called
    }

    private static File getSpellerCache() {
        return new File(mCtx.getCacheDir(), "spellers");
    }

    private static File extractSpellerFromAssets(String language) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(mCtx.getAssets().open("dicts/" + language + ".zhfst"));
        File f = new File(mCtx.getCacheDir() + "/" + language + ".zhfst");

        byte[] buffer = new byte[bis.available()];
        bis.read(buffer);
        bis.close();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(buffer);
        fos.close();

        return f;
    }

    private static ZHfstOspeller configure(ZHfstOspeller s) {
        s.setQueueLimit(3);
        s.setWeightLimit(50);
        return s;
    }

    @Nullable
    public static ZHfstOspeller getSpeller(@Nonnull Locale locale) {
        return getSpeller(locale.getLanguage());
    }

    @Nullable
    public static ZHfstOspeller getSpeller(@Nonnull String language) {
        ZHfstOspeller zhfst;
        File spellerDir = new File(getSpellerCache(), language);

        // If pre-cached, reuse.
        /*
        if (spellerDir.isDirectory()) {
            File acceptor = new File(spellerDir, ACCEPTOR);
            File errmodel = new File(spellerDir, ERRMODEL);

            if (acceptor.exists() && errmodel.exists()) {
                Log.i(TAG, "Using cached speller for " + language);
                return configure(new ZHfstOspeller(acceptor.getAbsolutePath(),
                                                   errmodel.getAbsolutePath()));
            }
        }
        */

        // Otherwise, unzip and rock on
        zhfst = new ZHfstOspeller();
        zhfst.setTemporaryDir(mCtx.getCacheDir().getAbsolutePath());

        File zhfstFile;
        try {
            zhfstFile = extractSpellerFromAssets(language);
        } catch (IOException e) {
            Log.e(TAG, "Could not load " + language + ".zhfst", e);
            return null;
        }

        File tmpPath = new File(zhfst.readZhfst(zhfstFile.getAbsolutePath()));
        Log.d(TAG, "tmpPath: " + tmpPath);

        zhfstFile.delete();
        tmpPath.renameTo(spellerDir);

        Log.i(TAG, "Newly created cached language " + language);

        return configure(zhfst);
    }

}
