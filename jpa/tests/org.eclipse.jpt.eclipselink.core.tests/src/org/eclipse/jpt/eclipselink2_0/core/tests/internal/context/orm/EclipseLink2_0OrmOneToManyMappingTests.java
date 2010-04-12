/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

/**
 *  EclipseLink2_0OrmOneToManyMappingTests
 */
@SuppressWarnings("nls")
public class EclipseLink2_0OrmOneToManyMappingTests
	extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmOneToManyMappingTests(String name) {
		super(name);
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
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEntityWithValidOneToManyMapping() throws Exception {
		this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
		
	private ICompilationUnit createTestEntityWithValidMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<String, Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidNonGenericMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private void createTestEmbeddableState() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("State").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    private String abbr;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}		
	
	private ICompilationUnit createTestEntityWithValidGenericMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
	
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<String, Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private OrmOrphanRemovable2_0 getOrphanRemovalOf(OneToManyMapping2_0 oneToManyMapping) {
		return ((OrmOrphanRemovalHolder2_0) oneToManyMapping).getOrphanRemoval();
	}
	
	public void testUpdateSpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 oneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		OrmOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals(null, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(null, oneToManyResource.getOrphanRemoval());
				
		//set enumerated in the resource model, verify context model updated
		oneToManyResource.setOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(Boolean.TRUE, oneToManyResource.getOrphanRemoval());
	
		oneToManyResource.setOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(Boolean.FALSE, oneToManyResource.getOrphanRemoval());
	}
	
	public void testModifySpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 oneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		OrmOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals(null, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(null, oneToManyResource.getOrphanRemoval());
				
		//set enumerated in the context model, verify resource model updated
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToManyResource.getOrphanRemoval());
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToManyResource.getOrphanRemoval());
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertTrue(ormOneToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setPkMapKey(true);
		assertEquals("id", ormOneToManyMapping.getMapKey());
		assertTrue(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertFalse(ormOneToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setCustomMapKey(true);
		javaOneToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("city", ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertTrue(ormOneToManyMapping.isCustomMapKey());
		assertFalse(ormOneToManyMapping.isNoMapKey());
		
		//set metadata complete and verify that the orm model ignores the java annotations
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(null, ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals(null, ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertTrue(ormOneToManyMapping.isNoMapKey());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = 
			ormOneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormOneToManyMapping.getPersistentAttribute().makeSpecified();
		ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		ormOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormOneToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 ormOneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKeyClass(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNotNull(oneToMany.getMapKeyClass());
				
		//set mapKey name in the resource model, verify context model updated
		oneToMany.getMapKeyClass().setClassName("String");
		assertEquals("String", ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", oneToMany.getMapKeyClass().getClassName());
		
		//set mapKey name to null in the resource model
		oneToMany.getMapKeyClass().setClassName(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass().getClassName());
		
		oneToMany.getMapKeyClass().setClassName("String");
		oneToMany.setMapKeyClass(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass());
	}
	
	public void testUpdateVirtualMapKeyClass() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmOneToManyMapping2_0 ormOneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("java.lang.Integer", ormOneToManyMapping.getMapKeyClass());
		assertEquals("java.lang.Integer", ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getDefaultMapKeyClass());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(null, ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 ormOneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass());
					
		//set mapKey  in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedMapKeyClass("String");
		assertEquals("String", ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", oneToMany.getMapKeyClass().getClassName());
	
		//set mapKey to null in the context model
		ormOneToManyMapping.setSpecifiedMapKeyClass(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass());
	}

	public void testOrderColumnDefaults() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "jobs");
		OrmOneToManyMapping oneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		oneToManyMapping.getRelationshipReference().setMappedByJoiningStrategy();
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("queue");

		Orderable2_0 orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		orderable.setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = orderable.getOrderColumn();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(null, orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTable());
		
		orderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTable());
		
		OrmPersistentType printJobPersistentType = (OrmPersistentType) getPersistenceUnit().getPersistentType("test.PrintJob");
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");

		assertEquals("MY_TABLE", orderColumn.getTable());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "jobs");
		OrmOneToManyMapping oneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaOneToManyMapping.getOrderable()).setOrderColumnOrdering(true);
				
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());

		ormPersistentAttribute.makeVirtual();		
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("jobs");
		oneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(false, orderable.isNoOrdering());
		assertEquals("PrintJob", orderable.getOrderColumn().getTable());
		assertEquals("jobs_ORDER", orderable.getOrderColumn().getName());
		
		((Orderable2_0) javaOneToManyMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals("PrintJob", orderable.getOrderColumn().getTable());
		assertEquals("FOO", orderable.getOrderColumn().getName());
	}
	
	
	private void createTestEntityPrintQueue() throws Exception {
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
					sb.append("import ");
					sb.append(JPA.ONE_TO_MANY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintQueue").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany(mappedBy=\"queue\")").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<PrintJob> jobs;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintQueue.java", sourceWriter);
	}
	
	private void createTestEntityPrintJob() throws Exception {
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
					sb.append("import ");
					sb.append(JPA.MANY_TO_ONE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintJob").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);
				sb.append("    private PrintQueue queue;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintJob.java", sourceWriter);
	}

	public void testVirtualMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//virtual attribute in orm.xml, java attribute has no value Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmOneToManyMapping2_0 addressesVirtualMapping = (OrmOneToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", ormColumn.getSpecifiedName());
		assertEquals(TYPE_NAME + "_Address", ormColumn.getSpecifiedTable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getSpecifiedLength().intValue());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getSpecifiedPrecision().intValue());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getSpecifiedScale().intValue());

		//set Column annotation in Java
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTable("FOO_TABLE");
		javaOneToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", ormColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEFINITION", ormColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Integer.valueOf(45), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(46), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(47), ormColumn.getSpecifiedScale());


		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmOneToManyMapping2_0 addressesMapping = (OrmOneToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		ormColumn = addressesMapping.getMapKeyColumn();
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());

		assertEquals("addresses_KEY", ormColumn.getSpecifiedName());
		assertEquals(TYPE_NAME + "_Address", ormColumn.getSpecifiedTable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getSpecifiedLength().intValue());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getSpecifiedPrecision().intValue());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getSpecifiedScale().intValue());
	}
	
	public void testNullMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "addresses");

		OrmOneToManyMapping2_0 addressesVirtualMapping = (OrmOneToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		//set Column annotation in Java
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTable("FOO_TABLE");
		javaOneToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));


		assertEquals("addresses_KEY", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_Address", ormColumn.getDefaultTable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
	}

	public void testVirtualMapKeyColumnTable() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmOneToManyMapping2_0 addressesVirtualMapping = (OrmOneToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		assertEquals(TYPE_NAME + "_Address", ormColumn.getSpecifiedTable());

		//entity table changes the join table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals("ORM_TABLE_Address", ormColumn.getSpecifiedTable());

		//set Column table element in Java
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("JAVA_JOIN_TABLE");
		assertEquals("JAVA_JOIN_TABLE", ormColumn.getSpecifiedTable());
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTable("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", ormColumn.getSpecifiedTable());

		//make name persistent attribute not virtual
		addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "addresses");
		addressesVirtualMapping = (OrmOneToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		ormColumn = addressesVirtualMapping.getMapKeyColumn();
		assertNull(ormColumn.getSpecifiedTable());
		assertEquals("ORM_TABLE_Address", ormColumn.getDefaultTable());
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("addresses");
		OrmEclipseLinkOneToManyMapping contextMapping = (OrmEclipseLinkOneToManyMapping) contextAttribute.getMapping();
		OrmEclipseLinkOneToManyRelationshipReference relationshipReference = contextMapping.getRelationshipReference();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		
		relationshipReference.setJoinColumnJoiningStrategy();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		
		relationshipReference.setMappedByJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		
		relationshipReference.setJoinTableJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("addresses");
		OrmEclipseLinkOneToManyMapping contextMapping = (OrmEclipseLinkOneToManyMapping) contextAttribute.getMapping();
		OrmEclipseLinkOneToManyRelationshipReference relationshipReference = contextMapping.getRelationshipReference();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.setJoinTable(null);
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		
		resourceMapping.getJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
	}

	public void testTargetForeignKeyJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OrmOneToManyMapping2_0 oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getSpecifiedMapping();
		javaOneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();

		JoinColumn joinColumn = oneToManyMapping.getRelationshipReference().getJoinColumnJoiningStrategy().specifiedJoinColumns().next();		
		assertTrue(persistentAttribute.isVirtual());
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("Address", joinColumn.getDefaultTable());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());

		//override the mapping in orm.xml
		persistentAttribute.makeSpecified();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		assertFalse(persistentAttribute.isVirtual());
		assertFalse(oneToManyMapping.getRelationshipReference().usesJoinColumnJoiningStrategy());

		oneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();
		joinColumn = oneToManyMapping.getRelationshipReference().getJoinColumnJoiningStrategy().specifiedJoinColumns().next();		
		assertFalse(persistentAttribute.isVirtual());
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());

		joinColumn.setSpecifiedName("FOO");
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());
	}

	//target foreign key case
	public void testGetMapKeyColumnJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OrmOneToManyMapping2_0 oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getSpecifiedMapping();
		javaOneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();

		assertTrue(persistentAttribute.isVirtual());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("Address", oneToManyMapping.getMapKeyColumn().getTable());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) attributeResource.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();

		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getDefaultTable());

		//override the mapping in orm.xml
		persistentAttribute.makeSpecified();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		assertFalse(persistentAttribute.isVirtual());
		assertFalse(oneToManyMapping.getRelationshipReference().usesJoinColumnJoiningStrategy());

		oneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());//target table name

		oneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
		assertEquals("FOO", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getDefaultTable());
	}

	//target foreign key case
	public void testOrderColumnDefaultsJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OrmOneToManyMapping2_0 oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOneToManyMapping2_0 javaOneToManyMapping = (JavaOneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getSpecifiedMapping();
		javaOneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();
		javaOneToManyMapping.getOrderable().setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = oneToManyMapping.getOrderable().getOrderColumn();

		assertEquals("addresses_ORDER", orderColumn.getSpecifiedName());
		assertEquals("Address", orderColumn.getTable());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", orderColumn.getTable());

		//override the mapping in orm.xml
		persistentAttribute.makeSpecified();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OrmOneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		assertFalse(persistentAttribute.isVirtual());
		assertFalse(oneToManyMapping.getRelationshipReference().usesJoinColumnJoiningStrategy());

		oneToManyMapping.getRelationshipReference().setJoinColumnJoiningStrategy();
		assertFalse(oneToManyMapping.getOrderable().isOrderColumnOrdering());
		oneToManyMapping.getOrderable().setOrderColumnOrdering(true);
		orderColumn = oneToManyMapping.getOrderable().getOrderColumn();

		assertNull(orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getName());
		assertEquals("ADDRESS_PRIMARY_TABLE", orderColumn.getTable());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("addresses_ORDER", orderColumn.getName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", orderColumn.getTable());//target table name
	}

}
