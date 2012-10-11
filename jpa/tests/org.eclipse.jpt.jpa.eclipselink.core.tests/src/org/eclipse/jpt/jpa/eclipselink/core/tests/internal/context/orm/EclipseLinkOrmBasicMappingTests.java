/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmBasicMappingTests
	extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableBasicDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, EclipseLink.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	public EclipseLinkOrmBasicMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateMutable() throws Exception {
		createTestEntityWithBasicMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping contextBasic = 
			(OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlBasic resourceBasic = 
			(XmlBasic) resourceEntity.getAttributes().getBasics().get(0);
		EclipseLinkBasicMapping javaBasicMapping = (EclipseLinkBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		// check defaults
		
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceBasic.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceBasic.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceBasic.setMutable(null);
		
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
		
		// set mutable on java basic mapping
		
		javaBasicMapping.getMutable().setSpecifiedMutable(Boolean.FALSE);
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentAttribute.removeFromXml();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkBasicMapping virtualContextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceBasic.getMutable());
		assertTrue(virtualContextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, virtualContextBasic.getMutable().getSpecifiedMutable());
		assertFalse(virtualContextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		virtualContextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceBasic.getMutable());
		assertTrue(virtualContextBasic.getMutable().isDefaultMutable());
		assertNull(virtualContextBasic.getMutable().getSpecifiedMutable());
		assertTrue(virtualContextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableBasicDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myDate"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping contextBasic = 
			(OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlBasic resourceBasic = 
			(XmlBasic) resourceEntity.getAttributes().getBasics().get(0);
		EclipseLinkBasicMapping javaBasicMapping = (EclipseLinkBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("myDate").getMapping();
		
		// check defaults
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceBasic.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceBasic.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceBasic.setMutable(null);
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.TRUE);
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.FALSE);
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(null);
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set mutable on java basic mapping
		
		javaBasicMapping.getMutable().setSpecifiedMutable(Boolean.TRUE);
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaBasicMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentAttribute.removeFromXml();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkBasicMapping virtualContextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceBasic.getMutable());
		assertFalse(virtualContextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, virtualContextBasic.getMutable().getSpecifiedMutable());
		assertTrue(virtualContextBasic.getMutable().isMutable());
		assertTrue(javaBasicMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		virtualContextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceBasic.getMutable());
		assertFalse(virtualContextBasic.getMutable().isDefaultMutable());
		assertNull(virtualContextBasic.getMutable().getSpecifiedMutable());
		assertFalse(virtualContextBasic.getMutable().isMutable());
		assertTrue(javaBasicMapping.getMutable().isMutable());
	}

	public void testModifyMutable() throws Exception {
		createTestEntityWithMutableBasicDate();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myDate"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping contextBasic = 
			(OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlBasic resourceBasic = 
			(XmlBasic) resourceEntity.getAttributes().getBasics().get(0);
		
		// check defaults
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextBasic.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextBasic.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		
		// set context mutable to null, check resource
		
		contextBasic.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(EclipseLinkConvert.class, ormBasicMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		
		
		javaBasicMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) javaBasicMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentAttribute.removeFromXml();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkBasicMapping virtualBasicMapping = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		
		assertEquals(EclipseLinkConvert.class, virtualBasicMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) virtualBasicMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaBasicMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.class, virtualBasicMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) virtualBasicMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());

		javaBasicMapping.setConverter(null);
		assertNull(virtualBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertNull(javaBasicMapping.getConverter().getType());
	}
	
	public void testModifyConvert() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
	
		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormBasicMapping.setConverter(EclipseLinkConvert.class);
		assertEquals("none", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormBasicMapping.getConverter().getType());
	
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormBasicMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) ormBasicMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormBasicMapping.getConverter()).setSpecifiedConverterName(null);

		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormBasicMapping.setConverter(null);
		assertNull(ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
	}
}
