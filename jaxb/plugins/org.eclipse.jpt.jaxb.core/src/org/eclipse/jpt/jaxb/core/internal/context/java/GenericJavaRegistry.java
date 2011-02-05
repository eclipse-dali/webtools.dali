/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;


public class GenericJavaRegistry
		extends AbstractJavaType
		implements JaxbRegistry {

	protected final ElementFactoryMethodContainer elementFactoryMethodContainer;

	private static final String ELEMENT_FACTORY_METHOD_CREATE_PREFIX = "create"; //$NON-NLS-1$

	public GenericJavaRegistry(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.elementFactoryMethodContainer = new ElementFactoryMethodContainer();
	}

	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}
	
	protected XmlRegistryAnnotation getAnnotation() {
		return (XmlRegistryAnnotation) getJavaResourceType().getNonNullAnnotation(XmlRegistryAnnotation.ANNOTATION_NAME);
	}
	
	
	// ********** JaxbType impl **********
	
	public Kind getKind() {
		return Kind.REGISTRY;
	}
	
	// ********** synchronize/update **********
	
	public void synchronizeWithResourceModel() {
		this.elementFactoryMethodContainer.synchronizeWithResourceModel();
	}
	
	public void update() {
		this.elementFactoryMethodContainer.update();
	}

	public Iterable<JaxbElementFactoryMethod> getElementFactoryMethods() {
		return this.elementFactoryMethodContainer.getContextElements();
	}

	public int getElementFactoryMethodsSize() {
		return this.elementFactoryMethodContainer.getContextElementsSize();
	}

	private JaxbElementFactoryMethod buildElementFactoryMethod(JavaResourceMethod resourceMethod) {
		return getFactory().buildJavaElementFactoryMethod(this, resourceMethod);
	}

	private Iterable<JavaResourceMethod> getResourceElementFactoryMethods() {
		return new FilteringIterable<JavaResourceMethod>(getJavaResourceType().getMethods()) {
			@Override
			protected boolean accept(JavaResourceMethod method) {
				return methodIsElementFactoryMethod(method);
			}
		};
	}

	//For now we will just check that the method has an @XmlElementDecl annotation.
	//In the future we could look for methods that are unannotated, but appear
	//to be element factory methods : begin with create, return type is JAXB element,
	//1 parameter, etc.
	protected static boolean methodIsElementFactoryMethod(JavaResourceMethod method) {
		return methodHasXmlElementDeclAnnotation(method);
	}

	protected static boolean methodHasXmlElementDeclAnnotation(JavaResourceMethod method) {
		return method.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME) != null;
	}
	
	protected static boolean methodStartsWithCreate(JavaResourceMethod method) {
		return method.getName().startsWith(ELEMENT_FACTORY_METHOD_CREATE_PREFIX);
	}

	protected static boolean methodReturnTypeIsJAXBElement(JavaResourceMethod method) {
		return method.typeIsSubTypeOf(JAXB_ELEMENT_TYPE_NAME);
	}

	protected static final String JAXB_ELEMENT_TYPE_NAME = "javax.xml.bind.JAXBElement"; //$NON-NLS-1$
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : super.getValidationTextRange(astRoot);
	}
	
	
	/**
	 * element factory method container adapter
	 */
	protected class ElementFactoryMethodContainer
		extends ContextCollectionContainer<JaxbElementFactoryMethod, JavaResourceMethod>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return ELEMENT_FACTORY_METHODS_COLLECTION;
		}
		@Override
		protected JaxbElementFactoryMethod buildContextElement(JavaResourceMethod resourceElement) {
			return GenericJavaRegistry.this.buildElementFactoryMethod(resourceElement);
		}
		@Override
		protected Iterable<JavaResourceMethod> getResourceElements() {
			return GenericJavaRegistry.this.getResourceElementFactoryMethods();
		}
		@Override
		protected JavaResourceMethod getResourceElement(JaxbElementFactoryMethod contextElement) {
			return contextElement.getResourceMethod();
		}
	}

}
