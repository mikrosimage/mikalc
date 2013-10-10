//
// timecode.java: Class file for WO Component 'timecode'
// Project mikalc3
//
// Created by g4devs on Fri Jun 28 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class TimeCode_Cp extends WOComponent {
    protected TimeCode timeCode;
    protected TimeCodeFormatter formatter;
    protected int randomId;

    public timecode(WOContext context) 
    {
        super(context);
        
        randomId = java.lang.Math.random();
    }
    
    public String getJsVarName()
    {
        return "tc_" + randomId;
    }
    
    public String getJSVarValue()
    {
        return timeCode.frames;
    }
    
    public String getJsFpsVarName()
    {
        return "tcFps_" + randomId;
    }
    
    public String getJsFpsVarValue()
    {
    
    }

}
