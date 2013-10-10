//
// Main.java: Class file for WO Component 'Main'
// Project mikalc3
//
// Created by gg on Sun Jun 23 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eoaccess.*;

public class Main extends WOComponent
{
    /** @TypeInfo fr.mikrosimage.image_management.Medium
    * an NSArray of Medium objects to store the displayed popup
    */

    protected NSMutableArray media;

    /*
    // static data before using database stored records
    private NSArray staticMedia = new NSArray( new Medium[] { new Medium( "vidéo" ), new Medium( "film" ) } );
    private NSArray staticVideoImageFormats = new NSArray( new ImageFormat[] { new ImageFormat( "PAL", 720, 576, true, ( Medium )staticMedia.objectAtIndex( 0 ) ),
    new ImageFormat( "NTSC", 720, 540, true, ( Medium )staticMedia.objectAtIndex( 0 ) ) }
                                                           );
    private NSArray staticFilmImageFormats = new NSArray( new ImageFormat[] { new ImageFormat( "Acad 1.33, 2K", 2048, 1536, true, ( Medium )staticMedia.objectAtIndex( 1 ) ),
    new ImageFormat( "Acad 1.33, 1K", 1024, 768, true, ( Medium )staticMedia.objectAtIndex( 1 ) ) }
                                                          );
    */
    private NSArray staticBitDepths = new NSArray( new Integer[] { new Integer( 8 ), new Integer( 10 ), new Integer( 12 ), new Integer( 16 ) } );
    
    private NSDictionary zoomFactor1 = new NSDictionary( new Object[] { new Double( 1.0 ), "taille actuelle" }, new String[] { "value", "displayString" } );
    private NSDictionary zoomFactor2 = new NSDictionary( new Object[] { new Double( 0.5 ), "1/2 res" }, new String[] { "value", "displayString" } );
    private NSDictionary zoomFactor3 = new NSDictionary( new Object[] { new Double( 0.25 ), "1/4 res" }, new String[] { "value", "displayString" } );

    protected NSArray staticZoomFactors = new NSArray( new NSDictionary[] { zoomFactor1, zoomFactor2, zoomFactor3 } );
    
    
    // projection frames by seconds
    private NSDictionary projectionFps1 = new NSDictionary( new Object[] { new Integer( 24 ), "film 24 i/sec" }, new String[] { "value", "displayString" } );
    private NSDictionary projectionFps2 = new NSDictionary( new Object[] { new Integer( 25 ), "PAL 25 i/sec" }, new String[] { "value", "displayString" } );
    private NSDictionary projectionFps3 = new NSDictionary( new Object[] { new Integer( 30 ), "NTSC 30 i/sec" }, new String[] { "value", "displayString" } );
    private NSDictionary projectionFps4 = new NSDictionary( new Object[] { new Integer( 48 ), "film 48 i/sec" }, new String[] { "value", "displayString" } );
    protected NSArray staticProjectionFps = new NSArray( new Object[] { projectionFps1, projectionFps2, projectionFps3, projectionFps4 } );
    protected NSDictionary projectionFpsItem;
    protected NSDictionary selectedProjectionFps;
    
    private NSArray staticLayerNb = new NSArray( new Integer[] { new Integer( 1 ),
        new Integer( 2 ),
        new Integer( 3 ),
        new Integer( 4 ),
        new Integer( 5 ) } );


    // static timecode fps and pictures by foot NSArrays and dictionaries (two keys : "displayString" and "value" ; ex "35 - 16 perfs" and 16)
    private NSDictionary timecode1 = new NSDictionary( new Object[] { new Double( 25.0 ), "PAL" }, new String[] { "value" , "displayString" } );
    private NSDictionary timecode2 = new NSDictionary( new Object[] { new Double( 30.0 ), "NTSC" }, new String[] { "value" , "displayString" } );
    private NSDictionary timecode3 = new NSDictionary( new Object[] { new Double( 24.0 ), "24P" }, new String[] { "value" , "displayString" } );
    protected NSArray staticTimeCodeFps = new NSArray( new NSDictionary[] { timecode1, timecode2, timecode3 } );

    private NSDictionary keycode1 = new NSDictionary( new Object[] { new Integer( 16 ), "35mm / 16" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode2 = new NSDictionary( new Object[] { new Integer( 8 ), "70mm / 8" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode3 = new NSDictionary( new Object[] { new Integer( 12 ), "70mm / 12" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode4 = new NSDictionary( new Object[] { new Integer( 15 ), "70mm / 15" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode5 = new NSDictionary( new Object[] { new Integer( 24 ), "70mm / 24" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode6 = new NSDictionary( new Object[] { new Integer( 20 ), "16mm / 20" }, new String[] { "value" , "displayString" } );
    private NSDictionary keycode7 = new NSDictionary( new Object[] { new Integer( 40 ), "16mm / 40" }, new String[] { "value" , "displayString" } );
    
    protected NSArray staticKeyCodeFramesByFoot = new NSArray( new NSDictionary[] { keycode1, keycode2, keycode3, keycode4, keycode5, keycode6, keycode7 } );

    protected NSDictionary selectedTimeCodeFps;
    protected NSDictionary selectedKeyCodeFramesByFoot;

    protected NSDictionary timeCodeFpsItem;
    protected NSDictionary keyCodeFramesByFootItem;

    // items
    protected Medium mediumItem;
    protected ImageFormat imageFormatItem;
    protected int bitDepthItem;
    protected NSDictionary zoomFactorItem;

    protected Medium selectedMedium;
    protected ImageFormat selectedImageFormat;
    protected int bitDepth;

    // converter
    protected SequenceTrackingConverter converter;

    // variables used to track changes in the popup menus
    private Medium previousMedium;
    private ImageFormat previousImageFormat;

    /** @TypeInfo int */
    protected NSArray bitDepths;

    /** @TypeInfo String */
    protected NSArray zoomFactors;

    /** @TypeInfo int */
    protected NSArray layerNumbers;
    protected int layerNumberItem;

    // timecode formatter
    protected TimeCodeFormatter timeCodeFormatter;
    
    // keycode formatter
    protected KeyCodeFormatter keyCodeFormatter;
    
    protected String mode;

    /** @TypeInfo java.lang.String */
    protected NSArray availableModes = new NSArray( new String[] { "simple", "expert" } );
    protected String modeItem;

    /** @TypeInfo ImageSequenceTreatmentMachine */
    protected NSArray availableScanners;

    /** @TypeInfo ImageSequenceTreatmentMachine */
    protected NSArray availableShooters;
    protected Scanner scannerItem;
    protected ImageSequenceTreatmentMachine shooterItem;
    protected ImageSequenceTreatmentMachine backuperItem;
    protected Scanner selectedScanner;
    protected ImageSequenceTreatmentMachine selectedShooter;
    protected ImageSequenceTreatmentMachine selectedBackuper;

    /** @TypeInfo ImageSequenceTreatmentMachine */
    protected NSArray availableBackupers;
    protected boolean displayFrameEdition;
    protected boolean displayTimeCodeEdition;
    protected boolean displayKeyCodeEdition;
    
    // the custom ImageFormat
    protected ImageFormat customFormat;
    protected boolean isCustomFormat;
    protected boolean isPreviousCustomFormat;
    protected NSDictionary selectedZoomFactor;
    
    protected NSMutableArray familyImageFormats;
    protected String selectedFamily;
    protected String previousSelectedFamily;
    
    protected NSMutableArray imageFormatFamilies;

    public Main( WOContext context )
    {
        super( context );
        familyImageFormats = new NSMutableArray();
        imageFormatFamilies = new NSMutableArray();
        
        // setting the mode
        mode = "simple";
        
        Medium customMedium = new Medium();
        customMedium.setMediumName( "autre..." );
        
        customFormat = new ImageFormat();
        customFormat.setImageFormatName( "custom format" );
        
        customFormat.setMedium( customMedium );
        

        // retrieving the media from the database ; selecting the first one
        EOEditingContext ec 		= ( ( Session )session() ).defaultEditingContext();

        EOFetchSpecification fetchSpec	= new EOFetchSpecification( "Medium", null, null );
        media 				= new NSMutableArray( ec.objectsWithFetchSpecification( fetchSpec ) );
        //media.addObject( customMedium );
        isCustomFormat = false;
        
        // getting the family image format
        
        EOFetchSpecification familyFetchSpec = new EOFetchSpecification( "ImageFormat", null, null );
        
        selectedMedium 			= ( Medium )media.objectAtIndex( 0 );
        selectedImageFormat 		= ( ImageFormat )selectedMedium.imageFormats().objectAtIndex( 0 );
        previousMedium			= selectedMedium;
        previousImageFormat		= selectedImageFormat;
        updateImageFormatFamilies();
        
        bitDepths	= staticBitDepths;
        layerNumbers	= staticLayerNb;

        // setting the default timecode and keycode
        selectedTimeCodeFps 		= ( NSDictionary )staticTimeCodeFps.objectAtIndex( 0 );
        selectedKeyCodeFramesByFoot 	= ( NSDictionary )staticKeyCodeFramesByFoot.objectAtIndex( 0 );
        selectedZoomFactor 		= ( NSDictionary )staticZoomFactors.objectAtIndex( 0 );
        selectedProjectionFps		= ( NSDictionary )staticProjectionFps.objectAtIndex( 0 );
        
        converter 			= new SequenceTrackingConverter();
        converter.setImageFormat( selectedImageFormat );
        converter.sizeReductionFactor 	= ( Double )selectedZoomFactor.objectForKey( "value" ); 
        converter.setProjectionFps( ( ( Integer )selectedProjectionFps.objectForKey( "value" ) ).intValue() );
        converter.setTimeCodeFps( ( ( Double )selectedTimeCodeFps.objectForKey( "value" ) ).doubleValue() );
        converter.bitDepth 		= 8;
        
        timeCodeFormatter 		= new TimeCodeFormatter( TimeCode.PAL_FPS );
        keyCodeFormatter		= new KeyCodeFormatter( KeyCode.FRAMES_BY_FOOT_35_MM );

        // initialisation of scanners
        initializeScanners();
        // initialisation of shooters
        initializeShooters();
        // initialisation of backupers
        initializeBackupers();

        displayFrameEdition = true;
    }
    
    
    /**
     * initializes the family
     * updates the third popup
     */
    
    public void updateImageFormatFamilies()
    {
        String tmpFamily;
    
        if ( ! isCustomFormat )
        {
            imageFormatFamilies.removeAllObjects();
            for ( int i = 0; i < selectedMedium.imageFormats().count(); i++ )
            {
                tmpFamily = ( ( ImageFormat )selectedMedium.imageFormats().objectAtIndex( i ) ).family();
                if ( ! imageFormatFamilies.containsObject( tmpFamily ) ) imageFormatFamilies.addObject( tmpFamily );
            }
            selectedFamily = ( String )imageFormatFamilies.objectAtIndex( 0 );
            previousSelectedFamily = selectedFamily;
            updateFamilyImageFormats();
        }
    }
    
    public void updateFamilyImageFormats()
    {
        EOEditingContext ec = session().defaultEditingContext();
        
        NSArray bindings = new NSArray( new Object[] { selectedFamily } );
        
        EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat( "family like %@", bindings );
        
        EOFetchSpecification familyFetchSpec = new EOFetchSpecification( "ImageFormat", qualifier, null );
        
        familyImageFormats = new NSMutableArray( ec.objectsWithFetchSpecification( familyFetchSpec ) );
        selectedImageFormat = ( ImageFormat )familyImageFormats.objectAtIndex( 0 );
    }


    public WOComponent updatePopup()
    {
        // updating the projection fps
        converter.setProjectionFps( ( ( Integer )selectedProjectionFps.objectForKey( "value" ) ).intValue() );
    
        // updating the zoom factor
        if ( selectedZoomFactor != null )
        {
            converter.sizeReductionFactor = ( Double )selectedZoomFactor.objectForKey( "value" );
        } else {
            converter.sizeReductionFactor = new Double( 1.0 );
        }
        
        // setting the converter format
        // if custom image format :
        if ( isCustomFormat )
        {
            if ( customFormat.width() == null ) customFormat.setWidth( new Integer( 0 ) );
            if ( customFormat.height() == null ) customFormat.setHeight( new Integer( 0 ) );
            
            // if previously, there was a custom format
            if ( !isPreviousCustomFormat )
            {
                customFormat.setWidth( previousImageFormat.width() );
                customFormat.setHeight( previousImageFormat.height() );
            }
            
            converter.setImageFormat( customFormat );
            
            // setting the variable allowing to know wether the user has switched from default formats to custom formats
            isPreviousCustomFormat = true;
            
        } else {
            
            // updating the selected format if there is a change in the medium
            // the new selected format is the first one
        
            // if selectedMedium is null, it means that the popup has been disabled 
            // and that selectedMedium should be set to previousMedium
            if ( selectedMedium == null )
            {
                selectedMedium	 	= previousMedium;
                selectedImageFormat 	= previousImageFormat;
            } else if ( selectedMedium != previousMedium ) {
                updateImageFormatFamilies();
            } else if ( selectedFamily != previousSelectedFamily ) {
                previousSelectedFamily = selectedFamily;
                updateFamilyImageFormats();
            }
        
            // setting the previous medium and format
            previousMedium 		= selectedMedium;
            previousImageFormat 	= selectedImageFormat;

            converter.setImageFormat( selectedImageFormat );
            
            isPreviousCustomFormat = false;
        }

        // updating the converter timecode fps and keycode frames by foot
        if ( selectedTimeCodeFps != null )
        {
            timeCodeFormatter.setTimeCodeFps( ( ( Double )selectedTimeCodeFps.objectForKey( "value" ) ).doubleValue() );
            converter.setTimeCodeFps( ( ( Double )selectedTimeCodeFps.objectForKey( "value" ) ).doubleValue() );
        } else {
            timeCodeFormatter.setTimeCodeFps( TimeCode.PAL_FPS );
            converter.setTimeCodeFps( TimeCode.PAL_FPS );
        }
        if ( selectedKeyCodeFramesByFoot != null )
        {
            keyCodeFormatter.setKeyCodeFramesByFoot( ( ( Integer )selectedKeyCodeFramesByFoot.objectForKey( "value" ) ).intValue() );
            converter.setKeyCodeFramesByFoot( ( ( Integer )selectedKeyCodeFramesByFoot.objectForKey( "value" ) ).intValue() );
        } else {
            keyCodeFormatter.setKeyCodeFramesByFoot( KeyCode.FRAMES_BY_FOOT_35_MM );
            converter.setKeyCodeFramesByFoot( KeyCode.FRAMES_BY_FOOT_35_MM );
        }

        // browsing the filled fields in the calculation table and calling the proper converter methods
        determineConversionStrategy();

        return null;
    }

    public WOComponent initInput()
    {
        // just a try... refaulting all data
        EOEditingContext ec = ( ( Session )session() ).defaultEditingContext();
        ec.invalidateAllObjects();
        
        initializeInput();
        
        return null;
    }
    
    public void initializeInput()
    {
        // setting the mode
        mode = "simple";
                        
        // retrieving the media from the database ; selecting the first one
        EOEditingContext ec 		= ( ( Session )session() ).defaultEditingContext();

        EOFetchSpecification fetchSpec	= new EOFetchSpecification( "Medium", null, null );
        media 				= new NSMutableArray( ec.objectsWithFetchSpecification( fetchSpec ) );
        //media.addObject( customMedium );
        isCustomFormat = false;
        
        selectedMedium 			= ( Medium )media.objectAtIndex( 0 );
        selectedImageFormat 		= ( ImageFormat )selectedMedium.imageFormats().objectAtIndex( 0 );
        previousMedium			= selectedMedium;
        previousImageFormat		= selectedImageFormat;

        // setting the default timecode and keycode
        selectedTimeCodeFps 		= ( NSDictionary )staticTimeCodeFps.objectAtIndex( 0 );
        selectedKeyCodeFramesByFoot 	= ( NSDictionary )staticKeyCodeFramesByFoot.objectAtIndex( 0 );
        selectedZoomFactor 		= ( NSDictionary )staticZoomFactors.objectAtIndex( 0 );
        selectedProjectionFps		= ( NSDictionary )staticProjectionFps.objectAtIndex( 0 );
        
        converter 			= new SequenceTrackingConverter();
        converter.setImageFormat( selectedImageFormat );
        converter.sizeReductionFactor 	= ( Double )selectedZoomFactor.objectForKey( "value" ); 
        converter.setProjectionFps( ( ( Integer )selectedProjectionFps.objectForKey( "value" ) ).intValue() );
        converter.setTimeCodeFps( ( ( Double )selectedTimeCodeFps.objectForKey( "value" ) ).doubleValue() );
        converter.bitDepth 		= 8;
    }

    
    /**
     * determines which fields are filled and calls the proper converter conversion method accordingly
     * the priority order for each category is : start/end, total
     * the category priority order is : frame, timecode, keycode
     */
    
    private void determineConversionStrategy()
    {
        // if the user wants to edit frames
        if ( displayFrameEdition )
        {
            if ( ( mode == "expert" ) && ( converter.startFrame != null ) && ( converter.endFrame != null ) )
            {
                converter.computeConversionsFromStartAndEndFrames();
            } else if ( converter.totalFrame != null ){
                converter.computeConversionsFromTotalFrames();
            }
        // if the user wants to edit timecodes
        } else if ( displayTimeCodeEdition ) {
            if ( ( mode == "expert" ) && ( converter.startTC != null ) && ( converter.endTC != null ) ) {
                converter.computeConversionsFromStartAndEndTimeCode();
            } else if ( converter.totalTC != null ) {
                converter.computeConversionsFromTotalTimeCode();
            }
        // if the user wants to edit keycodes
        } else if ( displayKeyCodeEdition ) {
            if ( ( mode == "expert" ) && ( converter.startKC != null ) && ( converter.endKC != null ) ) {
                converter.computeConversionsFromStartAndEndKeyCode();
            } else if ( converter.totalKC != null ) {
                converter.computeConversionsFromTotalKeyCode();
            }
        }
    }
    

    public String timeCodeFpsDisplayString()
    {
        return ( String )timeCodeFpsItem.objectForKey( "displayString" );
    }

    public String keyCodeFramesByFootDisplayString()
    {
        return ( String )keyCodeFramesByFootItem.objectForKey( "displayString" );
    }

    /**
     * action used to clear the converter data (frame, timecode, keycode)
     */

    public WOComponent clearConverter()
    {
        converter.clear();
        
        return null;
    }

    public boolean isExpertMode()
    {
        return ( mode == "expert" );
    }    

    private void initializeScanners()
    {
        EOEditingContext ec = session().defaultEditingContext();
    
        EOFetchSpecification fetchSpec = new EOFetchSpecification( "Scanner", null, null );
        availableScanners = ec.objectsWithFetchSpecification( fetchSpec );
        selectedScanner = ( ( availableScanners != null ) && ( availableScanners.count() > 0 ) ) ? ( Scanner )availableScanners.objectAtIndex( 0 ) : null;
    }

    private void initializeShooters()
    {
        EOEditingContext ec = session().defaultEditingContext();
    
        EOFetchSpecification fetchSpec = new EOFetchSpecification( "Shooter", null, null );
        availableShooters = ec.objectsWithFetchSpecification( fetchSpec );
        selectedShooter = ( ( availableShooters != null ) && ( availableShooters.count() > 0 ) ) ? ( Shooter )availableShooters.objectAtIndex( 0 ) : null;
    }

    private void initializeBackupers()
    {
        EOEditingContext ec = session().defaultEditingContext();
    
        EOFetchSpecification fetchSpec = new EOFetchSpecification( "Backuper", null, null );
        availableBackupers = ec.objectsWithFetchSpecification( fetchSpec );
        selectedBackuper = ( ( availableBackupers != null ) && ( availableBackupers.count() > 0 ) ) ? ( Backuper )availableBackupers.objectAtIndex( 0 ) : null;
    }
    
    public Medium getSelectedMedium() 			{ return selectedMedium; }
    public ImageFormat getSelectedImageFormat() 			{ return selectedImageFormat; }
    public void setSelectedMedium( Medium newMedium ) 	{ selectedMedium = newMedium; }
    public void setSelectedImageFormat(ImageFormat newImageFormat) 	{ selectedImageFormat = newImageFormat; }


    public String scanTime()
    {
        selectedScanner.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();

        return selectedScanner.formattedTotalTime();
    }
    
    public String shootTime()
    {
        selectedShooter.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();
        return selectedShooter.formattedTotalTime();
    }
    
    public String backupTime()
    {
        selectedBackuper.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();
        return selectedBackuper.formattedTotalTime();
    }

    public int scanFrames()
    {
        selectedScanner.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();

        return selectedScanner.framesAfterProcess();
    }

    public int shootFrames()
    {
        selectedShooter.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();
        return selectedShooter.framesAfterProcess();
    }

    public int backupFrames()
    {
        selectedBackuper.frames = ( converter.totalFrame == null ) ? 0 : converter.totalFrame.intValue();
        return selectedBackuper.framesAfterProcess();
    }
    
    public WOComponent editFrames()
    {
        displayFrameEdition 	= true;
        displayTimeCodeEdition 	= false;
        displayKeyCodeEdition 	= false;
        
        return null;
    }

    public WOComponent editTimeCode()
    {
        displayFrameEdition 	= false;
        displayTimeCodeEdition 	= true;
        displayKeyCodeEdition 	= false;
        
        return null;
    }

    public WOComponent editKeyCode()
    {
        displayFrameEdition 	= false;
        displayTimeCodeEdition 	= false;
        displayKeyCodeEdition 	= true;
        
        return null;
    }
    
    public boolean isSimpleMode()
    {
        return ( !isExpertMode() );
    }
    
    public String zoomFactorDisplayString()
    {
        return ( String )( ( NSDictionary )zoomFactorItem ).objectForKey( "displayString" );
    }
    
    public String projectionFpsDisplayString()
    {
        return ( String )( ( NSDictionary )projectionFpsItem ).objectForKey( "displayString" );
    }
}
