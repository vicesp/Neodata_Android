package DTO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sistemas on 01/05/2017.
 */

public class XMLParser_Fotos extends DefaultHandler
{
    public List<XmlValuesModel> listXML=null;
    // string builder acts as a buffer
    StringBuilder builder;

    XmlValuesModel Valores_Fotos_Seg=null;

    consultas con = new consultas();
    byte[] i_logo=null;

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

        if(localName.equals("l_foto")){
            Valores_Fotos_Seg = new XmlValuesModel();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

/////////     para las fotos de seguimiento del paciente //////////////////////
        if(localName.equals("l_foto")){
            listXML.add( Valores_Fotos_Seg );
            con.DAO_Insert_foto_sinc(i_logo,Valores_Fotos_Seg.getId_foto_logo());
        }

        else  if(localName.equalsIgnoreCase("IdLogo")){
            Valores_Fotos_Seg.setId_foto_logo(builder.toString());
        }
        else if(localName.equalsIgnoreCase("Logo")){

            byte[] bloc = Base64.decode(builder.toString(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(bloc, 0, bloc.length);

            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            i_logo=stream.toByteArray();
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