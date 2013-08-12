/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaPersistentAttributeTests extends ContextModelTestCase
{

	private ICompilationUnit createTestEntityAnnotatedField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private ICompilationUnit createTestEntityAnnotatedMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

		
	public GenericJavaPersistentAttributeTests(String name) {
		super(name);
	}
		
	public void testGetName() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		
		assertEquals("id", persistentAttribute.getName());
	}
	
	public void testGetMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertTrue(persistentAttribute.getMapping() instanceof JavaIdMapping);

		persistentAttribute.setMappingKey(null);
		assertTrue(persistentAttribute.getMapping() instanceof JavaBasicMapping);
	}
	
	public void testGetSpecifiedMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertTrue(persistentAttribute.getMapping() instanceof JavaIdMapping);

		persistentAttribute.setMappingKey(null);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testGetSpecifiedMappingNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();

		assertTrue(persistentAttribute.getMapping().isDefault());
		assertNotNull(persistentAttribute.getMapping());
	}
	
	public void testMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();

		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		persistentAttribute.setMappingKey(null);
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
	}
	
	public void testDefaultMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();

		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
	}
	
	public void testSetSpecifiedMappingKey() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertTrue(persistentAttribute.getMapping().isDefault());

		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		assertNotNull(resourceAttribute.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertTrue(persistentAttribute.getMapping() instanceof JavaEmbeddedMapping);
	}
	
	public void testSetSpecifiedMappingKey2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());

		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		assertNotNull(resourceAttribute.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertTrue(persistentAttribute.getMapping() instanceof JavaEmbeddedMapping);
	}

	public void testSetSpecifiedMappingKeyNull() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());

		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		assertNull(resourceAttribute.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		resourceAttribute.setPrimaryAnnotation(EmbeddedAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
		this.getJpaProject().synchronizeContextModel();
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel2() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		resourceAttribute.setPrimaryAnnotation(BasicAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
				
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMapping().getKey());
	}
	
	public void testGetAccessField() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertEquals(AccessType.FIELD, persistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, persistentAttribute.getDefaultAccess());
		assertEquals(null, persistentAttribute.getSpecifiedAccess());
	}
	
	public void testGetAccessProperty() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		assertEquals(AccessType.PROPERTY, persistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, persistentAttribute.getDefaultAccess());
		assertEquals(null, persistentAttribute.getSpecifiedAccess());
	}
	
	public void testNonResolvingField() throws Exception {
		createTestEntityWithNonResolvingField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField = getJavaPersistentType().getJavaResourceType().getFields().iterator().next();
		
		JavaSpecifiedPersistentAttribute attribute = getJavaPersistentType().getAttributeNamed("foo");
		assertTrue(attribute.isFor(resourceField));
	}
	
	public void testIsPersistableMethod2() throws Exception {
		createTestEntityWithNonResolvingMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaResourceMethod> methods = getJavaPersistentType().getJavaResourceType().getMethods().iterator();
		
		JavaResourceMethod resourceGetter = methods.next();
		JavaResourceMethod resourceSetter = methods.next();
		JavaSpecifiedPersistentAttribute attribute = getJavaPersistentType().getAttributeNamed("foo");
	
		assertTrue(attribute.isFor(resourceGetter, resourceSetter));
	}
	
	private ICompilationUnit createTestEntityWithNonResolvingField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("private Foo foo;").append(CR);
				sb.append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithNonResolvingMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("private Foo foo;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
				sb.append(CR);
				sb.append("    public Foo getFoo() {").append(CR);
				sb.append("        return this.foo;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append(CR);
				sb.append("    public void setFoo(Foo foo) {").append(CR);
				sb.append("        this.foo = foo;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	public void testGetAttributeTypeName() throws Exception {
		createGenericSuperclass();
		createGenericSubclass();
		JavaPersistentType superclassPT = 
				IterableTools.get(getPersistenceUnit().getClassRefs(), 0).getJavaPersistentType();
		JavaPersistentType subclassPT = 
				IterableTools.get(getPersistenceUnit().getClassRefs(), 1).getJavaPersistentType();
		
		// generic field
		JavaSpecifiedPersistentAttribute genericAttribute = superclassPT.getAttributeNamed("genericField");
		assertEquals("java.lang.Number", genericAttribute.getTypeName());
		assertEquals("java.lang.Number", genericAttribute.getTypeName(superclassPT));
		assertEquals("java.lang.Long", genericAttribute.getTypeName(subclassPT));
		
		// nongeneric field
		JavaSpecifiedPersistentAttribute nongenericAttribute = superclassPT.getAttributeNamed("nongenericField");
		assertEquals("java.lang.Number", nongenericAttribute.getTypeName());
		assertEquals("java.lang.Number", nongenericAttribute.getTypeName(superclassPT));
		assertEquals("java.lang.Number", nongenericAttribute.getTypeName(subclassPT));
	}
	
	private static String GENERIC_SUPERCLASS = "GenericSuperclass";
	
	private ICompilationUnit createGenericSuperclass() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(GENERIC_SUPERCLASS).append("<T extends Number> ").append("{").append(CR);
				sb.append("    protected T genericField;").append(CR);
				sb.append("    protected Number nongenericField;").append(CR);
				sb.append("}").append(CR);
			}
		};
		addXmlClassRef(PACKAGE_NAME_ + GENERIC_SUPERCLASS);
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, GENERIC_SUPERCLASS + ".java", sourceWriter);
	}
	
	private static String GENERIC_SUBCLASS = "GenericSubclass";
	
	private ICompilationUnit createGenericSubclass() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(GENERIC_SUBCLASS).append(CR);
				sb.append("        extends ").append(GENERIC_SUPERCLASS).append("<Long> {").append(CR);
				sb.append("}").append(CR);
			}
		};
		addXmlClassRef(PACKAGE_NAME_ + GENERIC_SUBCLASS);
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, GENERIC_SUBCLASS + ".java", sourceWriter);
	}
}
