/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
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
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class JavaResourcePersistentAttributeTests extends JavaResourceModelTestCase {
	
	public JavaResourcePersistentAttributeTests(String name) {
		super(name);
	}
		
	private IType createTestEntity() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");

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
	
	private IType createTestEntityWithNonResolvingField() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");

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
	private IType createTestEntityWithNonResolvingMethod() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");

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

	private IType createTestEntityMultipleVariableDeclarationsPerLine() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Column", "String name();");
	
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
				sb.append("@Column(name=\"baz\")");
				sb.append("    private String foo, bar;").append(CR);
				sb.append(CR);
			}
		});
	}

	private IType createTestEntityWithIdAndBasic() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Id", "");
		this.createAnnotationAndMembers("Basic", "");
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
	
	private IType createTestEntityAnnotatedField() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Column", "String name();");
		this.createAnnotationAndMembers("Id", "String name();");
	
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
	
	private IType createTestEntityWithColumn() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Column", "String name(); String table();");
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
				sb.append("@Column(name=\"FOO\", table=\"MY_TABLE\")");
			}
		});
	}
	
	private IType createTestEntityWithIdColumnGeneratedValue() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Column", "String name(); String table();");
		this.createAnnotationAndMembers("GeneratedValue", "");
		this.createAnnotationAndMembers("Id", "");
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

	
	private IType createTestEntityMultipleColumns() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Column", "String name();");
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
				sb.append("@Column(name=\"FOO\")");
				sb.append(CR);
				sb.append("@Column(name=\"BAR\")");
			}
		});
	}

	private IType createTestEmbeddedWithAttributeOverride() throws Exception {
		this.createAnnotationAndMembers("Embedded", "String name();");
		this.createAnnotationAndMembers("AttributeOverride", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverride(name=\"FOO\")");
			}
		});
	}
	private IType createTestEmbeddedWithAttributeOverrides() throws Exception {
		this.createAnnotationAndMembers("Embedded", "String name();");
		this.createAnnotationAndMembers("AttributeOverride", "String name();");
		this.createAnnotationAndMembers("AttributeOverrides", "AttributeOverride[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverrides(@AttributeOverride(name=\"FOO\"))");
			}
		});
	}
	private IType createTestEmbeddedWithAttributeOverridesEmpty() throws Exception {
		this.createAnnotationAndMembers("Embedded", "String name();");
		this.createAnnotationAndMembers("AttributeOverrides", "AttributeOverride[] value();");
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
	
	private IType createTestEmbeddedWith2AttributeOverrides() throws Exception {
		this.createAnnotationAndMembers("Embedded", "String name();");
		this.createAnnotationAndMembers("AttributeOverride", "String name();");
		this.createAnnotationAndMembers("AttributeOverrides", "AttributeOverride[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\")})");
			}
		});
	}
	
	private IType createTestEmbeddedWithAttributeOverrideAndAttributeOverrides() throws Exception {
		this.createAnnotationAndMembers("Embedded", "String name();");
		this.createAnnotationAndMembers("AttributeOverride", "String name();");
		this.createAnnotationAndMembers("AttributeOverrides", "AttributeOverride[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded");
				sb.append(CR);
				sb.append("@AttributeOverride(name=\"FOO\")");
				sb.append(CR);
				sb.append("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\")})");
			}
		});
	}
	
	public void testJavaAttributeAnnotations() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertEquals(1, attributeResource.annotationsSize());
	}

	public void testJavaAttributeAnnotation() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.getAnnotation(JPA.COLUMN));
	}

	public void testJavaAttributeAnnotationNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNull(attributeResource.getAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first column found
	public void testDuplicateAnnotations() throws Exception {
		IType testType = this.createTestEntityMultipleColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation columnResource = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertEquals("FOO", columnResource.getName());
	}

	public void testRemoveColumn() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		attributeResource.removeAnnotation(JPA.COLUMN);
		
		assertSourceDoesNotContain("@Column");
	}
	
	public void testRemoveColumnName() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		ColumnAnnotation columnResource = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		columnResource.setTable(null);
		assertSourceContains("@Column(name=\"FOO\")");

		columnResource.setName(null);
		assertSourceDoesNotContain("@Column");
		
		assertNull(typeResource.getAnnotation(JPA.TABLE));
	}
	
	public void testMultipleAttributeMappings() throws Exception {
		IType testType = this.createTestEntityWithIdAndBasic();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(2, attributeResource.mappingAnnotationsSize());
		assertEquals(0, attributeResource.annotationsSize());
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
		assertNotNull(attributeResource.getMappingAnnotation(JPA.ID));
		
		JavaResourceNode javaAttributeMappingAnnotation = attributeResource.getMappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof BasicAnnotation);
		assertSourceContains("@Basic");
		assertSourceContains("@Id");
		
		this.createAnnotationAndMembers("OneToMany", "");
		attributeResource.setMappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(1, attributeResource.mappingAnnotationsSize());
		javaAttributeMappingAnnotation = attributeResource.getMappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof OneToManyAnnotation);
		assertSourceDoesNotContain("@Id");
		assertSourceContains("@OneToMany");
		assertSourceDoesNotContain("@Basic");
	}
	
	public void testSetJavaAttributeMappingAnnotation() throws Exception {
		IType testType = createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertEquals(0, attributeResource.mappingAnnotationsSize());
		
		this.createAnnotationAndMembers("Id", "");
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		assertSourceContains("@Id");
	}

	public void testSetJavaAttributeMappingAnnotation2() throws Exception {
		IType testType = createTestEntityWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNull(attributeResource.getMappingAnnotation());
		
		this.createAnnotationAndMembers("Id", "");
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		assertSourceContains("@Id");
		assertSourceContains("@Column");
	}
	
	public void testSetJavaAttributeMappingAnnotation3() throws Exception {
		IType testType = createTestEntityWithIdColumnGeneratedValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		this.createAnnotationAndMembers("Basic", "");
		attributeResource.setMappingAnnotation(JPA.BASIC);
		assertTrue(attributeResource.getMappingAnnotation() instanceof BasicAnnotation);
		
		assertSourceDoesNotContain("@Id");
		assertSourceContains("@GeneratedValue"); //not supported by Basic
		assertSourceContains("@Column"); //common between Id and Basic
	}
	
	public void testSetJavaAttributeMappingAnnotationNull() throws Exception {
		IType testType = createTestEntityWithIdColumnGeneratedValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.getMappingAnnotation() instanceof IdAnnotation);
		
		attributeResource.setMappingAnnotation(null);
		assertNull(attributeResource.getMappingAnnotation());
		
		assertSourceDoesNotContain("@Id");
		assertSourceContains("@GeneratedValue"); //not supported by Basic
		assertSourceContains("@Column"); //common between Id and Basic
	}

	public void testAddJavaAttributeAnnotation() throws Exception {
		IType testType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 

		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertSourceDoesNotContain("@Column");
		attributeResource.addAnnotation(JPA.COLUMN);
		assertSourceContains("@Column");
	}
	
	public void testRemoveJavaAttributeAnnotation() throws Exception {
		IType testType = createTestEntityAnnotatedField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertSourceContains("@Column");
		attributeResource.removeAnnotation(JPA.COLUMN);
		assertSourceDoesNotContain("@Column");
	}
	
	
	//update source code to change from @Id to @OneToOne and make sure @Column is not removed
	public void testChangeAttributeMappingInSource() throws Exception {
		IType jdtType = createTestEntityAnnotatedField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		idField().removeAnnotation(((IdImpl) attributeResource.getMappingAnnotation()).getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("OneToOne", "");
		jdtType.getCompilationUnit().createImport("javax.persistence.OneToOne", null, new NullProgressMonitor());
		
		idField().newMarkerAnnotation(OneToOneImpl.DECLARATION_ANNOTATION_ADAPTER);
		
		assertNotNull(attributeResource.getAnnotation(JPA.COLUMN));
		assertNull(attributeResource.getMappingAnnotation(JPA.ID));
		assertNotNull(attributeResource.getMappingAnnotation(JPA.ONE_TO_ONE));
		assertSourceContains("@Column");
	}

	public void testJavaAttributeAnnotationsNestable() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(1, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();
		
		assertEquals("FOO", attributeOverride.getName());
	}
	
	public void testJavaAttributeAnnotationsNoNestable() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertEquals(0, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testJavaAttributeAnnotationsContainerNoNestable() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverridesEmpty();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		assertEquals(0, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}

	public void testJavaAttributeAnnotationsNestableAndContainer() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrideAndAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();	
		assertEquals("BAR", attributeOverrideResource.getName());
	}
			        
	//			-->>	@AttributeOverride(name="FOO")
	public void testAddJavaAttributeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		assertSourceContains("@AttributeOverride(name=\"FOO\")");
	}
	
	//  @Embedded     				-->>    @Embedded
	//	@AttributeOverride(name="FOO")		@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})	
	public void testAddJavaAttributeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		this.createAnnotationAndMembers("AttributeOverrides", "String name();");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"),@AttributeOverride(name=\"BAR\")})");
		
		assertNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	//  @Embedded     				
	//	@AttributeOverrides(@AttributeOverride(name="FOO"))
	//           ||
	//           \/
	//  @Embedded     				
	//	@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})
	public void testAddJavaAttributeAnnotationNestableContainer3() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"),@AttributeOverride(name=\"BAR\")})");
		
		assertNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testAddJavaAttributeAnnotationNestableContainer5() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"),@AttributeOverride(name=\"FOO\")})");
		
		assertNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
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
		IType jdtType = createTestEmbeddedWithAttributeOverrideAndAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\"),");
		assertSourceContains("@AttributeOverride})");
		attributeOverride.setName("BOO");
		
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		Iterator<JavaResourceNode> attributeOverrideAnnotations = attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BAR", attributeOverride.getName());
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BAZ", attributeOverride.getName());
		attributeOverride = (AttributeOverrideAnnotation) attributeOverrideAnnotations.next();	
		assertEquals("BOO", attributeOverride.getName());
		
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\"),");
		assertSourceContains("@AttributeOverride(name=\"BOO\")})");
	}

	//@Entity
	//@AttributeOverride(name="FOO")
	public void testRemoveJavaAttributeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride");
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaAttributeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride");
		assertSourceDoesNotContain("@AttributeOverrides");
	}
	
	public void testRemoveJavaAttributeAnnotationIndex() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE , JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"FOO\"");
		assertSourceContains("@AttributeOverride(name=\"BAR\"");
		assertSourceDoesNotContain("@AttributeOverrides");
	}
	
	public void testRemoveJavaAttributeAnnotationIndex2() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		attributeResource.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAZ\")})");
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation)attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		
		attributeResource.move(0, 2, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAZ\"), @AttributeOverride(name=\"FOO\"),");
		assertSourceContains("@AttributeOverride(name=\"BAR\")})");
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
	
		AttributeOverrideAnnotation newAnnotation = (AttributeOverrideAnnotation) attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		attributeResource.move(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"FOO\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
	}	

	//more detailed tests in JPTToolsTests
	public void testIsPersistableField() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertTrue(attributeResource.isPersistable());
	}
	
	public void testIsPersistableField2() throws Exception {
		IType jdtType = createTestEntityWithNonResolvingField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		Collection<JavaResourcePersistentAttribute> attributes = (Collection<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "attributes");
		JavaResourcePersistentAttribute attributeResource = attributes.iterator().next();
		
		assertEquals("foo", attributeResource.getName());
		assertTrue(attributeResource.isForField());
		assertTrue(attributeResource.isPersistable()); //bug 196200 changed this

		this.javaProject.createType("test", "Foo.java", "public class Foo {}");
		this.javaResourceModel.resolveTypes();
		
		assertTrue(attributeResource.isPersistable());
	}
	
	public void testGetQualifiedTypeName() throws Exception {
		IType jdtType = createTestEntityWithNonResolvingField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		Collection<JavaResourcePersistentAttribute> attributes = (Collection<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "attributes");
		JavaResourcePersistentAttribute attributeResource = attributes.iterator().next();
		
		assertEquals("foo", attributeResource.getName());
		assertEquals("test.Foo", attributeResource.getQualifiedTypeName()); //bug 196200 changed this

		this.javaProject.createType("test", "Foo.java", "public class Foo {}");
		this.javaResourceModel.resolveTypes();
		
		assertEquals("test.Foo", attributeResource.getQualifiedTypeName());
	}
	
	
	//more detailed tests in JPTToolsTests
	public void testIsPersistableMethod() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.properties().next();
		
		assertTrue(attributeResource.isPersistable());		
	}
	
	public void testIsPersistableMethod2() throws Exception {
		IType jdtType = createTestEntityWithNonResolvingMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		Collection<JavaResourcePersistentAttribute> attributes = (Collection<JavaResourcePersistentAttribute>) ClassTools.fieldValue(typeResource, "attributes");
		JavaResourcePersistentAttribute attributeResource = (JavaResourcePersistentAttribute) attributes.toArray()[3];
		
		assertEquals("foo", attributeResource.getName());
		assertTrue(attributeResource.isForProperty());
		assertTrue(attributeResource.isPersistable());//bug 196200 changed this

		this.javaProject.createType("test", "Foo.java", "public class Foo {}");
		this.javaResourceModel.resolveTypes();
		
		assertTrue(attributeResource.isPersistable());
	}
	
	//this tests that we handle mutliple variable declarations in one line.
	//The annotations should apply to all fields defined.  This is not really a useful
	//thing to do with JPA beyond the most basic things that use default column names
	public void testMultipleVariableDeclarationsPerLine() throws Exception {
		IType jdtType = createTestEntityMultipleVariableDeclarationsPerLine();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(4, CollectionTools.size(typeResource.fields()));
		Iterator<JavaResourcePersistentAttribute> fields = typeResource.fields();
		JavaResourcePersistentAttribute attributeResource = fields.next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());

		attributeResource = fields.next();
		column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertEquals("baz", column.getName());
	
	}
	//TODO add tests for JPTTools static methods
}
