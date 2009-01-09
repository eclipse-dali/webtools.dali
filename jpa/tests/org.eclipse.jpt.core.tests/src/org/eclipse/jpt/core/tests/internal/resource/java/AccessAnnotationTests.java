/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class AccessAnnotationTests extends Java2_0ResourceModelTestCase {
	
	public AccessAnnotationTests(String name) {
		super(name);
	}

	//we can remove the creation of these once we have a jpa2.0.jar to use in our builds for testing
	private void createAccessTypeEnum() throws Exception {
		this.createEnumAndMembers("AccessType", "FIELD, PROPERTY;");	
	}
	
	private void createAccessAnnotation() throws Exception {
		this.createAnnotationAndMembers("Access", "AccessType value();");
		createAccessTypeEnum();
	}

	private ICompilationUnit createTestAccessOnType() throws Exception {
		createAccessAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ACCESS, JPA.ACCESS_TYPE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}

	private ICompilationUnit createTestAccessOnField() throws Exception {
		createAccessAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ACCESS, JPA.ACCESS_TYPE);
			}
		
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.FIELD)");
			}
		});
	}

	private ICompilationUnit createTestAccessOnProperty() throws Exception {
		createAccessAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ACCESS, JPA.ACCESS_TYPE);
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}

	public void testGetAccessOnType() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		AccessAnnotation access = (AccessAnnotation) typeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.PROPERTY, access.getValue());
	}

	public void testSetAccessOnType() throws Exception {
		createAccessAnnotation();
		ICompilationUnit cu = this.createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		AccessAnnotation access = (AccessAnnotation) typeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNull(access);
		
		typeResource.addSupportingAnnotation(JPA.ACCESS);
		assertSourceContains("@Access", cu);

		access = (AccessAnnotation) typeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertNull(typeResource.getSupportingAnnotation(JPA.ACCESS));
		assertSourceDoesNotContain("@Access", cu);
	}
	
	public void testGetAccessOnField() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableFields().next();
		
		AccessAnnotation access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.FIELD, access.getValue());
	}

	public void testSetAccessOnField() throws Exception {
		createAccessAnnotation();
		ICompilationUnit cu = this.createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableFields().next();
		
		AccessAnnotation access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNull(access);
		
		attributeResource.addSupportingAnnotation(JPA.ACCESS);
		assertSourceContains("@Access", cu);

		access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.ACCESS));
		assertSourceDoesNotContain("@Access", cu);
	}
	
	public void testGetAccessOnProperty() throws Exception {
		ICompilationUnit cu = this.createTestAccessOnProperty();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableProperties().next();
		
		AccessAnnotation access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		assertEquals(AccessType.PROPERTY, access.getValue());
	}

	public void testSetAccessOnProperty() throws Exception {
		createAccessAnnotation();
		ICompilationUnit cu = this.createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableProperties().next();
		
		AccessAnnotation access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNull(access);
		
		attributeResource.addSupportingAnnotation(JPA.ACCESS);
		assertSourceContains("@Access", cu);

		access = (AccessAnnotation) attributeResource.getSupportingAnnotation(JPA.ACCESS);
		assertNotNull(access);
		
		access.setValue(AccessType.FIELD);
		assertEquals(AccessType.FIELD, access.getValue());		
		assertSourceContains("@Access(FIELD)", cu);
		
		access.setValue(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, access.getValue());
		assertSourceContains("@Access(PROPERTY)", cu);
		
		access.setValue(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.ACCESS));
		assertSourceDoesNotContain("@Access", cu);
	}
}
