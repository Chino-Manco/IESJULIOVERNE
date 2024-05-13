package com.ies.bargas.util;
//Clase que sirve para guardar el usuario y contraseña en el ActivityMain
import android.content.SharedPreferences;
public class Util {
    //Devuelve el email guardado
    public static String getUserMailPrefs(SharedPreferences preferences) {
        return preferences.getString("email", "");
    }
    //Devuelve la contraseña guardada
    public static String getUserClavePrefs(SharedPreferences preferences) {
        return preferences.getString("clave", "");
    }
    //Devuelve el nombre guardado

    public static String getUserNombrePrefs(SharedPreferences preferences) {
        return preferences.getString("nombre", "");
    }
    //Devuelve los apellidos guardados
    public static String getUserApellidosPrefs(SharedPreferences preferences) {
        return preferences.getString("apellidos", "");
    }
    //Devuelve el dni guardado

    public static String getUserDniPrefs(SharedPreferences preferences) {
        return preferences.getString("dni", "");
    }
    //Devuelve el validar guardado
    public static String getUserValidarPrefs(SharedPreferences preferences) {
        return preferences.getString("validar", "");
    }
    //Devuelve lel codigo de delphos

    public static int getUsercodDelphosPrefs(SharedPreferences preferences) {
        return preferences.getInt("cod_delphos", 0);
    }
    //Devuelve el codigo del usuario guardado
    public static int getUserCodUsuarioPrefs(SharedPreferences preferences) {
        return preferences.getInt("cod_usuario", 0);
    }

    //Devuelve el departamento guardado

    public static int getUserDeparmentoCodigoPrefs(SharedPreferences preferences) {
        return preferences.getInt("departamento_codigo", 0);
    }
    public static String getUserDeparmentoNombrePrefs(SharedPreferences preferences) {
        return preferences.getString("departamento_nombre", "");
    }
    //Devuelve el tutor guardado

    public static String getUsertutorPrefs(SharedPreferences preferences) {
        return preferences.getString("tutor_grupo", "");
    }
    public static String getUserRolPrefs(SharedPreferences preferences) {
        return preferences.getString("rol", "");
    }
    //Borra los valores guardados
    public static void removeSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("clave");
        editor.remove("cod_usuario");
        editor.remove("dni");
        editor.remove("nombre");
        editor.remove("apellidos");
        editor.remove("cod_delphos");
        editor.remove("rol");
        editor.remove("departamento_codigo");
        editor.remove("departamento_nombre");
        editor.remove("validar");
        editor.remove("tutor_grupo");
        editor.apply();
    }
}


