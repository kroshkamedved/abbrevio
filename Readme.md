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
DB_USERNAME="<your_db_username>"
DB_PASSWORD="<your_db_password>"
DB_NAME="<your_db_name>"
DB_DATA_PATH="<your_db_data_path>"
DB_HOST_PORT="<your_db_host_port>"
DB_DOCKER_INTERNAL_PORT="<your_db_docker_internal_port>"
POSTGRESQL_DB_LINK="<your_postgresql_db_link>"
AbbrevioHostPort="1991"
```

## Starting the Service
To start the service, execute the following command:
```bash
docker-compose up
```

