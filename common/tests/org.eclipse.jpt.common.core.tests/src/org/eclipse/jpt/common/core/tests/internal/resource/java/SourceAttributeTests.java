package org.eclipse.jpt.common.core.tests.internal.resource.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;


public class SourceAttributeTests
		extends JavaResourceModelTestCase {
	
	private static String TEST_CLASS_NAME = "TestClass";
	
	
	public SourceAttributeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return new AnnotationDefinition[0];
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return new NestableAnnotationDefinition[0];
	}
	
	
	private ICompilationUnit createTestClassWithVariousAttributes() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append(CR);
				sb.append("public class ").append(TEST_CLASS_NAME).append("<T extends Number> ").append("{").append(CR);
				sb.append("    public String string;").append(CR);
				sb.append("    public List<String> stringList;").append(CR);
				sb.append("    public String[] stringArray;").append(CR);
				sb.append("    public String[][] stringDoubleArray;").append(CR);
				sb.append("    public T generic;").append(CR);
				sb.append("    public List<T> genericList;").append(CR);
				sb.append("    public T[] genericArray;").append(CR);
				sb.append("    public List<?> wildcardList;").append(CR);
				sb.append("}").append(CR);
			}
		};
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, TEST_CLASS_NAME + ".java", sourceWriter);
	}
	
	
	public void testAttributeTypes() throws Exception {
		ICompilationUnit testClassCU = createTestClassWithVariousAttributes();
		JavaResourceType testClass = buildJavaResourceType(testClassCU);
		
		// String string
		JavaResourceField att = getField(testClass, 0);
		assertEquals("string", att.getName());
		assertEquals("java.lang.String", att.getTypeName());
		assertEquals(false, att.typeIsArray());
		assertEquals(null, att.getTypeArrayComponentTypeName());
		assertEquals(0, att.getTypeArrayDimensionality());
		assertEquals(0, att.getTypeTypeArgumentNamesSize());
		
		// List<String> stringList
		att = getField(testClass, 1);
		assertEquals("stringList", att.getName());
		assertEquals("java.util.List", att.getTypeName());
		assertEquals(false, att.typeIsArray());
		assertEquals(null, att.getTypeArrayComponentTypeName());
		assertEquals(0, att.getTypeArrayDimensionality());
		assertEquals(1, att.getTypeTypeArgumentNamesSize());
		assertEquals("java.lang.String", att.getTypeTypeArgumentName(0));
		
		// String[] stringArray
		att = getField(testClass, 2);
		assertEquals("stringArray", att.getName());
		assertEquals("java.lang.String[]", att.getTypeName());
		assertEquals(true, att.typeIsArray());
		assertEquals("java.lang.String", att.getTypeArrayComponentTypeName());
		assertEquals(1, att.getTypeArrayDimensionality());
		assertEquals(0, att.getTypeTypeArgumentNamesSize());
		
		// String[] stringDoubleArray
		att = getField(testClass, 3);
		assertEquals("stringDoubleArray", att.getName());
		assertEquals("java.lang.String[][]", att.getTypeName());
		assertEquals(true, att.typeIsArray());
		assertEquals("java.lang.String", att.getTypeArrayComponentTypeName());
		assertEquals(2, att.getTypeArrayDimensionality());
		assertEquals(0, att.getTypeTypeArgumentNamesSize());
		
		// T generic
		att = getField(testClass, 4);
		assertEquals("generic", att.getName());
		assertEquals("java.lang.Number", att.getTypeName());
		assertEquals(false, att.typeIsArray());
		assertEquals(null, att.getTypeArrayComponentTypeName());
		assertEquals(0, att.getTypeArrayDimensionality());
		assertEquals(0, att.getTypeTypeArgumentNamesSize());
		
		// List<T> genericList
		att = getField(testClass, 5);
		assertEquals("genericList", att.getName());
		assertEquals("java.util.List", att.getTypeName());
		assertEquals(false, att.typeIsArray());
		assertEquals(null, att.getTypeArrayComponentTypeName());
		assertEquals(0, att.getTypeArrayDimensionality());
		assertEquals(1, att.getTypeTypeArgumentNamesSize());
		assertEquals("java.lang.Number", att.getTypeTypeArgumentName(0));
		
		// T[] genericArray
		att = getField(testClass, 6);
		assertEquals("genericArray", att.getName());
		assertEquals("java.lang.Number[]", att.getTypeName());
		assertEquals(true, att.typeIsArray());
		assertEquals("java.lang.Number", att.getTypeArrayComponentTypeName());
		assertEquals(1, att.getTypeArrayDimensionality());
		assertEquals(0, att.getTypeTypeArgumentNamesSize());
		
		// List<?> wildcardList
		att = getField(testClass, 7);
		assertEquals("wildcardList", att.getName());
		assertEquals("java.util.List", att.getTypeName());
		assertEquals(false, att.typeIsArray());
		assertEquals(null, att.getTypeArrayComponentTypeName());
		assertEquals(0, att.getTypeArrayDimensionality());
		assertEquals(1, att.getTypeTypeArgumentNamesSize());
		assertEquals("java.lang.Object", att.getTypeTypeArgumentName(0));
	}
}
