


/**
 * returns the value of the key code global variable
 *
 * @param pSuffix String the suffix of the global var (supposed to be the id/name of the text field)
 */

function KC_getGlobal( pFieldId )
{
	var stringToEval = "var g_flag = G_KC_" + pFieldId + ";";
	eval(stringToEval);
	return(g_flag);
}


/**
 * updates the key code global variable
 *
 * @param pFieldId String the id of the calling text field
 * @param pValue int the value to be added or substracted
 * @param pSign String (+ ou - ou 0) respectively to add, substract pValue or init the global to pValue
 */

function KC_setGlobal( pFieldId, pValue, pSign )
{
	if ( pSign == '+' || pSign == '-' )
	{
		var stringToEval2 = "G_KC_" + pFieldId + " " + pSign + "= " + pValue + ";";
		eval(stringToEval2);
		return;
	}
	else if (pSign == 0)
	{
		var stringToEval2 = "G_KC_" + pFieldId + " = " + pValue + ";";
		eval(stringToEval2);
		return;
	}
}


/**
 * prevents the entry of chars that are not ints and allows one sign '+' or '-'
 *
 * @param e Event
 * @param pFieldId String the id of the calling text field
 */


function KC_onKeyPressHandler( e, pFieldId )
{
	var a 		= e.keyCode;
	var Plus 	= document.getElementById( pFieldId ).value.indexOf( '+', 0 );
	var Moins 	= document.getElementById( pFieldId ).value.indexOf( '-', 0 );
	
	// if there is already a '+' or '-' in the field, no more allowed
	/*
	if (Plus != -1 || Moins != -1)
		if (a != 32 && a != 48 && a != 49 && a != 50 && a != 51 && a != 52 && a != 53 && a != 54 && a != 55 && a != 56 && a != 57)
			e.returnValue = false;
	*/
	
	// else, allows '+' and '-' (ASCII 43 and 45)
	if (a != 43 && a != 45 && a != 32 && a != 48 && a != 49 && a != 50 && a != 51 && a != 52 && a != 53 && a != 54 && a != 55 && a != 56 && a != 57)
		e.returnValue = false;
}


/**
 * main formatting key code function
 * formats the KC into #### +##
 *
 * @param pName String the id of the calling field
 * @param pKeyCodeTpe String the id of the "select" or "hidden input" that contains the modulo value for the keycode OR if pIsFixedModulo is true, the modulo itself
 * @param pSuffix String the suffix of the key code global variable
 */

function KC_format( pFieldId, pIsFixedModulo, pKeyCodeType )
{
	// getting a reference and the value of the key code
	var kcField = document.getElementById( pFieldId );
	var str = kcField.value;
	
	// doesn't perform anything if the text field doesn't contain anything
	if (str == "")
		return;
	
	KC_basicFormat( pFieldId );		// checks if there is a '+' or a '-' in the text field, or adds +00 to it

	var Tab = getFormatedVal( pFieldId );	// getting an array containing the values before and after the sign
	var b;
        
        if ( pIsFixedModulo )
        {
            var modulo = pKeyCodeType;
        } else {
            var modulo = document.getElementById( pKeyCodeType ).value;
        }
	
        var apres;

	
	// if there is a '+' or '-' in the text field, something must be done (else doesn't do anything)
	if (Tab['Plus'] != -1 || Tab['Moins'] != -1)
	{
		if (Tab['Plus'] != -1)
		{
			sign = '+';
			apres = Tab['apPlus'];
		}
		if (Tab['Moins'] != -1)
		{
			sign = '-';
			apres = Tab['apMoins'];
		}
		
		if (KC_getGlobal( pFieldId ) != '' && ((formatspace(Tab['avPlus']) == '' && Tab['Plus'] != -1) || (formatspace(Tab['avMoins']) == '' && Tab['Moins'] != -1)))
		{
			KC_setGlobal( pFieldId, (formatspace(apres) * 1), sign);
		}
		else
		{
			tmp = KC_calcFrames(Tab, modulo);
			KC_setGlobal( pFieldId, tmp, 0 );
		}
		
		KC_calcul( pFieldId, modulo, sign );
	}
}


/**
 * displays a formated key code
 *
 * @param pName String the id of the calling text field
 * @param pModulo int
 * @param pSign String "+" or "-"
 * @param pSuffixe String le suffixe de la variable globale
 */

function KC_calcul( pFieldId, pModulo, sign )
{ 
	rest 	= getDivisionRest(KC_getGlobal( pFieldId ), pModulo);
	quot 	= getQuotient(KC_getGlobal( pFieldId ), pModulo);

	// formating the footage (before the '+')
	if (quot < 10)
		quot = "000" + quot;
	else if (quot < 100)
		quot = "00" + quot;
	else if (quot < 1000)
		quot = "0" + quot;

	// formating the value after the '+'
	if (rest < 10)
		rest = "0" + rest;

	document.getElementById( pFieldId ).value = (quot + ' +' + rest);
}


/**
 * performs a basic formating : if there is neither '+' nor '-', adds '+00' to the key code field
 *
 * @param pName String the id of the key code text field
 */

function KC_basicFormat( pFieldId )
{
	var kcField 		= document.getElementById( pFieldId );
	var kcFieldVal 	= kcField.value;
	
	var Plus 		= kcFieldVal.indexOf('+', 0);
	var Moins 	= kcFieldVal.indexOf('-', 0);
	
	if (Plus == -1 && Moins == -1)
		kcField.value = formatspace(kcFieldVal) + '+00';
}


/**
 * returns the rest of the division of x by y
 */

function getDivisionRest(x, y)
{
	return x % y;
}


/**
 * returns the quotient of the division of x by y
 */

function getQuotient(x, y)
{
	return Math.floor( x / y )
}


/**
 * calculation of the frame number from an associative array containing the 'apPlus' and 'avPlus' indexes
 *
 * @param pTab Array
 * @param pModulo int value of the key code modulo (nb of frames by footage)
 */

function KC_calcFrames( pTab, pModulo )
{
	if (pTab['Plus'] != -1)
	{
		return (formatspace(pTab['avPlus']) * pModulo) + (formatspace(pTab['apPlus']) * 1);
	}

	if (pTab['Moins'] != -1)
	{
		return (formatspace(pTab['avMoins']) * pModulo) - (formatspace(pTab['apMoins']) * 1);
	}
}


/**
 * removes the spaces from a string
 * 
 * @param pPreviousString the string to format
 */

function formatspace( pPreviousString )
{
	var myBlankRegExp = / /g;
	var tutu = new String(pPreviousString).replace(myBlankRegExp, "");
	return(tutu);
}


/**
 * returns an array containg the values before and after the sign
 * as well as the position of '+' and '-'
 *
 * @param pName String the id of the text field to consider
 */

function getFormatedVal( pFieldId )
{
	var Tab 			= new Array;
	var str 			= document.getElementById( pFieldId ).value;
	
	var Plus 			= str.indexOf('+', 0);
	Tab['Plus'] 	= Plus;
	var Moins 		= str.indexOf('-', 0);
	Tab['Moins']	= Moins;
	
	Tab['avPlus']  = str.substring(0, Plus);
	Tab['apPlus']  = str.substring(Plus + 1, str.length);
	Tab['avMoins'] = str.substring(0, Moins);
	Tab['apMoins'] = str.substring(Moins + 1, str.length);

	return (Tab);
}
