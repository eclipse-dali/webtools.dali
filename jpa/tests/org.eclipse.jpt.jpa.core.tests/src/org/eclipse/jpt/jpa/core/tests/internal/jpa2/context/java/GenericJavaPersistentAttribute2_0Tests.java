/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaPersistentAttribute2_0Tests extends Generic2_0ContextModelTestCase
{
	private ICompilationUnit createTestEntityForDerivedId() throws Exception {
		return createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private ICompilationUnit createTestEntitySpecifiedAccessField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.BASIC, JPA.TRANSIENT, JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Access(AccessType.FIELD)");
			}
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("transient");
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}

	private ICompilationUnit createTestEntitySpecifiedAccessProperty() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.BASIC, JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Access(AccessType.PROPERTY)");
			}
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
				sb.append("@Access(AccessType.FIELD)");
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

		
	public GenericJavaPersistentAttribute2_0Tests(String name) {
		super(name);
	}
	
	public void testGetAccessField() throws Exception {
		createTestEntitySpecifiedAccessField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute namePersistentAttribute = getJavaPersistentType().getAttributeNamed("name");
		assertEquals(AccessType.FIELD, namePersistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, namePersistentAttribute.getDefaultAccess());
		assertEquals(null, namePersistentAttribute.getSpecifiedAccess());
		
		PersistentAttribute idPersistentAttribute = getJavaPersistentType().getAttributeNamed("id");
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, idPersistentAttribute.getMappingKey());
		assertEquals(AccessType.PROPERTY, idPersistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, idPersistentAttribute.getDefaultAccess());
		assertEquals(AccessType.PROPERTY, idPersistentAttribute.getSpecifiedAccess());
	}
	
	public void testGetAccessProperty() throws Exception {
		createTestEntitySpecifiedAccessProperty();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute namePersistentAttribute = getJavaPersistentType().getAttributeNamed("name");
		assertEquals(AccessType.FIELD, namePersistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, namePersistentAttribute.getDefaultAccess());
		assertEquals(AccessType.FIELD, namePersistentAttribute.getSpecifiedAccess());
		
		PersistentAttribute idPersistentAttribute = getJavaPersistentType().getAttributeNamed("id");
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, idPersistentAttribute.getMappingKey());
		assertEquals(AccessType.PROPERTY, idPersistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, idPersistentAttribute.getDefaultAccess());
		assertEquals(null, idPersistentAttribute.getSpecifiedAccess());
	}
	
	public void testDerivedIdMappingInitialization() throws Exception {
		createTestEntityForDerivedId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentAttribute id = getJavaPersistentType().getAttributeNamed("id");
		assertEquals(id.getMappingKey(), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		
		id.getResourcePersistentAttribute().addAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		id.getResourcePersistentAttribute().addAnnotation(JPA.ONE_TO_ONE);
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(id.getResourcePersistentAttribute().getAnnotation(JPA.ID));
		
		id.getResourcePersistentAttribute().removeAnnotation(JPA.ONE_TO_ONE);
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		id.getResourcePersistentAttribute().addAnnotation(JPA.MANY_TO_ONE);
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(id.getResourcePersistentAttribute().getAnnotation(JPA.ID));
		
		id.getResourcePersistentAttribute().removeAnnotation(JPA.MANY_TO_ONE);
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		id.getResourcePersistentAttribute().setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		getJpaProject().synchronizeContextModel();
		assertEquals(id.getMappingKey(), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);	
	}
}
