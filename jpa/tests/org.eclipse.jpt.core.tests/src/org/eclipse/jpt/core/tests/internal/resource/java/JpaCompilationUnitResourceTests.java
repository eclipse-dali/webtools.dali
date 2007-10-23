/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnitResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JpaCompilationUnitResourceTests extends AnnotationTestCase {
	
	public JpaCompilationUnitResourceTests(String name) {
		super(name);
	}

	@Override
	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}
	
	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	private ICompilationUnit createTestCompilationUnit() throws Exception {
		IType type = createTestEntity();
		return type.getCompilationUnit();
	}

	private IType createTestEntity() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");

		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
		});
	}
	
	protected JpaCompilationUnitResource getJpaCompilationUnitResource(ICompilationUnit testCompilationUnit) throws CoreException {
		IJpaProject jpaProject = ((TestJpaProject) this.javaProject).getJpaProject();
		IJpaFile jpaFile = jpaProject.jpaFile((IFile) testCompilationUnit.getResource());
		JavaResourceModel javaResourceModel = (JavaResourceModel) jpaFile.getResourceModel();
		return javaResourceModel.getCompilationUnitResource();
	}
	
	public void testGetPersistentType() throws Exception {
		ICompilationUnit compilationUnit = this.createTestCompilationUnit();
		JpaCompilationUnitResource jpaCompilationUnit = getJpaCompilationUnitResource(compilationUnit);
		
		assertTrue(jpaCompilationUnit.getPersistentType().mappingAnnotation() instanceof Entity);
	}

}
