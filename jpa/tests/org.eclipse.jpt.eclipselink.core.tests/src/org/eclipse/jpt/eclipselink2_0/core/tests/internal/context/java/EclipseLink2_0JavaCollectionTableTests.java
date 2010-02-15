/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaCollectionTableTests extends EclipseLink2_0ContextModelTestCase
{
	public EclipseLink2_0JavaCollectionTableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityWithElementCollection() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidElementCollection() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
				sb.append("    private Collection<String> projects;").append(CR);
			}
		});
	}

	
	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceCollectionTable);
		
		
		//set name in the resource model, verify context model updated
		attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedName());
		assertEquals("FOO", resourceCollectionTable.getName());
	
		//set name to null in the resource model
		resourceCollectionTable.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceCollectionTable.getName());
		
		resourceCollectionTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedName());
		assertEquals("FOO", resourceCollectionTable.getName());

		attributeResource.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedName());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable3 = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceCollectionTable3);
	
		//set name in the context model, verify resource model modified
		collectionTable.setSpecifiedName("foo");
		resourceCollectionTable3 = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedName());
		assertEquals("foo", resourceCollectionTable3.getName());
		
		//set name to null in the context model
		collectionTable.setSpecifiedName(null);
		assertNull(collectionTable.getSpecifiedName());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(TYPE_NAME + "_projects", collectionTable.getDefaultName());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));

		//add the collection table annotation, verify default collection table name is the same
		attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(TYPE_NAME + "_projects", collectionTable.getDefaultName());
		assertNotNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
		
		//set the entity name, verify default collection table name updates
		getJavaEntity().setSpecifiedName("Foo");
		assertEquals("Foo_projects", collectionTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceCollectionTable);
		
		
		//set schema in the resource model, verify context model updated
		attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable.setSchema("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedSchema());
		assertEquals("FOO", resourceCollectionTable.getSchema());
	
		//set schema to null in the resource model
		resourceCollectionTable.setSchema(null);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceCollectionTable.getSchema());
		
		resourceCollectionTable.setSchema("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedSchema());
		assertEquals("FOO", resourceCollectionTable.getSchema());

		attributeResource.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceCollectionTable);
	
		//set schema in the context model, verify resource model modified
		collectionTable.setSpecifiedSchema("foo");
		resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedSchema());
		assertEquals("foo", resourceCollectionTable.getSchema());
		
		//set schema to null in the context model
		collectionTable.setSpecifiedSchema(null);
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceCollectionTable);
		
		
		//set catalog in the resource model, verify context model updated
		attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable.setCatalog("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedCatalog());
		assertEquals("FOO", resourceCollectionTable.getCatalog());
	
		//set catalog to null in the resource model
		resourceCollectionTable.setCatalog(null);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceCollectionTable.getCatalog());
		
		resourceCollectionTable.setCatalog("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", collectionTable.getSpecifiedCatalog());
		assertEquals("FOO", resourceCollectionTable.getCatalog());

		attributeResource.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceCollectionTable);
	
		//set catalog in the context model, verify resource model modified
		collectionTable.setSpecifiedCatalog("foo");
		resourceCollectionTable = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedCatalog());
		assertEquals("foo", resourceCollectionTable.getCatalog());
		
		//set catalog to null in the context model
		collectionTable.setSpecifiedCatalog(null);
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		
		JoinColumn joinColumn = collectionTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.joinColumnAt(0).getName());
		
		JoinColumn joinColumn2 = collectionTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(1).getName());
		
		JoinColumn joinColumn3 = collectionTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
		
		ListIterator<JoinColumn> joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		collectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		collectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		collectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.joinColumnsSize());
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());

		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.joinColumnsSize());
		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.joinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		collectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		collectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		collectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		
		collectionTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JoinColumn> joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());


		collectionTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
	
		joinTableResource.addJoinColumn(0);
		joinTableResource.addJoinColumn(1);
		joinTableResource.addJoinColumn(2);
		
		joinTableResource.joinColumnAt(0).setName("FOO");
		joinTableResource.joinColumnAt(1).setName("BAR");
		joinTableResource.joinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<JoinColumn> joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(collectionTable.specifiedJoinColumns().hasNext());
	}
	
	public void testGetDefaultJoinColumn() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(0, collectionTable.specifiedJoinColumnsSize());
		
		collectionTable.addSpecifiedJoinColumn(0);
		assertEquals(1, collectionTable.specifiedJoinColumnsSize());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, collectionTable.specifiedJoinColumnsSize());
	}

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = collectionTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(0,  collectionTable.uniqueConstraintsSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");

		getJpaProject().synchronizeContextModel();
		
		assertEquals(2,  collectionTable.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.uniqueConstraintsSize());

		collectionTable.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		collectionTable.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		collectionTable.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.uniqueConstraintsSize());
		
		
		collectionTable.moveUniqueConstraint(2, 0);
		ListIterator<UniqueConstraint> uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());


		collectionTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().attributes().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) attributeResource.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
	
		joinTableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		joinTableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();

		
		ListIterator<UniqueConstraint> uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
}