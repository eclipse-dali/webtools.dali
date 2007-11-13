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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.TemporalType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TemporalTests extends JavaResourceModelTestCase {

	public TemporalTests(String name) {
		super(name);
	}

	private IType createTestTemporal() throws Exception {
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");
		this.createEnumAndMembers("TemporalType", "DATE, TIME, TIMESTAMP");
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
	
	private IType createTestTemporalWithValue() throws Exception {
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");
		this.createEnumAndMembers("TemporalType", "DATE, TIME, TIMESTAMP");
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
		IType testType = this.createTestTemporal();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);
		assertNotNull(temporal);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestTemporalWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);
		assertEquals(TemporalType.DATE, temporal.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestTemporal();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();

		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);

		temporal.setValue(TemporalType.TIME);
		
		assertSourceContains("@Temporal(TIME)");
		
		temporal.setValue(null);
		
		assertSourceDoesNotContain("@Temporal");
	}

}
