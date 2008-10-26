/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
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
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmBasicMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createMutableAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Mutable", "boolean value() default true");		
	}
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		createConvertAnnotation();
		createMutableAnnotation();
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

	
	public EclipseLinkOrmBasicMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateMutable() throws Exception {
		createTestEntityWithBasicMapping();
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		EclipseLinkOrmBasicMapping contextBasic = 
			(EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
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
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceBasic.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaBasicMapping.getMutable().isMutable());
	}
	
	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		EclipseLinkOrmBasicMapping contextBasic = 
			(EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
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
		
		// set context read only to null, check resource
		
		contextBasic.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceBasic.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		
		
		javaBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) javaBasicMapping.getSpecifiedConverter()).setSpecifiedConverterName("foo");
		
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaBasicMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getSpecifiedConverter().getType());
		assertEquals("foo", ((Convert) ormBasicMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaBasicMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		((Convert) javaBasicMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getSpecifiedConverter().getType());
		assertEquals("bar", ((Convert) ormBasicMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((Convert) javaBasicMapping.getSpecifiedConverter()).getSpecifiedConverterName());

		javaBasicMapping.setSpecifiedConverter(Converter.NO_CONVERTER);
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals(null, javaBasicMapping.getSpecifiedConverter());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = (XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
	
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		assertEquals("", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
	
		((Convert) ormBasicMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", ((Convert) ormBasicMapping.getConverter()).getSpecifiedConverterName());

		((Convert) ormBasicMapping.getSpecifiedConverter()).setSpecifiedConverterName(null);

		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormBasicMapping.setSpecifiedConverter(null);
		assertEquals(null, ormBasicMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
	}
}
