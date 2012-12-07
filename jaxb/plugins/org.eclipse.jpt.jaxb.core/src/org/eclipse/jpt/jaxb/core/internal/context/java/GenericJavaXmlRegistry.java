/*******************************************************************************
 *  Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaXmlRegistry
		extends AbstractJavaContextNode
		implements XmlRegistry {

	protected final ElementFactoryMethodContainer elementFactoryMethodContainer;
	
	
	public GenericJavaXmlRegistry(JavaClass parent) {
		super(parent);
		this.elementFactoryMethodContainer = new ElementFactoryMethodContainer();
		
		initElementFactoryMethods();
	}
	
	
	public JavaClass getJaxbClass() {
		return (JavaClass) getParent();
	}
	
	public JavaResourceType getJavaResourceType() {
		return getJaxbClass().getJavaResourceType();
	}
	
	protected XmlRegistryAnnotation getAnnotation() {
		return (XmlRegistryAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_REGISTRY);
	}
	
	public JaxbPackage getJaxbPackage() {
		return getJaxbClass().getJaxbPackage();
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncElementFactoryMethods();
	}
	
	@Override
	public void update() {
		super.update();
		updateElementFactoryMethods();
	}
	
	
	// ***** element factory methods *****
	
	public Iterable<JaxbElementFactoryMethod> getElementFactoryMethods() {
		return this.elementFactoryMethodContainer.getContextElements();
	}
	
	public int getElementFactoryMethodsSize() {
		return this.elementFactoryMethodContainer.getContextElementsSize();
	}
	
	protected void initElementFactoryMethods() {
		this.elementFactoryMethodContainer.initialize();	
	}
	
	protected void syncElementFactoryMethods() {
		this.elementFactoryMethodContainer.synchronizeWithResourceModel();
	}
	
	protected void updateElementFactoryMethods() {
		this.elementFactoryMethodContainer.update();	
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
		return method.getTypeBinding().isSubTypeOf(JAXB.XML_ELEMENT);
	}
	
	
	// ***** content assist *****
	
	 @Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		for (JaxbElementFactoryMethod efm : getElementFactoryMethods()) {
			result = efm.getCompletionProposals(pos);
				if (! IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.<String>instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = getAnnotation().getTextRange();
		return (textRange != null) ? textRange : getJaxbClass().getValidationTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		Iterable<XmlRegistry> registries = getContextRoot().getXmlRegistries(getJaxbPackage());
		if (IterableTools.size(registries) > 1) {
			messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_REGISTRY__MULTIPLE_XML_REGISTRIES_FOR_PACKAGE,
						this,
						getValidationTextRange()));
		}
		
		validateDuplicateQNames(messages, reporter);
		
		for (JaxbElementFactoryMethod efm : getElementFactoryMethods()) {
			efm.validate(messages, reporter);
		}
	}
	
	protected void validateDuplicateQNames(List<IMessage> messages, IReporter reporter) {
		
		Map<String, Bag<QName>> xmlElementDeclQnames = new HashMap<String, Bag<QName>>();
		
		for (JaxbElementFactoryMethod xmlElementDecl : getElementFactoryMethods()) {
			String elementDeclName = xmlElementDecl.getQName().getName();
			if (! StringTools.isBlank(elementDeclName)) {
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
				String scopeDesc = ""; //$NON-NLS-1$
				if (! JaxbElementFactoryMethod.DEFAULT_SCOPE_CLASS_NAME.equals(fqScope)) {
					scopeDesc = NLS.bind(JptJaxbCoreMessages.XML_ELEMENT_DECL__SCOPE, fqScope);
				}
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_REGISTRY__DUPLICATE_XML_ELEMENT_QNAME,
								new String[] { xmlElementName, scopeDesc },
								xmlElementDecl,
								xmlElementDecl.getQName().getNameTextRange()));
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
			return XmlRegistry.ELEMENT_FACTORY_METHODS_COLLECTION;
		}
		
		@Override
		protected JaxbElementFactoryMethod buildContextElement(JavaResourceMethod resourceElement) {
			return GenericJavaXmlRegistry.this.buildElementFactoryMethod(resourceElement);
		}
		
		@Override
		protected Iterable<JavaResourceMethod> getResourceElements() {
			return GenericJavaXmlRegistry.this.getResourceElementFactoryMethods();
		}
		
		@Override
		protected JavaResourceMethod getResourceElement(JaxbElementFactoryMethod contextElement) {
			return contextElement.getResourceMethod();
		}
	}
}
