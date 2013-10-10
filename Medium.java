// Medium.java
// Created on Sun Jun 23 12:14:25 Europe/Paris 2002 by Apple EOModeler Version 5.0

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;
import java.math.BigDecimal;
import java.util.*;

public class Medium extends EOGenericRecord {

    public Medium() {
        super();
    }

/*
    // If you implement the following constructor EOF will use it to
    // create your objects, otherwise it will use the default
    // constructor. For maximum performance, you should only
    // implement this constructor if you depend on the arguments.
    public Medium(EOEditingContext context, EOClassDescription classDesc, EOGlobalID gid) {
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

    public String mediumName() {
        return (String)storedValueForKey("mediumName");
    }

    public void setMediumName(String value) {
        takeStoredValueForKey(value, "mediumName");
    }

    public NSArray imageFormats() {
        return (NSArray)storedValueForKey("imageFormats");
    }

    public void setImageFormats(NSMutableArray value) {
        takeStoredValueForKey(value, "imageFormats");
    }

    public void addToImageFormats(ImageFormat object) {
        NSMutableArray array = (NSMutableArray)imageFormats();

        willChange();
        array.addObject(object);
    }

    public void removeFromImageFormats(ImageFormat object) {
        NSMutableArray array = (NSMutableArray)imageFormats();

        willChange();
        array.removeObject(object);
    }

    public String toString() {
        return mediumName();
    }
}
