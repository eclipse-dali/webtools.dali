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
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEmbeddedMappingTests extends ContextModelTestCase
{

	public static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createEmbeddableAnnotation() throws Exception{
		this.createAnnotationAndMembers("Embeddable", "");		
	}

	private void createEmbeddedAnnotation() throws Exception{
		this.createAnnotationAndMembers("Embedded", "");		
	}

	private IType createTestEntityWithEmbeddedMapping() throws Exception {
		createEntityAnnotation();
		createEmbeddedAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded").append(CR);
				sb.append(CR);
				sb.append("    private " + EMBEDDABLE_TYPE_NAME +" myEmbedded;").append(CR);
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
	

	public JavaEmbeddedMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasic() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((IEmbeddedMapping) persistentAttribute.getMapping()).attributeOverrides().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testDefaultEmbedded() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof JavaNullAttributeMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testMorphToVersion() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToTransient() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}
	
	public void testMorphToId() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
	}

	
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		
		ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.move(0, 1, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = embeddedMapping.specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
	
		assertEquals(2, CollectionTools.size(embeddedMapping.defaultAttributeOverrides()));
		IAttributeOverride defaultAttributeOverride = embeddedMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IEmbeddable embeddable = (IEmbeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		IBasicMapping cityMapping = (IBasicMapping) embeddable.persistentType().attributeNamed("city").getMapping();
		cityMapping.getColumn().setSpecifiedName("FOO");
		cityMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(2, CollectionTools.size(embeddedMapping.defaultAttributeOverrides()));
		defaultAttributeOverride = embeddedMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("FOO", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR", defaultAttributeOverride.getColumn().getTable());

		cityMapping.getColumn().setSpecifiedName(null);
		cityMapping.getColumn().setSpecifiedTable(null);
		defaultAttributeOverride = embeddedMapping.defaultAttributeOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("city");
		assertEquals(1, CollectionTools.size(embeddedMapping.defaultAttributeOverrides()));
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		assertEquals(0, embeddedMapping.specifiedAttributeOverridesSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, embeddedMapping.specifiedAttributeOverridesSize());
	}
	
	public void testAddSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
				
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("BAR");
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAddSpecifiedAttributeOverride2() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertEquals(3, CollectionTools.size(attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME)));

		embeddedMapping.removeSpecifiedAttributeOverride(1);
		
		Iterator<JavaResource> attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertEquals("BAZ", ((AttributeOverride) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<IAttributeOverride> attributeOverrides = embeddedMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());		
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		embeddedMapping.removeSpecifiedAttributeOverride(1);
		attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertEquals("FOO", ((AttributeOverride) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = embeddedMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		embeddedMapping.removeSpecifiedAttributeOverride(0);
		attributeOverrideResources = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = embeddedMapping.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(attributeResource.annotation(AttributeOverrides.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		embeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		embeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		attributeResource.move(0, 2, AttributeOverrides.ANNOTATION_NAME);
		
		Iterator<JavaResource> attributeOverrides = attributeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);

		assertEquals("BAR", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("BAZ", ((AttributeOverride) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverride) attributeOverrides.next()).getName());		
	}
	
	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) javaPersistentType().attributeNamed("myEmbedded").getMapping();
		ListIterator<IAttributeOverride> defaultAttributeOverrides = embeddedMapping.defaultAttributeOverrides();	
		IAttributeOverride defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());

		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("state", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
		
		embeddedMapping.addSpecifiedAttributeOverride(0).setName("state");
		IAttributeOverride specifiedAttributeOverride = embeddedMapping.specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		defaultAttributeOverrides = embeddedMapping.defaultAttributeOverrides();	
		defaultAttributeOverride = defaultAttributeOverrides.next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertTrue(defaultAttributeOverride.isVirtual());
		assertFalse(defaultAttributeOverrides.hasNext());
	}
}
