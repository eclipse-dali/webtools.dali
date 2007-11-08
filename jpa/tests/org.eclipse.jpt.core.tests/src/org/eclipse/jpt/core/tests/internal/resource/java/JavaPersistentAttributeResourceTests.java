/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.IdImpl;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.internal.resource.java.OneToOneImpl;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class JavaPersistentAttributeResourceTests extends JavaResourceModelTestCase {
	
	public JavaPersistentAttributeResourceTests(String name) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertEquals(1, CollectionTools.size(attributeResource.annotations()));
	}

	public void testJavaAttributeAnnotation() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.annotation(JPA.COLUMN));
	}

	public void testJavaAttributeAnnotationNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertNull(attributeResource.annotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first column found
	public void testDuplicateAnnotations() throws Exception {
		IType testType = this.createTestEntityMultipleColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column columnResource = (Column) attributeResource.annotation(JPA.COLUMN);
		assertEquals("FOO", columnResource.getName());
	}

	public void testRemoveColumn() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		attributeResource.removeAnnotation(JPA.COLUMN);
		
		assertSourceDoesNotContain("@Column");
	}
	
	public void testRemoveColumnName() throws Exception {
		IType testType = this.createTestEntityWithColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();

		Column columnResource = (Column) attributeResource.annotation(JPA.COLUMN);
		columnResource.setTable(null);
		assertSourceContains("@Column(name=\"FOO\")");

		columnResource.setName(null);
		assertSourceDoesNotContain("@Column");
		
		//TODO should I be calling this in the test?  where should the IElementChangedListener be set up?
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType()));
		assertNull(typeResource.annotation(JPA.TABLE));
	}
	
	public void testMultipleAttributeMappings() throws Exception {
		IType testType = this.createTestEntityWithIdAndBasic();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertEquals(2, CollectionTools.size(attributeResource.mappingAnnotations()));
		assertEquals(0, CollectionTools.size(attributeResource.annotations()));
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
		assertNotNull(attributeResource.mappingAnnotation(JPA.ID));
		
		JavaResource javaAttributeMappingAnnotation = attributeResource.mappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof Basic);
		assertSourceContains("@Basic");
		assertSourceContains("@Id");
		
		attributeResource.setMappingAnnotation(JPA.ONE_TO_MANY);
		assertEquals(1, CollectionTools.size(attributeResource.mappingAnnotations()));
		javaAttributeMappingAnnotation = attributeResource.mappingAnnotation();
		assertTrue(javaAttributeMappingAnnotation instanceof OneToMany);
		assertSourceDoesNotContain("@Id");
		assertSourceContains("@OneToMany");
		assertSourceDoesNotContain("@Basic");
	}
	
	public void testSetJavaAttributeMappingAnnotation() throws Exception {
		IType testType = createTestType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertEquals(0, CollectionTools.size(attributeResource.mappingAnnotations()));
		
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.mappingAnnotation() instanceof Id);
		assertSourceContains("@Id");
		
		attributeResource.setMappingAnnotation(JPA.ID);
		//TODO need to test behavior here, throw an exception, remove the old @Id and replace it, 
		//thus clearing out any annotation elements??
	}

	public void testSetJavaAttributeMappingAnnotation2() throws Exception {
		IType testType = createTestEntityWithColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertNull(attributeResource.mappingAnnotation());
		
		attributeResource.setMappingAnnotation(JPA.ID);
		assertTrue(attributeResource.mappingAnnotation() instanceof Id);
		
		assertSourceContains("@Id");
		assertSourceContains("@Column");
	}
	
	public void testSetJavaAttributeMappingAnnotation3() throws Exception {
		IType testType = createTestEntityWithIdColumnGeneratedValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.mappingAnnotation() instanceof Id);
		
		attributeResource.setMappingAnnotation(JPA.BASIC);
		assertTrue(attributeResource.mappingAnnotation() instanceof Basic);
		
		assertSourceDoesNotContain("@Id");
		assertSourceDoesNotContain("@GeneratedValue"); //not supported by Basic
		assertSourceContains("@Column"); //common between Id and Basic
	}
	
	public void testSetJavaAttributeMappingAnnotationNull() throws Exception {
		IType testType = createTestEntityWithIdColumnGeneratedValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertTrue(attributeResource.mappingAnnotation() instanceof Id);
		
		attributeResource.setMappingAnnotation(null);
		assertNull(attributeResource.mappingAnnotation());
		
		assertSourceDoesNotContain("@Id");
		assertSourceDoesNotContain("@GeneratedValue"); //not supported by Basic
		assertSourceDoesNotContain("@Column"); //common between Id and Basic
	}

	public void testAddJavaAttributeAnnotation() throws Exception {
		IType testType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 

		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertSourceDoesNotContain("@Column");
		attributeResource.addAnnotation(JPA.COLUMN);
		assertSourceContains("@Column");
	}
	
	public void testRemoveJavaAttributeAnnotation() throws Exception {
		IType testType = createTestEntityAnnotatedField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertSourceContains("@Column");
		attributeResource.removeAnnotation(JPA.COLUMN);
		assertSourceDoesNotContain("@Column");
	}
	
	
	//update source code to change from @Id to @OneToOne and make sure @Column is not removed
	public void testChangeAttributeMappingInSource() throws Exception {
		IType jdtType = createTestEntityAnnotatedField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		idField().removeAnnotation(((IdImpl) attributeResource.mappingAnnotation()).getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("OneToOne", "");
		jdtType.getCompilationUnit().createImport("javax.persistence.OneToOne", null, new NullProgressMonitor());
		
		idField().newMarkerAnnotation(OneToOneImpl.DECLARATION_ANNOTATION_ADAPTER);
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType));
		
		
		assertNotNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(attributeResource.mappingAnnotation(JPA.ID));
		assertNotNull(attributeResource.mappingAnnotation(JPA.ONE_TO_ONE));
		assertSourceContains("@Column");
	}

	public void testJavaAttributeAnnotationsNestable() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertEquals(1, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
		
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();
		
		assertEquals("FOO", attributeOverride.getName());
	}
	
	public void testJavaAttributeAnnotationsNoNestable() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertEquals(0, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testJavaAttributeAnnotationsContainerNoNestable() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverridesEmpty();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		assertEquals(0, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}

	public void testJavaAttributeAnnotationsNestableAndContainer() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrideAndAttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverride attributeOverrideResource = (AttributeOverride) attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();	
		assertEquals("BAR", attributeOverrideResource.getName());
	}
			        
	//			-->>	@AttributeOverride(name="FOO")
	public void testAddJavaAttributeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		assertSourceContains("@AttributeOverride(name=\"FOO\")");
	}
	
	//  @Embedded     				-->>    @Embedded
	//	@AttributeOverride(name="FOO")		@AttributeOverrides({@AttributeOverride(name="FOO"), @AttributeOverride(name="BAR")})	
	public void testAddJavaAttributeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"),@AttributeOverride(name=\"BAR\")})");
		
		assertNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
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
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"),@AttributeOverride(name=\"BAR\")})");
		
		assertNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testAddJavaAttributeAnnotationNestableContainer5() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"),@AttributeOverride(name=\"FOO\")})");
		
		assertNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
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
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\"),");
		assertSourceContains("@AttributeOverride})");
		attributeOverride.setName("BOO");
		
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));

		Iterator<JavaResource> attributeOverrideAnnotations = attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride = (AttributeOverride) attributeOverrideAnnotations.next();	
		assertEquals("BAR", attributeOverride.getName());
		attributeOverride = (AttributeOverride) attributeOverrideAnnotations.next();	
		assertEquals("BAZ", attributeOverride.getName());
		attributeOverride = (AttributeOverride) attributeOverrideAnnotations.next();	
		assertEquals("BOO", attributeOverride.getName());
		
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"BAZ\"),");
		assertSourceContains("@AttributeOverride(name=\"BOO\")})");
	}

	//@Entity
	//@AttributeOverride(name="FOO")
	public void testRemoveJavaAttributeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverride();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride");
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaAttributeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEmbeddedWithAttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride");
		assertSourceDoesNotContain("@AttributeOverrides");
	}
	
	public void testRemoveJavaAttributeAnnotationIndex() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE , JPA.ATTRIBUTE_OVERRIDES);
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"FOO\"");
		assertSourceContains("@AttributeOverride(name=\"BAR\"");
		assertSourceDoesNotContain("@AttributeOverrides");
	}
	
	public void testRemoveJavaAttributeAnnotationIndex2() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();

		AttributeOverride newAnnotation = (AttributeOverride)attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		attributeResource.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAZ\")})");
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		AttributeOverride newAnnotation = (AttributeOverride)attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		
		attributeResource.move(2, 0, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAZ\"), @AttributeOverride(name=\"FOO\"),");
		assertSourceContains("@AttributeOverride(name=\"BAR\")})");
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		IType jdtType = createTestEmbeddedWith2AttributeOverrides();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
	
		AttributeOverride newAnnotation = (AttributeOverride) attributeResource.addAnnotation(2, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\"), @AttributeOverride(name=\"BAR\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
		
		attributeResource.move(0, 1, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAR\"), @AttributeOverride(name=\"FOO\"),");
		assertSourceContains("@AttributeOverride(name=\"BAZ\")})");
	}	

	//more detailed tests in JPTToolsTests
	public void testIsPersistableField() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		assertTrue(attributeResource.isPersistable());
	}
	
	//more detailed tests in JPTToolsTests
	public void testIsPersistableMethod() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.properties().next();
		
		assertTrue(attributeResource.isPersistable());		
	}
	
	//this tests that we handle mutliple variable declarations in one line.
	//The annotations should apply to all fields defined.  This is not really a useful
	//thing to do with JPA beyond the most basic things that use default column names
	public void testMultipleVariableDeclarationsPerLine() throws Exception {
		IType jdtType = createTestEntityMultipleVariableDeclarationsPerLine();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(4, CollectionTools.size(typeResource.fields()));
		Iterator<JavaPersistentAttributeResource> fields = typeResource.fields();
		JavaPersistentAttributeResource attributeResource = fields.next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertEquals("baz", column.getName());

		attributeResource = fields.next();
		column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertEquals("baz", column.getName());
	
	}
	//TODO add tests for JPTTools static methods
}
