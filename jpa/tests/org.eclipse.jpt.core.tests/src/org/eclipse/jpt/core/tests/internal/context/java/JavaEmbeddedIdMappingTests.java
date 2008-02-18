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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.context.persistence.IClassRef;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.ManyToMany;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.internal.resource.java.Transient;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEmbeddedIdMappingTests extends ContextModelTestCase
{

	public static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createEmbeddableAnnotation() throws Exception{
		this.createAnnotationAndMembers("Embeddable", "");		
	}

	private void createEmbeddedIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("EmbeddedId", "");		
	}

	private IType createTestEntityWithEmbeddedIdMapping() throws Exception {
		createEntityAnnotation();
		createEmbeddedIdAnnotation();
	
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

	private IType createEmbeddableType() throws Exception {
		createEmbeddableAnnotation();
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
		return this.javaProject.createType(PACKAGE_NAME, EMBEDDABLE_TYPE_NAME + ".java", sourceWriter);
	}
	

	public JavaEmbeddedIdMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((IEmbeddedMapping) persistentAttribute.getMapping()).attributeOverrides().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testDefaultEmbeddedIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof JavaNullAttributeMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}

	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) persistentAttribute.getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		
		ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.move(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertEquals("myEmbeddedId", attributeResource.getName());
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverrides.ANNOTATION_NAME));
	
		assertEquals(2, embeddedIdMapping.defaultAttributeOverridesSize());
		IAttributeOverride defaultAttributeOverride = embeddedIdMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IEmbeddable embeddable = (IEmbeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		IBasicMapping cityMapping = (IBasicMapping) embeddable.persistentType().attributeNamed("city").getMapping();
		cityMapping.getColumn().setSpecifiedName("FOO");
		cityMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(2, embeddedIdMapping.defaultAttributeOverridesSize());
		defaultAttributeOverride = embeddedIdMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("FOO", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR", defaultAttributeOverride.getColumn().getTable());

		cityMapping.getColumn().setSpecifiedName(null);
		cityMapping.getColumn().setSpecifiedTable(null);
		defaultAttributeOverride = embeddedIdMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("city");
		assertEquals(1, embeddedIdMapping.defaultAttributeOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		assertEquals(0, embeddedIdMapping.specifiedAttributeOverridesSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, embeddedIdMapping.specifiedAttributeOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		assertEquals(2, embeddedIdMapping.attributeOverridesSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(4, embeddedIdMapping.attributeOverridesSize());
		
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		assertEquals(4, embeddedIdMapping.attributeOverridesSize());	
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		assertEquals(2, embeddedIdMapping.defaultAttributeOverridesSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");

		assertEquals(2, embeddedIdMapping.defaultAttributeOverridesSize());
		
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		assertEquals(1, embeddedIdMapping.defaultAttributeOverridesSize());
		
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("state");
		assertEquals(0, embeddedIdMapping.defaultAttributeOverridesSize());
	}

	public void testAddSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
				
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("BAR");
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAddSpecifiedAttributeOverride2() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertEquals(3, CollectionTools.size(attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)));

		embeddedIdMapping.removeSpecifiedAttributeOverride(1);
		
		Iterator<JavaResource> attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<IAttributeOverride> attributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());		
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		embeddedIdMapping.removeSpecifiedAttributeOverride(1);
		attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		embeddedIdMapping.removeSpecifiedAttributeOverride(0);
		attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = embeddedIdMapping.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(attributeResource.annotation(AttributeOverrides.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		attributeResource.move(2, 0, AttributeOverrides.ANNOTATION_NAME);
		
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);

		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());		
	}
	
	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedIdMapping embeddedIdMapping = (IEmbeddedIdMapping) javaPersistentType().attributeNamed("myEmbeddedId").getMapping();
		ListIterator<IAttributeOverride> defaultAttributeOverrides = embeddedIdMapping.defaultAttributeOverrides();	
		IAttributeOverride defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());

		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("state", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
		
		embeddedIdMapping.addSpecifiedAttributeOverride(0).setName("state");
		IAttributeOverride specifiedAttributeOverride = embeddedIdMapping.specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		defaultAttributeOverrides = embeddedIdMapping.defaultAttributeOverrides();	
		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
	}
}
