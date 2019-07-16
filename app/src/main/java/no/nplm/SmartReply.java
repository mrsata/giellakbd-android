package no.nplm;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import java.util.*;


public class SmartReply extends Spell{

    private static final String TAG = "SmartReply";
    private static final String MODEL_PATH = "converted_model.tflite";

    private Context context;
    private MappedByteBuffer model;
    private Interpreter interpreter;

    public SmartReply(@NotNull Context context) {
        try {
            Log.d(TAG, "Prepare to load model with context: " + context.toString());
            this.context = context;
            this.model = loadModelFile();
            Log.d(TAG, "Model loaded");
            this.interpreter = new Interpreter(this.model);
            Log.d(TAG, "Interpreter initiated");
        } catch (Exception e) {
            Log.e(TAG, "Fail to load model", e);
            return;
        }
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull String word, long nBest, float maxWeight, float beam) {
        String[] placeholderArray = {"placeholder", "is", "wise"};
        String placeholder = TextUtils.join(",", placeholderArray);
        ByteBuffer output = Charset.forName("UTF-8").encode(placeholder);
        ByteBuffer input = Charset.forName("UTF-8").encode(word);
        this.interpreter.run(input, output);
        String suggestion = Charset.forName("UTF-8").decode(output).toString();
        Log.d(TAG, "suggestions are: " + suggestion);
        String[] suggestionArray = TextUtils.split(suggestion, ",");
        List<String> suggestionList = Arrays.asList(suggestionArray);
        return suggestionList;
    }

    @Override
    public boolean isCorrect(@NotNull String word) {
        return false;
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
