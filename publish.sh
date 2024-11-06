set -o allexport

echo "Publishing..."

./gradlew :timelineviewv2:publishToMavenLocal
