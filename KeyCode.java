//
//  KeyCode.java
//  mikros_calculator_2
//
//  Created by G4 Devs on Tue Jun 18 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//


public class KeyCode extends ImageSequenceTracking
{
    public int framesByFoot;
    public String stockNumber;
    
    public static final int FRAMES_BY_FOOT_35_MM = 16;
    
    public KeyCode( int pFrame, int pFramesByFoot, String pStockNumber )
    {
        super( pFrame );
        
        framesByFoot 	= pFramesByFoot;
        stockNumber	= pStockNumber;
    }

    
    public KeyCode( String pNotation, int pFramesByFoot, String pStockNumber )
    {
        super();
        
        framesByFoot 	= pFramesByFoot;
        stockNumber	= pStockNumber;

        parseNotation( pNotation );
    }

    public void add( KeyCode pKC )
    {
        frame += pKC.getFrame();
    }

    public void substract( KeyCode pKC )
    {
        frame -= pKC.getFrame();
    }

    
    /**
     * returns the timecode string notation of this TimeCode object
     * it is formatted %2d:%2d:%2d:%2d
     */
    
    public String notation()
    {
        String 	outStr = "";
        int 	inches, images = frame;

        inches 	= frame / framesByFoot;
        images -= inches * framesByFoot;

        outStr += fourDigitsInt( inches ) + " +";
        outStr += twoDigitsInt( images );
        
        return outStr;
    }

    
    /**
     * parses the %4d +%2d keycode to generate the frame number
     * for the moment, very basic implementation (awaits EXACTLY the previous format)
     */
    
    private void parseNotation( String pNotation )
    {
        int inches 	= Integer.parseInt( pNotation.substring( 0, 4 ) );
        int images 	= Integer.parseInt( pNotation.substring( 6, 8 ) );

        frame  = inches * framesByFoot;
        frame += images;
    }
        
    private String fourDigitsInt( int pValue )
    {
        if ( pValue == 0 )
            return "0000";
        if ( pValue < 10 )
            return "000" + pValue;
        if ( pValue < 100 )
            return "00" + pValue;
        if ( pValue < 1000 )
            return "0" + pValue;

        return String.valueOf( pValue );
    }
}

