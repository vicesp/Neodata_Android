package DTO;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;

public class Variables_Universales extends Application {
	
	private static String Nombre_condominio;
	private static int idCondominio;
	private static int mes;
	private static int anio;
	private static int idCalle;
	private static int idManzana;
	private static String numCondomino;
	private static Cursor CursorUniver;
	private static Cursor CursorUniver2;
	private static int position1;
	private static int posiManzana;
	private static int posiAnio;
	private static int posiMes;
	private static Boolean indicador = false;
	private static String IPServidor;
	private static Context context;
	public static int id_usuario;
	public static String nombre_usuario;
	public static String contrasena_usuario;
	public static String nombre_condominio_select;
	public static boolean qr=false;
    public static Activity activity_gral;
	public static String boton_select;
	public static String ubicacion;
	public static String modelo;
	public static String contrato;
	public static String nombre_estacion;
	public static View view_gen;
	public static CustomAdapter customAdapter;
	public static Boolean from_list;
	public static Boolean estacion_completa;
	private static String calendar_desde;
	private static String calendar_hasta;
	private static String fecha_inicio_trat;
	private static boolean estacion_terminada;
	private static String califica_orden;
	private static String user_login;
	private static String desarrollo_sync;
	private static String id_foto;
	private static String total_estimado;
	private static String contratista;
	private static String id_contrato;
	private static String id_contratista;
	private static String id_prototipo;
	private static String id_estacion;
	private static String id_proyecto_select;
	private static String mensaje_insumos="";
	private static boolean isFrom_requi_guardada;
	private static boolean cantidad_correcta;
	private static String id_requi_mandada;
	private static boolean is_from_guarda;

	public static boolean isIs_from_guarda() {
		return is_from_guarda;
	}

	public static void setIs_from_guarda(boolean is_from_guarda) {
		Variables_Universales.is_from_guarda = is_from_guarda;
	}

	public static String getId_requi_mandada() {
		return id_requi_mandada;
	}

	public static void setId_requi_mandada(String id_requi_mandada) {
		Variables_Universales.id_requi_mandada = id_requi_mandada;
	}

	public static boolean isCantidad_correcta() {
		return cantidad_correcta;
	}

	public static void setCantidad_correcta(boolean cantidad_correcta) {
		Variables_Universales.cantidad_correcta = cantidad_correcta;
	}

	public static boolean isIsFrom_requi_guardada() {
		return isFrom_requi_guardada;
	}

	public static void setIsFrom_requi_guardada(boolean isFrom_requi_guardada) {
		Variables_Universales.isFrom_requi_guardada = isFrom_requi_guardada;
	}

	public static String getMensaje_insumos() {
		return mensaje_insumos;
	}

	public static void setMensaje_insumos(String mensaje_insumos) {
		Variables_Universales.mensaje_insumos = mensaje_insumos;
	}

	public static String getId_proyecto_select() {
		return id_proyecto_select;
	}

	public static void setId_proyecto_select(String id_proyecto_select) {
		Variables_Universales.id_proyecto_select = id_proyecto_select;
	}

	public static String getId_estacion() {
		return id_estacion;
	}

	public static void setId_estacion(String id_estacion) {
		Variables_Universales.id_estacion = id_estacion;
	}

	public static String getId_prototipo() {
		return id_prototipo;
	}

	public static void setId_prototipo(String id_prototipo) {
		Variables_Universales.id_prototipo = id_prototipo;
	}

	public static String getId_contrato() {
		return id_contrato;
	}

	public static void setId_contrato(String id_contrato) {
		Variables_Universales.id_contrato = id_contrato;
	}

	public static String getId_contratista() {
		return id_contratista;
	}

	public static void setId_contratista(String id_contratista) {
		Variables_Universales.id_contratista = id_contratista;
	}

	public static String getContratista() {
		return contratista;
	}

	public static void setContratista(String contratista) {
		Variables_Universales.contratista = contratista;
	}

	public static String getTotal_estimado() {
		return total_estimado;
	}

	public static void setTotal_estimado(String total_estimado) {
		Variables_Universales.total_estimado = total_estimado;
	}

	public static String getId_foto() {
		return id_foto;
	}

	public static void setId_foto(String id_foto) {
		Variables_Universales.id_foto = id_foto;
	}

	public static String getDesarrollo_sync() {
		return desarrollo_sync;
	}

	public static void setDesarrollo_sync(String desarrollo_sync) {
		Variables_Universales.desarrollo_sync = desarrollo_sync;
	}

	public static String getUser_login() {
		return user_login;
	}

	public static void setUser_login(String user_login) {
		Variables_Universales.user_login = user_login;
	}

	public static String getCalifica_orden() {
		return califica_orden;
	}

	public static void setCalifica_orden(String califica_orden) {
		Variables_Universales.califica_orden = califica_orden;
	}

	public static boolean isEstacion_terminada() {
		return estacion_terminada;
	}

	public static void setEstacion_terminada(boolean estacion_terminada) {
		Variables_Universales.estacion_terminada = estacion_terminada;
	}

	public static String getFecha_inicio_trat() {
		return fecha_inicio_trat;
	}

	public static void setFecha_inicio_trat(String fecha_inicio_trat) {
		Variables_Universales.fecha_inicio_trat = fecha_inicio_trat;
	}

	public static String getCalendar_desde() {
		return calendar_desde;
	}

	public static void setCalendar_desde(String calendar_desde) {
		Variables_Universales.calendar_desde = calendar_desde;
	}

	public static String getCalendar_hasta() {
		return calendar_hasta;
	}

	public static void setCalendar_hasta(String calendar_hasta) {
		Variables_Universales.calendar_hasta = calendar_hasta;
	}

	public static Boolean getEstacion_completa() {
		return estacion_completa;
	}

	public static void setEstacion_completa(Boolean estacion_completa) {
		Variables_Universales.estacion_completa = estacion_completa;
	}

	public static Boolean getFrom_list() {
		return from_list;
	}

	public static void setFrom_list(Boolean from_list) {
		Variables_Universales.from_list = from_list;
	}

	public static CustomAdapter getCustomAdapter() {
		return customAdapter;
	}

	public static void setCustomAdapter(CustomAdapter customAdapter) {
		Variables_Universales.customAdapter = customAdapter;
	}

	public static View getView_gen() {
		return view_gen;
	}

	public static void setView_gen(View view_gen) {
		Variables_Universales.view_gen = view_gen;
	}

	public static String getUbicacion() {
		return ubicacion;
	}

	public static void setUbicacion(String ubicacion) {
		Variables_Universales.ubicacion = ubicacion;
	}

	public static String getModelo() {
		return modelo;
	}

	public static void setModelo(String modelo) {
		Variables_Universales.modelo = modelo;
	}

	public static String getContrato() {
		return contrato;
	}

	public static void setContrato(String contrato) {
		Variables_Universales.contrato = contrato;
	}

	public static String getNombre_estacion() {
		return nombre_estacion;
	}

	public static void setNombre_estacion(String nombre_estacion) {
		Variables_Universales.nombre_estacion = nombre_estacion;
	}

	public static String getBoton_select() {
		return boton_select;
	}

	public static void setBoton_select(String boton_select) {
		Variables_Universales.boton_select = boton_select;
	}

	public static Activity getActivity_gral() {
        return activity_gral;
    }

    public static void setActivity_gral(Activity activity_gral) {
        Variables_Universales.activity_gral = activity_gral;
    }

    public static boolean isQr() {
		return qr;
	}

	public static void setQr(boolean qr) {
		Variables_Universales.qr = qr;
	}

	public static String getNombre_condominio_select() {
		return nombre_condominio_select;
	}

	public static void setNombre_condominio_select(String nombre_condominio_select) {
		Variables_Universales.nombre_condominio_select = nombre_condominio_select;
	}

	public static int getId_usuario() {
		return id_usuario;
	}

	public static void setId_usuario(int id_usuario) {
		Variables_Universales.id_usuario = id_usuario;
	}

	public static String getNombre_usuario() {
		return nombre_usuario;
	}

	public static void setNombre_usuario(String nombre_usuario) {
		Variables_Universales.nombre_usuario = nombre_usuario;
	}

	public static String getContrasena_usuario() {
		return contrasena_usuario;
	}

	public static void setContrasena_usuario(String contrasena_usuario) {
		Variables_Universales.contrasena_usuario = contrasena_usuario;
	}

	public static int getPosiManzana() {
		return posiManzana;
	}

	public static void setPosiManzana(int posiManzana) {
		Variables_Universales.posiManzana = posiManzana;
	}

	public static int getPosiAnio() {
		return posiAnio;
	}

	public static void setPosiAnio(int posiAnio) {
		Variables_Universales.posiAnio = posiAnio;
	}

	public static int getPosiMes() {
		return posiMes;
	}

	public static void setPosiMes(int posiMes) {
		Variables_Universales.posiMes = posiMes;
	}

	public static Cursor getCursorUniver() {
		return CursorUniver;
	}

	public static void setCursorUniver(Cursor cursorUniver) {
		CursorUniver = cursorUniver;
	}

	public static String getNumCondomino() {
		return numCondomino;
	}

	public static void setNumCondomino(String numCondomino) {
		Variables_Universales.numCondomino = numCondomino;
	}

	public static int getIdManzana() {
		return idManzana;
	}

	public static void setIdManzana(int idManzana) {
		Variables_Universales.idManzana = idManzana;
	}

	public static int getIdCalle() {
		return idCalle;
	}

	public static void setIdCalle(int idCalle) {
		Variables_Universales.idCalle = idCalle;
	}

	public static int getIdCondominio() {
		return idCondominio;
	}

	public static void setIdCondominio(int idCondominio) {
		Variables_Universales.idCondominio = idCondominio;
	}

	public static int getMes() {
		return mes;
	}

	public static void setMes(int mes) {
		Variables_Universales.mes = mes;
	}

	public static int getAnio() {
		return anio;
	}

	public static void setAnio(int anio) {
		Variables_Universales.anio = anio;
	}

	public static String getNombre_condominio() {
		return Nombre_condominio;
	}

	public static void setNombre_condominio(String nombre_condominio) {
		Nombre_condominio = nombre_condominio;
	}

	public static Cursor getCursorUniver2() {
		return CursorUniver2;
	}

	public static void setCursorUniver2(Cursor cursorUniver2) {
		CursorUniver2 = cursorUniver2;
	}

	public static int getPosition1() {
		return position1;
	}

	public static void setPosition1(int position1) {
		Variables_Universales.position1 = position1;
	}

	public static Boolean getIndicador() {
		return indicador;
	}

	public static void setIndicador(Boolean indicador) {
		Variables_Universales.indicador = indicador;
	}

	public static String getIPServidor() {
		return IPServidor;
	}

	public static void setIPServidor(String iPServidor) {
		IPServidor = iPServidor;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		Variables_Universales.context = context;
	}
	
	

}
