/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaRegistryTests extends JaxbContextModelTestCase
{
	
	public GenericJavaRegistryTests(String name) {
		super(name);
	}
	
	protected ICompilationUnit createClassWithXmlRegistryAndCreateMethods() throws Exception {
		return this.createTestType(PACKAGE_NAME, "ObjectFactory.java", "ObjectFactory", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_REGISTRY, JAXB.XML_ELEMENT_DECL, "javax.xml.bind.JAXBElement");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRegistry");
			}
			
			@Override
			public void appendGetNameMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementDecl(name=\"foo\")").append(CR);
				sb.append("    JAXBElement<AnnotationTestType> createFoo(AnnotationTestType value) {return null}").append(CR);
				sb.append(CR);
				sb.append("    @XmlElementDecl(name=\"bar\")").append(CR);
				sb.append("    JAXBElement createBar(Object value) {return null}").append(CR);
				sb.append(CR);
			}
		});
	}
	
	
	protected void addElementFactoryMethod(TypeDeclaration typeDeclaration, String methodName) {
		AST ast = typeDeclaration.getAST();
		MethodDeclaration methodDeclaration = this.newMethodDeclaration(ast, methodName);
		Block body = ast.newBlock();
		methodDeclaration.setBody(body);
		methodDeclaration.setReturnType2(ast.newSimpleType(ast.newName("JAXBElement")));
		SingleVariableDeclaration parameter = ast.newSingleVariableDeclaration();
		parameter.setName(ast.newSimpleName("value"));
		parameter.setType(ast.newSimpleType(ast.newName("Object")));
		parameters(methodDeclaration).add(parameter);
		NormalAnnotation elementDeclAnnotation = this.newNormalAnnotation(ast, "XmlElementDecl");
		modifiers(methodDeclaration).add(elementDeclAnnotation);
		this.bodyDeclarations(typeDeclaration).add(methodDeclaration);
	}

	protected MethodDeclaration newMethodDeclaration(AST ast, String methodName) {
		MethodDeclaration methodDeclaration = ast.newMethodDeclaration();
		methodDeclaration.setName(ast.newSimpleName(methodName));
		return methodDeclaration;
	}

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected List<BodyDeclaration> bodyDeclarations(TypeDeclaration td) {
		return td.bodyDeclarations();
	}

	@SuppressWarnings("unchecked")
	protected List<SingleVariableDeclaration> parameters(MethodDeclaration md) {
		return md.parameters();
	}

	@SuppressWarnings("unchecked")
	protected List<IExtendedModifier> modifiers(MethodDeclaration md) {
		return md.modifiers();
	}

	protected void removeMethodDeclaration(TypeDeclaration typeDeclaration, String methodName) {
		for (MethodDeclaration md : typeDeclaration.getMethods()) {
			if (md.getName().getFullyQualifiedName().equals(methodName)) {
				this.bodyDeclarations(typeDeclaration).remove(md);
				break;
			}
		}
	}

	public void testUpdateElementFactoryMethods() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();
		
		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry contextRegistry = jaxbClass.getXmlRegistry();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createFoo", elementFactoryMethod.getName());
		assertEquals("foo", elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar", elementFactoryMethod.getName());
		assertEquals("bar", elementFactoryMethod.getQName().getName());
		assertFalse(elementFactoryMethods.hasNext());

		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaRegistryTests.this.addElementFactoryMethod((TypeDeclaration) declaration.getDeclaration(), "createFoo2");
				GenericJavaRegistryTests.this.addElementFactoryMethod((TypeDeclaration) declaration.getDeclaration(), "createBar2");
			}
		});
		assertEquals(4, contextRegistry.getElementFactoryMethodsSize());
		elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createFoo", elementFactoryMethod.getName());
		assertEquals("foo", elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar", elementFactoryMethod.getName());
		assertEquals("bar", elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createFoo2", elementFactoryMethod.getName());
		assertEquals(null, elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar2", elementFactoryMethod.getName());
		assertEquals(null, elementFactoryMethod.getQName().getName());
		assertFalse(elementFactoryMethods.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaRegistryTests.this.removeMethodDeclaration((TypeDeclaration) declaration.getDeclaration(), "createFoo");
			}
		});
		assertEquals(3, contextRegistry.getElementFactoryMethodsSize());
		elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar", elementFactoryMethod.getName());
		assertEquals("bar", elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createFoo2", elementFactoryMethod.getName());
		assertEquals(null, elementFactoryMethod.getQName().getName());
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar2", elementFactoryMethod.getName());
		assertEquals(null, elementFactoryMethod.getQName().getName());
		assertFalse(elementFactoryMethods.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaRegistryTests.this.removeMethodDeclaration((TypeDeclaration) declaration.getDeclaration(), "createFoo2");
				GenericJavaRegistryTests.this.removeMethodDeclaration((TypeDeclaration) declaration.getDeclaration(), "createBar2");
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
		elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		elementFactoryMethod = elementFactoryMethods.next();
		assertEquals("createBar", elementFactoryMethod.getName());
		assertEquals("bar", elementFactoryMethod.getQName().getName());
		assertFalse(elementFactoryMethods.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaRegistryTests.this.removeMethodDeclaration((TypeDeclaration) declaration.getDeclaration(), "createBar");
			}
		});
		assertEquals(0, contextRegistry.getElementFactoryMethodsSize());
		assertFalse(contextRegistry.getElementFactoryMethods().iterator().hasNext());
	}
}
