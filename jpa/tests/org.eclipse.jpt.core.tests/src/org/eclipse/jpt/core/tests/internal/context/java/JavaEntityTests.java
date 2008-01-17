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
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.base.INamedNativeQuery;
import org.eclipse.jpt.core.internal.context.base.INamedQuery;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverrides;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.IdClass;
import org.eclipse.jpt.core.internal.resource.java.Inheritance;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.java.NamedQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedQuery;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEntityTests extends ContextModelTestCase
{
	private static final String ENTITY_NAME = "entityName";
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String DISCRIMINATOR_VALUE = "MY_DISCRIMINATOR_VALUE";
	
	public JavaEntityTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception {
		this.createAnnotationAndMembers("Id", "");		
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

	private IType createTestEntityAnnotationOnProperty() throws Exception {
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
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private IType createTestMappedSuperclass() throws Exception {
		createMappedSuperclassAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    ");
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

	
	public void testMorphToMappedSuperclass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity entity = (IEntity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		entity.addSpecifiedAttributeOverride(0);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		
		javaPersistentType().setMappingKey(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof IMappedSuperclass);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Entity.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Table.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SecondaryTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(PrimaryKeyJoinColumn.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Inheritance.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(DiscriminatorValue.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity entity = (IEntity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		entity.addSpecifiedAttributeOverride(0);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		
		javaPersistentType().setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof IEmbeddable);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.mappingAnnotation(Entity.ANNOTATION_NAME));
		assertNull(typeResource.annotation(Table.ANNOTATION_NAME));
		assertNull(typeResource.annotation(SecondaryTable.ANNOTATION_NAME));
		assertNull(typeResource.annotation(PrimaryKeyJoinColumn.ANNOTATION_NAME));
		assertNull(typeResource.annotation(AttributeOverride.ANNOTATION_NAME));
		assertNull(typeResource.annotation(Inheritance.ANNOTATION_NAME));
		assertNull(typeResource.annotation(DiscriminatorValue.ANNOTATION_NAME));
		assertNull(typeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(typeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity entity = (IEntity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		entity.addSpecifiedAttributeOverride(0);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		
		javaPersistentType().setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.mappingAnnotation(Entity.ANNOTATION_NAME));
		assertNull(typeResource.annotation(Table.ANNOTATION_NAME));
		assertNull(typeResource.annotation(SecondaryTable.ANNOTATION_NAME));
		assertNull(typeResource.annotation(PrimaryKeyJoinColumn.ANNOTATION_NAME));
		assertNull(typeResource.annotation(AttributeOverride.ANNOTATION_NAME));
		assertNull(typeResource.annotation(Inheritance.ANNOTATION_NAME));
		assertNull(typeResource.annotation(DiscriminatorValue.ANNOTATION_NAME));
		assertNull(typeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(typeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
	}
	
	public void testAccessNoAnnotations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}

	public void testAccessAnnotationsOnParent() throws Exception {
		createTestEntityAnnotationOnProperty();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
			
		IJavaPersistentType childPersistentType = javaPersistentType();
		IPersistentType parentPersistentType = childPersistentType.parentPersistentType();
		
		assertEquals(AccessType.PROPERTY, parentPersistentType.access());
		assertEquals(AccessType.PROPERTY, childPersistentType.access());		
		
		((IIdMapping) parentPersistentType.attributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.properties().next().setMappingAnnotation(null);
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, parentPersistentType.access());
		assertEquals(AccessType.PROPERTY, childPersistentType.access());

		((IBasicMapping) parentPersistentType.attributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		assertEquals(AccessType.FIELD, parentPersistentType.access());
		assertEquals(AccessType.FIELD, childPersistentType.access());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, parentPersistentType.access());
		assertEquals(AccessType.PROPERTY, childPersistentType.access());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, parentPersistentType.access());
		assertEquals(AccessType.PROPERTY, childPersistentType.access());
		
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//only parent specified in orm.xml, i think this outcome is right??
		assertEquals(AccessType.FIELD, xmlPersistentType.javaPersistentType().access());
		assertEquals(AccessType.FIELD, childPersistentType.access());

		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		removeXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		//both parent and child specified in orm.xml
		assertEquals(AccessType.FIELD, xmlPersistentType.javaPersistentType().access());
		assertEquals(AccessType.FIELD, childXmlPersistentType.javaPersistentType().access());
	}
	
	public void testAccessWithXmlSettings() throws Exception {
		createTestEntityAnnotationOnProperty();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
				
		assertEquals(AccessType.PROPERTY, javaPersistentType().access());
			
		((IIdMapping) javaPersistentType().attributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.properties().next().setMappingAnnotation(null);
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, javaPersistentType().access());

		((IBasicMapping) javaPersistentType().attributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		assertEquals(AccessType.FIELD, javaPersistentType().access());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType().access());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, javaPersistentType().access());
		
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//now class is specified in orm.xml, so entityMappings access setting wins over persistence-unit-defaults
		assertEquals(AccessType.FIELD, xmlPersistentType.javaPersistentType().access());
		
		((XmlEntity) xmlPersistentType.getMapping()).setSpecifiedAccess(AccessType.PROPERTY);
		//accessType still FIELD because the xmlEntity setting should not affect the java access type
		assertEquals(AccessType.FIELD, xmlPersistentType.javaPersistentType().access());
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

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		entity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		ListIterator<SecondaryTable> javaSecondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaSecondaryTables));
		
		
		entity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<ISecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertEquals("BAR", javaSecondaryTables.next().getName());
		assertEquals("BAZ", javaSecondaryTables.next().getName());
		assertEquals("FOO", javaSecondaryTables.next().getName());


		entity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		assertEquals("BAZ", javaSecondaryTables.next().getName());
		assertEquals("BAR", javaSecondaryTables.next().getName());
		assertEquals("FOO", javaSecondaryTables.next().getName());
	}
	
	public void testUpdateSpecifiedSecondaryTables() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((SecondaryTable) typeResource.addAnnotation(0, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTable) typeResource.addAnnotation(1, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTable) typeResource.addAnnotation(2, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<ISecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.move(2, 0, SecondaryTables.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.move(0, 1, SecondaryTables.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeAnnotation(1,  SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeAnnotation(1,  SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.removeAnnotation(0,  SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertFalse(secondaryTables.hasNext());
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
		assertEquals(1, CollectionTools.size(typeResource.annotations()));
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
		assertEquals(1, CollectionTools.size(typeResource.annotations()));
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
		typeResource.move(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMNS);
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
	
	public void testDefaultPrimaryKeyJoinColumns() {
		//TODO
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

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		entity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		entity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		ListIterator<PrimaryKeyJoinColumn> javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaPrimaryKeyJoinColumns));
		
		
		entity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<IPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertEquals("BAR", javaPrimaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", javaPrimaryKeyJoinColumns.next().getName());
		assertEquals("FOO", javaPrimaryKeyJoinColumns.next().getName());


		entity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		assertEquals("BAZ", javaPrimaryKeyJoinColumns.next().getName());
		assertEquals("BAR", javaPrimaryKeyJoinColumns.next().getName());
		assertEquals("FOO", javaPrimaryKeyJoinColumns.next().getName());
	}
	
	public void testUpdateSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((PrimaryKeyJoinColumn) typeResource.addAnnotation(0, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME)).setName("FOO");
		((PrimaryKeyJoinColumn) typeResource.addAnnotation(1, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME)).setName("BAR");
		((PrimaryKeyJoinColumn) typeResource.addAnnotation(2, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<IPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.move(2, 0, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.move(0, 1, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeAnnotation(1,  PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeAnnotation(1,  PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.removeAnnotation(0,  PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertFalse(primaryKeyJoinColumns.hasNext());
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

		Iterator<String> overridableAssociationNames = javaPersistentType().parentPersistentType().getMapping().allOverridableAssociationNames();
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
		typeResource.move(1, 0, JPA.ATTRIBUTE_OVERRIDES);
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
		//TODO
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

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedAttributeOverride(0).setName("FOO");
		entity.addSpecifiedAttributeOverride(1).setName("BAR");
		entity.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		ListIterator<AttributeOverride> javaAttributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		
		entity.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<IAttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("BAR", javaAttributeOverrides.next().getName());
		assertEquals("BAZ", javaAttributeOverrides.next().getName());
		assertEquals("FOO", javaAttributeOverrides.next().getName());


		entity.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("BAZ", javaAttributeOverrides.next().getName());
		assertEquals("BAR", javaAttributeOverrides.next().getName());
		assertEquals("FOO", javaAttributeOverrides.next().getName());
	}
	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((AttributeOverride) typeResource.addAnnotation(0, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverride) typeResource.addAnnotation(1, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverride) typeResource.addAnnotation(2, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<IAttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.move(2, 0, AttributeOverrides.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.move(0, 1, AttributeOverrides.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());
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
	
	public void testAddNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		INamedQuery namedQuery = entity.addNamedQuery(0);
		namedQuery.setName("FOO");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedQuery namedQuery2 = entity.addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedQuery namedQuery3 = entity.addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		ListIterator<INamedQuery> namedQueries = entity.namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedQuery(0).setName("FOO");
		entity.addNamedQuery(1).setName("BAR");
		entity.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());

		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		
		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedQuery(0).setName("FOO");
		entity.addNamedQuery(1).setName("BAR");
		entity.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.moveNamedQuery(2, 0);
		ListIterator<INamedQuery> namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());


		entity.moveNamedQuery(0, 1);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((NamedQuery) typeResource.addAnnotation(0, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("FOO");
		((NamedQuery) typeResource.addAnnotation(1, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("BAR");
		((NamedQuery) typeResource.addAnnotation(2, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<INamedQuery> namedQueries = entity.namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.move(2, 0, NamedQueries.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.move(0, 1, NamedQueries.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeAnnotation(0,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertFalse(namedQueries.hasNext());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		INamedNativeQuery namedNativeQuery = entity.addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
		
		ListIterator<NamedNativeQuery> javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedNativeQuery namedNativeQuery2 = entity.addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedNativeQuery namedNativeQuery3 = entity.addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		ListIterator<INamedNativeQuery> namedQueries = entity.namedNativeQueries();
		assertEquals(namedNativeQuery2, namedQueries.next());
		assertEquals(namedNativeQuery3, namedQueries.next());
		assertEquals(namedNativeQuery, namedQueries.next());
		
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedNativeQuery(0).setName("FOO");
		entity.addNamedNativeQuery(1).setName("BAR");
		entity.addNamedNativeQuery(2).setName("BAZ");
		
		ListIterator<NamedNativeQuery> javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());

		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		
		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedNativeQuery(0).setName("FOO");
		entity.addNamedNativeQuery(1).setName("BAR");
		entity.addNamedNativeQuery(2).setName("BAZ");
		
		ListIterator<NamedNativeQuery> javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.moveNamedNativeQuery(2, 0);
		ListIterator<INamedNativeQuery> namedQueries = entity.namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());


		entity.moveNamedNativeQuery(0, 1);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((NamedNativeQuery) typeResource.addAnnotation(0, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQuery) typeResource.addAnnotation(1, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQuery) typeResource.addAnnotation(2, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<INamedNativeQuery> namedQueries = entity.namedNativeQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.move(2, 0, NamedNativeQueries.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.move(0, 1, NamedNativeQueries.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeAnnotation(0,  NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertFalse(namedQueries.hasNext());
	}	


	public void testAddSpecifiedAssociationOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		IAssociationOverride associationOverride = entity.addSpecifiedAssociationOverride(0);
		associationOverride.setName("FOO");
		
		ListIterator<AssociationOverride> javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("FOO", javaAssociationOverrides.next().getName());
		
		IAssociationOverride associationOverride2 = entity.addSpecifiedAssociationOverride(0);
		associationOverride2.setName("BAR");
		
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAR", javaAssociationOverrides.next().getName());
		assertEquals("FOO", javaAssociationOverrides.next().getName());
		
		IAssociationOverride associationOverride3 = entity.addSpecifiedAssociationOverride(1);
		associationOverride3.setName("BAZ");
		
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAR", javaAssociationOverrides.next().getName());
		assertEquals("BAZ", javaAssociationOverrides.next().getName());
		assertEquals("FOO", javaAssociationOverrides.next().getName());
		
		ListIterator<IAssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals(associationOverride2, associationOverrides.next());
		assertEquals(associationOverride3, associationOverrides.next());
		assertEquals(associationOverride, associationOverrides.next());
		
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
	}
	
	public void testRemoveSpecifiedAssociationOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedAssociationOverride(0).setName("FOO");
		entity.addSpecifiedAssociationOverride(1).setName("BAR");
		entity.addSpecifiedAssociationOverride(2).setName("BAZ");
		
		ListIterator<AssociationOverride> javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAssociationOverrides));
		
		entity.removeSpecifiedAssociationOverride(0);
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAR", javaAssociationOverrides.next().getName());
		assertEquals("BAZ", javaAssociationOverrides.next().getName());

		entity.removeSpecifiedAssociationOverride(0);
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaAssociationOverrides));
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAZ", javaAssociationOverrides.next().getName());
		
		entity.removeSpecifiedAssociationOverride(0);
		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaAssociationOverrides));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedAssociationOverride(0).setName("FOO");
		entity.addSpecifiedAssociationOverride(1).setName("BAR");
		entity.addSpecifiedAssociationOverride(2).setName("BAZ");
		
		ListIterator<AssociationOverride> javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAssociationOverrides));
		
		
		entity.moveSpecifiedAssociationOverride(2, 0);
		ListIterator<IAssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAR", javaAssociationOverrides.next().getName());
		assertEquals("BAZ", javaAssociationOverrides.next().getName());
		assertEquals("FOO", javaAssociationOverrides.next().getName());


		entity.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		assertEquals("BAZ", javaAssociationOverrides.next().getName());
		assertEquals("BAR", javaAssociationOverrides.next().getName());
		assertEquals("FOO", javaAssociationOverrides.next().getName());
	}
	
	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IEntity entity = javaEntity();		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((AssociationOverride) typeResource.addAnnotation(0, AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverride) typeResource.addAnnotation(1, AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverride) typeResource.addAnnotation(2, AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<IAssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.move(2, 0, AssociationOverrides.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.move(0, 1, AssociationOverrides.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IAssociationOverride> defaultAssociationOverrides = javaEntity().defaultAssociationOverrides();	
		IAssociationOverride defaultAssociationOverride = defaultAssociationOverrides.next();
		assertEquals("address", defaultAssociationOverride.getName());
		assertTrue(defaultAssociationOverride.isVirtual());
		assertFalse(defaultAssociationOverrides.hasNext());
		
		javaEntity().addSpecifiedAssociationOverride(0).setName("address");
		IAssociationOverride specifiedAssociationOverride = javaEntity().specifiedAssociationOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
				
		defaultAssociationOverrides = javaEntity().defaultAssociationOverrides();	
		assertFalse(defaultAssociationOverrides.hasNext());
	}

	public void testUpdateIdClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.annotation(IdClass.ANNOTATION_NAME));
		
		IdClass idClass = (IdClass) typeResource.addAnnotation(IdClass.ANNOTATION_NAME);	
		assertNull(javaEntity().getIdClass());
		assertNotNull(typeResource.annotation(IdClass.ANNOTATION_NAME));
		
		idClass.setValue("model.Foo");
		assertEquals("model.Foo", javaEntity().getIdClass());
		assertEquals("model.Foo", ((IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME)).getValue());
		
		//test setting  @IdClass value to null, IdClass annotation is removed
		idClass.setValue(null);
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.annotation(IdClass.ANNOTATION_NAME));
		
		//reset @IdClass value and then remove @IdClass
		idClass = (IdClass) typeResource.addAnnotation(IdClass.ANNOTATION_NAME);	
		idClass.setValue("model.Foo");
		typeResource.removeAnnotation(IdClass.ANNOTATION_NAME);
		
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.annotation(IdClass.ANNOTATION_NAME));		
	}
	
	public void testModifyIdClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.annotation(IdClass.ANNOTATION_NAME));
			
		javaEntity().setIdClass("model.Foo");
		assertEquals("model.Foo", ((IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME)).getValue());
		assertEquals("model.Foo", javaEntity().getIdClass());
		
		javaEntity().setIdClass(null);
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.annotation(IdClass.ANNOTATION_NAME));
	}
	
//	Iterator<String> allOverridableAttributeNames();
//
//	Iterator<String> allOverridableAssociationNames();
		
}
