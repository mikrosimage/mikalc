// ImageSequenceTreatmentMachine.java
// Created on Mon Jun 24 12:13:20 Europe/Paris 2002 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;
import java.math.BigDecimal;
import java.util.*;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * represents an object operating treatment on an image sequence - for instance a shooter like ArriLaser or a scanner or a DTF backup
 * for the moment, doesn't support multiple resolutions properly (with different calculation time for each resolution)
 * different resolutions can be supported by storing "processStaticTime" and "processTimeByFrame" as arrays of first person objects with links to the in and out formats
 * but certainly too complex, so it could only depend on the out format OR the in format (TBD)
 */

public class ImageSequenceTreatmentMachine extends EOGenericRecord 
{

    // attributes used to perform calculations (represent the in format, out format and the number of computed frames etc)
    protected ImageFormat inImageFormat;
    protected ImageFormat outImageFormat;
    protected int frames;

    public ImageSequenceTreatmentMachine() 
    {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public ImageSequenceTreatmentMachine(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
        super(context, classDesc, gid);
    }

    // If you add instance variables to store property values you
    // should add empty implementions of the Serialization methods
    // to avoid unnecessary overhead (the properties will be
    // serialized for you in the superclass).
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    }
*/

    public Number inStaticTime() {
        return (Number)storedValueForKey("inStaticTime");
    }

    public void setInStaticTime(Number value) {
        takeStoredValueForKey(value, "inStaticTime");
    }

    public Number inTimeByFrame() {
        return (Number)storedValueForKey("inTimeByFrame");
    }

    public void setInTimeByFrame(Number value) {
        takeStoredValueForKey(value, "inTimeByFrame");
    }

    public Number processFramesToAdd() {
        return (Number)storedValueForKey("processFramesToAdd");
    }

    public void setProcessFramesToAdd(Number value) {
        takeStoredValueForKey(value, "processFramesToAdd");
    }

    public Number processFramesToAddByFrame() {
        return (Number)storedValueForKey("processFramesToAddByFrame");
    }

    public void setProcessFramesToAddByFrame(Number value) {
        takeStoredValueForKey(value, "processFramesToAddByFrame");
    }

    public Number processStaticTime() {
        return (Number)storedValueForKey("processStaticTime");
    }

    public void setProcessStaticTime(Number value) {
        takeStoredValueForKey(value, "processStaticTime");
    }

    public Number processTimeByFrame() {
        return (Number)storedValueForKey("processTimeByFrame");
    }

    public void setProcessTimeByFrame(Number value) {
        takeStoredValueForKey(value, "processTimeByFrame");
    }

    public Number outFramesToAdd() {
        return (Number)storedValueForKey("outFramesToAdd");
    }

    public void setOutFramesToAdd(Number value) {
        takeStoredValueForKey(value, "outFramesToAdd");
    }

    public Number outFramesToAddByFrame() {
        return (Number)storedValueForKey("outFramesToAddByFrame");
    }

    public void setOutFramesToAddByFrame(Number value) {
        takeStoredValueForKey(value, "outFramesToAddByFrame");
    }

    public Number outStaticTime() {
        return (Number)storedValueForKey("outStaticTime");
    }

    public void setOutStaticTime(Number value) {
        takeStoredValueForKey(value, "outStaticTime");
    }

    public Number outTimeByFrame() {
        return (Number)storedValueForKey("outTimeByFrame");
    }

    public void setOutTimeByFrame(Number value) {
        takeStoredValueForKey(value, "outTimeByFrame");
    }

    public String machineName() {
        return (String)storedValueForKey("machineName");
    }

    public void setMachineName(String value) {
        takeStoredValueForKey(value, "machineName");
    }

    public String type() {
        return (String)storedValueForKey("type");
    }

    public void setType(String value) {
        takeStoredValueForKey(value, "type");
    }
    
    
    /**
     * returns the treatment time in milliseconds
     */
    
    public int totalTime()
    {
        int time;
        time = inTime() + processTime() + outTime();        
        return time;
    }
    

    /**
     * returns the input time
     */

    public int inTime()
    {
        int time;
        time = inStaticTime().intValue() + ( int )( inTimeByFrame().doubleValue() * frames );
        return time;
    }

    
    /**
     * returns the process time
     */

    public int processTime()
    {
        int time;
        time = processStaticTime().intValue() + ( int )( processTimeByFrame().doubleValue() * framesAfterIn() );
        return time;
    }


    /**
     * returns the output time
     */

    public int outTime()
    {
        int time;
        time = outStaticTime().intValue() + ( int )( outTimeByFrame().doubleValue() * framesAfterProcess() );
        return time;
    }
    
    
    /**
     * returns the treatment time in a properly formatted manner (1h 12min 3s)
     * the biggest unit is hours
     */
    
    public String formattedTotalTime()
    {
        /* didn't perform very well...
        Date duration = new Date( outTime() );

        // Format the current time.
        SimpleDateFormat formatter = new SimpleDateFormat ( "hh'h ' mm'min ' ss'sec '" );
        return formatter.format( duration );
        */
        
        int timeInSeconds 	= outTime() / 1000;
        int hours 		= timeInSeconds / 3600;
        timeInSeconds		-= hours * 3600;
        int minutes		= timeInSeconds / 60;
        timeInSeconds		-= minutes * 60;
        
        return hours + "h " + minutes + "min " + timeInSeconds + "sec";
    }
    
    
    /**
     * returns the total number of frames added before process and thus after the input
     */
    
    public int framesAddedAfterIn()
    {
        int fr;
        fr = processFramesToAdd().intValue() + ( int )( processFramesToAddByFrame().doubleValue() * frames );
        return fr;
    }

    
    /**
     * returns the number of frames added after process
     */

    public int framesAddedAfterProcess()
    {
        int fr;
        fr = outFramesToAdd().intValue() + ( int )( outFramesToAddByFrame().doubleValue() * framesAfterIn() );
        return fr;
    }


    /**
     * returns the number of frames after the image ingestion
     */

    public int framesAfterIn()
    {
        return frames + framesAddedAfterIn();
    }

    
    /**
     * returns the number of frames after the internal process
     * this is the number of frames that are going to be output
     */
    
    public int framesAfterProcess()
    {
        return framesAfterIn() + framesAddedAfterProcess();
    }
}
