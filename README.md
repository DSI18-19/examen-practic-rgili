# Examen pràctic DSI. Curs 18-19

L'examen parteix del codi complert de la pràctica 4, on hi ha el següent:
* **notes**: el microservei que gestiona les notes. Quan se li envia una nota nova d'un client fa una crida síncrona al microservei 
de **users** per comprovar si existeix. Aquesta crida està protegida amb Hystrix de manera que si l'**user** no respon es guarda la nota a la BBDD
amb la marca de *userChecked=false*, tot esperant de poder fer la comprovació més endavant. Hi ha un *Scheduler* que cada 5 segons mira
a la BBDD si hi ha notes sense comprovar i si n'hi ha intenta connectar-se de forma síncrona amb el servei d'usuaris per fer la comprovació i 
actualitzar les notes.
* **users**: el mocroservei que gestiona usuaris. Quan se li demana que esborri un usuari ho fa de la seva BBDD i envia 
un missatge de forma assíncrona a **notes** per tal que les notes de l'usuari també s'esborrin. 
* **edgezuul**: és un *reverse proxy* que fa de punt d'entrada al sistema. Les crides que comencen per *notes* les 
redirigeix al microservei de **notes** i les que comencen per *users* les envia al de **users**
* **discovery**: és un microservei Eureka que fa de descobriment dels microserveis.
* **logger**: és un nou mòdul que hauràs de programar. En una arquitectura de microserveis assíncrons i/o concurrents com és aquest cas el 
comportament del sistema no està codificat enlloc concret i per això és difícil de saber què passa quan alguna cosa funciona
malament. Per tal de mitigar aquest problema es mira d'enregistrar tot el que passa al sistema. Doncs aquest és l'objectiu
del mòdul: fer un log a pantalla (per no complicar encara més les coses) quan els microserveis de *notes* o de *usuaris* 
fan alguna operació. Usarà la llibreria *log4j*

## Què haig de fer?
Completar el nou servei logger i modificar pertinentment els serveis de **notes** i **users**. Quan aquests serveis facin una
operació que modifica l'estat de l'aplicació (POST, DELETE) hauran d'enviar un missatge al logger per a que aquest faci
 el log pertinent. Ara es detalla com s'ha de fer. 
* El **logger** s'haurà de registrar al servei de discovery per tal de poder ser localitzat
* El servei de **notes** enviarà els missatges al **logger** a través de missatges assíncrons (RabbitMQ). Tots els missatges
es poden enviar per una sola cua o canal
    * Esborra totes les notes d'un usuari, envia "Notes delete: <username>"
    * Esborra una nota d'un usuari, envia "Notes delete: <username> <title>"
    * Modifica una nota d'un uuari, envia "Notes Update: <title>"
    * Crea una nota nova, envia "Notes create: <username> <title>"
* El servei de **users** enviarà els missatges al **logger** de forma síncrona (a través de crida REST)
    * Esborra un usuari, fa una petició GET (també podria ser POST) amb el següent paràmetre "Users delete \<username\>". Que és el 
    missage que haurà d'escriure el logger a pantalla
    * Crea un usuari, fa una petició GET (també podria ser POST) amb el següent paràmetre "Users create <username>". Que és el 
    missage que haurà d'escriure el logger a pantalla

Evidentment en aquest segon cas aquestes crides síncrones hauran d'estar protegides amb l'hystrix. En cas de que la comunicació falli
NO s'ha de fer res d'especial.

He posat alguns TODO's en el codi per si facilita una mica la feina

## Crides als microserveis

### Crear nota
* http://localhost:8080/notes
* **Action:** POST
* **Headers:** Content-Type=application/json
* **body:** 
```json
{
"title": "Examen DSI",
"content": "Aquest examen està xupat",
"owner": "castells"
}
```

### Esborrar Usuari
* http://localhost:8080/users/<username>
* **Action:** DELETE

### Crear Usuari
* http://localhost:8080/users
* **Action:** POST
* **Headers:** Content-Type=application/json
* **body:**
```json
{
	"username": "perez",
	"name": "Anna",
	"secondName": "Perez",
	"email": "Perez@mail.cat",
	"password": "perez"
}
```

### Llistar tots els usuaris
* http://localhost:8080/users
* **Action:** GET

### Llistar un usari
* http://localhost:8080/users/<username>
* **Action:** GET

### Llistar totes les notes
* http://localhost:8080/notes
* **Action:** GET

### Llistar les notes d'un usuari
* http://localhost:8080/notes/<username>
* **Action:** GET
