var context = document.querySelector('base').getAttribute('href');
var confrontaLINK = document.querySelector("#confronta");
var usernome = document.getElementById("user1");
var add_conf = document.getElementById("add_conf");

add_conf.addEventListener("click", function(){
    confronta();
})

function confronta() {
    var url = context + "/item/confronta?usr="+usernome.value;
    var options = {method : "GET"};
    alert("Entro");
    fetch(url, options).then(function(response){
        return response.json();
    }).then(function(items) {

        confrontaLINK.innerHTML = "Confronta ["+items.lenght+"]";
    });
}
