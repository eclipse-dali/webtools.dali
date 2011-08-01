/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmCollectionTableTests extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmCollectionTableTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityWithValidElementCollection() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
				sb.append("    private Collection<String> projects;").append(CR);
				sb.append("@Id").append(CR);
			}
		});
	}

	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = elementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedName());
		assertNull(resourceElementCollection.getCollectionTable());
		
		
		//set name in the resource model, verify context model updated
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		resourceElementCollection.getCollectionTable().setName("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedName());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getName());
	
		//set name to null in the resource model
		resourceElementCollection.getCollectionTable().setName(null);
		assertNull(ormCollectionTable.getSpecifiedName());
		assertNull(resourceElementCollection.getCollectionTable().getName());
		
		resourceElementCollection.getCollectionTable().setName("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedName());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getName());

		resourceElementCollection.setCollectionTable(null);
		assertNull(ormCollectionTable.getSpecifiedName());
		assertNull(resourceElementCollection.getCollectionTable());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = elementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedName());
		assertNull(resourceElementCollection.getCollectionTable());
		
		//set name in the context model, verify resource model modified
		ormCollectionTable.setSpecifiedName("foo");
		assertEquals("foo", ormCollectionTable.getSpecifiedName());
		assertEquals("foo", resourceElementCollection.getCollectionTable().getName());
		
		//set name to null in the context model
		ormCollectionTable.setSpecifiedName(null);
		assertNull(ormCollectionTable.getSpecifiedName());
		assertNull(resourceElementCollection.getCollectionTable());
	}
	
	public void testVirtualCollectionTable() throws Exception {
		createTestEntityWithValidElementCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("projects");
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		CollectionTable2_0 virtualCollectionTable = virtualElementCollectionMapping.getCollectionTable();
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(TYPE_NAME + "_projects", virtualCollectionTable.getName());
		assertNull(virtualCollectionTable.getSpecifiedCatalog());
		assertNull(virtualCollectionTable.getSpecifiedSchema());
		assertEquals(0, virtualCollectionTable.getSpecifiedJoinColumnsSize());
		JoinColumn virtualJoinColumn = virtualCollectionTable.getDefaultJoinColumn();
		assertEquals(TYPE_NAME + "_id", virtualJoinColumn.getDefaultName());
		assertEquals("id", virtualJoinColumn.getDefaultReferencedColumnName());
	
		JavaPersistentAttribute javaPersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) javaPersistentAttribute.getMapping();
		JavaCollectionTable2_0 javaCollectionTable = javaElementCollectionMapping.getCollectionTable();
		javaCollectionTable.setSpecifiedName("FOO");
		javaCollectionTable.setSpecifiedCatalog("CATALOG");
		javaCollectionTable.setSpecifiedSchema("SCHEMA");
		JavaJoinColumn javaJoinColumn = javaCollectionTable.addSpecifiedJoinColumn(0);
		javaJoinColumn.setSpecifiedName("NAME");
		javaJoinColumn.setSpecifiedReferencedColumnName("REFERENCED_NAME");
		
		assertEquals("FOO", virtualCollectionTable.getSpecifiedName());
		assertEquals("CATALOG", virtualCollectionTable.getSpecifiedCatalog());
		assertEquals("SCHEMA", virtualCollectionTable.getSpecifiedSchema());
		assertEquals(1, virtualCollectionTable.getSpecifiedJoinColumnsSize());
		virtualJoinColumn = virtualCollectionTable.getSpecifiedJoinColumns().iterator().next();
		assertEquals("NAME", virtualJoinColumn.getSpecifiedName());
		assertEquals("REFERENCED_NAME", virtualJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntityWithValidElementCollection();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "projects");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		
		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		assertEquals(TYPE_NAME + "_projects", ormCollectionTable.getDefaultName());

		((JavaEntity) ormPersistentType.getJavaPersistentType().getMapping()).setSpecifiedName("Foo");
		assertEquals("Foo_projects", ormCollectionTable.getDefaultName());
		
		((OrmEntity) ormPersistentType.getMapping()).setSpecifiedName("Bar");
		assertEquals("Bar_projects", ormCollectionTable.getDefaultName());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("projects").getMapping();
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName("JAVA_COLLECTION_TABLE");
		
		assertEquals("Bar_projects", ormCollectionTable.getDefaultName());

		
		//set metadata-complete to true, will ignore java annotation settings
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals("Bar_projects", ormCollectionTable.getDefaultName());
		
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		//remove element collection mapping from the orm.xml file
		ormPersistentAttribute.convertToVirtual();
		//ormPersistentType.getMapping().setSpecifiedMetadataComplete(null);
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("projects");
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute2.getMapping();
		CollectionTable2_0  virtualCollectionTable = virtualElementCollectionMapping.getCollectionTable();
		assertTrue(ormPersistentAttribute2.isVirtual());
		assertEquals("JAVA_COLLECTION_TABLE", virtualCollectionTable.getSpecifiedName());//specifiedName since this is a virtual mapping now
		
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName(null);
		assertNull(virtualCollectionTable.getSpecifiedName());
		assertEquals("Bar_projects", virtualCollectionTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedSchema());
		assertNull(resourceElementCollection.getCollectionTable());
		
		//set schema in the resource model, verify context model updated
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		resourceElementCollection.getCollectionTable().setSchema("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedSchema());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getSchema());
	
		//set Schema to null in the resource model
		resourceElementCollection.getCollectionTable().setSchema(null);
		assertNull(ormCollectionTable.getSpecifiedSchema());
		assertNull(resourceElementCollection.getCollectionTable().getSchema());
		
		resourceElementCollection.getCollectionTable().setSchema("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedSchema());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getSchema());

		resourceElementCollection.setCollectionTable(null);
		assertNull(ormCollectionTable.getSpecifiedSchema());
		assertNull(resourceElementCollection.getCollectionTable());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedSchema());
		assertNull(resourceElementCollection.getCollectionTable());
		
		//set Schema in the context model, verify resource model modified
		ormCollectionTable.setSpecifiedSchema("foo");
		assertEquals("foo", ormCollectionTable.getSpecifiedSchema());
		assertEquals("foo", resourceElementCollection.getCollectionTable().getSchema());
		
		//set Schema to null in the context model
		ormCollectionTable.setSpecifiedSchema(null);
		assertNull(ormCollectionTable.getSpecifiedSchema());
		assertNull(resourceElementCollection.getCollectionTable());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedCatalog());
		assertNull(resourceElementCollection.getCollectionTable());
		
		//set Catalog in the resource model, verify context model updated
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		resourceElementCollection.getCollectionTable().setCatalog("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedCatalog());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getCatalog());
	
		//set Catalog to null in the resource model
		resourceElementCollection.getCollectionTable().setCatalog(null);
		assertNull(ormCollectionTable.getSpecifiedCatalog());
		assertNull(resourceElementCollection.getCollectionTable().getCatalog());
		
		resourceElementCollection.getCollectionTable().setCatalog("FOO");
		assertEquals("FOO", ormCollectionTable.getSpecifiedCatalog());
		assertEquals("FOO", resourceElementCollection.getCollectionTable().getCatalog());

		resourceElementCollection.setCollectionTable(null);
		assertNull(ormCollectionTable.getSpecifiedCatalog());
		assertNull(resourceElementCollection.getCollectionTable());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		assertNull(ormCollectionTable.getSpecifiedCatalog());
		assertNull(resourceElementCollection.getCollectionTable());
		
		//set Catalog in the context model, verify resource model modified
		ormCollectionTable.setSpecifiedCatalog("foo");
		assertEquals("foo", ormCollectionTable.getSpecifiedCatalog());
		assertEquals("foo", resourceElementCollection.getCollectionTable().getCatalog());
		
		//set Catalog to null in the context model
		ormCollectionTable.setSpecifiedCatalog(null);
		assertNull(ormCollectionTable.getSpecifiedCatalog());
		assertNull(resourceElementCollection.getCollectionTable());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		OrmJoinColumn joinColumn = ormCollectionTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		assertEquals("FOO", resourceCollectionTable.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = ormCollectionTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", resourceCollectionTable.getJoinColumns().get(0).getName());
		assertEquals("FOO", resourceCollectionTable.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = ormCollectionTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", resourceCollectionTable.getJoinColumns().get(0).getName());
		assertEquals("BAZ", resourceCollectionTable.getJoinColumns().get(1).getName());
		assertEquals("FOO", resourceCollectionTable.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		ormCollectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormCollectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormCollectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		assertEquals(3, resourceCollectionTable.getJoinColumns().size());
		
		ormCollectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, resourceCollectionTable.getJoinColumns().size());
		assertEquals("BAR", resourceCollectionTable.getJoinColumns().get(0).getName());
		assertEquals("BAZ", resourceCollectionTable.getJoinColumns().get(1).getName());

		ormCollectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, resourceCollectionTable.getJoinColumns().size());
		assertEquals("BAZ", resourceCollectionTable.getJoinColumns().get(0).getName());
		
		ormCollectionTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, resourceCollectionTable.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		
		ormCollectionTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormCollectionTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormCollectionTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		assertEquals(3, resourceCollectionTable.getJoinColumns().size());
		
		
		ormCollectionTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", resourceCollectionTable.getJoinColumns().get(0).getName());
		assertEquals("BAZ", resourceCollectionTable.getJoinColumns().get(1).getName());
		assertEquals("FOO", resourceCollectionTable.getJoinColumns().get(2).getName());


		ormCollectionTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", resourceCollectionTable.getJoinColumns().get(0).getName());
		assertEquals("BAR", resourceCollectionTable.getJoinColumns().get(1).getName());
		assertEquals("FOO", resourceCollectionTable.getJoinColumns().get(2).getName());
	}
	
	
	public void testUpdateJoinColumns() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
	
		resourceCollectionTable.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		resourceCollectionTable.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		resourceCollectionTable.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		resourceCollectionTable.getJoinColumns().get(0).setName("FOO");
		resourceCollectionTable.getJoinColumns().get(1).setName("BAR");
		resourceCollectionTable.getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		resourceCollectionTable.getJoinColumns().move(2, 0);
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		resourceCollectionTable.getJoinColumns().move(0, 1);
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		resourceCollectionTable.getJoinColumns().remove(1);
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		resourceCollectionTable.getJoinColumns().remove(1);
		joinColumns = ormCollectionTable.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		resourceCollectionTable.getJoinColumns().remove(0);
		assertFalse(ormCollectionTable.getSpecifiedJoinColumns().iterator().hasNext());
	}

	public void testUniqueConstraints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		
		assertEquals(0,  ormCollectionTable.getUniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  ormCollectionTable.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");

		ListIterator<XmlUniqueConstraint> uniqueConstraints = resourceCollectionTable.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();

		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormCollectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = resourceCollectionTable.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormCollectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormCollectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, resourceCollectionTable.getUniqueConstraints().size());

		ormCollectionTable.removeUniqueConstraint(1);
		
		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = resourceCollectionTable.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertFalse(uniqueConstraintResources.hasNext());
		
		Iterator<OrmUniqueConstraint> uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		ormCollectionTable.removeUniqueConstraint(1);
		uniqueConstraintResources = resourceCollectionTable.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		ormCollectionTable.removeUniqueConstraint(0);
		uniqueConstraintResources = resourceCollectionTable.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
		
		ormCollectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormCollectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormCollectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, resourceCollectionTable.getUniqueConstraints().size());
		
		
		ormCollectionTable.moveUniqueConstraint(2, 0);
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = resourceCollectionTable.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		ormCollectionTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintResources = resourceCollectionTable.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 resourceElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		OrmCollectionTable2_0 ormCollectionTable = ormElementCollectionMapping.getCollectionTable();
		resourceElementCollection.setCollectionTable(OrmFactory.eINSTANCE.createXmlCollectionTable());
		XmlCollectionTable resourceCollectionTable = resourceElementCollection.getCollectionTable();
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		resourceCollectionTable.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		resourceCollectionTable.getUniqueConstraints().move(2, 0);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		resourceCollectionTable.getUniqueConstraints().move(0, 1);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		resourceCollectionTable.getUniqueConstraints().remove(1);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		resourceCollectionTable.getUniqueConstraints().remove(1);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		resourceCollectionTable.getUniqueConstraints().remove(0);
		uniqueConstraints = ormCollectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}

	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntityWithValidElementCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributes().iterator().next().getMapping();
		CollectionTable2_0 virtualCollectionTable = virtualElementCollectionMapping.getCollectionTable();
		
		assertTrue(ormPersistentType.getAttributes().iterator().next().isVirtual());
		
		ListIterator<? extends UniqueConstraint> uniqueConstraints = virtualCollectionTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());

		JavaElementCollectionMapping2_0 javaElementCollectionMapping2_0 = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaCollectionTable2_0 javaCollectionTable = javaElementCollectionMapping2_0.getCollectionTable();
		
		javaCollectionTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		javaCollectionTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		javaCollectionTable.addUniqueConstraint(2).addColumnName(0, "BAZ");

		uniqueConstraints = virtualCollectionTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		ormPersistentType.getAttributes().iterator().next().convertToSpecified();
		
		virtualElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributes().iterator().next().getMapping();
		assertEquals(0,  virtualElementCollectionMapping.getCollectionTable().getUniqueConstraintsSize());
	}

}