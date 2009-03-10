/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.IdImpl;
import org.eclipse.jpt.core.internal.resource.java.OneToOneImpl;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.core.utility.jdt.Member.Editor;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaResourcePersistentAttributeTests extends JavaResourceModelTestCase {
	
	public JavaResourcePersistentAttributeTests(String name) {
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
	private ICompilationUnit createTestEntityWithNonResolvingMethod() throws Exception {
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
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
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
	
	private ICompilationUnit createTestEmbeddedWithAttributeOverrideAndAttributeOverrides() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverride(name = \"FOO\")");
				sb.append(CR);
				sb.append("@AttributeOverrides({@AttributeOverride(name = \"BAR\"), @AttributeOverride(name = \"BAZ\")})");
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertEquals(1, attributeResource.supportingAnnotationsSize());
	}

	public void testJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.COLUMN));
	}

	public void testJavaAttributeAnnotationNull() throws Exception {
		ICompilationUnit cu = this.createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNull(attributeResource.getSupportingAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first column found
	public void testDuplicateAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestEntityMultipleColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation columnResource = (ColumnAnnotation) attributeResource.getSupportingAnnotation(JPA.COLUMN);
		assertEquals("FOO", columnResource.getName());
	}

	public void testRemoveColumn() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		attributeResource.removeSupportingAnnotation(JPA.COLUMN);
		
		assertSourceDoesNotContain("@Column", cu);
	}
	
	public void testRemoveColumnName() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		ColumnAnnotation columnResource = (ColumnAnnotation) attributeResource.getSupportingAnnotation(JPA.COLUMN);
		columnResource.setTable(null);
		assertSourceContains("@Column(name = \"FOO\")", cu);

		columnResource.setName(null);
		assertSourceDoesNotContain("@Column", cu);
		
		assertNull(typeResource.getSupportingAnnotation(JPA.TABLE));
	}
	
	public void testMultipleAttributeMappings() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithIdAndBasic();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(2, attributeResource.mappingAnnotationsSize());
		assertEquals(0, attributeResource.supportingAnnotationsSize());
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
		assertNotNull(attributeResource.getMappingAnnotation(JPA.ID));
		
		JavaResourceNode javaAttributeMappingAnnotation = attributeResource.getMappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof BasicAnnotation);
		assertSourceContains("@Basic", cu);
		assertSourceContains("@Id", cu);
		
		attributeResource.setMappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(1, attributeResource.mappingAnnotationsSize());
		javaAttributeMappingAnnotation = attributeResource.getMappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof OneToManyAnnotation);
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@OneToMany", cu);
		assertSourceDoesNotContain("@Basic", cu);
	}
	
	public void testSetJavaAttributeMappingAnnotation() throws Exception {
		ICompilationUnit cu = createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertEquals(0, attributeResource.mappingAnnotationsSize());
		
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		assertSourceContains("@Id", cu);
	}

	public void testSetJavaAttributeMappingAnnotation2() throws Exception {
		ICompilationUnit cu = createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNull(attributeResource.getMappingAnnotation());
		
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		assertSourceContains("@Id", cu);
		assertSourceContains("@Column", cu);
	}
	
	public void testSetJavaAttributeMappingAnnotation3() throws Exception {
		ICompilationUnit cu = createTestEntityWithIdColumnGeneratedValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		attributeResource.setMappingAnnotation(JPA.BASIC);
		assertTrue(attributeResource.getMappingAnnotation() instanceof BasicAnnotation);
		
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@GeneratedValue", cu); //not supported by Basic
		assertSourceContains("@Column", cu); //common between Id and Basic
	}
	
	public void testSetJavaAttributeMappingAnnotationNull() throws Exception {
		ICompilationUnit cu = createTestEntityWithIdColumnGeneratedValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		attributeResource.setMappingAnnotation(null);
		assertNull(attributeResource.getMappingAnnotation());
		
		assertSourceDoesNotContain("@Id", cu);
		assertSourceContains("@GeneratedValue", cu); //not supported by Basic
		assertSourceContains("@Column", cu); //common between Id and Basic
	}

	public void testAddJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertSourceDoesNotContain("@Column", cu);
		attributeResource.addSupportingAnnotation(JPA.COLUMN);
		assertSourceContains("@Column", cu);
	}
	
	public void testRemoveJavaAttributeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertSourceContains("@Column", cu);
		attributeResource.removeSupportingAnnotation(JPA.COLUMN);
		assertSourceDoesNotContain("@Column", cu);
	}
	
	//update source code to change from @Id to @OneToOne and make sure @Column is not removed
	public void testChangeAttributeMappingInSource() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		final JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		idField(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				IdImpl.DECLARATION_ANNOTATION_ADAPTER.removeAnnotation(declaration);
			}
		});		
		
		cu.createImport("javax.persistence.OneToOne", null, new NullProgressMonitor());
		
		idField(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				OneToOneImpl.DECLARATION_ANNOTATION_ADAPTER.newMarkerAnnotation(declaration);
			}
		});		
		
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.COLUMN));
		assertNull(attributeResource.getMappingAnnotation(JPA.ID));
		assertNotNull(attributeResource.getMappingAnnotation(JPA.ONE_TO_ONE));
		assertSourceContains("@Column", cu);
	}

	public void testJavaAttributeAnnotationsNestable() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(1, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();
		
		assertEquals("FOO", attributeOverride.getName());
	}
	
	public void testJavaAttributeAnnotationsNoNestable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(0, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testJavaAttributeAnnotationsContainerNoNestable() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverridesEmpty();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		assertEquals(0, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}

	public void testJavaAttributeAnnotationsNestableAndContainer() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrideAndAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();	
		assertEquals("BAR", attributeOverrideResource.getName());
	}
			        
	//			-->>	@AttributeOverride(name="FOO")
	public void testAddJavaAttributeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		assertSourceContains("@AttributeOverride(name = \"FOO\")", cu);
	}
	
	//  @Embedded     				-->>    @Embedded
	//	@AttributeOverride(name="FOO")		@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})	
	public void testAddJavaAttributeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"),@AttributeOverride(name = \"BAR\")})", cu);
		
		assertNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	//  @Embedded     				
	//	@AttributeOverrides(@AttributeOverride(name="FOO"))
	//           ||
	//           \/
	//  @Embedded     				
	//	@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})
	public void testAddJavaAttributeAnnotationNestableContainer3() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"),@AttributeOverride(name = \"BAR\")})", cu);
		
		assertNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testAddJavaAttributeAnnotationNestableContainer5() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"),@AttributeOverride(name = \"FOO\")})", cu);
		
		assertNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	//  @Embedded     				
	//	@SecondaryTable(name=\"FOO\")
	//  @AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\")})
	//			 ||
	//           \/
	//  @Embedded     				
	//	@AttributeOverride(name=\"FOO\")
	//  @AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\"), @AttributeOverride(name=\"BOO\")})
	public void testAddJavaAttributeAnnotationNestableContainer4() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrideAndAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"), @AttributeOverride(name = \"BAZ\"),", cu);
		assertSourceContains("@AttributeOverride})", cu);
		attributeOverride.setName("BOO");
		
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getSupportingAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(3, CollectionTools.size(attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		Iterator<NestableAnnotation> attributeOverrideAnnotations = attributeResource.supportingAnnotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BAR", attributeOverride.getName());
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BAZ", attributeOverride.getName());
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BOO", attributeOverride.getName());
		
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"), @AttributeOverride(name = \"BAZ\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BOO\")})", cu);
	}

	//@Entity
	//@AttributeOverride(name="FOO")
	public void testRemoveJavaAttributeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride", cu);
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaAttributeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride", cu);
		assertSourceDoesNotContain("@AttributeOverrides", cu);
	}
	
	public void testRemoveJavaAttributeAnnotationIndex() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeSupportingAnnotation(0, JPA.ATTRIBUTE_OVERRIDE , JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride(name = \"FOO\"", cu);
		assertSourceContains("@AttributeOverride(name = \"BAR\"", cu);
		assertSourceDoesNotContain("@AttributeOverrides", cu);
	}
	
	public void testRemoveJavaAttributeAnnotationIndex2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)attributeResource.addSupportingAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		attributeResource.removeSupportingAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAZ\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)attributeResource.addSupportingAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		
		attributeResource.moveSupportingAnnotation(0, 2, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAZ\"), @AttributeOverride(name = \"FOO\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAR\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		ICompilationUnit cu = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation) attributeResource.addSupportingAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"FOO\"), @AttributeOverride(name = \"BAR\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
		
		attributeResource.moveSupportingAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name = \"BAR\"), @AttributeOverride(name = \"FOO\"),", cu);
		assertSourceContains("@AttributeOverride(name = \"BAZ\")})", cu);
	}	

	//more detailed tests in JPTToolsTests
	public void testIsPersistableField() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertTrue(attributeResource.isPersistable());
	}
	
	public void testIsPersistableField2() throws Exception {
		ICompilationUnit cu = createTestEntityWithNonResolvingField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		@SuppressWarnings("unchecked")
		List<JavaResourcePersistentAttribute> attributes = (List<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "fields");
		JavaResourcePersistentAttribute attributeResource = attributes.get(0);
		
		assertEquals("foo", attributeResource.getName());
		assertTrue(attributeResource.isField());
		assertTrue(attributeResource.isPersistable()); //bug 196200 changed this

		this.javaProject.createCompilationUnit("test", "Foo.java", "public class Foo {}");
		
		assertTrue(attributeResource.isPersistable());
	}
	
	public void testGetTypeName() throws Exception {
		ICompilationUnit cu = createTestEntityWithNonResolvingField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		@SuppressWarnings("unchecked")
		List<JavaResourcePersistentAttribute> attributes = (List<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "fields");
		JavaResourcePersistentAttribute attributeResource = attributes.get(0);
		
		assertEquals("foo", attributeResource.getName());
		assertEquals("test.Foo", attributeResource.getTypeName()); //bug 196200 changed this

		this.javaProject.createCompilationUnit("test", "Foo.java", "public class Foo {}");
		
		assertEquals("test.Foo", attributeResource.getTypeName());
	}
	
	
	//more detailed tests in JPTToolsTests
	public void testIsPersistableMethod() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableProperties().next();
		
		assertTrue(attributeResource.isPersistable());		
	}
	
	public void testIsPersistableMethod2() throws Exception {
		ICompilationUnit cu = createTestEntityWithNonResolvingMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		@SuppressWarnings("unchecked")
		List<JavaResourcePersistentAttribute> attributes = (List<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "methods");
		JavaResourcePersistentAttribute attributeResource = attributes.get(0);
		
		assertEquals("foo", attributeResource.getName());
		assertTrue(attributeResource.isProperty());
		assertTrue(attributeResource.isPersistable());//bug 196200 changed this

		this.javaProject.createCompilationUnit("test", "Foo.java", "public class Foo {}");
		
		assertTrue(attributeResource.isPersistable());
	}
	
	//this tests that we handle mutliple variable declarations in one line.
	//The annotations should apply to all fields defined.  This is not really a useful
	//thing to do with JPA beyond the most basic things that use default column names
	public void testMultipleVariableDeclarationsPerLine() throws Exception {
		ICompilationUnit cu = createTestEntityMultipleVariableDeclarationsPerLine();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		assertEquals(4, CollectionTools.size(typeResource.fields()));
		Iterator<JavaResourcePersistentAttribute> fields = typeResource.fields();
		JavaResourcePersistentAttribute attributeResource = fields.next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getSupportingAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());

		attributeResource = fields.next();
		column = (ColumnAnnotation) attributeResource.getSupportingAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());
	}
	
	public void testIsPublic() throws Exception {
		ICompilationUnit cu = createTestTypePublicAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attribute = typeResource.persistableAttributes().next();
		
		assertTrue(Modifier.isPublic(attribute.getModifiers()));
	}
	
	public void testIsPublicFalse() throws Exception {
		ICompilationUnit cu = createTestTypePackageAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attribute = typeResource.persistableAttributes().next();
		
		assertFalse(Modifier.isPublic(attribute.getModifiers()));
	}

	public void testIsFinal() throws Exception {
		ICompilationUnit cu = createTestTypeFinalAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attribute = typeResource.persistableAttributes().next();
		
		assertTrue(Modifier.isFinal(attribute.getModifiers()));
	}
	
	public void testIsFinalFalse() throws Exception {
		ICompilationUnit cu = createTestTypePackageAttribute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attribute = typeResource.persistableAttributes().next();
		
		assertFalse(Modifier.isFinal(attribute.getModifiers()));
	}
	
	//TODO add tests for JPTTools static methods
}
