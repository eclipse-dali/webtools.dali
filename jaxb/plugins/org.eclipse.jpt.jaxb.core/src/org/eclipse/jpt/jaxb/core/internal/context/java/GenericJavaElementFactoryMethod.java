/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.context.AbstractQName;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaElementFactoryMethod
		extends AbstractJavaContextNode
		implements JaxbElementFactoryMethod {

	protected final JavaResourceMethod resourceMethod;
	
	protected String scope;
	
	protected JaxbQName qName;
	
	protected JaxbQName substitutionHeadQName;
	
	protected String defaultValue;
	
	
	public GenericJavaElementFactoryMethod(XmlRegistry parent, JavaResourceMethod resourceMethod) {
		super(parent);
		this.resourceMethod = resourceMethod;
		this.scope = getResourceScope();
		this.qName = buildQName();
		this.substitutionHeadQName = buildSubstitutionHeadQName();
		this.defaultValue = this.getResourceDefaultValue();
	}
	
	
	protected JaxbQName buildQName() {
		return new XmlElementDeclQName(this);
	}
	
	protected JaxbQName buildSubstitutionHeadQName() {
		return new XmlElementDeclSubstitutionHeadQName(this);
	}
	
	protected XmlRegistry getRegistry() {
		return (XmlRegistry) getParent();
	}
	
	public JavaResourceMethod getResourceMethod() {
		return this.resourceMethod;
	}
	
	protected XmlElementDeclAnnotation getXmlElementDeclAnnotation() {
		return (XmlElementDeclAnnotation) getResourceMethod().getAnnotation(JAXB.XML_ELEMENT_DECL);
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setScope_(getResourceScope());
		this.qName.synchronizeWithResourceModel();
		this.substitutionHeadQName.synchronizeWithResourceModel();
		setDefaultValue_(getResourceDefaultValue());
	}
	
	@Override
	public void update() {
		super.update();
		this.qName.update();
		this.substitutionHeadQName.update();
	}
	

	// ***** JaxbElementFactoryMethod impl *****

	public String getName() {
		return this.resourceMethod.getName();
	}
	
	
	// ***** scope *****
	
	public String getScope() {
		return this.scope;
	}
	
	protected void setScope_(String scope) {
		String old = this.scope;
		this.scope = scope;
		firePropertyChanged(SCOPE_PROPERTY, old, scope);
	}
	
	public void setScope(String scope) {
		getXmlElementDeclAnnotation().setScope(scope);
		setScope_(scope);	
	}
	
	protected String getResourceScope() {
		return getXmlElementDeclAnnotation().getScope();
	}
	
	public String getFullyQualifiedScope() {
		return (this.scope == null) ? DEFAULT_SCOPE_CLASS_NAME : getXmlElementDeclAnnotation().getFullyQualifiedScopeClassName();
	}
	
	public boolean isGlobalScope() {
		return DEFAULT_SCOPE_CLASS_NAME.equals(getFullyQualifiedScope());
	}
	
	
	// ***** qname *****
	
	public JaxbQName getQName() {
		return this.qName;
	}
	
	
	// ***** substitution head qname *****
	
	public JaxbQName getSubstitutionHeadQName() {
		return this.substitutionHeadQName;
	}
	
	
	// ***** default value *****
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	protected void setDefaultValue_(String defaultValue) {
		String old = this.defaultValue;
		this.defaultValue = defaultValue;
		firePropertyChanged(DEFAULT_VALUE_PROPERTY, old, defaultValue);
	}
	
	public void setDefaultValue(String defaultValue) {
		getXmlElementDeclAnnotation().setDefaultValue(defaultValue);
		setDefaultValue_(defaultValue);	
	}
	
	protected String getResourceDefaultValue() {
		return getXmlElementDeclAnnotation().getDefaultValue();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.substitutionHeadQName.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return getXmlElementDeclAnnotation().getTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.qName.validate(messages, reporter);
		this.substitutionHeadQName.validate(messages, reporter);
		
		JavaResourceMethod resourceMethod = getResourceMethod();
		validateMethodReturnType(resourceMethod, messages);
		
		if (resourceMethod.getParametersSize() != 1) {
			messages.add(
					this.buildValidationMessage(
							getValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_PARAM
						));
		}
	}
	
	protected void validateMethodReturnType(
			JavaResourceMethod resourceMethod, List<IMessage> messages) {
		
		if (! JAXB.JAXB_ELEMENT.equals(resourceMethod.getTypeBinding().getQualifiedName())) {
			messages.add(
					this.buildValidationMessage(
							getValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE
						));
		}
	}
	
	
	protected class XmlElementDeclQName
			extends AbstractQName {
		
		protected XmlElementDeclQName(JaxbContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaElementFactoryMethod.this.getRegistry().getJaxbClass().getJaxbPackage();
		}
		
		@Override
		protected String buildDefaultName() {
			return null;
		}
		
		@Override
		protected String buildDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		public Iterable<String> getNameProposals() {
			if (! GenericJavaElementFactoryMethod.this.isGlobalScope()) {
				String fqScope = GenericJavaElementFactoryMethod.this.getFullyQualifiedScope();
				JaxbTypeMapping scopeTypeMapping = 
						GenericJavaElementFactoryMethod.this.getJaxbProject().getContextRoot().getTypeMapping(fqScope);
				if (scopeTypeMapping != null) {
					XsdTypeDefinition xsdType = scopeTypeMapping.getXsdTypeDefinition();
					if (xsdType != null) {
						return xsdType.getElementNameProposals(getNamespace(), true);
					}
				}
			}
			
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				return xsdSchema.getElementNameProposals(getNamespace());
			}
			
			return EmptyIterable.instance(); 
		}
		
		@Override
		public Iterable<String> getNamespaceProposals() {
			XsdSchema xsdSchema = this.getXsdSchema();
			return (xsdSchema == null) ? EmptyIterable.<String>instance() : xsdSchema.getNamespaceProposals();
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			if (! GenericJavaElementFactoryMethod.this.isGlobalScope()) {
				String fqScope = GenericJavaElementFactoryMethod.this.getFullyQualifiedScope();
				JaxbTypeMapping scopeTypeMapping = 
						GenericJavaElementFactoryMethod.this.getJaxbProject().getContextRoot().getTypeMapping(fqScope);
				if (scopeTypeMapping != null) {
					XsdTypeDefinition xsdType = scopeTypeMapping.getXsdTypeDefinition();
					if (xsdType == null) {
						return;
					}
					
					if (xsdType.getElement(getNamespace(), getName(), true) == null) {
						messages.add(getUnresolveSchemaComponentMessage());
					}
				}
			}
			else {
				XsdSchema xsdSchema = this.getXsdSchema();
				if (xsdSchema != null) {
					if (xsdSchema.getElementDeclaration(getNamespace(), getName()) == null) {
						messages.add(getUnresolveSchemaComponentMessage());
					}
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaElementFactoryMethod.this.getXmlElementDeclAnnotation();
		}
	}
	
	
	protected class XmlElementDeclSubstitutionHeadQName
			extends AbstractQName {
		
		protected XmlElementDeclSubstitutionHeadQName(JaxbContextNode parent) {
			super(parent, new SubstitutionHeadQNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaElementFactoryMethod.this.getRegistry().getJaxbClass().getJaxbPackage();
		}
		
		@Override
		protected String buildDefaultName() {
			return null;
		}
		
		@Override
		protected String buildDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		public Iterable<String> getNameProposals() {
			String fqScope = GenericJavaElementFactoryMethod.this.getFullyQualifiedScope();
			JaxbTypeMapping scopeTypeMapping = 
					GenericJavaElementFactoryMethod.this.getJaxbProject().getContextRoot().getTypeMapping(fqScope);
			if (scopeTypeMapping != null) {
				XsdTypeDefinition xsdType = scopeTypeMapping.getXsdTypeDefinition();
				if (xsdType != null) {
					return xsdType.getElementNameProposals(getNamespace());
				}
			}
			
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				return xsdSchema.getElementNameProposals(getNamespace());
			}
			
			return EmptyIterable.instance(); 
		}
		
		@Override
		public Iterable<String> getNamespaceProposals() {
			XsdSchema xsdSchema = this.getXsdSchema();
			return (xsdSchema == null) ? EmptyIterable.<String>instance() : xsdSchema.getNamespaceProposals();
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.SUBSTITUTION_HEAD_DESC;
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter) {
			// need to ignore the unspecified (null) case
			if ("".equals(getName())) {
				messages.add(
						this.buildValidationMessage(
								getNameValidationTextRange(),
								JptJaxbCoreValidationMessages.QNAME__MISSING_NAME,
								getReferencedComponentTypeDescription()));
			}
			else if (! StringTools.isBlank(getName())) {
				if (ObjectTools.equals(getName(), GenericJavaElementFactoryMethod.this.getQName().getName())) {
					messages.add(
							this.buildValidationMessage(
									getNameValidationTextRange(),
									JptJaxbCoreValidationMessages.XML_ELEMENT_DECL__SUBST_HEAD_NAME_EQUALS_NAME
								));
				}
			}
		}
	
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter) {
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				if (xsdSchema.getElementDeclaration(getNamespace(), getName()) == null) {
					messages.add(getUnresolveSchemaComponentMessage());
				}
			}
			
			for (JaxbElementFactoryMethod elementDecl : GenericJavaElementFactoryMethod.this.getRegistry().getElementFactoryMethods()) {
				if (ObjectTools.equals(getName(), elementDecl.getSubstitutionHeadQName().getName())
						&& ObjectTools.equals(getNamespace(), elementDecl.getSubstitutionHeadQName().getNamespace())) {
					return;
				}
			}
			messages.add(
					this.buildValidationMessage(
							getValidationTextRange(),
							JptJaxbCoreValidationMessages.XML_ELEMENT_DECL__SUBST_HEAD_NO_MATCHING_ELEMENT_DECL,
							getNamespace(),
							getName()));
		}
	}
	
	
	protected class SubstitutionHeadQNameAnnotationProxy 
			implements AbstractQName.ResourceProxy {
		
		protected XmlElementDeclAnnotation getAnnotation() {
			return 	GenericJavaElementFactoryMethod.this.getXmlElementDeclAnnotation();
		}
		
		public String getNamespace() {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return annotation == null ? null : annotation.getSubstitutionHeadNamespace();
		}
		
		public void setNamespace(String newSpecifiedNamespace) {
			getAnnotation().setSubstitutionHeadNamespace(newSpecifiedNamespace);
		}
		
		public boolean namespaceTouches(int pos) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? false : annotation.substitutionHeadNamespaceTouches(pos);
		}
		
		public TextRange getNamespaceValidationTextRange() {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? null : annotation.getSubstitutionHeadNamespaceValidationTextRange();
		}
		
		public String getName() {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return annotation == null ? null : annotation.getSubstitutionHeadName();
		}
		
		public void setName(String newSpecifiedName) {
			getAnnotation().setSubstitutionHeadName(newSpecifiedName);
		}
		
		public boolean nameTouches(int pos) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? false : annotation.substitutionHeadNameTouches(pos);
		}
		
		public TextRange getNameValidationTextRange() {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? null : annotation.getSubstitutionHeadNameValidationTextRange();
		}
	}
}
