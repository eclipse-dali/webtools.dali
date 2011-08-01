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

import java.util.Iterator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement.Editor;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceEmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceEntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.resource.java.JpaJavaResourceModelTestCase;

@SuppressWarnings("nls")
public class JavaResourceTypeTests extends JpaJavaResourceModelTestCase {
	
	public JavaResourceTypeTests(String name) {
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
	
	private ICompilationUnit createTestInvalidAnnotations() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>("javax.persistence.Foo");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Foo");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Foo");
			}
		});
	}

	private ICompilationUnit createTestEntityWithMemberEmbeddable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendMemberTypeTo(StringBuilder sb) {
				sb.append("     @Embeddable");
				sb.append(CR);
				sb.append("     public static class Foo { }").append(CR);

			}
		});
	}

	private ICompilationUnit createTestEntityDuplicates() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name = \"FirstEntity\")").append(CR);
				sb.append("@Entity(name = \"SecondEntity\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithEmbeddable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Embeddable");
			}
		});
	}


//	private ICompilationUnit createTestEntityLarge(final int i) throws Exception {
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY + i);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
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
//		List<ICompilationUnit> cus = new ArrayList<ICompilationUnit>();
//		for (int i = START; i <= END; i++) {
//			cus.add(createTestEntityLarge(i));
//		}
//		long start = System.currentTimeMillis();
//		List<JavaPersistentresourceType> resourceTypes = new ArrayList<JavaPersistentresourceType>();
//		for (int i = 0; i < END; i++) {
//			resourceTypes.add(buildJavaresourceType(cus.get(i))); 
//		}
//		long end = System.currentTimeMillis();
//		
//		System.out.println(end-start + "ms");
//		for (int i = 0; i < END; i++) {
//			assertEquals(1, CollectionTools.size(resourceTypes.get(i).javaTypeMappingAnnotations()));
//			assertNotNull(resourceTypes.get(i).javaTypeMappingAnnotation(JPA.ENTITY + (i+1)));
//		}
//		
//
////		assertEquals(0, CollectionTools.size(resourceType.javaTypeAnnotations()));
//	}

	
	
	private ICompilationUnit createTestEntityWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name = \"FOO\", schema = \"BAR\")");
			}
		});
	}
	
//	private ICompilationUnit createTestEntityWithTableAndIdClass() throws Exception {
//		createEntityAnnotation();
//		this.createAnnotationAndMembers("Table", "String name(); String schema();");
//		this.createAnnotationAndMembers("IdClass", "Class value();");
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE, JPA.ID_CLASS);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@Entity");
//				sb.append(CR);
//				sb.append("@Table");
//				sb.append(CR);
//				sb.append("@IdClass");
//			}
//		});
//	}
//
	
	private ICompilationUnit createTestEntityMultipleTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name = \"FOO\")");
				sb.append(CR);
				sb.append("@Table(name = \"BAR\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithSecondaryTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name = \"FOO\")");
			}
		});
	}
	private ICompilationUnit createTestEntityWithEmptySecondaryTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables()");
			}
		});
	}

	private ICompilationUnit createTestEntityWithSecondaryTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables(@SecondaryTable(name = \"FOO\"))");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWith2SecondaryTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\")})");
			}
		});
	}

	private ICompilationUnit createTestEntityWithMemberTypes() throws Exception {
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
			public void appendMemberTypeTo(StringBuilder sb) {
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
	
	public void testJavaTypeAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertEquals(2, resourceType.getAnnotationsSize());
	}

	public void testJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertNotNull(resourceType.getAnnotation(JPA.TABLE));
	}

	public void testJavaTypeAnnotationNull() throws Exception {
		ICompilationUnit cu = this.createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertNull(resourceType.getAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first table found
	public void testDuplicateAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestEntityMultipleTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		TableAnnotation tableResource = (TableAnnotation) resourceType.getAnnotation(JPA.TABLE);
		assertEquals("FOO", tableResource.getName());
	}

	public void testRemoveTable() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		resourceType.removeAnnotation(JPA.TABLE);
		
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testRemoveTableName() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		TableAnnotation tableAnnotation = (TableAnnotation) resourceType.getAnnotation(JPA.TABLE);
		tableAnnotation.setSchema(null);
		assertSourceContains("@Table(name = \"FOO\")", cu);

		tableAnnotation.setName(null);
		assertSourceDoesNotContain("(name", cu);
	}
	
	public void testMultipleTypeMappings() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithEmbeddable();

		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals(2, resourceType.getAnnotationsSize());
		assertNotNull(resourceType.getAnnotation(JPA.EMBEDDABLE));
		assertNotNull(resourceType.getAnnotation(JPA.ENTITY));
		
		JavaResourceNode javaTypeMappingAnnotation = resourceType.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME);
		assertTrue(javaTypeMappingAnnotation instanceof EmbeddableAnnotation);
		assertSourceContains("@Entity", cu);
		assertSourceContains("@Embeddable", cu);
		
		resourceType.setPrimaryAnnotation(JPA.MAPPED_SUPERCLASS, EmptyIterable.<String>instance());
		assertEquals(1, resourceType.getAnnotationsSize());
		javaTypeMappingAnnotation = resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME);
		assertTrue(javaTypeMappingAnnotation instanceof MappedSuperclassAnnotation);
		assertSourceDoesNotContain("@Entity", cu);
		assertSourceContains("@MappedSuperclass", cu);
		assertSourceDoesNotContain("@Embeddable", cu);
	}
	
	public void testSetJavaTypeMappingAnnotation() throws Exception {
		ICompilationUnit cu = createTestType();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertEquals(0, resourceType.getAnnotationsSize());
		
		resourceType.setPrimaryAnnotation(JPA.ENTITY, EmptyIterable.<String>instance());
		assertTrue(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME) instanceof EntityAnnotation);
		assertSourceContains("@Entity", cu);
	}

	public void testAddJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertSourceDoesNotContain("@Table", cu);
		resourceType.addAnnotation(JPA.TABLE);
	
		assertSourceContains("@Table", cu);
	}
	
	public void testRemoveJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntityWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertSourceContains("@Table", cu);
		resourceType.removeAnnotation(JPA.TABLE);
		assertSourceDoesNotContain("@Table", cu);
	}
	
	//update source code to change from @Entity to @Embeddable and make sure @Table is not removed
	public void testChangeTypeMappingInSource() throws Exception {
		ICompilationUnit cu = createTestEntityWithTable();
		final JavaResourceType resourceType = buildJavaResourceType(cu);
		
		testType(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SourceEntityAnnotation.DECLARATION_ANNOTATION_ADAPTER.removeAnnotation(declaration);
			}
		});	

		cu.createImport("javax.persistence.Embeddable", null, new NullProgressMonitor());
				
		this.testType(cu).edit(new Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SourceEmbeddableAnnotation.DECLARATION_ANNOTATION_ADAPTER.newMarkerAnnotation(declaration);
			}
		});		
		
		assertNotNull(resourceType.getAnnotation(JPA.TABLE));
		assertNull(resourceType.getAnnotation(JPA.ENTITY));
		assertNotNull(resourceType.getAnnotation(JPA.EMBEDDABLE));
		assertSourceContains("@Table", cu);
	}
	
	public void testJavaTypeAnnotationsNestable() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals(1, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.getAnnotations(JPA.SECONDARY_TABLE).iterator().next();
		
		assertEquals("FOO", secondaryTableResource.getName());
	}
	
	public void testJavaTypeAnnotationsNoNestable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals(0, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}
	
	public void testJavaTypeAnnotationsContainerNoNestable() throws Exception {
		ICompilationUnit cu = createTestEntityWithEmptySecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals(0, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}
	
	//  @Entity     -->>    @Entity
	//						@SecondaryTable(name="FOO")
	public void testAddJavaTypeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("FOO");
		assertSourceContains("@SecondaryTable(name = \"FOO\")", cu);
	}
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddJavaTypeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({ @SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\") })", cu);
		
		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}
	
	//  @Entity     				
	//	@SecondaryTables(@SecondaryTable(name="FOO"))
	//           ||
	//           \/
	//  @Entity     				
	//	@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})
	public void testAddJavaTypeAnnotationNestableContainer3() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\"),@SecondaryTable(name = \"BAR\")})", cu);
		
		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}
	
	public void testAddJavaTypeAnnotationNestableContainer5() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAR\"),@SecondaryTable(name = \"FOO\")})", cu);
		
		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}

	public void testAddJavaTypeAnnotationNestableContainer6() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAR\"),@SecondaryTable(name = \"FOO\")})", cu);
		
		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));

		secondaryTableResource = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTableResource.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAZ\"),@SecondaryTable(name = \"BAR\"), @SecondaryTable(name = \"FOO\")})", cu);

		assertEquals(3, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}


	//@Entity
	//@SecondaryTable(name="FOO")
	public void testRemoveJavaTypeAnnotationNestableContainer() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaTypeAnnotationNestableContainer2() throws Exception {
		ICompilationUnit cu = createTestEntityWithSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertSourceDoesNotContain("@SecondaryTable(name = \"FOO\")", cu);
		assertSourceContains("@SecondaryTables", cu);
	}
	
	public void testRemoveJavaTypeAnnotationIndex() throws Exception {
		ICompilationUnit cu = createTestEntityWith2SecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		resourceType.removeAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertSourceDoesNotContain("@SecondaryTable(name = \"FOO\"", cu);
		assertSourceContains("@SecondaryTable(name = \"BAR\"", cu);
		assertSourceDoesNotContain("@SecondaryTables", cu);
	}
	
	public void testRemoveJavaTypeAnnotationIndex2() throws Exception {
		ICompilationUnit cu = createTestEntityWith2SecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)resourceType.addAnnotation(2, JPA.SECONDARY_TABLE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\"), @SecondaryTable(name = \"BAZ", cu);
		
		resourceType.removeAnnotation(1, JPA.SECONDARY_TABLE);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAZ\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		ICompilationUnit cu = createTestEntityWith2SecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)resourceType.addAnnotation(2, JPA.SECONDARY_TABLE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\"), @SecondaryTable(name = \"BAZ\")})", cu);
		
		resourceType.moveAnnotation(0, 2, JPA.SECONDARY_TABLE);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAZ\"), @SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\")})", cu);
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		ICompilationUnit cu = createTestEntityWith2SecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)resourceType.addAnnotation(2, JPA.SECONDARY_TABLE);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\"), @SecondaryTable(name = \"BAR\"), @SecondaryTable(name = \"BAZ\")})", cu);
		
		resourceType.moveAnnotation(2, 0, JPA.SECONDARY_TABLE);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAR\"), @SecondaryTable(name = \"BAZ\"), @SecondaryTable(name = \"FOO\")})", cu);
	}
	
	public void testNestedTypes() throws Exception {
		ICompilationUnit cu = createTestEntityWithMemberTypes();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals("only not classes and interfaces should be returned by #getTypes()", 2, CollectionTools.size(resourceType.getTypes()));
		assertEquals("only enums should be returned by #getEnums()", 1, CollectionTools.size(resourceType.getEnums()));
	}
	
	public void testDuplicateEntityAnnotations() throws Exception {
		ICompilationUnit cu = createTestEntityDuplicates();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EntityAnnotation javaTypeMappingAnnotation = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
		assertEquals(1, resourceType.getAnnotationsSize());
		
		javaTypeMappingAnnotation = (EntityAnnotation) resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME);
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
	}
	
	
	public void testAttributes() throws Exception {
		// TODO	
	}
	
	public void testFields() throws Exception {
		// TODO
	}
	
	public void testProperties() throws Exception {
		// TODO	
	}
	
	
	//TODO more tests here with superclasses other than Object.
	//1. Test where the superclass does not resolve
	//2. Test a superclass that does resolve
	//3. What about a superclass that is a class file in a jar??
	//4.
	public void testGetSuperclassQualifiedName() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertEquals("java.lang.Object", resourceType.getSuperclassQualifiedName());
		
	}
	
	public void testIsAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertTrue(resourceType.isAnnotated());
		
		resourceType.removeAnnotation(JPA.ENTITY);
		assertFalse(resourceType.isAnnotated());
		
		resourceType.addAnnotation(JPA.TABLE);
		assertTrue(resourceType.isAnnotated());
	}

	public void testAnnotatedMemberType() throws Exception {
		ICompilationUnit cu = this.createTestEntityWithMemberEmbeddable();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertNotNull(resourceType.getAnnotation(JPA.ENTITY));
		assertNull(resourceType.getAnnotation(JPA.EMBEDDABLE));
		
		JavaResourceType nestedType = resourceType.getTypes().iterator().next();
		assertNull(nestedType.getAnnotation(JPA.ENTITY));
		assertNotNull(nestedType.getAnnotation(JPA.EMBEDDABLE));	
	}
	
	public void testInvalidAnnotations() throws Exception {
		ICompilationUnit cu = this.createTestInvalidAnnotations();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		assertEquals(0, resourceType.getAnnotationsSize());
		assertEquals(0, resourceType.getAnnotationsSize());
		
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertEquals(0, resourceField.getAnnotationsSize());
		assertEquals(0, resourceField.getAnnotationsSize());
	}

}
