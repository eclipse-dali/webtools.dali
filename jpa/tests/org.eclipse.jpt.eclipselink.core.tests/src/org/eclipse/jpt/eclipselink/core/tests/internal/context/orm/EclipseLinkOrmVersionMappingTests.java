/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmVersionMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmVersionMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithVersionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableVersionDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION, EclipseLinkJPA.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	public void testUpdateMutable() throws Exception {
		createTestEntityWithVersionMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		EclipseLinkVersionMapping javaVersionMapping = (EclipseLinkVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceVersion.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceVersion.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceVersion.setMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
		
		// set mutable on java basic mapping
		
		javaVersionMapping.getMutable().setSpecifiedMutable(Boolean.FALSE);
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextVersion = (EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		EclipseLinkOrmBasicMapping contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceVersion.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableVersionDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "myDate");
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		EclipseLinkVersionMapping javaVersionMapping = (EclipseLinkVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("myDate").getMapping();
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceVersion.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceVersion.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceVersion.setMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.TRUE);
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.FALSE);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(null);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set mutable on java version mapping
		
		javaVersionMapping.getMutable().setSpecifiedMutable(Boolean.TRUE);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextVersion = (EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		EclipseLinkOrmBasicMapping contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceVersion.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "basic");
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithVersionMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion basicResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		JavaVersionMapping javaVersionMapping = (JavaVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormVersionMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		
		
		javaVersionMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) javaVersionMapping.getSpecifiedConverter()).setSpecifiedConverterName("foo");
		
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaVersionMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormVersionMapping.getSpecifiedConverter().getType());
		assertEquals("foo", ((Convert) ormVersionMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaVersionMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		((Convert) javaVersionMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormVersionMapping.getSpecifiedConverter().getType());
		assertEquals("bar", ((Convert) ormVersionMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((Convert) javaVersionMapping.getSpecifiedConverter()).getSpecifiedConverterName());

		javaVersionMapping.setSpecifiedConverter(Converter.NO_CONVERTER);
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals(null, javaVersionMapping.getSpecifiedConverter());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion basicResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
	
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormVersionMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		assertEquals("", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormVersionMapping.getConverter().getType());
	
		((Convert) ormVersionMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormVersionMapping.getConverter().getType());
		assertEquals("bar", ((Convert) ormVersionMapping.getConverter()).getSpecifiedConverterName());

		((Convert) ormVersionMapping.getSpecifiedConverter()).setSpecifiedConverterName(null);

		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormVersionMapping.setSpecifiedConverter(null);
		assertEquals(null, ormVersionMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
	}
}
