/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
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
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(oneToMany.getMapKeyClass());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKeyClass(OrmFactory.eINSTANCE.createXmlMapKeyClass());
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

		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals(null, ormOneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getMapKeyClass());
		assertEquals("java.lang.String", ormOneToManyMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", ormOneToManyMapping.getMapKeyClass());
		assertEquals("Integer", ormOneToManyMapping.getSpecifiedMapKeyClass());
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
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
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
}
