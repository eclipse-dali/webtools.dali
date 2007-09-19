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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnitResource;
import org.eclipse.jpt.core.internal.resource.java.JpaPlatform;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JpaCompilationUnitResourceTests extends AnnotationTestCase {
	
	public JpaCompilationUnitResourceTests(String name) {
		super(name);
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
	

	protected JpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}
	
	protected JpaCompilationUnitResource buildJpaCompilationUnit(ICompilationUnit testCompilationUnit) {
		JpaCompilationUnitResource compilationUnit = 
			new JpaCompilationUnitResource(testCompilationUnit, buildJpaPlatform());
		compilationUnit.updateFromJava(JDTTools.buildASTRoot(testCompilationUnit));
		return compilationUnit;
	}
	
	public void testPersistentTypes() throws Exception {
		ICompilationUnit compilationUnit = this.createTestCompilationUnit();
		JpaCompilationUnitResource jpaCompilationUnit = buildJpaCompilationUnit(compilationUnit);
		
		assertTrue(jpaCompilationUnit.getPersistentType().javaTypeMappingAnnotation() instanceof Entity);
	}

}
