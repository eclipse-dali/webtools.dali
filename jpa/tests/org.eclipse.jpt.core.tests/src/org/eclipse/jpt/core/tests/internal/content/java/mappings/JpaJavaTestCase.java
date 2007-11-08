/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.content.java.mappings;

import java.util.Iterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.synch.SynchronizeClassesJob;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestPlatformProject;
import org.eclipse.jpt.utility.internal.ClassTools;

public abstract class JpaJavaTestCase extends AnnotationTestCase {

	public JpaJavaTestCase(String name) {
		super(name);
	}

	@Override
	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(TestPlatformProject.uniqueProjectName(projectName), autoBuild);
	}

	protected TestJpaProject jpaProject() {
		return (TestJpaProject) this.javaProject;
	}

	protected JavaPersistentType javaPersistentTypeNamed(String typeName) {
		for (Iterator<JavaPersistentType> stream = this.jpaProject().getJpaProject().javaPersistentTypes(); stream.hasNext(); ) {
			JavaPersistentType jpt = stream.next();
			if (jpt.fullyQualifiedTypeName().equals(typeName)) {
				return jpt;
			}
		}
		throw new IllegalArgumentException("missing type: " + typeName);
	}

	protected JavaPersistentAttribute javaPersistentAttributeNamed(String attributeName) {
		return this.javaPersistentAttributeNamed(attributeName, FULLY_QUALIFIED_TYPE_NAME);
	}

	protected JavaPersistentAttribute javaPersistentAttributeNamed(String attributeName, String typeName) {
		for (JavaPersistentAttribute attribute : this.javaPersistentTypeNamed(typeName).getAttributes()) {
			if (attribute.getName().equals(attributeName)) {
				return attribute;
			}
		}
		throw new IllegalArgumentException("missing attribute: " + typeName + "." + attributeName);
	}

	protected Type typeNamed(String typeName) {
		return this.javaPersistentTypeNamed(typeName).getType();
	}

	protected void synchPersistenceXml() {
		SynchronizeClassesJob job = new SynchronizeClassesJob(this.jpaProject().getProject().getFile("src/META-INF/persistence.xml"));
		ClassTools.executeMethod(job, "run", IProgressMonitor.class, new NullProgressMonitor());
	}

}
