
// general.js
// contains the general variables as well as the general functions for the BDF

var isNS4 =(navigator.appName.indexOf("Netscape")>-1) && (navigator.appVersion.indexOf("4") > -1);

function roll(imageName, newImage) 
{
	eval("document.images."+imageName+".src="+newImage+".src;");
}

/*
function overTab( tabToRoll )
{
	eval( tabToRoll + ".background=tabOver.src;" );
}
			
function outTab( tabToRoll )
{
	eval( tabToRoll + ".background=tabOut.src;" );
}
*/


/**
 * rolls over an extendible tab arrow
 */
 
function rollArrowOver( pIdDiv, pIdArrow )
{
    if (isNS4) return true;
    
    myDiv = document.getElementById( pIdDiv );
    myArrow = document.getElementById( pIdArrow );
    
    if ( myDiv.style.visibility == "hidden" ) 
    {
        myArrow.src = extendibleTabArrowRightOver.src;
    } else {
        myArrow.src = extendibleTabArrowDownOver.src;
    }	
}


/**
 * rolls out an extendible tab arrow
 */

function rollArrowOut( pIdDiv, pIdArrow )
{
    if (isNS4) return true;
    
    myDiv = document.getElementById( pIdDiv );
    myArrow = document.getElementById( pIdArrow );
    
    if ( myDiv.style.visibility == "hidden" ) {
        myArrow.src = extendibleTabArrowRight.src;
    } else {
        myArrow.src = extendibleTabArrowDown.src;
    }
}


/**
 * rolls a subpart arrow
 *
 * @param pMouseState String "out" or "over"
 */
/*
function rollSubpartArrow( pMouseState, pIdSubpart )
{
  subpart = document.getElementById(pIdSubpart);
  arrow = document.getElementById("subpartArrow_" + pIdSubpart);
  
  if (subpart.style.visibility == "hidden")
  {
    if (pMouseState == "out")
      arrow.src = smallRightArrowOut.src;
    else
      arrow.src = smallRightArrowOver.src;
  }
  else
  {
    if (pMouseState == "out")
      arrow.src = smallDownArrowOut.src;
    else
      arrow.src = smallDownArrowOver.src;
  }
}
*/


/**
 * changeDivVisibility switches the visibility of a div
 *
 * @param pIdDiv the id of the div whose visibility must be switched
 * @param pIdArrow the id of the arrow to switch
 */

function changeDivVisibility( pIdDiv, pIdArrow )
{
  myDiv = document.getElementById( pIdDiv );
  myArrow   = document.getElementById( pIdArrow );
  
  if (isNS4) return true;
  
  if ( myDiv.style.visibility == 'hidden' )
  {
    myDiv.style.position = 'relative';
    myDiv.style.visibility = 'visible';
    myArrow.src = extendibleTabArrowDown.src;
  }
  else
  {
    myDiv.style.visibility = "hidden";
    myDiv.style.position = "absolute";
    myArrow.src = extendibleTabArrowRight.src;
  }
  
  // because of IE which doesn't refresh the page
  myDiv.style.width = "100%";
}


//	changePartVisibility is a function that switches the visibility of a part
//	part id of the part whose visibility is to be changed
function changePartVisibility(part) 
{
	thePart = document.getElementById(part);
	theArrow = document.getElementById("arrow" + part);
	if (isNS4) return true;
	
	if (thePart.style.visibility == "hidden") {
		thePart.style.position = "relative"; 
		thePart.style.visibility = "visible";
		theArrow.src = downArrowOver.src;
		
        // because of IE which doesn't refresh the page
  	thePart.style.width = "100%";
	}
	else
	{
		thePart.style.visibility = "hidden";
		thePart.style.position = "absolute";
		theArrow.src = rightArrowOver.src;
	}
}

/*
//	initMakePartInvisible is to be called at the beginning to hide unwanted parts
//	elementsToHide : array containing the ids of all the elements to hide
function initMakePartInvisible(elementsToHide)
{
	var i = 0;
	if (isNS4) return true;
	for (i=0; i<elementsToHide.length; i++) {
		var currentElement = document.getElementById(elementsToHide[i]);
		currentElement.style.visibility = 'hidden';
		currentElement.style.position = 'absolute';
	}
}
*/