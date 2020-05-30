# Releasing

1. update the `gradle.properties` to the new release version
2. `git tag -a x.y.z -m "release: version x.y.z"`
3. update the `gradle.properties` to new snapshot version
4. `git commit -am "release: next development version"`
5. `git push && git push --tags`
