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
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaSecondaryTableTests extends ContextModelTestCase
{
	private static final String TABLE_NAME = "MY_TABLE";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("Id", "");		
	}
		
	private void createSecondaryTableAnnotation() throws Exception{
		this.createAnnotationAndMembers("eSecondaryTable", 
			"String name() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";");		
	}
	
	private void createSecondaryTablesAnnotation() throws Exception {
		createSecondaryTableAnnotation();
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");		
	}
	
	private IType createTestEntityWithSecondaryTable() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
		createSecondaryTableAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@SecondaryTable(name=\"" + TABLE_NAME + "\")");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private IType createTestEntityWithSecondaryTables() throws Exception {
		createEntityAnnotation();
		createSecondaryTablesAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"foo\"), @SecondaryTable(name=\"bar\")})");
			}
		});
	}



		
	public JavaSecondaryTableTests(String name) {
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
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ISecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertEquals(TABLE_NAME, secondaryTable.getSpecifiedName());
	}
	
	public void testGetDefaultNameNull() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertNull(secondaryTable.getDefaultName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ISecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().secondaryTables().next().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("foo", table.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ISecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		secondaryTable.setSpecifiedName(null);
		
		assertEquals(0, javaEntity().secondaryTablesSize());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		table.setName("foo");
		
		assertEquals("foo", javaEntity().secondaryTables().next().getSpecifiedName());
	}
	
	public void testUpdateFromSpecifiedCatalogChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		ListIterator<SecondaryTable> secondaryTableResources = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResources.next().setCatalog("foo");
		secondaryTableResources.next().setCatalog("bar");
		
		ListIterator<ISecondaryTable> secondaryTsbles = javaEntity().secondaryTables();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedCatalog());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedCatalog());
	}
	
	public void testUpdateFromSpecifiedSchemaChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		ListIterator<SecondaryTable> secondaryTableResources = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResources.next().setSchema("foo");
		secondaryTableResources.next().setSchema("bar");
		
		ListIterator<ISecondaryTable> secondaryTsbles = javaEntity().secondaryTables();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedSchema());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedSchema());
	}

	public void testGetCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.setCatalog("myCatalog");
		
		assertEquals("myCatalog", javaEntity().secondaryTables().next().getSpecifiedCatalog());
		assertEquals("myCatalog", javaEntity().secondaryTables().next().getCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().secondaryTables().next().getDefaultCatalog());
		
		javaEntity().secondaryTables().next().setSpecifiedCatalog("myCatalog");
		
		assertNull(javaEntity().secondaryTables().next().getDefaultCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ISecondaryTable table = javaEntity().secondaryTables().next();
		table.setSpecifiedCatalog("myCatalog");
		table.setSpecifiedName(null);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("myCatalog", tableResource.getCatalog());
		
		table.setSpecifiedCatalog(null);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}
	
	public void testGetSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		tableResource.setSchema("mySchema");
		
		assertEquals("mySchema", javaEntity().secondaryTables().next().getSpecifiedSchema());
		assertEquals("mySchema", javaEntity().secondaryTables().next().getSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().secondaryTables().next().getDefaultSchema());
		
		javaEntity().secondaryTables().next().setSpecifiedSchema("mySchema");
		
		assertNull(javaEntity().secondaryTables().next().getDefaultSchema());
	}
	
	public void testSetSpecifiedSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ISecondaryTable table = javaEntity().secondaryTables().next();
		table.setSpecifiedSchema("mySchema");
		table.setSpecifiedName(null);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("mySchema", tableResource.getSchema());
		
		table.setSpecifiedSchema(null);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}

	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumn pkJoinColumn = tableResource.addPkJoinColumn(0);
		pkJoinColumn.setName("FOO");
		specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = tableResource.addPkJoinColumn(0);
		pkJoinColumn.setName("BAR");
		specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		//move an annotation to the resource model and verify the context model is updated
		tableResource.movePkJoinColumn(0, 1);
		specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		tableResource.removePkJoinColumn(0);
		specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		tableResource.removePkJoinColumn(0);
		specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		assertEquals(0, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
	}

	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		Iterator<IPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumn> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<IPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumn> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals(3, tableResource.pkJoinColumnsSize());

		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<PrimaryKeyJoinColumn> pkJoinColumnResources = tableResource.pkJoinColumns();
		assertEquals("FOO", pkJoinColumnResources.next().getName());		
		assertEquals("BAZ", pkJoinColumnResources.next().getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<IPrimaryKeyJoinColumn> pkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = tableResource.pkJoinColumns();
		assertEquals("FOO", pkJoinColumnResources.next().getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = tableResource.pkJoinColumns();
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertFalse(pkJoinColumns.hasNext());

		assertEquals(0, tableResource.pkJoinColumnsSize());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<IPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable tableResource = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumn> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());

		
		secondaryTable.moveSpecifiedPrimaryKeyJoinColumn(0, 2);
		pkJoinColumns = tableResource.pkJoinColumns();

		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
	}
	
	public void testPrimaryKeyJoinColumnGetDefaultName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		IPrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", defaultPkJoinColumn.getDefaultName());

		
		//remove @Id annotation
		IPersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultName());
	}
	public void testPrimaryKeyJoinColumnGetDefaultReferencedColumnName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		IPrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.defaultPrimaryKeyJoinColumns().next();
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		//remove @Id annotation
		IPersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ISecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = secondaryTable.specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isVirtual());
		
		IPrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.defaultPrimaryKeyJoinColumns().next();
		assertTrue(defaultPkJoinColumn.isVirtual());
	}

}
