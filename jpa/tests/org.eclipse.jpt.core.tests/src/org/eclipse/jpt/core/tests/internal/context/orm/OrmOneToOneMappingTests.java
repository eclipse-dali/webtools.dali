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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmOneToOneMappingTests extends ContextModelTestCase
{
	public OrmOneToOneMappingTests(String name) {
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
	
	private ICompilationUnit createTestEntityOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne(fetch=FetchType.LAZY, optional=false, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @JoinColumn(name=\"MY_COLUMN\", referencedColumnName=\"MY_REFERENCED_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\")");
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private void createTestTargetEntityAddress() throws Exception {
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
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("oneToOneMapping", ormOneToOneMapping.getName());
		assertEquals("oneToOneMapping", oneToOne.getName());
				
		//set name in the resource model, verify context model updated
		oneToOne.setName("newName");
		assertEquals("newName", ormOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the resource model
		oneToOne.setName(null);
		assertNull(ormOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("oneToOneMapping", ormOneToOneMapping.getName());
		assertEquals("oneToOneMapping", oneToOne.getName());
				
		//set name in the context model, verify resource model updated
		ormOneToOneMapping.setName("newName");
		assertEquals("newName", ormOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the context model
		ormOneToOneMapping.setName(null);
		assertNull(ormOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToOne.setTargetEntity(null);
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the context model
		ormOneToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOneResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
	
		oneToOneResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());

		//set fetch to null in the resource model
		oneToOneResource.setFetch(null);
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getSpecifiedFetch());
	
		ormOneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());
		assertEquals(FetchType.LAZY, ormOneToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormOneToOneMapping.setSpecifiedFetch(null);
		assertNull(oneToOneResource.getFetch());
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToOne.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", ormOneToOneMapping.getMappedBy());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToOne.setMappedBy(null);
		assertNull(ormOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		ormOneToOneMapping.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", ormOneToOneMapping.getMappedBy());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the context model
		ormOneToOneMapping.setMappedBy(null);
		assertNull(ormOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}
	
	
	public void testUpdateSpecifiedOptional() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the resource model, verify context model updated
		oneToOneResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
	
		oneToOneResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());

		//set optional to null in the resource model
		oneToOneResource.setOptional(null);
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOptional());
	
		ormOneToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOptional());

		//set optional to null in the context model
		ormOneToOneMapping.setSpecifiedOptional(null);
		assertNull(oneToOneResource.getOptional());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
	}
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmJoinColumn joinColumn = ormOneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = ormOneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = ormOneToOneMapping.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = ormOneToOneMapping.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormOneToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		ormOneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormOneToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormOneToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getJoinColumns().size());
		
		ormOneToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(2, oneToOneResource.getJoinColumns().size());
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());

		ormOneToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(1, oneToOneResource.getJoinColumns().size());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(0).getName());
		
		ormOneToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(0, oneToOneResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		ormOneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormOneToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormOneToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getJoinColumns().size());
		
		
		ormOneToOneMapping.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = ormOneToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());


		ormOneToOneMapping.moveSpecifiedJoinColumn(0, 1);
		joinColumns = ormOneToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());
	}
	
	public void testOneToOneMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", ormOneToOneMapping.getName());

		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		assertNull(ormOneToOneMapping.getTargetEntity());

		
		assertFalse(ormOneToOneMapping.specifiedJoinColumns().hasNext());
		//TODO default joinColumns
		//assertTrue(ormOneToOneMapping.defaultJoinColumns().hasNext());
	
	
		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToOneMapping.getName());
		assertEquals(FetchType.LAZY, ormOneToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOptional());
		assertEquals("Address", ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(ormOneToOneMapping.getMappedBy());

		OrmJoinColumn ormJoinColumn = ormOneToOneMapping.specifiedJoinColumns().next();
		assertEquals("MY_COLUMN", ormJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", ormJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", ormJoinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", ormJoinColumn.getSpecifiedTable());

		Cascade cascade = ormOneToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.makeSpecified(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.specifiedAttributes().next();

		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToOneMapping.getName());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		assertEquals("test.Address", ormOneToOneMapping.getTargetEntity());
		assertNull(ormOneToOneMapping.getMappedBy());

		//TODO default join columns in xml one-to-one
//		XmlJoinColumn ormJoinColumn = ormOneToOneMapping.specifiedJoinColumns().next();
//		//TODO java default columns name in JavaSingleRelationshipMapping.JoinColumnOwner
//		//assertEquals("address", ormJoinColumn.getSpecifiedName());
//		//assertEquals("address", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedNullable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUpdatable());
//		assertNull(ormJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, ormJoinColumn.getSpecifiedTable());

		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormOneToOneMapping.getName());
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(ormOneToOneMapping.getMappedBy());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		//TODO default target entity in xml
		//assertEquals("test.Address", ormOneToOneMapping.getDefaultTargetEntity());
		
		assertFalse(ormOneToOneMapping.specifiedJoinColumns().hasNext());
		
		//TODO default join columns for specified xmlOneToOne mapping
//		XmlJoinColumn ormJoinColumn = ormOneToOneMapping.defaultJoinColumns().next();
//		assertNull(ormJoinColumn.getSpecifiedName());
//		assertNull(ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertNull(ormJoinColumn.getSpecifiedUnique());
//		assertNull(ormJoinColumn.getSpecifiedNullable());
//		assertNull(ormJoinColumn.getSpecifiedInsertable());
//		assertNull(ormJoinColumn.getSpecifiedUpdatable());
//		assertNull(ormJoinColumn.getColumnDefinition());
//		assertNull(ormJoinColumn.getSpecifiedTable());
//		
//		assertEquals("address", ormJoinColumn.getDefaultName());
//		assertEquals("address", ormJoinColumn.getDefaultReferencedColumnName());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getDefaultUnique());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultNullable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultInsertable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultUpdatable());
//		assertEquals(null, ormJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, ormJoinColumn.getDefaultTable());

		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	
	public void testOneToOneMorphToIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToVersionMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToTransientMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");

		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToEmbeddedMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");

		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToEmbeddedIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToManyToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToOneMorphToOneToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToOneMorphToManyToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
		
		joinColumn = ((ManyToOneMapping) ormPersistentAttribute.getMapping()).specifiedJoinColumns().next();
		assertEquals("name", joinColumn.getName());		
		assertEquals("referenceName", joinColumn.getReferencedColumnName());		
	}
	
	public void testOneToOneMorphToBasicMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.setMappedBy("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	
	
	
	
	public void testAddPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmPrimaryKeyJoinColumn joinColumn = ormOneToOneMapping.addPrimaryKeyJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn joinColumn2 = ormOneToOneMapping.addPrimaryKeyJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn joinColumn3 = ormOneToOneMapping.addPrimaryKeyJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmPrimaryKeyJoinColumn> joinColumns = ormOneToOneMapping.primaryKeyJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormOneToOneMapping.primaryKeyJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemovePrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		ormOneToOneMapping.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormOneToOneMapping.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormOneToOneMapping.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getPrimaryKeyJoinColumns().size());
		
		ormOneToOneMapping.removePrimaryKeyJoinColumn(0);
		assertEquals(2, oneToOneResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());

		ormOneToOneMapping.removePrimaryKeyJoinColumn(0);
		assertEquals(1, oneToOneResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		ormOneToOneMapping.removePrimaryKeyJoinColumn(0);
		assertEquals(0, oneToOneResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMovePrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		ormOneToOneMapping.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormOneToOneMapping.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormOneToOneMapping.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getPrimaryKeyJoinColumns().size());
		
		
		ormOneToOneMapping.movePrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmPrimaryKeyJoinColumn> joinColumns = ormOneToOneMapping.primaryKeyJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());


		ormOneToOneMapping.movePrimaryKeyJoinColumn(0, 1);
		joinColumns = ormOneToOneMapping.primaryKeyJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());
	}

}