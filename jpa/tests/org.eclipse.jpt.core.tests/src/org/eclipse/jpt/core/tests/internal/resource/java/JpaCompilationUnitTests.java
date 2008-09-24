/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JpaCompilationUnitTests extends AnnotationTestCase {
	
	public JpaCompilationUnitTests(String name) {
		super(name);
	}

	@Override
	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	protected JpaCompilationUnit getJpaCompilationUnitResource(ICompilationUnit testCompilationUnit) throws CoreException {
		JpaProject jpaProject = ((TestJpaProject) this.javaProject).getJpaProject();
		JpaFile jpaFile = jpaProject.getJpaFile((IFile) testCompilationUnit.getResource());
		JavaResourceModel javaResourceModel = (JavaResourceModel) jpaFile.getResourceModel();
		return javaResourceModel.getJpaCompilationUnit();
	}
	
	public void testGetPersistentType() throws Exception {
		ICompilationUnit compilationUnit = this.createTestEntity();
		JpaCompilationUnit jpaCompilationUnit = getJpaCompilationUnitResource(compilationUnit);
		
		assertTrue(jpaCompilationUnit.getPersistentType().getMappingAnnotation() instanceof EntityAnnotation);
	}

}
