/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement.Editor;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceOneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;

@SuppressWarnings("nls")
public class JavaResourceAttributeTests extends JpaJavaResourceModelTestCase {
	
	public JavaResourceAttributeTests(String name) {
		super(name);
	}
		
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithNonResolvingField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
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

	private ICompilationUnit createTestEntityMultipleVariableDeclarationsPerLine() throws Exception {
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
				sb.append(CR);
				sb.append("    ");
				sb.append("@Id");
				sb.append(CR);
				sb.append("    ");
				sb.append("@Column(name = \"baz\")");
				sb.append("    private String foo, bar;").append(CR);
				sb.append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithIdAndBasic() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append(CR);
				sb.append("@Basic");
			}
		});
	}
	
	private ICompilationUnit createTestEntityAnnotatedField() throws Exception {
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
				sb.append(CR);
				sb.append("    ");
				sb.append("@Column");
			}
		});
	}	
	
	private ICompilationUnit createTestEntityWithColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(name = \"FOO\", table = \"MY_TABLE\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithIdColumnGeneratedValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.COLUMN, JPA.ID, JPA.GENERATED_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append(CR);
				sb.append("@Column");
				sb.append(CR);
				sb.append("@GeneratedValue");
			}
		});
	}

	
	private ICompilationUnit createTestEntityMultipleColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(name = \"FOO\")");
				sb.append(CR);
				sb.append("@Column(name = \"BAR\")");
			}
		});
	}

	private ICompilationUnit createTestEmbeddedWithAttributeOverride() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverride(name = \"FOO\")");
			}
		});
	}
	private ICompilationUnit createTestEmbeddedWithAttributeOverrides() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverrides(@AttributeOverride(name = \"FOO\"))");
			}
		});
	}
	private ICompilationUnit createTestEmbeddedWithAttributeOverridesEmpty() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverrides()");
			}
		});
	}
	
	private ICompilationUnit createTestEmbeddedWith2AttributeOverrides() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\")})");
			}
		});
	}
	
	private ICompilationUnit createTestTypePublicAttribute() throws Exception {
	
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("   public String foo;");
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypePackageAttribute() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("   String foo;");
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeFinalAttribute() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("   public final String foo;");
				sb.append(CR);
			}
		});
	}

	public void testJavaAttributeAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertEquals(1, resourceAttribute.getAnnotationsSize());
	}

	public void testJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertNotNull(resourceAttribute.getAnnotation(JPA.COLUMN));
	}

	public void testJavaAttributeAnnotationNull() throws Exception {
		ICompilationUnit cu = this.createTestEntity();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertNull(resourceAttribute.getAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first column found
	public void testDuplicateAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestEntityMultipleColumns();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		ColumnAnnotation columnResource = (ColumnAnnotation) resourceAttribute.getAnnotation(JPA.COLUMN);
		assertEquals("FOO", columnResource.getName());
	}

	public void testRemoveColumn() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		resourceAttribute.removeAnnotation(JPA.COLUMN);
		
		assertSourceDoesNotContain("@Column", cu);
	}
	
	public void testRemoveColumnName() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();

		ColumnAnnotation columnResource = (ColumnAnnotation) resourceAttribute.getAnnotation(JPA.COLUMN);
		columnResource.setTable(null);
		assertSourceContains("@Column(name = \"FOO\")", cu);

		columnResource.setName(null);
		assertSourceDoesNotContain("(name", cu);
		assertSourceDoesNotContain("@Column(", cu);
	}
	
	public void testMultipleAttributeMappings() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithIdAndBasic();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		assertEquals(2, resourceAttribute.getAnnotationsSize());
		assertNotNull(resourceAttribute.getAnnotation(JPA.BASIC));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		
		JavaResourceNode javaAttributeMappingAnnotation = resourceAttribute.getAnnotation(BasicAnnotation.ANNOTATION_NAME);
		assertTrue(javaAttributeMappingAnnotation instanceof BasicAnnotation);
		assertSourceContains("@Basic", cu);
		assertSourceContains("@Id", cu);
		
		resourceAttribute.setPrimaryAnnotation(JPA.ONE_TO_MANY, EmptyIterable.<String>instance());
		assertEquals(1, resourceAttribute.getAnnotationsSize());
		javaAttributeMappingAnnotation = resourceAttribute.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertTrue(javaAttributeMappingAnnotation instanceof OneToManyAnnotation);
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@OneToMany", cu);
		assertSourceDoesNotContain("@Basic", cu);
	}
	
	public void testSetJavaAttributeMappingAnnotation() throws Exception {
		ICompilationUnit cu = createTestType();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertEquals(0, resourceAttribute.getAnnotationsSize());
		
		resourceAttribute.setPrimaryAnnotation(JPA.ID, EmptyIterable.<String>instance());
		assertTrue(resourceAttribute.getAnnotation(IdAnnotation.ANNOTATION_NAME) instanceof IdAnnotation);
		assertSourceContains("@Id", cu);
	}

	public void testSetJavaAttributeMappingAnnotation2() throws Exception {
		ICompilationUnit cu = createTestEntityWithColumn();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertEquals(1, resourceAttribute.getAnnotationsSize());
		
		resourceAttribute.setPrimaryAnnotation(JPA.ID, Collections.singleton(ColumnAnnotation.ANNOTATION_NAME));
		assertTrue(resourceAttribute.getAnnotation(IdAnnotation.ANNOTATION_NAME) instanceof IdAnnotation);
		
		assertSourceContains("@Id", cu);
		assertSourceContains("@Column", cu);
	}
	
	public void testSetJavaAttributeMappingAnnotation3() throws Exception {
		ICompilationUnit cu = createTestEntityWithIdColumnGeneratedValue();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertTrue(resourceAttribute.getAnnotation(IdAnnotation.ANNOTATION_NAME) instanceof IdAnnotation);
		
		resourceAttribute.setPrimaryAnnotation(
				JPA.BASIC, 
				Arrays.asList(new String[] {
					ColumnAnnotation.ANNOTATION_NAME,
					GeneratedValueAnnotation.ANNOTATION_NAME}));
		assertTrue(resourceAttribute.getAnnotation(BasicAnnotation.ANNOTATION_NAME) instanceof BasicAnnotation);
		
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@GeneratedValue", cu); //not supported by Basic
		assertSourceContains("@Column", cu); //common between Id and Basic
	}
	
	public void testSetJavaAttributeMappingAnnotationNull() throws Exception {
		ICompilationUnit cu = createTestEntityWithIdColumnGeneratedValue();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertTrue(resourceAttribute.getAnnotation(IdAnnotation.ANNOTATION_NAME) instanceof IdAnnotation);
		
		resourceAttribute.setPrimaryAnnotation(
				null, 
				Arrays.asList(new String[] {
					ColumnAnnotation.ANNOTATION_NAME,
					GeneratedValueAnnotation.ANNOTATION_NAME}));
		
		assertEquals(2, resourceAttribute.getAnnotationsSize());
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@GeneratedValue", cu); //not supported by Basic
		assertSourceContains("@Column", cu); //common between Id and Basic
	}

	public void testAddJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType typeResource = buildJavaResourceType(cu); 

		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		assertSourceDoesNotContain("@Column", cu);
		resourceAttribute.addAnnotation(JPA.COLUMN);
		assertSourceContains("@Column", cu);
	}
	
	public void testRemoveJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedField();
		JavaResourceType typeResource = buildJavaResourceType(cu); 
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		assertSourceContains("@Column", cu);
		resourceAttribute.removeAnnotation(JPA.COLUMN);
		assertSourceDoesNotContain("@Column", cu);
	}
	
	//update source code to change from @Id to @OneToOne and make sure @Column is not removed
	public void testChangeAttributeMappingInSource() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedField();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		final JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		idField(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SourceIdAnnotation.DECLARATION_ANNOTATION_ADAPTER.removeAnnotation(declaration);
			}
		});		
		
		cu.createImport("javax.persistence.OneToOne", null, new NullProgressMonitor());
		
		idField(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SourceOneToOneAnnotation.DECLARATION_ANNOTATION_ADAPTER.newMarkerAnnotation(declaration);
			}
		});		
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ONE_TO_ONE));
		assertSourceContains("@Column", cu);
	}

	public void testJavaAttributeAnnotationsNestable() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		assertEquals(1, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceAttribute.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator().next();
		
		assertEquals("FOO", attributeOverride.getName());
	}
	
	public void testJavaAttributeAnnotationsNoNestable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		assertEquals(0, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}
	
	public void testJavaAttributeAnnotationsContainerNoNestable() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverridesEmpty();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		assertEquals(0, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}
			        
	//			-->>	@AttributeOverride(name="FOO")
	public void testAddJavaAttributeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		assertSourceContains("@AttributeOverride(name = \"FOO\")", cu);
	}
	
	//  @Embedded     				-->>    @Embedded
	//	@AttributeOverride(name="FOO")		@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})	
	public void testAddJavaAttributeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceAttribute.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({ @AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\") })", cu);
		
		assertNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertNotNull(resourceAttribute.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE));
		assertEquals(2, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}
	
	//  @Embedded     				
	//	@AttributeOverrides(@AttributeOverride(name="FOO"))
	//           ||
	//           \/
	//  @Embedded     				
	//	@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})
	public void testAddJavaAttributeAnnotationNestableContainer3() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceAttribute.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"),@AttributeOverride(name = \"BAR\")})", cu);
		
		assertNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertNotNull(resourceAttribute.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE));
		assertEquals(2, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}
	
	public void testAddJavaAttributeAnnotationNestableContainer5() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"),@AttributeOverride(name = \"FOO\")})", cu);
		
		assertNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertNotNull(resourceAttribute.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE));
		assertEquals(2, resourceAttribute.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}

	//@Entity
	//@AttributeOverride(name="FOO")
	public void testRemoveJavaAttributeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		resourceAttribute.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		
		assertSourceDoesNotContain("@AttributeOverride", cu);
	}

	//@Embedded
	//@AttributeOverrides(@AttributeOverride(name = \"FOO\"))
	public void testRemoveJavaAttributeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		resourceAttribute.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		
		assertSourceDoesNotContain("@AttributeOverride(name = \"FOO\")", cu);
		assertSourceContains("@AttributeOverrides", cu);
	}
	
	public void testRemoveJavaAttributeAnnotationIndex() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		resourceAttribute.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		
		assertSourceDoesNotContain("@AttributeOverride(name = \"FOO\"", cu);
		assertSourceContains("@AttributeOverride(name = \"BAR\"", cu);
		assertSourceDoesNotContain("@AttributeOverrides", cu);
	}
	
	public void testRemoveJavaAttributeAnnotationIndex2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();

		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)resourceAttribute.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		resourceAttribute.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAZ\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = typeResource.getFields().iterator().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)resourceAttribute.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		
		resourceAttribute.moveAnnotation(0, 2, JPA.ATTRIBUTE_OVERRIDE);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAZ\"), @AttributeOverride(name = \"FOO\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAR\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation) resourceAttribute.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		resourceAttribute.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"), @AttributeOverride(name = \"FOO\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
	}	
	
	public void testGetTypeName() throws Exception {
		ICompilationUnit cu = createTestEntityWithNonResolvingField();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		JavaResourceAttribute resourceAttribute = resourceType.getFields().iterator().next();
		
		assertEquals("foo", resourceAttribute.getName());
		assertEquals("test.Foo", resourceAttribute.getTypeName()); //bug 196200 changed this

		this.javaProject.createCompilationUnit("test", "Foo.java", "public class Foo {}");
		
		assertEquals("test.Foo", resourceAttribute.getTypeName());
	}
	
	//this tests that we handle mutliple variable declarations in one line.
	//The annotations should apply to all fields defined.  This is not really a useful
	//thing to do with JPA beyond the most basic things that use default column names
	public void testMultipleVariableDeclarationsPerLine() throws Exception {
		ICompilationUnit cu = createTestEntityMultipleVariableDeclarationsPerLine();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		
		assertEquals(4, CollectionTools.size(typeResource.getFields()));
		Iterator<JavaResourceField> fields = typeResource.getFields().iterator();
		JavaResourceField resourceField = fields.next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());

		resourceField = fields.next();
		column = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());
	}
	
	public void testIsPublic() throws Exception {
		ICompilationUnit cu = createTestTypePublicAttribute();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute attribute = typeResource.getFields().iterator().next();
		
		assertTrue(Modifier.isPublic(attribute.getModifiers()));
	}
	
	public void testIsPublicFalse() throws Exception {
		ICompilationUnit cu = createTestTypePackageAttribute();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute attribute = typeResource.getFields().iterator().next();
		
		assertFalse(Modifier.isPublic(attribute.getModifiers()));
	}

	public void testIsFinal() throws Exception {
		ICompilationUnit cu = createTestTypeFinalAttribute();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute attribute = typeResource.getFields().iterator().next();
		
		assertTrue(Modifier.isFinal(attribute.getModifiers()));
	}
	
	public void testIsFinalFalse() throws Exception {
		ICompilationUnit cu = createTestTypePackageAttribute();
		JavaResourceType typeResource = buildJavaResourceType(cu);
		JavaResourceAttribute attribute = typeResource.getFields().iterator().next();
		
		assertFalse(Modifier.isFinal(attribute.getModifiers()));
	}
	
	//TODO add tests for JPTTools static methods
}
