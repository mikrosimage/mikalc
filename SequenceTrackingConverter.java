//
//  SequenceTrackingConverter.java
//  mikros_calculator
//
//  Created by G4 Devs on Mon Jun 17 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

/**
 * in charge of computing conversions between frames, timecodes, keycodes
 * and finally providing the weight of the corresponding UNCOMPRESSED image sequence
 * could be used to track image layers in Mikado v2
 */

public class SequenceTrackingConverter
{
    protected ImageFormat imageFormat;
    protected Integer startFrame;
    protected Integer endFrame;
    protected TimeCode startTC;
    protected KeyCode startKC;
    protected Integer layerNb;
    protected int bitDepth;
    protected Double sizeReductionFactor;

    // data necessary to create timecode and keycodes
    private double timeCodeFps;
    private int keyCodeFramesByFoot;
    private int projectionFps;
    
    // temp ie not really necessary data, but usefull for binding ; will maybe be converted into derived properties
    protected Integer totalFrame;
    protected TimeCode endTC;
    protected TimeCode totalTC;
    protected KeyCode endKC;
    protected KeyCode totalKC;
    protected int weightInOctets;


    /**
     * Empty sequence tracking constructor. Inits the internal parameters to default values.
     */
    
    public SequenceTrackingConverter()
    {
        layerNb = new Integer( 1 );
        clear();
    }

    
    /**
     * clears the converter
     */
    
    public void clear()
    {
        startFrame 	= null;
        endFrame 	= null;
        totalFrame 	= null;
        startTC 	= null;
        endTC 		= null;
        totalTC 	= null;
        startKC 	= null;
        endKC 		= null;
        totalKC 	= null;
        weightInOctets	= 0;
    }

    
    /**
     * returns weight of the corresponding UNCOMPRESSED image sequence in a String with Ko, Mo, Go, To appended, depending on the weight.
     * for the moment, 1 Ko = 1000 octets
     */
    
    public String formattedWeight()
    {
        String outStr = "";
        
        if ( imageFormat == null )
        {
            return outStr;
        } else {
            if ( totalFrame != null )
            {
                if ( layerNb == null ) layerNb = new Integer( 1 );
                double weight =  (imageFormat.imageWeightInOctetsWithDepth( bitDepth ) * layerNb.intValue() * totalFrame.intValue() ) * Math.pow( sizeReductionFactor.doubleValue(), 2 ) ;
                outStr = formattedOctetWeight( ( long )weight );
            } else {
                outStr = "0 octet";
            }
            
            return outStr;
        }
    }
    
    
    /**
     * returns the specification of 1 frame (ie resolution, 
     * doesn't take the number of layers 
     *
     * @returns String
     */
    
    public String frameSpecification()
    {
        int reducedWidth 	= ( int ) ( sizeReductionFactor.doubleValue() * imageFormat.width().intValue() );
        int reducedHeight 	= ( int ) ( sizeReductionFactor.doubleValue() * imageFormat.height().intValue() );
        String weightStr 	= formattedOctetWeight( ( long )( imageFormat.imageWeightInOctetsWithDepth( bitDepth ) * Math.pow( sizeReductionFactor.doubleValue(), 2 ) ) );
        return "Chaque image " + reducedWidth + " x " + reducedHeight + ", " + bitDepth + " bits par pixel pèse " + weightStr + ".";
    }
    
    
    /**
     * returns a formatted weight (with the proper extension, octet, Ko, Mo...)
     * @param pOctetWeight
     */
    
    public String formattedOctetWeight( long pOctetWeight )
    {
        String outStr = "";
    
        if ( pOctetWeight < 1000l )
            outStr = pOctetWeight + " octets";
        else if ( pOctetWeight < 1000000l )
            outStr = displayRound( ( double )pOctetWeight / 1000l ) + " Ko";
        else if ( pOctetWeight < 1000000000l )
            outStr = displayRound( ( double )pOctetWeight / 1000000l ) + " Mo";
        else if ( pOctetWeight < 1000000000000l )
            outStr = displayRound( ( double )pOctetWeight / 1000000000l ) + " Go";
        else if ( pOctetWeight < 1000000000000000l )
            outStr = displayRound( ( double )pOctetWeight / 1000000000000l ) + " To";
            
        return outStr;
    }
    
    
    /**
     * returns the projection duration in milliseconds
     */
    
    public long projectionTime()
    {
        long duration;
        duration = ( long )( totalFrame.doubleValue() / projectionFps * 1000 );
        return duration;
    }
    
    
    /**
     * returns a properly formatted projection time (hours, minutes, seconds, milliseconds
     */
    
    public String formattedProjectionTime()
    {
        String outStr = "";
        
        if ( totalFrame != null && projectionFps != 0 )
        {
            int hours, minutes, seconds, images, tmpFrames;
            tmpFrames = totalFrame.intValue();
            
            hours = ( int )tmpFrames / ( projectionFps * 3600 );
            tmpFrames -= hours * ( projectionFps * 3600 );
            
            minutes = ( int )tmpFrames / ( projectionFps * 60 );
            tmpFrames -= minutes * ( projectionFps * 60 );
    
            seconds = ( int )tmpFrames / ( projectionFps );
            tmpFrames -= seconds * ( projectionFps );
            
            outStr += ( hours > 0 ) ? hours + "h " : "";
            outStr += ( hours > 0 || minutes > 0 ) ? minutes + "min " : "";
            outStr += ( hours > 0 || minutes > 0 || seconds > 0 ) ? seconds + "sec " : "";
            outStr += tmpFrames + "im";
        }
        
        return outStr;
    }
    
    
    public void computeConversionsFromTotalFrames()
    {
        // setting the first frame to 1
        startFrame 	= new Integer( 1 );
        endFrame 	= totalFrame;

        // setting the timecodes
        computeTimeCodesFromFrames();
        
        //setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeConversionsFromStartAndEndFrames()
    {
        totalFrame = new Integer( endFrame.intValue() - startFrame.intValue() + 1 );

        // setting the timecodes
        computeTimeCodesFromFrames();

        //setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeConversionsFromTotalTimeCode()
    {
        startFrame = new Integer( 1 );
        endFrame = new Integer( totalTC.frame );
        totalFrame = new Integer( totalTC.frame );

        // setting the timecodes
        computeTimeCodesFromFrames();

        //setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeConversionsFromStartAndEndTimeCode()
    {
        startFrame = new Integer( startTC.frame );
        endFrame = new Integer( endTC.frame );
        totalFrame = new Integer( endTC.frame - startTC.frame + 1 );

        // setting the timecodes
        computeTimeCodesFromFrames();

        // setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeConversionsFromTotalKeyCode()
    {
        startFrame = new Integer( 1 );
        endFrame = new Integer( totalKC.frame );
        totalFrame = new Integer( totalKC.frame );

        // setting the timecodes
        computeTimeCodesFromFrames();

        //setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeConversionsFromStartAndEndKeyCode()
    {
        startFrame = new Integer( startKC.frame );
        endFrame = new Integer( endKC.frame );
        totalFrame = new Integer( endKC.frame - startKC.frame + 1 );

        // setting the timecodes
        computeTimeCodesFromFrames();

        // setting the keycodes
        computeKeyCodesFromFrames();
    }

    public void computeTimeCodesFromFrames()
    {
        startTC = new TimeCode( startFrame.intValue(), timeCodeFps );
        endTC 	= new TimeCode( endFrame.intValue(), timeCodeFps );
        totalTC = new TimeCode( totalFrame.intValue(), timeCodeFps );
        // System.out.println( "total timecode : " + totalTC );
    }
    
    public void computeKeyCodesFromFrames()
    {
        startKC = new KeyCode( startFrame.intValue(), keyCodeFramesByFoot, "" );
        endKC 	= new KeyCode( endFrame.intValue(), keyCodeFramesByFoot, "" );
        totalKC = new KeyCode( totalFrame.intValue(), keyCodeFramesByFoot, "" );
        // System.out.println( "total timecode : " + totalTC );    
    }

    public double displayRound( double pValue )
    {
        double outDbl = ( ( double )Math.round( pValue * 100 ) ) / 100;
        return outDbl;
    }
    
    public ImageFormat getImageFormat() 				{ return imageFormat; }
    public void setImageFormat( ImageFormat newImageFormat ) 		{ imageFormat = newImageFormat; }
    public void setTimeCodeFps( double newTimeCodeFps ) 		{ timeCodeFps = newTimeCodeFps; }
    public void setKeyCodeFramesByFoot( int newKeyCodeFramesByFoot ) 	{ keyCodeFramesByFoot = newKeyCodeFramesByFoot; }
    public void setProjectionFps( int newProjectionFps )		{ projectionFps = newProjectionFps; }
}
