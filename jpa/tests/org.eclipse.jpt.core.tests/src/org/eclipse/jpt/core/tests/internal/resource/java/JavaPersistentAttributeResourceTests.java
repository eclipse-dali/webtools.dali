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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.NullAnnotationEditFormatter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.MappingAnnotation;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class JavaPersistentAttributeResourceTests extends AnnotationTestCase {
	
	public JavaPersistentAttributeResourceTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
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

	private IType createTestEntityDuplicates() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");

		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity(name=\"FirstEntity\")").append(CR);
				sb.append("@Entity(name=\"SecondEntity\")");
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

	private IType createTestEntityAnnotatedMethod() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Id", "");
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
				sb.append("@Id");
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

	private IType createTestEntityWithSecondaryTable() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\")");
			}
		});
	}
	private IType createTestEntityWithSecondaryTables() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables(@SecondaryTable(name=\"FOO\"))");
			}
		});
	}
	
	private IType createTestEntityWith2SecondaryTables() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
			}
		});
	}
	
	private IType createTestEntityWithSecondaryTableAndSecondaryTables() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\")");
				sb.append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
			}
		});
	}

	private IType createTestEntityWithMemberTypes() throws Exception {
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
			@Override
			public void appendMemberTypeTo(StringBuffer sb) {
				sb.append("     static class FooStatic {}").append(CR);
				sb.append(CR);
				sb.append("     class FooNotStatic {}").append(CR);
				sb.append(CR);
				sb.append("     @interface MyAnnotation {}").append(CR);
				sb.append(CR);
				sb.append("     enum MyEnum {}").append(CR);
			}
		});
	}

	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}
	
	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = 
			new JavaPersistentTypeResourceImpl(
				buildParentResource(buildJpaPlatform()),
				new Type(testType, 
					MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER, 
					NullAnnotationEditFormatter.instance()));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
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
		
		MappingAnnotation javaAttributeMappingAnnotation = attributeResource.mappingAnnotation();
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
	
		idField().removeAnnotation(attributeResource.mappingAnnotation().getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("OneToOne", "");
		jdtType.getCompilationUnit().createImport("javax.persistence.OneToOne", null, new NullProgressMonitor());
		
		idField().newMarkerAnnotation(OneToOne.DECLARATION_ANNOTATION_ADAPTER);
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType));
		
		
		assertNotNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(attributeResource.mappingAnnotation(JPA.ID));
		assertNotNull(attributeResource.mappingAnnotation(JPA.ONE_TO_ONE));
		assertSourceContains("@Column");
	}

	
//TODO persistent attribute tests 
//	public void testJavaTypeAnnotationsSingular() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTable();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		assertEquals(1, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//		
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
//		
//		assertEquals("FOO", secondaryTableResource.getName());
//	}
//	
//	public void testJavaTypeAnnotationsSingularAndPlural() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();	
//		assertEquals("BAR", secondaryTableResource.getName());
//	}
//	
//	//  @Entity     -->>    @Entity
//	//						@SecondaryTable(name="FOO")
//	public void testAddJavaTypeAnnotationSingularPlural() throws Exception {
//		IType jdtType = createTestEntity();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		secondaryTableResource.setName("FOO");
//		assertSourceContains("@SecondaryTable(name=\"FOO\")");
//	}
//	
//	//  @Entity     				-->>    @Entity
//	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
//	public void testAddJavaTypeAnnotationSingularPlural2() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTable();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		secondaryTableResource.setName("BAR");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
//		
//		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//	}
//	
//	//  @Entity     				
//	//	@SecondaryTables(@SecondaryTable(name="FOO"))
//	//           ||
//	//           \/
//	//  @Entity     				
//	//	@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})
//	public void testAddJavaTypeAnnotationSingularPlural3() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		secondaryTableResource.setName("BAR");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
//		
//		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//	}
//	
//	public void testAddJavaTypeAnnotationSingularPlural5() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		secondaryTableResource.setName("BAR");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
//		
//		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//	}
//	
//	//  @Entity     				
//	//	@SecondaryTable(name=\"FOO\")
//	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})
//	//			 ||
//	//           \/
//	//  @Entity     				
//	//	@SecondaryTable(name=\"FOO\")
//	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})
//	public void testAddJavaTypeAnnotationSingularPlural4() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//		
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//
//		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable})");
//		secondaryTableResource.setName("BOO");
//		
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
//		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
//		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
//
//		Iterator<Annotation> secondaryTableAnnotations = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
//		assertEquals("BAR", secondaryTableResource.getName());
//		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
//		assertEquals("BAZ", secondaryTableResource.getName());
//		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
//		assertEquals("BOO", secondaryTableResource.getName());
//		
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})");
//	}
//
//	//@Entity
//	//@SecondaryTable(name="FOO")
//	public void testRemoveJavaTypeAnnotationSingularPlural() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTable();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SingularAnnotation secondaryTableAnnotation = (SingularAnnotation) typeResource.annotation(JPA.SECONDARY_TABLE);
//		typeResource.removeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
//		
//		assertSourceDoesNotContain("@SecondaryTable");
//	}
//	
//
//	//@Entity
//	//@SecondaryTables(@SecondaryTable(name="FOO"))
//	public void testRemoveJavaTypeAnnotationSingularPlural2() throws Exception {
//		IType jdtType = createTestEntityWithSecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SingularAnnotation secondaryTableAnnotation = (SingularAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
//		typeResource.removeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
//		
//		assertSourceDoesNotContain("@SecondaryTable");
//		assertSourceDoesNotContain("@SecondaryTables");
//	}
//	
//	//@Entity
//	//@SecondaryTables(@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR"))
//	public void testRemoveJavaTypeAnnotationSingularPlural3() throws Exception {
//		IType jdtType = createTestEntityWith2SecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SingularAnnotation secondaryTableAnnotation = (SingularAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
//		typeResource.removeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
//		
//		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
//		assertSourceContains("@SecondaryTable(name=\"BAR\"");
//		assertSourceDoesNotContain("@SecondaryTables");
//	}
//	
//	public void testRemoveJavaTypeAnnotationIndex() throws Exception {
//		IType jdtType = createTestEntityWith2SecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLES);
//		
//		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
//		assertSourceContains("@SecondaryTable(name=\"BAR\"");
//		assertSourceDoesNotContain("@SecondaryTables");
//	}
//	
//	public void testRemoveJavaTypeAnnotationIndex2() throws Exception {
//		IType jdtType = createTestEntityWith2SecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		newAnnotation.setName("BAZ");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
//		
//		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLES);
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
//	}
//	
//	public void testMoveJavaTypeAnnotation() throws Exception {
//		IType jdtType = createTestEntityWith2SecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		newAnnotation.setName("BAZ");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
//		
//		typeResource.move(2, 0, JPA.SECONDARY_TABLES);
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
//	}
//	
//	public void testMoveJavaTypeAnnotation2() throws Exception {
//		IType jdtType = createTestEntityWith2SecondaryTables();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
//	
//		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
//		newAnnotation.setName("BAZ");
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
//		
//		typeResource.move(0, 1, JPA.SECONDARY_TABLES);
//		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
//	}
//	
//	public void testNestedTypes() throws Exception {
//		IType jdtType = createTestEntityWithMemberTypes();
//		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
//		
//		assertEquals("only persistable types should be returned by nestedTypes()", 1, CollectionTools.size(persistentType.nestedTypes()));
//		
//		List<JavaPersistentTypeResource> nestedTypes = (List<JavaPersistentTypeResource>) ClassTools.getFieldValue(persistentType, "nestedTypes");
//		
//		assertEquals(4, CollectionTools.size(nestedTypes));
//		
//	}
//	
//	public void testDuplicateEntityAnnotations() throws Exception {
//		IType jdtType = createTestEntityDuplicates();
//		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
//		
//		Entity javaTypeMappingAnnotation = (Entity) persistentType.mappingAnnotation(JPA.ENTITY);
//		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
//		
//		assertEquals(1, CollectionTools.size(persistentType.mappingAnnotations()));
//		
//		javaTypeMappingAnnotation = (Entity) persistentType.mappingAnnotation();
//		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
//		
//	}
	

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
