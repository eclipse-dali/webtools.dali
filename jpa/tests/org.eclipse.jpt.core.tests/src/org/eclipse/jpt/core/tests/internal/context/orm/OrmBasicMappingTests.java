/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmBasicMappingTests extends ContextModelTestCase
{
	public OrmBasicMappingTests(String name) {
		super(name);
	}	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntityBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.FETCH_TYPE, JPA.COLUMN, JPA.LOB, JPA.TEMPORAL, JPA.TEMPORAL_TYPE, JPA.ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(fetch=FetchType.LAZY, optional=false)");
				sb.append(CR);
				sb.append("    @Column(name=\"MY_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\", length=5, precision=6, scale=7)");
				sb.append(CR);
				sb.append("    @Lob");
				sb.append(CR);
				sb.append("    @Temporal(TemporalType.TIMESTAMP)");
				sb.append(CR);
				sb.append("    @Enumerated(EnumType.STRING)");
			}
		});
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", ormBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the resource model, verify context model updated
		basicResource.setName("newName");
		assertEquals("newName", ormBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the resource model
		basicResource.setName(null);
		assertNull(ormBasicMapping.getName());
		assertNull(basicResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", ormBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the context model, verify resource model updated
		ormBasicMapping.setName("newName");
		assertEquals("newName", ormBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the context model
		ormBasicMapping.setName(null);
		assertNull(ormBasicMapping.getName());
		assertNull(basicResource.getName());
	}

	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		basicResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, basicResource.getFetch());
	
		basicResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, basicResource.getFetch());

		//set fetch to null in the resource model
		basicResource.setFetch(null);
		assertNull(ormBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, basicResource.getFetch());
		assertEquals(FetchType.EAGER, ormBasicMapping.getSpecifiedFetch());
	
		ormBasicMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, basicResource.getFetch());
		assertEquals(FetchType.LAZY, ormBasicMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormBasicMapping.setSpecifiedFetch(null);
		assertNull(basicResource.getFetch());
		assertNull(ormBasicMapping.getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedEnumerated() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setEnumerated(org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, ((EnumeratedConverter) ormBasicMapping.getConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
	
		basicResource.setEnumerated(org.eclipse.jpt.core.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, ((EnumeratedConverter) ormBasicMapping.getConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.core.resource.orm.EnumType.STRING, basicResource.getEnumerated());

		//set enumerated to null in the resource model
		basicResource.setEnumerated(null);
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedConverter(Converter.ENUMERATED_CONVERTER);
		((EnumeratedConverter) ormBasicMapping.getConverter()).setSpecifiedEnumType(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
		assertEquals(EnumType.ORDINAL, ((EnumeratedConverter) ormBasicMapping.getConverter()).getSpecifiedEnumType());
	
		((EnumeratedConverter) ormBasicMapping.getConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.core.resource.orm.EnumType.STRING, basicResource.getEnumerated());
		assertEquals(EnumType.STRING, ((EnumeratedConverter) ormBasicMapping.getConverter()).getSpecifiedEnumType());

		//set enumerated to null in the context model
		ormBasicMapping.setSpecifiedConverter(null);
		assertNull(basicResource.getEnumerated());
		assertNull(ormBasicMapping.getSpecifiedConverter());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, basicResource.getOptional());
	
		basicResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, basicResource.getOptional());

		//set enumerated to null in the resource model
		basicResource.setOptional(null);
		assertNull(ormBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicResource.getOptional());
		assertEquals(Boolean.TRUE, ormBasicMapping.getSpecifiedOptional());
	
		ormBasicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, basicResource.getOptional());
		assertEquals(Boolean.FALSE, ormBasicMapping.getSpecifiedOptional());

		//set enumerated to null in the context model
		ormBasicMapping.setSpecifiedOptional(null);
		assertNull(basicResource.getOptional());
		assertNull(ormBasicMapping.getSpecifiedOptional());
	}
	
	public void testUpdateSpecifiedLob() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertFalse(basicResource.isLob());
				
		//set lob in the resource model, verify context model updated
		basicResource.setLob(true);
		assertEquals(Converter.LOB_CONVERTER, ormBasicMapping.getConverter().getType());
		assertTrue(basicResource.isLob());

		//set lob to null in the resource model
		basicResource.setLob(false);
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertFalse(basicResource.isLob());
	}
	
	public void testModifySpecifiedLob() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
	
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertFalse(basicResource.isLob());
				
		//set lob in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedConverter(Converter.LOB_CONVERTER);
		assertTrue(basicResource.isLob());
		assertEquals(Converter.LOB_CONVERTER, ormBasicMapping.getConverter().getType());
	
		//set lob to false in the context model
		ormBasicMapping.setSpecifiedConverter(null);
		assertFalse(basicResource.isLob());
		assertNull(ormBasicMapping.getSpecifiedConverter());
	}
	
	public void testUpdateTemporal() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		basicResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((TemporalConverter) ormBasicMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, basicResource.getTemporal());
	
		basicResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((TemporalConverter) ormBasicMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, basicResource.getTemporal());

		basicResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) ormBasicMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());

		//set temporal to null in the resource model
		basicResource.setTemporal(null);
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		XmlBasic basicResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormBasicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, basicResource.getTemporal());
		assertEquals(TemporalType.DATE, ((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).getTemporalType());
	
		((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, basicResource.getTemporal());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).getTemporalType());

		((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIMESTAMP);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) ormBasicMapping.getSpecifiedConverter()).getTemporalType());

		//set temporal to null in the context model
		ormBasicMapping.setSpecifiedConverter(null);
		assertNull(basicResource.getTemporal());
		assertNull(ormBasicMapping.getSpecifiedConverter());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml


	public void testBasicMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityBasicMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", ormBasicMapping.getName());
		assertNull(ormBasicMapping.getSpecifiedConverter());
		assertNull(ormBasicMapping.getSpecifiedFetch());
		assertNull(ormBasicMapping.getSpecifiedOptional());
		assertEquals(FetchType.EAGER, ormBasicMapping.getFetch());
		assertEquals(true, ormBasicMapping.isOptional());

		
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("foo", ormColumn.getDefaultName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(255, ormColumn.getDefaultLength());
		assertEquals(0, ormColumn.getDefaultPrecision());
		assertEquals(0, ormColumn.getDefaultScale());
	}
	
	//@Basic(fetch=FetchType.LAZY, optional=false)
	//@Column(name="MY_COLUMN", unique=true, nullable=false, insertable=false, updatable=false, 
	//    columnDefinition="COLUMN_DEFINITION", table="MY_TABLE", length=5, precision=6, scale=7)");
	//@Column(
	//@Lob
	//@Temporal(TemporalType.TIMESTAMP)
	//@Enumerated(EnumType.STRING)
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormBasicMapping.getName());
		assertEquals(Converter.ENUMERATED_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(EnumType.STRING, ((EnumeratedConverter) ormBasicMapping.getConverter()).getEnumType());
		assertEquals(FetchType.LAZY, ormBasicMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, ormBasicMapping.getSpecifiedOptional());
		
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		assertEquals("MY_COLUMN", ormColumn.getSpecifiedName());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", ormColumn.getColumnDefinition());
		assertEquals("MY_TABLE", ormColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(5), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(6), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedScale());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormBasicMapping.getName());
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals(FetchType.EAGER, ormBasicMapping.getSpecifiedFetch());
		assertEquals(Boolean.TRUE, ormBasicMapping.getSpecifiedOptional());
		
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		assertEquals("id", ormColumn.getSpecifiedName());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(255), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(0), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(0), ormColumn.getSpecifiedScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityBasicMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", ormBasicMapping.getName());
		assertEquals(Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertNull(ormBasicMapping.getSpecifiedFetch());
		assertNull(ormBasicMapping.getSpecifiedOptional());
		assertEquals(FetchType.EAGER, ormBasicMapping.getDefaultFetch());
		assertEquals(true, ormBasicMapping.isDefaultOptional());
		
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("id", ormColumn.getDefaultName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(255, ormColumn.getDefaultLength());
		assertEquals(0, ormColumn.getDefaultPrecision());
		assertEquals(0, ormColumn.getDefaultScale());

	}
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations
	
	
	public void testBasicMorphToIdMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((IdMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testBasicMorphToVersionMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((VersionMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testBasicMorphToTransientMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToEmbeddedMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToOneToOneMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToOneToManyMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToManyToOneMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testBasicMorphToManyToManyMapping() throws Exception {
		createTestEntityBasicMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basic");
		
		BasicMapping basicMapping = (BasicMapping) ormPersistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("basic", ormPersistentAttribute.getMapping().getName());
	}
}