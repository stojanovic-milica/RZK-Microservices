# School System - Microservices Project

Ovaj projekat predstavlja backend aplikaciju za školski sistem, razvijenu korišćenjem **Spring Boot** frameworka i arhitekture **mikroservisa**.

### Informacije o autoru
* **Ime i prezime:** Milica Stojanović
* **Broj indeksa:** 225/22

---

### Tehnologije i Arhitektura
Sistem se sastoji od nekoliko nezavisnih servisa:

* **Spring Cloud Netflix Eureka:** Omogućava Service discovery.
* **API Gateway:** Centralna tačka pristupa sistemu.
* **Mikroservisi:** Autonomne jedinice za korisnike, edukaciju, termine i plaćanje.
* **Docker & Docker Compose:** Omogućavaju laku portabilnost i pokretanje sistema.

---

### Docker Hub Images
Sve komponente su dostupne kao javne slike na Docker Hub-u:

* **Eureka Server:** [hub.docker.com/r/stojanovicmilica/eureka-server](https://hub.docker.com/r/stojanovicmilica/eureka-server)
* **Korisnik Service:** [hub.docker.com/r/stojanovicmilica/korisnik-service](https://hub.docker.com/r/stojanovicmilica/korisnik-service)
* **Edukacija Service:** [hub.docker.com/r/stojanovicmilica/edukacija-service](https://hub.docker.com/r/stojanovicmilica/edukacija-service)
* **Termin Service:** [hub.docker.com/r/stojanovicmilica/termin-service](https://hub.docker.com/r/stojanovicmilica/termin-service)
* **Placanje Service:** [hub.docker.com/r/stojanovicmilica/placanje-service](https://hub.docker.com/r/stojanovicmilica/placanje-service)
* **API Gateway:** [hub.docker.com/r/stojanovicmilica/api-gateway](https://hub.docker.com/r/stojanovicmilica/api-gateway)

* ---
