package org.eclipse.jpt.core.tests.internal.content.java.mappings;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.synch.SynchronizeClassesJob;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.ClassTools;

public abstract class JpaJavaTestCase extends AnnotationTestCase {

	public JpaJavaTestCase(String name) {
		super(name);
	}

	@Override
	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);
	}

	protected TestJpaProject jpaProject() {
		return (TestJpaProject) this.javaProject;
	}

	protected JavaPersistentType javaPersistentTypeNamed(String typeName) {
		for (IJpaFile jpaFile : this.jpaProject().getJpaProject().jpaFiles(JavaCore.JAVA_SOURCE_CONTENT_TYPE)) {
			JpaCompilationUnit cu = (JpaCompilationUnit) jpaFile.getContent();
			for (JavaPersistentType pt : cu.getTypes()) {
				if (pt.fullyQualifiedTypeName().equals(typeName)) {
					return pt;
				}
			}
		}
		throw new IllegalArgumentException("missing type: " + typeName);
	}

	protected Type typeNamed(String typeName) {
		return this.javaPersistentTypeNamed(typeName).getType();
	}

	protected void synchPersistenceXml() {
		SynchronizeClassesJob job = new SynchronizeClassesJob(this.jpaProject().getProject().getFile("src/META-INF/persistence.xml"));
		ClassTools.executeMethod(job, "run", IProgressMonitor.class, new NullProgressMonitor());
	}

}
