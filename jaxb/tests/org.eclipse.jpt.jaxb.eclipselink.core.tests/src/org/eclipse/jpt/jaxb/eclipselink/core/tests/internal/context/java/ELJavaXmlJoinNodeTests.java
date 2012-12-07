package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlJoinNodesMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaXmlJoinNodeTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlJoinNodeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected JaxbPlatformConfig getPlatformConfig() {
		return ELJaxbPlatform.VERSION_2_2;
	}
	
	private ICompilationUnit createTypeWithXmlJoinNode() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, ELJaxb.XML_JOIN_NODE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJoinNode");
			}
		});
	}
	
	
	public void testModifyXmlPath() throws Exception {
		createTypeWithXmlJoinNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELXmlJoinNodesMapping mapping = (ELXmlJoinNodesMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlJoinNode xmlJoinNode = IterableTools.get(mapping.getXmlJoinNodes(), 0);
		XmlJoinNodeAnnotation annotation = (XmlJoinNodeAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_JOIN_NODE);
		
		assertNull(annotation.getXmlPath());
		assertNull(xmlJoinNode.getXmlPath());
		
		xmlJoinNode.setXmlPath("foo");
		
		assertEquals("foo", annotation.getXmlPath());
		assertEquals("foo", xmlJoinNode.getXmlPath());
		
		xmlJoinNode.setXmlPath("");
		
		assertEquals("", annotation.getXmlPath());
		assertEquals("", xmlJoinNode.getXmlPath());
		
		xmlJoinNode.setXmlPath(null);
		
		assertNull(annotation.getXmlPath());
		assertNull(xmlJoinNode.getXmlPath());
	}

	public void testUpdateXmlPath() throws Exception {
		createTypeWithXmlJoinNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlJoinNodesMapping mapping = (ELJavaXmlJoinNodesMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlJoinNode xmlJoinNode = IterableTools.get(mapping.getXmlJoinNodes(), 0);
		XmlJoinNodeAnnotation annotation = (XmlJoinNodeAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_JOIN_NODE);
		
		assertNull(annotation.getXmlPath());
		assertNull(xmlJoinNode.getXmlPath());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__XML_PATH, "foo");
			}
		});
		
		assertEquals("foo", annotation.getXmlPath());
		assertEquals("foo", xmlJoinNode.getXmlPath());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__XML_PATH, "");
			}
		});
		
		assertEquals("", annotation.getXmlPath());
		assertEquals("", xmlJoinNode.getXmlPath());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.removeMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__XML_PATH);
			}
		});
		
		assertNull(annotation.getXmlPath());
		assertNull(xmlJoinNode.getXmlPath());
	}
	
	public void testModifyReferencedXmlPath() throws Exception {
		createTypeWithXmlJoinNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELXmlJoinNodesMapping mapping = (ELXmlJoinNodesMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlJoinNode xmlJoinNode = IterableTools.get(mapping.getXmlJoinNodes(), 0);
		XmlJoinNodeAnnotation annotation = (XmlJoinNodeAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_JOIN_NODE);
		
		assertNull(annotation.getReferencedXmlPath());
		assertNull(xmlJoinNode.getReferencedXmlPath());
		
		xmlJoinNode.setReferencedXmlPath("foo");
		
		assertEquals("foo", annotation.getReferencedXmlPath());
		assertEquals("foo", xmlJoinNode.getReferencedXmlPath());
		
		xmlJoinNode.setReferencedXmlPath("");
		
		assertEquals("", annotation.getReferencedXmlPath());
		assertEquals("", xmlJoinNode.getReferencedXmlPath());
		
		xmlJoinNode.setReferencedXmlPath(null);
		
		assertNull(annotation.getReferencedXmlPath());
		assertNull(xmlJoinNode.getReferencedXmlPath());
	}

	public void testUpdateReferencedXmlPath() throws Exception {
		createTypeWithXmlJoinNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlJoinNodesMapping mapping = (ELJavaXmlJoinNodesMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlJoinNode xmlJoinNode = IterableTools.get(mapping.getXmlJoinNodes(), 0);
		XmlJoinNodeAnnotation annotation = (XmlJoinNodeAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_JOIN_NODE);
		
		assertNull(annotation.getReferencedXmlPath());
		assertNull(xmlJoinNode.getReferencedXmlPath());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH, "foo");
			}
		});
		
		assertEquals("foo", annotation.getReferencedXmlPath());
		assertEquals("foo", xmlJoinNode.getReferencedXmlPath());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH, "");
			}
		});
		
		assertEquals("", annotation.getReferencedXmlPath());
		assertEquals("", xmlJoinNode.getReferencedXmlPath());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlJoinNodeTests.this.removeMemberValuePair(
						declaration, ELJaxb.XML_JOIN_NODE, ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH);
			}
		});
		
		assertNull(annotation.getReferencedXmlPath());
		assertNull(xmlJoinNode.getReferencedXmlPath());
	}
}