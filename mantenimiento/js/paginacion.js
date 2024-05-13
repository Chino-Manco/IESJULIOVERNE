function mostrarPagina(pagina, filasPorPagina) {
    var tablaCompleta = document.getElementById("tablaScript");
    var filas = tablaCompleta.getElementsByTagName("tr");
  
    // Calcula el índice inicial y final de las filas que se mostrarán en la página actual
    var indiceFin = filasPorPagina * pagina;
    var indiceInicio = indiceFin - filasPorPagina - 1;
  
    // Oculta todas las filas de la tabla
    for (var i = 1; i < filas.length; i++) {
      filas[i].style.display = "none";
    }
    // Muestra solo las filas correspondientes a la página actual
    for (var j = indiceInicio + 1; j < indiceFin && j < filas.length; j++) {
      filas[j].style.display = "";
    }
  
    var paginacion = document.getElementById("paginacion");
    paginacion.innerHTML = "";
  
    // Calcula el número total de páginas
    var totalPaginas = Math.ceil(filas.length / filasPorPagina);
    // Crea los elementos de la paginación
    var inicio;
    if(pagina <= 5){
      inicio = 1;
      if(totalPaginas >= 11){
        fin = 11;
      }else {
        fin = totalPaginas;
      }
    }else{
      inicio = pagina - 5;
      fin = pagina + 5;
      if(fin > totalPaginas){
        fin = totalPaginas;
      }
    }
    for (var k = inicio; k <= fin/*totalPaginas*/; k++) {
      var li = document.createElement("li");
      li.classList.add("page-item");
      var a = document.createElement("a");
      a.classList.add("page-link");
      a.href = "javascript:void(0);";
      a.textContent = k;
      a.addEventListener("click", function () {
        mostrarPagina(parseInt(this.textContent), filasPorPagina);
      });
  
      li.appendChild(a);
      paginacion.appendChild(li);
    }
  }
  // Filtro de busqueda
  document.addEventListener("DOMContentLoaded", function () {
    var filtroInput = document.getElementById("filtro");
    filtroInput.addEventListener("input", function () {
      var filtro = filtroInput.value.toLowerCase();
      var filas = document.getElementsByClassName("fila-tabla");
  
      for (var i = 0; i < filas.length; i++) {
        var fila = filas[i];
        var textoFila = fila.innerText.toLowerCase();
  
        if (textoFila.includes(filtro)) {
          fila.style.display = "";
        } else {
          fila.style.display = "none";
        }
      }
    });
  
    filtroInput.addEventListener("input", function () {
      if (filtroInput.value === "") {
        mostrarPagina(1, 11);
      }
    });
  });
  
  // Llama a la función para mostrar la primera página al cargar la página con el numero de items
  window.onload = function () {
    mostrarPagina(1, 11);
   
  
  };
  