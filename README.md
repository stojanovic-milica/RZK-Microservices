# School System - Microservices Project

Ovaj projekat predstavlja backend aplikaciju za školski sistem, razvijenu korišćenjem **Spring Boot** frameworka i arhitekture **mikroservisa**.[cite: 1]

### 👤 Informacije o autoru
* **Ime i prezime:** Milica Stojanović[cite: 1]
* **Broj indeksa:** 225/22[cite: 1]

---

### 🛠 Tehnologije i Arhitektura
Sistem se sastoji od nekoliko nezavisnih servisa:

* **Spring Cloud Netflix Eureka:** Omogućava Service discovery.[cite: 1]
* **API Gateway:** Centralna tačka pristupa sistemu.[cite: 1]
* **Mikroservisi:** Autonomne jedinice za korisnike, edukaciju, termine i plaćanje.[cite: 1]
* **Docker & Docker Compose:** Omogućavaju laku portabilnost i pokretanje sistema.[cite: 1]

---

### 🐳 Docker Hub Images
Sve komponente su dostupne kao javne slike na Docker Hub-u:

* **Eureka Server:** [hub.docker.com/r/stojanovicmilica/eureka-server](https://hub.docker.com/r/stojanovicmilica/eureka-server)[cite: 1]
* **Korisnik Service:** [hub.docker.com/r/stojanovicmilica/korisnik-service](https://hub.docker.com/r/stojanovicmilica/korisnik-service)[cite: 1]
* **Edukacija Service:** [hub.docker.com/r/stojanovicmilica/edukacija-service](https://hub.docker.com/r/stojanovicmilica/edukacija-service)[cite: 1]
* **Termin Service:** [hub.docker.com/r/stojanovicmilica/termin-service](https://hub.docker.com/r/stojanovicmilica/termin-service)[cite: 1]
* **Placanje Service:** [hub.docker.com/r/stojanovicmilica/placanje-service](https://hub.docker.com/r/stojanovicmilica/placanje-service)[cite: 1]
* **API Gateway:** [hub.docker.com/r/stojanovicmilica/api-gateway](https://hub.docker.com/r/stojanovicmilica/api-gateway)[cite: 1]

---

### 🚀 Pokretanje
U korenskom direktorijumu se nalazi `docker-compose.yml` fajl. Sistem se pokreće komandom:
`docker-compose up`[cite: 1]
