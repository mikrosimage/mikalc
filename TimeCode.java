//
//  TimeCode.java
//  mikros_calculator
//
//  Created by G4 Devs on Mon Jun 17 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

public class TimeCode extends ImageSequenceTracking
{
    public double fps;
    
    public static final double PAL_FPS			= 25.0;
    public static final double NTSC_FPS 		= 30;
    public static final double NTSC_FPS_DROP_FRAME 	= 29.97;
    
    public TimeCode( int pFrame, double pFps )
    {
        super( pFrame );
        
        fps 	= pFps;
    }

    
    public TimeCode( String pNotation, double pFps )
    {
        super();
        fps	= pFps;
        
        parseNotation( pNotation );
    }

    public void add( TimeCode pTC )
    {
        frame += pTC.getFrame();
    }

    public void substract( TimeCode pTC )
    {
        frame -= pTC.getFrame();
    }

    
    /**
     * returns the timecode string notation of this TimeCode object
     * it is formatted %2d:%2d:%2d:%2d
     */
    
    public String notation()
    {
        String 	outStr = "";
        int 	hours, minutes, seconds, images = frame;

        if ( fps == 0 ) fps = NTSC_FPS;

        hours 	= frame / ( int )( 3600 * fps );
        images -= hours * 3600 * fps ;

        minutes = images / ( int )( 60 * fps );
        images -= minutes * 60 * fps ;

        seconds = ( int )( images / fps );
        images -= seconds * fps;

        outStr += twoDigitsInt( hours ) + ":";
        outStr += twoDigitsInt( minutes ) + ":";
        outStr += twoDigitsInt( seconds ) + ":";
        outStr += twoDigitsInt( images );
        
        return outStr;
    }

    
    /**
     * parses the %2d:%2d:%2d:%2d timecode to generate the frame number
     * for the moment, very basic implementation (awaits EXACTLY the previous format)
     */
    
    private void parseNotation( String pNotation )
    {
        double tmpFrame;
        int hourFrames, minuteFrames, secondFrames;
        
        /*
        System.out.println( "the original string : " + pNotation );
        System.out.println( "supposed to be images : " +  pNotation.substring( 9, 10 ) );
        */
        
        int hours 	= Integer.parseInt( pNotation.substring( 0, 2 ) );
        int minutes 	= Integer.parseInt( pNotation.substring( 3, 5 ) );
        int seconds 	= Integer.parseInt( pNotation.substring( 6, 8 ) );
        int images 	= Integer.parseInt( pNotation.substring( 9, 11 ) );
        
        // hours
        frame  = ( int )( ( double )hours * 3600 * fps );
        
        // minutes
        frame += ( double )minutes * 60 * fps;
        
        // seconds
        tmpFrame = ( double )seconds * fps;
        if ( tmpFrame > Math.floor( tmpFrame ) ) tmpFrame = Math.floor( tmpFrame ) + 1;
        frame += tmpFrame;
        
        // images
        frame += images;
    }
}
