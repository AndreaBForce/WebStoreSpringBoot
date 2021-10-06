riccardi

Serie 02) 
Il server va sulla /annunci/Item e per mandare le cose con POST utilizzo Postman
Acceta quindi da post man sia da Param, che da application/x-www-form-urlencoded che da Raw JSON
http://localhost:8080/annunci/Item per vedere la GET
Per poter modificare la /annunci/ bisogna andare nella configurazione e Run -> Edi Configuration -> Deployment -> Application Context e li cambi

PP2)
RESTAPI DOCUMENTATION
In questo caso bisognera andare su /items per aver la richiesta GET,
Sul server online sarebbe riccardi/items

a. Creazione elemento:
Avviene con una chiamata POST su /items come parametri si aspetta un JSON con title, description, author, 
Alla creazione ritornera il codice di stato 200
Ad ogni item viene assegnato in automatico un id univoco per poter andare ad identificare dopo.

b. Lettura di un Item:
La lettura di un item avviene con la richiesta GET /items/item/ID_ITEM dove ID_ITEM sta per il numero o stringa id
che ha l'item voluto.
Partono dallo 0 e salgono di uno in uno
In caso di lettura corretta verra restituito il codice di stato 200
In caso di input errato verra restitutito il codice di stato 400
In caso di item non presente verra restituito il codice di stato 404

b. Lettura lista di Item:
La richiesta di tutti gli item avviene con GET /items dove verra restituita tutta la lista di item presenti
Il codice di stato sara 200

c. Aggiornamento/Modifica completa di un item:
Per l'aggiornamento/modifica avviene con la richiesta PUT /items/item/ID_ITEM dove id item è l'identificativo dell'item da modificare
Si manda con postman il raw JSON dove ci sara dentro il nuovo item con cui sostituire quello vecchio
In caso di successo il codice di stato sara 200
In caso di item non trovato sara 404
in caso di bad request sara 400

d. Cancellazione di un item:
Per la cancellazione di un item avviene la richiesta DELETE /items/item/ID_ITEM con l'id da rimuovere
In caso l'id non esista codice di stato 404
In caso richiesta sbagliata codice di stato 400
In caso di successo codice di stato 200

e. Aggiornamento/Modifica parziale di un item utilizzando il metodo PATCH:
Per la modifica parziale si usa la richiesta PATCH /items/item/ID_ITEM dove id item è l'item da modificare
Si invia il Raw JSON con i/il campi/o da modificare
In caso di successo si avra codice 200
In caso di bad request il codice 400
In caso di item non trovato il codice 404

