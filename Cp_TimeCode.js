/**
 * gets the global timecode value
 *
 * @param pFieldId String the timecode field id used to store the global
 */

function TC_getGlobal( pFieldId )
{
	var stringToEval = "var tcGlobal = G_TC_" + pFieldId + ";";
	eval( stringToEval );
	return tcGlobal;
}


/**
 * sets the global timecode value
 *
 * @param pSuffix String
 * @param pValue int the new time code frame value
 */

function TC_setGlobal( pFieldId, pValue )
{
    var stringToEval = "G_TC_" + pFieldId + " = pValue;";
    eval( stringToEval );
}


/**
 * filter function used to prevent users from entering unacceptable time code values
 * accepted values : int, ":", "+", "-"
 * max : 3 * ":", 1 * "+", 1 * "-"
 *
 * @param e event
 * @param pFieldId String the id of the calling text field
 */

function TC_onKeyPressHandler(e, pFieldId)
{
	var a = e.keyCode;
	var ok = false;
	var fieldValue = document.getElementById( pFieldId ).value;
	var is3DblPoint = new String( fieldValue ).match(/[0-9]*:[0-9]*:[0-9]*:[0-9]*/g);
	var isSign = ( new String( fieldValue ).indexOf("-") >=0 || new String( fieldValue ).indexOf( "+" ) >= 0 ) ? true : false ;

	if ( isSign && ( a == 43 || a == 45 ) || is3DblPoint  && a == 58 )
	{
		e.returnValue = false;
		return;
	}

	// replacing the point "." or coma "," by a double point ":"
	if ( !is3DblPoint && ( a == 46 || a == 44 ) )
	{
		e.keyCode = 58;
		return;
	}

	if ( (a != 43 && a != 45 && a != 58)
		&& (a != 48 && a != 49 && a != 50 && a != 51 && a != 52 && a != 53 && a != 54 && a != 55 && a != 56 && a != 57) )
	{
		e.returnValue = false;
		return;
	}
}

/**
 * main time code formating function
 * should be called by an "onblur" event
 *
 * @param pField String the id of the "calling" text field
 * @param pIsFixedFps boolean true if the fps is not dependant on a popup menu for instance
 * @param pTimeCodeType String the id of the select of hidden input containing the time code modulo OR the timecode modulo itself if fixed fps
 */

function TC_format( pFieldId, pIsFixedFps, pTimeCodeType )
{
    var i;
    var frames = 0;
    var fieldValue = document.getElementById(pFieldId).value;
    var strValue = new String(fieldValue);
    var modulo;
    var tcArray = new Array;

    // setting the modulo
    if ( pIsFixedFps )
    {
        modulo = parseFloat(pTimeCodeType);
    } else {
        modulo = parseFloat(document.getElementById(pTimeCodeType).value);
    }
            
    // the time code frame multipliers
    var factorArray = new Array;
    factorArray[0] = 1;
    factorArray[1] = modulo;
    factorArray[2] = modulo * 60;
    factorArray[3] = modulo * 60 * 60;

    // if there is nothing in the textfield, return
    if ( strValue == "" )
            return true;
    
    // if the user has entered only a number of images
    if (strValue.indexOf( ":" ) == -1 && strValue.indexOf( "+" ) == -1 && strValue.indexOf( "-" ) == -1 )
    {
            frames = 1 * fieldValue;
            ok = true;
    }
    else if ( strValue.indexOf( "+" ) >= 0 )
    {
            tcArray = strValue.split( "+" );
            frames = TC_getGlobal( pFieldId ) + 1 * tcArray[1];
            ok = true;
    }
    else if ( strValue.indexOf( "-" ) >= 0 )
    {
            tcArray = strValue.split( "-" );
            frames = TC_getGlobal( pFieldId ) - 1 * tcArray[1];
            ok = true;
    }
    // if there are ":" in the text field ie "real" timecode
    else if ( strValue.indexOf( ":" ) >= 0 )
    {
        tcArray = strValue.split( ":" );
        max = tcArray.length;

        for (i = tcArray.length - 1; i >= 0; i--)
        {
            factorIndex = max - i - 1;
            if (tcArray[i] == "")
            {
                tcArray[i] = 0;
            }
            
            // rounding the value to the upper int
            // (if 1 second in NTSC format, it represents 30 frames, not 29 which would be the case if you use the 29.97 framerate
            tmpFrames = factorArray[factorIndex] * 1 * tcArray[i];
            if ( tmpFrames > Math.floor( tmpFrames ) ) tmpFrames = Math.floor( tmpFrames ) + 1;
            
            frames += tmpFrames;
        }

        ok = true;
    }


    if ( ok )
    {
        //alert( "inside tc format : frames before setting them : " + frames );
        TC_setGlobal( pFieldId, frames );
        TC_display( pFieldId, modulo, frames );
    }
}


/**
 * formats the timecode and displays it
 *
 * @param pFieldId String the text field id to update
 * @param pModulo float the timecode modulo
 * @param pFrames int the number of frames
 */

function TC_display( pFieldId, pModulo, pFrames )
{
    //alert( "frames : " + pFrames + "\n" + "modulo : " + pModulo );
    var hours = 0;
    var minutes = 0;
    var seconds = 0;

    var factorArray = new Array;
    factorArray["second"] = pModulo;
    factorArray["minute"] = pModulo * 60;
    factorArray["hour"] = pModulo * 3600;

    hours = parseInt(pFrames / factorArray["hour"]);
    pFrames -= hours * factorArray["hour"];
    minutes = parseInt(pFrames / factorArray["minute"]);
    pFrames -= minutes * factorArray["minute"];
    seconds = parseInt(pFrames / factorArray["second"]);
    pFrames -= seconds * factorArray["second"];
    pFrames = parseInt(pFrames);

    if (hours < 10)
            hours = "0" + hours;
    if (minutes < 10)
            minutes = "0" + minutes;
    if (seconds < 10)
            seconds = "0" + seconds;
    if (pFrames < 10)
            pFrames = "0" + pFrames;
    //alert( "end of transformation frames : " + pFrames );
    
    document.getElementById( pFieldId ).value = hours + ":" + minutes + ":" + seconds + ":" + pFrames;
}

