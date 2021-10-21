riccardi

PP4)
RESTAPI DOCUMENTATION
In questo caso bisognera andare su /items per aver la richiesta GET,
Sul server online sarebbe riccardi/items

a. Creazione elemento:
Avviene con una chiamata POST su /items come parametri si aspetta un JSON con title, description, author, caterory
Alla creazione ritornera il codice di stato 200 e ritorna anche il JSON appena creato (TODO)
Ad ogni item viene assegnato in automatico un id univoco per poter andare ad identificare dopo.
ex.

{
    "title":"PERICOLO",
    "description":"MASSIMO",
    "author":11,
    "category":"Home"
}


b. Lettura di un Item:
La lettura di un item avviene con la richiesta GET /items/ID_ITEM dove ID_ITEM sta per il numero o stringa id
che ha l'item voluto.
In caso di lettura corretta verra restituito il codice di stato 200 e l'elemento
In caso di input errato verra restitutito il codice di stato 400
In caso di item non presente verra restituito il codice di stato 404
ex.
{
    "title": "PERICOLO",
    "description": "MASSIMO",
    "author": {
        "username": "ADM_Max",
        "ruolo": {
            "role": "Administrator",
            "id": 7
        },
        "id": 11
    },
    "category": {
        "category": "Home",
        "id": 2
    },
    "id": 34
}

b. Lettura lista di Item:
La richiesta di tutti gli item avviene con GET /items dove verra restituita tutta la lista di item presenti
Il codice di stato sara 200
ex.
[{"title":"PERICOLO","description":"MASSIMO","author":{"username":"ADM_Max","ruolo":{"role":"Administrator","id":7},"id":11},"category":{"category":"Home","id":2},"id":34}]

c. Aggiornamento/Modifica completa di un item:
Per l'aggiornamento/modifica avviene con la richiesta PUT /items/ID_ITEM dove id item è l'identificativo dell'item da modificare
Si manda con postman il raw JSON dove ci sara dentro il nuovo item con cui sostituire quello vecchio
In caso di successo il codice di stato sara 200
In caso di item non trovato sara 404
in caso di bad request sara 400

d. Cancellazione di un item:
Per la cancellazione di un item avviene la richiesta DELETE /items/ID_ITEM con l'id da rimuovere
In caso l'id non esista codice di stato 404
In caso richiesta sbagliata codice di stato 400
In caso di successo codice di stato 200
ex.
{
	success: true
}


