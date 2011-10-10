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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Bag;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaRegistry
		extends AbstractJavaType
		implements JaxbRegistry {

	protected final ContextCollectionContainer<JaxbElementFactoryMethod, JavaResourceMethod> elementFactoryMethodContainer;
	
	
	public GenericJavaRegistry(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.elementFactoryMethodContainer = this.buildElementFactoryMethodContainer();
	}
	
	
	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}
	
	protected XmlRegistryAnnotation getAnnotation() {
		return (XmlRegistryAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_REGISTRY);
	}
	
	
	// ***** JaxbType impl *****
	
	public Kind getKind() {
		return Kind.REGISTRY;
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.elementFactoryMethodContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
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
	
	// Return methods with XmlElementDecl annotation
	protected static boolean methodIsElementFactoryMethod(JavaResourceMethod method) {
		return methodHasXmlElementDeclAnnotation(method);
	}
	
	protected static boolean methodHasXmlElementDeclAnnotation(JavaResourceMethod method) {
		return method.getAnnotation(JAXB.XML_ELEMENT_DECL) != null;
	}
	
	protected static boolean methodReturnTypeIsJAXBElement(JavaResourceMethod method) {
		return method.typeIsSubTypeOf(JAXB.XML_ELEMENT);
	}

	protected ContextCollectionContainer<JaxbElementFactoryMethod, JavaResourceMethod> buildElementFactoryMethodContainer() {
		ElementFactoryMethodContainer container = new ElementFactoryMethodContainer();
		container.initialize();
		return container;
	}
	
	
	// ***** content assist *****
	
	 @Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		for (JaxbElementFactoryMethod efm : getElementFactoryMethods()) {
			result = efm.getJavaCompletionProposals(pos, filter, astRoot);
				if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.<String>instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : super.getValidationTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validateDuplicateQNames(messages, reporter, astRoot);
		
		for (JaxbElementFactoryMethod efm : getElementFactoryMethods()) {
			efm.validate(messages, reporter, astRoot);
		}
	}
	
	protected void validateDuplicateQNames(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		
		Map<String, Bag<QName>> xmlElementDeclQnames = new HashMap<String, Bag<QName>>();
		
		for (JaxbElementFactoryMethod xmlElementDecl : getElementFactoryMethods()) {
			String elementDeclName = xmlElementDecl.getQName().getName();
			if (! StringTools.stringIsEmpty(elementDeclName)) {
				String fqScope = xmlElementDecl.getFullyQualifiedScope();
				if (xmlElementDeclQnames.get(fqScope) == null) {
					xmlElementDeclQnames.put(fqScope, new HashBag<QName>());
				}
				xmlElementDeclQnames.get(fqScope).add(new QName(xmlElementDecl.getQName().getNamespace(), elementDeclName));
			}
		}
		
		for (JaxbElementFactoryMethod xmlElementDecl : getElementFactoryMethods()) {
			String fqScope = xmlElementDecl.getFullyQualifiedScope();
			String xmlElementNamespace = xmlElementDecl.getQName().getNamespace();
			String xmlElementName = xmlElementDecl.getQName().getName();
			if (xmlElementDeclQnames.get(fqScope).count(new QName(xmlElementNamespace, xmlElementName)) > 1) {
				String scopeDesc = "";
				if (! JaxbElementFactoryMethod.DEFAULT_SCOPE_CLASS_NAME.equals(fqScope)) {
					scopeDesc = NLS.bind(JptJaxbCoreMessages.XML_ELEMENT_DECL__SCOPE, fqScope);
				}
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_REGISTRY__DUPLICATE_XML_ELEMENT_QNAME,
								new String[] { xmlElementName, scopeDesc },
								xmlElementDecl,
								xmlElementDecl.getQName().getNameTextRange(astRoot)));
			}
		}
	}
	
	
	/**
	 * element factory method container adapter
	 */
	protected class ElementFactoryMethodContainer
			extends ContextCollectionContainer<JaxbElementFactoryMethod, JavaResourceMethod> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return JaxbRegistry.ELEMENT_FACTORY_METHODS_COLLECTION;
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
