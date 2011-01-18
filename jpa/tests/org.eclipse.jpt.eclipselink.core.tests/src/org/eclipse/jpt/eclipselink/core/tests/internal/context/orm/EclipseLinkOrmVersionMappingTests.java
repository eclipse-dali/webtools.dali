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
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION, EclipseLink.MUTABLE, "java.util.Date");
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
			ormPersistentType.addSpecifiedAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		OrmEclipseLinkVersionMapping contextVersion = 
			(OrmEclipseLinkVersionMapping) ormPersistentAttribute.getMapping();
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
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkVersionMapping virtualContextVersion = (EclipseLinkVersionMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertTrue(virtualContextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, virtualContextVersion.getMutable().getSpecifiedMutable());
		assertFalse(virtualContextVersion.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkBasicMapping contextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceVersion.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableVersionDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "myDate");
		OrmEclipseLinkVersionMapping contextVersion = 
			(OrmEclipseLinkVersionMapping) ormPersistentAttribute.getMapping();
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
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkVersionMapping virtualContextVersion = (EclipseLinkVersionMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertFalse(virtualContextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, virtualContextVersion.getMutable().getSpecifiedMutable());
		assertTrue(virtualContextVersion.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkBasicMapping contextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceVersion.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "basic");
		OrmEclipseLinkVersionMapping contextVersion = 
			(OrmEclipseLinkVersionMapping) ormPersistentAttribute.getMapping();
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion basicResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		JavaVersionMapping javaVersionMapping = (JavaVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(ormVersionMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertNull(ormVersionMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		
		
		javaVersionMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) javaVersionMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertNull(ormVersionMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentAttribute.convertToVirtual();
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		VersionMapping virtualVersionMapping = (VersionMapping) ormPersistentAttribute2.getMapping();
		
		assertEquals(EclipseLinkConvert.class, virtualVersionMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) virtualVersionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaVersionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.class, virtualVersionMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) virtualVersionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());

		javaVersionMapping.setConverter(null);
		assertNull(virtualVersionMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
		assertNull(javaVersionMapping.getConverter().getType());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion basicResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
	
		assertNull(ormVersionMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormVersionMapping.setConverter(EclipseLinkConvert.class);
		assertEquals("", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getType());
	
		((EclipseLinkConvert) ormVersionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getType());
		assertEquals("bar", ((EclipseLinkConvert) ormVersionMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormVersionMapping.getConverter()).setSpecifiedConverterName(null);

		assertNull(ormVersionMapping.getConverter().getType());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormVersionMapping.setConverter(null);
		assertNull(ormVersionMapping.getConverter().getType());
		assertNull(basicResource.getConvert());
	}
}
