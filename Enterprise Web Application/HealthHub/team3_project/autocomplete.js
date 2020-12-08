var req;
var isIE;
var searchId;
var completeTable;
var autoRow;

function init() {
	//window.alert("inside init()");
    searchId = document.getElementById("searchId");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
}

function doCompletion() {
	//window.alert("inside doCompletion());
    var url = "autocomplete?action=complete&searchId=" + escape(searchId.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function initRequest() {
	//window.alert("inside initRequest());
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    clearTable();

    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages(req.responseXML);
        }
    }
}

function appendProduct(providerName, providerId) {

    var row;
    var cell;
    var linkElement;
    
    if (isIE) {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell = row.insertCell(0);
    } else {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTable.appendChild(row);
    }
	
	console.log("inside appendProduct");
	console.log(providerName);
	console.log(providerId);
	
    cell.className = "popupCell";
    cell.setAttribute("style", "background-color:darkslategray");

    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "autocomplete?action=lookup&searchId=" + providerId);
    linkElement.setAttribute("style", "text-decoration:none; font-size:11px;");
    linkElement.appendChild(document.createTextNode(providerName));
    cell.appendChild(linkElement);
}

function clearTable() {
    if (completeTable.getElementsByTagName("tr").length > 0) {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
}


function parseMessages(responseXML) {
    
    // no matches returned
    if (responseXML == null) {
        return false;
    } else {
		console.log("inside parseMessages");
		
        var providers = responseXML.getElementsByTagName("providers")[0];

		console.log(providers);

        if (providers.childNodes.length > 0) {
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");
    
            for (loop = 0; loop < providers.childNodes.length; loop++) {
                var provider = providers.childNodes[loop];
                var providerName = provider.getElementsByTagName("providerName")[0];
                var providerId = provider.getElementsByTagName("id")[0];
                appendProduct(providerName.childNodes[0].nodeValue,
                    providerId.childNodes[0].nodeValue);
            }
        }
    }
}