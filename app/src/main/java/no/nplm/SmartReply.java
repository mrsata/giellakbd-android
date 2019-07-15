package no.nplm;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;


public class SmartReply {

    private static final String TAG = "SmartReply";
    private static final String MODEL_PATH = "smartreply.tflite";

    private Context context;
    private MappedByteBuffer model;
    private Interpreter interpreter;

    public SmartReply(Context context) {
        try {
            Log.d(TAG, "Prepare to load model with context: " + context.toString());
            this.context = context;
            this.model = loadModelFile();
            this.interpreter = new Interpreter(this.model);
        } catch (IOException e) {
            Log.e(TAG, "Fail to load model", e);
            return;
        }
    }

    public String[] suggest(String word) {
        String[] placeholderArray = {"placeholder", "is", "wise"};
        String placeholder = TextUtils.join(",", placeholderArray);
        ByteBuffer output = Charset.forName("UTF-8").encode(placeholder);
        ByteBuffer input = Charset.forName("UTF-8").encode(word);
        this.interpreter.run(input, output);
        String suggestion = Charset.forName("UTF-8").decode(output).toString();
        String[] suggestionArray = TextUtils.split(suggestion, ",");
        return suggestionArray;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        try (AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_PATH);
             FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }


}
