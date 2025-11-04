
budget () {
    CURRENT=$(pwd)
    cd ~/Budgeteer
    java -jar xmercerweiss-budgeteer-v1.0.jar
    cd $CURRENT
}
