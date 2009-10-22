/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmEntityTests extends ContextModelTestCase
{
	
	protected static final String CHILD_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_CHILD_TYPE_NAME = PACKAGE_NAME + "." + CHILD_TYPE_NAME;

	public OrmEntityTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntityDefaultFieldAccess() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private ICompilationUnit createTestEntityFieldAccess() throws Exception {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private ICompilationUnit createTestEntityPropertyAccess() throws Exception {
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
				sb.append("public class ").append(CHILD_TYPE_NAME).append(" ");
				sb.append("extends ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	private void createTestSubTypeUnmapped() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(CHILD_TYPE_NAME).append(" ");
				sb.append("extends ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
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
				sb.append("    private " + CHILD_TYPE_NAME + " oneToOne;").append(CR);
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
	
	private void createTestAbstractType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public abstract class ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, sourceWriter);
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

	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the resource model, verify context model updated
		entityResource.setName("foo");
		assertEquals("foo", ormEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
	
		//set name to null in the resource model
		entityResource.setName(null);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the context model, verify resource model modified
		ormEntity.setSpecifiedName("foo");
		assertEquals("foo", ormEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
		
		//set name to null in the context model
		ormEntity.setSpecifiedName(null);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}

	public void testUpdateDefaultName() throws Exception {
		createTestEntityFieldAccess();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		ormEntity.getJavaEntity().setSpecifiedName("Foo");
		//xml default entity name comes from java
		assertEquals("Foo", ormEntity.getDefaultName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", ormEntity.getDefaultName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(ormEntity.getDefaultName());
		
		entityResource.setClassName(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals("Foo", ormEntity.getDefaultName());
		entityResource.setMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		ormEntity.getJavaEntity().setSpecifiedName("Foo1");
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		entityResource.setMetadataComplete(null);
		assertEquals("Foo1", ormEntity.getDefaultName());
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals("Foo", ormEntity.getName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", ormEntity.getName());
		
		entityResource.setName("Baz");
		assertEquals("Baz", ormEntity.getName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertEquals("Baz", ormEntity.getName());
		
		entityResource.setName(null);
		assertNull(ormEntity.getName());
	}

	public void testUpdateClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("com.Bar", ormEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
	
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(ormEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the context model, verify resource model modified
		ormEntity.setClass("com.Bar");
		assertEquals("com.Bar", ormEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
		
		//set class to null in the context model
		ormEntity.setClass(null);
		assertNull(ormEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the resource model, verify context model updated
		entityResource.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormPersistentType.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, entityResource.getAccess());
	
		//set access to null in the resource model
		entityResource.setAccess(null);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the context model, verify resource model modified
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entityResource.getAccess());
		
		//set access to null in the context model
		ormPersistentType.setSpecifiedAccess(null);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromPersistenceUnitDefaults() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		assertNull(entityResource.getAccess());
		
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		//java has no annotations, defaultAccess in xml is FIELD anyway
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().setSpecifiedAccess(AccessType.FIELD);
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);		
		//entityMappings access wins over persistence-unit-defaults access
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().setSpecifiedAccess(null);		
		//persistence-unit-defaults access used now
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		//java has annotations on fields now, that should win in all cases
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());

		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
	}

	public void testUpdateDefaultAccessFromJavaFieldAccess() throws Exception {
		createTestEntityFieldAccess();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessFromJavaPropertyAccess() throws Exception {
		createTestEntityPropertyAccess();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessNoUnderlyingJava() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
	}
		
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		entityResource.setMetadataComplete(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(Boolean.TRUE, entityResource.getMetadataComplete());
	
		//set access to false in the resource model
		entityResource.setMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(Boolean.FALSE, entityResource.getMetadataComplete());
		
		entityResource.setMetadataComplete(null);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(ormEntity.getSpecifiedMetadataComplete().booleanValue());
		assertTrue(entityResource.getMetadataComplete().booleanValue());
		
		//set access to null in the context model
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(ormEntity.getSpecifiedMetadataComplete().booleanValue());
		assertFalse(entityResource.getMetadataComplete().booleanValue());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEntity.isDefaultMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormEntity.getSpecifiedMetadataComplete());
		assertTrue(ormEntity.isDefaultMetadataComplete());
		assertTrue(ormEntity.isMetadataComplete());
	}

	public void testUpdateMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEntity.isMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(ormEntity.isMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	
	public void testUpdateInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getInheritanceStrategy());
		assertNull(entityResource.getInheritance());

		//set inheritance strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS);
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, ormEntity.getInheritanceStrategy());		
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());		
	}
	
	public void testUpdateDefaultInheritanceStrategyFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		//no inheritance strategy specified in java so single-table is default
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
		
		ormEntity.getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());
			
		ormEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		//inheritance tag exists in xml, so it overrides anything in java
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());

		ormEntity.setSpecifiedInheritanceStrategy(null);		
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());

		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		//this setting overrides the false meta-data complete found on ormEntity
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
	}
	
	public void testUpdateDefaultInheritanceStrategyFromParent() throws Exception {
		createTestEntityDefaultFieldAccess();
		createTestSubType();
	
		OrmPersistentType superPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType subPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentXmlEntity = (OrmEntity) superPersistentType.getMapping();
		OrmEntity childXmlEntity = (OrmEntity) subPersistentType.getMapping();
		
		assertEquals(parentXmlEntity, childXmlEntity.getParentEntity());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());

		//set specified inheritance strategy in java and verify defaults in xml are correct
		parentXmlEntity.setSpecifiedInheritanceStrategy(null);
		parentXmlEntity.getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.JOINED, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(parentXmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
	}

	public void testUpdateSpecifiedInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.JOINED, entityResource.getInheritance().getStrategy());
	
		//set strategy to null in the resource model
		entityResource.getInheritance().setStrategy(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance().getStrategy());
		
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.SINGLE_TABLE);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.SINGLE_TABLE, entityResource.getInheritance().getStrategy());

		entityResource.setInheritance(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
	}
	
	public void testModifySpecifiedInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the context model, verify resource model modified
		ormEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.TABLE_PER_CLASS, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());
		
		//set strategy to null in the context model
		ormEntity.setSpecifiedInheritanceStrategy(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());	
	}
	
	public void testAddSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("FOO");
				
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
		
		OrmSecondaryTable secondaryTable2 = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable2.setSpecifiedName("BAR");
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(1).getName());
		
		OrmSecondaryTable secondaryTable3 = ormEntity.addSpecifiedSecondaryTable(1);
		secondaryTable3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
		
		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals(secondaryTable2, secondaryTables.next());
		assertEquals(secondaryTable3, secondaryTables.next());
		assertEquals(secondaryTable, secondaryTables.next());
		
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
	}
	
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(2, entityResource.getSecondaryTables().size());
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());

		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(1, entityResource.getSecondaryTables().size());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		
		ormEntity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());


		ormEntity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAR", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
	}
	
	public void testUpdateSecondaryTables() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		
		entityResource.getSecondaryTables().get(0).setName("FOO");
		entityResource.getSecondaryTables().get(1).setName("BAR");
		entityResource.getSecondaryTables().get(2).setName("BAZ");

		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().move(2, 0);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().move(0, 1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
	}
	
	public void testVirtualSecondaryTables() throws Exception {
		createTestEntityFieldAccess();
		createTestSubType();
	
		OrmPersistentType superPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType subPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentOrmEntity = (OrmEntity) superPersistentType.getMapping();
		OrmEntity childOrmEntity = (OrmEntity) subPersistentType.getMapping();
		JavaEntity javaEntity = childOrmEntity.getJavaEntity();
		
		JavaSecondaryTable javaSecondaryTableFoo = javaEntity.addSpecifiedSecondaryTable(0);
		javaSecondaryTableFoo.setSpecifiedName("FOO");
		OrmSecondaryTable virtualSecondaryTableFoo = childOrmEntity.virtualSecondaryTables().next();
		assertEquals("FOO", childOrmEntity.secondaryTables().next().getName());
		assertEquals("FOO", virtualSecondaryTableFoo.getName());
		assertEquals(0, virtualSecondaryTableFoo.specifiedPrimaryKeyJoinColumnsSize());
		assertEquals("id", virtualSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultName());
		assertEquals("id", virtualSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultReferencedColumnName());
		
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(1, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(1, childOrmEntity.secondaryTablesSize());
		
		javaEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		ListIterator<OrmSecondaryTable> virtualSecondaryTables = childOrmEntity.virtualSecondaryTables();
		ListIterator<OrmSecondaryTable> secondaryTables = childOrmEntity.secondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		
		childOrmEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		
		childOrmEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		
		
		childOrmEntity.setSecondaryTablesDefinedInXml(true);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		ListIterator<OrmSecondaryTable> specifiedSecondaryTables = childOrmEntity.specifiedSecondaryTables();
		assertEquals("BAR", specifiedSecondaryTables.next().getName());
		OrmSecondaryTable specifiedSecondaryTableFoo = specifiedSecondaryTables.next();
		assertEquals("FOO", specifiedSecondaryTableFoo.getName());
		assertEquals(0, specifiedSecondaryTableFoo.specifiedPrimaryKeyJoinColumnsSize());
		assertEquals("id", specifiedSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultName());
		assertEquals("id", specifiedSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultReferencedColumnName());
		
		
		childOrmEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(1, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(1, childOrmEntity.secondaryTablesSize());
		assertEquals("FOO", childOrmEntity.specifiedSecondaryTables().next().getName());
		
	
		childOrmEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		virtualSecondaryTables = childOrmEntity.virtualSecondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		
		
		//add a specified secondary table to the parent, this will not affect virtual secondaryTables in child
		parentOrmEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("PARENT_TABLE");
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());	
	}

	public void testAssociatedTables() throws Exception {
		createTestEntityFieldAccess();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		assertEquals(1, CollectionTools.size(entity.associatedTables()));
		
		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		assertEquals(2, CollectionTools.size(entity.associatedTables()));
	
		entity.addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		assertEquals(3, CollectionTools.size(entity.associatedTables()));
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		
	}
	
	public void testTableNameIsInvalid() throws Exception {
		
	}
	
	public void testMakeEntityEmbeddable() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = getXmlEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, entityPersistentType.getSpecifiedAccess());
	}
	
	//TODO test that attribute mappings are not removed when changing type mapping.
	public void testMakeEntityEmbeddable2() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
//		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
	
		entityPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = getXmlEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
//		assertEquals("basicMapping", embeddable.getAttributes().getBasics().get(0).getName());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, entityPersistentType.getSpecifiedAccess());
//		assertEquals("basicMapping", ormEmbeddable.persistentType().attributes().next().getName());
	}
	
	public void testMakeEntityMappedSuperclass() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass mappedSuperclass = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, entityPersistentType.getSpecifiedAccess());
	}
	
	public void testMakeEntityMappedSuperclass2() throws Exception {
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass mappedSuperclass = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, entityPersistentType.getSpecifiedAccess());
	}

	
	public void testAddSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		
		ormEntity.getGeneratorContainer().addSequenceGenerator();
		
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			ormEntity.getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		ormEntity.getGeneratorContainer().addSequenceGenerator();
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(ormEntity.getGeneratorContainer().getSequenceGenerator());

		ormEntity.getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			ormEntity.getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		assertEquals(0, ormEntity.getPersistenceUnit().generatorsSize());
		
		entityResource.setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGenerator());
				
		assertNotNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(entityResource.getSequenceGenerator());
		assertEquals(1, ormEntity.getPersistenceUnit().generatorsSize());
		
		ormEntity.getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, ormEntity.getPersistenceUnit().generatorsSize());

		entityResource.setSequenceGenerator(null);
		assertNull(ormEntity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		assertEquals(0, ormEntity.getPersistenceUnit().generatorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		
		ormEntity.getGeneratorContainer().addTableGenerator();
		
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(ormEntity.getGeneratorContainer().getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			ormEntity.getGeneratorContainer().addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		ormEntity.getGeneratorContainer().addTableGenerator();
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(ormEntity.getGeneratorContainer().getTableGenerator());

		ormEntity.getGeneratorContainer().removeTableGenerator();
		
		assertNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			ormEntity.getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		assertEquals(0, ormEntity.getPersistenceUnit().generatorsSize());
		
		entityResource.setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGenerator());
				
		assertNotNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNotNull(entityResource.getTableGenerator());
		assertEquals(1, ormEntity.getPersistenceUnit().generatorsSize());

		ormEntity.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, ormEntity.getPersistenceUnit().generatorsSize());
		
		entityResource.setTableGenerator(null);
		assertNull(ormEntity.getGeneratorContainer().getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		assertEquals(0, ormEntity.getPersistenceUnit().generatorsSize());
	}
	
	public void testUpdateDiscriminatorColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNotNull(ormEntity.getDiscriminatorColumn());

		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		
		assertEquals("FOO", ormEntity.getDiscriminatorColumn().getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName(null);
		
		assertNull(ormEntity.getDiscriminatorColumn().getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		
		assertNotNull(ormEntity.getDiscriminatorColumn());
	}
	
	public void testUpdateDiscriminatorValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		entityResource.setDiscriminatorValue("FOO");
		
		assertEquals("FOO", ormEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		entityResource.setDiscriminatorValue(null);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testModifyDiscriminatorValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		ormEntity.setSpecifiedDiscriminatorValue("FOO");
		
		assertEquals("FOO", ormEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		ormEntity.setSpecifiedDiscriminatorValue(null);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testGetDefaultDiscriminatorValue() throws Exception {
		createTestEntityFieldAccess();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		JavaEntity javaEntity = (JavaEntity) persistentType.getJavaPersistentType().getMapping();
		assertEquals(null, ormEntity.getDefaultDiscriminatorValue());

		createTestSubType();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		assertEquals(ormEntity.getName(), ormEntity.getDefaultDiscriminatorValue());
	
		javaEntity.getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.INTEGER);
		assertNull(ormEntity.getDefaultDiscriminatorValue());
		
		javaEntity.getDiscriminatorColumn().setSpecifiedDiscriminatorType(null);
		javaEntity.setSpecifiedDiscriminatorValue("FOO");
		
		assertEquals("FOO", ormEntity.getDefaultDiscriminatorValue());

		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(ormEntity.getName(), ormEntity.getDefaultDiscriminatorValue());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn2 = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn3 = ormEntity.addSpecifiedPrimaryKeyJoinColumn(1);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals(primaryKeyJoinColumn2, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn3, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn, primaryKeyJoinColumns.next());
		
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(2, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());

		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(1, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(0, entityResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		
		ormEntity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());


		ormEntity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		
		entityResource.getPrimaryKeyJoinColumns().get(0).setName("FOO");
		entityResource.getPrimaryKeyJoinColumns().get(1).setName("BAR");
		entityResource.getPrimaryKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().move(2, 0);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().move(0, 1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormEntity.specifiedPrimaryKeyJoinColumns().hasNext());
	}

	public void testDefaultPrimaryKeyJoinColumns() throws Exception {
		createTestType();
		createTestSubTypeUnmapped();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		childPersistentType.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		
		persistentType.getAttributeNamed("id").makeSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		((OrmEntity) persistentType.getMapping()).setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		
		OrmEntity childEntity = (OrmEntity) childPersistentType.getMapping();
		
		assertTrue(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultName());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultReferencedColumnName());
		
		childPersistentType.getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		persistentType.getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		assertTrue(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultName());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultReferencedColumnName());
		
		OrmPrimaryKeyJoinColumn specifiedPkJoinColumn = childEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		specifiedPkJoinColumn.setSpecifiedName("FOO");
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName("BAR");
		
		assertFalse(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		
		//remove the pkJoinColumn from the context mode, verify context model has a default pkJoinColumn
		childEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertTrue(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultName());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultReferencedColumnName());

		
		childPersistentType.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		persistentType.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		
		specifiedPkJoinColumn = childEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		specifiedPkJoinColumn.setSpecifiedName("FOO");
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName("BAR");		
		assertFalse(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		//now remove the pkJoinColumn from the resource model, verify context model updates and has a default pkJoinColumn
		((XmlEntity)childEntity.getResourceTypeMapping()).getPrimaryKeyJoinColumns().remove(0);
		assertTrue(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultName());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultReferencedColumnName());
	}
	
	public void testDefaultPrimaryKeyJoinColumnsFromJava() throws Exception {
		createTestEntityFieldAccess();
		createTestSubType();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		
		
		((JavaEntity) persistentType.getJavaPersistentType().getMapping()).setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		
		OrmEntity childEntity = (OrmEntity) childPersistentType.getMapping();
		
		assertTrue(childEntity.defaultPrimaryKeyJoinColumns().hasNext());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultName());
		assertEquals("id", childEntity.defaultPrimaryKeyJoinColumns().next().getDefaultReferencedColumnName());
		
		JavaEntity javaEntity = (JavaEntity) childPersistentType.getJavaPersistentType().getMapping();
		JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn = javaEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		javaPrimaryKeyJoinColumn.setSpecifiedName("FOO");
		javaPrimaryKeyJoinColumn.setSpecifiedReferencedColumnName("BAR");
		
		JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn2 = javaEntity.addSpecifiedPrimaryKeyJoinColumn(1);
		javaPrimaryKeyJoinColumn2.setSpecifiedName("FOO2");
		javaPrimaryKeyJoinColumn2.setSpecifiedReferencedColumnName("BAR2");
		
		childPersistentType.getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		persistentType.getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns = childEntity.defaultPrimaryKeyJoinColumns();
		OrmPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn = defaultPrimaryKeyJoinColumns.next();
		assertEquals("FOO", defaultPrimaryKeyJoinColumn.getName());
		assertEquals("BAR", defaultPrimaryKeyJoinColumn.getReferencedColumnName());
		
		defaultPrimaryKeyJoinColumn = defaultPrimaryKeyJoinColumns.next();
		assertEquals("FOO2", defaultPrimaryKeyJoinColumn.getName());
		assertEquals("BAR2", defaultPrimaryKeyJoinColumn.getReferencedColumnName());
		assertFalse(defaultPrimaryKeyJoinColumns.hasNext());
		
		childEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		defaultPrimaryKeyJoinColumns = childEntity.defaultPrimaryKeyJoinColumns();
		defaultPrimaryKeyJoinColumn = defaultPrimaryKeyJoinColumns.next();
		assertEquals("id", defaultPrimaryKeyJoinColumn.getDefaultName());
		assertEquals("id", defaultPrimaryKeyJoinColumn.getDefaultReferencedColumnName());
		
		assertFalse(defaultPrimaryKeyJoinColumns.hasNext());
		
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().get(2).setName("BAZ");
		
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		overrideContainer.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());


		overrideContainer.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		assertFalse(overrideContainer.specifiedAttributeOverrides().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		assertEquals(4, overrideContainer.virtualAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		OrmAttributeOverride attributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", attributeOverride.getName());
		assertEquals("name", attributeOverride.getColumn().getName());
		assertEquals(CHILD_TYPE_NAME, attributeOverride.getColumn().getTable());
		assertEquals(null, attributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, attributeOverride.getColumn().isInsertable());
		assertEquals(true, attributeOverride.getColumn().isUpdatable());
		assertEquals(false, attributeOverride.getColumn().isUnique());
		assertEquals(true, attributeOverride.getColumn().isNullable());
		assertEquals(255, attributeOverride.getColumn().getLength());
		assertEquals(0, attributeOverride.getColumn().getPrecision());
		assertEquals(0, attributeOverride.getColumn().getScale());
		
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", attributeOverride.getName());
		
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", attributeOverride.getName());
	
		overrideContainer.specifiedAttributeOverrides().next().setVirtual(true);
		entity.setSpecifiedMetadataComplete(Boolean.FALSE);
		entity.getJavaEntity().getAttributeOverrideContainer().virtualAttributeOverrides().next().setVirtual(false);
		JavaColumn javaColumn = entity.getJavaEntity().getAttributeOverrideContainer().specifiedAttributeOverrides().next().getColumn();
		javaColumn.setSpecifiedName("FOO");
		javaColumn.setSpecifiedTable("BAR");
		javaColumn.setColumnDefinition("COLUMN_DEF");
		javaColumn.setSpecifiedInsertable(Boolean.FALSE);
		javaColumn.setSpecifiedUpdatable(Boolean.FALSE);
		javaColumn.setSpecifiedUnique(Boolean.TRUE);
		javaColumn.setSpecifiedNullable(Boolean.FALSE);
		javaColumn.setSpecifiedLength(Integer.valueOf(7));
		javaColumn.setSpecifiedPrecision(Integer.valueOf(8));
		javaColumn.setSpecifiedScale(Integer.valueOf(9));
		
		assertEquals(4, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", attributeOverride.getName());
		assertEquals("FOO", attributeOverride.getColumn().getSpecifiedName());//TODO specified or default?
		assertEquals("BAR", attributeOverride.getColumn().getSpecifiedTable());
		assertEquals("COLUMN_DEF", attributeOverride.getColumn().getColumnDefinition());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, attributeOverride.getColumn().getSpecifiedUnique());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedNullable());
		assertEquals(Integer.valueOf(7), attributeOverride.getColumn().getSpecifiedLength());
		assertEquals(Integer.valueOf(8), attributeOverride.getColumn().getSpecifiedPrecision());
		assertEquals(Integer.valueOf(9), attributeOverride.getColumn().getSpecifiedScale());
		
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		assertEquals("basic", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", attributeOverride.getName());
		
		persistentType2.getAttributeNamed("basic").makeSpecified();
		OrmBasicMapping basicMapping = (OrmBasicMapping) persistentType2.getAttributeNamed("basic").getMapping();
		basicMapping.getColumn().setSpecifiedName("MY_NAME");
		basicMapping.getColumn().setSpecifiedTable("BAR");
		basicMapping.getColumn().setColumnDefinition("COLUMN_DEF");
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		basicMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		basicMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		
		
		assertEquals(4, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		assertEquals("MY_NAME", attributeOverride.getColumn().getSpecifiedName());
		assertEquals("BAR", attributeOverride.getColumn().getTable());
		assertEquals("COLUMN_DEF", attributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, attributeOverride.getColumn().isInsertable());
		assertEquals(false, attributeOverride.getColumn().isUpdatable());
		assertEquals(true, attributeOverride.getColumn().isUnique());
		assertEquals(false, attributeOverride.getColumn().isNullable());
		assertEquals(5, attributeOverride.getColumn().getLength());
		assertEquals(6, attributeOverride.getColumn().getPrecision());
		assertEquals(7, attributeOverride.getColumn().getScale());

		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", attributeOverride.getName());
		assertEquals("FOO", attributeOverride.getColumn().getSpecifiedName());//TODO specified or default?
		assertEquals("BAR", attributeOverride.getColumn().getSpecifiedTable());
		assertEquals("COLUMN_DEF", attributeOverride.getColumn().getColumnDefinition());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, attributeOverride.getColumn().getSpecifiedUnique());
		assertEquals(Boolean.FALSE, attributeOverride.getColumn().getSpecifiedNullable());
		assertEquals(Integer.valueOf(7), attributeOverride.getColumn().getSpecifiedLength());
		assertEquals(Integer.valueOf(8), attributeOverride.getColumn().getSpecifiedPrecision());
		assertEquals(Integer.valueOf(9), attributeOverride.getColumn().getSpecifiedScale());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
	}
	
	public void testVirtualAttributeOverridesNoJavaEntity() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		persistentType.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(4, overrideContainer.virtualAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		OrmAttributeOverride attributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("basic", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", attributeOverride.getName());
	}
	
	public void testAttributeOverrideColumnDefaults() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		OrmAttributeOverride attributeOverride = overrideContainer.specifiedAttributeOverrides().next();
		assertEquals("foo", attributeOverride.getColumn().getDefaultName());
		assertEquals(CHILD_TYPE_NAME, attributeOverride.getColumn().getDefaultTable());
		
		((JavaEntity) persistentType.getJavaPersistentType().getMapping()).getTable().setSpecifiedName("FOO");
		assertEquals("foo", attributeOverride.getColumn().getDefaultName());
		assertEquals("FOO", attributeOverride.getColumn().getDefaultTable());
		
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals("foo", attributeOverride.getColumn().getDefaultName());
		assertEquals(CHILD_TYPE_NAME, attributeOverride.getColumn().getDefaultTable());
		
		entity.setSpecifiedMetadataComplete(Boolean.FALSE);
		entity.getTable().setSpecifiedName("BAR");
		assertEquals("foo", attributeOverride.getColumn().getDefaultName());
		assertEquals("BAR", attributeOverride.getColumn().getDefaultTable());
	}
	
	public void testAttributeOverrideColumnDefaultsNoJavaAnnotations() throws Exception {
		createTestType();
		createTestSubTypeUnmapped();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();	
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		OrmAttributeOverride attributeOverride = overrideContainer.virtualAttributeOverrides().next();
		((OrmPersistentAttribute) persistentType.getSuperPersistentType().getAttributeNamed("id")).makeSpecified();
		BasicMapping basicMapping = (BasicMapping) persistentType.getSuperPersistentType().getAttributeNamed("id").getSpecifiedMapping();
		basicMapping.getColumn().setSpecifiedName("MY_COLUMN");
		basicMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals("MY_COLUMN", attributeOverride.getColumn().getDefaultName());
		assertEquals("BAR", attributeOverride.getColumn().getDefaultTable());

	
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		attributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("MY_COLUMN", attributeOverride.getColumn().getName());
		assertEquals("BAR", attributeOverride.getColumn().getTable());
	}
	
	public void testOverridableAttributes() throws Exception {
		createTestEntityDefaultFieldAccess();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = (Entity) persistentType.getMapping();
		Iterator<String> overridableAttributes = persistentType.getMapping().overridableAttributeNames();
		assertFalse(overridableAttributes.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = entity.overridableAttributeNames();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntityDefaultFieldAccess();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = (Entity) persistentType.getMapping();
		Iterator<String> overridableAttributeNames = persistentType.getMapping().overridableAttributeNames();
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = entity.overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
	
		Iterator<String> overridableAttributes = entity.allOverridableAttributeNames();
		assertEquals("foo", overridableAttributes.next());
		assertEquals("basic", overridableAttributes.next());
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testAllOverridableAttributesTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		OrmPersistentType abstractPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		OrmEntity abstractEntity = (OrmEntity) abstractPersistentType.getMapping();
	
		Iterator<String> overridableAttributes = entity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		overridableAttributes = abstractEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testVirtualAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		
		PersistentType abstractPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		PersistentType concretePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		Entity concreteEntity = (Entity) concretePersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = concreteEntity.getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(CHILD_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		
		OrmBasicMapping idMapping = (OrmBasicMapping) abstractPersistentType.getAttributeNamed("id").getMapping();
		idMapping.getPersistentAttribute().makeSpecified();
		idMapping = (OrmBasicMapping) abstractPersistentType.getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		

		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);

		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(CHILD_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, overrideContainer.virtualAttributeOverridesSize());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().get(2).setName("BAZ");
		
		assertEquals(3, entityResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedAssociationOverride(2, 0);
		ListIterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());


		overrideContainer.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());
	}
	
	public void testUpdateAssociationOverrides() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().get(2).setName("BAZ");

		ListIterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().move(2, 0);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().move(0, 1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().remove(0);
		assertFalse(overrideContainer.specifiedAssociationOverrides().hasNext());
	}
	
	public void testVirtualAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		AssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		OrmAssociationOverride associationOverride = virtualAssociationOverrides.next();
		OrmJoinColumnJoiningStrategy joiningStrategy = associationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		assertEquals("oneToOne", associationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		OrmJoinColumn joinColumn = joiningStrategy.joinColumns().next();
		assertEquals("oneToOne_id", joinColumn.getName());
		assertEquals("id", joinColumn.getReferencedColumnName());
		assertEquals(CHILD_TYPE_NAME, joinColumn.getTable());
		assertEquals(null, joinColumn.getColumnDefinition());
		assertEquals(true, joinColumn.isInsertable());
		assertEquals(true, joinColumn.isUpdatable());
		assertEquals(false, joinColumn.isUnique());
		assertEquals(true, joinColumn.isNullable());
		
		
		associationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", associationOverride.getName());
		assertFalse(virtualAssociationOverrides.hasNext());

		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		associationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", associationOverride.getName());
		
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		associationOverride = virtualAssociationOverrides.next();
		assertEquals("manyToOne", associationOverride.getName());
		
		overrideContainer.specifiedAssociationOverrides().next().setVirtual(true);
		entity.setSpecifiedMetadataComplete(Boolean.FALSE);
		entity.getJavaEntity().getAssociationOverrideContainer().virtualAssociationOverrides().next().setVirtual(false);
		
		getOrmXmlResource().save(null);

		JavaAssociationOverride javaAssociationOverride = entity.getJavaEntity().getAssociationOverrideContainer().specifiedAssociationOverrides().next();
		JavaJoinColumnJoiningStrategy javaJoiningStrategy = javaAssociationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		JavaJoinColumn javaJoinColumn = javaJoiningStrategy.joinColumns().next();
		javaJoinColumn.setSpecifiedName("FOO");
		javaJoinColumn.setSpecifiedReferencedColumnName("REFERENCE");
		javaJoinColumn.setSpecifiedTable("BAR");
		javaJoinColumn.setColumnDefinition("COLUMN_DEF");
		javaJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
		javaJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		javaJoinColumn.setSpecifiedUnique(Boolean.TRUE);
		javaJoinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		associationOverride = virtualAssociationOverrides.next();
		joiningStrategy = associationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("oneToOne", associationOverride.getName());
		OrmJoinColumn ormJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("FOO", ormJoinColumn.getSpecifiedName());
		assertEquals("REFERENCE", ormJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", ormJoinColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEF", ormJoinColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		
		associationOverride = virtualAssociationOverrides.next();
		joiningStrategy = associationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("manyToOne", associationOverride.getName());
		assertEquals(null, joiningStrategy.joinColumns().next().getSpecifiedName());
		
		persistentType2.getAttributeNamed("manyToOne").makeSpecified();
		OrmManyToOneMapping manyToOneMapping = (OrmManyToOneMapping) persistentType2.getAttributeNamed("manyToOne").getMapping();
		joinColumn = manyToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_NAME");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERNCE_NAME");
		joinColumn.setSpecifiedTable("BAR2");
		joinColumn.setColumnDefinition("COLUMN_DEF2");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);

		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		associationOverride = virtualAssociationOverrides.next();
		joiningStrategy = associationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("manyToOne", associationOverride.getName());
		ormJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("MY_NAME", ormJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERNCE_NAME", ormJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR2", ormJoinColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEF2", ormJoinColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		
		associationOverride = virtualAssociationOverrides.next();
		joiningStrategy = associationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("oneToOne", associationOverride.getName());
		ormJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("FOO", ormJoinColumn.getSpecifiedName());
		assertEquals("REFERENCE", ormJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", ormJoinColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEF", ormJoinColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
	}
//TODO
//	public void testVirtualAssociationOverridesNoJavaEntity() throws Exception {
//		createTestMappedSuperclass();
//		createTestSubType();
//		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
//		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		persistentType.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
//		persistentType2.getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
//		
//		persistentType2.getAttributeNamed("oneToOne").setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
//		persistentType2.getAttributeNamed("manyToOne").setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
//			
//		OrmEntity entity = (OrmEntity) persistentType.getMapping();
//		
//		assertEquals(2, entity.virtualAssociationOverridesSize());
//		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = entity.virtualAssociationOverrides();
//		OrmAssociationOverride associationOverride = virtualAssociationOverrides.next();
//		assertEquals("oneToOne", associationOverride.getName());
//		assertEquals(1, associationOverride.joinColumnsSize());
//		OrmJoinColumn joinColumn = associationOverride.joinColumns().next();
//		assertEquals("oneToOne_id", joinColumn.getName());
//		assertEquals("id", joinColumn.getReferencedColumnName());
//		assertEquals(CHILD_TYPE_NAME, joinColumn.getTable());
//		assertEquals(null, joinColumn.getColumnDefinition());
//		assertEquals(true, joinColumn.isInsertable());
//		assertEquals(true, joinColumn.isUpdatable());
//		assertEquals(false, joinColumn.isUnique());
//		assertEquals(true, joinColumn.isNullable());
//		
//		
//		associationOverride = virtualAssociationOverrides.next();
//		assertEquals("manyToOne", associationOverride.getName());
//		assertFalse(virtualAssociationOverrides.hasNext());
//
//		
//		entity.virtualAssociationOverrides().next().setVirtual(false);
//		
//		assertEquals(1, entity.virtualAssociationOverridesSize());
//		virtualAssociationOverrides = entity.virtualAssociationOverrides();
//		associationOverride = virtualAssociationOverrides.next();
//		assertEquals("manyToOne", associationOverride.getName());
//		
//		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(1, entity.virtualAssociationOverridesSize());
//		virtualAssociationOverrides = entity.virtualAssociationOverrides();
//		associationOverride = virtualAssociationOverrides.next();
//		assertEquals("manyToOne", associationOverride.getName());
//		
//		entity.specifiedAssociationOverrides().next().setVirtual(true);
//		entity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		entity.getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
//		JavaAssociationOverride javaAssociationOverride = entity.getJavaEntity().specifiedAssociationOverrides().next();
//		JavaJoinColumn javaJoinColumn = javaAssociationOverride.addSpecifiedJoinColumn(0);
//		javaJoinColumn.setSpecifiedName("FOO");
//		javaJoinColumn.setSpecifiedReferencedColumnName("REFERENCE");
//		javaJoinColumn.setSpecifiedTable("BAR");
//		javaJoinColumn.setColumnDefinition("COLUMN_DEF");
//		javaJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
//		javaJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
//		javaJoinColumn.setSpecifiedUnique(Boolean.TRUE);
//		javaJoinColumn.setSpecifiedNullable(Boolean.FALSE);
//		
//		assertEquals(2, entity.virtualAssociationOverridesSize());
//		virtualAssociationOverrides = entity.virtualAssociationOverrides();
//		associationOverride = virtualAssociationOverrides.next();
//		assertEquals("oneToOne", associationOverride.getName());
//		OrmJoinColumn ormJoinColumn = associationOverride.joinColumns().next();
//		assertEquals("FOO", ormJoinColumn.getSpecifiedName());
//		assertEquals("REFERENCE", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals("BAR", ormJoinColumn.getSpecifiedTable());
//		assertEquals("COLUMN_DEF", ormJoinColumn.getColumnDefinition());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
//		
//		associationOverride = virtualAssociationOverrides.next();
//		assertEquals("manyToOne", associationOverride.getName());
//		assertEquals(null, associationOverride.joinColumns().next().getSpecifiedName());
//		
//		persistentType2.getAttributeNamed("manyToOne").makeSpecified();
//		OrmManyToOneMapping manyToOneMapping = (OrmManyToOneMapping) persistentType2.getAttributeNamed("manyToOne").getMapping();
//		joinColumn = manyToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy().addSpecifiedJoinColumn(0);
//		joinColumn.setSpecifiedName("MY_NAME");
//		joinColumn.setSpecifiedReferencedColumnName("MY_REFERNCE_NAME");
//		joinColumn.setSpecifiedTable("BAR2");
//		joinColumn.setColumnDefinition("COLUMN_DEF2");
//		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
//		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
//		joinColumn.setSpecifiedUnique(Boolean.TRUE);
//		joinColumn.setSpecifiedNullable(Boolean.FALSE);
//
//		
//		
//		assertEquals(2, entity.virtualAssociationOverridesSize());
//		virtualAssociationOverrides = entity.virtualAssociationOverrides();
//		assertEquals("oneToOne", associationOverride.getName());
//		ormJoinColumn = associationOverride.joinColumns().next();
//		assertEquals("FOO", ormJoinColumn.getSpecifiedName());
//		assertEquals("REFERENCE", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals("BAR", ormJoinColumn.getSpecifiedTable());
//		assertEquals("COLUMN_DEF", ormJoinColumn.getColumnDefinition());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
//
//		
//		associationOverride = entity.virtualAssociationOverrides().next();
//		associationOverride = entity.virtualAssociationOverrides().next();
//		assertEquals("manyToOne", associationOverride.getName());
//		ormJoinColumn = associationOverride.joinColumns().next();
//		assertEquals("MY_NAME", ormJoinColumn.getSpecifiedName());
//		assertEquals("MY_REFERNCE_NAME", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals("BAR2", ormJoinColumn.getSpecifiedTable());
//		assertEquals("COLUMN_DEF2", ormJoinColumn.getColumnDefinition());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
//	}
	
	public void testAddNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmNamedQuery namedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		namedQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedQueries().get(0).getName());
		
		OrmNamedQuery namedQuery2 = ormEntity.getQueryContainer().addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(1).getName());
		
		OrmNamedQuery namedQuery3 = ormEntity.getQueryContainer().addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedQuery(0).setName("FOO");
		ormEntity.getQueryContainer().addNamedQuery(1).setName("BAR");
		ormEntity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(2, entityResource.getNamedQueries().size());
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());

		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(1, entityResource.getNamedQueries().size());
		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(0, entityResource.getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedQuery(0).setName("FOO");
		ormEntity.getQueryContainer().addNamedQuery(1).setName("BAR");
		ormEntity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		
		ormEntity.getQueryContainer().moveNamedQuery(2, 0);
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());


		ormEntity.getQueryContainer().moveNamedQuery(0, 1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		assertEquals(0, ormEntity.getPersistenceUnit().queriesSize());
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().get(0).setName("FOO");
		entityResource.getNamedQueries().get(1).setName("BAR");
		entityResource.getNamedQueries().get(2).setName("BAZ");
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().move(2, 0);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().move(0, 1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().remove(0);
		assertFalse(ormEntity.getQueryContainer().namedQueries().hasNext());
		assertEquals(0, ormEntity.getPersistenceUnit().queriesSize());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmNamedNativeQuery namedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(0).getName());
		
		OrmNamedNativeQuery namedNativeQuery2 = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(1).getName());
		
		OrmNamedNativeQuery namedNativeQuery3 = ormEntity.getQueryContainer().addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
		
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals(namedNativeQuery2, namedNativeQueries.next());
		assertEquals(namedNativeQuery3, namedNativeQueries.next());
		assertEquals(namedNativeQuery, namedNativeQueries.next());
		
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		ormEntity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		ormEntity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		ormEntity.getQueryContainer().removeNamedNativeQuery(0);
		assertEquals(2, entityResource.getNamedNativeQueries().size());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());

		ormEntity.getQueryContainer().removeNamedNativeQuery(0);
		assertEquals(1, entityResource.getNamedNativeQueries().size());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		
		ormEntity.getQueryContainer().removeNamedNativeQuery(0);
		assertEquals(0, entityResource.getNamedNativeQueries().size());
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedNativeQuery(0).setName("FOO");
		ormEntity.getQueryContainer().addNamedNativeQuery(1).setName("BAR");
		ormEntity.getQueryContainer().addNamedNativeQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		
		ormEntity.getQueryContainer().moveNamedNativeQuery(2, 0);
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());


		ormEntity.getQueryContainer().moveNamedNativeQuery(0, 1);
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		assertEquals(0, ormEntity.getPersistenceUnit().queriesSize());
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().get(0).setName("FOO");
		entityResource.getNamedNativeQueries().get(1).setName("BAR");
		entityResource.getNamedNativeQueries().get(2).setName("BAZ");
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(3, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedNativeQueries().move(2, 0);
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		entityResource.getNamedNativeQueries().move(0, 1);
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(2, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQueries = ormEntity.getQueryContainer().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(1, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedNativeQueries().remove(0);
		assertFalse(ormEntity.getQueryContainer().namedNativeQueries().hasNext());
		assertEquals(0, ormEntity.getPersistenceUnit().queriesSize());
	}
	
	public void testUpdateIdClass() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
		
		entityResource.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());
		
		assertNull(ormEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		entityResource.getIdClass().setClassName("model.Foo");
		assertEquals("model.Foo", ormEntity.getIdClass());
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		
		//test setting  @IdClass value to null, id-class tag is not removed
		entityResource.getIdClass().setClassName(null);
		assertNull(ormEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		//reset @IdClass value and then remove id-class tag
		entityResource.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());
		entityResource.getIdClass().setClassName("model.Foo");
		entityResource.setIdClass(null);
		
		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
			
		ormEntity.setIdClass("model.Foo");
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		assertEquals("model.Foo", ormEntity.getIdClass());
		
		ormEntity.setIdClass(null);
		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}

	
	public void testGetPrimaryKeyColumnNameWithAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType superPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType subPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity childXmlEntity = (OrmEntity) subPersistentType.getMapping();
		
		superPersistentType.getAttributeNamed("id").makeSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("id", childXmlEntity.getPrimaryKeyColumnName());
		
		((OrmIdMapping) superPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("MY_ID", childXmlEntity.getPrimaryKeyColumnName());

		//TODO once bug 228718 is fixed
		OrmAttributeOverride ormAttributeOverride = childXmlEntity.getAttributeOverrideContainer().virtualAttributeOverrides().next();
		assertEquals("id", ormAttributeOverride.getName());
		
		ormAttributeOverride = (OrmAttributeOverride) ormAttributeOverride.setVirtual(false);
		ormAttributeOverride.getColumn().setSpecifiedName("ID");
		assertEquals("ID", childXmlEntity.getPrimaryKeyColumnName());
	}

	
	public void testDiscriminatorValueIsUndefinedConcreteClass() throws Exception {
		createTestType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		assertTrue(entity.discriminatorValueIsUndefined());
		
		createTestSubType();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		assertFalse(entity.discriminatorValueIsUndefined());
	}

	public void testDiscriminatorValueIsAllowedAbstractClass() throws Exception {
		createTestAbstractType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) persistentType.getMapping();
		assertTrue(entity.discriminatorValueIsUndefined());
		
		createTestSubType();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_CHILD_TYPE_NAME);
		assertTrue(entity.discriminatorValueIsUndefined());
	}
	
	public void testDiscriminatorColumnIsAllowed() throws Exception {
		createAbstractTestEntity();
		createTestSubType();
		OrmPersistentType concretePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmPersistentType abstractPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Entity concreteEntity = (Entity) concretePersistentType.getMapping();
		assertEquals("AnnotationTestTypeChild", concreteEntity.getName());
		
		Entity abstractEntity = (Entity) abstractPersistentType.getMapping();
		assertEquals(TYPE_NAME, abstractEntity.getName());

		
		//table-per-class, no discriminator column allowed
		assertFalse(concreteEntity.specifiedDiscriminatorColumnIsAllowed());
		assertFalse(abstractEntity.specifiedDiscriminatorColumnIsAllowed());

		
		//single-table, discriminator column allowed on root entity
		((JavaEntity) abstractPersistentType.getJavaPersistentType().getMapping()).setSpecifiedInheritanceStrategy(null);
		assertFalse(concreteEntity.specifiedDiscriminatorColumnIsAllowed());
		assertTrue(abstractEntity.specifiedDiscriminatorColumnIsAllowed());
	}
	
	public void testAbstractEntityGetDefaultDiscriminatorColumnNameTablePerClassInheritance() throws Exception {
		createAbstractTestEntity();
		createTestSubType();
		
		OrmPersistentType concretePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmPersistentType abstractPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Entity concreteEntity = (Entity) concretePersistentType.getMapping();
		assertEquals("AnnotationTestTypeChild", concreteEntity.getName());
		
		Entity abstractEntity = (Entity) abstractPersistentType.getMapping();
		assertEquals(TYPE_NAME, abstractEntity.getName());

		
		assertEquals(null, abstractEntity.getSpecifiedInheritanceStrategy());
		assertEquals(null, concreteEntity.getSpecifiedInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, abstractEntity.getDefaultInheritanceStrategy());
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