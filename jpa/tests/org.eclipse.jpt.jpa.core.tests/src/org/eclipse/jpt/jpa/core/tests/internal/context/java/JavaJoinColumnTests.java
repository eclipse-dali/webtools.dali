/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaJoinColumnTests extends ContextModelTestCase
{
	public JavaJoinColumnTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityWithOneToOne() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidOneToOne() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
				sb.append("    private Project project;").append(CR);
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
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation joinColumnAnnotation = (JoinColumnAnnotation) resourceField.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(joinColumnAnnotation);
		
		
		//set name in the resource model, verify context model updated
		joinColumnAnnotation = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		joinColumnAnnotation.setName("FOO");	
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("FOO", joinColumnAnnotation.getName());
	
		//set name to null, annotation is NOT removed
		joinColumn.setSpecifiedName(null);
		assertEquals(1, joinColumns.getSpecifiedJoinColumnsSize());
		joinColumnAnnotation = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertNull(joinColumnAnnotation.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set name in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		JoinColumnAnnotation joinColumnAnnotation = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinColumn.getSpecifiedName());
		assertEquals("foo", joinColumnAnnotation.getName());
		
		//set name to null in the context model
		joinColumn.setSpecifiedName(null);
		assertNull(joinColumn.getSpecifiedName());
		joinColumnAnnotation = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertNull(joinColumnAnnotation.getName());
	}
	
	// <attribute name>_<referenced column name>
	//     or
	// <target entity name>_<referenced column name>	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidOneToOne();
		createTargetEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
//		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().attributes().next().getMapping();
//		JavaJoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnJoiningStrategy();
//		JavaJoinColumn defaultJavaJoinColumn = joinColumns.getDefaultJoinColumn();
//	
//		assertNull(defaultJavaJoinColumn.getDefaultName());
//TODO test default join column name
//		//add target entity to the persistence unit, now join table name is [table name]_[target table name]
//		addXmlClassRef(PACKAGE_NAME + ".Project");
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//		
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentresourceField resourceField = typeResource.attributes().next();
//		assertNull(resourceField.annotation(JoinTable.ANNOTATION_NAME));
//	
//		//target entity does not resolve, default name is null
//		manyToManyMapping.setSpecifiedTargetEntity("Foo");
//		assertNull(joinTable.getDefaultName());
//
//		//default target entity does resolve, so default name is again [table name]_[target table name]
//		manyToManyMapping.setSpecifiedTargetEntity(null);
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//
//		//add the join table annotation, verify default join table name is the same
//		resourceField.addAnnotation(JoinTable.ANNOTATION_NAME);
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//		assertNotNull(resourceField.annotation(JoinTable.ANNOTATION_NAME));
//		
//		//set a table on the target entity, very default join table name updates
//		manyToManyMapping.getResolvedTargetEntity().getTable().setSpecifiedName("FOO");
//		assertEquals(TYPE_NAME + "_FOO", joinTable.getDefaultName());
//		
//		//set a table on the owning entity, very default join table name updates
//		javaEntity().getTable().setSpecifiedName("BAR");
//		assertEquals("BAR_FOO", joinTable.getDefaultName());
	}
	
	public void testUpdateSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set referenced column name in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setReferencedColumnName("BAR");
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals("BAR", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", javaJoinColumn.getReferencedColumnName());
	
		//set referenced column name to null in the resource model, 
		javaJoinColumn.setReferencedColumnName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull("BAR", javaJoinColumn.getReferencedColumnName());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set referenced column name in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedReferencedColumnName("BAR");
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", javaJoinColumn.getReferencedColumnName());
		
		//set referenced column name to null in the context model
		joinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(javaJoinColumn.getReferencedColumnName());
	}
	
	public void testDefaultReferencedColumnName() throws Exception {
	//TODO test default join column referenced column name
		
	}
	
	public void testUpdateTable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set table in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setTable("BAR");
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals("BAR", joinColumn.getSpecifiedTable());
		assertEquals("BAR", javaJoinColumn.getTable());
	
		//set table to null in the resource model, 
		javaJoinColumn.setTable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedTable());
		assertNull("BAR", javaJoinColumn.getTable());
	}
	
	public void testModifyTable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set table in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedTable("BAR");
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", joinColumn.getSpecifiedTable());
		assertEquals("BAR", javaJoinColumn.getTable());
		
		//set table to null in the context model
		joinColumn.setSpecifiedTable(null);
		assertNull(joinColumn.getSpecifiedTable());
		assertNull(javaJoinColumn.getTable());
	}
	
	public void testDefaultTable() throws Exception {
	//TODO test default join column table
		
	}

	public void testUpdateUnique() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set unique in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setUnique(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, javaJoinColumn.getUnique());
	
		//set unique to null in the resource model, 
		javaJoinColumn.setUnique(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedUnique());
		assertNull(javaJoinColumn.getUnique());
	}
	
	public void testModifyUnique() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set unique in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, javaJoinColumn.getUnique());
		
		//set unique to null in the context model
		joinColumn.setSpecifiedUnique(null);
		assertNull(joinColumn.getSpecifiedUnique());
		assertNull(javaJoinColumn.getUnique());
	}
	
	public void testUpdateNullable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set nullable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setNullable(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getNullable());
	
		//set nullable to null in the resource model, 
		javaJoinColumn.setNullable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedNullable());
		assertNull(javaJoinColumn.getNullable());
	}
	
	public void testModifyNullable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set nullable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getNullable());
		
		//set nullable to null in the context model
		joinColumn.setSpecifiedNullable(null);
		assertNull(joinColumn.getSpecifiedNullable());
		assertNull(javaJoinColumn.getNullable());
	}

	public void testUpdateInsertable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set insertable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setInsertable(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getInsertable());
	
		//set insertable to null in the resource model, 
		javaJoinColumn.setInsertable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedInsertable());
		assertNull(javaJoinColumn.getInsertable());
	}
	
	public void testModifyInsertable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set insertable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getInsertable());
		
		//set insertable to null in the context model
		joinColumn.setSpecifiedInsertable(null);
		assertNull(joinColumn.getSpecifiedInsertable());
		assertNull(javaJoinColumn.getInsertable());
	}

	public void testUpdateUpdatable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set updatable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setUpdatable(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		JavaJoinColumn joinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getUpdatable());
	
		//set updatable to null in the resource model, 
		javaJoinColumn.setUpdatable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(joinColumn.getSpecifiedUpdatable());
		assertNull(javaJoinColumn.getUpdatable());
	}
	
	public void testModifyUpdatable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		
		JavaJoinColumn joinColumn = joinColumns.addSpecifiedJoinColumn(0);
		//set updatable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getUpdatable());
		
		//set updatable to null in the context model
		joinColumn.setSpecifiedUpdatable(null);
		assertNull(joinColumn.getSpecifiedUpdatable());
		assertNull(javaJoinColumn.getUpdatable());
	}
}
