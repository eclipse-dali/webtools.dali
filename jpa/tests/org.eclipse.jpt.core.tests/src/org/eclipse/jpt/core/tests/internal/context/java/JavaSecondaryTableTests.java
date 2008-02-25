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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
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
		this.createAnnotationAndMembers("SecondaryTable", 
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

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertEquals(TABLE_NAME, secondaryTable.getSpecifiedName());
	}
	
	public void testGetDefaultNameNull() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertNull(secondaryTable.getDefaultName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().secondaryTables().next().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("foo", table.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SecondaryTable secondaryTable = javaEntity().secondaryTables().next();
		secondaryTable.setSpecifiedName(null);
		
		assertEquals(0, javaEntity().secondaryTablesSize());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		table.setName("foo");
		
		assertEquals("foo", javaEntity().secondaryTables().next().getSpecifiedName());
	}
	
	public void testUpdateFromSpecifiedCatalogChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		ListIterator<SecondaryTableAnnotation> secondaryTableResources = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResources.next().setCatalog("foo");
		secondaryTableResources.next().setCatalog("bar");
		
		ListIterator<SecondaryTable> secondaryTsbles = javaEntity().secondaryTables();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedCatalog());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedCatalog());
	}
	
	public void testUpdateFromSpecifiedSchemaChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		ListIterator<SecondaryTableAnnotation> secondaryTableResources = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResources.next().setSchema("foo");
		secondaryTableResources.next().setSchema("bar");
		
		ListIterator<SecondaryTable> secondaryTsbles = javaEntity().secondaryTables();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedSchema());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedSchema());
	}

	public void testGetCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
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
		SecondaryTable table = javaEntity().secondaryTables().next();
		table.setSpecifiedCatalog("myCatalog");
		table.setSpecifiedName(null);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("myCatalog", tableResource.getCatalog());
		
		table.setSpecifiedCatalog(null);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}
	
	public void testGetSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
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
		SecondaryTable table = javaEntity().secondaryTables().next();
		table.setSpecifiedSchema("mySchema");
		table.setSpecifiedName(null);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("mySchema", tableResource.getSchema());
		
		table.setSpecifiedSchema(null);
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
	}

	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		ListIterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation pkJoinColumn = tableResource.addPkJoinColumn(0);
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
		tableResource.movePkJoinColumn(1, 0);
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
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		assertEquals(0, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
	}

	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		//just the default pkJoinColumn, so 1
		assertEquals(1, secondaryTable.primaryKeyJoinColumnsSize());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		//only the specified pkJoinColumns, 3
		assertEquals(3, secondaryTable.primaryKeyJoinColumnsSize());
	}

	public void testGetDefaultPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		assertNotNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	}
	
	public void testPrimaryKeyJoinColumnDefaults() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn(); 
		assertNotNull(defaultPkJoinColumn);	
		assertEquals("id", defaultPkJoinColumn.getDefaultName());
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		
		IdMapping idMapping = (IdMapping) javaEntity().persistentType().attributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");		
		assertEquals("FOO", defaultPkJoinColumn.getDefaultName());
		assertEquals("FOO", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		idMapping.getColumn().setSpecifiedName(null);
		assertEquals("id", defaultPkJoinColumn.getDefaultName());
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
	}

	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		Iterator<PrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
	
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<PrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals(3, tableResource.pkJoinColumnsSize());

		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumnResources = tableResource.pkJoinColumns();
		assertEquals("FOO", pkJoinColumnResources.next().getName());		
		assertEquals("BAZ", pkJoinColumnResources.next().getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<PrimaryKeyJoinColumn> pkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
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
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<PrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.pkJoinColumns();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());

		
		secondaryTable.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		pkJoinColumns = tableResource.pkJoinColumns();

		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
	}
	
	public void testPrimaryKeyJoinColumnGetDefaultName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", defaultPkJoinColumn.getDefaultName());

		
		//remove @Id annotation
		PersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultName());
	}
	public void testPrimaryKeyJoinColumnGetDefaultReferencedColumnName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		//remove @Id annotation
		PersistentAttribute idAttribute = javaPersistentType().attributeNamed("id");
		idAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable secondaryTable = javaEntity().specifiedSecondaryTables().next();
		
		assertTrue(secondaryTable.getDefaultPrimaryKeyJoinColumn().isVirtual());
		
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		PrimaryKeyJoinColumn specifiedPkJoinColumn = secondaryTable.specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isVirtual());
		
		assertNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	}

}
