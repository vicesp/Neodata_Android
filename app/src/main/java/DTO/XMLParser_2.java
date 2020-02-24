package DTO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser_2 extends DefaultHandler
{

    public List<XmlValuesModel> listXML=null;
    // string builder acts as a buffer
    StringBuilder builder;

    XmlValuesModel Valores_Responsables=null;
    XmlValuesModel Valores_Desarrollos=null;
    XmlValuesModel Valores_Usuarios=null;
    XmlValuesModel Valores_Rel_user_des=null;

    consultas con = new consultas();

    @Override
    public void startDocument() throws SAXException {
        /******* Create ArrayList To Store XmlValuesModel object ******/
        listXML = new ArrayList<XmlValuesModel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        /****  When New XML Node initiating to parse this function called *****/
        // Create StringBuilder object to store xml node value
        builder=new StringBuilder();

        if(localName.equals("r_desarrollos")){
            Valores_Desarrollos = new XmlValuesModel();
        }
        else if(localName.equals("r_responsables")){
            Valores_Responsables = new XmlValuesModel();
        }
        else if(localName.equals("d_usuarios")){
            Valores_Usuarios = new XmlValuesModel();
        }
        else if(localName.equals("d_user_des")){
            Valores_Rel_user_des = new XmlValuesModel();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        /////////     para los Desarrollos //////////////////////
        if(localName.equals("r_desarrollos")){
            listXML.add( Valores_Desarrollos );
            con.DAO_Insert_Desarrollo(Valores_Desarrollos.getId_desarrollo(), Valores_Desarrollos.getClave(),
                    Valores_Desarrollos.getDesarrollo());
        }

        else  if(localName.equalsIgnoreCase("id_desarrollo")){
            Valores_Desarrollos.setId_desarrollo(builder.toString());
        }
        else if(localName.equalsIgnoreCase("clave")){
            Valores_Desarrollos.setClave(builder.toString());
        }
        else if(localName.equalsIgnoreCase("desarrollo")){
            Valores_Desarrollos.setDesarrollo(builder.toString());
        }



        /////////     para el catalogo de Responsables //////////////////////
        else if(localName.equals("r_responsables")){
            listXML.add( Valores_Responsables );
            con.DAO_Insert_Responsables(Valores_Responsables.getR_id_resp(),Valores_Responsables.getR_cod_resp(),
                    Valores_Responsables.getR_nom_resp(),Valores_Responsables.getR_cargo_resp());
        }

        else  if(localName.equalsIgnoreCase("idResponsable")){
            Valores_Responsables.setR_id_resp(builder.toString());
        }
        else if(localName.equalsIgnoreCase("responsable")){
            Valores_Responsables.setR_cod_resp(builder.toString());
        }
        else if(localName.equalsIgnoreCase("nombre")){
            Valores_Responsables.setR_nom_resp(builder.toString());
        }
        else if(localName.equalsIgnoreCase("cargo")){
            Valores_Responsables.setR_cargo_resp(builder.toString());
        }

        /////////     para los Usuarios de Android //////////////////////
        else if(localName.equals("d_usuarios")){
            listXML.add( Valores_Usuarios );
            con.DAO_Insert_Usuarios(Valores_Usuarios.getIsActive(), Valores_Usuarios.getId_usuario(),
                    Valores_Usuarios.getNombre(),Valores_Usuarios.getContrasena(),Valores_Usuarios.getFecha_creacion());
        }

        else  if(localName.equalsIgnoreCase("isActive_u")){
            Valores_Usuarios.setIsActive(builder.toString());
        }
        else if(localName.equalsIgnoreCase("id_usuario_u")){
            Valores_Usuarios.setId_usuario(builder.toString());
        }
        else if(localName.equalsIgnoreCase("nombre_u")){
            Valores_Usuarios.setNombre(builder.toString());
        }
        else if(localName.equalsIgnoreCase("contrasena_u")){
            Valores_Usuarios.setContrasena(builder.toString());
        }
        else if(localName.equalsIgnoreCase("fecha_creacion_u")){
            Valores_Usuarios.setFecha_creacion(builder.toString());
        }

        /////////     para la Relacion de usuarios-desarrollo //////////////////////
        else if(localName.equals("d_user_des")){
            listXML.add( Valores_Rel_user_des );
            con.DAO_Insert_Rel_User_Des(Valores_Rel_user_des.getId_usuario_rel(), Valores_Rel_user_des.getId_desarrollo_rel(),
                    Valores_Rel_user_des.getFecha_creacion_rel());
        }

        else  if(localName.equalsIgnoreCase("id_usuario_rel")){
            Valores_Rel_user_des.setId_usuario_rel(builder.toString());
        }
        else if(localName.equalsIgnoreCase("id_desarrollo_rel")){
            Valores_Rel_user_des.setId_desarrollo_rel(builder.toString());
        }
        else if(localName.equalsIgnoreCase("fecha_creacion_rel")){
            Valores_Rel_user_des.setFecha_creacion_rel(builder.toString());
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        /******  Read the characters and append them to the buffer  ******/
        String tempString=new String(ch, start, length);
        builder.append(tempString);
    }
}