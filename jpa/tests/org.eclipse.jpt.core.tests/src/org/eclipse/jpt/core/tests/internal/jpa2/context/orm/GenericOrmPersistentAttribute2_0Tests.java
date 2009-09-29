/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmNullAttributeMapping;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmPersistentAttribute2_0Tests
	extends Generic2_0OrmContextModelTestCase
{

	public GenericOrmPersistentAttribute2_0Tests(String name) {
		super(name);
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
	
	private ICompilationUnit createTestEntityIdMappingPropertyAccess() throws Exception {
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
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
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

	private ICompilationUnit createTestEntityAnnotatedFieldPropertySpecified() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.ID, JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Access(AccessType.PROPERTY)");
			}
	
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
				sb.append("@Access(AccessType.FIELD)");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	public void testMakeSpecified() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		ormPersistentAttribute.makeSpecified();
		
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormPersistentAttribute.makeSpecified();
		
		assertEquals(0, ormPersistentType.virtualAttributesSize());
		assertEquals(2, ormPersistentType.specifiedAttributesSize());
		ListIterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.specifiedAttributes();
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("name", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
	}
	
	public void testMakeSpecifiedWithAccess() throws Exception {
		createTestEntityAnnotatedFieldPropertySpecified();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getSpecifiedAccess());
		assertEquals(null, ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess());
		ormPersistentAttribute.makeSpecified();
		
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertEquals(null, specifiedOrmPersistentAttribute.getSpecifiedAccess());
		assertEquals(AccessType.PROPERTY, specifiedOrmPersistentAttribute.getAccess());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("name", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getSpecifiedAccess());
		ormPersistentAttribute.makeSpecified();
		
		assertEquals(0, ormPersistentType.virtualAttributesSize());
		assertEquals(2, ormPersistentType.specifiedAttributesSize());
		ListIterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.specifiedAttributes();
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, specifiedOrmPersistentAttribute.getAccess());
		assertEquals(null, specifiedOrmPersistentAttribute.getSpecifiedAccess());
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("name", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, specifiedOrmPersistentAttribute.getSpecifiedAccess());
	}

	public void testMakeSpecifiedMappingKey() throws Exception {
		createTestTypeNullAttributeMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		//take a virtual mapping with a mapping type and make it specified
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("address", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertTrue(ormPersistentAttribute.getMapping() instanceof GenericOrmNullAttributeMapping);
		ormPersistentAttribute.makeSpecified(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		assertEquals("address", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertTrue(specifiedOrmPersistentAttribute.getMapping() instanceof OrmOneToOneMapping);
		
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormPersistentAttribute.makeSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(2, ormPersistentType.specifiedAttributesSize());
		ListIterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.specifiedAttributes();
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertTrue(specifiedOrmPersistentAttribute.getMapping() instanceof OrmIdMapping);
		
		specifiedOrmPersistentAttribute = specifiedAttributes.next();
		assertEquals("address", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
	}
	
	public void testMakeVirtual() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		ormPersistentType.virtualAttributes().next().makeSpecified();
		ormPersistentType.virtualAttributes().next().makeSpecified();

		assertEquals(0, ormPersistentType.virtualAttributesSize());
		assertEquals(2, ormPersistentType.specifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		specifiedOrmPersistentAttribute.makeVirtual();
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		
		specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		specifiedOrmPersistentAttribute.makeVirtual();
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		assertEquals(0, ormPersistentType.specifiedAttributesSize());
		
		ListIterator<OrmPersistentAttribute> virtualAttributes = ormPersistentType.virtualAttributes();
		OrmPersistentAttribute virtualAttribute = virtualAttributes.next();		
		assertEquals("id", virtualAttribute.getName());
		virtualAttribute = virtualAttributes.next();		
		assertEquals("name", virtualAttribute.getName());
	}
	
	public void testMakeVirtualNoUnderlyingJavaAttribute() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		ormPersistentType.virtualAttributes().next().makeSpecified();
		ormPersistentType.virtualAttributes().next().makeSpecified();

		
		ormPersistentType.specifiedAttributes().next().getMapping().setName("noJavaAttribute");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(2, ormPersistentType.specifiedAttributesSize());
		
		
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		specifiedOrmPersistentAttribute.makeVirtual();
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		
		assertEquals("id", ormPersistentType.virtualAttributes().next().getName());
		assertEquals("name", ormPersistentType.specifiedAttributes().next().getName());
	}
	
	public void testVirtualMappingTypeWhenMetadataComplete()  throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertEquals("FOO", ((IdMapping) ormPersistentAttribute.getMapping()).getColumn().getName());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);

		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertEquals("id", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getName());
	}
	
	public void testVirtualMappingTypeWhenMetadataComplete2()  throws Exception {
		createTestEntityOneToOneMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("address", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);

		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		assertEquals("address", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
	}
	
	public void testGetJavaPersistentAttribute() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
			
		//virtual orm attribute, access type matches java : FIELD, name matches java
		assertTrue(ormPersistentAttribute.isVirtual());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		JavaResourcePersistentAttribute javaResourcePersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute();
		assertTrue(javaResourcePersistentAttribute.isField());
		assertEquals("id", javaResourcePersistentAttribute.getName());
		assertEquals(javaPersistentType.getResourcePersistentType().persistableFields().next(), javaResourcePersistentAttribute);
		
		
		//specified orm attribute, access type matches java : FIELD, name matches java
		//javaPersistentAttribute should be == to java context model object
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
	
		
		//virtual orm attribute, java access type FIELD, orm access type PROPERTY, name matches java
		//verify the property java resource persistent attribute is used in orm.
		ormPersistentAttribute.makeVirtual();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");		
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		assertNotSame(ormPersistentAttribute, ormPersistentType.getAttributeNamed("id"));
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		javaResourcePersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute();
		assertTrue(javaResourcePersistentAttribute.isProperty());
		assertEquals("id", javaResourcePersistentAttribute.getName());
		assertEquals(javaPersistentType.getResourcePersistentType().persistableProperties().next(), javaResourcePersistentAttribute);
		
		
		ormPersistentType.setSpecifiedAccess(null);//default access will be field
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");		
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");		
		ormPersistentAttribute.getMapping().setName("id2");
		assertEquals(null, ormPersistentAttribute.getJavaPersistentAttribute());
		
		ormPersistentAttribute.getMapping().setName(null);
		assertEquals(null, ormPersistentAttribute.getJavaPersistentAttribute());

		ormPersistentAttribute.getMapping().setName("id");
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());

		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
	}
	
	public void testGetJavaPersistentAttributeMixedAccess() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getPersistentType().getAccess());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());
		
		
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isProperty());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isField());
		
		ormPersistentAttribute.setSpecifiedAccess(AccessType.FIELD);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isField());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isField());
	}
	
	public void testGetAccess() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isField());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isField());
		
		
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isField());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isField());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isField());
	}
	
	public void testGetAccessPropertyInJava() throws Exception {
		createTestEntityIdMappingPropertyAccess();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isProperty());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());
		
		
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isProperty());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());
	}

	public void testGetAccessPropertyInJava2() throws Exception {
		createTestEntityAnnotatedFieldPropertySpecified();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isProperty());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());

		
		OrmPersistentAttribute nameOrmPersistentAttribute = ormPersistentType.getAttributeNamed("name");
		JavaPersistentAttribute nameJavaPersistentAttribute = javaPersistentType.getAttributeNamed("name");
		
		assertTrue(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertTrue(nameOrmPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isField());
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourcePersistentAttribute().isField());

			
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isProperty());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());
		
		nameOrmPersistentAttribute.makeSpecified();
		nameOrmPersistentAttribute = ormPersistentType.getAttributeNamed("name");
		assertFalse(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertTrue(nameOrmPersistentAttribute.getJavaPersistentAttribute().getResourcePersistentAttribute().isField());
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourcePersistentAttribute().isField());
		assertEquals(nameJavaPersistentAttribute, nameOrmPersistentAttribute.getJavaPersistentAttribute());
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourcePersistentAttribute().isProperty());

		assertFalse(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, nameOrmPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourcePersistentAttribute().isField());

	}

}