/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class Generic2_0JavaPersistentAttributeTests extends Generic2_0ContextModelTestCase
{

	private ICompilationUnit createTestEntitySpecifiedAccessField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.BASIC, JPA.TRANSIENT, JPA.ACCESS, JPA.ACCESS_TYPE);
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.BASIC, JPA.ACCESS, JPA.ACCESS_TYPE);
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

		
	public Generic2_0JavaPersistentAttributeTests(String name) {
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
}
