package com.example.facerecognitionapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button btnCam;
    private ImageView ivImg;
    private FaceDetectorOptions faceDetectorOptions;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceDetectorOptions = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build();

        ivImg = findViewById(R.id.ivImg);
        btnCam = findViewById(R.id.btnCam);

        btnCam.setOnClickListener(view -> getImageUri());
    }

    void getImageUri() {
        try {
            File file = new File(getFilesDir(), "picFromCamera");

            uri = FileProvider.getUriForFile(
                    Objects.requireNonNull(getApplicationContext()),
                    getApplicationContext().getPackageName() + ".provider",
                    file
            );

            clickPhoto.launch(uri);

        } catch (Exception e) {
            logError(e);
        }
    }

    ActivityResultLauncher<Uri> clickPhoto = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            result -> {
                if(!result || uri == null) Toast.makeText(this, "Try again after some time", Toast.LENGTH_LONG).show();

                startFaceDetector(uri);
            }
    );

    void startFaceDetector(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            InputImage image = InputImage.fromBitmap(bitmap, 0);

            ivImg.setImageBitmap(bitmap);

            com.google.mlkit.vision.face.FaceDetector detector =  FaceDetection.getClient(faceDetectorOptions);

            detector.process(image)
                    .addOnSuccessListener(this::processFaces)
                    .addOnFailureListener(this::logError);

        } catch (IOException e) {
            logError(e);
        }
    }

    private void processFaces(List<Face> faces) {
        if(faces.isEmpty()) {
            displayDialog(new StringBuilder("No Faces Detected"));
            return;
        }

        StringBuilder message = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(2);

        for (Face face : faces) {
            Rect bounds = face.getBoundingBox();
            float rotY = face.getHeadEulerAngleY();
            float rotZ = face.getHeadEulerAngleZ();

            message.append("\nRectangular Bounds:")
                    .append("\n\tBottom: ").append(bounds.bottom)
                    .append("\n\tTop: ").append(bounds.top)
                    .append("\n\tLeft: ").append(bounds.left)
                    .append("\n\tRight: ").append(bounds.right);

            message.append("\n\nHead Rotation (y): ").append(decimalFormat.format(rotY));
            message.append("\nHead Rotation (z): ").append(decimalFormat.format(rotZ));

            FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
            if (leftEar != null) {
                PointF leftEarPos = leftEar.getPosition();
                message.append("\n\nLeft Ear Position (x): ")
                        .append(decimalFormat.format(leftEarPos.x))
                        .append("\nLeft Ear Position (y): ")
                        .append(decimalFormat.format(leftEarPos.y));
            }

            FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
            if (rightEar != null) {
                PointF rightEarPos = rightEar.getPosition();
                message.append("\n\nRight Ear Position (x): ")
                        .append(decimalFormat.format(rightEarPos.x))
                        .append("\nRight Ear Position (y): ")
                        .append(decimalFormat.format(rightEarPos.y));
            }

            if (face.getSmilingProbability() != null) {
                float smileProb = face.getSmilingProbability();
                message.append("\n\nSmile Probability: ").append(decimalFormat.format(smileProb));
            }

            if (face.getRightEyeOpenProbability() != null) {
                float rightEyeOpenProb = face.getRightEyeOpenProbability();
                message.append("\n\nRight Eye Open Probability: ").append(decimalFormat.format(rightEyeOpenProb));
            }

            if (face.getLeftEyeOpenProbability() != null) {
                float leftEyeOpenProb = face.getLeftEyeOpenProbability();
                message.append("\nLeft Eye Open Probability: ").append(decimalFormat.format(leftEyeOpenProb));
            }

            message.append("\n");
        }

        displayDialog(message);
    }

    private void displayDialog(StringBuilder message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Face Recognition Result")
                .setMessage(message)
                .setPositiveButton("OKAY", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        alertDialog.show();
    }

    private void logError(Exception e) {
        Log.e("TAG", e.getLocalizedMessage());
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
    }
}