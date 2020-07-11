# Runbook

How to do stuff

## Release a new version

- On GitHub,
  - Create PR
  - Wait for CI results
  - Merge PR
- Locally,
  - Checkout `main`
  - Edit `pom.xml`
    - Breaking changes, increase major (`X.*.*`)
    - Upgrading dependencies, increase minor (`*.Y.*`)
    - Fixing issues, increase patch (`*.*.Z`)
  - Run `mvn release:clean release:prepare`
  - (See [Generate Javadoc](#Generate-Javadoc)
  - Run `mvn release:perform`
  - Run `git push`
- On GitHub,
  - Create new release
  - Select tag that created by maven
  - Describe changes

## Generate Javadoc

- Locally,
  - If after you prepared a new release...
    - Copy the contents of folder `target/apidocs`
  - If not...
    - Run `mvn javadoc:javadoc` to generate docs
    - Copy the contents of folder `target/site/apidocs/`
  - Delete the contents of folder `docs`
  - Paste your clipboard inside that folder
  - Run `git add .; git commit -m "doc: update to version X.Y.Z"`
