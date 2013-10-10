//
// extendible_tab.java: Class file for WO Component 'extendible_tab'
// Project mikalc3
//
// Created by g4devs on Tue Jun 25 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class extendible_tab extends WOComponent {
    
    protected String extendibleTabName;
    protected String headColor;
    protected boolean isStart;
    protected boolean isEnd;
        
    protected int randomId;
    
    public static final String DEFAULT_HEAD_COLOR = "#aaaaaa";

    public extendible_tab(WOContext context) 
    {
        super(context);
        
        randomId = ( int )( Math.random() * 1000000000 );
    }

    public String getExtendibleTabName()
    {
        return extendibleTabName;
    }
    
    public void setExtendibleTabName(String newExtendibleTabName)
    {
            extendibleTabName = newExtendibleTabName;
    }

    public void setIsStart(boolean newIsStart)
    {
            isStart = newIsStart;
    }

    public void setIsEnd(boolean newIsEnd)
    {
            isEnd = newIsEnd;
    }
    
    public void setRandomId(int newRandomId)
    {
            randomId = newRandomId;
    }

    public String getDivRandomId()
    {
            return "div_" + randomId;
    }
    public String imageRandomId()
    {
            return "img_" + randomId;
    }
    
    public String headColor()
    {
        if ( headColor == null ) return DEFAULT_HEAD_COLOR;
        return headColor;
    }
}
