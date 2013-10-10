// ImageFormat.java
// Created on Fri Jul 12 12:34:11 Europe/Paris 2002 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;
import java.math.BigDecimal;
import java.util.*;

public class ImageFormat extends EOGenericRecord {

    public ImageFormat() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public ImageFormat(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public String imageFormatName() {
        return (String)storedValueForKey("imageFormatName");
    }

    public void setImageFormatName(String value) {
        takeStoredValueForKey(value, "imageFormatName");
    }

    public Number width() {
        return (Number)storedValueForKey("width");
    }

    public void setWidth(Number value) {
        takeStoredValueForKey(value, "width");
    }

    public Number height() {
        return (Number)storedValueForKey("height");
    }

    public void setHeight(Number value) {
        takeStoredValueForKey(value, "height");
    }

    public Number arePixelsSquare() {
        return (Number)storedValueForKey("arePixelsSquare");
    }

    public void setArePixelsSquare(Number value) {
        takeStoredValueForKey(value, "arePixelsSquare");
    }

    public String family() {
        return (String)storedValueForKey("family");
    }

    public void setFamily(String value) {
        takeStoredValueForKey(value, "family");
    }

    public Medium medium() {
        return (Medium)storedValueForKey("medium");
    }

    public void setMedium(Medium value) {
        takeStoredValueForKey(value, "medium");
    }

    public long imageWeightInOctetsWithDepth(int bits)
    {
        return ( long ) bits * width().intValue() * height().intValue();
    }
    
    public String toString()
    {
        return imageFormatName();
    }
}
