
document.getElementById("richiesta").disabled = false;
document.getElementById("richiesta").hidden = false;

document.getElementById("offerta").disabled = false;
document.getElementById("offerta").hidden = false;

document.getElementById("risultato").disabled = true;
document.getElementById("risultato").hidden = true;

var context = window.location.pathname;
var dataLetta = document.getElementById('q');
var options = {method : "GET"};

dataLetta.addEventListener('input', function (){
    const searchRegex = new RegExp('^(.){3,}$');
    var codeTotale = '';

    if (searchRegex.test(dataLetta.value)){

        document.getElementById("risultato").disabled = false;
        document.getElementById("risultato").hidden = false;

        document.getElementById("richiesta").disabled = true;
        document.getElementById("richiesta").hidden = true;

        document.getElementById("offerta").disabled = true;
        document.getElementById("offerta").hidden = true;


        var url = context+"item/search?q="+dataLetta.value;


        fetch(url,options)
            .then(function (response){

                return response.json();
            })
            .then(function (elements){

                for (const element of elements ){

                    var inCode = '<div class="card" style="width: 10rem;">\n' +
                        '                                <img src="'+context+'item/image/'+element.id+'" class="card-img-top" alt="...">\n' +
                        '                                <div class="card-body">\n' +
                        '                                    <h5 class="card-title">'+element.title+'</h5>\n' +
                        '                                    <p class="card-text">'+element.description+'</p>\n' +
                        '\n' +
                        '                                    <form method="post">\n' +
                        '                                        <input type="text" name="id" value="'+element.id+'" id="idric" hidden>\n' +
                        '                                        <input type="submit" value="Mostra" class="btn btn-outline-primary btn-sm"/>\n' +
                        '                                        </form>\n' +
                        '                                    <form method="post" action="'+context+'edita">\n' +
                        '                                        <input type="text" name="id" value="'+element.id+'" id="idsocio" hidden>\n' +
                        '                                        <input type="submit" value="Edit" class="btn btn-outline-primary btn-sm" sec:authorize="isAuthenticated()"/>\n' +
                        '                                    </form>\n' +
                        '                                </div>\n' +
                        '                            </div>';

                    codeTotale = codeTotale+inCode;

                }

                document.getElementById('resultDiv').innerHTML = codeTotale;
            })
    }else {
        document.getElementById("richiesta").disabled = false;
        document.getElementById("richiesta").hidden = false;

        document.getElementById("offerta").disabled = false;
        document.getElementById("offerta").hidden = false;

        document.getElementById("risultato").disabled = true;
        document.getElementById("risultato").hidden = true;
    }
})

dataLetta.addEventListener('keypress', function (){
    const searchRegex = new RegExp('^(.){3,}$');
    var codeTotale = '';

    if(event.key === "Enter") {
            document.getElementById("risultato").disabled = false;
            document.getElementById("risultato").hidden = false;

            document.getElementById("richiesta").disabled = true;
            document.getElementById("richiesta").hidden = true;

            document.getElementById("offerta").disabled = true;
            document.getElementById("offerta").hidden = true;


            var url = context + "item/search?q=" + dataLetta.value;


            fetch(url, options)
                .then(function (response) {

                    return response.json();
                })
                .then(function (elements) {

                    for (const element of elements) {

                        var inCode = '<div class="card" style="width: 10rem;">\n' +
                            '                                <img src="'+context+'item/image/' + element.id + '" class="card-img-top" alt="...">\n' +
                            '                                <div class="card-body">\n' +
                            '                                    <h5 class="card-title">' + element.title + '</h5>\n' +
                            '                                    <p class="card-text">' + element.description + '</p>\n' +
                            '\n' +
                            '                                    <form method="post">\n' +
                            '                                        <input type="text" name="id" value="' + element.id + '" id="idric" hidden>\n' +
                            '                                        <input type="submit" value="Mostra" class="btn btn-outline-primary btn-sm"/>\n' +
                            '                                        </form>\n' +
                            '                                    <form method="post" action='+context+'edita">\n' +
                            '                                        <input type="text" name="id" value="' + element.id + '" id="idsocio" hidden>\n' +
                            '                                        <input type="submit" value="Edit" class="btn btn-outline-primary btn-sm" sec:authorize="isAuthenticated()"/>\n' +
                            '                                    </form>\n' +
                            '                                </div>\n' +
                            '                            </div>';

                        codeTotale = codeTotale + inCode;

                    }

                    document.getElementById('resultDiv').innerHTML = codeTotale;
                })
        } else {
            document.getElementById("richiesta").disabled = false;
            document.getElementById("richiesta").hidden = false;

            document.getElementById("offerta").disabled = false;
            document.getElementById("offerta").hidden = false;

            document.getElementById("risultato").disabled = true;
            document.getElementById("risultato").hidden = true;
        }
})