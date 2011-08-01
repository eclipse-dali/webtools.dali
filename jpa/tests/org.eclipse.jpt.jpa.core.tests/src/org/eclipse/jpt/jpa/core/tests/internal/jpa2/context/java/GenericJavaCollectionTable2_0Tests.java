/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaCollectionTable2_0Tests extends Generic2_0ContextModelTestCase
{
	public GenericJavaCollectionTable2_0Tests(String name) {
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
				sb.append("@ElementCollection");
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

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceCollectionTable);
		
		
		//set name in the resource model, verify context model updated
		resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
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

		resourceField.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable3 = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceCollectionTable3);
	
		//set name in the context model, verify resource model modified
		collectionTable.setSpecifiedName("foo");
		resourceCollectionTable3 = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedName());
		assertEquals("foo", resourceCollectionTable3.getName());
		
		//set name to null in the context model
		collectionTable.setSpecifiedName(null);
		assertNull(collectionTable.getSpecifiedName());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(TYPE_NAME + "_projects", collectionTable.getDefaultName());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));

		//add the collection table annotation, verify default collection table name is the same
		resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(TYPE_NAME + "_projects", collectionTable.getDefaultName());
		assertNotNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
		
		//set the entity name, verify default collection table name updates
		getJavaEntity().setSpecifiedName("Foo");
		assertEquals("Foo_projects", collectionTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceCollectionTable);
		
		
		//set schema in the resource model, verify context model updated
		resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
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

		resourceField.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceCollectionTable);
	
		//set schema in the context model, verify resource model modified
		collectionTable.setSpecifiedSchema("foo");
		resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedSchema());
		assertEquals("foo", resourceCollectionTable.getSchema());
		
		//set schema to null in the context model
		collectionTable.setSpecifiedSchema(null);
		assertNull(collectionTable.getSpecifiedSchema());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceCollectionTable);
		
		
		//set catalog in the resource model, verify context model updated
		resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
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

		resourceField.removeAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceCollectionTable);
	
		//set catalog in the context model, verify resource model modified
		collectionTable.setSpecifiedCatalog("foo");
		resourceCollectionTable = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals("foo", collectionTable.getSpecifiedCatalog());
		assertEquals("foo", resourceCollectionTable.getCatalog());
		
		//set catalog to null in the context model
		collectionTable.setSpecifiedCatalog(null);
		assertNull(collectionTable.getSpecifiedCatalog());
		assertNull(resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME));
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		
		JoinColumn joinColumn = collectionTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);

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
		
		ListIterator<JavaJoinColumn> joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		collectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		collectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		collectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.getJoinColumnsSize());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.getJoinColumnsSize());
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());

		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.getJoinColumnsSize());
		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.getJoinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		collectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		collectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		collectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.getJoinColumnsSize());
		
		
		collectionTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JavaJoinColumn> joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());


		collectionTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
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

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		CollectionTable2_0Annotation joinTableResource = (CollectionTable2_0Annotation) resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
	
		joinTableResource.addJoinColumn(0);
		joinTableResource.addJoinColumn(1);
		joinTableResource.addJoinColumn(2);
		
		joinTableResource.joinColumnAt(0).setName("FOO");
		joinTableResource.joinColumnAt(1).setName("BAR");
		joinTableResource.joinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<JavaJoinColumn> joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = collectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(collectionTable.getSpecifiedJoinColumns().iterator().hasNext());
	}
	
	public void testGetDefaultJoinColumn() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(0, collectionTable.getSpecifiedJoinColumnsSize());
		
		collectionTable.addSpecifiedJoinColumn(0);
		assertEquals(1, collectionTable.getSpecifiedJoinColumnsSize());
		
		collectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, collectionTable.getSpecifiedJoinColumnsSize());
	}

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		
		assertEquals(0,  collectionTable.getUniqueConstraintsSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(2,  collectionTable.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		CollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.getUniqueConstraintsSize());

		collectionTable.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<JavaUniqueConstraint> uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		collectionTable.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		collectionTable.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		collectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		collectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		collectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.getAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.getUniqueConstraintsSize());
		
		
		collectionTable.moveUniqueConstraint(2, 0);
		ListIterator<JavaUniqueConstraint> uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));


		collectionTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithElementCollection();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 collectionTable = elementCollectionMapping.getCollectionTable();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		CollectionTable2_0Annotation joinTableAnnotation = (CollectionTable2_0Annotation) resourceField.addAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
	
		joinTableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		joinTableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();

		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = collectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
}