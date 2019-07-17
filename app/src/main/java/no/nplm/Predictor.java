package no.nplm;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.List;


public class Predictor extends Spell{

    private static final String TAG = "Predictor";
    private static final String MODEL_PATH = "converted_model.tflite";
    private static final String LABEL_PATH = "google-10000-english.txt";
    private static final int MAX_RESULTS = 3;

    private Context context;
    private MappedByteBuffer model;
    protected Interpreter interpreter;
    protected byte[][] input = null;
    protected List<String> labelList = null;
    private byte[][][] labelProbArray = null;

    public Predictor(@NotNull Context context) {
        try {
            Log.d(TAG, "Prepare to load model with context: " + context.toString());
            this.context = context;
            model = loadModelFile();
            Log.d(TAG, "Model loaded");
            interpreter = new Interpreter(this.model);
            Log.d(TAG, "Interpreter initiated");
            // Allocate input and output
            input = new byte[1][2];
            labelList = loadLabelList();
            labelProbArray = new byte[1][2][labelList.size()];
            Log.d(TAG, "Model input and output allocated");
        } catch (Exception e) {
            Log.e(TAG, "Fail to load model", e);
            return;
        }
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull String word, long nBest, float maxWeight, float beam) {

        // Prepare inputs
        input[0][0] = (byte) 0;
        if (isCorrect(word)){
            input[0][1] = (byte) labelList.indexOf(word);
        }

        // Begin inference
        Trace.beginSection("runInference");
        long startTime = SystemClock.uptimeMillis();
        interpreter.run(input, labelProbArray);
        long endTime = SystemClock.uptimeMillis();
        Trace.endSection();
        Log.d(TAG, "Timecost to run model inference: " + (endTime - startTime));

        // Find the best predictions
        int[] candidateIndex = new int[MAX_RESULTS];
        for (int i =0; i< 10000;i++){
            for(int j =0;j<MAX_RESULTS;j++){
                if (candidateIndex[j] < labelProbArray[0][j][i]) {
                    candidateIndex[j] = labelProbArray[0][j][i];
                    break;
                }
            }
        }

        // Create outputs
        List<String> predictionsArray = new ArrayList<String>();
        for (int i : candidateIndex){
            predictionsArray.add(labelList.get(i));
        }
        String predictions = TextUtils.join(", ", predictionsArray);
        Log.d(TAG, "predictions are: " + predictions);
        return predictionsArray;

    }

    @Override
    public boolean isCorrect(@NotNull String word) {
        return labelList.contains(word);
    }

    private List<String> loadLabelList() throws IOException {
        List<String> labels = new ArrayList<String>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(context.getAssets().open(LABEL_PATH)));
        String line;
        while ((line = reader.readLine()) != null) {
            labels.add(line);
        }
        reader.close();
        return labels;
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
