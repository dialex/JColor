# Runbook

How to do stuff

## Release a new version

- On GitHub,
  - Create PR
  - Wait for CI results
  - Merge PR
- Locally,
  - Checkout `main`
  - Run `mvn release:clean release:prepare`
    - Breaking changes, increase major (`X.*.*`)
    - New features or upgraded dependencies, increase minor (`*.Y.*`)
    - Fixing issues, increase patch (`*.*.Z`)
  - (See [Generate Javadoc](#Generate-Javadoc)
  - Run `mvn release:perform`
  - Run `git push`
- On GitHub,
  - Create new release
  - Select tag created by maven
  - Describe changes

## Generate Javadoc

```sh
jenv shell 17
mvn javadoc:javadoc
rm -r docs
cp -R target/site/apidocs docs
git add .; git commit -m "doc: update to version X.Y.Z"
```

- Locally,
  - If after you prepared a new release...
    - Copy the contents of folder `target/apidocs`
  - If not...
    - Run `mvn javadoc:javadoc` to generate docs
    - Copy the contents of folder `target/site/apidocs/`
  - Delete the contents of folder `docs`
  - Paste your clipboard inside that folder
  - Run `git add .; git commit -m "doc: update to version X.Y.Z"`

## Update dependencies

- Check what is outdated: `mvn versions:display-dependency-updates`
- Update all of them: `mvn versions:use-latest-releases`
- Check that tests still pass: `mvn test`

## Generate GPG

- `gpg --gen-key`
- `gpg --list-keys`
- `gpg -ab README.md` (you can delete the generated file, it's just to test the key)
- `gpg --keyserver keyserver.ubuntu.com --send-keys <PUBKEY>`