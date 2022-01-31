var context = document.querySelector('base').getAttribute('href');
var confrontaLINK = document.querySelector("#confronta");
var usernome = document.querySelector("#user1");
var add_conf = document.querySelector("#add_conf");

add_conf.addEventListener("click", function(){
    search();
})

function search() {
    var url = context + "item/search?usr="+usernome.value;
    var options = {method : "GET"};
    alert("Entro");
    fetch(url, options).then(function(response){
        return response.json();
    }).then(function(items) {

        confrontaLINK.innerHTML = "Confronta ["+items.lenght+"]";
    });
}
