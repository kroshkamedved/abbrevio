# Abbrevio Microservice:: Handling CDXML Files for Molecule Abbreviation Recognition

## Description
The Abbrevio microservice is designed to handle CDXML files created by the ChemDraw service. It analyzes these files for the presence of unrecognized abbreviations. If such abbreviations are found in the reaction scheme and in the CDXML respectively, the service attempts to identify the abbreviation, searches for them in the linked database, and returns relevant information about the molecule if the database contains information about the abbreviation.

Furthermore, if unrecognized molecules are present in different places of a single or multi-step reaction, the service relates molecules to the corresponding reaction step and groups them as: reactant, reagent, or product.

The microservice has a single endpoint - `host:${AbbrevioHostPort}/api/v1/abbreviation/seek` which accepts XML and returns a JSON that describes the reaction or reaction scheme and contains reaction steps and the corresponding group mentioned above.

Examples of expected requests and responses can be found in the resources of the test subdirectories.

## Prerequisites
1. Docker
2. Creation of the `.env` files in the root directory of the project.

The `.env` file should contain the following environmental variables:
```bash
DB_USERNAME="<local_user>"
DB_PASSWORD="<password>"
DB_NAME="<abbrevio_db>"
DB_DATA_PATH="<${HOME}/dbDateFromDocker>"
DB_HOST_PORT="<5433>"
DB_DOCKER_INTERNAL_PORT="<5432>"
POSTGRESQL_DB_LINK="localhost:${DB_DOCKER_INTERNAL_PORT}/${DB_NAME}"
AbbrevioHostPort="1991"
REQUEST_ORIGIN="REQUEST_ORIGIN_URL"
```

## Starting the Service
To start the service, execute the following command:
```bash
docker-compose up
```

