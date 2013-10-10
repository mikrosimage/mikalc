//
//  KeyCodeFormatter.java
//  mikalc3
//
//  Created by G4 Devs on Tue Jun 25 2002.
//  Copyright (c) 2002 __MyCompanyName__. All rights reserved.
//

import java.text.*;

public class KeyCodeFormatter extends Format
{
    private int framesByFoot;
    
    public KeyCodeFormatter( int pFramesByFoot )
    {
        super();
        
        framesByFoot = pFramesByFoot;
    }
    
    public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos )
    {
        String outStr;
        ( ( KeyCode )obj ).framesByFoot = framesByFoot;
        
        outStr  = "" + ( ( KeyCode )obj ).notation();
        
        toAppendTo = new StringBuffer( outStr );

        return new StringBuffer( outStr );
    }

    public Object parseObject( String source, ParsePosition pos )
    {
        KeyCode obj;
        
        if ( source == null )
        {
            obj = null;
        } else {
            obj = new KeyCode( source, framesByFoot, "");
        }
        
        pos.setIndex( source.length() );
        return obj;
    }

    
    public int getKeyCodeFramesByFoot() 			{ return framesByFoot; }
    public void setKeyCodeFramesByFoot( int newFramesByFoot ) 	{ framesByFoot = newFramesByFoot; }
}
