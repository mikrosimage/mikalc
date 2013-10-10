//
// Application.java
// Project mikalc3
//
// Created by gg on Sun Jun 23 2002
//

import com.webobjects.foundation.*;
import com.webobjects.appserver.*;
import com.webobjects.eocontrol.*;

public class Application extends WOApplication 
{
    protected String tabHeadColor = "#116611";
        
    public static void main(String argv[]) {
        WOApplication.main(argv, Application.class);
    }

    public Application() {
        super();
        System.out.println("Welcome to " + this.name() + "!");
        
        /* ** Put your application initialization code here ** */
    }
}
