/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class TemporalTests extends JavaResourceModelTestCase {

	public TemporalTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTemporal() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TEMPORAL);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Temporal");
			}
		});
	}
	
	private ICompilationUnit createTestTemporalWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Temporal(TemporalType.DATE)");
			}
		});
	}

	public void testTemporal() throws Exception {
		ICompilationUnit cu = this.createTestTemporal();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getSupportingAnnotation(JPA.TEMPORAL);
		assertNotNull(temporal);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestTemporalWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getSupportingAnnotation(JPA.TEMPORAL);
		assertEquals(TemporalType.DATE, temporal.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestTemporal();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getSupportingAnnotation(JPA.TEMPORAL);

		temporal.setValue(TemporalType.TIME);
		
		assertSourceContains("@Temporal(TIME)", cu);
		
		temporal.setValue(null);
		
		assertSourceDoesNotContain("@Temporal", cu);
	}

}
