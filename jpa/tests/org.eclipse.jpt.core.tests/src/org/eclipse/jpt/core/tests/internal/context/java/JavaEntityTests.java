/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaEntityTests extends ContextModelTestCase
{
	private static final String ENTITY_NAME = "entityName";
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String DISCRIMINATOR_VALUE = "MY_DISCRIMINATOR_VALUE";
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;
	
	
	public JavaEntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
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
	
	private void createTestAbstractEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public abstract class ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, sourceWriter);
	}

	private ICompilationUnit createTestEntityAnnotationOnProperty() throws Exception {	
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

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
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
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	private ICompilationUnit createTestEntityWithName() throws Exception {
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
	
	private ICompilationUnit createTestEntityWithTable() throws Exception {
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

	private void createTestSubType() throws Exception {
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
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	private ICompilationUnit createTestEntityWithInheritance() throws Exception {	
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
	
	private ICompilationUnit createTestEntityWithDiscriminatorValue() throws Exception {	
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
	
	private ICompilationUnit createTestEntityWithSecondaryTable() throws Exception {
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
	
	public void testMorphToMappedSuperclass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = (Entity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		entity.setIdClass("myIdClass");
		entity.addNamedNativeQuery(0);
		entity.addNamedQuery(0);
		
		javaPersistentType().setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof MappedSuperclass);
		
		assertNull(typeResource.getMappingAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = (Entity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		entity.setIdClass("myIdClass");
		entity.addNamedNativeQuery(0);
		entity.addNamedQuery(0);
		
		javaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof Embeddable);
		
		assertNull(typeResource.getMappingAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = (Entity) javaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.addTableGenerator();
		entity.addSequenceGenerator();
		entity.setIdClass("myIdClass");
		entity.addNamedNativeQuery(0);
		entity.addNamedQuery(0);
		
		javaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		assertNull(typeResource.getMappingAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}
	
	public void testAccessNoAnnotations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(AccessType.FIELD, javaPersistentType().getAccess());
	}

	public void testAccessAnnotationsOnParent() throws Exception {
		createTestEntityAnnotationOnProperty();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
			
		JavaPersistentType childPersistentType = javaPersistentType();
		PersistentType parentPersistentType = childPersistentType.getParentPersistentType();
		
		assertEquals(AccessType.PROPERTY, parentPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());		
		
		((IdMapping) parentPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.persistableProperties().next().setMappingAnnotation(null);
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, parentPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());

		((BasicMapping) parentPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		assertEquals(AccessType.FIELD, parentPersistentType.getAccess());
		assertEquals(AccessType.FIELD, childPersistentType.getAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, parentPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, parentPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//only parent specified in orm.xml, i think this outcome is right??
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		assertEquals(AccessType.FIELD, childPersistentType.getAccess());

		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		removeXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		//both parent and child specified in orm.xml
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		assertEquals(AccessType.FIELD, childOrmPersistentType.getJavaPersistentType().getAccess());
	}
	
	public void testAccessWithXmlSettings() throws Exception {
		createTestEntityAnnotationOnProperty();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
				
		assertEquals(AccessType.PROPERTY, javaPersistentType().getAccess());
			
		((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.persistableProperties().next().setMappingAnnotation(null);
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, javaPersistentType().getAccess());

		((BasicMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		assertEquals(AccessType.FIELD, javaPersistentType().getAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType().getAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, javaPersistentType().getAccess());
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//now class is specified in orm.xml, so entityMappings access setting wins over persistence-unit-defaults
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		
		((OrmEntity) ormPersistentType.getMapping()).setSpecifiedAccess(AccessType.PROPERTY);
		
		//accessType should be PROPERTY now, java gets the access from xml entity if it is specified
		assertEquals(AccessType.PROPERTY, ormPersistentType.getJavaPersistentType().getAccess());
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
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("foo", ((EntityAnnotation) typeResource.getMappingAnnotation()).getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().setSpecifiedName(null);
		
		assertNull(javaEntity().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(((EntityAnnotation) typeResource.getMappingAnnotation()).getName());
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EntityAnnotation entity = (EntityAnnotation) typeResource.getMappingAnnotation();
		entity.setName("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedName());
	}

	public void testGetTableName() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithName();
	
		assertEquals(ENTITY_NAME, javaEntity().getPrimaryTableName());
	}
	
	public void testGetTableName2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TYPE_NAME, javaEntity().getPrimaryTableName());
	}
	
	public void testGetTableName3() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TABLE_NAME, javaEntity().getPrimaryTableName());
	}	
	
	public void testSetTableNameWithNullTable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Table table = javaEntity().getTable();
		assertEquals(TYPE_NAME, table.getName());
		assertSourceDoesNotContain("@Table", cu);
		
		table.setSpecifiedName(TABLE_NAME);
		assertSourceContains("@Table(name=\"" + TABLE_NAME + "\")", cu);
		
		assertEquals(TABLE_NAME, javaEntity().getPrimaryTableName());
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
		
		assertNotSame(javaEntity(), javaEntity().getRootEntity());
		assertEquals(InheritanceType.SINGLE_TABLE, javaEntity().getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		javaEntity().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);

		assertEquals(InheritanceType.SINGLE_TABLE, javaEntity().getRootEntity().getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getInheritanceStrategy());
		assertNull(javaEntity().getSpecifiedInheritanceStrategy());
	}
	
	public void testGetSpecifiedInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(InheritanceType.TABLE_PER_CLASS, javaEntity().getSpecifiedInheritanceStrategy());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) typeResource.getSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME);

		inheritance.setStrategy(org.eclipse.jpt.core.resource.java.InheritanceType.JOINED);
		
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
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) typeResource.getSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.core.resource.java.InheritanceType.JOINED, inheritance.getStrategy());
		
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

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);

		discriminatorValue.setValue("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedDiscriminatorValue());
		
		discriminatorValue.setValue(null);
		
		assertNull(javaEntity().getSpecifiedDiscriminatorValue());
		assertNull(typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testSetSpecifiedDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(DISCRIMINATOR_VALUE, javaEntity().getSpecifiedDiscriminatorValue());

		javaEntity().setSpecifiedDiscriminatorValue("foo");
		
		assertEquals("foo", javaEntity().getSpecifiedDiscriminatorValue());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) typeResource.getSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
		assertEquals("foo", discriminatorValue.getValue());
	}

	public void testSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> secondaryTables = javaEntity().secondaryTables();
		
		assertTrue(secondaryTables.hasNext());
		assertEquals("foo", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.secondaryTablesSize());

		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		assertEquals(3, entity.secondaryTablesSize());
	}
	
	public void testSpecifiedSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> specifiedSecondaryTables = javaEntity().specifiedSecondaryTables();
		
		assertTrue(specifiedSecondaryTables.hasNext());
		assertEquals("foo", specifiedSecondaryTables.next().getName());
		assertEquals("bar", specifiedSecondaryTables.next().getName());
		assertFalse(specifiedSecondaryTables.hasNext());
	}
	
	public void testSpecifiedSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.specifiedSecondaryTablesSize());

		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		assertEquals(3, entity.specifiedSecondaryTablesSize());
	}

	public void testAddSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> secondaryTables = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAddSpecifiedSecondaryTable2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> secondaryTables = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAddSpecifiedSecondaryTablePreservePkJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable specifiedSecondaryTable = javaEntity().addSpecifiedSecondaryTable(0);
		specifiedSecondaryTable.setSpecifiedName("FOO");
		specifiedSecondaryTable.setSpecifiedCatalog("CATALOG");
		specifiedSecondaryTable.setSpecifiedSchema("SCHEMA");
		specifiedSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("PK_NAME");
		
		//add another specified secondary table, pkJoinColumns from first should be saved.
		SecondaryTable specifiedSecondaryTable2 = javaEntity().addSpecifiedSecondaryTable(1);
		specifiedSecondaryTable2.setSpecifiedName("BAR");
		
		
		Iterator<SecondaryTable> secondaryTables = javaEntity().secondaryTables();
		SecondaryTable secondaryTable = secondaryTables.next();
		assertEquals(secondaryTable, specifiedSecondaryTable);
		assertEquals("FOO", secondaryTable.getName());
		assertEquals("CATALOG", secondaryTable.getCatalog());
		assertEquals("SCHEMA", secondaryTable.getSchema());
		assertEquals(1, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
		PrimaryKeyJoinColumn pkJoinColumn = secondaryTable.specifiedPrimaryKeyJoinColumns().next();
		assertEquals("PK_NAME", pkJoinColumn.getName());
		
		secondaryTable = secondaryTables.next();
		assertEquals(secondaryTable, specifiedSecondaryTable2);
		assertEquals("BAR", secondaryTable.getName());
		assertEquals(0, secondaryTable.specifiedPrimaryKeyJoinColumnsSize());
			
	}
	
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)));

		javaEntity().removeSpecifiedSecondaryTable(1);
		
		Iterator<NestableAnnotation> secondaryTableResources = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());
		assertFalse(secondaryTableResources.hasNext());
		
		Iterator<SecondaryTable> secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());		
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		
		javaEntity().removeSpecifiedSecondaryTable(1);
		secondaryTableResources = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertFalse(secondaryTableResources.hasNext());

		secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		
		javaEntity().removeSpecifiedSecondaryTable(0);
		secondaryTableResources = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertFalse(secondaryTableResources.hasNext());
		secondaryTables = javaEntity().secondaryTables();
		assertFalse(secondaryTables.hasNext());

		assertNull(typeResource.getSupportingAnnotation(SecondaryTablesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		entity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		ListIterator<NestableAnnotation> javaSecondaryTables = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaSecondaryTables));
		
		
		entity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<SecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());


		entity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
	}
	
	public void testUpdateSpecifiedSecondaryTables() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addSupportingAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<SecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.moveSupportingAnnotation(2, 0, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.moveSupportingAnnotation(0, 1, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.removeSupportingAnnotation(0,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTables = entity.specifiedSecondaryTables();
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAssociatedTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(javaEntity().associatedTables()));
		Iterator<Table> associatedTables = javaEntity().associatedTables();
		Table table1 = associatedTables.next();
		SecondaryTable table2 = (SecondaryTable) associatedTables.next();
		SecondaryTable table3 = (SecondaryTable) associatedTables.next();
		assertEquals(TYPE_NAME, table1.getName());
		assertEquals("foo", table2.getName());
		assertEquals("bar", table3.getName());
	}
	
	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestEntityWithSecondaryTables();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity parentEntity = javaEntity().getRootEntity();
		assertEquals(3, CollectionTools.size(parentEntity.associatedTablesIncludingInherited()));
		Iterator<Table> associatedTables = parentEntity.associatedTablesIncludingInherited();
		Table table1 = associatedTables.next();
		SecondaryTable table2 = (SecondaryTable) associatedTables.next();
		SecondaryTable table3 = (SecondaryTable) associatedTables.next();
		assertEquals(TYPE_NAME, table1.getName());
		assertEquals("foo", table2.getName());
		assertEquals("bar", table3.getName());

		Entity childEntity = javaEntity();
		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.associatedTablesIncludingInherited()));
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestEntityWithSecondaryTables();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity parentEntity = javaEntity().getRootEntity();
		assertEquals(3, CollectionTools.size(parentEntity.associatedTableNamesIncludingInherited()));
		Iterator<String> associatedTables = parentEntity.associatedTableNamesIncludingInherited();
		String table1 = associatedTables.next();
		String table2 = associatedTables.next();
		String table3 = associatedTables.next();
		assertEquals(TYPE_NAME, table1);
		assertEquals("foo", table2);
		assertEquals("bar", table3);
		
		Entity childEntity = javaEntity();
		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.associatedTableNamesIncludingInherited()));
	}
	
	public void testAddSecondaryTableToResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) typeResource.addSupportingAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("FOO");
		
		assertEquals(1, javaEntity().secondaryTablesSize());
		assertEquals("FOO", javaEntity().secondaryTables().next().getSpecifiedName());
		assertEquals("FOO", javaEntity().secondaryTables().next().getName());

		SecondaryTableAnnotation secondaryTable2 = (SecondaryTableAnnotation) typeResource.addSupportingAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable2.setName("BAR");
		
		assertEquals(2, javaEntity().secondaryTablesSize());
		ListIterator<SecondaryTable> secondaryTables = javaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());

		SecondaryTableAnnotation secondaryTable3 = (SecondaryTableAnnotation) typeResource.addSupportingAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
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
		ListIterator<SecondaryTable> secondaryTables = javaEntity().secondaryTables();
		
		assertEquals(3, javaEntity().secondaryTablesSize());
		assertEquals("foo", secondaryTables.next().getSpecifiedName());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeSupportingAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(2, javaEntity().secondaryTablesSize());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		typeResource.removeSupportingAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(1, javaEntity().secondaryTablesSize());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
		
		
		typeResource.removeSupportingAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		secondaryTables = javaEntity().secondaryTables();
		assertEquals(0, javaEntity().secondaryTablesSize());
		assertFalse(secondaryTables.hasNext());
	}	
	
	public void testGetSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getSequenceGenerator());
		assertEquals(0, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertNotNull(javaEntity().getSequenceGenerator());
		assertEquals(1, typeResource.supportingAnnotationsSize());
		assertEquals(1, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
		
		javaEntity().getSequenceGenerator().setName("foo");
		assertEquals(1, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		assertNull(javaEntity().getSequenceGenerator());
		
		javaEntity().addSequenceGenerator();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
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
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		
		javaEntity().removeSequenceGenerator();
		
		assertNull(javaEntity().getSequenceGenerator());
		assertNull(typeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));

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
		assertEquals(0, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(JPA.TABLE_GENERATOR);
		
		assertNotNull(javaEntity().getTableGenerator());		
		assertEquals(1, typeResource.supportingAnnotationsSize());
		assertEquals(1, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
		
		javaEntity().getTableGenerator().setName("foo");
		assertEquals(1, CollectionTools.size(javaEntity().getPersistenceUnit().allGenerators()));
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getTableGenerator());
		
		javaEntity().addTableGenerator();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.getSupportingAnnotation(JPA.TABLE_GENERATOR));
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

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addSupportingAnnotation(JPA.TABLE_GENERATOR);
		
		javaEntity().removeTableGenerator();
		
		assertNull(javaEntity().getTableGenerator());
		assertNull(typeResource.getSupportingAnnotation(JPA.TABLE_GENERATOR));
		
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

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setName("foo");
		
		assertEquals("foo", javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		column.setName(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());

		typeResource.removeSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertNotNull(javaEntity().getDiscriminatorColumn());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("FOO");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAR");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());


		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAZ");
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveSupportingAnnotation(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		typeResource.removeSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		typeResource.removeSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		typeResource.removeSupportingAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		specifiedPkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, javaEntity().specifiedPrimaryKeyJoinColumnsSize());
	
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, javaEntity().specifiedPrimaryKeyJoinColumnsSize());
	}

	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		//just the default pkJoinColumn, so 1
		assertEquals(1, javaEntity().primaryKeyJoinColumnsSize());
	
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		//only the specified pkJoinColumns, 3
		assertEquals(3, javaEntity().primaryKeyJoinColumnsSize());
	}

	public void testGetDefaultPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotNull(javaEntity().getDefaultPrimaryKeyJoinColumn());
	
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertNull(javaEntity().getDefaultPrimaryKeyJoinColumn());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> pkJoinColumns = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> pkJoinColumns = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		javaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)));

		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<NestableAnnotation> pkJoinColumnResources = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<PrimaryKeyJoinColumn> pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		javaEntity().removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = javaEntity().specifiedPrimaryKeyJoinColumns();
		assertFalse(pkJoinColumns.hasNext());

		assertNull(typeResource.getSupportingAnnotation(PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		entity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		entity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		ListIterator<NestableAnnotation> javaPrimaryKeyJoinColumns = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaPrimaryKeyJoinColumns));
		
		
		entity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<PrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());


		entity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
	}
	
	public void testUpdateSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("FOO");
		((PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAR");
		((PrimaryKeyJoinColumnAnnotation) typeResource.addSupportingAnnotation(2, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<PrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.moveSupportingAnnotation(2, 0, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.moveSupportingAnnotation(0, 1, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.removeSupportingAnnotation(0,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertFalse(primaryKeyJoinColumns.hasNext());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertTrue(javaEntity().getDefaultPrimaryKeyJoinColumn().isVirtual());

		javaEntity().addSpecifiedPrimaryKeyJoinColumn(0);
		PrimaryKeyJoinColumn specifiedPkJoinColumn = javaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isVirtual());
		
		assertNull(javaEntity().getDefaultPrimaryKeyJoinColumn());
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

		Entity entity = (Entity) javaPersistentType().getMapping();
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}
	
	public void testOverridableAttributes() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAttributes = javaEntity().overridableAttributes();
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = javaEntity().overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAttributes = javaEntity().allOverridableAttributes();
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertEquals("foo", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<PersistentAttribute> overridableAttributes = javaEntity().allOverridableAttributes();
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertEquals("foo", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = javaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveSupportingAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		typeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		typeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = javaEntity().specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, javaEntity.virtualAttributeOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, javaEntity().specifiedAttributeOverridesSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, javaEntity().specifiedAttributeOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = javaEntity();
		
		assertEquals(3, javaEntity.virtualAttributeOverridesSize());

		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(2, javaEntity.virtualAttributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(1, javaEntity.virtualAttributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(0, javaEntity.virtualAttributeOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = javaEntity();
		
		assertEquals(3, javaEntity.attributeOverridesSize());

		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, javaEntity.attributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, javaEntity.attributeOverridesSize());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("bar");	
		assertEquals(4, javaEntity.attributeOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAttributeOverride> virtualAttributeOverrides = javaEntity().virtualAttributeOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(3, CollectionTools.size(typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)));

		javaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		
		Iterator<NestableAnnotation> attributeOverrideResources = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<AttributeOverride> attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		javaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrideResources = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		javaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrideResources = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = javaEntity().specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(typeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = javaEntity();
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		javaEntity().virtualAttributeOverrides().next().setVirtual(false);

		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<NestableAnnotation> javaAttributeOverrides = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		
		entity.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<AttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		entity.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
//	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) typeResource.addSupportingAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<AttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.moveSupportingAnnotation(2, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.moveSupportingAnnotation(0, 1, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.removeSupportingAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<AttributeOverride> virtualAttributeOverrides = javaEntity().virtualAttributeOverrides();	
		AttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());

		javaEntity().virtualAttributeOverrides().next().setVirtual(false);
		AttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = javaEntity().virtualAttributeOverrides();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}
	
	
	public void testOverridableAssociations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<PersistentAttribute> overridableAssociations = javaEntity().overridableAssociations();
		assertFalse(overridableAssociations.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = javaEntity().overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = javaEntity().allOverridableAssociationNames();
		assertEquals("address", overridableAssociationNames.next());
		assertEquals("address2", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociations() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAssociations = javaEntity().allOverridableAssociations();
		assertEquals("address", overridableAssociations.next().getName());
		assertEquals("address2", overridableAssociations.next().getName());
		assertFalse(overridableAssociations.hasNext());
	}
	
	public void testAllOverridableAssociationsMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<PersistentAttribute> overridableAssociations = javaEntity().allOverridableAssociations();
		assertEquals("address", overridableAssociations.next().getName());
		assertEquals("address2", overridableAssociations.next().getName());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveSupportingAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		typeResource.removeSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		typeResource.removeSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = javaEntity().specifiedAssociationOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testDefaultAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, javaEntity.virtualAssociationOverridesSize());
		AssociationOverride virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		

		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
		//idMapping.getColumn().setSpecifiedName("FOO");
		//idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, javaEntity.virtualAssociationOverridesSize());
		virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());

		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getSupportingAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getSupportingAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride = virtualAssociationOverride.setVirtual(false);
		assertEquals(1, javaEntity.virtualAssociationOverridesSize());
		
		
		
//		//TODO joinColumns for default association overrides
////	IJoinColumn defaultJoinColumn = defaultAssociationOverride.joinColumns().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////
////	IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
////	
////	IOneToOneMapping addressMapping = (IOneToOneMapping) mappedSuperclass.persistentType().attributeNamed("address").getMapping();
////	IJoinColumn joinColumn = addressMapping.addSpecifiedJoinColumn(0);
////	joinColumn.setSpecifiedName("FOO");
////	joinColumn.setSpecifiedReferencedColumnName("BAR");
////	joinColumn.setSpecifiedTable("BAZ");
////	
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	assertEquals(1, CollectionTools.size(javaEntity.defaultAssociationOverrides()));
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultAssociationOverride.getName());
////	assertEquals("FOO", defaultJoinColumn.getName());
////	assertEquals("BAR", defaultJoinColumn.getReferencedColumnName());
////	assertEquals("BAZ", defaultJoinColumn.getTable());
////
////	joinColumn.setSpecifiedName(null);
////	joinColumn.setSpecifiedReferencedColumnName(null);
////	joinColumn.setSpecifiedTable(null);
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////	javaEntity.addSpecifiedAssociationOverride(0).setName("address");
////	assertEquals(0, CollectionTools.size(javaEntity.defaultAssociationOverrides()));

	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, javaEntity().specifiedAssociationOverridesSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");

		assertEquals(2, javaEntity().specifiedAssociationOverridesSize());
	}
	
	public void testDefaultAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = javaEntity();
		
		assertEquals(2, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(1, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(0, javaEntity.virtualAssociationOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = javaEntity();
		
		assertEquals(2, javaEntity.associationOverridesSize());

		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, javaEntity.associationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, javaEntity.associationOverridesSize());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		annotation.setName("bar");	
		assertEquals(3, javaEntity.associationOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAssociationOverride> virtualAssociationOverrides = javaEntity().virtualAssociationOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().setVirtual(false);
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(2, CollectionTools.size(typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)));

		javaEntity().specifiedAssociationOverrides().next().setVirtual(true);
		
		Iterator<NestableAnnotation> associationOverrideResources = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<AssociationOverride> associationOverrides = javaEntity().specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		javaEntity().specifiedAssociationOverrides().next().setVirtual(true);
		associationOverrideResources = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = javaEntity().specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());

		assertNull(typeResource.getSupportingAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = javaEntity();
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		javaEntity().virtualAssociationOverrides().next().setVirtual(false);

		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<NestableAnnotation> javaAssociationOverrides = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		entity.moveSpecifiedAssociationOverride(1, 0);
		ListIterator<AssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		entity.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) typeResource.addSupportingAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<AssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.moveSupportingAnnotation(2, 0, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.moveSupportingAnnotation(0, 1, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeSupportingAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.removeSupportingAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<AssociationOverride> virtualAssociationOverrides = javaEntity().virtualAssociationOverrides();	
		AssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		javaEntity().virtualAssociationOverrides().next().setVirtual(false);
		AssociationOverride specifiedAssociationOverride = javaEntity().specifiedAssociationOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = javaEntity().virtualAssociationOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testAddNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		NamedQuery namedQuery = entity.addNamedQuery(0);
		namedQuery.setName("FOO");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery2 = entity.addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery3 = entity.addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<NamedQuery> namedQueries = entity.namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		
		entity.addNamedNativeQuery(0).setName("foo");
	}
	
	public void testRemoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedQuery(0).setName("FOO");
		entity.addNamedQuery(1).setName("BAR");
		entity.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());

		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.removeNamedQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testAddNamedNativeQueryWithNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedQuery(0).setName("FOO");
		
		
		NamedNativeQueryAnnotation nativeQuery = (NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(0, JPA.NAMED_NATIVE_QUERY, JPA.NAMED_NATIVE_QUERIES);
		nativeQuery.setName("BAR");
		
		assertEquals(1, entity.namedNativeQueriesSize());
		assertEquals("BAR", entity.namedNativeQueries().next().getName());
	}
	
	public void testAddNamedQueryWithNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedNativeQuery(0).setName("FOO");
		
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.addSupportingAnnotation(0, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		
		assertEquals(1, entity.namedQueriesSize());
		assertEquals("BAR", entity.namedQueries().next().getName());
	}

	public void testMoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedQuery(0).setName("FOO");
		entity.addNamedQuery(1).setName("BAR");
		entity.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.moveNamedQuery(2, 0);
		ListIterator<NamedQuery> namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());


		entity.moveNamedQuery(0, 1);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		ListIterator<NamedQuery> namedQueries = entity.namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.moveSupportingAnnotation(2, 0, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.moveSupportingAnnotation(0, 1, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeSupportingAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.removeSupportingAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.removeSupportingAnnotation(0,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedQueries();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
	}
	
	public void testNamedQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.namedQueriesSize());

		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) typeResource.addSupportingAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		assertEquals(3, entity.namedQueriesSize());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		NamedNativeQuery namedNativeQuery = entity.addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery2 = entity.addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery3 = entity.addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<NamedNativeQuery> namedQueries = entity.namedNativeQueries();
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

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedNativeQuery(0).setName("FOO");
		entity.addNamedNativeQuery(1).setName("BAR");
		entity.addNamedNativeQuery(2).setName("BAZ");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());

		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addNamedNativeQuery(0).setName("FOO");
		entity.addNamedNativeQuery(1).setName("BAR");
		entity.addNamedNativeQuery(2).setName("BAZ");
		
		ListIterator<NestableAnnotation> javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.moveNamedNativeQuery(2, 0);
		ListIterator<NamedNativeQuery> namedQueries = entity.namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());


		entity.moveNamedNativeQuery(0, 1);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");	
		ListIterator<NamedNativeQuery> namedQueries = entity.namedNativeQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.moveSupportingAnnotation(2, 0, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.moveSupportingAnnotation(0, 1, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeSupportingAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.removeSupportingAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
		
		typeResource.removeSupportingAnnotation(0,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedQueries = entity.namedNativeQueries();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, CollectionTools.size(entity.getPersistenceUnit().allQueries()));
	}	
	
	public void testNamedNativeQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = javaEntity();		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.namedNativeQueriesSize());

		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) typeResource.addSupportingAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		assertEquals(3, entity.namedNativeQueriesSize());
	}

	public void testUpdateIdClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		IdClassAnnotation idClass = (IdClassAnnotation) typeResource.addSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		assertNull(javaEntity().getIdClass());
		assertNotNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		idClass.setValue("model.Foo");
		assertEquals("model.Foo", javaEntity().getIdClass());
		assertEquals("model.Foo", ((IdClassAnnotation) typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		
		//test setting  @IdClass value to null, IdClass annotation is removed
		idClass.setValue(null);
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		//reset @IdClass value and then remove @IdClass
		idClass = (IdClassAnnotation) typeResource.addSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		idClass.setValue("model.Foo");
		typeResource.removeSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));		
	}
	
	public void testModifyIdClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
			
		javaEntity().setIdClass("model.Foo");
		assertEquals("model.Foo", ((IdClassAnnotation) typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals("model.Foo", javaEntity().getIdClass());
		
		javaEntity().setIdClass(null);
		assertNull(javaEntity().getIdClass());
		assertNull(typeResource.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetPrimaryKeyColumnNameWithAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<ClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		JavaPersistentType entityPersistentType = classRefs.next().getJavaPersistentType();
		JavaEntity javaEntity = (JavaEntity) entityPersistentType.getMapping();
		JavaPersistentType mappedSuperclassPersistentType = classRefs.next().getJavaPersistentType();
		
		assertNull(javaEntity.getPrimaryKeyColumnName());

		mappedSuperclassPersistentType.getAttributeNamed("id").setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("id", javaEntity.getPrimaryKeyColumnName());
		
		((JavaIdMapping) mappedSuperclassPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("MY_ID", javaEntity.getPrimaryKeyColumnName());
		
		JavaAttributeOverride javaAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", javaAttributeOverride.getName());
		
		javaAttributeOverride = (JavaAttributeOverride) javaAttributeOverride.setVirtual(false);
		javaAttributeOverride.getColumn().setSpecifiedName("ID");
		assertEquals("ID", javaEntity.getPrimaryKeyColumnName());
	}
	
	public void testDiscriminatorValueIsAllowedConcreteClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(javaEntity().isDiscriminatorValueAllowed());
	}
	
	public void testDiscriminatorValueIsAllowedAbstractClass() throws Exception {
		createTestAbstractEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertFalse(javaEntity().isDiscriminatorValueAllowed());
	}
}
