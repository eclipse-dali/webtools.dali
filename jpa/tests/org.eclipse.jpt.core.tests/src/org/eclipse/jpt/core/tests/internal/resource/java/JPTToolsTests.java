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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;

@SuppressWarnings("nls")
public class JPTToolsTests extends JavaResourceModelTestCase {

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
	
//	private ICompilationUnit createTestTypeInvalidMethodReturnType() throws Exception {
//		return this.createTestType(new DefaultAnnotationWriter() {
//			
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>("com.foo.Foo");
//			}
//			@Override
//			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
//				sb.append(CR);
//				sb.append("    public Foo getFoo() {").append(CR);
//				sb.append("        return null;").append(CR);
//				sb.append("    }").append(CR);
//				sb.append(CR);
//				sb.append("    public void setFoo(Foo id) {").append(CR);
//				sb.append("        this.id = id;").append(CR);
//				sb.append("    }").append(CR);
//				sb.append(CR);
//			}
//		});
//	}

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

	protected MethodAttribute fooMethod(ICompilationUnit cu) {
		return this.buildMethod("getFoo", cu);
	}

	//private String foo; - persistable
	public void testFieldIsPersistable1() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private");
		FieldAttribute fieldAttribute = fooField(cu);
		IVariableBinding variableBinding = fieldAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private static String foo; - not persistable
	public void testFieldIsPersistable2() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private static");
		FieldAttribute fieldAttribute = fooField(cu);
		IVariableBinding variableBinding = fieldAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private transient String foo; - not persistable
	public void testFieldIsPersistable3() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private transient");
		FieldAttribute fieldAttribute = fooField(cu);
		IVariableBinding variableBinding = fieldAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private final String foo; - persistable
	public void testFieldIsPersistable4() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("private final");
		FieldAttribute fieldAttribute = fooField(cu);
		IVariableBinding variableBinding = fieldAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//public String foo; - persistable
	public void testFieldIsPersistable5() throws Exception {
		ICompilationUnit cu = createTestTypeFieldWithModifier("public");
		FieldAttribute fieldAttribute = fooField(cu);
		IVariableBinding variableBinding = fieldAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	

	//public int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter1() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//protected int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter2() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter3() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//private int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter4() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//public static int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter5() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public final int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter6() throws Exception {
		ICompilationUnit cu = createTestTypeGetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter7() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//protected void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter8() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter9() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//private void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter10() throws Exception {
		ICompilationUnit cu =  createTestTypeSetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//public static void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter11() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public final void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter12() throws Exception {
		ICompilationUnit cu = createTestTypeSetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod(cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public boolean isFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter13() throws Exception {
		ICompilationUnit cu = createTestTypeIsMethod();
		MethodAttribute methodAttribute =  this.buildMethod("isFoo", cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//public int isFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter14() throws Exception {
		ICompilationUnit cu = createTestTypeIsMethodReturnInt();
		MethodAttribute methodAttribute =  this.buildMethod("isFoo", cu);
		IMethodBinding methodBinding = methodAttribute.getBinding(JDTTools.buildASTRoot(cu));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//public int isFoo() {} - persistable
	//public int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter15() throws Exception {
		ICompilationUnit cu = createTestTypeIsAndGetMethodWithModifier("public");
		MethodAttribute isFooMethod =  this.buildMethod("isFoo", cu);
		MethodAttribute getFooMethod =  this.buildMethod("getFoo", cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(isFooMethod.getBinding(astRoot)));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(getFooMethod.getBinding(astRoot)));
	}
	
	//public int foo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter16() throws Exception {
		ICompilationUnit cu = createTestTypeInvalidMethodName();
		MethodAttribute fooMethod =  this.buildMethod("foo", cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.getBinding(astRoot)));
	}

	//public void getFoo() {} - not persistable - void return type
	public void testMethodIsPersistablePropertyGetter17() throws Exception {
		ICompilationUnit cu = createTestTypeVoidMethodReturnType();
		MethodAttribute fooMethod =  this.buildMethod("getFoo", cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.getBinding(astRoot)));
	}
	
	//TODO
	//**getFooMethod.binding(CompliationUnit) is returning null, not sure why and don't know how to test
	//**this if it is returning null there instead of returning null for IMethodBinding.getReturnType()
//	//public Foo getFoo() {} - not persistable - Foo does not resolve
//	public void testMethodIsPersistablePropertyGetter18() throws Exception {
//		ICompilationUnit cu = createTestTypeInvalidMethodReturnType();
//		MethodAttribute getFooMethod =  this.buildMethod("getFoo");
//		
//		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
//		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.binding(astRoot)));
//	}
	
	//method with parameters - not persistable
	public void testMethodIsPersistablePropertyGetter19() throws Exception {
		ICompilationUnit cu = createTestType();
		MethodAttribute setIdMethod =  idSetMethod(cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(setIdMethod.getBinding(astRoot)));
	}
	
	//constructor - not persistable
	public void testMethodIsPersistablePropertyGetter20() throws Exception {
		ICompilationUnit cu = createTestTypeConstructor();
		MethodAttribute constructor =  buildMethod(TYPE_NAME, cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(constructor.getBinding(astRoot)));
	}
	
	//no corresponding set method - not persistable
	public void testMethodIsPersistablePropertyGetter21() throws Exception {
		ICompilationUnit cu = createTestType();
		MethodAttribute getNameMethod =  nameGetMethod(cu);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(getNameMethod.getBinding(astRoot)));
	}
	
	//public class AnnotationTestType
	public void cuIsPersistable() throws Exception {
		ICompilationUnit cu = createTestType();
		Type type = testType(cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertTrue(JPTTools.typeIsPersistable(type.getBinding(astRoot)));
	}
	
	//public final class MyFinal
	public void cuIsPersistable2() throws Exception {
		ICompilationUnit cu = this.javaProject.createCompilationUnit("finals", "MyFinal.java", "public final class MyFinal { }");
		Type type = buildType("MyFinal", cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.typeIsPersistable(type.getBinding(astRoot)));
	}
	
	//public interface MyInterface
	public void cuIsPersistable3() throws Exception {
		ICompilationUnit cu = this.javaProject.createCompilationUnit("interfaces", "MyInterface.java", "public interface MyInterface { }");
		Type type = buildType("MyInterface", cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.typeIsPersistable(type.getBinding(astRoot)));
	}
	
	//enum not persistable
	public void cuIsPersistable4() throws Exception {
		ICompilationUnit cu = this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		Type type = buildType("TestEnum", cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.typeIsPersistable(type.getBinding(astRoot)));
	}
	
	//annotation not persistable
	public void cuIsPersistable5() throws Exception {
		ICompilationUnit cu = this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");
		Type type = buildType("TestAnnotation", cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.typeIsPersistable(type.getBinding(astRoot)));
	}
	
	//public static member type is persistable
	public void cuIsPersistable6() throws Exception {
		ICompilationUnit cu = this.createTestTypeWithMemberTypes();
		Type testType = this.testType(cu);
		Type memberType = this.buildType(testType, "FooStatic", 1, cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertTrue(JPTTools.typeIsPersistable(memberType.getBinding(astRoot)));
	}
	
	//non-static member type is not persistable
	public void cuIsPersistable7() throws Exception {
		ICompilationUnit cu = this.createTestTypeWithMemberTypes();
		Type testType = this.testType(cu);
		Type memberType = this.buildType(testType, "FooNotStatic", 1, cu);
		CompilationUnit astRoot = JDTTools.buildASTRoot(cu);
		assertFalse(JPTTools.typeIsPersistable(memberType.getBinding(astRoot)));
	}
	//TODO still need to test typeIsPersistable() returns false for local and anonymous classes

	
}

