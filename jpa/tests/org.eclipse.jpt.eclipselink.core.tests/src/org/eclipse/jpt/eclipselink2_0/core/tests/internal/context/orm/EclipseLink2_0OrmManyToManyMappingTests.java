/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmManyToManyMappingTests
	extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmManyToManyMappingTests(String name) {
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
	
	private ICompilationUnit createTestEntityWithValidManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidMapManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private java.util.Map<String, Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidNonGenericMapManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
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
				sb.append("    private String foo;").append(CR);
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "addresses");

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = manyToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	public void testUpdateMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(manyToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		manyToMany.setMapKey(OrmFactory.eINSTANCE.createMapKey());
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNotNull(manyToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		manyToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormManyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		manyToMany.getMapKey().setName(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(manyToMany.getMapKey().getName());
		
		manyToMany.getMapKey().setName("myMapKey");
		manyToMany.setMapKey(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(manyToMany.getMapKey());
	}
	
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(ormManyToManyMapping.getMapKey());
		assertFalse(ormManyToManyMapping.isPkMapKey());
		assertFalse(ormManyToManyMapping.isCustomMapKey());
		assertTrue(ormManyToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setPkMapKey(true);
		assertEquals("id", ormManyToManyMapping.getMapKey());
		assertTrue(ormManyToManyMapping.isPkMapKey());
		assertFalse(ormManyToManyMapping.isCustomMapKey());
		assertFalse(ormManyToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setCustomMapKey(true);
		javaManyToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", ormManyToManyMapping.getSpecifiedMapKey());
		assertEquals("city", ormManyToManyMapping.getMapKey());
		assertFalse(ormManyToManyMapping.isPkMapKey());
		assertTrue(ormManyToManyMapping.isCustomMapKey());
		assertFalse(ormManyToManyMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(manyToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormManyToManyMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormManyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormManyToManyMapping.setSpecifiedMapKey(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(manyToMany.getMapKey());
	}

	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = 
			ormManyToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = ormManyToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaManyToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormManyToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormManyToManyMapping.getPersistentAttribute().makeSpecified();
		ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = ormManyToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		ormManyToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormManyToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormManyToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = ormManyToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass());
		
		//set mapKey in the resource model, verify context model does not change
		manyToMany.setMapKeyClass(OrmFactory.eINSTANCE.createXmlMapKeyClass());
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNotNull(manyToMany.getMapKeyClass());
				
		//set mapKey name in the resource model, verify context model updated
		manyToMany.getMapKeyClass().setClassName("String");
		assertEquals("String", ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", manyToMany.getMapKeyClass().getClassName());
		
		//set mapKey name to null in the resource model
		manyToMany.getMapKeyClass().setClassName(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass().getClassName());
		
		manyToMany.getMapKeyClass().setClassName("String");
		manyToMany.setMapKeyClass(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass());
	}
	
	public void testUpdateVirtualMapKeyClass() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", ormManyToManyMapping.getMapKeyClass());
		assertEquals(null, ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormManyToManyMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", ormManyToManyMapping.getMapKeyClass());
		assertEquals("Integer", ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormManyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass());
					
		//set mapKey  in the context model, verify resource model updated
		ormManyToManyMapping.setSpecifiedMapKeyClass("String");
		assertEquals("String", ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", manyToMany.getMapKeyClass().getClassName());
	
		//set mapKey to null in the context model
		ormManyToManyMapping.setSpecifiedMapKeyClass(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass());
	}

	public void testOrderColumnDefaults() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType printQueuePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		OrmPersistentAttribute jobsPersistentAttribute = printQueuePersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "jobs");
		OrmManyToManyMapping jobsMapping = (OrmManyToManyMapping) jobsPersistentAttribute.getMapping();
		jobsMapping.getRelationshipReference().setMappedByJoiningStrategy();
		jobsMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("queues");

		OrmPersistentType printJobPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmPersistentAttribute queuesPersistentAttribute = printJobPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "queues");
		OrmManyToManyMapping queuesMapping = (OrmManyToManyMapping) queuesPersistentAttribute.getMapping();

		getOrmXmlResource().save(null);
		Orderable2_0 jobsOrderable = ((Orderable2_0) jobsMapping.getOrderable());
		OrderColumn2_0 jobsOrderColumn = jobsOrderable.getOrderColumn();
		assertEquals(false, jobsOrderable.isOrderColumnOrdering());
		assertEquals(true, jobsOrderable.isNoOrdering());
		Orderable2_0 queuesOrderable = ((Orderable2_0) queuesMapping.getOrderable());
		OrderColumn2_0 queuesOrderColumn = queuesOrderable.getOrderColumn();
		assertEquals(false, queuesOrderable.isOrderColumnOrdering());
		assertEquals(true, queuesOrderable.isNoOrdering());

		
		jobsOrderable.setOrderColumnOrdering(true);
		jobsOrderColumn = jobsOrderable.getOrderColumn();
		assertEquals(true, jobsOrderable.isOrderColumnOrdering());
		assertEquals(null, jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTable());
		queuesOrderable.setOrderColumnOrdering(true);
		queuesOrderColumn = queuesOrderable.getOrderColumn();
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(null, queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTable());
		
		jobsOrderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTable());
		queuesOrderColumn.setSpecifiedName("BAR");
		assertEquals("BAR", queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTable());
		
		
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");
		assertEquals("MY_TABLE_PrintQueue", jobsOrderColumn.getTable());
		assertEquals("MY_TABLE_PrintQueue", queuesOrderColumn.getTable());
		
		((Entity) printQueuePersistentType.getMapping()).getTable().setSpecifiedName("OTHER_TABLE");
		assertEquals("MY_TABLE_OTHER_TABLE", jobsOrderColumn.getTable());
		assertEquals("MY_TABLE_OTHER_TABLE", queuesOrderColumn.getTable());
		
		queuesMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", jobsOrderColumn.getTable());
		assertEquals("MY_JOIN_TABLE", queuesOrderColumn.getTable());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType printQueuePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		OrmPersistentAttribute jobsPersistentAttribute = printQueuePersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "jobs");
		OrmManyToManyMapping jobsMapping = (OrmManyToManyMapping) jobsPersistentAttribute.getMapping();

		OrmPersistentType printJobPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmPersistentAttribute queuesPersistentAttribute = printJobPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "queues");
		OrmManyToManyMapping queuesMapping = (OrmManyToManyMapping) queuesPersistentAttribute.getMapping();

		Orderable2_0 jobsOrderable = ((Orderable2_0) jobsMapping.getOrderable());
		assertEquals(false, jobsOrderable.isOrderColumnOrdering());
		assertEquals(true, jobsOrderable.isNoOrdering());
		Orderable2_0 queuesOrderable = ((Orderable2_0) queuesMapping.getOrderable());
		assertEquals(false, queuesOrderable.isOrderColumnOrdering());
		assertEquals(true, queuesOrderable.isNoOrdering());
		
		JavaManyToManyMapping javaJobsManyToManyMapping = (JavaManyToManyMapping) jobsPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaJobsManyToManyMapping.getOrderable()).setOrderColumnOrdering(true);
				
		assertEquals(false, jobsOrderable.isOrderColumnOrdering());
		assertEquals(true, jobsOrderable.isNoOrdering());
		assertEquals(false, queuesOrderable.isOrderColumnOrdering());
		assertEquals(true, queuesOrderable.isNoOrdering());

		jobsPersistentAttribute.makeVirtual();		
		jobsPersistentAttribute = printQueuePersistentType.getAttributeNamed("jobs");
		jobsMapping = (OrmManyToManyMapping) jobsPersistentAttribute.getMapping();
		jobsOrderable = ((Orderable2_0) jobsMapping.getOrderable());
		assertEquals(true, jobsOrderable.isOrderColumnOrdering());
		assertEquals(false, jobsOrderable.isNoOrdering());
		assertEquals("PrintJob_PrintQueue", jobsOrderable.getOrderColumn().getTable());
		assertEquals("jobs_ORDER", jobsOrderable.getOrderColumn().getName());
		queuesPersistentAttribute.makeVirtual();		
		queuesPersistentAttribute = printJobPersistentType.getAttributeNamed("queues");
		queuesMapping = (OrmManyToManyMapping) queuesPersistentAttribute.getMapping();
		queuesOrderable = ((Orderable2_0) queuesMapping.getOrderable());
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(false, queuesOrderable.isNoOrdering());
		assertEquals("PrintJob_PrintQueue", queuesOrderable.getOrderColumn().getTable());
		assertEquals("queues_ORDER", queuesOrderable.getOrderColumn().getName());
		
		((Orderable2_0) javaJobsManyToManyMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals("PrintJob_PrintQueue", jobsOrderable.getOrderColumn().getTable());
		assertEquals("FOO", jobsOrderable.getOrderColumn().getName());
		assertEquals("PrintJob_PrintQueue", queuesOrderable.getOrderColumn().getTable());
		assertEquals("queues_ORDER", queuesOrderable.getOrderColumn().getName());
		
		JavaManyToManyMapping javaQueuesManyToManyMapping = (JavaManyToManyMapping) queuesPersistentAttribute.getJavaPersistentAttribute().getMapping();
		javaQueuesManyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("JOIN_TABLE");
		assertEquals("JOIN_TABLE", jobsOrderable.getOrderColumn().getTable());
		assertEquals("FOO", jobsOrderable.getOrderColumn().getName());
		assertEquals("JOIN_TABLE", queuesOrderable.getOrderColumn().getTable());
		assertEquals("queues_ORDER", queuesOrderable.getOrderColumn().getName());
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
					sb.append(JPA.MANY_TO_MANY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintQueue").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany(mappedBy=\"queues\")").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<test.PrintJob> jobs;").append(CR);
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
					sb.append(JPA.MANY_TO_MANY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
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
				sb.append("    @ManyToMany").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<test.PrintQueue> queues;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintJob.java", sourceWriter);
	}
}
