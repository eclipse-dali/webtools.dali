/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaEmbeddedIdMappingTests extends ContextModelTestCase
{

	public static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	private ICompilationUnit createTestEntityWithEmbeddedIdMapping() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@EmbeddedId").append(CR);
				sb.append(CR);
				sb.append("    private " + EMBEDDABLE_TYPE_NAME +" myEmbeddedId;").append(CR);
				sb.append(CR);
			}
		});
	}

	private void createEmbeddableType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import ");
				sb.append(JPA.EMBEDDABLE);
				sb.append(";");
				sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append(EMBEDDABLE_TYPE_NAME).append(" {");
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, EMBEDDABLE_TYPE_NAME + ".java", sourceWriter);
	}
	

	public JavaEmbeddedIdMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((EmbeddedMapping) persistentAttribute.getMapping()).attributeOverrides().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testDefaultEmbeddedIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof GenericJavaNullAttributeMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
	
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		assertNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.moveSupportingAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertEquals("myEmbeddedId", attributeResource.getName());
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, embeddedMapping.virtualAttributeOverridesSize());
		AttributeOverride defaultAttributeOverride = embeddedMapping.virtualAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		Embeddable embeddable = (Embeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		BasicMapping cityMapping = (BasicMapping) embeddable.getPersistentType().getAttributeNamed("city").getMapping();
		cityMapping.getColumn().setSpecifiedName("FOO");
		cityMapping.getColumn().setSpecifiedTable("BAR");
		cityMapping.getColumn().setColumnDefinition("COLUMN_DEF");
		cityMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		cityMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		cityMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		cityMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));
		
		assertEquals("myEmbeddedId", attributeResource.getName());
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, embeddedMapping.virtualAttributeOverridesSize());
		defaultAttributeOverride = embeddedMapping.virtualAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("FOO", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR", defaultAttributeOverride.getColumn().getTable());
		assertEquals("COLUMN_DEF", defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(false, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(5, defaultAttributeOverride.getColumn().getLength());
		assertEquals(6, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(7, defaultAttributeOverride.getColumn().getScale());

		cityMapping.getColumn().setSpecifiedName(null);
		cityMapping.getColumn().setSpecifiedTable(null);
		cityMapping.getColumn().setColumnDefinition(null);
		cityMapping.getColumn().setSpecifiedInsertable(null);
		cityMapping.getColumn().setSpecifiedUpdatable(null);
		cityMapping.getColumn().setSpecifiedUnique(null);
		cityMapping.getColumn().setSpecifiedNullable(null);
		cityMapping.getColumn().setSpecifiedLength(null);
		cityMapping.getColumn().setSpecifiedPrecision(null);
		cityMapping.getColumn().setSpecifiedScale(null);
		defaultAttributeOverride = embeddedMapping.virtualAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("city");
		assertEquals(1, embeddedMapping.virtualAttributeOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		assertEquals(0, embeddedIdMapping.specifiedAttributeOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, embeddedIdMapping.specifiedAttributeOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		assertEquals(2, embeddedIdMapping.attributeOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(4, embeddedIdMapping.attributeOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		assertEquals(4, embeddedIdMapping.attributeOverridesSize());	
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		assertEquals(2, embeddedIdMapping.virtualAttributeOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");

		assertEquals(2, embeddedIdMapping.virtualAttributeOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		assertEquals(1, embeddedIdMapping.virtualAttributeOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("state");
		assertEquals(0, embeddedIdMapping.virtualAttributeOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
				
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.virtualAttributeOverrides().next().setVirtual(false);
		embeddedIdMapping.virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedIdMapping.specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrides = attributeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		assertEquals("city", embeddedIdMapping.virtualAttributeOverrides().next().getName());
		assertEquals(1, embeddedIdMapping.virtualAttributeOverridesSize());
		
		embeddedIdMapping.specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrides = attributeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<AttributeOverride> virtualAttributeOverrides = embeddedIdMapping.virtualAttributeOverrides();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state", virtualAttributeOverrides.next().getName());
		assertEquals(2, embeddedIdMapping.virtualAttributeOverridesSize());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		ListIterator<AttributeOverride> virtualAttributeOverrides = embeddedIdMapping.virtualAttributeOverrides();
		virtualAttributeOverrides.next();	
		virtualAttributeOverrides.next().setVirtual(false);
		embeddedIdMapping.virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}

	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) getJavaPersistentType().getAttributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.virtualAttributeOverrides().next().setVirtual(false);
		embeddedIdMapping.virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		attributeResource.moveSupportingAnnotation(1, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);

		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
}
