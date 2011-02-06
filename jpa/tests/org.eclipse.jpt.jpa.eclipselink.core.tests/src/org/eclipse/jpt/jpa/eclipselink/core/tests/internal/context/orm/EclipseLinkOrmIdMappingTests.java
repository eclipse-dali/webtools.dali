/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId;

@SuppressWarnings("nls")
public class EclipseLinkOrmIdMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmIdMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableIdDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLink.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	public void testUpdateMutable() throws Exception {
		createTestEntityWithIdMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		OrmEclipseLinkIdMapping contextId = 
			(OrmEclipseLinkIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		EclipseLinkIdMapping javaIdMapping = (EclipseLinkIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceId.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceId.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceId.setMutable(null);
		
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
		
		// set mutable on java basic mapping
		
		javaIdMapping.getMutable().setSpecifiedMutable(Boolean.FALSE);
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkIdMapping virtualIdMapping = (EclipseLinkIdMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceId.getMutable());
		assertTrue(virtualIdMapping.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, virtualIdMapping.getMutable().getSpecifiedMutable());
		assertFalse(virtualIdMapping.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkBasicMapping virtualBasicMapping = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceId.getMutable());
		assertTrue(virtualBasicMapping.getMutable().isDefaultMutable());
		assertNull(virtualBasicMapping.getMutable().getSpecifiedMutable());
		assertTrue(virtualBasicMapping.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableIdDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "myDate");
		OrmEclipseLinkIdMapping contextId = 
			(OrmEclipseLinkIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		EclipseLinkIdMapping javaIdMapping = (EclipseLinkIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("myDate").getMapping();
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceId.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceId.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceId.setMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.TRUE);
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.FALSE);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(null);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set mutable on java id mapping
		
		javaIdMapping.getMutable().setSpecifiedMutable(Boolean.TRUE);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkIdMapping virtualIdMapping = (EclipseLinkIdMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceId.getMutable());
		assertFalse(virtualIdMapping.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, virtualIdMapping.getMutable().getSpecifiedMutable());
		assertTrue(virtualIdMapping.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkBasicMapping virtualBasicMapping = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceId.getMutable());
		assertFalse(virtualBasicMapping.getMutable().isDefaultMutable());
		assertNull(virtualBasicMapping.getMutable().getSpecifiedMutable());
		assertFalse(virtualBasicMapping.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
	}

	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "basic");
		OrmEclipseLinkIdMapping contextId = 
			(OrmEclipseLinkIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextId.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithIdMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId basicResource = (XmlId) getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		JavaIdMapping javaIdMapping = (JavaIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(ormIdMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(EclipseLinkConvert.class, ormIdMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertNull(ormIdMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		
		
		javaIdMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) javaIdMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertNull(ormIdMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaIdMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		IdMapping virtualIdMapping = (IdMapping) ormPersistentAttribute2.getMapping();
		
		assertEquals(EclipseLinkConvert.class, virtualIdMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) virtualIdMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaIdMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaIdMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.class, virtualIdMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) virtualIdMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaIdMapping.getConverter()).getSpecifiedConverterName());

		javaIdMapping.setConverter(null);
		assertNull(virtualIdMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
		assertNull(javaIdMapping.getConverter().getType());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId basicResource = (XmlId) getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
	
		assertNull(ormIdMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormIdMapping.setConverter(EclipseLinkConvert.class);
		assertEquals("", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormIdMapping.getConverter().getType());
	
		((EclipseLinkConvert) ormIdMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormIdMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) ormIdMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormIdMapping.getConverter()).setSpecifiedConverterName(null);

		assertNull(ormIdMapping.getConverter().getType());
		assertNull(basicResource.getConvert());

		//set lob to false in the context model
		ormIdMapping.setConverter(null);
		assertNull(ormIdMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
	}
}
