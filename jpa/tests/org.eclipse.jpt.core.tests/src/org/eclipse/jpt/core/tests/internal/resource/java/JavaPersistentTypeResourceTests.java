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
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.AccessType;
import org.eclipse.jpt.core.internal.resource.java.Embeddable;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableImpl;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.EntityImpl;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPersistentTypeResourceTests extends JavaResourceModelTestCase {
	
	public JavaPersistentTypeResourceTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
		
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();

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
	
	private IType createTestInvalidAnnotations() throws Exception {
		this.createAnnotationAndMembers("Foo", "String name();");

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

	private IType createTestEntityWithMemberEmbeddable() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Embeddable", "String name();");

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

	private IType createTestEntityDuplicates() throws Exception {
		createEntityAnnotation();

		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name=\"FirstEntity\")").append(CR);
				sb.append("@Entity(name=\"SecondEntity\")");
			}
		});
	}

	private IType createTestEntityWithEmbeddable() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Embeddable", "String name();");
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
	
	private IType createTestEntityAnnotatedField() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
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

	private IType createTestEntityAnnotatedMethod() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
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
	private IType createTestEntityAnnotatedFieldAndMethod() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private IType createTestEntityAnnotatedNonPersistableMethod() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendGetNameMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private IType createTestEntityAnnotatedPersistableMethodNonPersistableField() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
		this.createAnnotationAndMembers("Column", "");
	
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
				sb.append("@Column");
				sb.append("    private transient int notPersistable;").append(CR);
				sb.append(CR);

			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Column");
			}
		});
	}
	private IType createTestEntityNoPersistableFields() throws Exception {
		createEntityAnnotation();
	
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
				sb.delete(sb.indexOf("private int id;"), sb.indexOf("private int id;") + "private int id;".length());
				sb.delete(sb.indexOf("private String name;"), sb.indexOf("private String name;") + "private String name;".length());
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
		createEntityAnnotation();
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name=\"FOO\", schema=\"BAR\")");
			}
		});
	}
	
	private IType createTestEntityWithTableAndIdClass() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		this.createAnnotationAndMembers("IdClass", "Class value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE, JPA.ID_CLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table");
				sb.append(CR);
				sb.append("@IdClass");
			}
		});
	}

	
	private IType createTestEntityMultipleTables() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Table", "String name(); String schema();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@Table(name=\"FOO\")");
				sb.append(CR);
				sb.append("@Table(name=\"BAR\")");
			}
		});
	}

	private IType createTestEntityWithSecondaryTable() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\")");
			}
		});
	}
	private IType createTestEntityWithEmptySecondaryTables() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables()");
			}
		});
	}

	private IType createTestEntityWithSecondaryTables() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables(@SecondaryTable(name=\"FOO\"))");
			}
		});
	}
	
	private IType createTestEntityWith2SecondaryTables() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
			}
		});
	}
	
	private IType createTestEntityWithSecondaryTableAndSecondaryTables() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name();");
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\")");
				sb.append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
			}
		});
	}

	private IType createTestEntityWithMemberTypes() throws Exception {
		createEntityAnnotation();

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
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertEquals(1, CollectionTools.size(typeResource.annotations()));
	}

	public void testJavaTypeAnnotation() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertNotNull(typeResource.annotation(JPA.TABLE));
	}

	public void testJavaTypeAnnotationNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertNull(typeResource.annotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first table found
	public void testDuplicateAnnotations() throws Exception {
		IType testType = this.createTestEntityMultipleTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		Table tableResource = (Table) typeResource.annotation(JPA.TABLE);
		assertEquals("FOO", tableResource.getName());
	}

	public void testRemoveTable() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		typeResource.removeAnnotation(JPA.TABLE);
		
		assertSourceDoesNotContain("@Table");
	}
	
	public void testRemoveTableName() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 

		Table tableResource = (Table) typeResource.annotation(JPA.TABLE);
		tableResource.setSchema(null);
		assertSourceContains("@Table(name=\"FOO\")");

		tableResource.setName(null);
		assertSourceDoesNotContain("@Table");
		
		//TODO should I be calling this in the test?  where should the IElementChangedListener be set up?
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType()));
		assertNull(typeResource.annotation(JPA.TABLE));
	}
	
	public void testMultipleTypeMappings() throws Exception {
		IType testType = this.createTestEntityWithEmbeddable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		assertEquals(2, CollectionTools.size(typeResource.mappingAnnotations()));
		assertEquals(0, CollectionTools.size(typeResource.annotations()));
		assertNotNull(typeResource.mappingAnnotation(JPA.EMBEDDABLE));
		assertNotNull(typeResource.mappingAnnotation(JPA.ENTITY));
		
		JavaResource javaTypeMappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof Embeddable);
		assertSourceContains("@Entity");
		assertSourceContains("@Embeddable");
		
		typeResource.setMappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertEquals(1, CollectionTools.size(typeResource.mappingAnnotations()));
		javaTypeMappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof MappedSuperclass);
		assertSourceDoesNotContain("@Entity");
		assertSourceContains("@MappedSuperclass");
		assertSourceDoesNotContain("@Embeddable");
	}
	
	public void testSetJavaTypeMappingAnnotation() throws Exception {
		IType testType = createTestType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertEquals(0, CollectionTools.size(typeResource.mappingAnnotations()));
		
		typeResource.setMappingAnnotation(JPA.ENTITY);
		assertTrue(typeResource.mappingAnnotation() instanceof Entity);
		assertSourceContains("@Entity");
		
		typeResource.setMappingAnnotation(JPA.ENTITY);
		//TODO need to test behavior here, throw an exception, remove the old @Entity and replace it, 
		//thus clearing out any annotation elements??
	}

	public void testSetJavaTypeMappingAnnotation2() throws Exception {
		IType testType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertTrue(typeResource.mappingAnnotation() instanceof Entity);
		
		typeResource.setMappingAnnotation(JPA.EMBEDDABLE);
		assertTrue(typeResource.mappingAnnotation() instanceof Embeddable);
		
		assertSourceDoesNotContain("@Entity");
		assertSourceContains("@Table");
	}

	public void testAddJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		assertSourceDoesNotContain("@Table");
		typeResource.addAnnotation(JPA.TABLE);
	
		assertSourceContains("@Table");
	}
	
	public void testRemoveJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertSourceContains("@Table");
		typeResource.removeAnnotation(JPA.TABLE);
		assertSourceDoesNotContain("@Table");
	}
	
	
	//update source code to change from @Entity to @Embeddable and make sure @Table is not removed
	public void testChangeTypeMappingInSource() throws Exception {
		IType jdtType = createTestEntityWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		testType().removeAnnotation(((EntityImpl) typeResource.mappingAnnotation()).getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("Embeddable", "String name();");
		jdtType.getCompilationUnit().createImport("javax.persistence.Embeddable", null, new NullProgressMonitor());
		
		testType().newMarkerAnnotation(EmbeddableImpl.DECLARATION_ANNOTATION_ADAPTER);
		
		typeResource.updateFromJava(JDTTools.buildASTRoot(jdtType));
		
		
		assertNotNull(typeResource.annotation(JPA.TABLE));
		assertNull(typeResource.mappingAnnotation(JPA.ENTITY));
		assertNotNull(typeResource.mappingAnnotation(JPA.EMBEDDABLE));
		assertSourceContains("@Table");
	}
	
	public void testJavaTypeAnnotationsNestable() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(1, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		assertEquals("FOO", secondaryTableResource.getName());
	}
	
	public void testJavaTypeAnnotationsNoNestable() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(0, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testJavaTypeAnnotationsContainerNoNestable() throws Exception {
		IType jdtType = createTestEntityWithEmptySecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(0, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testJavaTypeAnnotationsNestableAndContainer() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();	
		assertEquals("BAR", secondaryTableResource.getName());
	}
	
	//  @Entity     -->>    @Entity
	//						@SecondaryTable(name="FOO")
	public void testAddJavaTypeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("FOO");
		assertSourceContains("@SecondaryTable(name=\"FOO\")");
	}
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddJavaTypeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	//  @Entity     				
	//	@SecondaryTables(@SecondaryTable(name="FOO"))
	//           ||
	//           \/
	//  @Entity     				
	//	@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})
	public void testAddJavaTypeAnnotationNestableContainer3() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testAddJavaTypeAnnotationNestableContainer5() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
		
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testAddJavaTypeAnnotationNestableContainer6() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
		
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"),@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"FOO\")})");

		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	//  @Entity     				
	//	@SecondaryTable(name=\"FOO\")
	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})
	//			 ||
	//           \/
	//  @Entity     				
	//	@SecondaryTable(name=\"FOO\")
	//  @SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})
	public void testAddJavaTypeAnnotationNestableContainer4() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTable secondaryTableResource = (SecondaryTable) typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable})");
		secondaryTableResource.setName("BOO");
		
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		Iterator<JavaResource> secondaryTableAnnotations = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
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
	public void testRemoveJavaTypeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaTypeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
		assertSourceContains("@SecondaryTable(name=\"BAR\"");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(0, 2, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTable newAnnotation = (SecondaryTable)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(2, 0, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\")})");
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
		
		Entity javaTypeMappingAnnotation = (Entity) persistentType.mappingAnnotation(JPA.ENTITY);
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
		assertEquals(1, CollectionTools.size(persistentType.mappingAnnotations()));
		
		javaTypeMappingAnnotation = (Entity) persistentType.mappingAnnotation();
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
	}
	
	
	public void testAttributes() throws Exception {
		
	}
	
	public void testFields() throws Exception {
		
	}
	
	public void testProperties() throws Exception {
		
	}
	
	public void testGetAccessNoAttributesAnnotated() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertNull(persistentType.getAccess());
	}
	
	public void testGetAccessFieldsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedField();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.FIELD, persistentType.getAccess());
	}
	
	public void testGetAccessMethodsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedMethod();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	public void testGetAccessFieldsAndMethodsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedFieldAndMethod();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.FIELD, persistentType.getAccess());
	}
	
	public void testGetAccessNonPersistableMethodAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedNonPersistableMethod();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertNull(persistentType.getAccess());
	}
	
	public void testGetAccessPersistableMethodAndNonPersistableFieldAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedPersistableMethodNonPersistableField();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	public void testGetAccessNoPersistableFieldsAnnotated() throws Exception {
		IType jdtType = createTestEntityNoPersistableFields();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	//TODO more tests here with superclasses other than Object.
	//1. Test where the superclass does not resolve
	//2. Test a superclass that does resolve
	//3. What about a superclass that is a class file in a jar??
	//4.
	public void testGetSuperclassQualifiedName() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals("java.lang.Object", persistentType.getSuperClassQualifiedName());
		
	}
	
	//more detailed tests in JPTToolsTests
	public void testIsPersistable() throws Exception {
		IType jdtType = createTestEntity();
		JavaPersistentTypeResource persistentType = buildJavaTypeResource(jdtType);
		
		assertTrue(persistentType.isPersistable());
	}
	
	public void testAnnotatedMemberType() throws Exception {
		IType testType = this.createTestEntityWithMemberEmbeddable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		assertNotNull(typeResource.mappingAnnotation(JPA.ENTITY));
		assertNull(typeResource.mappingAnnotation(JPA.EMBEDDABLE));
		
		JavaPersistentTypeResource nestedType = typeResource.nestedTypes().next();
		assertNull(nestedType.mappingAnnotation(JPA.ENTITY));
		assertNotNull(nestedType.mappingAnnotation(JPA.EMBEDDABLE));	
	}
	
	public void testInvalidAnnotations() throws Exception {
		IType testType = this.createTestInvalidAnnotations();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		assertEquals(0, CollectionTools.size(typeResource.mappingAnnotations()));
		assertEquals(0, CollectionTools.size(typeResource.annotations()));
		
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		assertEquals(0, CollectionTools.size(attributeResource.mappingAnnotations()));
		assertEquals(0, CollectionTools.size(attributeResource.annotations()));
	}

}
