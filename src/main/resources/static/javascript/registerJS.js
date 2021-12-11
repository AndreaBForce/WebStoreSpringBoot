
const name=document.getElementById('nome');
name.addEventListener('change',validName);

const cognome=document.getElementById('surname');
cognome.addEventListener('change',validSurname);

const username=document.getElementById('username');
username.addEventListener('change',validUsername);

const password=document.getElementById('password');
password.addEventListener('change',validPassword);

const passwordconferma=document.getElementById('passwordconfirm');
passwordconferma.addEventListener('change',validPasswordConfirm);

document.getElementById("creabottone").disabled = true;

var flagN = false;
var flagS = false;
var flagU = false;
var flagP = false;
var flagC = false;

function validName(event) {
    const name=document.getElementById("nome");

    if(/^[A-Za-z]+$/.test(name.value)){
        name.classList.add("is-valid");
        name.classList.remove("is-invalid");
        flagN = true;
        checkFlags(event);
    }else{
        name.classList.add("is-invalid");
        name.classList.remove("is-valid");
        alert("Caratteri consentiti nome A-Z/ a-z");
        flagN = false;
        checkFlags(event);
    }
}

function validSurname(event) {
    const surname=document.getElementById("surname");

    if(/^[A-Za-z]+$/.test(name.value)){
        surname.classList.add("is-valid");
        surname.classList.remove("is-invalid");
        flagS = true;
        checkFlags(event);
    }else{
        surname.classList.add("is-invalid");
        surname.classList.remove("is-valid");
        alert("Caratteri consentiti cognome A-Z/ a-z");
        flagS = false;
        checkFlags(event);
    }
}

function validUsername(event) {
    const username=document.getElementById("username");

    if(/^[A-Za-z0-9_]+$/.test(username.value)){
        username.classList.add("is-valid");
        username.classList.remove("is-invalid");
        flagU = true;
        checkFlags(event);
    }else{
        username.classList.add("is-invalid");
        username.classList.remove("is-valid");
        alert("Caratteri consentiti username A-Z/ a-z 0-9 _");
        flagU = false;
        checkFlags(event);
    }
}

function validPassword(event) {
    const password=document.getElementById("password");

    if(/^[A-Za-z0-9_]+$/.test(password.value) && password.value.length>=8 && password.value.length<=15){
        password.classList.add("is-valid");
        password.classList.remove("is-invalid");
        flagP = true;
        checkFlags(event);
    }else{
        password.classList.add("is-invalid");
        password.classList.remove("is-valid");
        alert("Caratteri consentiti password A-Z/ a-z 0-9 _ \nLunghezza consentita 8-15 caratteri");
        flagP = false;
        checkFlags(event);
    }
}

function validPasswordConfirm(event) {
    const password=document.getElementById("password");
    const passwordconf=document.getElementById("passwordconfirm");

    if(passwordconf.value == password.value && password.value.length>=8 && password.value.length<=15){
        password.classList.add("is-valid");
        password.classList.remove("is-invalid");

        passwordconf.classList.add("is-valid");
        passwordconf.classList.remove("is-invalid");
        flagC = true;
        checkFlags(event);
    }else{
        password.classList.add("is-invalid");
        password.classList.remove("is-valid");

        passwordconf.classList.add("is-invalid");
        passwordconf.classList.remove("is-valid");
        alert("Password non corrispondono");
        flagC = false;
        checkFlags(event);
    }
}

function checkFlags(event) {

    if(flagN && flagS && flagU && flagP && flagC) {
        document.getElementById("creabottone").disabled = false;
        document.getElementById("creabottone").hidden = false;
    }else{
        document.getElementById("creabottone").disabled = true;
        document.getElementById("creabottone").hidden = true;
    }
}