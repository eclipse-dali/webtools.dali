/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEmbeddableTests extends ContextModelTestCase
{

	private void createEmbeddableAnnotation() throws Exception {
		this.createAnnotationAndMembers("Embeddable", "");		
	}
	
	private IType createTestEmbeddable() throws Exception {
		createEmbeddableAnnotation();
	
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
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResource();
		return prm.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected IClassRef classRef() {
		return persistenceUnit().classRefs().next();
	}
	
	protected IJavaPersistentType javaPersistentType() {
		return classRef().getJavaPersistentType();
	}
	
	protected IEntity javaEntity() {
		return (IEntity) javaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}
	
	public void testEmbeddable() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(javaPersistentType().getMapping() instanceof IEmbeddable);
	}
	
	public void testOverridableAttributeNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = embeddable.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = embeddable.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();

		assertFalse(embeddable.tableNameIsInvalid(FULLY_QUALIFIED_TYPE_NAME));
		assertFalse(embeddable.tableNameIsInvalid("FOO"));
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();
		assertTrue(embeddable.attributeMappingKeyAllowed(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(embeddable.attributeMappingKeyAllowed(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertFalse(embeddable.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}


	public void testAssociatedTables() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();

		assertFalse(embeddable.associatedTables().hasNext());
	}

	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();

		assertFalse(embeddable.associatedTablesIncludingInherited().hasNext());
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();

		assertFalse(embeddable.associatedTableNamesIncludingInherited().hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = embeddable.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	//TODO need to create a subclass mappedSuperclass and test this
	public void testAllOverridableAssociationNames() throws Exception {
		createTestEmbeddable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEmbeddable embeddable = (IEmbeddable) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = embeddable.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}

}
