/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmPersistentAttribute2_0Tests
	extends Generic2_0ContextModelTestCase
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
		
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getVirtualAttributes().iterator().next();
		assertEquals("id", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		ormPersistentAttribute.convertToSpecified();
		
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		ormPersistentAttribute = ormPersistentType.getVirtualAttributes().iterator().next();
		ormPersistentAttribute.convertToSpecified();
		
		assertEquals(0, ormPersistentType.getVirtualAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		Iterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.getSpecifiedAttributes().iterator();
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
		
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = CollectionTools.get(ormPersistentType.getVirtualAttributes(), 1);
		assertEquals("id", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertEquals(null, ormPersistentAttribute.getJavaPersistentAttribute().getSpecifiedAccess());
		ormPersistentAttribute.convertToSpecified();
		
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertEquals(null, specifiedOrmPersistentAttribute.getSpecifiedAccess());
		assertEquals(AccessType.PROPERTY, specifiedOrmPersistentAttribute.getAccess());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		ormPersistentAttribute = ormPersistentType.getVirtualAttributes().iterator().next();
		assertEquals("name", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		ormPersistentAttribute.convertToSpecified();
		
		assertEquals(0, ormPersistentType.getVirtualAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		Iterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.getSpecifiedAttributes().iterator();
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
		
		assertEquals(3, ormPersistentType.getVirtualAttributesSize());
		
		//take a virtual mapping with a mapping type and make it specified
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		assertEquals("address", ormPersistentAttribute.getName());
		assertTrue(ormPersistentAttribute.isVirtual());
		assertTrue(ormPersistentAttribute.getMapping() instanceof GenericJavaNullAttributeMapping);
		ormPersistentAttribute.convertToSpecified(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("address", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		assertTrue(specifiedOrmPersistentAttribute.getMapping() instanceof OrmOneToOneMapping);
		
		
		ormPersistentAttribute = ormPersistentType.getVirtualAttributes().iterator().next();
		ormPersistentAttribute.convertToSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		Iterator<OrmPersistentAttribute> specifiedAttributes = ormPersistentType.getSpecifiedAttributes().iterator();
		
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
		
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		
		ormPersistentType.getVirtualAttributes().iterator().next().convertToSpecified();
		ormPersistentType.getVirtualAttributes().iterator().next().convertToSpecified();

		assertEquals(0, ormPersistentType.getVirtualAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		assertEquals("id", specifiedOrmPersistentAttribute.getName());
		assertFalse(specifiedOrmPersistentAttribute.isVirtual());
		
		specifiedOrmPersistentAttribute.convertToVirtual();
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		
		specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		specifiedOrmPersistentAttribute.convertToVirtual();
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		assertEquals(0, ormPersistentType.getSpecifiedAttributesSize());
		
		Iterator<OrmReadOnlyPersistentAttribute> virtualAttributes = ormPersistentType.getVirtualAttributes().iterator();
		OrmReadOnlyPersistentAttribute virtualAttribute = virtualAttributes.next();		
		assertEquals("id", virtualAttribute.getName());
		virtualAttribute = virtualAttributes.next();		
		assertEquals("name", virtualAttribute.getName());
	}
	
	public void testMakeVirtualNoUnderlyingJavaAttribute() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(2, ormPersistentType.getVirtualAttributesSize());
		
		ormPersistentType.getVirtualAttributes().iterator().next().convertToSpecified();
		ormPersistentType.getVirtualAttributes().iterator().next().convertToSpecified();

		
		ormPersistentType.getSpecifiedAttributes().iterator().next().getMapping().setName("noJavaAttribute");
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(2, ormPersistentType.getSpecifiedAttributesSize());
		
		
		OrmPersistentAttribute specifiedOrmPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		specifiedOrmPersistentAttribute.convertToVirtual();
		assertEquals(1, ormPersistentType.getVirtualAttributesSize());
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		
		assertEquals("id", ormPersistentType.getVirtualAttributes().iterator().next().getName());
		assertEquals("name", ormPersistentType.getSpecifiedAttributes().iterator().next().getName());
	}
	
	public void testVirtualMappingTypeWhenMetadataComplete()  throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
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
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
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
		JavaResourceAttribute javaResourceAttribute = ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute();
		assertTrue(javaResourceAttribute.getKind() == Kind.FIELD);
		assertEquals("id", javaResourceAttribute.getName());
		assertEquals(javaPersistentType.getJavaResourceType().getFields().iterator().next(), javaResourceAttribute);
		
		
		//specified orm attribute, access type matches java : FIELD, name matches java
		//javaPersistentAttribute should be == to java context model object
		ormPersistentAttribute.convertToSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
	
		
		//virtual orm attribute, java access type FIELD, orm access type PROPERTY, name matches java
		//verify both the property java resource attribute and the field java resource attribute are used in orm
		//because the field is annotated and property is specified
		((OrmPersistentAttribute) ormPersistentAttribute).convertToVirtual();
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
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentAttribute.convertToSpecified();
		ormPersistentAttribute2.getMapping().setName("id2");
		assertEquals(null, ormPersistentAttribute2.getJavaPersistentAttribute());
		
		ormPersistentAttribute2.getMapping().setName(null);
		assertEquals(null, ormPersistentAttribute2.getJavaPersistentAttribute());

		ormPersistentAttribute2.getMapping().setName("id");
		assertEquals(javaPersistentAttribute, ormPersistentAttribute2.getJavaPersistentAttribute());

		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(javaPersistentAttribute, ormPersistentAttribute2.getJavaPersistentAttribute());
	}
	
	public void testGetJavaPersistentAttributeMixedAccess() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		ListIterator<OrmReadOnlyPersistentAttribute> attributes = ormPersistentType.getAttributes().iterator();
		OrmReadOnlyPersistentAttribute ormFieldAttribute = attributes.next();
		OrmReadOnlyPersistentAttribute ormPropertyAttribute = attributes.next();
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertEquals(AccessType.FIELD, ormFieldAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, ormPropertyAttribute.getAccess());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getOwningPersistentType().getAccess());
		assertTrue(ormFieldAttribute.isVirtual());
		assertTrue(ormPropertyAttribute.isVirtual());
		assertNotSame(javaPersistentAttribute, ormPropertyAttribute.getJavaPersistentAttribute());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormFieldAttribute.getMappingKey());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPropertyAttribute.getMappingKey());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());
		
		
		ormPropertyAttribute.convertToSpecified();
		ormPropertyAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPropertyAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPropertyAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPropertyAttribute.getJavaPersistentAttribute());
		assertTrue(ormPropertyAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.METHOD);
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
		
		((OrmPersistentAttribute) ormPropertyAttribute).setSpecifiedAccess(AccessType.FIELD);
		ormPropertyAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPropertyAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPropertyAttribute.getAccess());
		assertEquals(javaPersistentAttribute, ormPropertyAttribute.getJavaPersistentAttribute());
		assertTrue(ormPropertyAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.FIELD);
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
	}
	
	public void testGetAccess() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
		
		
		ormPersistentAttribute.convertToSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertEquals(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
	}
	
	public void testGetAccessPropertyInJava() throws Exception {
		createTestEntityIdMappingPropertyAccess();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.METHOD);
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);
		
		
		ormPersistentAttribute.convertToSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.METHOD);
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);
		
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);
	}

	public void testGetAccessPropertyInJava2() throws Exception {
		createTestEntityAnnotatedFieldPropertySpecified();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		JavaPersistentAttribute javaPersistentAttribute = javaPersistentType.getAttributeNamed("id");
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.METHOD);
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);

		
		OrmReadOnlyPersistentAttribute nameOrmPersistentAttribute = ormPersistentType.getAttributeNamed("name");
		JavaPersistentAttribute nameJavaPersistentAttribute = javaPersistentType.getAttributeNamed("name");
		
		assertTrue(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertTrue(nameOrmPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.FIELD);
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);

			
		ormPersistentAttribute.convertToSpecified();
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
		assertTrue(ormPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.METHOD);
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);
		
		nameOrmPersistentAttribute.convertToSpecified();
		nameOrmPersistentAttribute = ormPersistentType.getAttributeNamed("name");
		assertFalse(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertTrue(nameOrmPersistentAttribute.getJavaPersistentAttribute().getResourceAttribute().getKind() == Kind.FIELD);
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);
		assertEquals(nameJavaPersistentAttribute, nameOrmPersistentAttribute.getJavaPersistentAttribute());
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, ormPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, ormPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertTrue(javaPersistentAttribute.getResourceAttribute().getKind() == Kind.METHOD);

		assertFalse(nameOrmPersistentAttribute.isVirtual());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getAccess());
		assertNotSame(javaPersistentAttribute, nameOrmPersistentAttribute.getJavaPersistentAttribute());
		assertEquals(AccessType.FIELD, nameOrmPersistentAttribute.getJavaPersistentAttribute().getAccess());
		assertEquals(AccessType.FIELD, nameJavaPersistentAttribute.getAccess());
		assertTrue(nameJavaPersistentAttribute.getResourceAttribute().getKind() == Kind.FIELD);

	}

}