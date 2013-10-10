//
// Cp_TimeCode.java: Class file for WO Component 'Cp_TimeCode'
// Project mikalc3
//
// Created by g4devs on Fri Jun 28 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Cp_TimeCode extends WOComponent {
    protected TimeCode timeCode;
    protected TimeCodeFormatter formatter;
    protected int randomId;
    protected String customStyle;
    protected String customClass;
    
    private static final double DEFAULT_TIMECODE_FPS = 25.0;
    private static final String DEFAULT_STYLE = "border-style: 'groove'";
    private static final String DEFAULT_CLASS = "";

    public Cp_TimeCode(WOContext context)
    {
        super( context );
        
        randomId = ( int )( java.lang.Math.random() * 1000000 );
    }
    
    public String jsId()
    {
        return "timeCode_" + randomId;
    }
    
    public String jsVarName()
    {
        return "G_TC_" + jsId();
    }
    
    public int jsVarValue()
    {
        return ( timeCode == null ) ? 0 : timeCode.frame;
    }
    
    public String jsFpsVarName()
    {
        return "G_TC_Fps_" + randomId;
    }
    
    public double jsFpsVarValue()
    {
        return ( timeCode == null ) ? 0 : timeCode.fps;
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
        double fps = ( timeCode == null ) ? DEFAULT_TIMECODE_FPS : timeCode.fps;
        
        outStr = "TC_format( '" + jsId() + "', true, " + fps + ")";
        return outStr;
    }
    
    public String onKeyPressHandlerCall()
    {
        String outStr;
        outStr = "TC_onKeyPressHandler( event, '" + jsId() + "' )";
        return outStr;
    }
}
