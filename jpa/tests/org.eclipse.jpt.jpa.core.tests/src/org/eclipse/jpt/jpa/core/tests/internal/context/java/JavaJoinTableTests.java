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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaJoinTableTests extends ContextModelTestCase
{
	public JavaJoinTableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityWithManyToMany() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidManyToMany() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
				sb.append("    private Collection<Project> projects;").append(CR);
			}
		});
	}

	private void createTargetEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Project {").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int proj_id;").append(CR);
				sb.append(CR);
				sb.append("}");
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}

	private void createTargetEntityWithBackPointer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MANY_TO_MANY);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Project {").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int proj_id;").append(CR);
				sb.append("    @ManyToMany(mappedBy=\"projects\"").append(CR);
				sb.append("    private java.util.Collection<" + TYPE_NAME + "> employees;").append(CR);
				sb.append(CR);
				sb.append("}");
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}

	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable);
		
		
		//set name in the resource model, verify context model updated
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());
	
		//set name to null in the resource model
		javaJoinTable.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable.getName());
		
		javaJoinTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());

		resourceField.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedName());
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(joinTableAnnotation);
	
		//set name in the context model, verify resource model modified
		joinTable.setSpecifiedName("foo");
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedName());
		assertEquals("foo", joinTableAnnotation.getName());
		
		//set name to null in the context model
		joinTable.setSpecifiedName(null);
		assertNull(joinTable.getSpecifiedName());
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTableAnnotation.getName());
	}
	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinTable.getDefaultName());

		//add target entity to the persistence unit, now join table name is [table name]_[target table name]
		createTargetEntity();
		addXmlClassRef(PACKAGE_NAME + ".Project");
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	
		//target entity does not resolve, default name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertNull(joinTable.getDefaultName());

		//default target entity does resolve, so default name is again [table name]_[target table name]
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());

		//add the join table annotation, verify default join table name is the same
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		
		//set a table on the target entity, very default join table name updates
		manyToManyMapping.getResolvedTargetEntity().getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_FOO", joinTable.getDefaultName());
		
		//set a table on the owning entity, very default join table name updates
		getJavaEntity().getTable().setSpecifiedName("BAR");
		assertEquals("BAR_FOO", joinTable.getDefaultName());
	}

	public void testDefaultJoinColumns() throws Exception {
		createTestEntityWithValidManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		JoinColumn joinColumn = joinTable.getJoinColumns().iterator().next();
		JoinColumn inverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//add target entity to the persistence unit, join column default name and referenced column still null because owning entity has no primary key
		createTargetEntity();
		addXmlClassRef(PACKAGE_NAME + ".Project");
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		//create primary key  in owning entity
		getJavaPersistentType().getAttributeNamed("id").setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(TYPE_NAME + "_id", joinColumn.getDefaultName());
		assertEquals("id", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//set specified column name on primary key in owning entity
		((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	
		//target entity does not resolve, inverse join column name and referenced column name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//default target entity does resolve, so defaults for join column are back
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//add the join table annotation, verify default join table name is the same
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}

	public void testDefaultJoinColumnsBidirectionalRelationship() throws Exception {
		createTestEntityWithValidManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		JoinColumn joinColumn = joinTable.getJoinColumns().iterator().next();
		JoinColumn inverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//add target entity to the persistence unit, join column default name and referenced column still null because owning entity has no primary key
		createTargetEntityWithBackPointer();
		addXmlClassRef(PACKAGE_NAME + ".Project");
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		//create primary key  in owning entity
		getJavaPersistentType().getAttributeNamed("id").setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("employees_id", joinColumn.getDefaultName());
		assertEquals("id", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//set specified column name on primary key in owning entity
		((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	
		//target entity does not resolve, inverse join column name and referenced column name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//default target entity does resolve, so defaults for join column are back
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//add the join table annotation, verify default join table name is the same
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable);
		
		
		//set schema in the resource model, verify context model updated
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setSchema("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());
	
		//set schema to null in the resource model
		javaJoinTable.setSchema(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable.getSchema());
		
		javaJoinTable.setSchema("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());

		resourceField.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(joinTableAnnotation);
	
		//set schema in the context model, verify resource model modified
		joinTable.setSpecifiedSchema("foo");
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedSchema());
		assertEquals("foo", joinTableAnnotation.getSchema());
		
		//set schema to null in the context model
		joinTable.setSpecifiedSchema(null);
		assertNull(joinTable.getSpecifiedSchema());
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTableAnnotation.getSchema());
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable);
		
		
		//set catalog in the resource model, verify context model updated
		resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setCatalog("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());
	
		//set catalog to null in the resource model
		javaJoinTable.setCatalog(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable.getCatalog());
		
		javaJoinTable.setCatalog("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());

		resourceField.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(joinTableAnnotation);
	
		//set catalog in the context model, verify resource model modified
		joinTable.setSpecifiedCatalog("foo");
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedCatalog());
		assertEquals("foo", joinTableAnnotation.getCatalog());
		
		//set catalog to null in the context model
		joinTable.setSpecifiedCatalog(null);
		assertNull(joinTable.getSpecifiedCatalog());
		joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTableAnnotation.getCatalog());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		
		JoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.joinColumnAt(0).getName());
		
		JoinColumn joinColumn2 = joinTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(1).getName());
		
		JoinColumn joinColumn3 = joinTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
		
		ListIterator<JavaJoinColumn> joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableAnnotation.getJoinColumnsSize());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableAnnotation.getJoinColumnsSize());
		assertEquals("BAR", joinTableAnnotation.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableAnnotation.joinColumnAt(1).getName());

		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableAnnotation.getJoinColumnsSize());
		assertEquals("BAZ", joinTableAnnotation.joinColumnAt(0).getName());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableAnnotation.getJoinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.getJoinColumnsSize());
		
		
		joinTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JavaJoinColumn> joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());


		joinTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableResource.addJoinColumn(0);
		joinTableResource.addJoinColumn(1);
		joinTableResource.addJoinColumn(2);
		
		joinTableResource.joinColumnAt(0).setName("FOO");
		joinTableResource.joinColumnAt(1).setName("BAR");
		joinTableResource.joinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<JavaJoinColumn> joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joinTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(joinTable.getSpecifiedJoinColumns().iterator().hasNext());
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertEquals(0, joinTable.getSpecifiedJoinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.getSpecifiedJoinColumnsSize());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTable.getSpecifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertEquals(1, joinTable.getJoinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.getJoinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(2, joinTable.getJoinColumnsSize());

		joinTable.removeSpecifiedJoinColumn(0);
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.getJoinColumnsSize());

		// default columns
		assertNotNull(manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable());
		JavaResourceAttribute resAttribute = this.getJavaPersistentType().getAttributes().iterator().next().getResourceAttribute();
		assertNotNull(resAttribute.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		manyToManyMapping.getRelationship().getJoinTableStrategy().removeStrategy();
		// default join table
		assertNotNull(manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable());
		assertNull(resAttribute.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		
		//if non-owning side of the relationship then no default join table
		manyToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("foo");
		assertNull(manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable());
	}
	
	public void testAddSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		
		JoinColumn inverseJoinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("FOO");
				
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(0).getName());
		
		JoinColumn inverseJoinColumn2 = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(1).getName());
		
		JoinColumn inverseJoinColumn3 = joinTable.addSpecifiedInverseJoinColumn(1);
		inverseJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
		
		ListIterator<JavaJoinColumn> inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals(inverseJoinColumn2, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn3, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn, inverseJoinColumns.next());
		
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.getInverseJoinColumnsSize());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTableResource.getInverseJoinColumnsSize());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());

		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTableResource.getInverseJoinColumnsSize());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTableResource.getInverseJoinColumnsSize());
	}
	
	public void testMoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.getInverseJoinColumnsSize());
		
		
		joinTable.moveSpecifiedInverseJoinColumn(2, 0);
		ListIterator<JavaJoinColumn> inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());


		joinTable.moveSpecifiedInverseJoinColumn(0, 1);
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
	}
	
	public void testUpdateInverseJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableResource.addInverseJoinColumn(0);
		joinTableResource.addInverseJoinColumn(1);
		joinTableResource.addInverseJoinColumn(2);
		
		joinTableResource.inverseJoinColumnAt(0).setName("FOO");
		joinTableResource.inverseJoinColumnAt(1).setName("BAR");
		joinTableResource.inverseJoinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<JavaJoinColumn> inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.moveInverseJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.moveInverseJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		inverseJoinColumns = joinTable.getSpecifiedInverseJoinColumns().iterator();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.removeInverseJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(joinTable.getSpecifiedInverseJoinColumns().iterator().hasNext());
	}

	public void testGetDefaultInverseJoinColumn() {
		//TODO
	}
	
	public void testSpecifiedInverseJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertEquals(0, joinTable.getSpecifiedInverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.getSpecifiedInverseJoinColumnsSize());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTable.getSpecifiedInverseJoinColumnsSize());
	}

	public void testInverseJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTable.getInverseJoinColumnsSize());

		joinTable.removeSpecifiedInverseJoinColumn(0);
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		
		//if non-owning side of the relationship then no default join table
		manyToManyMapping.getRelationship().setStrategyToMappedBy();
		assertNull(manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable());
	}

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertEquals(0,  joinTable.getUniqueConstraintsSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(2,  joinTable.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.getUniqueConstraintsSize());

		joinTable.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<JavaUniqueConstraint> uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		joinTable.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		joinTable.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.getUniqueConstraintsSize());
		
		
		joinTable.moveUniqueConstraint(2, 0);
		ListIterator<? extends UniqueConstraint> uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));


		joinTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintAnnotations = joinTableAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) resourceField.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		joinTableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();
		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = joinTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
}