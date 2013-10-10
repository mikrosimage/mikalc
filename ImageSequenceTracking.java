//
//  ImageSequenceTracking.java
//  mikros_calculator
//
//  Created by G4 Devs on Mon Jun 17 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

public abstract class ImageSequenceTracking
{
    public int frame;

    public ImageSequenceTracking() {}

    public ImageSequenceTracking(int pFrame)
    {
        frame = pFrame;
    }

    public void setFrame( int pFrame )
    {
        frame = pFrame;
    }

    public int getFrame()
    {
        return frame;
    }
    
    public void addFrames(int pFrames)
    {
        frame += pFrames;
    }
    
    public void substractFrames(int pFrames)
    {
        frame -= pFrames;
    }
    
    
    /**
     * returns the number of frames between two sequence tracking objects
     */
    
    public static int framesBetween(ImageSequenceTracking seq1, ImageSequenceTracking seq2)
    {
        int dif = seq1.getFrame() - seq2.getFrame();
        
        if ( dif < 0 )
            dif *= -1;

        return dif;
    }
    
    protected String twoDigitsInt( int pValue )
    {
        if ( pValue == 0 )
            return "00";
        if ( pValue < 10 )
            return "0" + pValue;
        
        return String.valueOf( pValue );
    }
    
    public String toString()
    {
        return notation();
    }

    
    public abstract String notation();
}
