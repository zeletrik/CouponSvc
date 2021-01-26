# Coupon Service #

## Overview ##

Coupon service is a small web application (backend) for a specific ticket redeeming scenario. There can be selected X ticket in every day where every #th coupon sender wins. The application supports multiple territories with different conditions.

## Technology stack ##

- Java 11
- Maven
- Spring
- Postgres

## API ##

| Path       |      Method      | Path variable | Parameter                               | Request   | Response        |
|------------|:----------------:|--------------:|----------------------------------------:|----------:|----------------:|
| /redeem    |  Post            | n/a           |  n/a                                    |  JSON     | Winner flag     |
| /analytics |  Get             | country       |  startDate/endDate/pageNumber/pageSize  |  n/a      | List of winners |


## Database ##

The underlying database holds 3 table.

- The territory infos
- The redeemed ticket
- The actual status for every territory

A pre-defined database image could be created with the given sql script and Dockerfile inside the `/database` folder

## How to run ##

### With maven ###

- The `skipTests` flag indicates that the tests should not run
- There is no special profile so a simple `mvn spring-boot:run` would do the magic

## With docker ##

- The Docker image creation is wired in the maven build cycle with a profile `container`
- The image should expose port `8080`
- The `spring.datasource.url` value should be overriden

## Testing ##

The project uses `TestNg` with the following cosntraints:

- Class Ratio: 75%
- Method Ratio: 75%
- Branch Ratio: 75%
- Line Ratio: 75%

## Current limitations ##

- The acutal state table should be reseted everyday manually
