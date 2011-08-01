/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaSecondaryTableTests extends ContextModelTestCase
{
	private static final String TABLE_NAME = "MY_TABLE";

	private ICompilationUnit createTestEntityWithSecondaryTable() throws Exception {
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
	
	private ICompilationUnit createTestEntityWithSecondaryTables() throws Exception {
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

		SecondaryTable secondaryTable = getJavaEntity().getSecondaryTables().iterator().next();
		assertEquals(TABLE_NAME, secondaryTable.getSpecifiedName());
	}
	
	public void testGetDefaultNameNull() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSecondaryTables().iterator().next();
		assertNull(secondaryTable.getDefaultName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSecondaryTables().iterator().next();
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SecondaryTable secondaryTable = getJavaEntity().getSecondaryTables().iterator().next();
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", getJavaEntity().getSecondaryTables().iterator().next().getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals("foo", table.getName());
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		table.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", getJavaEntity().getSecondaryTables().iterator().next().getSpecifiedName());
	}
	
	public void testUpdateFromSpecifiedCatalogChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> secondaryTableResources = resourceType.getAnnotations(JPA.SECONDARY_TABLE).iterator();
		((SecondaryTableAnnotation) secondaryTableResources.next()).setCatalog("foo");
		((SecondaryTableAnnotation) secondaryTableResources.next()).setCatalog("bar");
		getJpaProject().synchronizeContextModel();
		
		ListIterator<JavaSecondaryTable> secondaryTsbles = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedCatalog());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedCatalog());
	}
	
	public void testUpdateFromSpecifiedSchemaChangeInResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> secondaryTableResources = resourceType.getAnnotations(JPA.SECONDARY_TABLE).iterator();
		((SecondaryTableAnnotation) secondaryTableResources.next()).setSchema("foo");
		((SecondaryTableAnnotation) secondaryTableResources.next()).setSchema("bar");
		getJpaProject().synchronizeContextModel();
		
		ListIterator<JavaSecondaryTable> secondaryTsbles = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("foo", secondaryTsbles.next().getSpecifiedSchema());
		assertEquals("bar", secondaryTsbles.next().getSpecifiedSchema());
	}

	public void testGetCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.setCatalog("myCatalog");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myCatalog", getJavaEntity().getSecondaryTables().iterator().next().getSpecifiedCatalog());
		assertEquals("myCatalog", getJavaEntity().getSecondaryTables().iterator().next().getCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getSecondaryTables().iterator().next().getDefaultCatalog());
		
		getJavaEntity().getSecondaryTables().iterator().next().setSpecifiedCatalog("myCatalog");
		
		assertNull(getJavaEntity().getSecondaryTables().iterator().next().getDefaultCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable table = getJavaEntity().getSecondaryTables().iterator().next();
		table.setSpecifiedCatalog("myCatalog");
		table.setSpecifiedName(null);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals("myCatalog", tableAnnotation.getCatalog());
	}
	
	public void testGetSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		tableResource.setSchema("mySchema");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("mySchema", getJavaEntity().getSecondaryTables().iterator().next().getSpecifiedSchema());
		assertEquals("mySchema", getJavaEntity().getSecondaryTables().iterator().next().getSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getSecondaryTables().iterator().next().getDefaultSchema());
		
		getJavaEntity().getSecondaryTables().iterator().next().setSpecifiedSchema("mySchema");
		
		assertNull(getJavaEntity().getSecondaryTables().iterator().next().getDefaultSchema());
	}
	
	public void testSetSpecifiedSchema() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable table = getJavaEntity().getSecondaryTables().iterator().next();
		table.setSpecifiedSchema("mySchema");
		table.setSpecifiedName(null);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals("mySchema", tableAnnotation.getSchema());
	}

	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		ListIterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation pkJoinColumn = tableResource.addPkJoinColumn(0);
		pkJoinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = tableResource.addPkJoinColumn(0);
		pkJoinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		//move an annotation to the resource model and verify the context model is updated
		tableResource.movePkJoinColumn(1, 0);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		tableResource.removePkJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		tableResource.removePkJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		assertEquals(0, secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		//just the default pkJoinColumn, so 1
		assertEquals(1, secondaryTable.getPrimaryKeyJoinColumnsSize());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		//only the specified pkJoinColumns, 3
		assertEquals(3, secondaryTable.getPrimaryKeyJoinColumnsSize());
	}

	public void testGetDefaultPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		assertNotNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	}
	
	public void testPrimaryKeyJoinColumnDefaults() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn(); 
		assertNotNull(defaultPkJoinColumn);	
		assertEquals("id", defaultPkJoinColumn.getDefaultName());
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		
		IdMapping idMapping = (IdMapping) getJavaEntity().getPersistentType().getAttributeNamed("id").getMapping();
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
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		Iterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.getPkJoinColumns().iterator();
		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.getPkJoinColumns().iterator();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();

		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(3, tableResource.getPkJoinColumnsSize());

		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumnResources = tableResource.getPkJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumnResources.next().getName());		
		assertEquals("BAZ", pkJoinColumnResources.next().getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<JavaPrimaryKeyJoinColumn> pkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = tableResource.getPkJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumnResources.next().getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		secondaryTable.removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = tableResource.getPkJoinColumns().iterator();
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertFalse(pkJoinColumns.hasNext());

		assertEquals(0, tableResource.getPkJoinColumnsSize());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation tableResource = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		Iterator<PrimaryKeyJoinColumnAnnotation> pkJoinColumns = tableResource.getPkJoinColumns().iterator();
		
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());

		
		secondaryTable.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		pkJoinColumns = tableResource.getPkJoinColumns().iterator();

		assertEquals("BAR", pkJoinColumns.next().getName());
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertEquals("FOO", pkJoinColumns.next().getName());
	}
	
	public void testPrimaryKeyJoinColumnGetDefaultName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", defaultPkJoinColumn.getDefaultName());

		
		//remove @Id annotation
		PersistentAttribute idAttribute = getJavaPersistentType().getAttributeNamed("id");
		idAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultName());
	}
	public void testPrimaryKeyJoinColumnGetDefaultReferencedColumnName() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		PrimaryKeyJoinColumn defaultPkJoinColumn = secondaryTable.getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", defaultPkJoinColumn.getDefaultReferencedColumnName());
		
		//remove @Id annotation
		PersistentAttribute idAttribute = getJavaPersistentType().getAttributeNamed("id");
		idAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(defaultPkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		
		assertTrue(secondaryTable.getDefaultPrimaryKeyJoinColumn().isDefault());
		
		secondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		PrimaryKeyJoinColumn specifiedPkJoinColumn = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertFalse(specifiedPkJoinColumn.isDefault());
		
		assertNull(secondaryTable.getDefaultPrimaryKeyJoinColumn());
	}


	public void testUniqueConstraints() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();

		ListIterator<JavaUniqueConstraint> uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		secondaryTableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();

		assertEquals(0,  secondaryTable.getUniqueConstraintsSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		secondaryTableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(2,  secondaryTable.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = secondaryTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		secondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = secondaryTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		secondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		secondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(3, secondaryTableAnnotation.getUniqueConstraintsSize());

		secondaryTable.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = secondaryTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<JavaUniqueConstraint> uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		secondaryTable.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = secondaryTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		secondaryTable.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = secondaryTableAnnotation.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		secondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		secondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(3, secondaryTableAnnotation.getUniqueConstraintsSize());
		
		
		secondaryTable.moveUniqueConstraint(2, 0);
		ListIterator<JavaUniqueConstraint> uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = secondaryTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));


		secondaryTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintAnnotations = secondaryTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSecondaryTable secondaryTable = getJavaEntity().getSpecifiedSecondaryTables().iterator().next();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTableAnnotation = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
	
		secondaryTableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		secondaryTableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		secondaryTableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();

		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = secondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
}
