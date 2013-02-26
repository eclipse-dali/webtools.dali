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
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlManyToMany_2_0;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmManyToManyMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericOrmManyToManyMapping2_0Tests(String name) {
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

	private void createTestTargetEntityAddressWithElementCollection() throws Exception {
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
					sb.append(JPA2_0.ELEMENT_COLLECTION);
					sb.append(";");
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
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<State> state;").append(CR);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
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
	
	private ICompilationUnit createTestEntityManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, JPA.ORDER_BY, JPA2_0.MAP_KEY_ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany(fetch=FetchType.EAGER, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})");
				sb.append(CR);
				sb.append("    @OrderBy(\"city\"");
				sb.append(CR);
				sb.append("    @MapKeyEnumerated(EnumType.STRING)").append(CR);
				sb.append("    private java.util.Map<String, Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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

	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
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

		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(virtualManyToManyMapping.getSpecifiedMapKey());
		assertNull(virtualManyToManyMapping.getMapKey());
		assertFalse(virtualManyToManyMapping.isPkMapKey());
		assertFalse(virtualManyToManyMapping.isCustomMapKey());
		assertTrue(virtualManyToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setPkMapKey(true);
		assertEquals("id", virtualManyToManyMapping.getMapKey());
		assertTrue(virtualManyToManyMapping.isPkMapKey());
		assertFalse(virtualManyToManyMapping.isCustomMapKey());
		assertFalse(virtualManyToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setCustomMapKey(true);
		javaManyToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", virtualManyToManyMapping.getSpecifiedMapKey());
		assertEquals("city", virtualManyToManyMapping.getMapKey());
		assertFalse(virtualManyToManyMapping.isPkMapKey());
		assertTrue(virtualManyToManyMapping.isCustomMapKey());
		assertFalse(virtualManyToManyMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany xmlManyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(xmlManyToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormManyToManyMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormManyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", xmlManyToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormManyToManyMapping.setSpecifiedMapKey(null);
		assertNull(ormManyToManyMapping.getSpecifiedMapKey());
		assertNull(xmlManyToMany.getMapKey().getName());
	}

	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		
		Iterator<String> mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
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
		
		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaManyToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormPersistentType.getAttributeNamed("addresses").addToXml();
		virtualManyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		virtualManyToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		virtualManyToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = virtualManyToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(manyToMany.getMapKeyClass());
		
		//set mapKey in the resource model, verify context model does not change
		manyToMany.setMapKeyClass(OrmFactory.eINSTANCE.createXmlClassReference());
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

		ManyToManyMapping2_0 virtualManyToManyMapping = (ManyToManyMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaCollectionMapping2_0 javaManyToManyMapping = (JavaCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", virtualManyToManyMapping.getMapKeyClass());
		assertNull(virtualManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualManyToManyMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaManyToManyMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", virtualManyToManyMapping.getMapKeyClass());
		assertEquals("Integer", virtualManyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualManyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
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
		OrmModifiablePersistentAttribute jobsPersistentAttribute = printQueuePersistentType.addAttributeToXml(printQueuePersistentType.getAttributeNamed("jobs"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping jobsMapping = (OrmManyToManyMapping) jobsPersistentAttribute.getMapping();
		jobsMapping.getRelationship().setStrategyToMappedBy();
		jobsMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("queues");

		OrmPersistentType printJobPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmModifiablePersistentAttribute queuesPersistentAttribute = printJobPersistentType.addAttributeToXml(printJobPersistentType.getAttributeNamed("queues"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping queuesMapping = (OrmManyToManyMapping) queuesPersistentAttribute.getMapping();

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
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTableName());
		queuesOrderable.setOrderColumnOrdering(true);
		queuesOrderColumn = queuesOrderable.getOrderColumn();
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(null, queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTableName());
		
		jobsOrderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTableName());
		queuesOrderColumn.setSpecifiedName("BAR");
		assertEquals("BAR", queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTableName());
		
		
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");
		assertEquals("MY_TABLE_PrintQueue", jobsOrderColumn.getTableName());
		assertEquals("MY_TABLE_PrintQueue", queuesOrderColumn.getTableName());
		
		((Entity) printQueuePersistentType.getMapping()).getTable().setSpecifiedName("OTHER_TABLE");
		assertEquals("MY_TABLE_OTHER_TABLE", jobsOrderColumn.getTableName());
		assertEquals("MY_TABLE_OTHER_TABLE", queuesOrderColumn.getTableName());
		
		queuesMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", jobsOrderColumn.getTableName());
		assertEquals("MY_JOIN_TABLE", queuesOrderColumn.getTableName());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType printQueuePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		OrmModifiablePersistentAttribute jobsPersistentAttribute = printQueuePersistentType.addAttributeToXml(printQueuePersistentType.getAttributeNamed("jobs"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping jobsMapping = (OrmManyToManyMapping) jobsPersistentAttribute.getMapping();

		OrmPersistentType printJobPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmModifiablePersistentAttribute queuesPersistentAttribute = printJobPersistentType.addAttributeToXml(printJobPersistentType.getAttributeNamed("queues"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
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

		jobsPersistentAttribute.removeFromXml();		
		OrmReadOnlyPersistentAttribute jobsPersistentAttribute2 = printQueuePersistentType.getAttributeNamed("jobs");
		ManyToManyMapping virtualJobsMapping = (ManyToManyMapping) jobsPersistentAttribute2.getMapping();
		jobsOrderable = ((Orderable2_0) virtualJobsMapping.getOrderable());
		assertEquals(true, jobsOrderable.isOrderColumnOrdering());
		assertEquals(false, jobsOrderable.isNoOrdering());
		assertEquals("PrintJob_PrintQueue", jobsOrderable.getOrderColumn().getTableName());
		assertEquals("jobs_ORDER", jobsOrderable.getOrderColumn().getName());
		queuesPersistentAttribute.removeFromXml();		
		OrmReadOnlyPersistentAttribute queuesPersistentAttribute2 = printJobPersistentType.getAttributeNamed("queues");
		ManyToManyMapping virtualQueuesMapping = (ManyToManyMapping) queuesPersistentAttribute2.getMapping();
		queuesOrderable = ((Orderable2_0) virtualQueuesMapping.getOrderable());
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(false, queuesOrderable.isNoOrdering());
		assertEquals("PrintJob_PrintQueue", queuesOrderable.getOrderColumn().getTableName());
		assertEquals("queues_ORDER", queuesOrderable.getOrderColumn().getName());
		
		((Orderable2_0) javaJobsManyToManyMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals("PrintJob_PrintQueue", jobsOrderable.getOrderColumn().getTableName());
		assertEquals("FOO", jobsOrderable.getOrderColumn().getName());
		assertEquals("PrintJob_PrintQueue", queuesOrderable.getOrderColumn().getTableName());
		assertEquals("queues_ORDER", queuesOrderable.getOrderColumn().getName());
		
		JavaManyToManyMapping javaQueuesManyToManyMapping = (JavaManyToManyMapping) queuesPersistentAttribute.getJavaPersistentAttribute().getMapping();
		javaQueuesManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("JOIN_TABLE");
		assertEquals("JOIN_TABLE", jobsOrderable.getOrderColumn().getTableName());
		assertEquals("FOO", jobsOrderable.getOrderColumn().getName());
		assertEquals("JOIN_TABLE", queuesOrderable.getOrderColumn().getTableName());
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

	public void testVirtualMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//virtual attribute in orm.xml, java attribute has no value Column annotation
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		ManyToManyMapping2_0 addressesVirtualMapping = (ManyToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", ormColumn.getName());
		assertEquals(TYPE_NAME + "_Address", ormColumn.getTableName());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertTrue(ormColumn.isInsertable());
		assertTrue(ormColumn.isUpdatable());
		assertTrue(ormColumn.isNullable());
		assertFalse(ormColumn.isUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, ormColumn.getLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, ormColumn.getPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, ormColumn.getScale());

		//set Column annotation in Java
		JavaCollectionMapping2_0 javaManyToManyMapping = (JavaCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaManyToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", ormColumn.getSpecifiedTableName());
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
		addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmModifiablePersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		OrmCollectionMapping2_0 addressesVirtualMapping = (OrmCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		//set Column annotation in Java
		JavaCollectionMapping2_0 javaManyToManyMapping = (JavaCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaManyToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));


		assertEquals("addresses_KEY", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_Address", ormColumn.getDefaultTableName());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTableName());
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
		createTestEntityWithValidMapManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ManyToManyMapping2_0 addressesVirtualMapping = (ManyToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		Column column = addressesVirtualMapping.getMapKeyColumn();

		assertEquals(TYPE_NAME + "_Address", column.getTableName());

		//entity table changes the join table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals("ORM_TABLE_Address", column.getTableName());

		//set Column table element in Java
		ManyToManyMapping2_0 javaManyToManyMapping = (ManyToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("JAVA_JOIN_TABLE");
		assertEquals("JAVA_JOIN_TABLE", column.getTableName());
		javaManyToManyMapping.getMapKeyColumn().setSpecifiedTableName("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", column.getTableName());

		//make name persistent attribute not default
		addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		addressesVirtualMapping = (ManyToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		column = addressesVirtualMapping.getMapKeyColumn();
		assertNull(column.getSpecifiedTableName());
		assertEquals("ORM_TABLE_Address", column.getDefaultTableName());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualAttribute = ormPersistentType.getDefaultAttributes().iterator().next();

		ManyToManyMapping2_0 virtualManyToManyMapping = (ManyToManyMapping2_0) virtualAttribute.getMapping();	
		assertEquals("address", virtualManyToManyMapping.getName());
		assertEquals(FetchType.EAGER, virtualManyToManyMapping.getSpecifiedFetch());
		assertEquals("Address", virtualManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(virtualManyToManyMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) virtualManyToManyMapping.getMapKeyConverter()).getEnumType());

		Cascade2_0 cascade = (Cascade2_0) virtualManyToManyMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
		assertTrue(cascade.isDetach());

		assertTrue(virtualManyToManyMapping.getOrderable().isCustomOrdering());
		assertEquals("city", virtualManyToManyMapping.getOrderable().getSpecifiedOrderBy());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());
		virtualPersistentAttribute.addToXml(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmReadOnlyPersistentAttribute ormPersistentAttribute= ormPersistentType.getSpecifiedAttributes().iterator().next();

		ManyToManyMapping2_0 ormManyToManyMapping = (ManyToManyMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormManyToManyMapping.getName());
		assertEquals(FetchType.LAZY, ormManyToManyMapping.getFetch());
		assertEquals("test.Address", ormManyToManyMapping.getTargetEntity());
		assertNull(ormManyToManyMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertTrue(ormManyToManyMapping.getMapKeyConverter() instanceof NullOrmConverter);

		Cascade2_0 cascade = (Cascade2_0) ormManyToManyMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
		assertFalse(cascade.isDetach());

		assertTrue(ormManyToManyMapping.getOrderable().isNoOrdering());
		assertEquals(null, ormManyToManyMapping.getOrderable().getSpecifiedOrderBy());
	}
	
	public void testUpdateSpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		manyToManyResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, manyToManyResource.getMapKeyEnumerated());
	
		manyToManyResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, manyToManyResource.getMapKeyEnumerated());

		//set enumerated to null in the resource model
		manyToManyResource.setMapKeyEnumerated(null);
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		ormManyToManyMapping.setMapKeyConverter(BaseEnumeratedConverter.class);
		((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, manyToManyResource.getMapKeyEnumerated());
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
	
		((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, manyToManyResource.getMapKeyEnumerated());
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormManyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());

		//set enumerated to null in the context model
		ormManyToManyMapping.setMapKeyConverter(null);
		assertNull(manyToManyResource.getMapKeyEnumerated());
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
	}
	
	public void testUpdateSpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyTemporal());
				
		//set temporal in the resource model, verify context model updated
		manyToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, manyToManyResource.getMapKeyTemporal());
	
		manyToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, manyToManyResource.getMapKeyTemporal());
		
		manyToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, manyToManyResource.getMapKeyTemporal());

		//set temporal to null in the resource model
		manyToManyResource.setMapKeyTemporal(null);
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyTemporal());
	}
	
	public void testModifySpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmCollectionMapping2_0 ormManyToManyMapping = (OrmCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
		assertNull(manyToManyResource.getMapKeyTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormManyToManyMapping.setMapKeyConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, manyToManyResource.getMapKeyTemporal());
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).getTemporalType());
	
		((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, manyToManyResource.getMapKeyTemporal());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormManyToManyMapping.getMapKeyConverter()).getTemporalType());

		//set temporal to null in the context model
		ormManyToManyMapping.setMapKeyConverter(null);
		assertNull(manyToManyResource.getMapKeyTemporal());
		assertNull(ormManyToManyMapping.getMapKeyConverter().getType());
	}
	
	public void testAddSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ManyToManyMapping2_0 ormManyToManyMapping = (ManyToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		JoinColumn joinColumn = ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		
		JoinColumn joinColumn2 = ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("FOO", manyToManyResource.getMapKeyJoinColumns().get(1).getName());
		
		JoinColumn joinColumn3 = ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", manyToManyResource.getMapKeyJoinColumns().get(2).getName());
		
		ListIterator<? extends JoinColumn> joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ManyToManyMapping2_0 ormManyToManyMapping = (ManyToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToManyResource.getMapKeyJoinColumns().size());
		
		ormManyToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(2, manyToManyResource.getMapKeyJoinColumns().size());
		assertEquals("BAR", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToManyResource.getMapKeyJoinColumns().get(1).getName());

		ormManyToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, manyToManyResource.getMapKeyJoinColumns().size());
		assertEquals("BAZ", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		
		ormManyToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(0, manyToManyResource.getMapKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ManyToManyMapping2_0 ormManyToManyMapping = (ManyToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormManyToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToManyResource.getMapKeyJoinColumns().size());
		
		
		ormManyToManyMapping.moveSpecifiedMapKeyJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", manyToManyResource.getMapKeyJoinColumns().get(2).getName());


		ormManyToManyMapping.moveSpecifiedMapKeyJoinColumn(0, 1);
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", manyToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAR", manyToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", manyToManyResource.getMapKeyJoinColumns().get(2).getName());
	}

	public void testUpdateMapKeyJoinColumns() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ManyToManyMapping2_0 ormManyToManyMapping = (ManyToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlManyToMany_2_0 manyToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
	
		manyToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		manyToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		manyToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		manyToManyResource.getMapKeyJoinColumns().get(0).setName("FOO");
		manyToManyResource.getMapKeyJoinColumns().get(1).setName("BAR");
		manyToManyResource.getMapKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<? extends JoinColumn> joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		manyToManyResource.getMapKeyJoinColumns().move(2, 0);
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		manyToManyResource.getMapKeyJoinColumns().move(0, 1);
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		manyToManyResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		manyToManyResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		manyToManyResource.getMapKeyJoinColumns().remove(0);
		assertFalse(ormManyToManyMapping.getSpecifiedMapKeyJoinColumns().iterator().hasNext());
	}

}
