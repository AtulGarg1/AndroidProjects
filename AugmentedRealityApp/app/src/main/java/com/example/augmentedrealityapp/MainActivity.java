package com.example.augmentedrealityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private final String MODEL_GLTF = "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";
    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        assert arFragment != null;
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> buildModel(hitResult));
    }

    private void buildModel(HitResult hitResult) {
        Anchor anchor = hitResult.createAnchor();

        ModelRenderable.builder()
                .setSource(
                        this, RenderableSource.builder()
                                .setSource(this, Uri.parse(MODEL_GLTF), RenderableSource.SourceType.GLTF2)
                                .setScale(0.5f)
                                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                .build()
                )
                .setRegistryId(MODEL_GLTF)
                .build()
                .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                .exceptionally(
                        throwable -> {
                            makeToast();
                            return null;
                        }
                );
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());

        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

    private void makeToast() {
        Toast toast = Toast.makeText(
                this,
                "Unable to load renderable: " + MODEL_GLTF,
                Toast.LENGTH_LONG
        );
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}