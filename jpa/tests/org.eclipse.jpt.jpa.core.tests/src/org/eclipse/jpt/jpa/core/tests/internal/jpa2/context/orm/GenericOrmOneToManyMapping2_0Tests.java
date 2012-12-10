/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOneToMany_2_0;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmOneToManyMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericOrmOneToManyMapping2_0Tests(String name) {
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
		for (OrmReadOnlyPersistentAttribute each : ormPersistentType.getAttributes()) {
			each.addToXml();
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

	private ICompilationUnit createTestEntityOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, JPA.ORDER_BY, JPA2_0.MAP_KEY_ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany(fetch=FetchType.EAGER, targetEntity=Address.class, orphanRemoval = true, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})");
				sb.append(CR);
				sb.append("    @OrderBy(\"city\"");
				sb.append(CR);
				sb.append("    @MapKeyEnumerated(EnumType.STRING)").append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.name", attributeNames.next());
		assertEquals("state.abbr", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.name", attributeNames.next());
		assertEquals("state.abbr", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.name");
		assertEquals("name", stateFooMapping.getName());
	}

	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.name", attributeNames.next());
		assertEquals("state.abbr", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.name", attributeNames.next());
		assertEquals("state.abbr", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.name");
		assertEquals("name", stateFooMapping.getName());
	}

	private OrphanRemovable2_0 getOrphanRemovalOf(OneToManyMapping2_0 oneToManyMapping) {
		return ((OrphanRemovalHolder2_0) oneToManyMapping).getOrphanRemoval();
	}
	
	public void testUpdateSpecifiedOrphanRemoval() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(ormOneToManyMapping);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(ormOneToManyMapping);
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
	
	public void testUpdateMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKey(OrmFactory.eINSTANCE.createMapKey());
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNotNull(oneToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		oneToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		oneToMany.getMapKey().setName(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey().getName());
		
		oneToMany.getMapKey().setName("myMapKey");
		oneToMany.setMapKey(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
	}
	
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToManyMapping.getMapKey());
		assertFalse(oneToManyMapping.isPkMapKey());
		assertFalse(oneToManyMapping.isCustomMapKey());
		assertTrue(oneToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setPkMapKey(true);
		assertEquals("id", oneToManyMapping.getMapKey());
		assertTrue(oneToManyMapping.isPkMapKey());
		assertFalse(oneToManyMapping.isCustomMapKey());
		assertFalse(oneToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setCustomMapKey(true);
		javaOneToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", oneToManyMapping.getSpecifiedMapKey());
		assertEquals("city", oneToManyMapping.getMapKey());
		assertFalse(oneToManyMapping.isPkMapKey());
		assertTrue(oneToManyMapping.isCustomMapKey());
		assertFalse(oneToManyMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormOneToManyMapping.setSpecifiedMapKey(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey().getName());
	}

	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
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
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormPersistentType.getAttributeNamed("addresses").addToXml();
		oneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = oneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
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

		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", oneToManyMapping.getMapKeyClass());
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", oneToManyMapping.getDefaultMapKeyClass());

		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", oneToManyMapping.getMapKeyClass());
		assertEquals("Integer", oneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", oneToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("jobs"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping oneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		oneToManyMapping.getRelationship().setStrategyToMappedBy();
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("queue");

		Orderable2_0 orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		orderable.setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = orderable.getOrderColumn();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(null, orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTableName());
		
		orderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTableName());
		
		OrmPersistentType printJobPersistentType = (OrmPersistentType) getPersistenceUnit().getPersistentType("test.PrintJob");
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");

		assertEquals("MY_TABLE", orderColumn.getTableName());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintQueue");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".PrintJob");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("jobs"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaOneToManyMapping.getOrderable()).setOrderColumnOrdering(true);
				
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());

		ormPersistentAttribute.removeFromXml();		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("jobs");
		oneToManyMapping = (OneToManyMapping) ormPersistentAttribute2.getMapping();
		orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(false, orderable.isNoOrdering());
		assertEquals("PrintJob", orderable.getOrderColumn().getTableName());
		assertEquals("jobs_ORDER", orderable.getOrderColumn().getName());
		
		((Orderable2_0) javaOneToManyMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals("PrintJob", orderable.getOrderColumn().getTableName());
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
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OneToManyMapping2_0 virtualAddressesMapping = (OneToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		Column virtualColumn = virtualAddressesMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", virtualColumn.getName());
		assertEquals(TYPE_NAME + "_Address", virtualColumn.getTableName());
		assertNull(virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, virtualColumn.getScale());

		//set Column annotation in Java
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaOneToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", virtualColumn.getName());
		assertEquals("FOO_TABLE", virtualColumn.getTableName());
		assertEquals("COLUMN_DEFINITION", virtualColumn.getColumnDefinition());
		assertFalse(virtualColumn.isInsertable());
		assertFalse(virtualColumn.isUpdatable());
		assertFalse(virtualColumn.isNullable());
		assertTrue(virtualColumn.isUnique());
		assertEquals(45, virtualColumn.getLength());
		assertEquals(46, virtualColumn.getPrecision());
		assertEquals(47, virtualColumn.getScale());


		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		OneToManyMapping2_0 addressesVirtualMapping = (OneToManyMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		//set Column annotation in Java
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaOneToManyMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));


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
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		OneToManyMapping2_0 virtualAddressesMapping = (OneToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		Column virtualColumn = virtualAddressesMapping.getMapKeyColumn();

		assertEquals(TYPE_NAME + "_Address", virtualColumn.getTableName());
		assertNull(virtualColumn.getSpecifiedTableName());

		//entity table changes the join table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals("ORM_TABLE_Address", virtualColumn.getTableName());

		//set Column table element in Java
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaOneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("JAVA_JOIN_TABLE");
		assertEquals("JAVA_JOIN_TABLE", virtualColumn.getTableName());
		javaOneToManyMapping.getMapKeyColumn().setSpecifiedTableName("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", virtualColumn.getTableName());

		//make name persistent attribute not default
		addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 specifiedAddressesMapping = (OneToManyMapping2_0) addressesPersistentAttribute.getMapping();	
		Column specifiedColumn = specifiedAddressesMapping.getMapKeyColumn();
		assertNull(specifiedColumn.getSpecifiedTableName());
		assertEquals("ORM_TABLE_Address", specifiedColumn.getDefaultTableName());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("addresses");
		OneToManyMapping2_0 contextMapping = (OneToManyMapping2_0) contextAttribute.getMapping();
		OrmOneToManyRelationship2_0 rel = (OrmOneToManyRelationship2_0) contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinColumn();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToMappedBy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinTable();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("addresses");
		OneToManyMapping2_0 contextMapping = (OneToManyMapping2_0) contextAttribute.getMapping();
		OrmOneToManyRelationship2_0 rel = (OrmOneToManyRelationship2_0) contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.setJoinTable(null);
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.getJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}

	public void testTargetForeignKeyJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getMapping();
		OneToManyRelationship2_0 rr = (OneToManyRelationship2_0) javaOneToManyMapping.getRelationship();
		rr.setStrategyToJoinColumn();

		JoinColumn joinColumn = rr.getJoinColumnStrategy().getJoinColumns().iterator().next();		
		assertTrue(persistentAttribute.isVirtual());
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("Address", joinColumn.getDefaultTableName());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTableName());

		//override the mapping in orm.xml
		persistentAttribute.addToXml();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertFalse(persistentAttribute.isVirtual());
		rr = (OneToManyRelationship2_0) oneToManyMapping.getRelationship();
		assertFalse(rr.strategyIsJoinColumn());

		rr.setStrategyToJoinColumn();
		joinColumn = rr.getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().next();		
		assertFalse(persistentAttribute.isVirtual());
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTableName());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTableName());

		joinColumn.setSpecifiedName("FOO");
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTableName());
	}

	//target foreign key case
	public void testGetMapKeyColumnJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getMapping();
		OneToManyRelationship2_0 rr = (OneToManyRelationship2_0) javaOneToManyMapping.getRelationship();
		rr.setStrategyToJoinColumn();

		assertTrue(persistentAttribute.isVirtual());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertNull(oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("Address", oneToManyMapping.getMapKeyColumn().getTableName());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTableName());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();

		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getDefaultTableName());

		//override the mapping in orm.xml
		persistentAttribute.addToXml();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertFalse(persistentAttribute.isVirtual());
		rr = (OneToManyRelationship2_0) oneToManyMapping.getRelationship();
		assertFalse(rr.strategyIsJoinColumn());

		rr.setStrategyToJoinColumn();
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTableName());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTableName());//target table name

		oneToManyMapping.getMapKeyColumn().setSpecifiedName("FOO");
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
		assertEquals("FOO", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getDefaultTableName());
	}

	//target foreign key case
	public void testOrderColumnDefaultsJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormTargetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		//test virtual orm mapping, setting the join column on the java mapping
		OrmReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OneToManyMapping2_0 javaOneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getJavaPersistentAttribute().getMapping();
		OneToManyRelationship2_0 rr = (OneToManyRelationship2_0) javaOneToManyMapping.getRelationship();
		rr.setStrategyToJoinColumn();
		((Orderable2_0) javaOneToManyMapping.getOrderable()).setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = ((Orderable2_0) oneToManyMapping.getOrderable()).getOrderColumn();

		assertNull(orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getDefaultName());
		assertEquals("Address", orderColumn.getTableName());//target table name

		JavaEntity addressEntity = (JavaEntity) ormTargetPersistentType.getJavaPersistentType().getMapping();
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", orderColumn.getTableName());

		//override the mapping in orm.xml
		persistentAttribute.addToXml();
		persistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertFalse(persistentAttribute.isVirtual());
		rr = (OneToManyRelationship2_0) oneToManyMapping.getRelationship();
		assertFalse(rr.strategyIsJoinColumn());

		rr.setStrategyToJoinColumn();
		assertFalse(((Orderable2_0) oneToManyMapping.getOrderable()).isOrderColumnOrdering());
		((Orderable2_0) oneToManyMapping.getOrderable()).setOrderColumnOrdering(true);
		orderColumn = ((Orderable2_0) oneToManyMapping.getOrderable()).getOrderColumn();

		assertNull(orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getName());
		assertEquals("ADDRESS_PRIMARY_TABLE", orderColumn.getTableName());//target table name

		OrmEntity ormAddressEntity = (OrmEntity) ormTargetPersistentType.getMapping();
		ormAddressEntity.getTable().setSpecifiedName("ORM_ADDRESS_PRIMARY_TABLE");
		assertEquals("addresses_ORDER", orderColumn.getName());
		assertEquals("ORM_ADDRESS_PRIMARY_TABLE", orderColumn.getTableName());//target table name
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();

		OneToManyMapping2_0 virtualOneToManyMapping = (OneToManyMapping2_0) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualOneToManyMapping.getName());
		assertEquals(FetchType.EAGER, virtualOneToManyMapping.getSpecifiedFetch());
		assertEquals("Address", virtualOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(virtualOneToManyMapping.getRelationship().
			getMappedByStrategy().getMappedByAttribute());
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) virtualOneToManyMapping.getMapKeyConverter()).getEnumType());

		Cascade2_0 cascade = (Cascade2_0) virtualOneToManyMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
		assertTrue(cascade.isDetach());

		assertTrue(virtualOneToManyMapping.getOrderable().isCustomOrdering());
		assertEquals("city", virtualOneToManyMapping.getOrderable().getSpecifiedOrderBy());

		assertTrue(((OrphanRemovalHolder2_0) virtualOneToManyMapping).getOrphanRemoval().isOrphanRemoval());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());

		virtualPersistentAttribute.addToXml(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();

		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToManyMapping.getName());
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getFetch());
		assertEquals("test.Address", ormOneToManyMapping.getTargetEntity());
		assertNull(ormOneToManyMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertTrue(ormOneToManyMapping.getMapKeyConverter() instanceof NullOrmConverter);

		Cascade2_0 cascade = (Cascade2_0) ormOneToManyMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
		assertFalse(cascade.isDetach());

		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());
		assertEquals(null, ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());

		assertFalse(((OrphanRemovalHolder2_0) ormOneToManyMapping).getOrphanRemoval().isOrphanRemoval());
	}

	public void testUpdateSpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		oneToManyResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, oneToManyResource.getMapKeyEnumerated());
	
		oneToManyResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, oneToManyResource.getMapKeyEnumerated());

		//set enumerated to null in the resource model
		oneToManyResource.setMapKeyEnumerated(null);
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		ormOneToManyMapping.setMapKeyConverter(BaseEnumeratedConverter.class);
		((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, oneToManyResource.getMapKeyEnumerated());
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
	
		((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, oneToManyResource.getMapKeyEnumerated());
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormOneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());

		//set enumerated to null in the context model
		ormOneToManyMapping.setMapKeyConverter(null);
		assertNull(oneToManyResource.getMapKeyEnumerated());
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
	}
	
	public void testUpdateSpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyTemporal());
				
		//set temporal in the resource model, verify context model updated
		oneToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, oneToManyResource.getMapKeyTemporal());
	
		oneToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, oneToManyResource.getMapKeyTemporal());
		
		oneToManyResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, oneToManyResource.getMapKeyTemporal());

		//set temporal to null in the resource model
		oneToManyResource.setMapKeyTemporal(null);
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyTemporal());
	}
	
	public void testModifySpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
		assertNull(oneToManyResource.getMapKeyTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormOneToManyMapping.setMapKeyConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, oneToManyResource.getMapKeyTemporal());
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).getTemporalType());
	
		((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, oneToManyResource.getMapKeyTemporal());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormOneToManyMapping.getMapKeyConverter()).getTemporalType());

		//set temporal to null in the context model
		ormOneToManyMapping.setMapKeyConverter(null);
		assertNull(oneToManyResource.getMapKeyTemporal());
		assertNull(ormOneToManyMapping.getMapKeyConverter().getType());
	}
	
	public void testAddSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		JoinColumn joinColumn = ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		
		JoinColumn joinColumn2 = ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("FOO", oneToManyResource.getMapKeyJoinColumns().get(1).getName());
		
		JoinColumn joinColumn3 = ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToManyResource.getMapKeyJoinColumns().get(2).getName());
		
		ListIterator<? extends JoinColumn> joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);

		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToManyResource.getMapKeyJoinColumns().size());
		
		ormOneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(2, oneToManyResource.getMapKeyJoinColumns().size());
		assertEquals("BAR", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToManyResource.getMapKeyJoinColumns().get(1).getName());

		ormOneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, oneToManyResource.getMapKeyJoinColumns().size());
		assertEquals("BAZ", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		
		ormOneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(0, oneToManyResource.getMapKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);

		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormOneToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToManyResource.getMapKeyJoinColumns().size());
		
		
		ormOneToManyMapping.moveSpecifiedMapKeyJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToManyResource.getMapKeyJoinColumns().get(2).getName());


		ormOneToManyMapping.moveSpecifiedMapKeyJoinColumn(0, 1);
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", oneToManyResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAR", oneToManyResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToManyResource.getMapKeyJoinColumns().get(2).getName());
	}

	public void testUpdateMapKeyJoinColumns() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OneToManyMapping2_0 ormOneToManyMapping = (OneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany_2_0 oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
	
		oneToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		oneToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		oneToManyResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		oneToManyResource.getMapKeyJoinColumns().get(0).setName("FOO");
		oneToManyResource.getMapKeyJoinColumns().get(1).setName("BAR");
		oneToManyResource.getMapKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<? extends JoinColumn> joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		oneToManyResource.getMapKeyJoinColumns().move(2, 0);
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		oneToManyResource.getMapKeyJoinColumns().move(0, 1);
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		oneToManyResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		oneToManyResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		oneToManyResource.getMapKeyJoinColumns().remove(0);
		assertFalse(ormOneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator().hasNext());
	}
}
