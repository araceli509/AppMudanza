package com.example.appmudanzas.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmudanzas.R;
import com.example.appmudanzas.RecycleView.MainActivityRecycle;
import com.example.appmudanzas.prestador_Servicio.Prestador_Servicio_Activity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPrincipal extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button buttonConductor;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==SIGN_IN_CODE){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            firebaseAuthwithGoogle(result.getSignInAccount());

        }else{
            Toast.makeText(this, R.string.not_log_in,Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthwithGoogle(GoogleSignInAccount signInAccount) {



        AuthCredential credential= GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "No se pudo autenticar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen() {
        Intent intent= new Intent(this, MainActivityRecycle.class);
        //Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_principal);



        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton=(SignInButton)findViewById(R.id.signInButton);

        signInButton.setSize(SignInButton.SIZE_WIDE);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if (user != null){
                    goMainScreen();
                }
            }
        };




        Button buttonConductor = (Button) findViewById(R.id.buttonConductor);
        buttonConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Prestador_Servicio_Activity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onStop() {

        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}


