#!/bin/bash

# Set the paths to your Java programs
# java_executable="/usr/bin/java"
load_product_cost_program="/home/udayvarma/jobs/BatchJob3/src/main/java/loadcost/LoadProductCost.java"
push_cost_changes_program="/home/udayvarma/jobs/BatchJob3/src/main/java/pushcost/PushCostChanges.jar"

# Set the paths to your files and databases
cost_file="/path/to/CostFile.csv"
eprn_central_database="eprn_central_db"
store_database="store_db"

# Step 1: Load Product Cost file to EPRN Central database
echo "Loading product cost file to EPRN Central database..."
"$java_executable" -jar "$load_product_cost_program" "$cost_file" "$eprn_central_database"

# Step 2: Push cost changes to Store database using IBM MQ
echo "Pushing cost changes to the store database using IBM MQ..."
"$java_executable" -jar "$push_cost_changes_program" "$eprn_central_database" "$store_database"

echo "Cost changes have been successfully pushed to the store database."
