/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class ELJavaXmlJoinNodesMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlJoinNodesMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected JaxbPlatformConfig getPlatformConfig() {
		return ELJaxbPlatform.VERSION_2_2;
	}
	
	private ICompilationUnit createTypeWithXmlJoinNodes() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, ELJaxb.XML_JOIN_NODES);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNodes");
			}
		});
	}
	
	protected NormalAnnotation newXmlJoinNodeAnnotation(AST ast, String xmlPath, String referencedXmlPath) {
		NormalAnnotation annotation = newNormalAnnotation(ast, ELJaxb.XML_JOIN_NODE);
		addMemberValuePair(annotation, ELJaxb.XML_JOIN_NODE__XML_PATH, xmlPath);
		addMemberValuePair(annotation, ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH, referencedXmlPath);
		return annotation;
	}
	
	protected void addXmlJoinNode(ModifiedDeclaration declaration, int index, String xmlPath, String referencedXmlPath) {
		NormalAnnotation annotation = newXmlJoinNodeAnnotation(declaration.getAst(), xmlPath, referencedXmlPath);
		addArrayElement(declaration, ELJaxb.XML_JOIN_NODES, index, "value", annotation);		
	}
	
	protected void moveXmlJoinNode(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_JOIN_NODES), "value", targetIndex, sourceIndex);
	}
	
	protected void removeXmlJoinNode(ModifiedDeclaration declaration, int index) {
		removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_JOIN_NODES), "value", index);
	}
	
	
	public void testUpdateXmlJoinNodes() throws Exception {
		createTypeWithXmlJoinNodes();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		ELXmlJoinNodesMapping mapping = (ELXmlJoinNodesMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		Iterable<ELXmlJoinNode> xmlJoinNodes = mapping.getXmlJoinNodes();
		assertTrue(IterableTools.isEmpty(xmlJoinNodes));
		assertEquals(0, mapping.getXmlJoinNodesSize());
		
		//add 2 XmlJoinNode annotations
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlJoinNodesMappingTests.this.addXmlJoinNode(declaration, 0, "foo", "@foo");
						ELJavaXmlJoinNodesMappingTests.this.addXmlJoinNode(declaration, 1, "bar", "@bar");
					}
				});
		
		xmlJoinNodes = mapping.getXmlJoinNodes();
		
		assertFalse(IterableTools.isEmpty(xmlJoinNodes));
		assertEquals(2, mapping.getXmlJoinNodesSize());
		assertEquals("foo", IterableTools.get(xmlJoinNodes, 0).getXmlPath());
		assertEquals("@foo", IterableTools.get(xmlJoinNodes, 0).getReferencedXmlPath());
		assertEquals("bar", IterableTools.get(xmlJoinNodes, 1).getXmlPath());
		assertEquals("@bar", IterableTools.get(xmlJoinNodes, 1).getReferencedXmlPath());
		
		// switch XmlJoinNode annotations
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlJoinNodesMappingTests.this.moveXmlJoinNode(declaration, 0, 1);
					}
				});
		
		xmlJoinNodes = mapping.getXmlJoinNodes();
		
		assertFalse(IterableTools.isEmpty(xmlJoinNodes));
		assertEquals(2, mapping.getXmlJoinNodesSize());
		assertEquals("bar", IterableTools.get(xmlJoinNodes, 0).getXmlPath());
		assertEquals("@bar", IterableTools.get(xmlJoinNodes, 0).getReferencedXmlPath());
		assertEquals("foo", IterableTools.get(xmlJoinNodes, 1).getXmlPath());
		assertEquals("@foo", IterableTools.get(xmlJoinNodes, 1).getReferencedXmlPath());
		
		// remove XmlJoinNode annotations
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaXmlJoinNodesMappingTests.this.removeXmlJoinNode(declaration, 1);
						ELJavaXmlJoinNodesMappingTests.this.removeXmlJoinNode(declaration, 0);
					}
				});
		
		xmlJoinNodes = mapping.getXmlJoinNodes();
		
		assertTrue(IterableTools.isEmpty(xmlJoinNodes));
		assertEquals(0, mapping.getXmlJoinNodesSize());
	}

	public void testModifyXmlJoinNodes() throws Exception {
		createTypeWithXmlJoinNodes();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		ELXmlJoinNodesMapping mapping = (ELXmlJoinNodesMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertEquals(0, resourceAttribute.getAnnotationsSize(ELJaxb.XML_JOIN_NODE));
		assertEquals(0, mapping.getXmlJoinNodesSize());
		
		ELXmlJoinNode joinNode = mapping.addXmlJoinNode(0);
		joinNode.setXmlPath("foo");
		joinNode.setReferencedXmlPath("@foo");
		joinNode = mapping.addXmlJoinNode(1);
		joinNode.setXmlPath("baz");
		joinNode.setReferencedXmlPath("@baz");
		joinNode = mapping.addXmlJoinNode(1);
		joinNode.setXmlPath("bar");
		joinNode.setReferencedXmlPath("@bar");
		
		Iterable<XmlJoinNodeAnnotation> xmlJoinNodeAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_JOIN_NODE));
		
		assertEquals(3, IterableTools.size(xmlJoinNodeAnnotations));
		assertEquals(3, mapping.getXmlJoinNodesSize());
		assertEquals("foo", IterableTools.get(xmlJoinNodeAnnotations, 0).getXmlPath());
		assertEquals("@foo", IterableTools.get(xmlJoinNodeAnnotations, 0).getReferencedXmlPath());
		assertEquals("bar", IterableTools.get(xmlJoinNodeAnnotations, 1).getXmlPath());
		assertEquals("@bar", IterableTools.get(xmlJoinNodeAnnotations, 1).getReferencedXmlPath());
		assertEquals("baz", IterableTools.get(xmlJoinNodeAnnotations, 2).getXmlPath());
		assertEquals("@baz", IterableTools.get(xmlJoinNodeAnnotations, 2).getReferencedXmlPath());
		
		mapping.moveXmlJoinNode(1, 2);
		
		xmlJoinNodeAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_JOIN_NODE));
		
		assertEquals(3, IterableTools.size(xmlJoinNodeAnnotations));
		assertEquals(3, mapping.getXmlJoinNodesSize());
		assertEquals("foo", IterableTools.get(xmlJoinNodeAnnotations, 0).getXmlPath());
		assertEquals("@foo", IterableTools.get(xmlJoinNodeAnnotations, 0).getReferencedXmlPath());
		assertEquals("baz", IterableTools.get(xmlJoinNodeAnnotations, 1).getXmlPath());
		assertEquals("@baz", IterableTools.get(xmlJoinNodeAnnotations, 1).getReferencedXmlPath());
		assertEquals("bar", IterableTools.get(xmlJoinNodeAnnotations, 2).getXmlPath());
		assertEquals("@bar", IterableTools.get(xmlJoinNodeAnnotations, 2).getReferencedXmlPath());
		
		mapping.removeXmlJoinNode(2);
		mapping.removeXmlJoinNode(0);
		mapping.removeXmlJoinNode(0);
		
		xmlJoinNodeAnnotations = 
				new SubIterableWrapper(resourceAttribute.getAnnotations(ELJaxb.XML_JOIN_NODE));
		
		assertEquals(0, IterableTools.size(xmlJoinNodeAnnotations));
		assertEquals(0, mapping.getXmlJoinNodesSize());
	}
}
