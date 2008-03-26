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

import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableImpl;
import org.eclipse.jpt.core.internal.resource.java.EntityImpl;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaResourcePersistentTypeTests extends JavaResourceModelTestCase {
	
	public JavaResourcePersistentTypeTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createEmbeddableAnnotation() throws Exception{
		this.createAnnotationAndMembers("Embeddable", "");		
	}
	
	private void createMappedSuperclassAnnotation() throws Exception{
		this.createAnnotationAndMembers("MappedSuperclass", "");		
	}
	
	private void createSecondaryTableAnnotation() throws Exception{
		this.createAnnotationAndMembers("SecondaryTable", "String name();");		
	}
	
	private void createSecondaryTablesAnnotation() throws Exception{
		this.createAnnotationAndMembers("SecondaryTables", "");		
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
		createEmbeddableAnnotation();

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
		createEmbeddableAnnotation();
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
		createSecondaryTableAnnotation();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertEquals(1, typeResource.annotationsSize());
	}

	public void testJavaTypeAnnotation() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertNotNull(typeResource.getAnnotation(JPA.TABLE));
	}

	public void testJavaTypeAnnotationNull() throws Exception {
		IType testType = this.createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertNull(typeResource.getAnnotation(JPA.TABLE));
	}

	//This will result in a compilation error, but we assume the first table found
	public void testDuplicateAnnotations() throws Exception {
		IType testType = this.createTestEntityMultipleTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation tableResource = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertEquals("FOO", tableResource.getName());
	}

	public void testRemoveTable() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		typeResource.removeAnnotation(JPA.TABLE);
		
		assertSourceDoesNotContain("@Table");
	}
	
	public void testRemoveTableName() throws Exception {
		IType testType = this.createTestEntityWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 

		TableAnnotation tableResource = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		tableResource.setSchema(null);
		assertSourceContains("@Table(name=\"FOO\")");

		tableResource.setName(null);
		assertSourceDoesNotContain("@Table");
		
		assertNull(typeResource.getAnnotation(JPA.TABLE));
	}
	
	public void testMultipleTypeMappings() throws Exception {
		IType testType = this.createTestEntityWithEmbeddable();
		createMappedSuperclassAnnotation();

		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		assertEquals(2, typeResource.mappingAnnotationsSize());
		assertEquals(0, typeResource.annotationsSize());
		assertNotNull(typeResource.getMappingAnnotation(JPA.EMBEDDABLE));
		assertNotNull(typeResource.getMappingAnnotation(JPA.ENTITY));
		
		JavaResourceNode javaTypeMappingAnnotation = typeResource.getMappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof EmbeddableAnnotation);
		assertSourceContains("@Entity");
		assertSourceContains("@Embeddable");
		
		typeResource.setMappingAnnotation(JPA.MAPPED_SUPERCLASS);
		assertEquals(1, typeResource.mappingAnnotationsSize());
		javaTypeMappingAnnotation = typeResource.getMappingAnnotation();
		assertTrue(javaTypeMappingAnnotation instanceof MappedSuperclassAnnotation);
		assertSourceDoesNotContain("@Entity");
		assertSourceContains("@MappedSuperclass");
		assertSourceDoesNotContain("@Embeddable");
	}
	
	public void testSetJavaTypeMappingAnnotation() throws Exception {
		createEntityAnnotation();
		IType testType = createTestType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertEquals(0, typeResource.mappingAnnotationsSize());
		
		typeResource.setMappingAnnotation(JPA.ENTITY);
		assertTrue(typeResource.getMappingAnnotation() instanceof EntityAnnotation);
		assertSourceContains("@Entity");
	}

	public void testSetJavaTypeMappingAnnotation2() throws Exception {
		IType testType = createTestEntityWithTable();
		createEmbeddableAnnotation();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertTrue(typeResource.getMappingAnnotation() instanceof EntityAnnotation);
		
		typeResource.setMappingAnnotation(JPA.EMBEDDABLE);
		assertTrue(typeResource.getMappingAnnotation() instanceof EmbeddableAnnotation);
		
		assertSourceDoesNotContain("@Entity");
		assertSourceContains("@Table");
	}

	public void testAddJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		assertSourceDoesNotContain("@Table");
		typeResource.addAnnotation(JPA.TABLE);
	
		assertSourceContains("@Table");
	}
	
	public void testRemoveJavaTypeAnnotation() throws Exception {
		IType testType = createTestEntityWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertSourceContains("@Table");
		typeResource.removeAnnotation(JPA.TABLE);
		assertSourceDoesNotContain("@Table");
	}
	
	
	//update source code to change from @Entity to @Embeddable and make sure @Table is not removed
	public void testChangeTypeMappingInSource() throws Exception {
		IType jdtType = createTestEntityWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		testType().removeAnnotation(((EntityImpl) typeResource.getMappingAnnotation()).getDeclarationAnnotationAdapter());

		this.createAnnotationAndMembers("Embeddable", "String name();");
		jdtType.getCompilationUnit().createImport("javax.persistence.Embeddable", null, new NullProgressMonitor());
		
		testType().newMarkerAnnotation(EmbeddableImpl.DECLARATION_ANNOTATION_ADAPTER);
		
		assertNotNull(typeResource.getAnnotation(JPA.TABLE));
		assertNull(typeResource.getMappingAnnotation(JPA.ENTITY));
		assertNotNull(typeResource.getMappingAnnotation(JPA.EMBEDDABLE));
		assertSourceContains("@Table");
	}
	
	public void testJavaTypeAnnotationsNestable() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(1, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		assertEquals("FOO", secondaryTableResource.getName());
	}
	
	public void testJavaTypeAnnotationsNoNestable() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(0, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testJavaTypeAnnotationsContainerNoNestable() throws Exception {
		IType jdtType = createTestEntityWithEmptySecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertEquals(0, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testJavaTypeAnnotationsNestableAndContainer() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTableAndSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();	
		assertEquals("BAR", secondaryTableResource.getName());
	}
	
	//  @Entity     -->>    @Entity
	//						@SecondaryTable(name="FOO")
	public void testAddJavaTypeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("FOO");
		assertSourceContains("@SecondaryTable(name=\"FOO\")");
	}
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddJavaTypeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		createSecondaryTablesAnnotation();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testAddJavaTypeAnnotationNestableContainer5() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
		
		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testAddJavaTypeAnnotationNestableContainer6() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"),@SecondaryTable(name=\"FOO\")})");
		
		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable})");
		secondaryTableResource.setName("BOO");
		
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));

		Iterator<JavaResourceNode> secondaryTableAnnotations = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTableResource = (SecondaryTableAnnotation) secondaryTableAnnotations.next();	
		assertEquals("BAR", secondaryTableResource.getName());
		secondaryTableResource = (SecondaryTableAnnotation) secondaryTableAnnotations.next();	
		assertEquals("BAZ", secondaryTableResource.getName());
		secondaryTableResource = (SecondaryTableAnnotation) secondaryTableAnnotations.next();	
		assertEquals("BOO", secondaryTableResource.getName());
		
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"BOO\")})");
	}

	//@Entity
	//@SecondaryTable(name="FOO")
	public void testRemoveJavaTypeAnnotationNestableContainer() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
	}
	

	//@Entity
	//@SecondaryTables(@SecondaryTable(name="FOO"))
	public void testRemoveJavaTypeAnnotationNestableContainer2() throws Exception {
		IType jdtType = createTestEntityWithSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		typeResource.removeAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		
		assertSourceDoesNotContain("@SecondaryTable(name=\"FOO\"");
		assertSourceContains("@SecondaryTable(name=\"BAR\"");
		assertSourceDoesNotContain("@SecondaryTables");
	}
	
	public void testRemoveJavaTypeAnnotationIndex2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAZ\")})");
	}
	
	public void testMoveJavaTypeAnnotation() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(0, 2, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\")})");
	}
	
	public void testMoveJavaTypeAnnotation2() throws Exception {
		IType jdtType = createTestEntityWith2SecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
	
		SecondaryTableAnnotation newAnnotation = (SecondaryTableAnnotation)typeResource.addAnnotation(2, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		newAnnotation.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\"), @SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\")})");
		
		typeResource.move(2, 0, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAR\"), @SecondaryTable(name=\"BAZ\"), @SecondaryTable(name=\"FOO\")})");
	}
	
	public void testNestedTypes() throws Exception {
		IType jdtType = createTestEntityWithMemberTypes();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals("only persistable types should be returned by nestedTypes()", 1, CollectionTools.size(persistentType.nestedTypes()));
		
		List<JavaResourcePersistentType> nestedTypes = (List<JavaResourcePersistentType>) ClassTools.fieldValue(persistentType, "nestedTypes");
		
		assertEquals(4, CollectionTools.size(nestedTypes));
		
	}
	
	public void testDuplicateEntityAnnotations() throws Exception {
		IType jdtType = createTestEntityDuplicates();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		EntityAnnotation javaTypeMappingAnnotation = (EntityAnnotation) persistentType.getMappingAnnotation(JPA.ENTITY);
		assertEquals("FirstEntity", javaTypeMappingAnnotation.getName());
		
		assertEquals(1, persistentType.mappingAnnotationsSize());
		
		javaTypeMappingAnnotation = (EntityAnnotation) persistentType.getMappingAnnotation();
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
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertNull(persistentType.getAccess());
	}
	
	public void testGetAccessFieldsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedField();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.FIELD, persistentType.getAccess());
	}
	
	public void testGetAccessMethodsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	public void testGetAccessFieldsAndMethodsAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedFieldAndMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.FIELD, persistentType.getAccess());
	}
	
	public void testGetAccessNonPersistableMethodAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedNonPersistableMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertNull(persistentType.getAccess());
	}
	
	public void testGetAccessPersistableMethodAndNonPersistableFieldAnnotated() throws Exception {
		IType jdtType = createTestEntityAnnotatedPersistableMethodNonPersistableField();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	public void testGetAccessNoPersistableFieldsAnnotated() throws Exception {
		IType jdtType = createTestEntityNoPersistableFields();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals(AccessType.PROPERTY, persistentType.getAccess());
	}
	
	//TODO more tests here with superclasses other than Object.
	//1. Test where the superclass does not resolve
	//2. Test a superclass that does resolve
	//3. What about a superclass that is a class file in a jar??
	//4.
	public void testGetSuperclassQualifiedName() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertEquals("java.lang.Object", persistentType.getSuperClassQualifiedName());
		
	}
	
	//more detailed tests in JPTToolsTests
	public void testIsPersistable() throws Exception {
		IType jdtType = createTestEntity();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(jdtType);
		
		assertTrue(persistentType.isPersistable());
	}
	
	public void testAnnotatedMemberType() throws Exception {
		IType testType = this.createTestEntityWithMemberEmbeddable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		assertNotNull(typeResource.getMappingAnnotation(JPA.ENTITY));
		assertNull(typeResource.getMappingAnnotation(JPA.EMBEDDABLE));
		
		JavaResourcePersistentType nestedType = typeResource.nestedTypes().next();
		assertNull(nestedType.getMappingAnnotation(JPA.ENTITY));
		assertNotNull(nestedType.getMappingAnnotation(JPA.EMBEDDABLE));	
	}
	
	public void testInvalidAnnotations() throws Exception {
		IType testType = this.createTestInvalidAnnotations();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		assertEquals(0, typeResource.mappingAnnotationsSize());
		assertEquals(0, typeResource.annotationsSize());
		
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		assertEquals(0, attributeResource.mappingAnnotationsSize());
		assertEquals(0, attributeResource.annotationsSize());
	}

}
