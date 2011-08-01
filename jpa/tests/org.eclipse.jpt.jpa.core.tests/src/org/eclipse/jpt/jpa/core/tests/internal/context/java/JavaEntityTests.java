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
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

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
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
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
		
		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = (JavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
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
		
		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = (JavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
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
		
		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME));
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
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
			
		JavaPersistentType childPersistentType = getJavaPersistentType();
		PersistentType superPersistentType = childPersistentType.getSuperPersistentType();
		
		assertEquals(AccessType.PROPERTY, superPersistentType.getAccess());
		assertEquals(AccessType.PROPERTY, childPersistentType.getAccess());		
		
		((IdMapping) superPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		resourceType.getMethods().iterator().next().removeAnnotation(JPA.ID);
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
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
				
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
			
		((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("FOO");
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		resourceType.getMethods().iterator().next().removeAnnotation(JPA.ID);
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertEquals("foo", ((EntityAnnotation) resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME)).getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaEntity().setSpecifiedName(null);
		
		assertNull(getJavaEntity().getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertNull(((EntityAnnotation) resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME)).getName());
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithName();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME);
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME);

		inheritance.setStrategy(org.eclipse.jpt.jpa.core.resource.java.InheritanceType.JOINED);
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		InheritanceAnnotation inheritance = (InheritanceAnnotation) resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.InheritanceType.JOINED, inheritance.getStrategy());
		
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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);

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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
		assertEquals("foo", discriminatorValue.getValue());
	}

	public void testSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		
		assertTrue(secondaryTables.hasNext());
		assertEquals("foo", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	}
	
	public void testSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertEquals(0, entity.getSecondaryTablesSize());

		((SecondaryTableAnnotation) resourceType.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) resourceType.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) resourceType.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getSecondaryTablesSize());
	}
	
	public void testSpecifiedSecondaryTables() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaSecondaryTable> specifiedSecondaryTables = getJavaEntity().getSpecifiedSecondaryTables().iterator();
		
		assertTrue(specifiedSecondaryTables.hasNext());
		assertEquals("foo", specifiedSecondaryTables.next().getName());
		assertEquals("bar", specifiedSecondaryTables.next().getName());
		assertFalse(specifiedSecondaryTables.hasNext());
	}
	
	public void testSpecifiedSecondaryTablesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertEquals(0, entity.getSpecifiedSecondaryTablesSize());

		((SecondaryTableAnnotation) resourceType.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) resourceType.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) resourceType.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getSpecifiedSecondaryTablesSize());
	}

	public void testAddSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> secondaryTables = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> secondaryTables = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		
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
		
		
		Iterator<JavaSecondaryTable> secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		SecondaryTable secondaryTable = secondaryTables.next();
		assertEquals(secondaryTable, specifiedSecondaryTable);
		assertEquals("FOO", secondaryTable.getName());
		assertEquals("CATALOG", secondaryTable.getCatalog());
		assertEquals("SCHEMA", secondaryTable.getSchema());
		assertEquals(1, secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize());
		PrimaryKeyJoinColumn pkJoinColumn = secondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("PK_NAME", pkJoinColumn.getName());
		
		secondaryTable = secondaryTables.next();
		assertEquals(secondaryTable, specifiedSecondaryTable2);
		assertEquals("BAR", secondaryTable.getName());
		assertEquals(0, secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize());
			
	}
	
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertEquals(3, resourceType.getAnnotationsSize(SecondaryTableAnnotation.ANNOTATION_NAME));

		getJavaEntity().removeSpecifiedSecondaryTable(1);
		
		Iterator<NestableAnnotation> secondaryTableResources = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());
		assertFalse(secondaryTableResources.hasNext());
		
		Iterator<JavaSecondaryTable> secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("FOO", secondaryTables.next().getName());		
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		
		getJavaEntity().removeSpecifiedSecondaryTable(1);
		secondaryTableResources = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTableResources.next()).getName());		
		assertFalse(secondaryTableResources.hasNext());

		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		
		getJavaEntity().removeSpecifiedSecondaryTable(0);
		secondaryTableResources = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(secondaryTableResources.hasNext());
		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertFalse(secondaryTables.hasNext());

		assertNull(resourceType.getAnnotation(SecondaryTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		entity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		Iterator<NestableAnnotation> javaSecondaryTables = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaSecondaryTables));
		
		
		entity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<JavaSecondaryTable> secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());


		entity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());

		javaSecondaryTables = resourceType.getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) javaSecondaryTables.next()).getName());
	}
	
	public void testUpdateSpecifiedSecondaryTables() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((SecondaryTableAnnotation) resourceType.addAnnotation(0, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("FOO");
		((SecondaryTableAnnotation) resourceType.addAnnotation(1, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAR");
		((SecondaryTableAnnotation) resourceType.addAnnotation(2, SecondaryTableAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaSecondaryTable> secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		resourceType.moveAnnotation(2, 0, SecondaryTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		resourceType.moveAnnotation(0, 1, SecondaryTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		resourceType.removeAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
	
		resourceType.removeAnnotation(1,  SecondaryTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		resourceType.removeAnnotation(0,  SecondaryTableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		secondaryTables = entity.getSpecifiedSecondaryTables().iterator();
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable.setName("FOO");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, getJavaEntity().getSecondaryTablesSize());
		assertEquals("FOO", getJavaEntity().getSecondaryTables().iterator().next().getSpecifiedName());
		assertEquals("FOO", getJavaEntity().getSecondaryTables().iterator().next().getName());

		SecondaryTableAnnotation secondaryTable2 = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTable2.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(2, getJavaEntity().getSecondaryTablesSize());
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());

		SecondaryTableAnnotation secondaryTable3 = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable3.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(3, getJavaEntity().getSecondaryTablesSize());
		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("BAZ", secondaryTables.next().getSpecifiedName());
		assertEquals("FOO", secondaryTables.next().getSpecifiedName());
		assertEquals("BAR", secondaryTables.next().getSpecifiedName());
	}
	
	public void testRemoveSecondaryTableFromResourceModel() throws Exception {
		createTestEntityWithSecondaryTables();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().addSpecifiedSecondaryTable(2).setSpecifiedName("baz");
		ListIterator<JavaSecondaryTable> secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		
		assertEquals(3, getJavaEntity().getSecondaryTablesSize());
		assertEquals("foo", secondaryTables.next().getSpecifiedName());
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(2, getJavaEntity().getSecondaryTablesSize());
		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals("bar", secondaryTables.next().getSpecifiedName());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
	
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		getJpaProject().synchronizeContextModel();
		
		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals(1, getJavaEntity().getSecondaryTablesSize());
		assertEquals("baz", secondaryTables.next().getSpecifiedName());
		
		
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		getJpaProject().synchronizeContextModel();
		
		secondaryTables = getJavaEntity().getSecondaryTables().iterator();
		assertEquals(0, getJavaEntity().getSecondaryTablesSize());
		assertFalse(secondaryTables.hasNext());
	}	
	
	public void testGetSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertEquals(0, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertEquals(2, resourceType.getAnnotationsSize());
		assertEquals(1, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
		
		getJavaEntity().getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		
		getJavaEntity().getGeneratorContainer().addSequenceGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertNotNull(resourceType.getAnnotation(JPA.SEQUENCE_GENERATOR));
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaEntity().getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(getJavaEntity().getGeneratorContainer().getSequenceGenerator());
		assertNull(resourceType.getAnnotation(JPA.SEQUENCE_GENERATOR));

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
		assertEquals(0, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getGeneratorContainer().getTableGenerator());		
		assertEquals(2, resourceType.getAnnotationsSize());
		assertEquals(1, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
		
		getJavaEntity().getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, getJavaEntity().getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		
		getJavaEntity().getGeneratorContainer().addTableGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertNotNull(resourceType.getAnnotation(JPA.TABLE_GENERATOR));
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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaEntity().getGeneratorContainer().removeTableGenerator();
		
		assertNull(getJavaEntity().getGeneratorContainer().getTableGenerator());
		assertNull(resourceType.getAnnotation(JPA.TABLE_GENERATOR));
		
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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.addAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		column.setName(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedName());

		resourceType.removeAnnotation(JPA.DISCRIMINATOR_COLUMN);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaEntity().getDiscriminatorColumn());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaPrimaryKeyJoinColumn> specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();
		
		assertFalse(specifiedPkJoinColumns.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		pkJoinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		pkJoinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());


		pkJoinColumn = (PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		pkJoinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceType.moveAnnotation(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedPkJoinColumns.next().getName());
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		resourceType.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAZ", specifiedPkJoinColumns.next().getName());
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());
	
		resourceType.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertEquals("FOO", specifiedPkJoinColumns.next().getName());
		assertFalse(specifiedPkJoinColumns.hasNext());

		
		resourceType.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();		
		assertFalse(specifiedPkJoinColumns.hasNext());
	}
	
	public void testSpecifiedPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, getJavaEntity().getSpecifiedPrimaryKeyJoinColumnsSize());
	
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		assertEquals(3, getJavaEntity().getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		//just the default pkJoinColumn, so 1
		assertEquals(1, getJavaEntity().getPrimaryKeyJoinColumnsSize());
	
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		//only the specified pkJoinColumns, 3
		assertEquals(3, getJavaEntity().getPrimaryKeyJoinColumnsSize());
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> pkJoinColumns = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> pkJoinColumns = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertEquals(3, resourceType.getAnnotationsSize(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));

		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		
		Iterator<NestableAnnotation> pkJoinColumnResources = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());
		assertFalse(pkJoinColumnResources.hasNext());
		
		Iterator<JavaPrimaryKeyJoinColumn> pkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumns.next().getName());		
		assertEquals("BAZ", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());
	
		
		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(1);
		pkJoinColumnResources = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumnResources.next()).getName());		
		assertFalse(pkJoinColumnResources.hasNext());

		pkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", pkJoinColumns.next().getName());
		assertFalse(pkJoinColumns.hasNext());

		
		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumnResources = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(pkJoinColumnResources.hasNext());
		pkJoinColumns = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertFalse(pkJoinColumns.hasNext());

		assertNull(resourceType.getAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		entity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		entity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Iterator<NestableAnnotation> javaPrimaryKeyJoinColumns = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaPrimaryKeyJoinColumns));
		
		
		entity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());


		entity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaPrimaryKeyJoinColumns = resourceType.getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaPrimaryKeyJoinColumns.next()).getName());
	}
	
	public void testUpdateSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME)).setName("FOO");
		((PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME)).setName("BAR");
		((PrimaryKeyJoinColumnAnnotation) resourceType.addAnnotation(2, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		resourceType.moveAnnotation(2, 0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		resourceType.moveAnnotation(0, 1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		resourceType.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		resourceType.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		resourceType.removeAnnotation(0,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = entity.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertFalse(primaryKeyJoinColumns.hasNext());
	}
	
	public void testPrimaryKeyJoinColumnIsVirtual() throws Exception {
		createTestEntityWithSecondaryTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertTrue(getJavaEntity().getDefaultPrimaryKeyJoinColumn().isDefault());

		getJavaEntity().addSpecifiedPrimaryKeyJoinColumn(0);
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
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
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceType.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testVirtualAttributeOverrideDefaults() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
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
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
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
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
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
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testVirtualAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		JavaEntity superclass = (JavaEntity) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(4, overrideContainer.getVirtualOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(0, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(4, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();		
		assertEquals(5, overrideContainer.getOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> attributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
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

		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> attributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(3, CollectionTools.size(resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME)));

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		Iterator<NestableAnnotation> attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("basic", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<JavaAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("basic", attributeOverrides.next().getName());		
		assertEquals("id", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("id", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());

		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		
		Iterator<NestableAnnotation> javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<JavaAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("basic", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());

		javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("basic", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("id", attributeOverrides.next().getName());
		assertEquals("basic", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());

		javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("basic", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((AttributeOverrideAnnotation) resourceType.addAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) resourceType.addAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) resourceType.addAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		resourceType.moveAnnotation(2, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.moveAnnotation(0, 1, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		resourceType.removeAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();

		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
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
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceType.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrideDefaults() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AssociationOverrideContainer overrideContainer = javaEntity.getAssociationOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		ReadOnlyJoinColumnRelationshipStrategy joiningStrategy = ((ReadOnlyJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		ReadOnlyJoinColumn virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("oneToOne_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(SUB_TYPE_NAME, virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		

		OneToOneMapping oneToOneMapping = (OneToOneMapping) getJavaPersistentType().getAttributeNamed("oneToOne").getMapping();
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTable("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		joiningStrategy = ((ReadOnlyJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals("MY_JOIN_COLUMN", joiningStrategy.getJoinColumns().iterator().next().getName());


		
		
		
		
		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		
		
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
////	assertEquals(SUB_TYPE_NAME, resourceType.getName());
////	assertNull(resourceType.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(resourceType.annotation(AssociationOverrides.ANNOTATION_NAME));
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
////	assertEquals(SUB_TYPE_NAME, resourceType.getName());
////	assertNull(resourceType.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(resourceType.annotation(AssociationOverrides.ANNOTATION_NAME));
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
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(0, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, overrideContainer.getOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> associationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		
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
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> associationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		
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
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(2, resourceType.getAnnotationsSize(AssociationOverrideAnnotation.ANNOTATION_NAME));

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("manyToOne", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		associationOverrideResources = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());

		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		
		Iterator<NestableAnnotation> javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("manyToOne", associationOverrides.next().getName());
		assertEquals("oneToOne", associationOverrides.next().getName());

		javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("oneToOne", associationOverrides.next().getName());
		assertEquals("manyToOne", associationOverrides.next().getName());

		javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("oneToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("manyToOne", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((AssociationOverrideAnnotation) resourceType.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) resourceType.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) resourceType.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceType.moveAnnotation(2, 0, AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.moveAnnotation(0, 1, AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceType.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
		JavaVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("oneToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testAddNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		NamedQuery namedQuery1 = entity.getQueryContainer().addNamedQuery(0);
		namedQuery1.setName("FOO");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery2 = entity.getQueryContainer().addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedQuery namedQuery3 = entity.getQueryContainer().addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery1, namedQueries.next());
		
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		
		entity.getQueryContainer().addNamedNativeQuery(0).setName("foo");
	}
	
	public void testInvalidNamedQueries() throws Exception {
		createTestEntityInvalidNamedQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		assertEquals(1, entity.getQueryContainer().getNamedQueriesSize());
	}

	public void testRemoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());

		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.getQueryContainer().removeNamedQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testAddNamedNativeQueryWithNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		
		
		NamedNativeQueryAnnotation nativeQuery = (NamedNativeQueryAnnotation) resourceType.addAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		nativeQuery.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, entity.getQueryContainer().getNamedNativeQueriesSize());
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
	}
	
	public void testAddNamedQueryWithNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.addAnnotation(0, JPA.NAMED_QUERY);
		namedQuery.setName("BAR");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(1, entity.getQueryContainer().getNamedQueriesSize());
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
	}

	public void testMoveNamedQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		entity.getQueryContainer().moveNamedQuery(2, 0);
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());


		entity.getQueryContainer().moveNamedQuery(0, 1);
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = resourceType.getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertEquals(0, entity.getPersistenceUnit().getQueriesSize());
		
		((NamedQueryAnnotation) resourceType.addAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) resourceType.addAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) resourceType.addAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
		ListIterator<JavaNamedQuery> namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.moveAnnotation(2, 0, NamedQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		resourceType.moveAnnotation(0, 1, NamedQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		resourceType.removeAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.removeAnnotation(1,  NamedQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.removeAnnotation(0,  NamedQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedQueries().iterator();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, entity.getPersistenceUnit().getQueriesSize());
	}
	
	public void testNamedQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertEquals(0, entity.getQueryContainer().getNamedQueriesSize());

		((NamedQueryAnnotation) resourceType.addAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedQueryAnnotation) resourceType.addAnnotation(1, NamedQueryAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedQueryAnnotation) resourceType.addAnnotation(2, NamedQueryAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getQueryContainer().getNamedQueriesSize());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery2 = entity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		NamedNativeQuery namedNativeQuery3 = entity.getQueryContainer().addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals(namedNativeQuery2, namedQueries.next());
		assertEquals(namedNativeQuery3, namedQueries.next());
		assertEquals(namedNativeQuery, namedQueries.next());
		
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());

		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		
		entity.getQueryContainer().removeNamedNativeQuery(0);
		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		entity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		entity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		entity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		Iterator<NestableAnnotation> javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		entity.getQueryContainer().moveNamedNativeQuery(2, 0);
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());


		entity.getQueryContainer().moveNamedNativeQuery(0, 1);
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = resourceType.getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("BAR", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
		assertEquals("FOO", ((NamedNativeQueryAnnotation) javaNamedQueries.next()).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertEquals(0, entity.getPersistenceUnit().getQueriesSize());
		
		((NamedNativeQueryAnnotation) resourceType.addAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) resourceType.addAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) resourceType.addAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("BAZ");	
		getJpaProject().synchronizeContextModel();
		ListIterator<JavaNamedNativeQuery> namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.moveAnnotation(2, 0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		resourceType.moveAnnotation(0, 1, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		resourceType.removeAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.removeAnnotation(1,  NamedNativeQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, entity.getPersistenceUnit().getQueriesSize());
		
		resourceType.removeAnnotation(0,  NamedNativeQueryAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		namedQueries = entity.getQueryContainer().getNamedNativeQueries().iterator();
		assertFalse(namedQueries.hasNext());
		assertEquals(0, entity.getPersistenceUnit().getQueriesSize());
	}	
	
	public void testNamedNativeQueriesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEntity entity = getJavaEntity();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertEquals(0, entity.getQueryContainer().getNamedNativeQueriesSize());

		((NamedNativeQueryAnnotation) resourceType.addAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("FOO");
		((NamedNativeQueryAnnotation) resourceType.addAnnotation(1, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("BAR");
		((NamedNativeQueryAnnotation) resourceType.addAnnotation(2, NamedNativeQueryAnnotation.ANNOTATION_NAME)).setName("BAZ");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(3, entity.getQueryContainer().getNamedNativeQueriesSize());
	}

	public void testUpdateIdClass() throws Exception {
		createTestEntity();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		IdClassAnnotation idClassAnnotation = (IdClassAnnotation) resourceType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNotNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		// test setting id class name to nonexistent class.  test class name is set, but class is null
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		idClassAnnotation.setValue(nonExistentIdClassName);
		getJpaProject().synchronizeContextModel();
		assertEquals(nonExistentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		// test setting id class name to existent class.  test class name is set and class is not null
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		idClassAnnotation.setValue(existentIdClassName);
		getJpaProject().synchronizeContextModel();
		assertEquals(existentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertEquals(existentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertNotNull(getJavaEntity().getIdClassReference().getIdClass());
		
		//test setting  @IdClass value to null
		idClassAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		//reset @IdClass value and then remove @IdClass
		idClassAnnotation = (IdClassAnnotation) resourceType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		idClassAnnotation.setValue(existentIdClassName);
		resourceType.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		createTestEntity();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		String nonExistentIdClassName = PACKAGE_NAME_ + "Foo";
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(nonExistentIdClassName);
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(nonExistentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
		
		String existentIdClassName = PACKAGE_NAME_ + "TestTypeId";
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(existentIdClassName);
		assertEquals(existentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(existentIdClassName, getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNotNull(getJavaEntity().getIdClassReference().getIdClass());
		
		getJavaEntity().getIdClassReference().setSpecifiedIdClassName(null);
		assertNull(getJavaEntity().getIdClassReference().getSpecifiedIdClassName());
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(getJavaEntity().getIdClassReference().getIdClass());
	}
	
	public void testGetPrimaryKeyColumnNameWithAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		JavaPersistentType entityPersistentType = classRefs.next().getJavaPersistentType();
		JavaEntity javaEntity = (JavaEntity) entityPersistentType.getMapping();
		JavaPersistentType mappedSuperclassPersistentType = classRefs.next().getJavaPersistentType();
		
		assertEquals("id", javaEntity.getPrimaryKeyColumnName());
		
		((JavaIdMapping) mappedSuperclassPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("MY_ID", javaEntity.getPrimaryKeyColumnName());
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = javaEntity.getAttributeOverrideContainer().getVirtualOverrides().iterator();
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
