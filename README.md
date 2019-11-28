# Coursesystem
## 3. modulopgave

Dette system bruges til at styre tilmelding til kurser.
Studerende kan ansøge om at komme på et kursus.
Administrationsmedarbejdere kan godkende eller afvise studerende til kurser.
Undervisere kan oprette nye kurser.

Systemet taler sammen med et REST api over allerede eksisterende kurser og undervisere.


Systemet er lavet ved hjælp af Spring Boot og et MVC pattern.
Login er lavet ved hjælp af Spring Security.


Systemet kan kun køre lokalt. Det bruger en MySQL-database med en dedikeret bruger (se oplysninger om port, brugernavn og adgangskode i properties-filen). Første gang programmet kører, skal ddl-auto sættes til update for at skabe databasen ved hjælp af JPA.
