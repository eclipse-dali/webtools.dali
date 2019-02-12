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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.EnumType;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class EnumeratedTests extends JpaJavaResourceModelTestCase {

	public EnumeratedTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEnumerated() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENUMERATED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Enumerated");
			}
		});
	}
	
	private ICompilationUnit createTestEnumeratedWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Enumerated(EnumType.ORDINAL)");
			}
		});
	}

	public void testEnumerated() throws Exception {
		ICompilationUnit cu = this.createTestEnumerated();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) resourceField.getAnnotation(JPA.ENUMERATED);
		assertNotNull(enumerated);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestEnumeratedWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) resourceField.getAnnotation(JPA.ENUMERATED);
		assertEquals(EnumType.ORDINAL, enumerated.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestEnumerated();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) resourceField.getAnnotation(JPA.ENUMERATED);

		enumerated.setValue(EnumType.STRING);
		
		assertSourceContains("@Enumerated(STRING)", cu);
		
		enumerated.setValue(null);
		
		assertSourceDoesNotContain("@Enumerated(", cu);
		assertSourceContains("@Enumerated", cu);
	}

}
