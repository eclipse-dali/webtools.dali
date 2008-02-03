/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.orm.XmlBasicMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlBasicMappingTests extends ContextModelTestCase
{
	public XmlBasicMappingTests(String name) {
		super(name);
	}	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createBasicAnnotation() throws Exception{
		this.createAnnotationAndMembers("Basic", "FetchType fetch() default EAGER; boolean optional() default true;");		
	}
	
	private void createColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";" +
			"int length() default 255;" +
			"int precision() default 0;" +
			"int scale() default 0;");		
	}
	
	private void createLobAnnotation() throws Exception{
		this.createAnnotationAndMembers("Lob", "");		
	}
	
	private void createEnumeratedAnnotation() throws Exception{
		this.createAnnotationAndMembers("Enumerated", "EnumType value() default ORDINAL;");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}
	
	private IType createTestEntityBasicMapping() throws Exception {
		createEntityAnnotation();
		createBasicAnnotation();
		createColumnAnnotation();
		createLobAnnotation();
		createTemporalAnnotation();
		createEnumeratedAnnotation();
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", xmlBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the resource model, verify context model updated
		basicResource.setName("newName");
		assertEquals("newName", xmlBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the resource model
		basicResource.setName(null);
		assertNull(xmlBasicMapping.getName());
		assertNull(basicResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", xmlBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlBasicMapping.setName("newName");
		assertEquals("newName", xmlBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the context model
		xmlBasicMapping.setName(null);
		assertNull(xmlBasicMapping.getName());
		assertNull(basicResource.getName());
	}

	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		basicResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, basicResource.getFetch());
	
		basicResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, basicResource.getFetch());

		//set fetch to null in the resource model
		basicResource.setFetch(null);
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, basicResource.getFetch());
		assertEquals(FetchType.EAGER, xmlBasicMapping.getSpecifiedFetch());
	
		xmlBasicMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, basicResource.getFetch());
		assertEquals(FetchType.LAZY, xmlBasicMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlBasicMapping.setSpecifiedFetch(null);
		assertNull(basicResource.getFetch());
		assertNull(xmlBasicMapping.getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedEnumerated() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setEnumerated(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
	
		basicResource.setEnumerated(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING, basicResource.getEnumerated());

		//set enumerated to null in the resource model
		basicResource.setEnumerated(null);
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getSpecifiedEnumerated());
	
		xmlBasicMapping.setSpecifiedEnumerated(EnumType.STRING);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING, basicResource.getEnumerated());
		assertEquals(EnumType.STRING, xmlBasicMapping.getSpecifiedEnumerated());

		//set enumerated to null in the context model
		xmlBasicMapping.setSpecifiedEnumerated(null);
		assertNull(basicResource.getEnumerated());
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, basicResource.getOptional());
	
		basicResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, basicResource.getOptional());

		//set enumerated to null in the resource model
		basicResource.setOptional(null);
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicResource.getOptional());
		assertEquals(Boolean.TRUE, xmlBasicMapping.getSpecifiedOptional());
	
		xmlBasicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, basicResource.getOptional());
		assertEquals(Boolean.FALSE, xmlBasicMapping.getSpecifiedOptional());

		//set enumerated to null in the context model
		xmlBasicMapping.setSpecifiedOptional(null);
		assertNull(basicResource.getOptional());
		assertNull(xmlBasicMapping.getSpecifiedOptional());
	}
	
	public void testUpdateSpecifiedLob() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
				
		//set lob in the resource model, verify context model updated
		basicResource.setLob(true);
		ormResource().save(null);
		assertTrue(xmlBasicMapping.isLob());
		assertTrue(basicResource.isLob());

		//set lob to null in the resource model
		basicResource.setLob(false);
		ormResource().save(null);
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
	}
	
	public void testModifySpecifiedLob() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
	
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
				
		//set lob in the context model, verify resource model updated
		xmlBasicMapping.setLob(true);
		ormResource().save(null);
		assertTrue(basicResource.isLob());
		assertTrue(xmlBasicMapping.isLob());
	
		//set lob to false in the context model
		xmlBasicMapping.setLob(false);
		ormResource().save(null);
		assertFalse(basicResource.isLob());
		assertFalse(xmlBasicMapping.isLob());
	}
	
	public void testUpdateTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, basicResource.getTemporal());
	
		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, basicResource.getTemporal());

		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());

		//set temporal to null in the resource model
		basicResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlBasicMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, basicResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlBasicMapping.getTemporal());
	
		xmlBasicMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, basicResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlBasicMapping.getTemporal());

		xmlBasicMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlBasicMapping.getTemporal());

		//set temporal to null in the context model
		xmlBasicMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(basicResource.getTemporal());
		assertNull(xmlBasicMapping.getTemporal());
	}
	
	//TODO test morphing to other mapping types
	//TODO test defaults


	public void testBasicMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityBasicMapping();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlBasicMapping.getName());
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertFalse(xmlBasicMapping.isLob());
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getEnumerated());
		assertEquals(FetchType.EAGER, xmlBasicMapping.getFetch());
		assertEquals(Boolean.TRUE, xmlBasicMapping.getOptional());
		assertNull(xmlBasicMapping.getTemporal());

		
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(xmlColumn.getSpecifiedPrecision());
		assertNull(xmlColumn.getSpecifiedScale());
		
		assertEquals("foo", xmlColumn.getDefaultName());
		assertEquals(Boolean.FALSE, xmlColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultUpdatable());
		assertEquals(null, xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultScale());
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlBasicMapping.getName());
		assertTrue(xmlBasicMapping.isLob());
		assertEquals(EnumType.STRING, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(FetchType.LAZY, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, xmlBasicMapping.getSpecifiedOptional());
		assertEquals(TemporalType.TIMESTAMP, xmlBasicMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		assertEquals("MY_COLUMN", xmlColumn.getSpecifiedName());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", xmlColumn.getColumnDefinition());
		assertEquals("MY_TABLE", xmlColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(5), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(6), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedScale());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityBasicMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.getMapping().setSpecifiedMetadataComplete(true);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlBasicMapping.getName());
		assertFalse(xmlBasicMapping.isLob());
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(FetchType.EAGER, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(Boolean.TRUE, xmlBasicMapping.getSpecifiedOptional());
		assertNull(xmlBasicMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		assertEquals("id", xmlColumn.getSpecifiedName());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getSpecifiedScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityBasicMapping();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlBasicMapping.getName());
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertFalse(xmlBasicMapping.isLob());
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getDefaultEnumerated());
		assertEquals(FetchType.EAGER, xmlBasicMapping.getDefaultFetch());
		assertEquals(Boolean.TRUE, xmlBasicMapping.getDefaultOptional());
		assertNull(xmlBasicMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(xmlColumn.getSpecifiedPrecision());
		assertNull(xmlColumn.getSpecifiedScale());
		
		assertEquals("id", xmlColumn.getDefaultName());
		assertEquals(Boolean.FALSE, xmlColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultUpdatable());
		assertEquals(null, xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultScale());

	}
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations
	
}