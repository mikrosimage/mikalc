//
// Cp_KeyCode.java: Class file for WO Component 'Cp_KeyCode'
// Project mikalc3
//
// Created by g4devs on Wed Jul 10 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Cp_KeyCode extends WOComponent 
{
    protected KeyCode keyCode;
    protected KeyCodeFormatter formatter;
    protected int randomId;
    protected String customStyle;
    protected String customClass;
    
    private static final int DEFAULT_KEYCODE_FRAMES_BY_FOOT 	= 16;
    private static final String DEFAULT_STYLE 			= "border-style: 'groove'";
    private static final String DEFAULT_CLASS 			= "";

    public Cp_KeyCode(WOContext context) 
    {
        super(context);
        
        randomId = ( int )( java.lang.Math.random() * 1000000 );
    }

    public String jsId()
    {
        return "keyCode_" + randomId;
    }
    
    public String jsVarName()
    {
        return "G_KC_" + jsId();
    }
    
    public int jsVarValue()
    {
        return ( keyCode == null ) ? 0 : keyCode.frame;
    }
    
    public String jsFramesByFootVarName()
    {
        return "G_KC_Modulo_" + randomId;
    }
    
    public double jsFramesByFootVarValue()
    {
        return ( keyCode == null ) ? 0 : keyCode.framesByFoot;
    }

    public String decorationStyle()
    {
        String outStr;
        outStr = ( customStyle == null ) ? DEFAULT_STYLE : customStyle;
        return outStr;
    }
    
    public String decorationClass()
    {
        String outStr;
        outStr = ( customClass == null ) ? DEFAULT_CLASS : customClass;
        return outStr;
    }
    
    public String onBlurHandlerCall()
    {
        String outStr;
        int framesByFoot = ( keyCode == null ) ? DEFAULT_KEYCODE_FRAMES_BY_FOOT : keyCode.framesByFoot;
        
        outStr = "KC_format( '" + jsId() + "', true, " + framesByFoot + ")";
        return outStr;
    }
    
    public String onKeyPressHandlerCall()
    {
        String outStr;
        outStr = "KC_onKeyPressHandler( event, '" + jsId() + "' )";
        return outStr;
    }
}
