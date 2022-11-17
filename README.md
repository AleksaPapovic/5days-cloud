FRONT SAM DODAO U OKVIRU DRUGOG REPO-a link do njega je 
https://github.com/AleksaPapovic/5days-cloud-angular

# 5days-cloud
• nephodne stvari da se uradi build

SPRING CLOUD SUIT
JAVA JDK 11
POSTGRESSQL 14 i pgadmin, baza exchange database user:postgres password:postgres
NodeJS version 15+
Lombok

• Kako se radi build

build u SPRING CLOUD SUIT podesiti desni klik na pom.xml->RUN AS->Maven Build
otkucati clean install -X
dečekirati tests
pod Maven Dependencies->Lombok.jar
desni klik na upravo taj jar pa RUN AS->Java Application
naći lokaciju gde se nalazi SPRING CLOUD SUIT.exe

• Primer kako se aplikacija pokreće

#klijentski(frontend) interfejs pokretanje
otvoriti terminal u folderu exchang-front/exchange-angular
git clone https://github.com/AleksaPapovic/5days-cloud-angular
npm install
npm start
prikaz u pretraživaču je dostupan na linku localhost:4200

#serverski(backend) deo
git clone https://github.com/AleksaPapovic/5days-cloud
otvoriti SPRING CLOUD SUIT
ispratiti korake iz build dela
desni klik na ExchangeApplication pa RUN AS->Java Application

moguce je testirati iz Postman-a
linkovi su kao u tekstu zadatka pokrenuti na localhost:8080

• Listu korišćenih tehnologija sa kratkim opisom 

SPRING CLOUD SUIT
JAVA JDK 11
Angular
Postman
POSTGRESQL 14
NodeJS 15+, 
Lombok
