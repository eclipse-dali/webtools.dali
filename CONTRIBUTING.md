# Contributing to Eclipse Dali Java Persistence Tools

Thank you for your interest in this project.  Don't forget to sign your Eclipse Contributor Agreement before proposing any changes.

In addition to the changes you're proposing, you should be familiar with Git and using SSH keys with Git.

If you're seeing this repository somewhere other than git.eclipse.org, it's not the authoritative version used for our releases every quarter. Opening pull requests or submitting changes there will be of no use.

- Clone the repository using one of the URLs at the bottom of https://git.eclipse.org/c/dali/webtools.dali.git/
- Set up your Gerrit access, including a SSH public key, on https://git.eclipse.org/r/ .
- Take note of your Username as listed on https://git.eclipse.org/r/settings/ (when logged in).
- Add a `git remote` for gerrit using `ssh://{username}@git.eclipse.org:29418/dali/webtools.dali.git`
- Ready your changes
- You can also run the Maven build, supplying using at least Tycho 4.0.0 for the values in `-Dtycho-extras.version=${tychoVersion} -Dtycho.version=${tychoVersion}` and profile `bree-libs` with `-Pbree-libs`.
- Create a local "topic" branch and commit your changes.  Please start your commit messages first line with the word "bug" and the bug number, e.g. `Bug 12345` to refer to bug 12345, both to make it more understandable in the eventual git history and to allow Gerrit to automagically link it to the bug report.
- Push your changes to the Gerrit remote using refspec `HEAD:refs/for/master`
- You'll be given a URL like https://git.eclipse.org/r/c/dali/webtools.dali/+/163278 unique to your changes. Please go there and click on ADD REVIEWER, choosing the [project lead](https://projects.eclipse.org/projects/webtools.dali/who), so they get notified of your proposal.
- If you need to make more changes, find the generated Change-Id on your change's page (e.g. `Change-Id: Ibe9f831a614edfe4e637038c4de0c3a849efb329`), and make sure it is copied as the last line in your updated commit message. Then you can push your commit as before and it will have a history in Gerrit on the same page with the same conversations.

## Eclipse Development Process

This Eclipse Foundation open project is governed by the Eclipse Foundation
Development Process and operates under the terms of the Eclipse IP Policy.

* https://eclipse.org/projects/dev_process
* https://www.eclipse.org/org/documents/Eclipse_IP_Policy.pdf

## Eclipse Contributor Agreement

In order to be able to contribute to Eclipse Foundation projects you must
electronically sign the Eclipse Contributor Agreement (ECA).

* http://www.eclipse.org/legal/ECA.php

The ECA provides the Eclipse Foundation with a permanent record that you agree
that each of your contributions will comply with the commitments documented in
the Developer Certificate of Origin (DCO). Having an ECA on file associated with
the email address matching the "Author" field of your contribution's Git commits
fulfills the DCO's requirement that you sign-off on your contributions.

For more information, please see the Eclipse Committer Handbook:
https://www.eclipse.org/projects/handbook/#resources-commit

## Contact

Contact the project developers via the project's "dev" list.

* https://accounts.eclipse.org/mailing-list/dali-dev
