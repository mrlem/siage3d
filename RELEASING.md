# Releasing

1. update the `gradle.properties` to the new release version
2. `git commit -am "release: prepare for release"`
3. `git tag -a x.y.z -m "release: version x.y.z"`
4. update the `gradle.properties` to new snapshot version
5. `git commit -am "release: next development version"`
6. `git push && git push --tags`
