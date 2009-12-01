/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.LockModeType_2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmEntity2_0Tests extends Generic2_0ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;
	
	
	public GenericOrmEntity2_0Tests(String name) {
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

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
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
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	private ICompilationUnit createTestAbstractEntityTablePerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
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
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
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
	
	private void createTestMappedSuperclassCustomer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.MAPPED_SUPERCLASS);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@MappedSuperclass");
				sb.append(CR);
				sb.append("public class ").append("Customer ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String id;").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Customer.java", sourceWriter);
	}
	
	private void createTestEntityLongTimeCustomer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("LongTimeCustomer extends Customer ");
				sb.append("{}");
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "LongTimeCustomer.java", sourceWriter);
	}


	private void createTestEmbeddableAddress() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String street;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private ZipCode zipCode;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEmbeddableZipCode() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("ZipCode").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String zip;").append(CR);
				sb.append(CR);
				sb.append("    private String plusfour;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "ZipCode.java", sourceWriter);
	}

	
	private LockModeType_2_0 lockModeOf(XmlNamedQuery resourceQuery) {
		return resourceQuery == null ? null : LockModeType_2_0.fromOrmResourceModel(resourceQuery.getLockMode());
	}

	public void testAddNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmNamedQuery2_0 namedQuery = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(0);
			namedQuery.setName("FOO");
			namedQuery.setSpecifiedLockMode(LockModeType_2_0.OPTIMISTIC);
			
		XmlNamedQuery resourceQuery = entityResource.getNamedQueries().get(0);
			assertEquals("FOO", resourceQuery.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, this.lockModeOf(resourceQuery));
		
		OrmNamedQuery2_0 namedQuery2 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(0);
			namedQuery2.setName("BAR");
			namedQuery2.setSpecifiedLockMode(LockModeType_2_0.READ);

		resourceQuery = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", resourceQuery.getName());
			assertEquals(LockModeType_2_0.READ, this.lockModeOf(resourceQuery));
		assertEquals("FOO", entityResource.getNamedQueries().get(1).getName());
		
		OrmNamedQuery2_0 namedQuery3 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(1);
			namedQuery3.setName("BAZ");
			namedQuery3.setSpecifiedLockMode(LockModeType_2_0.WRITE);
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		resourceQuery = entityResource.getNamedQueries().get(1);
			assertEquals("BAZ", resourceQuery.getName());
			assertEquals(LockModeType_2_0.WRITE, this.lockModeOf(resourceQuery));
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
		OrmNamedQuery2_0 namedQuery1 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(1);
			namedQuery1.setName("BAR");
			namedQuery1.setSpecifiedLockMode(LockModeType_2_0.READ);
	
			OrmNamedQuery2_0 namedQuery2 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(2);
			namedQuery2.setName("BAZ");
			namedQuery2.setSpecifiedLockMode(LockModeType_2_0.OPTIMISTIC);
	
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(2, entityResource.getNamedQueries().size());
		XmlNamedQuery xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", xmlQuery0.getName());
			assertEquals(LockModeType_2_0.READ, this.lockModeOf(xmlQuery0));
	
		XmlNamedQuery xmlQuery1 = entityResource.getNamedQueries().get(1);
			assertEquals("BAZ", xmlQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, this.lockModeOf(xmlQuery1));

		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(1, entityResource.getNamedQueries().size());
		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAZ", xmlQuery0.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, this.lockModeOf(xmlQuery0));
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(0, entityResource.getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedQuery(0).setName("FOO");
		OrmNamedQuery2_0 namedQuery1 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(1);
			namedQuery1.setName("BAR");
			namedQuery1.setSpecifiedLockMode(LockModeType_2_0.OPTIMISTIC);
		ormEntity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		
		ormEntity.getQueryContainer().moveNamedQuery(2, 0);
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().namedQueries();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAR", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		XmlNamedQuery xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", xmlQuery0.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, this.lockModeOf(xmlQuery0));
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());


		ormEntity.getQueryContainer().moveNamedQuery(0, 1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAR", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		XmlNamedQuery xmlQuery1 = entityResource.getNamedQueries().get(1);
			assertEquals("BAR", xmlQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, this.lockModeOf(xmlQuery1));
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
		XmlNamedQuery xmlQuery = entityResource.getNamedQueries().get(2);
			xmlQuery.setName("BAZ");
			xmlQuery.setLockMode(org.eclipse.jpt.core.resource.orm.v2_0.LockModeType_2_0.OPTIMISTIC);
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		OrmNamedQuery2_0 namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().move(2, 0);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().move(0, 1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().namedQueries();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType_2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, ormEntity.getPersistenceUnit().queriesSize());
		
		entityResource.getNamedQueries().remove(0);
		assertFalse(ormEntity.getQueryContainer().namedQueries().hasNext());
		assertEquals(0, ormEntity.getPersistenceUnit().queriesSize());
	}
	
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();

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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributes = entity.overridableAttributeNames();
		assertFalse(overridableAttributes.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = entity.overridableAttributeNames();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributeNames = entity.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = entity.overridableAttributeNames();
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
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAttributesTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributes = ormEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAttributes = abstractEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAssociationsTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAssociations = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAssociations = abstractEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}
//TODO
//	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
//		createTestMappedSuperclass();
//		createTestSubType();
//		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
//		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		Iterator<PersistentAttribute> overridableAttributes = getJavaEntity().allOverridableAttributes();
//		assertEquals("id", overridableAttributes.next().getName());
//		assertEquals("name", overridableAttributes.next().getName());
//		assertEquals("foo", overridableAttributes.next().getName());
//		assertFalse(overridableAttributes.hasNext());
//	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributeNames = ormEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		ListIterator<OrmAttributeOverride> specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertFalse(specifiedAttributeOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAZ");
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().move(1, 0);
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(0, entityResource.getAttributeOverrides().size());
		
		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) ormEntity.getPersistentType().getSuperPersistentType().getMapping();
		
		mappedSuperclass.getPersistentType().getAttributeNamed("id").makeSpecified();
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, overrideContainer.virtualAttributeOverridesSize());
	}
	
	public void testDefaultAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		OrmEntity superclass = (OrmEntity) ormEntity.getParentEntity();
		
		superclass.getPersistentType().getAttributeNamed("id").makeSpecified();
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		virtualAttributeOverride = overrideContainer.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, overrideContainer.virtualAttributeOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(0, overrideContainer.specifiedAttributeOverridesSize());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.specifiedAttributeOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.virtualAttributeOverridesSize());

		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(2, overrideContainer.virtualAttributeOverridesSize());
		
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(1, overrideContainer.virtualAttributeOverridesSize());
		
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(0, overrideContainer.virtualAttributeOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.attributeOverridesSize());

		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, overrideContainer.attributeOverridesSize());
		
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, overrideContainer.attributeOverridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("bar");
		assertEquals(4, overrideContainer.attributeOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals("name", entityResource.getAttributeOverrides().get(1).getName());		
		assertEquals(2, entityResource.getAttributeOverrides().size());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(1).getName());		
		assertEquals(2, entityResource.getAttributeOverrides().size());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAttributeOverrides().size());

		overrideContainer.specifiedAttributeOverrides().next().setVirtual(true);
		
		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals(2, entityResource.getAttributeOverrides().size());
		
		Iterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.specifiedAttributeOverrides().next().setVirtual(true);
		assertEquals("foo", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals(1, entityResource.getAttributeOverrides().size());

		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.specifiedAttributeOverrides().next().setVirtual(true);
		assertEquals(0, entityResource.getAttributeOverrides().size());
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		overrideContainer.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(2).getName());


		overrideContainer.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		assertEquals("foo", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("name", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
	
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().add(2, OrmFactory.eINSTANCE.createXmlAttributeOverride());
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
		attributeOverrides = overrideContainer.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();	
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

		overrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		AttributeOverride specifiedAttributeOverride = overrideContainer.specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = overrideContainer.virtualAttributeOverrides();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();

		Iterator<String> overridableAssociationNames = entity.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();

		Iterator<String> overridableAssociationNames = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociationNames.next());
		assertEquals("address2", overridableAssociationNames.next());
		assertEquals("address3", overridableAssociationNames.next());
		assertEquals("address4", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociations() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAssociations = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmAssociationOverride> specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("FOO");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(1, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAR");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAZ");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().move(1, 0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testDefaultAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(FULLY_QUALIFIED_SUB_TYPE_NAME, entityResource.getClassName());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
		
		assertEquals(4, overrideContainer.virtualAssociationOverridesSize());
		AssociationOverride virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
//
//		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
//		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
//		//idMapping.getColumn().setSpecifiedName("FOO");
//		//idMapping.getColumn().setSpecifiedTable("BAR");
//		
//		assertEquals(SUB_TYPE_NAME, entityResource.getName());
//		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
//		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());

//		//idMapping.getColumn().setSpecifiedName(null);
//		//idMapping.getColumn().setSpecifiedTable(null);
//		assertEquals(SUB_TYPE_NAME, typeResource.getName());
//		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
//		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride = virtualAssociationOverride.setVirtual(false);
		assertEquals(3, overrideContainer.virtualAssociationOverridesSize());
		
		
		
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, overrideContainer.specifiedAssociationOverridesSize());

		//add to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.specifiedAssociationOverridesSize());
	}
	
	public void testDefaultAssociationOverridesSize() throws Exception {		
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(3, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(0, overrideContainer.virtualAssociationOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.associationOverridesSize());

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(4, overrideContainer.associationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(4, overrideContainer.associationOverridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("bar");
		assertEquals(5, overrideContainer.associationOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("address", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address2", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, entityResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, entityResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());

		overrideContainer.specifiedAssociationOverrides().next().setVirtual(true);
		
		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals(1, entityResource.getAssociationOverrides().size());

		Iterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedAssociationOverrides().next().setVirtual(true);
		assertEquals(0, entityResource.getAssociationOverrides().size());
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedAssociationOverride(1, 0);
		ListIterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", entityResource.getAssociationOverrides().get(1).getName());


		overrideContainer.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		assertEquals("address", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address2", entityResource.getAssociationOverrides().get(1).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(1, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().add(2, OrmFactory.eINSTANCE.createXmlAssociationOverride());
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
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();	
		AssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedAssociationOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestMappedSuperclassCustomer();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		AttributeOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(7, attributeOverrideContainer.virtualAttributeOverridesSize());
		ListIterator<AttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		AttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.state", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
		zipCodePersistentType.getAttributeNamed("plusfour").makeSpecified();
		BasicMapping plusFourMapping = (BasicMapping) zipCodePersistentType.getAttributeNamed("plusfour").getMapping();
		plusFourMapping.getColumn().setSpecifiedName("BLAH");
		plusFourMapping.getColumn().setSpecifiedTable("BLAH_TABLE");
		plusFourMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		plusFourMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		plusFourMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		plusFourMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		plusFourMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = attributeOverrideContainer.getAttributeOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTable());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());
		
		//set an attribute override on Address.zipCode embedded mapping
		addressPersistentType.getAttributeNamed("zipCode").makeSpecified();
		AttributeOverride specifiedAttributeOverride = ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getAttributeOverrideNamed("plusfour").setVirtual(false);
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = attributeOverrideContainer.getAttributeOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH_OVERRIDE", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE_OVERRIDE", virtualAttributeOverride.getColumn().getTable());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		specifiedAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("address.zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals(null, specifiedAttributeOverride.getColumn().getSpecifiedTable());	
		assertEquals(null, specifiedAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, specifiedAttributeOverride.getColumn().isInsertable());
		assertEquals(true, specifiedAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, specifiedAttributeOverride.getColumn().isUnique());
		assertEquals(true, specifiedAttributeOverride.getColumn().isNullable());
		assertEquals(255, specifiedAttributeOverride.getColumn().getLength());
		assertEquals(0, specifiedAttributeOverride.getColumn().getPrecision());
		assertEquals(0, specifiedAttributeOverride.getColumn().getScale());
	}
	
	public void testSetSpecifiedCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
		
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, entityResource.getCacheable());
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, entityResource.getCacheable());
		
		cacheable.setSpecifiedCacheable(null);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
	}
	
	public void testGetSpecifiedCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
		
		entityResource.setCacheable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, entityResource.getCacheable());

		entityResource.setCacheable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, entityResource.getCacheable());
		
		entityResource.setCacheable(null);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
	}
	
	public void testIsDefaultCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		assertEquals(SharedCacheMode.UNSPECIFIED, persistenceUnit2_0.getSharedCacheMode());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ALL);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.NONE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testIsDefaultCacheableFromSuperType() throws Exception {
		createTestEntity();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) subOrmPersistentType.getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
	}
	
	public void testIsDefaultCacheableFromJava() throws Exception {
		createTestEntity();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) subOrmPersistentType.getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		
		Cacheable2_0 javaCacheable = ((CacheableHolder2_0) ormPersistentType.getJavaPersistentType().getMapping()).getCacheable();
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
	}
}
