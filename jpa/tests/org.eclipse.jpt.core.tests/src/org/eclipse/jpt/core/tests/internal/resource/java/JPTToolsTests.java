/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JPTToolsTests extends JpaJavaResourceModelTestCase {

	public JPTToolsTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTypeFieldWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    " + modifier + " String foo;").append(CR);
				sb.append(CR);
			}
		});
	}
	
	protected FieldAttribute fooField(ICompilationUnit cu) {
		return this.buildField("foo", cu);
	}
	
	
	private ICompilationUnit createTestTypeGetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    " + modifier + " int getFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(int id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeInvalidMethodName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public int foo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(int id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeConstructor() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public " + TYPE_NAME + "() {").append(CR);
				sb.append("        super();").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeVoidMethodReturnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public void getFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(int id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeInvalidMethodReturnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>("com.foo.Foo");
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public Foo getFoo() {").append(CR);
				sb.append("        return null;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(Foo id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}

	private ICompilationUnit createTestTypeIsMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public boolean isFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(boolean id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeIsMethodReturnInt() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public int isFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(int id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestTypeIsAndGetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    " + modifier + " boolean isFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    " + modifier + " boolean getFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    public void setFoo(boolean id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}

	private ICompilationUnit createTestTypeSetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    public int getFoo() {").append(CR);
				sb.append("        return this.id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
				sb.append("    " + modifier + " void setFoo(int id) {").append(CR);
				sb.append("        this.id = id;").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}

	private ICompilationUnit createTestTypeWithMemberTypes() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public void appendMemberTypeTo(StringBuilder sb) {
				sb.append("     public static class FooStatic {}").append(CR);
				sb.append(CR);
				sb.append("     public class FooNotStatic {}").append(CR);
				sb.append(CR);
				sb.append("     public @interface MyAnnotation {}").append(CR);
				sb.append(CR);
				sb.append("     public enum MyEnum {}").append(CR);
			}
		});
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
	
	private ICompilationUnit createTestEntityAnnotatedField() throws Exception {
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

	private ICompilationUnit createTestEntityAnnotatedMethod() throws Exception {
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

	private ICompilationUnit createTestEntityAnnotatedFieldAndMethod() throws Exception {
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
	
	private ICompilationUnit createTestEntityAnnotatedNonPersistableMethod() throws Exception {
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
	
	private ICompilationUnit createTestEntityAnnotatedPersistableMethodNonPersistableField() throws Exception {
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

	private ICompilationUnit createTestEntityNoPersistableFields() throws Exception {
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

	protected MethodAttribute fooMethod(ICompilationUnit cu) {
		return this.buildMethod("getFoo", cu);
	}

	//private String foo; - persistable
	public void testFieldIsPersistable1() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private");
		FieldAttribute fieldAttribute = fooField(cu);
		assertTrue(fieldAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//private static String foo; - not persistable
	public void testFieldIsPersistable2() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private static");
		FieldAttribute fieldAttribute = fooField(cu);
		assertFalse(fieldAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//private transient String foo; - not persistable
	public void testFieldIsPersistable3() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private transient");
		FieldAttribute fieldAttribute = fooField(cu);
		assertFalse(fieldAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//private final String foo; - persistable
	public void testFieldIsPersistable4() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private final");
		FieldAttribute fieldAttribute = fooField(cu);
		assertTrue(fieldAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public String foo; - persistable
	public void testFieldIsPersistable5() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("public");
		FieldAttribute fieldAttribute = fooField(cu);
		assertTrue(fieldAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	

	//public int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter01() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//protected int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter02() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter03() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//private int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter04() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public static int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter05() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertFalse(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}

	//public final int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter06() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}

	//public void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter07() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//protected void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter08() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter09() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//private void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter10() throws Exception {
		ICompilationUnit cu =  createTestTypeSetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public static void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter11() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertFalse(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}

	//public final void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter12() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod(cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}

	//public boolean isFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter13() throws Exception {
		ICompilationUnit cu = createTestTypeIsMethod();
		MethodAttribute methodAttribute =  this.buildMethod("isFoo", cu);
		assertTrue(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public int isFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter14() throws Exception {
		ICompilationUnit cu = createTestTypeIsMethodReturnInt();
		MethodAttribute methodAttribute =  this.buildMethod("isFoo", cu);
		assertFalse(methodAttribute.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public int isFoo() {} - persistable
	//public int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter15() throws Exception {
		ICompilationUnit cu = createTestTypeIsAndGetMethodWithModifier("public");
		MethodAttribute isFooMethod =  this.buildMethod("isFoo", cu);
		MethodAttribute getFooMethod =  this.buildMethod("getFoo", cu);
		
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertTrue(isFooMethod.isPersistable(astRoot));
		assertFalse(getFooMethod.isPersistable(astRoot));
	}
	
	//public int foo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter16() throws Exception {
		ICompilationUnit cu = createTestTypeInvalidMethodName();
		MethodAttribute fooMethod =  this.buildMethod("foo", cu);
		assertFalse(fooMethod.isPersistable(this.buildASTRoot(cu)));
	}

	//public void getFoo() {} - not persistable - void return type
	public void testMethodIsPersistablePropertyGetter17() throws Exception {
		ICompilationUnit cu = createTestTypeVoidMethodReturnType();
		MethodAttribute fooMethod =  this.buildMethod("getFoo", cu);
		assertFalse(fooMethod.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public Foo getFoo() {} - persistable??? - Foo does not resolve
	public void testMethodIsPersistablePropertyGetter18() throws Exception {
		ICompilationUnit cu = createTestTypeInvalidMethodReturnType();
		MethodAttribute getFooMethod =  this.buildMethod("getFoo", cu);
		assertTrue(getFooMethod.isPersistable(this.buildASTRoot(cu)));
	}
	
	//method with parameters - not persistable
	public void testMethodIsPersistablePropertyGetter19() throws Exception {
		ICompilationUnit cu = createTestType();
		MethodAttribute setIdMethod =  idSetMethod(cu);
		assertFalse(setIdMethod.isPersistable(this.buildASTRoot(cu)));
	}
	
	//constructor - not persistable
	public void testMethodIsPersistablePropertyGetter20() throws Exception {
		ICompilationUnit cu = createTestTypeConstructor();
		MethodAttribute constructor =  buildMethod(TYPE_NAME, cu);
		assertFalse(constructor.isPersistable(this.buildASTRoot(cu)));
	}
	
	//no corresponding set method - not persistable
	public void testMethodIsPersistablePropertyGetter21() throws Exception {
		ICompilationUnit cu = createTestType();
		MethodAttribute getNameMethod =  nameGetMethod(cu);
		assertFalse(getNameMethod.isPersistable(this.buildASTRoot(cu)));
	}
	
	//public class AnnotationTestType
	public void testTypeIsPersistable1() throws Exception {
		ICompilationUnit cu = createTestType();
		Type type = testType(cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertTrue(type.isPersistable(astRoot));
	}
	
	//public final class MyFinal
	public void testTypeIsPersistable2() throws Exception {
		ICompilationUnit cu = this.javaProject.createCompilationUnit("finals", "MyFinal.java", "public final class MyFinal { }");
		Type type = buildType("MyFinal", cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertTrue(type.isPersistable(astRoot));
	}
	
	//public interface MyInterface
	public void testTypeIsPersistable3() throws Exception {
		ICompilationUnit cu = this.javaProject.createCompilationUnit("interfaces", "MyInterface.java", "public interface MyInterface { }");
		Type type = buildType("MyInterface", cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertFalse(type.isPersistable(astRoot));
	}
	
	//enum not persistable
	public void testTypeIsPersistable4() throws Exception {
		ICompilationUnit cu = this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		Type type = buildType("TestEnum", cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertFalse(type.isPersistable(astRoot));
	}
	
	//annotation not persistable
	public void testTypeIsPersistable5() throws Exception {
		ICompilationUnit cu = this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");
		Type type = buildType("TestAnnotation", cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertFalse(type.isPersistable(astRoot));
	}
	
	//public static member type is persistable
	public void testTypeIsPersistable6() throws Exception {
		ICompilationUnit cu = this.createTestTypeWithMemberTypes();
		Type testType = this.testType(cu);
		Type memberType = this.buildType(testType, "FooStatic", 1, cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertTrue(memberType.isPersistable(astRoot));
	}
	
	//non-static member type is persistable, handled with validation
	public void testTypeIsPersistable7() throws Exception {
		ICompilationUnit cu = this.createTestTypeWithMemberTypes();
		Type testType = this.testType(cu);
		Type memberType = this.buildType(testType, "FooNotStatic", 1, cu);
		CompilationUnit astRoot = this.buildASTRoot(cu);
		assertTrue(memberType.isPersistable(astRoot));
	}
	//TODO still need to test typeIsPersistable() returns false for local and anonymous classes

	public void testGetAccessNoAttributesAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertNull(JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessFieldsAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedField();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertEquals(AccessType.FIELD, JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessMethodsAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertEquals(AccessType.PROPERTY, JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessFieldsAndMethodsAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedFieldAndMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertEquals(AccessType.FIELD, JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessNonPersistableMethodAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedNonPersistableMethod();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertNull(JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessPersistableMethodAndNonPersistableFieldAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityAnnotatedPersistableMethodNonPersistableField();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertEquals(AccessType.PROPERTY, JPTTools.buildAccess(persistentType));
	}
	
	public void testGetAccessNoPersistableFieldsAnnotated() throws Exception {
		ICompilationUnit cu = createTestEntityNoPersistableFields();
		JavaResourcePersistentType persistentType = buildJavaTypeResource(cu);
		
		assertNull(JPTTools.buildAccess(persistentType));
	}	
}

