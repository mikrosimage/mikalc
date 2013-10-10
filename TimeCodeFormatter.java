//
//  TimeCodeFormatter.java
//  custom_formatter
//
//  Created by G4 Devs on Thu Jun 20 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

import java.text.*;

public class TimeCodeFormatter extends Format
{
    private double timeCodeFps;
    
    public TimeCodeFormatter( double fps )
    {
        super();
        
        timeCodeFps = fps;
    }
    
    public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos )
    {
        String outStr;
        ( ( TimeCode )obj ).fps = timeCodeFps;
        
        outStr  = "" + ( ( TimeCode )obj ).notation();
        
        toAppendTo = new StringBuffer( outStr );

        return new StringBuffer( outStr );
    }

    public Object parseObject( String source, ParsePosition pos )
    {
        TimeCode obj;
        
        if ( source == null )
        {
            obj = null;
        } else {
            obj = new TimeCode( source, timeCodeFps );
        }
        
        pos.setIndex( source.length() );
        return obj;
    }

    
    public double getTimeCodeFps() 		{ return timeCodeFps; }
    public void setTimeCodeFps( double newFps ) { timeCodeFps = newFps; }
}
