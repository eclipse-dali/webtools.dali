/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmPersistentAttributeTests extends ContextModelTestCase
{
	public GenericOrmPersistentAttributeTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestTypeNullAttributeMapping() throws Exception {
	
		return this.createTestType(new DefaultAnnotationWriter() {			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);			
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append("@Column(name=\"FOO\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne");
				sb.append("    private Address address;");
			}
		});
	}
	
	public void testMakeSpecified() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		ormPersistentAttribute.addToXml();
		
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ormPersistentAttribute.addToXml();
		
		assertEquals(0, ormPersistentType.getDefaultAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		Iterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.getSpecifiedAttributes().iterator();
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("name", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
	}
	
	public void testMakeSpecifiedMappingKey() throws Exception {
		createTestTypeNullAttributeMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());
		
		//take a virtual mapping with a mapping type and make it specified
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		assertEquals("address", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertNull(ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute.addToXml(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("address", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertTrue(specifiedOrmPersistentAttribute.getMapping() instanceof GenericOrmOneToOneMapping);
		
		
		ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ormPersistentAttribute.addToXml(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		Iterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.getSpecifiedAttributes().iterator();
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertTrue(specifiedOrmPersistentAttribute.getMapping() instanceof GenericOrmIdMapping);
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("address", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
	}
	
	public void testMakeVirtual() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		ormPersistentType.getDefaultAttributes().iterator().next().addToXml();
		ormPersistentType.getDefaultAttributes().iterator().next().addToXml();

		assertEquals(0, ormPersistentType.getDefaultAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		specifiedOrmPersistentAttribute.removeFromXml();
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		
		specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		specifiedOrmPersistentAttribute.removeFromXml();
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		assertEquals(0, ormPersistentType.getSpecifiedAttributesSize());
		
		Iterator<OrmReadOnlyPersistentAttribute> virtualAttributes = ormPersistentType.getDefaultAttributes().iterator();
		OrmReadOnlyPersistentAttribute virtualAttribute = virtualAttributes.next();		
		assertEquals("id", virtualAttribute.getName());
		virtualAttribute = virtualAttributes.next();		
		assertEquals("name", virtualAttribute.getName());
	}
	
	public void testMakeVirtualNoUnderlyingJavaAttribute() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		ormPersistentType.getDefaultAttributes().iterator().next().addToXml();
		ormPersistentType.getDefaultAttributes().iterator().next().addToXml();

		
		ormPersistentType.getSpecifiedAttributes().iterator().next().getMapping().setName("noJavaAttribute");
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		
		
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		specifiedOrmPersistentAttribute.removeFromXml();
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		
		assertEquals("id", ormPersistentType.getDefaultAttributes().iterator().next().getName());
		assertEquals("name", ormPersistentType.getSpecifiedAttributes().iterator().next().getName());
	}
	
	public void testVirtualMappingTypeWhenMetadataComplete()  throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertEquals("FOO", ((IdMapping) ormPersistentAttribute.getMapping()).getColumn().getName());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);

		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertEquals("id", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertEquals("id", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getName());
	}
	
	public void testVirtualMappingTypeWhenMetadataComplete2()  throws Exception {
		createTestEntityOneToOneMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		assertEquals("address", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);

		ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		assertEquals("address", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
	}
	
	public void testGetJavaPersistentAttribute() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
			
		//virtual orm attribute, access type matches java : FIELD, name matches java
		assertTrue(ormPersistentAttribute.isVirtual());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		JavaResourceAttribute javaResourceAttribute = ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute();
		assertEquals("id", javaResourceAttribute.getName());
		assertEquals(javaPersistentType.getJavaResourceType().getFields().iterator().next(), javaResourceAttribute);
		
		
		//specified orm attribute, access type matches java : FIELD, name matches java
		//javaPersistentAttribute should be == to java context model object
		ormPersistentAttribute.addToXml();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
	
		
		//virtual orm attribute, java access type FIELD, orm access type PROPERTY, name matches java
		//verify both the property java resource attribute and the field java resource attribute are used in orm
		//because the field is annotated and property is specified
		((OrmPersistentAttribute) ormPersistentAttribute).removeFromXml();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");		
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		ListIterator<OrmReadOnlyPersistentAttribute> attributes = ormPersistentType.getAttributes().iterator();
		OrmReadOnlyPersistentAttribute idFieldAttribute = attributes.next();
		OrmReadOnlyPersistentAttribute idPropertyAttribute = attributes.next();
		assertEquals(ormPersistentAttribute, idFieldAttribute);
		assertNotSame(ormPersistentAttribute, idPropertyAttribute);
		ormPersistentAttribute = idPropertyAttribute;
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.FIELD, idFieldAttribute.getJavaPersistentAttribute().getAccess());
		javaResourceAttribute = ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute();
		assertEquals("id", javaResourceAttribute.getName());
		assertEquals(javaPersistentType.getJavaResourceType().getMethods().iterator().next(), javaResourceAttribute);
		
		
		ormPersistentType.setSpecifiedAccess(null);//default access will be field
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");		
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentAttribute.addToXml();
		ormPersistentAttribute2.getMapping().setName("id2");
		assertEquals(null, ormPersistentAttribute2.getJavaPersistentAttribute());
		
		ormPersistentAttribute2.getMapping().setName(null);
		assertEquals(null, ormPersistentAttribute2.getJavaPersistentAttribute());

		ormPersistentAttribute2.getMapping().setName("id");
		assertEquals(javaPersistentAttribute, ormPersistentAttribute2.getJavaPersistentAttribute());

		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(javaPersistentAttribute, ormPersistentAttribute2.getJavaPersistentAttribute());
	}
}