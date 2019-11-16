package com.example.appmudanzas.prestador_Servicio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appmudanzas.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro_Tarjeta_Circulacion_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro_Tarjeta_Circulacion_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro_Tarjeta_Circulacion_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btn_registrar_tarjeta_circulacion,btnFoto;
    private ImageView imageTarjetaCirculacion;
    private static final String CARPETA_PRINCIPAL="misImagenesApp/";
    private static final String CARPETA_IMAGEN="imagenes";
    private String nombreImagen;
    private static final String DIRECTORIO_IMAGEN=CARPETA_PRINCIPAL+CARPETA_IMAGEN;
    private String path;
    private File fileImagen;
    private Bitmap bitmap;
    private static final int COD_SELECCIONA =10 ;
    private static final int COD_FOTO=20;


    public Registro_Tarjeta_Circulacion_Fragment() {
    }

    public static Registro_Tarjeta_Circulacion_Fragment newInstance(String param1, String param2) {
        Registro_Tarjeta_Circulacion_Fragment fragment = new Registro_Tarjeta_Circulacion_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_registro__tarjeta__circulacion_, container, false);
        crearComponentes();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoOpciones();
            }
        });

        btn_registrar_tarjeta_circulacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro_Datos_Vehiculo_Fragment registro_datos_vehiculo_fragment= new Registro_Datos_Vehiculo_Fragment();
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor,registro_datos_vehiculo_fragment).addToBackStack(null);
                fr.commit();
            }
        });
        return vista;
    }

    private void mostrarDialogoOpciones() {
        final CharSequence []opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Tomar Foto")){
                    abrirCamara();
                }else {
                    if(opciones[i].equals("Elegir de Galeria")){
                        Intent intent= new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada){
            Long consecutivo=System.currentTimeMillis()/1000;
            nombreImagen=consecutivo.toString()+".jpg";
            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+
                    File.separator+nombreImagen;
            fileImagen= new File(path);
            Intent intent=null;
            intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            }else{
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);;
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void crearComponentes(){
        btn_registrar_tarjeta_circulacion=vista.findViewById(R.id.btn_registrar_tarjeta_circulacion);
        btnFoto=vista.findViewById(R.id.btnFoto);
        imageTarjetaCirculacion=vista.findViewById(R.id.imageTarjetaCirculacion);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(miPath, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    Long consecutivo=System.currentTimeMillis()/1000;
                    nombreImagen=consecutivo.toString()+".jpg";
                    fileImagen= new File(imgDecodableString);
                    cursor.close();
                    imageTarjetaCirculacion.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Path",path);
                                }
                            });
                    bitmap= BitmapFactory.decodeFile(path);
                    imageTarjetaCirculacion.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}
