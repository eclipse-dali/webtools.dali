package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlElementsMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaXmlElementsMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlElementsMappingTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlElements() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENTS);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElements");
			}
		});
	}
	
	protected NormalAnnotation newXmlPathAnnotation(AST ast, String value) {
		NormalAnnotation annotation = newNormalAnnotation(ast, ELJaxb.XML_PATH);
		addMemberValuePair(annotation, ELJaxb.XML_PATH__VALUE, value);
		return annotation;
	}
	
	protected void addXmlPath(ModifiedDeclaration declaration, int index, String value) {
		NormalAnnotation annotation = newXmlPathAnnotation(declaration.getAst(), value);
		addArrayElement(declaration, ELJaxb.XML_PATHS, index, "value", annotation);		
	}
	
	protected void moveXmlPath(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_PATHS), "value", targetIndex, sourceIndex);
	}
	
	protected void removeXmlPath(ModifiedDeclaration declaration, int index) {
		removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_PATHS), "value", index);
	}
	
	
	public void testUpdateXmlElements() throws Exception {
		createTypeWithXmlElements();
		JaxbClass jaxbClass = (JaxbClass) IterableTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		ELXmlElementsMapping mapping = (ELXmlElementsMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		Iterable<ELXmlPath> xmlPaths = mapping.getXmlPaths();
		assertTrue(IterableTools.isEmpty(xmlPaths));
		assertEquals(0, mapping.getXmlPathsSize());
		
		//add 2 XmlPath annotations
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlElementsMappingTests.this.addXmlPath(declaration, 0, "foo");
						ELJavaXmlElementsMappingTests.this.addXmlPath(declaration, 1, "bar");
					}
				});
		
		xmlPaths = mapping.getXmlPaths();
		
		assertFalse(IterableTools.isEmpty(xmlPaths));
		assertEquals(2, mapping.getXmlPathsSize());
		assertEquals("foo", IterableTools.get(xmlPaths, 0).getValue());
		assertEquals("bar", IterableTools.get(xmlPaths, 1).getValue());
		
		// switch XmlPath annotations
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlElementsMappingTests.this.moveXmlPath(declaration, 0, 1);
					}
				});
		
		xmlPaths = mapping.getXmlPaths();
		
		assertFalse(IterableTools.isEmpty(xmlPaths));
		assertEquals(2, mapping.getXmlPathsSize());
		assertEquals("bar", IterableTools.get(xmlPaths, 0).getValue());
		assertEquals("foo", IterableTools.get(xmlPaths, 1).getValue());
		
		// remove XmlPath annotations
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlElementsMappingTests.this.removeXmlPath(declaration, 1);
						ELJavaXmlElementsMappingTests.this.removeXmlPath(declaration, 0);
					}
				});
		
		xmlPaths = mapping.getXmlPaths();
		
		assertTrue(IterableTools.isEmpty(xmlPaths));
		assertEquals(0, mapping.getXmlPathsSize());
	}

	public void testModifyXmlPaths() throws Exception {
		createTypeWithXmlElements();
		JaxbClass jaxbClass = (JaxbClass) IterableTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		ELXmlElementsMapping mapping = (ELXmlElementsMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertEquals(0, resourceAttribute.getAnnotationsSize(ELJaxb.XML_PATH));
		assertEquals(0, mapping.getXmlPathsSize());
		
		mapping.addXmlPath(0).setValue("foo");
		mapping.addXmlPath(1).setValue("baz");
		mapping.addXmlPath(1).setValue("bar");
		
		Iterable<XmlPathAnnotation> xmlPathAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_PATH));
		
		assertEquals(3, IterableTools.size(xmlPathAnnotations));
		assertEquals(3, mapping.getXmlPathsSize());
		assertEquals("foo", IterableTools.get(xmlPathAnnotations, 0).getValue());
		assertEquals("bar", IterableTools.get(xmlPathAnnotations, 1).getValue());
		assertEquals("baz", IterableTools.get(xmlPathAnnotations, 2).getValue());
		
		mapping.moveXmlPath(1, 2);
		
		xmlPathAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_PATH));
		
		assertEquals(3, IterableTools.size(xmlPathAnnotations));
		assertEquals(3, mapping.getXmlPathsSize());
		assertEquals("foo", IterableTools.get(xmlPathAnnotations, 0).getValue());
		assertEquals("baz", IterableTools.get(xmlPathAnnotations, 1).getValue());
		assertEquals("bar", IterableTools.get(xmlPathAnnotations, 2).getValue());
		
		mapping.removeXmlPath(2);
		mapping.removeXmlPath(0);
		mapping.removeXmlPath(0);
		
		xmlPathAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_PATH));
		
		assertEquals(0, IterableTools.size(xmlPathAnnotations));
		assertEquals(0, mapping.getXmlPathsSize());
	}
}
