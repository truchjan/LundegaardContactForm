# Contact form for Lundegaard

#### Poznámky

- dle domluvy jsem vypracoval pouze backend, níže je několik endpointů na rychlé otestování
- používám spring, hibernate a postgresql
- v application.properties je potřeba nastavit tyto proměnné na váš lokální postgres:
    - *spring.datasource.url*
    - *spring.datasource.username*
    - *spring.datasource.password*
- nebyl jsem si jistý návrhem modelu BE, pokud to bylo myšleno výrazně jinak, rád přepracuji
- model: Person(name, surname) - 1:N - Request(type, policy_number, text)
- vytvořil jsem maven wrapper pro snadné spuštění pomocí: *mvnw spring-boot:run*
- na mém počítači mi vše fungovalo, tak doufám, že bude i Vám

#### Endpointy (příklady)

- POST localhost:8080/person/create
    - Content-Type application/json
    - body: {"name":"Karel","surname":"Novák"}
- POST localhost:8080/person/{id Karla Nováka}/request/create
    - Content-Type application/json
    - body: {"requestType":"1","policyNumber":"152","requestText":"text"}
- GET localhost:8080/person/all
- GET localhost:8080/person/{id Karla Nováka}/request/all
- PUT localhost:8080/person/{id Karla Nováka}/request/update/{id requestu}
    - Content-Type application/json
    - body: {"requestType":"1","policyNumber":"152","requestText":"jiny text"}
- GET localhost:8080/person/{id Karla Nováka}/request/all
- DELETE localhost:8080/person/{id Karla Nováka}/request/delete/{id requestu}
- GET localhost:8080/person/{id Karla Nováka}/request/all

#### Co jsem nestihl za daný limit 1MD:

- ošetřit všechny mezní podmínky: například co se děje s requesty po smazání jejich majitele a dalších, na které bohužel nezbyl čas
- nedostal jsem se k pořádnému otestování aplikace, které je určitě nutné, napsal jsem pouze základní testy
- některé části by se daly generalizovat pro snadnější rozšiřitelnost systému,
    například repository by šel udělat jeden generický pomocí reflexe apod
- použití nástoroje pro statickou analýzu kódu(př sonarqube), který by určitě odhalil další problémy
- trochu mi tam blbo generování ids, chtělo by se na to podívat
- pořádně zalogovat jednotlivé události
- rád bych si více pohrál s ResponseEntity návratovou hodnotou pro jednotlivé CRUD operace
- cokoliv z těchto(nebo jiných) co by byla fatální chyba ovlivňující moje přijetí rád doplním
