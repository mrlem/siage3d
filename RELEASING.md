# Releasing

1. update the `gradle.properties` to the new release version
2. `git commit -am "release: prepare for release"`
3. ./gradlew clean uploadArchives
4. on sonatype, promote the artifact
5. `git tag -a x.y.z -m "release: version x.y.z"`
6. update the `gradle.properties` to new snapshot version
7. `git commit -am "release: next development version"`
8. `git push && git push --tags`
