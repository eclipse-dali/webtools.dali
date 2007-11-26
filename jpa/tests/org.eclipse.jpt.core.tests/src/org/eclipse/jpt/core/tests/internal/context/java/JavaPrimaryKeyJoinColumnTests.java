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
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPrimaryKeyJoinColumnTests extends ContextModelTestCase
{
	private static final String PRIMARY_KEY_JOIN_COLUMN_NAME = "MY_PRIMARY_KEY_JOIN_COLUMN";
	private static final String COLUMN_DEFINITION = "MY_COLUMN_DEFINITION";

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("Id", "");		
	}
	
	private void createPrimaryKeyJoinColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", 
			"String name() default \"\";" +
			"String referencedColumnName() default \"\";" +
			"String columnDefinition() default \"\";");		
	}
		

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private IType createTestEntityWithPrimaryKeyJoinColumn() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
		createPrimaryKeyJoinColumnAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@PrimaryKeyJoinColumn(name=\"" + PRIMARY_KEY_JOIN_COLUMN_NAME + "\")");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

		
	public JavaPrimaryKeyJoinColumnTests(String name) {
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
	
	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals(PRIMARY_KEY_JOIN_COLUMN_NAME, specifiedPkJoinColumn.getSpecifiedName());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn pkJoinColumnResource = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setName("FOO");
		specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals("FOO", specifiedPkJoinColumn.getName());
	}
	
	public void testGetDefaultNameNoSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn pkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", pkJoinColumn.getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn defaultPkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", defaultPkJoinColumn.getDefaultName());
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals("id", specifiedPkJoinColumn.getDefaultName());
		
		//remove @Id annotation
		IPersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(specifiedPkJoinColumn.getDefaultName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals(PRIMARY_KEY_JOIN_COLUMN_NAME, specifiedPkJoinColumn.getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		
		specifiedPkJoinColumn.setSpecifiedName("foo");
		assertEquals("foo", specifiedPkJoinColumn.getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", pkJoinColumn.getName());
		
		specifiedPkJoinColumn.setSpecifiedName(null);
		assertNull(specifiedPkJoinColumn.getSpecifiedName());
		pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(pkJoinColumn);
	}

	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertNull(specifiedPkJoinColumn.getColumnDefinition());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		column.setColumnDefinition(COLUMN_DEFINITION);
		
		assertEquals(COLUMN_DEFINITION, specifiedPkJoinColumn.getColumnDefinition());
		
		column.setColumnDefinition(null);
		
		assertNull(specifiedPkJoinColumn.getColumnDefinition());

		typeResource.removeAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals(0, javaEntity().specifiedPrimaryKeyJoinColumnsSize());
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		specifiedPkJoinColumn.setColumnDefinition("foo");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		specifiedPkJoinColumn.setColumnDefinition(null);
		column = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(column.getColumnDefinition());
	}

	public void testGetSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertNull(specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn pkJoinColumnResource = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setReferencedColumnName("FOO");
		specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals("FOO", specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	public void testGetDefaultReferencedColumnNameNoSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn pkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", pkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testGetDefaultReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPrimaryKeyJoinColumn defaultPkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals("id", specifiedPkJoinColumn.getDefaultReferencedColumnName());
		
		
		//remove @Id annotation
		IPersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(specifiedPkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testGetReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn pkJoinColumnResource = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setReferencedColumnName("FOO");
		
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertEquals("FOO", specifiedPkJoinColumn.getReferencedColumnName());
	}

	public void testSetSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName("foo");
		assertEquals("foo", specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		PrimaryKeyJoinColumn pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", pkJoinColumn.getReferencedColumnName());
		
		specifiedPkJoinColumn.setSpecifiedName(null);
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(pkJoinColumn);
	}
	
	public void testIsVirtual() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isVirtual());
		
		IPrimaryKeyJoinColumn defaultPkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertTrue(defaultPkJoinColumn.isVirtual());
	}
	
	public void testIsReferencedColumnResolved() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isReferencedColumnResolved());
	}
	
	public void testDbColumn() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertNull(specifiedPkJoinColumn.dbColumn());
	}
	
	public void testDbReferencedColumn() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertNull(specifiedPkJoinColumn.dbReferencedColumn());
	}
	
	public void testDbTable() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertNull(specifiedPkJoinColumn.dbTable());
	}

}
