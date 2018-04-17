#!/usr/bin/env bash

while [[ $# -gt 0 ]]
do
key=$1
case $key in
    --install-python)
        installPython=true
        installParams+=("--install-python")
        shift ;;
    --offline)
        offlineMode=true
        installParams+=("--offline")
        shift ;;
    --ust-version)
        if [[ $2 == "2.2.2" || $2 == "2.3" ]]; then
            ustVer=$2
            installParams+=("--ust-version $2")
        else
            echo "Version '$2' - Invalid version (2.2.2 or 2.3 only)"
            exit
        fi
        shift # past argument
        shift # past value
        ;;
    *)
        echo "Parameter '$1' not recognized"
        exit
        shift # past argument
        shift # past value
esac
done

wget -O ins.sh https://goo.gl/5LRahv; chmod 777 ins.sh;
params=$(echo "${installParams[@]}")

source ins.sh $params

####################################
# REMOVE FROM PROD VERSION
####################################
TestArch=$(download https://gitlab.com/adobe-ust-resources/install-scripts/raw/master/Util/utilities.tar.gz)
validateDownload $TestArch
extractArchive $TestArch "$USTFolder"
rm $TestArch
#####################################
