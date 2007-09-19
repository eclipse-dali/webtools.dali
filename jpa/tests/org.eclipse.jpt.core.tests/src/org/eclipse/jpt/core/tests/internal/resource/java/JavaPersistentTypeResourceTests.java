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
import java.util.List;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.NullAnnotationEditFormatter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.Embeddable;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SingularTypeAnnotation;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.core.internal.resource.java.TypeAnnotation;
import org.eclipse.jpt.core.internal.resource.java.TypeMappingAnnotation;
import org.eclipse.jpt.core.internal.resource.java.JpaPlatform;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPersistentTypeResourceTests extends AnnotationTestCase {
	
	public JavaPersistentTypeResourceTests(String name) {
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

	private IType createTestEntityWithEmbeddable() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Embeddable", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Embeddable");
			}
		});
	}
		
//	private IType createTestEntityLarge(final int i) throws Exception {
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY + i);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuffer sb) {
//				sb.append("@Entity" + i);
//				sb.append(CR);
//			}
//			@Override
//			public String typeName() {
//				return TYPE_NAME + i;
//			}
//		});
//	}
//
//	public void testLarge() throws Exception {
//		for (int i = START; i <= END; i++) {
//			this.createAnnotationAndMembers("Entity" +i, "String name();");
//		}
//
//		List<IType> testTypes = new ArrayList<IType>();
//		for (int i = START; i <= END; i++) {
//			testTypes.add(createTestEntityLarge(i));
//		}
//		long start = System.currentTimeMillis();
//		List<JavaPersistentTypeResource> typeResources = new ArrayList<JavaPersistentTypeResource>();
//		for (int i = 0; i < END; i++) {
//			typeResources.add(buildJavaTypeResource(testTypes.get(i))); 
//		}
//		long end = System.currentTimeMillis();
//		
//		System.out.println(end-start + "ms");
//		for (int i = 0; i < END; i++) {
//			assertEquals(1, CollectionTools.size(typeResources.get(i).javaTypeMappingAnnotations()));
//			assertNotNull(typeResources.get(i).javaTypeMappingAnnotation(JPA.ENTITY + (i+1)));
//		}
//		
//
////		assertEquals(0, CollectionTools.size(typeResource.javaTypeAnnotations()));
//	}

	
	
	private IType createTestEntityWithTable() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name=\"FOO\", schema=\"BAR\")");
			}
		});
	}
	
	private IType createTestEntityWithTableAndIdClass() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		this.createAnnotationAndMembers("IdClass", "Class value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE, JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table");
				sb.append(CR);
				sb.append("@IdClass");
			}
		});
	}

	
	private IType createTestEntityMultipleTables() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name();");
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name=\"FOO\")");
				sb.append(CR);
				sb.append("@Table(name=\"BAR\")");
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

	protected JpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}
	
	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = 
			new JavaPersistentTypeResourceImpl(
				new Type(testType, 
					MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER, 
					NullAnnotationEditFormatter.instance()), 
				buildJpaPlatform());
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}
	
	public void testJavaTypeAnnotations() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertEquals(1, CollectionTools.size(typeResource.javaTypeAnnotations()));
	}

	public void testJavaTypeAnnotation() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertNotNull(typeResource.javaTypeAnnotation(JPA.TABLE));
	}

	public void testJavaTypeAnnotationNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertNull(typeResource.javaTypeAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first table found
	public void testDuplicateAnnotations() throws Exception {
		IType testType = this.createTestEntityMultipleTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		Table tableResource = (Table) typeResource.javaTypeAnnotation(JPA.TABLE);
		assertEquals("FOO", tableResource.getName());
	}

	public void testRemoveTable() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		typeResource.removeJavaTypeAnnotation(JPA.TABLE);
		
		assertSourceDoesNotContain("@Table");
	}
	
	public void testRemoveTableName() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 

		Table tableResource = (Table) typeResource.javaTypeAnnotation(JPA.TABLE);
		tableResource.setSchema(null);
		assertSourceContains("@Table(name=\"FOO\")");

		tableResource.setName(null);
		assertSourceDoesNotContain("@Table");
		
		//TODO should I be calling this in the test?  where should the IElementChangedListener be set up?
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType()));
		assertNull(typeResource.javaTypeAnnotation(JPA.TABLE));
	}
	
	public void testMultipleTypeMappings() throws Exception {
		IType testType = this.createTestEntityWithEmbeddable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		assertEquals(2, CollectionTools.size(typeResource.javaTypeMappingAnnotations()));
		assertEquals(0, CollectionTools.size(typeResource.javaTypeAnnotations()));
		assertNotNull(typeResource.javaTypeMappingAnnotation(JPA.EMBEDDABLE));
		assertNotNull(typeResource.javaTypeMappingAnnotation(JPA.ENTITY));
		
		TypeMappingAnnotation javaTypeMappingAnnotation = typeResource.javaTypeMappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof Embeddable);
		assertSourceContains("@Entity");
		assertSourceContains("@Embeddable");
		
		typeResource.setJavaTypeMappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertEquals(1, CollectionTools.size(typeResource.javaTypeMappingAnnotations()));
		javaTypeMappingAnnotation = typeResource.javaTypeMappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof MappedSuperclass);
		//not positive on this being the correct behavior, but wanted to test it so we are conscious of saving it
		assertSourceDoesNotContain("@Entity");
		assertSourceContains("@MappedSuperclass");
		assertSourceDoesNotContain("@Embeddable");
	}
	
	public void testSetJavaTypeMappingAnnotation() throws Exception {
		IType testType = createTestType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertEquals(0, CollectionTools.size(typeResource.javaTypeMappingAnnotations()));
		
		typeResource.setJavaTypeMappingAnnotation(JPA.ENTITY);
		assertTrue(typeResource.javaTypeMappingAnnotation() instanceof Entity);
		assertSourceContains("@Entity");
		
		typeResource.setJavaTypeMappingAnnotation(JPA.ENTITY);
	}

	public void testSetJavaTypeMappingAnnotation2() throws Exception {
		IType testType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertTrue(typeResource.javaTypeMappingAnnotation() instanceof Entity);
		
		typeResource.setJavaTypeMappingAnnotation(JPA.EMBEDDABLE);
		assertTrue(typeResource.javaTypeMappingAnnotation() instanceof Embeddable);
		
		assertSourceDoesNotContain("@Entity");
		assertSourceDoesNotContain("@Table");
	}
	
	public void testSetJavaTypeMappingAnnotation3() throws Exception {
		IType testType = createTestEntityWithTableAndIdClass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertTrue(typeResource.javaTypeMappingAnnotation() instanceof Entity);
		
		typeResource.setJavaTypeMappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertTrue(typeResource.javaTypeMappingAnnotation() instanceof MappedSuperclass);
		
		assertSourceDoesNotContain("@Entity");
		assertSourceDoesNotContain("@Table"); //not supported by MappedSuperclass
		assertSourceContains("@IdClass"); //common between Entity and MappedSuperclass
	}
	
	public void testAddJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		assertSourceDoesNotContain("@Table");
		typeResource.addJavaTypeAnnotation(JPA.TABLE);
	
		assertSourceContains("@Table");
	}
	
	public void testRemoveJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertSourceContains("@Table");
		typeResource.removeJavaTypeAnnotation(JPA.TABLE);
		assertSourceDoesNotContain("@Table");
	}
	
	
	//update source code to change from @Entity to @Embeddable and make sure @Table is not removed
	public void testChangeTypeMappingInSource() throws Exception {
		IType jdtType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		testType().removeAnnotation(typeResource.javaTypeMappingAnnotation().getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("Embeddable", "String name();");
		jdtType.getCompilationUnit().createImport("javax.persistence.Embeddable", null, new NullProgressMonitor());
		
		testType().newMarkerAnnotation(typeResource.jpaPlatform().javaTypeMappingAnnotationProvider(JPA.EMBEDDABLE).getDeclarationAnnotationAdapter());
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType));
		
		
		assertNotNull(typeResource.javaTypeAnnotation(JPA.TABLE));
		assertNull(typeResource.javaTypeMappingAnnotation(JPA.ENTITY));
		assertNotNull(typeResource.javaTypeMappingAnnotation(JPA.EMBEDDABLE));
		assertSourceContains("@Table");
	}
	
	public void testJavaTypeAnnotationsSingular() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(1, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		assertEquals("FOO", secondaryTableResource.getName());
	}
	
	public void testJavaTypeAnnotationsSingularAndPlural() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();	
		assertEquals("BAR", secondaryTableResource.getName());
	}
	
	//  @Entity     -->>    @Entity
	//						@SecondaryTable(name="FOO")
	public void testAddJavaTypeAnnotationSingularPlural() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addJavaTypeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("FOO");
		assertSourceContains("@SecondaryTable(name=\"FOO\")");
	}
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddJavaTypeAnnotationSingularPlural2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addJavaTypeAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	//  @Entity     				
	//	@SecondaryTables(@SecondaryTable(name="FOO"))
	//           ||
	//           \/
	//  @Entity     				
	//	@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})
	public void testAddJavaTypeAnnotationSingularPlural3() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addJavaTypeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testAddJavaTypeAnnotationSingularPlural5() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addJavaTypeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
		
		assertNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	//  @Entity     				
	//	@SecondaryTable(name=\"FOO\")
	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})
	//			 ||
	//           \/
	//  @Entity     				
	//	@SecondaryTable(name=\"FOO\")
	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})
	public void testAddJavaTypeAnnotationSingularPlural4() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addJavaTypeAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable})");
		secondaryTableResource.setName("BOO");
		
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(3, CollectionTools.size(typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		Iterator<TypeAnnotation> secondaryTableAnnotations = typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
		assertEquals("BAR", secondaryTableResource.getName());
		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
		assertEquals("BAZ", secondaryTableResource.getName());
		secondaryTableResource = (SecondaryTable) secondaryTableAnnotations.next();	
		assertEquals("BOO", secondaryTableResource.getName());
		
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})");
	}

	//@Entity
	//@SecondaryTable(name="FOO")
	public void testRemoveJavaTypeAnnotationSingularPlural() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SingularTypeAnnotation secondaryTableAnnotation = (SingularTypeAnnotation) typeResource.javaTypeAnnotation(JPA.SECONDARY_TABLE);
		typeResource.removeJavaTypeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaTypeAnnotationSingularPlural2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SingularTypeAnnotation secondaryTableAnnotation = (SingularTypeAnnotation) typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		typeResource.removeJavaTypeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR"))
	public void testRemoveJavaTypeAnnotationSingularPlural3() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SingularTypeAnnotation secondaryTableAnnotation = (SingularTypeAnnotation) typeResource.javaTypeAnnotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		typeResource.removeJavaTypeAnnotation(secondaryTableAnnotation, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
		assertSourceContains("@SecondaryTable(name=\"BAR\"");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeJavaTypeAnnotation(0, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
		assertSourceContains("@SecondaryTable(name=\"BAR\"");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addJavaTypeAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.removeJavaTypeAnnotation(1, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addJavaTypeAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(2, 0, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addJavaTypeAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(0, 1, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
	}
	
	public void testNestedTypes() throws Exception {
		IType jdtType = createTestEntityWithMemberTypes();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals("only persistable types should be returned by nestedTypes()", 1, CollectionTools.size(persistentType.nestedTypes()));
		
		List<JavaPersistentTypeResource> nestedTypes = (List<JavaPersistentTypeResource>) ClassTools.getFieldValue(persistentType, "nestedTypes");
		
		assertEquals(4, CollectionTools.size(nestedTypes));
		
	}
	
	public void testDuplicateEntityAnnotations() throws Exception {
		IType jdtType = createTestEntityDuplicates();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		Entity javaTypeMappingAnnotation = (Entity) persistentType.javaTypeMappingAnnotation(JPA.ENTITY);
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
		assertEquals(1, CollectionTools.size(persistentType.javaTypeMappingAnnotations()));
		
		javaTypeMappingAnnotation = (Entity) persistentType.javaTypeMappingAnnotation();
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
	}
}
