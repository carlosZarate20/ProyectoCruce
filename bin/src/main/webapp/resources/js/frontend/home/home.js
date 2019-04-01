'use strict'

$(document).ready(function(){
    console.log("Se ejecuto el codigo");

$("#boton").click(function(){
        $.get("044-ejemplo-jquery-get-pagina-externa02.php", function(htmlexterno){
   $("#cargaexterna").html(htmlexterno);
    	});
});

});