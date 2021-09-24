package com.example.trabajopractico_lab1;

import static java.lang.Integer.parseInt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar seek;
    private TextView valor;
    private Switch switchP;
    private TextView varPorcentaje;
    private CheckBox checkBoxretiro;
    private TextView textViewDireccion;
    private EditText editTextDireccionRetiro;
    private Spinner spinner;
    private Button button;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerCategoria();
        switchPorcentaje();
        mostrarDireccionRetiro();
        bottonPublicar();

        valor = (TextView)findViewById(R.id.txtSalida);
        seek = (SeekBar)findViewById(R.id.skB);
        valor.setText(String.valueOf(seek.getProgress()));

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valor.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void spinnerCategoria(){
        String[] opciones = {"SELECCIONE CATEGORIA","INDUMENTARIA", "ELECTRONICA", "ENTRETENIMIENTO", "JARDIN", "VEHICULOS", "JUGUETES"};
        spinner = (Spinner) findViewById(R.id.listaCategorias);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter (this,
                android.R.layout.simple_spinner_item,
                opciones) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public String seleccionarCategoria() {

        Spinner categoria = (Spinner) findViewById(R.id.listaCategorias);

        final String[] selec = new String[1];
        selec[0] =categoria.getSelectedItem().toString();

        return selec[0];

    }

    public  void switchPorcentaje() {


        TextView ceroPorcentaje;
        TextView variablePorcentaje;

        switchP = (Switch) findViewById(R.id.descuento);
        switchP .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SeekBar seekBarPorcentaje;
                Switch switchP;
                TextView ceroPorcentaje;
                TextView variablePorcentaje;
                seekBarPorcentaje = (SeekBar) findViewById(R.id.skB);
                ceroPorcentaje= (TextView)  findViewById(R.id.textEntrada);
                variablePorcentaje = (TextView)  findViewById(R.id.txtSalida);
                if (isChecked) {
                    ceroPorcentaje.setVisibility(View.VISIBLE);
                    seekBarPorcentaje.setVisibility(View.VISIBLE);
                    variablePorcentaje.setVisibility(View.VISIBLE);
                    mostrarProgreso();
                }
                if (!isChecked) {

                    ceroPorcentaje.setVisibility(View.GONE);
                    seekBarPorcentaje.setVisibility(View.GONE);
                    variablePorcentaje.setVisibility(View.GONE);
                }

            }
        });
    }

    public void mostrarProgreso(){

        SeekBar seekBarPorcentaje;

        varPorcentaje = (TextView)findViewById(R.id.txtSalida);
        // SeekBar
        seekBarPorcentaje = (SeekBar) findViewById(R.id.skB);
        // Valor Inicial
        seekBarPorcentaje.setProgress(0);
        // Valot Final
        seekBarPorcentaje.setMax(100);
        seekBarPorcentaje.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    //hace un llamado a la perilla cuando se arrastra
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        varPorcentaje.setText(String.valueOf(progress));
                    }
                    //hace un llamado  cuando se toca la perilla
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    //hace un llamado  cuando se detiene la perilla
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


    }

    public void mostrarDireccionRetiro() {

        checkBoxretiro = (CheckBox) findViewById(R.id.direcionCheck);
        textViewDireccion = (TextView) findViewById(R.id.direcion);
        editTextDireccionRetiro = (EditText) findViewById(R.id.direcionRetiro);
        button = (Button) findViewById(R.id.aceptar);

        checkBoxretiro.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    textViewDireccion.setVisibility(View.VISIBLE);
                    editTextDireccionRetiro.setVisibility(View.VISIBLE);
                    button.setTop(0);
                    System.out.println(button.getTop());
                }

                if (!b) {
                    textViewDireccion.setVisibility(View.GONE);
                    editTextDireccionRetiro.setVisibility(View.GONE);

                }

            }
        });
    }

    public void bottonPublicar() {

        Button publicar = (Button) findViewById(R.id.aceptar);
        EditText  titulo = (EditText) findViewById(R.id.tituloEdit);
        EditText precio= (EditText) findViewById(R.id.precioEdit);
        EditText retiro=(EditText) findViewById(R.id.direcionRetiro);
        EditText email=(EditText) findViewById(R.id.correoEdit);
        CheckBox checkBoxAceptar = (CheckBox) findViewById(R.id.terminosyCondic);

        final String[] textoPrecio = new String[1];
        final Integer[] numeroPrecio = new Integer[1];
        final String [] textoPorcentaje= new String[1];
        final Integer [] numeroPorcentaje =new Integer[1];
        final String[] categoria = new String[1];


        Validaciones validaciones= new Validaciones();

        publicar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                categoria[0] = seleccionarCategoria();
                boolean[] permisoPublicar = {true};

                if(validaciones.Vacio(titulo)){
                    //  titulo.setError("Campo Requerido");
                    // titulo.requestFocus();
                    permisoPublicar[0] =false;
                    Toast.makeText(getApplicationContext(), "Debe ingresar un título", Toast.LENGTH_LONG).show();
                }
                else{
                    if(!validaciones.validarString(titulo.getText().toString()))

                    {   //  titulo.setError("Título no valido");
                        //   titulo.requestFocus();
                        permisoPublicar[0] =false;
                        Toast.makeText(getApplicationContext(), "Título no valido ", Toast.LENGTH_LONG).show();}

                }

                if(validaciones.Vacio(email)) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un correo ", Toast.LENGTH_LONG).show();
                }
                else{
                    if (!validaciones.isEmail(email.getText().toString())){
                        permisoPublicar[0] =false;
                        Toast.makeText(getApplicationContext(), "Correo no valido", Toast.LENGTH_LONG).show();}


                }

                if(validaciones.Vacio(precio)){
                    // precio.setError("Campo Requerido");
                    //precio.requestFocus();
                    permisoPublicar[0] =false;
                    Toast.makeText(getApplicationContext(), "Debe ingresar un valor al precio", Toast.LENGTH_LONG).show();
                }
                else {
                    textoPrecio[0] = precio.getText().toString();
                    numeroPrecio[0] = parseInt(textoPrecio[0]);

                    if(numeroPrecio[0].equals(0)){
                        permisoPublicar[0] =false;
                        Toast.makeText(getApplicationContext(), "El precio debe ser mayor 0", Toast.LENGTH_LONG).show();
                    }

                }
                if(categoria[0].equals("SELECCIONE CATEGORIA")){
                    permisoPublicar[0] =false;
                    Toast.makeText(getApplicationContext(), "Debe seleccionar una categoria", Toast.LENGTH_LONG).show();

                }
                if(checkBoxretiro.isChecked()){
                    if(validaciones.Vacio(retiro)){
                        //retiro.setError("Campo Requerido");
                        //retiro.requestFocus();
                        permisoPublicar[0] =false;
                        Toast.makeText(getApplicationContext(), "Debe ingresar direccion de retiro ", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(!validaciones.validarString(retiro.getText().toString())){

                            //  titulo.setError("Título no valido");
                            //   titulo.requestFocus();
                            permisoPublicar[0] =false;
                            Toast.makeText(getApplicationContext(), "Direccion no valida ", Toast.LENGTH_LONG).show();}
                    }

                }
                if(switchP.isChecked()){
                    textoPorcentaje[0]=varPorcentaje.getText().toString();
                    numeroPorcentaje[0]= parseInt(textoPorcentaje[0]);
                    if(numeroPorcentaje[0].equals(0)){
                        permisoPublicar[0] =false;
                        Toast.makeText(getApplicationContext(), " Por favor seleccione un porcentaje mayor a 0 o quite la opcion de ofrecer descuento de envio ", Toast.LENGTH_LONG).show();

                    }
                }

                if(!checkBoxAceptar.isChecked()){
                    permisoPublicar[0] =false;
                    Toast.makeText(getApplicationContext(), "Debe aceptar los terminos para publicar los datos ", Toast.LENGTH_LONG).show();

                }

                if( permisoPublicar[0] ){
                    permisoPublicar[0] =false;
                    Toast.makeText(getApplicationContext(), "Los datos fueron cargados correctamente ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}