function abrirPopUp(myId2, myId3)
{
	document.getElementById(myId2).classList.add('active');
	document.getElementById(myId3).classList.add('active');
}


function cerrarPopUp(myId2, myId3)
{
	document.getElementById(myId2).classList.remove('active');
	document.getElementById(myId3).classList.remove('active');
}


function addNodo(containerId)
{
    let container = document.getElementById(containerId);

    container.appendChild(document.createTextNode("Agregado nuevo coloborador"));
    
    let input = document.createElement("input");
    input.type = "text";
    input.placeholder = "Nombre"
    container.appendChild(input);
    
    container.appendChild(document.createElement("br"));
}

function deleteNodo(containerId)
{
	let container = document.getElementById(containerId);
    document.getElementById(containerId).removeChild(container.lastChild);
    document.getElementById(containerId).removeChild(container.lastChild);
    document.getElementById(containerId).removeChild(container.lastChild);
}



