
budget () {
    CURRENT=$(pwd)
    cd ~/Budgeteer
    java -jar xmercerweiss-budgeteer-v1.1.jar
    cd $CURRENT
}
