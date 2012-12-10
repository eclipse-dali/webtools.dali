/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;

@SuppressWarnings("nls")
public class XmlJoinNodeAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlJoinNodeAnnotationTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return ELJaxb_2_2_PlatformDefinition.instance().getAnnotationDefinitions();
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return ELJaxb_2_2_PlatformDefinition.instance().getNestableAnnotationDefinitions();
	}
	
	
	private ICompilationUnit createTestXmlJoinNode()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_JOIN_NODE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNode");
			}
		});
	}
	
	private ICompilationUnit createTestXmlJoinNodeWithXmlPath()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_JOIN_NODE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNode(xmlPath=\"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestXmlJoinNodeWithReferencedXmlPath()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_JOIN_NODE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNode(referencedXmlPath=\"foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestXmlJoinNodes()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_JOIN_NODES, ELJaxb.XML_JOIN_NODE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNodes({@XmlJoinNode,@XmlJoinNode})");
			}
		});
	}
	
	private XmlJoinNodeAnnotation getXmlJoinNodeAnnotation(JavaResourceAttribute resourceAttribute) {
		return getXmlJoinNodeAnnotation(resourceAttribute, 0);
	}
	
	private XmlJoinNodeAnnotation getXmlJoinNodeAnnotation(JavaResourceAttribute resourceAttribute, int index) {
		return (XmlJoinNodeAnnotation) resourceAttribute.getAnnotation(index, ELJaxb.XML_JOIN_NODE);
	}
	
	
	public void testXmlPath() throws Exception {
		ICompilationUnit cu = createTestXmlJoinNodeWithXmlPath();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlJoinNodeAnnotation annotation = getXmlJoinNodeAnnotation(resourceAttribute);
		
		assertEquals("foo", annotation.getXmlPath());
		assertSourceContains("@XmlJoinNode(xmlPath=\"foo\")", cu);
		
		annotation.setXmlPath("bar");
		
		assertEquals("bar", annotation.getXmlPath());
		assertSourceContains("@XmlJoinNode(xmlPath=\"bar\")", cu);
		
		annotation.setXmlPath("");
		
		assertEquals("", annotation.getXmlPath());
		assertSourceContains("@XmlJoinNode(xmlPath=\"\")", cu);
		
		annotation.setXmlPath(null);
		
		assertNull(annotation.getXmlPath());
		assertSourceContains("@XmlJoinNode", cu);
		assertSourceDoesNotContain("@XmlJoinNode(", cu);
	}
	
	public void testReferencedXmlPath() throws Exception {
		ICompilationUnit cu = createTestXmlJoinNodeWithReferencedXmlPath();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlJoinNodeAnnotation annotation = getXmlJoinNodeAnnotation(resourceAttribute);
		
		assertEquals("foo", annotation.getReferencedXmlPath());
		assertSourceContains("@XmlJoinNode(referencedXmlPath=\"foo\")", cu);
		
		annotation.setReferencedXmlPath("bar");
		
		assertEquals("bar", annotation.getReferencedXmlPath());
		assertSourceContains("@XmlJoinNode(referencedXmlPath=\"bar\")", cu);
		
		annotation.setReferencedXmlPath("");
		
		assertEquals("", annotation.getReferencedXmlPath());
		assertSourceContains("@XmlJoinNode(referencedXmlPath=\"\")", cu);
		
		annotation.setReferencedXmlPath(null);
		
		assertNull(annotation.getReferencedXmlPath());
		assertSourceContains("@XmlJoinNode", cu);
		assertSourceDoesNotContain("@XmlJoinNode(", cu);
	}
	
	public void testContainedXmlPath() throws Exception {
		// test contained annotation xmlPath setting/updating
		
		ICompilationUnit cu = createTestXmlJoinNodes();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlJoinNodeAnnotation annotation = getXmlJoinNodeAnnotation(resourceAttribute);
		
		assertNull(annotation.getXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode,@XmlJoinNode})", cu);
		
		annotation.setXmlPath("foo");
		assertEquals("foo", annotation.getXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode(xmlPath = \"foo\"),@XmlJoinNode})", cu);
		
		annotation.setXmlPath(null);
		assertNull(annotation.getXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode,@XmlJoinNode})", cu);
	}
	
	public void testContainedReferencedXmlPath() throws Exception {
		// test contained annotation referencedXmlPath setting/updating
		
		ICompilationUnit cu = createTestXmlJoinNodes();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlJoinNodeAnnotation annotation = getXmlJoinNodeAnnotation(resourceAttribute);
		
		assertNull(annotation.getReferencedXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode,@XmlJoinNode})", cu);
		
		annotation.setReferencedXmlPath("foo");
		assertEquals("foo", annotation.getReferencedXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode(referencedXmlPath = \"foo\"),@XmlJoinNode})", cu);
		
		annotation.setReferencedXmlPath(null);
		assertNull(annotation.getReferencedXmlPath());
		assertSourceContains(
				"@XmlJoinNodes({@XmlJoinNode,@XmlJoinNode})", cu);
	}
	
	public void testContained()
			throws Exception {
		// test adding/removing/moving
		
		ICompilationUnit cu = createTestXmlJoinNode();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		
		assertEquals(1, resourceAttribute.getAnnotationsSize(ELJaxb.XML_JOIN_NODE));
		
		resourceAttribute.addAnnotation(1, ELJaxb.XML_JOIN_NODE);
		
		assertEquals(2, resourceAttribute.getAnnotationsSize(ELJaxb.XML_JOIN_NODE));
		assertSourceContains("@XmlJoinNodes({ @XmlJoinNode, @XmlJoinNode })", cu);
		
		XmlJoinNodeAnnotation annotation1 = getXmlJoinNodeAnnotation(resourceAttribute, 0);
		annotation1.setXmlPath("foo");
		XmlJoinNodeAnnotation annotation2 = getXmlJoinNodeAnnotation(resourceAttribute, 1);
		annotation2.setXmlPath("bar");
		assertSourceContains(
				"@XmlJoinNodes({ @XmlJoinNode(xmlPath = \"foo\"), @XmlJoinNode(xmlPath = \"bar\") })", cu);
		
		resourceAttribute.moveAnnotation(0, 1, ELJaxb.XML_JOIN_NODE);
		assertSourceContains(
				"@XmlJoinNodes({ @XmlJoinNode(xmlPath = \"bar\"), @XmlJoinNode(xmlPath = \"foo\") })", cu);
		
		resourceAttribute.removeAnnotation(0, ELJaxb.XML_JOIN_NODE);
		assertEquals(1, resourceAttribute.getAnnotationsSize(ELJaxb.XML_JOIN_NODE));
		assertSourceContains(
				"@XmlJoinNode(xmlPath = \"foo\")", cu);
		assertSourceDoesNotContain("@XmlJoinNodes", cu);
	}
}
