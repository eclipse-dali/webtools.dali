
These JET scripts cause a problem when used alongside EGit:

The JET Builder translates these scripts into Java source code with,
seemingly, every resource change (e.g. annotated_entity.javajet generates the file
org.eclipse.jpt.jpa.ui.internal.wizards.entity.AnnotatedEntityTemplate.java).
If the generated Java source is different from the existing file (in particular,
if it has different line delimiters), JET replaces the file with a new file;
and EGit, appropriately, detects this as a change.
NB: There is no way to configure JET to NOT use the current OS line delimiters
when translating scripts (see bug 163256).

EGit/JGit does not fully support the core.autocrlf=true setting on Windows. As a
result, Windows workspaces must check out text files with Unix line delimiters
and also create new files with Unix line delimiters (see Windows > Preferences >
General > Workspace > New text file line delimiter).

As a result, if the Java source files are checked into the repository with
Windows line delimiters (since that is how they would be generated on Windows);
whenever they are checked out on Unix, the files are
immediately regenerated with Unix line delimiters and marked as "changed".
And vice-versa.


There are several possible solutions to this problem:

1. JET could respect the "New text file line delimiter" preference. As a result
JET would not detect any differences from the files checked out from the Git
repository with Unix line delimiters.
See bug 163256.

2. Since they are derived files, the generated Java source files could be added
to the .gitignore file. But JET is not easily added to a PDE build; so the Java
source files could not be generated at build time (i.e. not checked out from the
repository by developers or the build).
See bug 210447.

3. EGit could fully support core.autocrlf=true. As a result, the Java source
files would be checked out of the repository with the appropriate platform-
specific line delimiters. Since JET would use those same line delimiters, it
would not detect and changes.

Until at least one of these solutions is in place, the JET Builder is disabled.
It must be manually enabled whenever the templates must be changed. [Since it
is actively being worked on, as of Oct 2012, option 3 is the most likely to
happen.]
