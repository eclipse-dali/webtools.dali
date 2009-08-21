/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEmbeddableTests extends ContextModelTestCase
{
	
	private ICompilationUnit createTestEmbeddable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable");
			}
		});
	}


	public JavaEmbeddableTests(String name) {
		super(name);
	}
	
	public void testMorphToEntity() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Entity);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToMappedSuperclass() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaPersistentType().setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof MappedSuperclass);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME));
	}
	
	public void testEmbeddable() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(getJavaPersistentType().getMapping() instanceof Embeddable);
	}
	
	public void testOverridableAttributeNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = embeddable.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = embeddable.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();

		assertFalse(embeddable.tableNameIsInvalid(FULLY_QUALIFIED_TYPE_NAME));
		assertFalse(embeddable.tableNameIsInvalid("FOO"));
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();
		assertTrue(embeddable.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(embeddable.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}


	public void testAssociatedTables() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();

		assertFalse(embeddable.associatedTables().hasNext());
	}

	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();

		assertFalse(embeddable.associatedTablesIncludingInherited().hasNext());
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();

		assertFalse(embeddable.associatedTableNamesIncludingInherited().hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = embeddable.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	//TODO need to create a subclass mappedSuperclass and test this
	public void testAllOverridableAssociationNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Embeddable embeddable = (Embeddable) getJavaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = embeddable.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}

}
