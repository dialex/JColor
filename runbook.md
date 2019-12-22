## New release

- On GitHub,
  - Create PR
  - Wait for CI results
  - Merge PR
- Locally,
  - Checkout master
  - Edit `pom.xml`
    - Breaking changes, increase major (`X.*.*`)
    - Upgrading dependencies, increase minor (`*.Y.*`)
    - Fixing issues, increase patch (`*.*.Z`)
  - Run `mvn release:clean release:prepare`
  - Run `mvn release:perform`
  - Run `git push`
- On GitHub,
  - Create new release
  - Select tag that created by maven
  - Describe changes

