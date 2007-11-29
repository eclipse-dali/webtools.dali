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
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.Inheritance;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEntityTests extends ContextModelTestCase
{
	private static final String ENTITY_NAME = "entityName";
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String DISCRIMINATOR_VALUE = "MY_DISCRIMINATOR_VALUE";
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createMappedSuperclassAnnotation() throws Exception{
		this.createAnnotationAndMembers("MappedSuperclass", "");		
	}
	
	private void createTableAnnotation() throws Exception {
		this.createAnnotationAndMembers("Table", "String name() default \"\";");		
	}
	
	private void createInheritanceAnnotation() throws Exception {
		createInheritanceTypeEnum();
		this.createAnnotationAndMembers("Inheritance", "InheritanceType strategy() default SINGLE_TABLE;");		
	}
	
	private void createInheritanceTypeEnum() throws Exception {
		this.createEnumAndMembers("InheritanceType", "SINGLE_TABLE, JOINED, TABLE_PER_CLASS");
	}
	
	private void createDiscriminatorValueAnnotation() throws Exception {
		this.createAnnotationAndMembers("DiscriminatorValue", "String value();");		
	}
	
	private void createSecondaryTableAnnotation() throws Exception {
		this.createAnnotationAndMembers("SecondaryTable", 
			"String name(); " +
			"String catalog() default \"\"; " +
			"String schema() default \"\"; ");					
//			PrimaryKeyJoinColumn[] pkJoinColumns() default {};
//			UniqueConstraint[] uniqueConstraints() default {};
	}
	
	private void createSecondaryTablesAnnotation() throws Exception {
		createSecondaryTableAnnotation();
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");		
	}
	
	private void createPrimaryKeyJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", 
			"String name(); " +
			"String referencedColumnName() default \"\"; " +
			"String columnDefinition() default \"\"; ");
	}
	
	private void createPrimaryKeyJoinColumnsAnnotation() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		this.createAnnotationAndMembers("PrimaryKeyJoinColumns", "PrimaryKeyJoinColumn[] value();");		
	}

	
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	
	private IType createTestMappedSuperclass() throws Exception {
		createMappedSuperclassAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
		});
	}

	private IType createTestEntityWithName() throws Exception {
		createEntityAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name=\"" + ENTITY_NAME + "\")");
			}
		});
	}
	
	private IType createTestEntityWithTable() throws Exception {
		createEntityAnnotation();
		createTableAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Table(name=\"" + TABLE_NAME + "\")");
			}
		});
	}

	private IType createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	private IType createTestEntityWithInheritance() throws Exception {
		createEntityAnnotation();
		createInheritanceAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithDiscriminatorValue() throws Exception {
		createEntityAnnotation();
		createDiscriminatorValueAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@DiscriminatorValue(value=\"" + DISCRIMINATOR_VALUE + "\")");
			}
		});
	}
	
	private IType createTestEntityWithSecondaryTable() throws Exception {
		createEntityAnnotation();
		createSecondaryTableAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@SecondaryTable(name=\"foo\")");
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

	private IType createTestEntityWithPrimaryKeyJoinColumns() throws Exception {
		createEntityAnnotation();
		createPrimaryKeyJoinColumnsAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name=\"foo\"), @PrimaryKeyJoinColumn(name=\"bar\")})");
			}
		});
	}

	public JavaEntityTests(String name) {
		super(name);
	}
	
	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(ENTITY_NAME, javaEntity().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(ENTITY_NAME, javaEntity().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("foo", ((Entity) typeResource.mappingAnnotation()).getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().setSpecifiedName(null);
		
		assertNull(javaEntity().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(((Entity) typeResource.mappingAnnotation()).getName());
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = (Entity) typeResource.mappingAnnotation();
		entity.setName("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedName());
	}

	public void testGetTableName() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithName();
	
		assertEquals(ENTITY_NAME, javaEntity().getTableName());
	}
	
	public void testGetTableName2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TYPE_NAME, javaEntity().getTableName());
	}
	
	public void testGetTableName3() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TABLE_NAME, javaEntity().getTableName());
	}	
	
	public void testSetTableNameWithNullTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		ITable table = javaEntity().getTable();
		assertEquals(TYPE_NAME, table.getName());
		assertSourceDoesNotContain("@Table");
		
		table.setSpecifiedName(TABLE_NAME);
		assertSourceContains("@Table(name=\"" + TABLE_NAME + "\")");
		
		assertEquals(TABLE_NAME, javaEntity().getTableName());
		assertEquals(TABLE_NAME, table.getName());

		table.setSpecifiedCatalog(TABLE_NAME);
	}
		
	public void testGetInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getInheritanceStrategy());		
	}
	
	public void testGetDefaultInheritanceStrategy() throws Exception {
		createTestEntity();
		createTestSubType();
				
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotSame(javaEntity(), javaEntity().rootEntity());
		assertEquals(InheritanceType.SINGLE_TABLE, javaEntity().getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		javaEntity().rootEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);

		assertEquals(InheritanceType.SINGLE_TABLE, javaEntity().rootEntity().getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getInheritanceStrategy());
		assertNull(javaEntity().getSpecifiedInheritanceStrategy());
	}
	
	public void testGetSpecifiedInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getSpecifiedInheritanceStrategy());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Inheritance inheritance = (Inheritance) typeResource.annotation(Inheritance.ANNOTATION_NAME);

		inheritance.setStrategy(org.eclipse.jpt.core.internal.resource.java.InheritanceType.JOINED);
		
		assertEquals(InheritanceType.JOINED, javaEntity().getSpecifiedInheritanceStrategy());
		
		inheritance.setStrategy(null);
		
		assertNull(javaEntity().getSpecifiedInheritanceStrategy());
	}
	
	public void testSetSpecifiedInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getSpecifiedInheritanceStrategy());

		javaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		
		assertEquals(InheritanceType.JOINED, javaEntity().getSpecifiedInheritanceStrategy());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Inheritance inheritance = (Inheritance) typeResource.annotation(Inheritance.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.core.internal.resource.java.InheritanceType.JOINED, inheritance.getStrategy());
		
	}
	
	public void testGetDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_VALUE, javaEntity().getDiscriminatorValue());		
	}
	
	public void testGetDefaultDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(javaEntity().getName(), javaEntity().getDefaultDiscriminatorValue());

		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.INTEGER);
		assertNull(javaEntity().getDefaultDiscriminatorValue());
	}
	
	public void testGetSpecifiedDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_VALUE, javaEntity().getSpecifiedDiscriminatorValue());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(DiscriminatorValue.ANNOTATION_NAME);

		discriminatorValue.setValue("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedDiscriminatorValue());
		
		discriminatorValue.setValue(null);
		
		assertNull(javaEntity().getSpecifiedDiscriminatorValue());
		assertNull(typeResource.annotation(DiscriminatorValue.ANNOTATION_NAME));
	}
	
	public void testSetSpecifiedDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(DISCRIMINATOR_VALUE, javaEntity().getSpecifiedDiscriminatorValue());

		javaEntity().setSpecifiedDiscriminatorValue("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedDiscriminatorValue());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(DiscriminatorValue.ANNOTATION_NAME);
		assertEquals("foo", discriminatorValue.getValue());
	}

	public void testSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IJavaSecondaryTable> secondaryTables = javaEntity().secondaryTables();
		
		assertTrue(secondaryTables.hasNext());
		assertEquals("foo", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testSecondaryTablesSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(1, javaEntity().secondaryTablesSize());
	}
	
	public void testSpecifiedSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IJavaSecondaryTable> specifiedSecondaryTables = javaEntity().specifiedSecondaryTables();
		
		assertTrue(specifiedSecondaryTables.hasNext());
		assertEquals("foo", specifiedSecondaryTables.next().getName());
		assertEquals("bar", specifiedSecondaryTables.next().getName());
		assertFalse(specifiedSecondaryTables.hasNext());
	}
	
	public void testSpecifiedSecondaryTablesSize() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, javaEntity().specifiedSecondaryTablesSize());
	}

	public void testAddSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> secondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTable) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAddSpecifiedSecondaryTable2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> secondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTable) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME)));

		javaEntity().removeSpecifiedSecondaryTable(1);
		
		Iterator<JavaResource> secondaryTableResources = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTable) secondaryTableResources.next()).getName());		
		assertEquals("BAZ", ((SecondaryTable) secondaryTableResources.next()).getName());
		assertFalse(secondaryTableResources.hasNext());
		
		Iterator<ISecondaryTable> secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());		
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		
		javaEntity().removeSpecifiedSecondaryTable(1);
		secondaryTableResources = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTable) secondaryTableResources.next()).getName());		
		assertFalse(secondaryTableResources.hasNext());

		secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		
		javaEntity().removeSpecifiedSecondaryTable(0);
		secondaryTableResources = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertFalse(secondaryTableResources.hasNext());
		secondaryTables = javaEntity().secondaryTables();
		assertFalse(secondaryTables.hasNext());

		assertNull(typeResource.annotation(SecondaryTables.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		typeResource.move(0, 2, SecondaryTables.ANNOTATION_NAME);
		
		Iterator<JavaResource> secondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);

		assertEquals("BAR", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("BAZ", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTable) secondaryTables.next()).getName());		
	}
	
	public void testAssociatedTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(javaEntity().associatedTables()));
		Iterator<ITable> associatedTables = javaEntity().associatedTables();
		ITable table1 = associatedTables.next();
		ISecondaryTable table2 = (ISecondaryTable) associatedTables.next();
		ISecondaryTable table3 = (ISecondaryTable) associatedTables.next();
		assertEquals(TYPE_NAME, table1.getName());
		assertEquals("foo", table2.getName());
		assertEquals("bar", table3.getName());
	}
	
	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestEntityWithSecondaryTables();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity parentEntity = javaEntity().rootEntity();
		assertEquals(3, CollectionTools.size(parentEntity.associatedTablesIncludingInherited()));
		Iterator<ITable> associatedTables = parentEntity.associatedTablesIncludingInherited();
		ITable table1 = associatedTables.next();
		ISecondaryTable table2 = (ISecondaryTable) associatedTables.next();
		ISecondaryTable table3 = (ISecondaryTable) associatedTables.next();
		assertEquals(TYPE_NAME, table1.getName());
		assertEquals("foo", table2.getName());
		assertEquals("bar", table3.getName());

		IEntity childEntity = javaEntity();
		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.associatedTablesIncludingInherited()));
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestEntityWithSecondaryTables();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity parentEntity = javaEntity().rootEntity();
		assertEquals(3, CollectionTools.size(parentEntity.associatedTableNamesIncludingInherited()));
		Iterator<String> associatedTables = parentEntity.associatedTableNamesIncludingInherited();
		String table1 = associatedTables.next();
		String table2 = associatedTables.next();
		String table3 = associatedTables.next();
		assertEquals(TYPE_NAME, table1);
		assertEquals("foo", table2);
		assertEquals("bar", table3);
		
		IEntity childEntity = javaEntity();
		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.associatedTableNamesIncludingInherited()));
	}
	
	public void testAddSecondaryTableToResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("FOO");
		
		assertEquals(1, javaEntity().secondaryTablesSize());
		assertEquals("FOO", javaEntity().secondaryTables().next().getSpecifiedName());
		assertEquals("FOO", javaEntity().secondaryTables().next().getName());

		SecondaryTable secondaryTable2 = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable2.setName("BAR");
		
		assertEquals(2, javaEntity().secondaryTablesSize());
		ListIterator<ISecondaryTable> secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());

		SecondaryTable secondaryTable3 = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable3.setName("BAZ");
		
		assertEquals(3, javaEntity().secondaryTablesSize());
		secondaryTables = javaEntity().secondaryTables();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
	}
	
	public void testRemoveSecondaryTableFromResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("baz");
		ListIterator<ISecondaryTable> secondaryTables = javaEntity().secondaryTables();
		
		assertEquals(3, javaEntity().secondaryTablesSize());
		assertEquals("foo", secondaryTables.next().getSpecifiedName());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(2, javaEntity().secondaryTablesSize());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(1, javaEntity().secondaryTablesSize());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
		
		
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(0, javaEntity().secondaryTablesSize());
		assertFalse(secondaryTables.hasNext());
	}	
	
	public void testGetSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getSequenceGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertNotNull(javaEntity().getSequenceGenerator());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		assertNull(javaEntity().getSequenceGenerator());
		
		javaEntity().addSequenceGenerator();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.annotation(JPA.SEQUENCE_GENERATOR));
		assertNotNull(javaEntity().getSequenceGenerator());
		
		//try adding another sequence generator, should get an IllegalStateException
		try {
			javaEntity().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		
		javaEntity().removeSequenceGenerator();
		
		assertNull(javaEntity().getSequenceGenerator());
		assertNull(typeResource.annotation(JPA.SEQUENCE_GENERATOR));

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			javaEntity().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getTableGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.TABLE_GENERATOR);
		
		assertNotNull(javaEntity().getTableGenerator());		
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getTableGenerator());
		
		javaEntity().addTableGenerator();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.annotation(JPA.TABLE_GENERATOR));
		assertNotNull(javaEntity().getTableGenerator());
		
		//try adding another table generator, should get an IllegalStateException
		try {
			javaEntity().addTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.TABLE_GENERATOR);
		
		javaEntity().removeTableGenerator();
		
		assertNull(javaEntity().getTableGenerator());
		assertNull(typeResource.annotation(JPA.TABLE_GENERATOR));
		
		//try removing the table generator again, should get an IllegalStateException
		try {
			javaEntity().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetDiscriminatorColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotNull(javaEntity().getDiscriminatorColumn());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setName("foo");
		
		assertEquals("foo", javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		column.setName(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());

		typeResource.removeAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertNotNull(javaEntity().getDiscriminatorColumn());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumn pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("FOO");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAR");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());


		pkJoinColumn = (PrimaryKeyJoinColumn) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAZ");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.move(0, 1, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testDefaultPrimarykeyJoinColumns() {
		
	}
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumns();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, javaEntity().specifiedPrimaryKeyJoinColumnsSize());
	}

	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> pkJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> pkJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		assertEquals("FOO", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME)));

		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<JavaResource> pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumn) pkJoinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumn) pkJoinColumnResources.next()).getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<IPrimaryKeyJoinColumn> pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumn) pkJoinColumnResources.next()).getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertFalse(pkJoinColumns.hasNext());

		assertNull(typeResource.annotation(PrimaryKeyJoinColumns.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		typeResource.move(0, 2, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		Iterator<JavaResource> pkJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);

		assertEquals("BAR", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumn) pkJoinColumns.next()).getName());		
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0);
		IPrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isVirtual());
		
		IPrimaryKeyJoinColumn defaultPkJoinColumn = javaEntity().defaultPrimaryKeyJoinColumns().next();
		assertTrue(defaultPkJoinColumn.isVirtual());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = javaEntity().overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = javaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = javaEntity().overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	//TODO add some associations to the MappedSuperclass
	//add all mapping types to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = javaEntity().allOverridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
		
	public void testTableNameIsInvalid() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertFalse(javaEntity().tableNameIsInvalid(TYPE_NAME));
		assertTrue(javaEntity().tableNameIsInvalid("FOO"));
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		
		assertFalse(javaEntity().tableNameIsInvalid("BAR"));
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = (IEntity) javaPersistentType().getMapping();
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}
	
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverride) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverride) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.move(0, 1, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, javaEntity().specifiedAttributeOverridesSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverride) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, javaEntity().specifiedAttributeOverridesSize());
	}
	
	public void testAddSpecifiedAttributeOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedAttributeOverride(0).setName("FOO");
		javaEntity().addSpecifiedAttributeOverride(0).setName("BAR");
		javaEntity().addSpecifiedAttributeOverride(0).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> attributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAddSpecifiedAttributeOverride2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedAttributeOverride(0).setName("FOO");
		javaEntity().addSpecifiedAttributeOverride(1).setName("BAR");
		javaEntity().addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResource> attributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedAttributeOverride(0).setName("FOO");
		javaEntity().addSpecifiedAttributeOverride(1).setName("BAR");
		javaEntity().addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)));

		javaEntity().removeSpecifiedAttributeOverride(1);
		
		Iterator<JavaResource> attributeOverrideResources = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<IAttributeOverride> attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());		
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		javaEntity().removeSpecifiedAttributeOverride(1);
		attributeOverrideResources = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		javaEntity().removeSpecifiedAttributeOverride(0);
		attributeOverrideResources = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(typeResource.annotation(AttributeOverrides.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedAttributeOverride(0).setName("FOO");
		javaEntity().addSpecifiedAttributeOverride(1).setName("BAR");
		javaEntity().addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		typeResource.move(0, 2, AttributeOverrides.ANNOTATION_NAME);
		
		Iterator<JavaResource> attributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);

		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());		
	}
	
	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IAttributeOverride> defaultAttributeOverrides = javaEntity().defaultAttributeOverrides();	
		IAttributeOverride defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("id", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());

		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("name", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
		
		javaEntity().addSpecifiedAttributeOverride(0).setName("id");
		IAttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		defaultAttributeOverrides = javaEntity().defaultAttributeOverrides();	
		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("name", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
	}
	
//	Iterator<String> allOverridableAttributeNames();
//
//	Iterator<String> allOverridableAssociationNames();
		
}
