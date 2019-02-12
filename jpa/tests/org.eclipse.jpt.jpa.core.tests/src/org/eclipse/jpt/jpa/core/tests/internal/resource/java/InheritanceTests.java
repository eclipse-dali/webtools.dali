/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class InheritanceTests extends JpaJavaResourceModelTestCase {

	public InheritanceTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestInheritance() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.INHERITANCE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Inheritance");
			}
		});
	}
	
	private ICompilationUnit createTestInheritanceWithStrategy() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Inheritance(strategy = InheritanceType.JOINED)");
			}
		});
	}

	public void testInheritance() throws Exception {
		ICompilationUnit cu = this.createTestInheritance();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		InheritanceAnnotation inheritance = (InheritanceAnnotation) resourceType.getAnnotation(JPA.INHERITANCE);
		assertNotNull(inheritance);
	}
	
	public void testGetStrategy() throws Exception {
		ICompilationUnit cu = this.createTestInheritanceWithStrategy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		InheritanceAnnotation inheritance = (InheritanceAnnotation) resourceType.getAnnotation(JPA.INHERITANCE);
		assertEquals(InheritanceType.JOINED, inheritance.getStrategy());
	}
	
	public void testSetStrategy() throws Exception {
		ICompilationUnit cu = this.createTestInheritance();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		InheritanceAnnotation inheritance = (InheritanceAnnotation) resourceType.getAnnotation(JPA.INHERITANCE);
		inheritance.setStrategy(InheritanceType.TABLE_PER_CLASS);
		
		assertSourceContains("@Inheritance(strategy = TABLE_PER_CLASS)", cu);
		
		inheritance.setStrategy(null);
		
		assertSourceDoesNotContain("strategy", cu);
	}

}
