/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkMutableAnnotation;

@SuppressWarnings("nls")
public class MutableAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public MutableAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestMutable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.MUTABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Mutable");
			}
		});
	}
	
	private ICompilationUnit createTestMutableWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.MUTABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Mutable(value=true)");
			}
		});
	}
	

	
	public void testMutableAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestMutable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getAnnotation(EclipseLink.MUTABLE));
		
		attributeResource.removeAnnotation(EclipseLink.MUTABLE);		
		assertNull(attributeResource.getAnnotation(EclipseLink.MUTABLE));
		
		attributeResource.addAnnotation(EclipseLink.MUTABLE);
		assertNotNull(attributeResource.getAnnotation(EclipseLink.MUTABLE));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMutableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkMutableAnnotation mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLink.MUTABLE);
		assertEquals(Boolean.TRUE, mutableAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMutableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkMutableAnnotation mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLink.MUTABLE);
		assertEquals(Boolean.TRUE, mutableAnnotation.getValue());
		
		mutableAnnotation.setValue(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mutableAnnotation.getValue());
		
		assertSourceContains("@Mutable(value=false)", cu);
		
		mutableAnnotation.setValue(null);
		mutableAnnotation.setValue(Boolean.FALSE);
		assertSourceContains("@Mutable(false)", cu);
	}
	
	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestMutableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkMutableAnnotation mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLink.MUTABLE);
		assertEquals(Boolean.TRUE, mutableAnnotation.getValue());
		
		mutableAnnotation.setValue(null);
		assertNull(mutableAnnotation.getValue());
		
		assertSourceContains("@Mutable", cu);
		assertSourceDoesNotContain("value", cu);
	}

}
