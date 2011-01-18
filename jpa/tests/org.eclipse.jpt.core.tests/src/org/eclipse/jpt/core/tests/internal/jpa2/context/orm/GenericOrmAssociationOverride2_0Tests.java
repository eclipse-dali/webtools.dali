/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.VirtualJoinTable;
import org.eclipse.jpt.core.context.VirtualJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.jpa2.context.AssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.VirtualAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmAssociationOverride2_0Tests extends Generic2_0ContextModelTestCase
{
	private ICompilationUnit createTestMappedSuperclass() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne");
				sb.append(CR);
				sb.append("    private AnnotationTestTypeChild address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassManyToMany() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany");
				sb.append(CR);
				sb.append("    private java.util.Collection<AnnotationTestTypeChild> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
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


		
	public GenericOrmAssociationOverride2_0Tests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride ormVirtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", ormVirtualAssociationOverride.getName());
	
		ormVirtualAssociationOverride.convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		OrmAssociationOverride ormAssociationOverride = overrideContainer.specifiedOverrides().next();
		
		assertEquals("address", ormAssociationOverride.getName());
		assertEquals("address", xmlAssociationOverride.getName());
		assertTrue(overrideContainer.overrides().hasNext());
		assertFalse(entityResource.getAssociationOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		xmlAssociationOverride.setName("FOO");
		assertEquals("FOO", ormAssociationOverride.getName());
		assertEquals("FOO", xmlAssociationOverride.getName());
	
		//set name to null in the resource model
		xmlAssociationOverride.setName(null);
		assertNull(ormAssociationOverride.getName());
		assertNull(xmlAssociationOverride.getName());
		
		xmlAssociationOverride.setName("FOO");
		assertEquals("FOO", ormAssociationOverride.getName());
		assertEquals("FOO", xmlAssociationOverride.getName());

		entityResource.getAssociationOverrides().remove(0);
		assertFalse(overrideContainer.specifiedOverrides().hasNext());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
	
		virtualAssociationOverride.convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		
		assertEquals("address", specifiedAssociationOverride.getName());
		assertEquals("address", xmlAssociationOverride.getName());
		assertTrue(overrideContainer.overrides().hasNext());
		assertFalse(entityResource.getAssociationOverrides().isEmpty());
		
		//set name in the context model, verify resource model modified
		specifiedAssociationOverride.setName("foo");
		assertEquals("foo", specifiedAssociationOverride.getName());
		assertEquals("foo", xmlAssociationOverride.getName());
		
		//set name to null in the context model
		specifiedAssociationOverride.setName(null);
		assertNull(specifiedAssociationOverride.getName());
		xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		assertNull(xmlAssociationOverride.getName());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride ormAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", ormAssociationOverride.getName());
	
		OrmAssociationOverride specifiedOverride = ormAssociationOverride.convertToSpecified();
		OrmJoinColumnJoiningStrategy joiningStrategy = specifiedOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);

		
		JoinColumn joinColumn = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		JoinColumn joinColumn2 = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(1).getName());
		
		JoinColumn joinColumn3 = joiningStrategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride ormAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", ormAssociationOverride.getName());
	
		AssociationOverride specifiedOverride = ormAssociationOverride.convertToSpecified();
		JoinColumnJoiningStrategy joiningStrategy = specifiedOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		assertEquals(1, xmlAssociationOverride.getJoinColumns().size());

		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(4, xmlAssociationOverride.getJoinColumns().size());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(3, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());

		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, xmlAssociationOverride.getJoinColumns().size());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, xmlAssociationOverride.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride ormAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", ormAssociationOverride.getName());
	
		OrmAssociationOverride specifiedOverride = ormAssociationOverride.convertToSpecified();
		OrmJoinColumnJoiningStrategy joiningStrategy = specifiedOverride.getRelationshipReference().getJoinColumnJoiningStrategy();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
				
		assertEquals(4, xmlAssociationOverride.getJoinColumns().size());
		
		
		joiningStrategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());


		joiningStrategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride ormAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", ormAssociationOverride.getName());
	
		OrmAssociationOverride specifiedOverride = ormAssociationOverride.convertToSpecified();
		OrmJoinColumnJoiningStrategy joiningStrategy = specifiedOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		ListIterator<OrmJoinColumn> joinColumns = joiningStrategy.specifiedJoinColumns();
		JoinColumn joinColumn = joinColumns.next();
		assertEquals("address_id", joinColumn.getSpecifiedName());
		assertEquals("id", joinColumn.getSpecifiedReferencedColumnName());
	
		xmlAssociationOverride.getJoinColumns().add(0, OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinColumns().add(1, OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinColumns().add(2, OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		xmlAssociationOverride.getJoinColumns().get(0).setName("FOO");
		xmlAssociationOverride.getJoinColumns().get(1).setName("BAR");
		xmlAssociationOverride.getJoinColumns().get(2).setName("BAZ");

		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinColumns().move(2, 0);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().move(0, 1);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());		
		
		xmlAssociationOverride.getJoinColumns().remove(0);
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("address_id", joinColumns.next().getName());

		xmlAssociationOverride.getJoinColumns().remove(0);
		assertFalse(joiningStrategy.specifiedJoinColumns().hasNext());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		assertEquals("AnnotationTestTypeChild", ormEntity.getName());
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		ReadOnlyAssociationOverride associationOverride = overrideContainer.virtualOverrides().next();
		assertTrue(associationOverride.isVirtual());
	}
	
	
	public void testSetJoinTableName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		OrmVirtualAssociationOverride virtualOverride = overrideContainer.virtualOverrides().next();
		OrmAssociationOverride associationOverride = virtualOverride.convertToSpecified();
		((AssociationOverrideRelationshipReference2_0) associationOverride.getRelationshipReference()).setJoinTableJoiningStrategy();
		JoinTableJoiningStrategy joiningStrategy = ((AssociationOverrideRelationshipReference2_0) associationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);

		
		JoinTable joinTable = joiningStrategy.getJoinTable();
		joinTable.setSpecifiedName("FOO");
				
		assertEquals("FOO", xmlAssociationOverride.getJoinTable().getName());
	}
	
	public void testUpdateJoinTable() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		OrmVirtualAssociationOverride virtualOverride = overrideContainer.virtualOverrides().next();
		OrmAssociationOverride associationOverride = virtualOverride.convertToSpecified();
		((OrmAssociationOverrideRelationshipReference2_0) associationOverride.getRelationshipReference()).setJoinTableJoiningStrategy();
		OrmJoinTableJoiningStrategy joiningStrategy = ((OrmAssociationOverrideRelationshipReference2_0) associationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
	
		xmlAssociationOverride.getJoinTable().setName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", joiningStrategy.getJoinTable().getSpecifiedName());

		xmlAssociationOverride.getJoinTable().getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinTable().getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinTable().getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		xmlAssociationOverride.getJoinTable().getJoinColumns().get(0).setName("FOO");
		xmlAssociationOverride.getJoinTable().getJoinColumns().get(1).setName("BAR");
		xmlAssociationOverride.getJoinTable().getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinTable().getJoinColumns().move(2, 0);
		joinColumns = joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinTable().getJoinColumns().move(0, 1);
		joinColumns = joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinTable().getJoinColumns().remove(1);
		joinColumns = joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinTable().getJoinColumns().remove(1);
		joinColumns = joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinTable().getJoinColumns().remove(0);
		assertFalse(joiningStrategy.getJoinTable().specifiedJoinColumns().hasNext());
	}	
	
	public void testDefaultJoinTableName() throws Exception {
		createTestMappedSuperclassManyToMany();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		ReadOnlyAssociationOverride associationOverride = overrideContainer.virtualOverrides().next();
		VirtualJoinTableJoiningStrategy joiningStrategy = ((VirtualAssociationOverrideRelationshipReference2_0) associationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();

		VirtualJoinTable joinTable = joiningStrategy.getJoinTable();
		
		assertEquals("AnnotationTestTypeChild_AnnotationTestTypeChild", joinTable.getName());
		
		
		ormEntity.getTable().setSpecifiedName("FOO");
		assertEquals("FOO_FOO", joinTable.getName());
		
		PersistentType mappedSuperclass = ormPersistentType.getSuperPersistentType();
		((OrmReadOnlyPersistentAttribute)  mappedSuperclass.getAttributeNamed("address")).convertToSpecified();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) mappedSuperclass.getAttributeNamed("address").getMapping();
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("BAR");
		assertEquals("BAR", manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().getName());
	}
}
