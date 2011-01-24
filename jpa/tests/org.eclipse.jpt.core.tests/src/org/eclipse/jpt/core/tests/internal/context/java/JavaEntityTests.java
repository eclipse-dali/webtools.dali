/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAttributeOverride;
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
	
	private ICompilationUnit createTestEntityInvalidNamedQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.NAMED_QUERIES, JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@NamedQueries(value={@NamedQuery(query=\"asdf\", name=\"foo\"), @NamedQuer})");
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
				return new ArrayIterator<String>(
					JPA.MAPPED_SUPERCLASS, 
					JPA.BASIC,
					JPA.VERSION,
					JPA.TRANSIENT,
					JPA.EMBEDDED,
					JPA.EMBEDDED_ID,
					JPA.ONE_TO_ONE,
					JPA.ONE_TO_MANY,
					JPA.MANY_TO_ONE,
					JPA.MANY_TO_MANY,
					JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @Basic");
				sb.append(CR);
				sb.append("    private int basic;").append(CR);
				sb.append(CR);
				sb.append("    @Version");
				sb.append(CR);
				sb.append("    private int version;").append(CR);
				sb.append(CR);
				sb.append("    @Transient");
				sb.append(CR);
				sb.append("    private int transient;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded");
				sb.append(CR);
				sb.append("    private int embedded;").append(CR);
				sb.append(CR);
				sb.append("    @EmbeddedId");
				sb.append(CR);
				sb.append("    private int embeddedId;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private " + SUB_TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int oneToMany;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int manyToOne;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int manyToMany;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
			}
		});
	}
	
	private ICompilationUnit createTestAbstractEntityTablePerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)");
				sb.append("abstract");
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

	private ICompilationUnit createAbstractTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)").append(CR);
				sb.append("abstract");
			}
		});
	}
	
	private void createTestIdClass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append("TestTypeId").append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "TestTypeId.java", sourceWriter);
	}

	public void testMorphToMappedSuperclass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = (JavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.getGeneratorContainer().addTableGenerator();
		entity.getGeneratorContainer().addSequenceGenerator();
		entity.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		entity.getQueryContainer().addNamedNativeQuery(0);
		entity.getQueryContainer().addNamedQuery(0);
		
		getJavaPersistentType().setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof MappedSuperclass);
		
		assertNull(typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = (JavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.getGeneratorContainer().addTableGenerator();
		entity.getGeneratorContainer().addSequenceGenerator();
		entity.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		entity.getQueryContainer().addNamedNativeQuery(0);
		entity.getQueryContainer().addNamedQuery(0);
		
		getJavaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Embeddable);
		
		assertNull(typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = (JavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.getGeneratorContainer().addTableGenerator();
		entity.getGeneratorContainer().addSequenceGenerator();
		entity.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		entity.getQueryContainer().addNamedNativeQuery(0);
		entity.getQueryContainer().addNamedQuery(0);
		
		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		assertNull(typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}
	
	public void testAccessNoAnnotations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
	}

	public void testAccessAnnotationsOnSuper() throws Exception {
		createTestEntityAnnotationOnProperty();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME_ + "AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
			
		JavaPersistentType childPersistentType = getJavaPersistentType();
		PersistentType superPersistentType = childPersistentType.getSuperPersistentType();
		
		assertEquals(AccessType.PROPERTY, superPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());		
		
		((IdMapping) superPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourcePersistentType resourceType = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		resourceType.persistableProperties().next().removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, superPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());

		((BasicMapping) superPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		assertEquals(AccessType.FIELD, superPersistentType.getAccess());
		assertEquals(AccessType.FIELD, childPersistentType.getAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, superPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());
		
		getEntityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, superPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//only parent specified in orm.xml, i think this outcome is right??
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		assertEquals(AccessType.FIELD, childPersistentType.getAccess());

		OrmPersistentType childOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		removeXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		//both parent and child specified in orm.xml
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		assertEquals(AccessType.FIELD, childOrmPersistentType.getJavaPersistentType().getAccess());
	}
	
	public void testAccessWithXmlSettings() throws Exception {
		createTestEntityAnnotationOnProperty();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
				
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
			
		((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		typeResource.persistableProperties().next().removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		//no mapping(Id) annotation, but still a Column annotation, so access should still be property
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
		
		((BasicMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
		
		getEntityMappings().setSpecifiedAccess(AccessType.FIELD);
		//still accessType of PROPERTY because the java class is not specified in this orm.xml
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//now class is specified in orm.xml, so entityMappings access setting wins over persistence-unit-defaults
		assertEquals(AccessType.FIELD, ormPersistentType.getJavaPersistentType().getAccess());
		
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		
		//accessType should be PROPERTY now, java gets the access from xml entity if it is specified
		assertEquals(AccessType.PROPERTY, ormPersistentType.getJavaPersistentType().getAccess());
	}	
	
	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaEntity().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(ENTITY_NAME, getJavaEntity().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, getJavaEntity().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, getJavaEntity().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, getJavaEntity().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(ENTITY_NAME, getJavaEntity().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaEntity().setSpecifiedName("foo");
		
		assertEquals("foo", getJavaEntity().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("foo", ((EntityAnnotation) typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME)).getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaEntity().setSpecifiedName(null);
		
		assertNull(getJavaEntity().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(((EntityAnnotation) typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME)).getName());
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EntityAnnotation entity = (EntityAnnotation) typeResource.getAnnotation(EntityAnnotation.ANNOTATION_NAME);
		entity.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", getJavaEntity().getSpecifiedName());
	}

	public void testGetTableName() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithName();
	
		assertEquals(ENTITY_NAME, getJavaEntity().getPrimaryTableName());
	}
	
	public void testGetTableName2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TYPE_NAME, getJavaEntity().getPrimaryTableName());
	}
	
	public void testGetTableName3() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(TABLE_NAME, getJavaEntity().getPrimaryTableName());
	}	
	
	public void testSetTableNameWithNullTable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Table table = getJavaEntity().getTable();
		assertEquals(TYPE_NAME, table.getName());
		assertSourceDoesNotContain("@Table", cu);
		
		table.setSpecifiedName(TABLE_NAME);
		assertSourceContains("@Table(name = \"" + TABLE_NAME + "\")", cu);
		
		assertEquals(TABLE_NAME, getJavaEntity().getPrimaryTableName());
		assertEquals(TABLE_NAME, table.getName());

		table.setSpecifiedCatalog(TABLE_NAME);
	}
		
	public void testGetInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(InheritanceType.TABLE_PER_CLASS, getJavaEntity().getInheritanceStrategy());		
	}
	
	public void testGetDefaultInheritanceStrategy() throws Exception {
		createTestEntity();
		createTestSubType();
				
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		Entity childEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		Entity rootEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		
		assertNotSame(childEntity, rootEntity);
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		rootEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);

		assertEquals(InheritanceType.SINGLE_TABLE, rootEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, childEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, childEntity.getInheritanceStrategy());
		assertNull(childEntity.getSpecifiedInheritanceStrategy());
	}
	
	public void testGetSpecifiedInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(InheritanceType.TABLE_PER_CLASS, getJavaEntity().getSpecifiedInheritanceStrategy());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) typeResource.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME);

		inheritance.setStrategy(org.eclipse.jpt.core.resource.java.InheritanceType.JOINED);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(InheritanceType.JOINED, getJavaEntity().getSpecifiedInheritanceStrategy());
		
		inheritance.setStrategy(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(getJavaEntity().getSpecifiedInheritanceStrategy());
	}
	
	public void testSetSpecifiedInheritanceStrategy() throws Exception {
		createTestEntityWithInheritance();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(InheritanceType.TABLE_PER_CLASS, getJavaEntity().getSpecifiedInheritanceStrategy());

		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		
		assertEquals(InheritanceType.JOINED, getJavaEntity().getSpecifiedInheritanceStrategy());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) typeResource.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.core.resource.java.InheritanceType.JOINED, inheritance.getStrategy());
		
	}
	
	public void testGetDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_VALUE, getJavaEntity().getDiscriminatorValue());		
	}
	
	public void testGetDefaultDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(null, getJavaEntity().getDefaultDiscriminatorValue());
		
		createTestSubType();
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		assertEquals(getJavaEntity().getName(), getJavaEntity().getDefaultDiscriminatorValue());

		getJavaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.INTEGER);
		assertNull(getJavaEntity().getDefaultDiscriminatorValue());
	}
	
	public void testGetSpecifiedDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_VALUE, getJavaEntity().getSpecifiedDiscriminatorValue());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) typeResource.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);

		discriminatorValue.setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", getJavaEntity().getSpecifiedDiscriminatorValue());
		
		discriminatorValue.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(getJavaEntity().getSpecifiedDiscriminatorValue());
	}
	
	public void testSetSpecifiedDiscriminatorValue() throws Exception {
		createTestEntityWithDiscriminatorValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(DISCRIMINATOR_VALUE, getJavaEntity().getSpecifiedDiscriminatorValue());

		getJavaEntity().setSpecifiedDiscriminatorValue("foo");
		
		assertEquals("foo", getJavaEntity().getSpecifiedDiscriminatorValue());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) typeResource.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
		assertEquals("foo", discriminatorValue.getValue());
	}

	public void testSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().secondaryTables();
		
		assertTrue(secondaryTables.hasNext());
		assertEquals("foo", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.secondaryTablesSize());

		((SecondaryTableAnnotation) typeResource.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.secondaryTablesSize());
	}
	
	public void testSpecifiedSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> specifiedSecondaryTables = getJavaEntity().specifiedSecondaryTables();
		
		assertTrue(specifiedSecondaryTables.hasNext());
		assertEquals("foo", specifiedSecondaryTables.next().getName());
		assertEquals("bar", specifiedSecondaryTables.next().getName());
		assertFalse(specifiedSecondaryTables.hasNext());
	}
	
	public void testSpecifiedSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.specifiedSecondaryTablesSize());

		((SecondaryTableAnnotation) typeResource.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.specifiedSecondaryTablesSize());
	}

	public void testAddSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> secondaryTables = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAddSpecifiedSecondaryTable2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> secondaryTables = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAddSpecifiedSecondaryTablePreservePkJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SecondaryTable specifiedSecondaryTable = getJavaEntity().addSpecifiedSecondaryTable(0);
		specifiedSecondaryTable.setSpecifiedName("FOO");
		specifiedSecondaryTable.setSpecifiedCatalog("CATALOG");
		specifiedSecondaryTable.setSpecifiedSchema("SCHEMA");
		specifiedSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("PK_NAME");
		
		//add another specified secondary table, pkJoinColumns from first should be saved.
		SecondaryTable specifiedSecondaryTable2 = getJavaEntity().addSpecifiedSecondaryTable(1);
		specifiedSecondaryTable2.setSpecifiedName("BAR");
		
		
		Iterator<JavaSecondaryTable> secondaryTables = getJavaEntity().secondaryTables();
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
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)));

		getJavaEntity().removeSpecifiedSecondaryTable(1);
		
		Iterator<NestableAnnotation> secondaryTableResources = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());
		assertFalse(secondaryTableResources.hasNext());
		
		Iterator<JavaSecondaryTable> secondaryTables = getJavaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());		
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		
		getJavaEntity().removeSpecifiedSecondaryTable(1);
		secondaryTableResources = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertFalse(secondaryTableResources.hasNext());

		secondaryTables = getJavaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		
		getJavaEntity().removeSpecifiedSecondaryTable(0);
		secondaryTableResources = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertFalse(secondaryTableResources.hasNext());
		secondaryTables = getJavaEntity().secondaryTables();
		assertFalse(secondaryTables.hasNext());

		assertNull(typeResource.getAnnotation(SecondaryTablesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		entity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		Iterator<NestableAnnotation> javaSecondaryTables = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaSecondaryTables));
		
		
		entity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<JavaSecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());


		entity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = typeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
	}
	
	public void testUpdateSpecifiedSecondaryTables() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((SecondaryTableAnnotation) typeResource.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) typeResource.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) typeResource.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaSecondaryTable> secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.moveAnnotation(2, 0, SecondaryTablesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.moveAnnotation(0, 1, SecondaryTablesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		typeResource.removeAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		typeResource.removeAnnotation(0,  SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.specifiedSecondaryTables();
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testAssociatedTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(getJavaEntity().associatedTables()));
		Iterator<ReadOnlyTable> associatedTables = getJavaEntity().associatedTables();
		ReadOnlyTable table1 = associatedTables.next();
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		Entity childEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		Entity rootEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();

		assertEquals(3, CollectionTools.size(rootEntity.allAssociatedTables()));
		Iterator<ReadOnlyTable> associatedTables = rootEntity.allAssociatedTables();
		ReadOnlyTable table1 = associatedTables.next();
		SecondaryTable table2 = (SecondaryTable) associatedTables.next();
		SecondaryTable table3 = (SecondaryTable) associatedTables.next();
		assertEquals(TYPE_NAME, table1.getName());
		assertEquals("foo", table2.getName());
		assertEquals("bar", table3.getName());

		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.allAssociatedTables()));
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestEntityWithSecondaryTables();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		Entity childEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		Entity rootEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		
		assertEquals(3, CollectionTools.size(rootEntity.allAssociatedTableNames()));
		Iterator<String> associatedTables = rootEntity.allAssociatedTableNames();
		String table1 = associatedTables.next();
		String table2 = associatedTables.next();
		String table3 = associatedTables.next();
		assertEquals(TYPE_NAME, table1);
		assertEquals("foo", table2);
		assertEquals("bar", table3);
		
		//TODO probably want this to be 3, since in this case the child descriptor really uses the
		//parent table because it is single table inheritance strategy.  Not sure yet how to deal with this.
		assertEquals(4, CollectionTools.size(childEntity.allAssociatedTableNames()));
	}
	
	public void testAddSecondaryTableToResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, getJavaEntity().secondaryTablesSize());
		assertEquals("FOO", getJavaEntity().secondaryTables().next().getSpecifiedName());
		assertEquals("FOO", getJavaEntity().secondaryTables().next().getName());

		SecondaryTableAnnotation secondaryTable2 = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable2.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(2, getJavaEntity().secondaryTablesSize());
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().secondaryTables();
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());

		SecondaryTableAnnotation secondaryTable3 = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable3.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(3, getJavaEntity().secondaryTablesSize());
		secondaryTables = getJavaEntity().secondaryTables();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
	}
	
	public void testRemoveSecondaryTableFromResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("baz");
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().secondaryTables();
		
		assertEquals(3, getJavaEntity().secondaryTablesSize());
		assertEquals("foo", secondaryTables.next().getSpecifiedName());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(2, getJavaEntity().secondaryTablesSize());
		secondaryTables = getJavaEntity().secondaryTables();
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		getJpaProject().synchronizeContextModel();
		
		secondaryTables = getJavaEntity().secondaryTables();
		assertEquals(1, getJavaEntity().secondaryTablesSize());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
		
		
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		getJpaProject().synchronizeContextModel();
		
		secondaryTables = getJavaEntity().secondaryTables();
		assertEquals(0, getJavaEntity().secondaryTablesSize());
		assertFalse(secondaryTables.hasNext());
	}	
	
	public void testGetSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertEquals(0, getJavaEntity().getPersistenceUnit().generatorsSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertEquals(2, typeResource.annotationsSize());
		assertEquals(1, getJavaEntity().getPersistenceUnit().generatorsSize());
		
		getJavaEntity().getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, getJavaEntity().getPersistenceUnit().generatorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		
		getJavaEntity().getGeneratorContainer().addSequenceGenerator();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.getAnnotation(JPA.SEQUENCE_GENERATOR));
		assertNotNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		
		//try adding another sequence generator, should get an IllegalStateException
		try {
			getJavaEntity().getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaEntity().getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertNull(typeResource.getAnnotation(JPA.SEQUENCE_GENERATOR));

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			getJavaEntity().getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		assertEquals(0, getJavaEntity().getPersistenceUnit().generatorsSize());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getGeneratorContainer().getTableGenerator());		
		assertEquals(2, typeResource.annotationsSize());
		assertEquals(1, getJavaEntity().getPersistenceUnit().generatorsSize());
		
		getJavaEntity().getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, getJavaEntity().getPersistenceUnit().generatorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		
		getJavaEntity().getGeneratorContainer().addTableGenerator();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertNotNull(typeResource.getAnnotation(JPA.TABLE_GENERATOR));
		assertNotNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		
		//try adding another table generator, should get an IllegalStateException
		try {
			getJavaEntity().getGeneratorContainer().addTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaEntity().getGeneratorContainer().removeTableGenerator();
		
		assertNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		assertNull(typeResource.getAnnotation(JPA.TABLE_GENERATOR));
		
		//try removing the table generator again, should get an IllegalStateException
		try {
			getJavaEntity().getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetDiscriminatorColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotNull(getJavaEntity().getDiscriminatorColumn());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		column.setName(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedName());

		typeResource.removeAnnotation(JPA.DISCRIMINATOR_COLUMN);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getDiscriminatorColumn());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());


		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		pkJoinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		typeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, getJavaEntity().specifiedPrimaryKeyJoinColumnsSize());
	
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, getJavaEntity().specifiedPrimaryKeyJoinColumnsSize());
	}

	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		//just the default pkJoinColumn, so 1
		assertEquals(1, getJavaEntity().primaryKeyJoinColumnsSize());
	
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		//only the specified pkJoinColumns, 3
		assertEquals(3, getJavaEntity().primaryKeyJoinColumnsSize());
	}

	public void testGetDefaultPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
	
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> pkJoinColumns = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<NestableAnnotation> pkJoinColumns = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertFalse(pkJoinColumns.hasNext());
	}
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, CollectionTools.size(typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)));

		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<NestableAnnotation> pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<JavaPrimaryKeyJoinColumn> pkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = getJavaEntity().specifiedPrimaryKeyJoinColumns();
		assertFalse(pkJoinColumns.hasNext());

		assertNull(typeResource.getAnnotation(PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		entity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		entity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<NestableAnnotation> javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaPrimaryKeyJoinColumns));
		
		
		entity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());


		entity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = typeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
	}
	
	public void testUpdateSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("FOO");
		((PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAR");
		((PrimaryKeyJoinColumnAnnotation) typeResource.addAnnotation(2, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.moveAnnotation(2, 0, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.moveAnnotation(0, 1, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		typeResource.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		typeResource.removeAnnotation(0,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.specifiedPrimaryKeyJoinColumns();
		assertFalse(primaryKeyJoinColumns.hasNext());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertTrue(getJavaEntity().getDefaultPrimaryKeyJoinColumn().isDefault());

		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0);
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().specifiedPrimaryKeyJoinColumns().next();
		assertFalse(specifiedPkJoinColumn.isDefault());
		
		assertNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertFalse(getJavaEntity().tableNameIsInvalid(TYPE_NAME));
		assertTrue(getJavaEntity().tableNameIsInvalid("FOO"));
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		
		assertFalse(getJavaEntity().tableNameIsInvalid("BAR"));
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = (Entity) getJavaPersistentType().getMapping();
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
	
		Iterator<String> overridableAttributes = getJavaEntity().overridableAttributeNames();
		assertFalse(overridableAttributes.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = getJavaEntity().overridableAttributeNames();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
		assertEquals("foo", overridableAttributes.next());
		assertEquals("basic", overridableAttributes.next());
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAttributesTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAttributes = abstractEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAssociationsTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAssociations = getJavaEntity().allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAssociations = abstractEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
		assertEquals("foo", overridableAttributes.next());
		assertEquals("basic", overridableAttributes.next());
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().allOverridableAttributeNames();
		assertEquals("foo", overridableAttributeNames.next());
		assertEquals("basic", overridableAttributeNames.next());
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testVirtualAttributeOverrideDefaults() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertEquals("foo", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		
		BasicMapping fooMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("foo").getMapping();
		fooMapping.getColumn().setSpecifiedName("FOO");
		fooMapping.getColumn().setSpecifiedTable("BAR");
		fooMapping.getColumn().setColumnDefinition("COLUMN_DEF");
		fooMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		fooMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		fooMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		fooMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		fooMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		fooMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		fooMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());
		assertEquals("COLUMN_DEF", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());

		fooMapping.getColumn().setSpecifiedName(null);
		fooMapping.getColumn().setSpecifiedTable(null);
		fooMapping.getColumn().setColumnDefinition(null);
		fooMapping.getColumn().setSpecifiedInsertable(null);
		fooMapping.getColumn().setSpecifiedUpdatable(null);
		fooMapping.getColumn().setSpecifiedUnique(null);
		fooMapping.getColumn().setSpecifiedNullable(null);
		fooMapping.getColumn().setSpecifiedLength(null);
		fooMapping.getColumn().setSpecifiedPrecision(null);
		fooMapping.getColumn().setSpecifiedScale(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertEquals("foo", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(3, overrideContainer.virtualOverridesSize());
	}
	
	public void testVirtualAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		JavaEntity superclass = (JavaEntity) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(4, overrideContainer.virtualOverridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(0, overrideContainer.virtualOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(4, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();		
		assertEquals(5, overrideContainer.overridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("basic", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("basic", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(3, CollectionTools.size(typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)));

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		Iterator<NestableAnnotation> attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("basic", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<JavaAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("basic", attributeOverrides.next().getName());		
		assertEquals("id", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("id", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<JavaAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("basic", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("basic", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("id", attributeOverrides.next().getName());
		assertEquals("basic", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("basic", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AttributeOverrideAnnotation) typeResource.addAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualOverrides();	
		JavaVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		AttributeOverride specifiedAttributeOverride = overrideContainer.specifiedOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = overrideContainer.virtualOverrides();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().allOverridableAssociationNames();
		assertEquals("oneToOne", overridableAssociationNames.next());
		assertEquals("manyToOne", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociations() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAssociations = getJavaEntity().allOverridableAssociationNames();
		assertEquals("oneToOne", overridableAssociations.next());
		assertEquals("manyToOne", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}
//	
//	public void testAllOverridableAssociationsMappedSuperclassInOrmXml() throws Exception {
//		createTestMappedSuperclass();
//		createTestSubType();
//		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
//		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		Iterator<RelationshipMapping> overridableAssociations = getJavaEntity().allOverridableAssociations();
//		assertEquals("oneToOne", overridableAssociations.next().getName());
//		assertEquals("manyToOne", overridableAssociations.next().getName());
//		assertFalse(overridableAssociations.hasNext());
//	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrideDefaults() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AssociationOverrideContainer overrideContainer = javaEntity.getAssociationOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.virtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		ReadOnlyJoinColumnJoiningStrategy joiningStrategy = ((ReadOnlyJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnJoiningStrategy();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		ReadOnlyJoinColumn virtualJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("oneToOne_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(SUB_TYPE_NAME, virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		

		OneToOneMapping oneToOneMapping = (OneToOneMapping) getJavaPersistentType().getAttributeNamed("oneToOne").getMapping();
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnJoiningStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTable("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.virtualOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		joiningStrategy = ((ReadOnlyJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnJoiningStrategy();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		virtualJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals("MY_JOIN_COLUMN", joiningStrategy.joinColumns().next().getName());


		
		
		
		
		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		
		
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
		
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(0, overrideContainer.virtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.overridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, overrideContainer.overridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(2, CollectionTools.size(typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)));

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("manyToOne", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("manyToOne", associationOverrides.next().getName());
		assertEquals("oneToOne", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("oneToOne", associationOverrides.next().getName());
		assertEquals("manyToOne", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AssociationOverrideAnnotation) typeResource.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();	
		JavaVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.virtualOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testAddNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		NamedQuery namedQuery1 = entity.getQueryContainer().addNamedQuery(0);
		namedQuery1.setName("FOO");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery2 = entity.getQueryContainer().addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery3 = entity.getQueryContainer().addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery1, namedQueries.next());
		
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		
		entity.getQueryContainer().addNamedNativeQuery(0).setName("foo");
	}
	
	public void testInvalidNamedQueries() throws Exception {
		createTestEntityInvalidNamedQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		assertEquals(1, entity.getQueryContainer().namedQueriesSize());
	}

	public void testRemoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());

		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testAddNamedNativeQueryWithNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		
		
		NamedNativeQueryAnnotation nativeQuery = (NamedNativeQueryAnnotation) typeResource.addAnnotation(0, JPA.NAMED_NATIVE_QUERY, JPA.NAMED_NATIVE_QUERIES);
		nativeQuery.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, entity.getQueryContainer().namedNativeQueriesSize());
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
	}
	
	public void testAddNamedQueryWithNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.addAnnotation(0, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, entity.getQueryContainer().namedQueriesSize());
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
	}

	public void testMoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.getQueryContainer().moveNamedQuery(2, 0);
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());


		entity.getQueryContainer().moveNamedQuery(0, 1);
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, entity.getPersistenceUnit().queriesSize());
		
		((NamedQueryAnnotation) typeResource.addAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) typeResource.addAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) typeResource.addAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, entity.getPersistenceUnit().queriesSize());
		
		typeResource.moveAnnotation(2, 0, NamedQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.moveAnnotation(0, 1, NamedQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, entity.getPersistenceUnit().queriesSize());
		
		typeResource.removeAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, entity.getPersistenceUnit().queriesSize());
		
		typeResource.removeAnnotation(0,  NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedQueries();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, entity.getPersistenceUnit().queriesSize());
	}
	
	public void testNamedQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.getQueryContainer().namedQueriesSize());

		((NamedQueryAnnotation) typeResource.addAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) typeResource.addAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) typeResource.addAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getQueryContainer().namedQueriesSize());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery2 = entity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery3 = entity.getQueryContainer().addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals(namedNativeQuery2, namedQueries.next());
		assertEquals(namedNativeQuery3, namedQueries.next());
		assertEquals(namedNativeQuery, namedQueries.next());
		
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());

		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().moveNamedNativeQuery(2, 0);
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());


		entity.getQueryContainer().moveNamedNativeQuery(0, 1);
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, entity.getPersistenceUnit().queriesSize());
		
		((NamedNativeQueryAnnotation) typeResource.addAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) typeResource.addAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) typeResource.addAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");	
		getJpaProject().synchronizeContextModel();
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, entity.getPersistenceUnit().queriesSize());
		
		typeResource.moveAnnotation(2, 0, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.moveAnnotation(0, 1, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, entity.getPersistenceUnit().queriesSize());
		
		typeResource.removeAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, entity.getPersistenceUnit().queriesSize());
		
		typeResource.removeAnnotation(0,  NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().namedNativeQueries();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, entity.getPersistenceUnit().queriesSize());
	}	
	
	public void testNamedNativeQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		assertEquals(0, entity.getQueryContainer().namedNativeQueriesSize());

		((NamedNativeQueryAnnotation) typeResource.addAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) typeResource.addAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) typeResource.addAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getQueryContainer().namedNativeQueriesSize());
	}

	public void testUpdateIdClass() throws Exception {
		createTestEntity();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		IdClassAnnotation idClassAnnotation = (IdClassAnnotation) typeResource.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNotNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		// test setting id class name to nonexistent class.  test class name is set, but class is null
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		idClassAnnotation.setValue(nonExistentIdClassName);
		getJpaProject().synchronizeContextModel();
		assertEquals(nonExistentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		// test setting id class name to existent class.  test class name is set and class is not null
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		idClassAnnotation.setValue(existentIdClassName);
		getJpaProject().synchronizeContextModel();
		assertEquals(existentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertEquals(existentIdClassName, ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertNotNull(getJavaEntity().getIdClassReference().getIdClass());
		
		//test setting  @IdClass value to null
		idClassAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		//reset @IdClass value and then remove @IdClass
		idClassAnnotation = (IdClassAnnotation) typeResource.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		idClassAnnotation.setValue(existentIdClassName);
		typeResource.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		createTestEntity();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		String nonExistentIdClassName = PACKAGE_NAME_ + "Foo";
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(nonExistentIdClassName);
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(nonExistentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		String existentIdClassName = PACKAGE_NAME_ + "TestTypeId";
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(existentIdClassName);
		assertEquals(existentIdClassName, ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(existentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNotNull(getJavaEntity().getIdClassReference().getIdClass());
		
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(null);
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
	}
	
	public void testGetPrimaryKeyColumnNameWithAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		JavaPersistentType entityPersistentType = classRefs.next().getJavaPersistentType();
		JavaEntity javaEntity = (JavaEntity) entityPersistentType.getMapping();
		JavaPersistentType mappedSuperclassPersistentType = classRefs.next().getJavaPersistentType();
		
		assertEquals("id", javaEntity.getPrimaryKeyColumnName());
		
		((JavaIdMapping) mappedSuperclassPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("MY_ID", javaEntity.getPrimaryKeyColumnName());
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = javaEntity.getAttributeOverrideContainer().virtualOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next();
		JavaVirtualAttributeOverride virtualOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualOverride.getName());
		
		virtualOverride.convertToSpecified().getColumn().setSpecifiedName("ID");
		assertEquals("ID", javaEntity.getPrimaryKeyColumnName());
	}
	
	public void testDiscriminatorValueIsUndefinedConcreteClass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(getJavaEntity().discriminatorValueIsUndefined());
		
		createTestSubType();
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertFalse(getJavaEntity().discriminatorValueIsUndefined());
	}
	
	public void testDiscriminatorValueIsUndefinedAbstractClass() throws Exception {
		createTestAbstractEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(getJavaEntity().discriminatorValueIsUndefined());
		
		createTestSubType();
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertTrue(getJavaEntity().discriminatorValueIsUndefined());
	}
	
	public void testSpecifiedDiscriminatorColumnIsAllowed() throws Exception {
		createAbstractTestEntity();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		Entity concreteEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		assertEquals("AnnotationTestTypeChild", concreteEntity.getName());
		
		Entity abstractEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		assertEquals(TYPE_NAME, abstractEntity.getName());

		//table-per-class, no discriminator column allowed
		assertFalse(concreteEntity.specifiedDiscriminatorColumnIsAllowed());
		assertFalse(abstractEntity.specifiedDiscriminatorColumnIsAllowed());

		
		//single-table, discriminator column allowed on root entity
		abstractEntity.setSpecifiedInheritanceStrategy(null);
		assertFalse(concreteEntity.specifiedDiscriminatorColumnIsAllowed());
		assertTrue(abstractEntity.specifiedDiscriminatorColumnIsAllowed());
	}
	
	public void testAbstractEntityGetDefaultDiscriminatorColumnNameTablePerClassInheritance() throws Exception {
		createAbstractTestEntity();
		createTestSubType();
		
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		Entity concreteEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		assertEquals("AnnotationTestTypeChild", concreteEntity.getName());
		
		Entity abstractEntity = (Entity) specifiedClassRefs.next().getJavaPersistentType().getMapping();
		assertEquals(TYPE_NAME, abstractEntity.getName());
		
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, abstractEntity.getSpecifiedInheritanceStrategy());
		assertEquals(null, concreteEntity.getSpecifiedInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, concreteEntity.getDefaultInheritanceStrategy());
		
		
		assertTrue(abstractEntity.discriminatorValueIsUndefined());
		assertFalse(concreteEntity.specifiedDiscriminatorColumnIsAllowed());
		assertEquals(null, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(null, concreteEntity.getDiscriminatorColumn().getDefaultName());
		
		assertTrue(abstractEntity.discriminatorValueIsUndefined());
		assertEquals(null, abstractEntity.getDefaultDiscriminatorValue());
		assertTrue(concreteEntity.discriminatorValueIsUndefined());
		assertEquals(null, concreteEntity.getDefaultDiscriminatorValue());
	}
}
