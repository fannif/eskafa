# Ohtu-projekti

#### Product backlog
[Product Backlog](https://docs.google.com/spreadsheets/d/1x9YFq1DHGwN0qCpNWJFqQOZS0CGin8dj2chu-N1ArWo/edit?usp=gmail_thread&ts=5dd2977a)

#### Sprint Backlog
[Sprint Backlog](https://docs.google.com/spreadsheets/d/1OqxUPbk6urff_mOtHHC_oDDHmhOTyWZxNcCAq9ybvWM/edit?usp=sharing)

#### Työmääräkirjanpito ja Burndown chart

[Menneet sprintit](https://github.com/fannif/eskafa/blob/master/documentation/kirjanpito.md)

[Sprintti 3](https://docs.google.com/spreadsheets/d/1Hvs-0SekRi4vSxOb8fyAxVITSERtZ6C_UGIzY706q-Y/edit?usp=sharing)


#### CircleCI
[![CircleCI](https://circleci.com/gh/fannif/eskafa.svg?style=svg)](https://circleci.com/gh/fannif/eskafa)

#### Codeclimate
[![Maintainability](https://api.codeclimate.com/v1/badges/a99a88d28ad37a79dbf6/maintainability)](https://codeclimate.com/github/fannif/eskafa)

#### Codecov
[![codecov](https://codecov.io/gh/fannif/eskafa/branch/master/graph/badge.svg)](https://codecov.io/gh/fannif/eskafa)

#### Licence
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Definition of done
Hyväksymiskriteerit on täytetty. Suuria bugeja ei löydy. Yksikkötestejä on tehty kattavasti. Myös Cucumber-testejä on jonkin verran. Kaikki toteutettavat toiminnallisuudet ovat käytettävissä ilman, että ohjelma kaatuu. Toiminnallisuus on sen verran selkeää, että käyttäjän on helppo käyttää ohjelmaa omatoimisesti.

## Ohjelman asennus- ja käyttöohje

### Asennus ja käynnistys

Lataa ohjelman viimeisin versio jar-tiedostona (release 2.0, Java 11) [täältä](https://github.com/fannif/eskafa/releases/download/v2.0/recommendations_java11.jar). (Version Java 8:lle löydät [täältä](https://github.com/fannif/eskafa/releases/download/v2.0/recommendations_java8.jar)). Ohjelma ei vaadi asennusta eli jar-tiedoston 
voi ajaa suoraan komentoriviltä. Tätä varten hakemistossa, johon jar-tiedosto on tallennettu, täytyy suorittaa alla oleva 
komento, joka käynnistää sovelluksen: 

`java -jar recommendations_java11.jar` (Java 11)

`java -jar recommendations_java8.jar` (Java 8)

Ensimmäisellä käynnistyskerralla ohjelma luo recommendations.db-nimisen tiedoston projektin juurikansioon
lukuvinkkien tallentamista varten. Ohjelman oikea toiminta edellyttää, että koneelle on asennettu Java.   

### [Lukuvinkkikirjaston käyttö](https://github.com/fannif/eskafa/blob/master/kayttoohje.md)
  


