/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlAssociationOverride_2_0;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
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
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Customer.java", sourceWriter);
	}
	private void createTestMappedSuperclassCustomerWithElementCollection() throws Exception {
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
					sb.append(JPA2_0.ELEMENT_COLLECTION);
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
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Customer.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "LongTimeCustomer.java", sourceWriter);
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
					sb.append("import ");
					sb.append(JPA.ONE_TO_ONE);
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
				sb.append("    @OneToOne").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private ZipCode zipCode;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "ZipCode.java", sourceWriter);
	}
	
	private LockModeType2_0 lockModeOf(XmlNamedQuery resourceQuery) {
		return resourceQuery == null ? null : LockModeType2_0.fromOrmResourceModel(resourceQuery.getLockMode());
	}

	public void testAddNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		OrmNamedQuery2_0 namedQuery = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(0);
			namedQuery.setName("FOO");
			namedQuery.setSpecifiedLockMode(LockModeType2_0.OPTIMISTIC);
			
		XmlNamedQuery resourceQuery = entityResource.getNamedQueries().get(0);
			assertEquals("FOO", resourceQuery.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, this.lockModeOf(resourceQuery));
		
		OrmNamedQuery2_0 namedQuery2 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(0);
			namedQuery2.setName("BAR");
			namedQuery2.setSpecifiedLockMode(LockModeType2_0.READ);

		resourceQuery = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", resourceQuery.getName());
			assertEquals(LockModeType2_0.READ, this.lockModeOf(resourceQuery));
		assertEquals("FOO", entityResource.getNamedQueries().get(1).getName());
		
		OrmNamedQuery2_0 namedQuery3 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(1);
			namedQuery3.setName("BAZ");
			namedQuery3.setSpecifiedLockMode(LockModeType2_0.WRITE);
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		resourceQuery = entityResource.getNamedQueries().get(1);
			assertEquals("BAZ", resourceQuery.getName());
			assertEquals(LockModeType2_0.WRITE, this.lockModeOf(resourceQuery));
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
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
			namedQuery1.setSpecifiedLockMode(LockModeType2_0.READ);
	
			OrmNamedQuery2_0 namedQuery2 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(2);
			namedQuery2.setName("BAZ");
			namedQuery2.setSpecifiedLockMode(LockModeType2_0.OPTIMISTIC);
	
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(2, entityResource.getNamedQueries().size());
		XmlNamedQuery xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", xmlQuery0.getName());
			assertEquals(LockModeType2_0.READ, this.lockModeOf(xmlQuery0));
	
		XmlNamedQuery xmlQuery1 = entityResource.getNamedQueries().get(1);
			assertEquals("BAZ", xmlQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, this.lockModeOf(xmlQuery1));

		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(1, entityResource.getNamedQueries().size());
		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAZ", xmlQuery0.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, this.lockModeOf(xmlQuery0));
		
		ormEntity.getQueryContainer().removeNamedQuery(0);
		assertEquals(0, entityResource.getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.getQueryContainer().addNamedQuery(0).setName("FOO");
		OrmNamedQuery2_0 namedQuery1 = (OrmNamedQuery2_0) ormEntity.getQueryContainer().addNamedQuery(1);
			namedQuery1.setName("BAR");
			namedQuery1.setSpecifiedLockMode(LockModeType2_0.OPTIMISTIC);
		ormEntity.getQueryContainer().addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		
		ormEntity.getQueryContainer().moveNamedQuery(2, 0);
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAR", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		XmlNamedQuery xmlQuery0 = entityResource.getNamedQueries().get(0);
			assertEquals("BAR", xmlQuery0.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, this.lockModeOf(xmlQuery0));
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());


		ormEntity.getQueryContainer().moveNamedQuery(0, 1);
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAZ", namedQueries.next().getName());
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAR", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		XmlNamedQuery xmlQuery1 = entityResource.getNamedQueries().get(1);
			assertEquals("BAR", xmlQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, this.lockModeOf(xmlQuery1));
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		assertEquals(0, ormEntity.getPersistenceUnit().getQueriesSize());
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().get(0).setName("FOO");
		entityResource.getNamedQueries().get(1).setName("BAR");
		XmlNamedQuery xmlQuery = entityResource.getNamedQueries().get(2);
			xmlQuery.setName("BAZ");
			xmlQuery.setLockMode(org.eclipse.jpt.jpa.core.resource.orm.v2_0.LockModeType_2_0.OPTIMISTIC);
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		OrmNamedQuery2_0 namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, ormEntity.getPersistenceUnit().getQueriesSize());
		
		entityResource.getNamedQueries().move(2, 0);
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		assertEquals("BAR", namedQueries.next().getName());
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().move(0, 1);
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, ormEntity.getPersistenceUnit().getQueriesSize());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.getQueryContainer().getNamedQueries().iterator();
		namedQuery1 = (OrmNamedQuery2_0) namedQueries.next();
			assertEquals("BAZ", namedQuery1.getName());
			assertEquals(LockModeType2_0.OPTIMISTIC, namedQuery1.getLockMode());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, ormEntity.getPersistenceUnit().getQueriesSize());
		
		entityResource.getNamedQueries().remove(0);
		assertFalse(ormEntity.getQueryContainer().getNamedQueries().iterator().hasNext());
		assertEquals(0, ormEntity.getPersistenceUnit().getQueriesSize());
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
	
		Iterator<String> overridableAttributes = entity.getOverridableAttributeNames().iterator();
		assertFalse(overridableAttributes.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = entity.getOverridableAttributeNames().iterator();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributeNames = entity.getOverridableAttributeNames().iterator();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = entity.getOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().getAllOverridableAttributeNames().iterator();
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
	
		Iterator<String> overridableAttributes = ormEntity.getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAttributes = abstractEntity.getAllOverridableAttributeNames().iterator();
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
	
		Iterator<String> overridableAssociations = ormEntity.getAllOverridableAssociationNames().iterator();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAssociations = abstractEntity.getAllOverridableAssociationNames().iterator();
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
	
		Iterator<String> overridableAttributeNames = ormEntity.getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		ListIterator<OrmSpecifiedAttributeOverride> specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(specifiedAttributeOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAZ");
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().move(1, 0);
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
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
		
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTableName());
		

		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) ormEntity.getPersistentType().getSuperPersistentType().getMapping();
		
		mappedSuperclass.getPersistentType().getAttributeNamed("id").addToXml();
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTableName("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTableName());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTableName(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTableName());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
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

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTableName());
		

		OrmEntity superclass = (OrmEntity) ormEntity.getParentEntity();
		
		superclass.getPersistentType().getAttributeNamed("id").addToXml();
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTableName("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTableName());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTableName(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTableName());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
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
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getOverridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("bar");
		assertEquals(4, overrideContainer.getOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAttributeOverrides().size());

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals(2, entityResource.getAttributeOverrides().size());
		
		Iterator<OrmSpecifiedAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		assertEquals("foo", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals(1, entityResource.getAttributeOverrides().size());

		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		assertEquals(0, entityResource.getAttributeOverrides().size());
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<OrmSpecifiedAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(2).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
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
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
	
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().add(2, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(2).setName("BAZ");
			
		ListIterator<OrmSpecifiedAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		SpecifiedAttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
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

		Iterator<String> overridableAssociationNames = entity.getOverridableAssociationNames().iterator();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();

		Iterator<String> overridableAssociationNames = ormEntity.getAllOverridableAssociationNames().iterator();
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
	
		Iterator<String> overridableAssociations = ormEntity.getAllOverridableAssociationNames().iterator();
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
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmSpecifiedAssociationOverride> specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("FOO");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(1, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAR");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAZ");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().move(1, 0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
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
		
		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
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

		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());

//		//idMapping.getColumn().setSpecifiedName(null);
//		//idMapping.getColumn().setSpecifiedTable(null);
//		assertEquals(SUB_TYPE_NAME, typeResource.getName());
//		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
//		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		
		
		
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
		
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		//add to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testDefaultAssociationOverridesSize() throws Exception {		
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
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
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("bar");
		assertEquals(5, overrideContainer.getOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals(1, entityResource.getAssociationOverrides().size());

		Iterator<OrmSpecifiedAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		assertEquals(0, entityResource.getAssociationOverrides().size());
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<OrmSpecifiedAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", entityResource.getAssociationOverrides().get(1).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
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
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(1, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().add(2, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(2).setName("BAZ");
			
		ListIterator<OrmSpecifiedAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().move(2, 0);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().move(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().remove(0);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		SpecifiedAssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
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
		
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAttributeOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTableName());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
		zipCodePersistentType.getAttributeNamed("plusfour").addToXml();
		BasicMapping plusFourMapping = (BasicMapping) zipCodePersistentType.getAttributeNamed("plusfour").getMapping();
		plusFourMapping.getColumn().setSpecifiedName("BLAH");
		plusFourMapping.getColumn().setSpecifiedTableName("BLAH_TABLE");
		plusFourMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		plusFourMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		plusFourMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		plusFourMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		plusFourMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());
		
		//set an attribute override on Address.zipCode embedded mapping
		addressPersistentType.getAttributeNamed("zipCode").addToXml();
		OrmSpecifiedAttributeOverride specifiedAttributeOverride = ((OrmVirtualAttributeOverride) ((OrmEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTableName("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH_OVERRIDE", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE_OVERRIDE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		specifiedAttributeOverride = virtualAttributeOverride.convertToSpecified();
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("address.zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals("BLAH_TABLE_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", specifiedAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, specifiedAttributeOverride.getColumn().isInsertable());
		assertEquals(true, specifiedAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, specifiedAttributeOverride.getColumn().isUnique());
		assertEquals(true, specifiedAttributeOverride.getColumn().isNullable());
		assertEquals(255, specifiedAttributeOverride.getColumn().getLength());
		assertEquals(0, specifiedAttributeOverride.getColumn().getPrecision());
		assertEquals(0, specifiedAttributeOverride.getColumn().getScale());
	}
	public void testNestedVirtualAttributeOverridesElementCollection() throws Exception {
		createTestMappedSuperclassCustomerWithElementCollection();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAttributeOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTableName());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
		zipCodePersistentType.getAttributeNamed("plusfour").addToXml();
		BasicMapping plusFourMapping = (BasicMapping) zipCodePersistentType.getAttributeNamed("plusfour").getMapping();
		plusFourMapping.getColumn().setSpecifiedName("BLAH");
		plusFourMapping.getColumn().setSpecifiedTableName("BLAH_TABLE");
		plusFourMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		plusFourMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		plusFourMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		plusFourMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		plusFourMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());
		
		//set an attribute override on Address.zipCode embedded mapping
		addressPersistentType.getAttributeNamed("zipCode").addToXml();
		OrmSpecifiedAttributeOverride specifiedAttributeOverride = ((OrmVirtualAttributeOverride) ((OrmEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTableName("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH_OVERRIDE", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE_OVERRIDE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		specifiedAttributeOverride = virtualAttributeOverride.convertToSpecified();
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("address.zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals("BLAH_TABLE_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", specifiedAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, specifiedAttributeOverride.getColumn().isInsertable());
		assertEquals(true, specifiedAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, specifiedAttributeOverride.getColumn().isUnique());
		assertEquals(true, specifiedAttributeOverride.getColumn().isNullable());
		assertEquals(255, specifiedAttributeOverride.getColumn().getLength());
		assertEquals(0, specifiedAttributeOverride.getColumn().getPrecision());
		assertEquals(0, specifiedAttributeOverride.getColumn().getScale());
	}

	public void testNestedVirtualAssociationOverrides() throws Exception {
		createTestMappedSuperclassCustomer();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAssociationOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}

	public void testNestedVirtualAssociationOverridesElementCollection() throws Exception {
		createTestMappedSuperclassCustomerWithElementCollection();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAssociationOverrideContainer overrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}

	public void testSetSpecifiedCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) ormPersistentType.getMapping()).getCacheable();
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
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) ormPersistentType.getMapping()).getCacheable();
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
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) ormPersistentType.getMapping()).getCacheable();
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		assertEquals(SharedCacheMode2_0.UNSPECIFIED, persistenceUnit2_0.getSharedCacheMode());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ALL);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.NONE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ENABLE_SELECTIVE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testIsDefaultCacheableFromSuperType() throws Exception {
		createTestEntity();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableReference2_0) subOrmPersistentType.getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableReference2_0) ormPersistentType.getMapping()).getCacheable();
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ENABLE_SELECTIVE);
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
		
		Cacheable2_0 subCacheable = ((CacheableReference2_0) subOrmPersistentType.getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableReference2_0) ormPersistentType.getMapping()).getCacheable();
		
		Cacheable2_0 javaCacheable = ((CacheableReference2_0) ormPersistentType.getJavaPersistentType().getMapping()).getCacheable();
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		PersistenceUnit2_0 persistenceUnit2_0 = (PersistenceUnit2_0) getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	//This is a test for bug 301892
	public void testAssociationOverrideJoinTableUpdate() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
		
		XmlEntity resourceEntity = getXmlEntityMappings().getEntities().get(0);
		
		XmlAssociationOverride_2_0 resourceAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		resourceEntity.getAssociationOverrides().add((XmlAssociationOverride) resourceAssociationOverride);
		((XmlAssociationOverride) resourceAssociationOverride).setName("a");
		
		OrmSpecifiedAssociationOverride associationOverride = entity.getAssociationOverrideContainer().getSpecifiedOverrides().iterator().next();
		assertEquals("a", associationOverride.getName());
		
		XmlJoinTable resourceJoinTable = OrmFactory.eINSTANCE.createXmlJoinTable();
		resourceAssociationOverride.setJoinTable(resourceJoinTable);
		resourceJoinTable.setName("FOO");
		XmlJoinColumn resourceJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
		resourceJoinTable.getInverseJoinColumns().add(resourceJoinColumn);
		resourceJoinColumn.setName("BAR");

		associationOverride = entity.getAssociationOverrideContainer().getSpecifiedOverrides().iterator().next();
		assertEquals("a", associationOverride.getName());
		SpecifiedJoinTable joinTable = ((OrmSpecifiedOverrideRelationship2_0) associationOverride.getRelationship()).getJoinTableStrategy().getJoinTable();
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("BAR", joinTable.getInverseJoinColumns().iterator().next().getName());
	}
}
