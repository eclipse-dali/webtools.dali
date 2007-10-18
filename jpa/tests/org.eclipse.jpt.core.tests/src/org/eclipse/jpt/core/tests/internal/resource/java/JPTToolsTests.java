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

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jpt.core.internal.jdtutility.FieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.JPTTools;
import org.eclipse.jpt.core.internal.jdtutility.MethodAttribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class JPTToolsTests extends JavaResourceModelTestCase {

	public JPTToolsTests(String name) {
		super(name);
	}

	private IType createTestTypeFieldWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append(CR);
				sb.append("    " + modifier + " String foo;").append(CR);
				sb.append(CR);
			}
		});
	}
	
	protected FieldAttribute fooField() throws JavaModelException {
		return this.fieldNamed("foo");
	}
	
	
	private IType createTestTypeGetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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
	
	private IType createTestTypeInvalidMethodName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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
	
	private IType createTestTypeConstructor() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
				sb.append(CR);
				sb.append("    public " + TYPE_NAME + "() {").append(CR);
				sb.append("        super();").append(CR);
				sb.append("    }").append(CR);
				sb.append(CR);
			}
		});
	}
	
	private IType createTestTypeVoidMethodReturnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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
	
//	private IType createTestTypeInvalidMethodReturnType() throws Exception {
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

	private IType createTestTypeIsMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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
	
	private IType createTestTypeIsMethodReturnInt() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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
	
	private IType createTestTypeIsAndGetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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

	private IType createTestTypeSetMethodWithModifier(final String modifier) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuffer sb) {
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

	private IType createTestTypeWithMemberTypes() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public void appendMemberTypeTo(StringBuffer sb) {
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

	protected MethodAttribute fooMethod() throws JavaModelException {
		return this.methodNamed("getFoo");
	}

	//private String foo; - persistable
	public void testFieldIsPersistable1() throws Exception {
		IType testType = createTestTypeFieldWithModifier("private");
		FieldAttribute fieldAttribute = fooField();
		IVariableBinding variableBinding = fieldAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private static String foo; - not persistable
	public void testFieldIsPersistable2() throws Exception {
		IType testType = createTestTypeFieldWithModifier("private static");
		FieldAttribute fieldAttribute = fooField();
		IVariableBinding variableBinding = fieldAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private transient String foo; - not persistable
	public void testFieldIsPersistable3() throws Exception {
		IType testType = createTestTypeFieldWithModifier("private transient");
		FieldAttribute fieldAttribute = fooField();
		IVariableBinding variableBinding = fieldAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//private final String foo; - persistable
	public void testFieldIsPersistable4() throws Exception {
		IType testType = createTestTypeFieldWithModifier("private final");
		FieldAttribute fieldAttribute = fooField();
		IVariableBinding variableBinding = fieldAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	
	//public String foo; - persistable
	public void testFieldIsPersistable5() throws Exception {
		IType testType = createTestTypeFieldWithModifier("public");
		FieldAttribute fieldAttribute = fooField();
		IVariableBinding variableBinding = fieldAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.fieldIsPersistable(variableBinding));
	}
	

	//public int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter1() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//protected int getFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter2() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter3() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//private int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter4() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//public static int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter5() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public final int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter6() throws Exception {
		IType testType = createTestTypeGetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter7() throws Exception {
		IType testType = createTestTypeSetMethodWithModifier("public");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//protected void setFoo(int foo) {} - persistable
	public void testMethodIsPersistablePropertyGetter8() throws Exception {
		IType testType = createTestTypeSetMethodWithModifier("protected");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter9() throws Exception {
		IType testType = createTestTypeSetMethodWithModifier("");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//private void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter10() throws Exception {
		IType testType =  createTestTypeSetMethodWithModifier("private");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}
	
	//public static void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter11() throws Exception {
		IType testType = createTestTypeSetMethodWithModifier("public static");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public final void setFoo(int foo) {} - not persistable
	public void testMethodIsPersistablePropertyGetter12() throws Exception {
		IType testType = createTestTypeSetMethodWithModifier("public final");
		MethodAttribute methodAttribute = fooMethod();
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
	}

	//public boolean isFoo() {} - persistable
	public void testMethodIsPersistablePropertyGetter13() throws Exception {
		IType testType = createTestTypeIsMethod();
		MethodAttribute methodAttribute =  this.methodNamed("isFoo");
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//public int isFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter14() throws Exception {
		IType testType = createTestTypeIsMethodReturnInt();
		MethodAttribute methodAttribute =  this.methodNamed("isFoo");
		IMethodBinding methodBinding = methodAttribute.binding(JDTTools.buildASTRoot(testType));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(methodBinding));
		
	}
	
	//public int isFoo() {} - persistable
	//public int getFoo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter15() throws Exception {
		IType testType = createTestTypeIsAndGetMethodWithModifier("public");
		MethodAttribute isFooMethod =  this.methodNamed("isFoo");
		MethodAttribute getFooMethod =  this.methodNamed("getFoo");
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertTrue(JPTTools.methodIsPersistablePropertyGetter(isFooMethod.binding(astRoot)));
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(getFooMethod.binding(astRoot)));
	}
	
	//public int foo() {} - not persistable
	public void testMethodIsPersistablePropertyGetter16() throws Exception {
		IType testType = createTestTypeInvalidMethodName();
		MethodAttribute fooMethod =  this.methodNamed("foo");
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.binding(astRoot)));
	}

	//public void getFoo() {} - not persistable - void return type
	public void testMethodIsPersistablePropertyGetter17() throws Exception {
		IType testType = createTestTypeVoidMethodReturnType();
		MethodAttribute fooMethod =  this.methodNamed("getFoo");
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.binding(astRoot)));
	}
	
	//TODO
	//**getFooMethod.binding(CompliationUnit) is returning null, not sure why and don't know how to test
	//**this if it is returning null there instead of returning null for IMethodBinding.getReturnType()
//	//public Foo getFoo() {} - not persistable - Foo does not resolve
//	public void testMethodIsPersistablePropertyGetter18() throws Exception {
//		IType testType = createTestTypeInvalidMethodReturnType();
//		MethodAttribute getFooMethod =  this.methodNamed("getFoo");
//		
//		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
//		assertFalse(JPTTools.methodIsPersistablePropertyGetter(fooMethod.binding(astRoot)));
//	}
	
	//method with parameters - not persistable
	public void testMethodIsPersistablePropertyGetter19() throws Exception {
		IType testType = createTestType();
		MethodAttribute setIdMethod =  idSetMethod();
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(setIdMethod.binding(astRoot)));
	}
	
	//constructor - not persistable
	public void testMethodIsPersistablePropertyGetter20() throws Exception {
		IType testType = createTestTypeConstructor();
		MethodAttribute constructor =  methodNamed(TYPE_NAME);
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(constructor.binding(astRoot)));
	}
	
	//no corresponding set method - not persistable
	public void testMethodIsPersistablePropertyGetter21() throws Exception {
		IType testType = createTestType();
		MethodAttribute getNameMethod =  nameGetMethod();
		
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.methodIsPersistablePropertyGetter(getNameMethod.binding(astRoot)));
	}
	
	//public class AnnotationTestType
	public void testTypeIsPersistable() throws Exception {
		IType testType = createTestType();
		Type type = buildType(testType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertTrue(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//public final class MyFinal
	public void testTypeIsPersistable2() throws Exception {
		IType testType = this.javaProject.createType("finals", "MyFinal.java", "public final class MyFinal { }");
		Type type = buildType(testType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//public interface AnnotationTestType
	public void testTypeIsPersistable3() throws Exception {
		IType testType = this.javaProject.createType("interfaces", "MyInterface.java", "public interface MyInterface { }");
		Type type = buildType(testType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//enum not persistable
	public void testTypeIsPersistable4() throws Exception {
		IType testType = this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		Type type = buildType(testType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//annotation not persistable
	public void testTypeIsPersistable5() throws Exception {
		IType testType = this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");
		Type type = buildType(testType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//public static member type is persistable
	public void testTypeIsPersistable6() throws Exception {
		IType testType = this.createTestTypeWithMemberTypes();
		IType staticType = testType.getType("FooStatic");
		Type type = buildType(staticType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertTrue(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	
	//non-static member type is not persistable
	public void testTypeIsPersistable7() throws Exception {
		IType testType = this.createTestTypeWithMemberTypes();
		IType staticType = testType.getType("FooNotStatic");
		Type type = buildType(staticType);
		CompilationUnit astRoot = JDTTools.buildASTRoot(testType);
		assertFalse(JPTTools.typeIsPersistable(type.binding(astRoot)));
	}
	//TODO still need to test typeIsPersistable() returns false for local and anonymous classes

}

