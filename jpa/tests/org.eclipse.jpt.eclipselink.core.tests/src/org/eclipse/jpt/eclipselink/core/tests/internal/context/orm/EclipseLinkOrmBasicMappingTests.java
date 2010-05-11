/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmBasicMappingTests
	extends EclipseLinkOrmContextModelTestCase
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
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
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
		
		ormPersistentType.removeSpecifiedAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		contextBasic = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		contextBasic = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableBasicDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "myDate");
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
		
		ormPersistentType.removeSpecifiedAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("myDate");
		contextBasic = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertTrue(javaBasicMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("myDate");
		contextBasic = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaBasicMapping.getMutable().isMutable());
	}

	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		
		
		javaBasicMapping.setConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) javaBasicMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentType.removeSpecifiedAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) ormBasicMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaBasicMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) ormBasicMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaBasicMapping.getConverter()).getSpecifiedConverterName());

		javaBasicMapping.setConverter(Converter.NO_CONVERTER);
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertEquals(Converter.NO_CONVERTER, javaBasicMapping.getConverter().getType());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
	
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormBasicMapping.setConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		assertEquals("", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
	
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) ormBasicMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormBasicMapping.getConverter()).setSpecifiedConverterName(null);

		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormBasicMapping.setConverter(Converter.NO_CONVERTER);
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
	}
}
