/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbQName;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.QNameAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.qName.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.substitutionHeadQName.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getXmlElementDeclAnnotation().getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.qName.validate(messages, reporter, astRoot);
		this.substitutionHeadQName.validate(messages, reporter, astRoot);
		
		JavaResourceMethod resourceMethod = getResourceMethod();
		if (! JAXB.JAXB_ELEMENT.equals(resourceMethod.getTypeName())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE,
							this,
							getValidationTextRange(astRoot)));
		}
		if (resourceMethod.getParametersSize() != 1 || 
				! JDTTools.typeIsSubType(getJaxbProject().getJavaProject(), resourceMethod.getParameterTypeName(0), Object.class.getName())) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_PARAM,
							this,
							getValidationTextRange(astRoot)));
		}
	}
	
	
	protected class XmlElementDeclQName
			extends AbstractJavaQName {
		
		protected XmlElementDeclQName(JavaContextNode parent) {
			super(parent, new QNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaElementFactoryMethod.this.getRegistry().getJaxbClass().getJaxbPackage();
		}
		
		@Override
		public String getDefaultName() {
			return null;
		}
		
		@Override
		public String getDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			if (! GenericJavaElementFactoryMethod.this.isGlobalScope()) {
				String fqScope = GenericJavaElementFactoryMethod.this.getFullyQualifiedScope();
				JaxbTypeMapping scopeTypeMapping = 
						GenericJavaElementFactoryMethod.this.getJaxbProject().getContextRoot().getTypeMapping(fqScope);
				if (scopeTypeMapping != null) {
					XsdTypeDefinition xsdType = scopeTypeMapping.getXsdTypeDefinition();
					if (xsdType != null) {
						return xsdType.getElementNameProposals(getNamespace(), filter, true);
					}
				}
			}
			
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				return xsdSchema.getElementNameProposals(getNamespace(), filter);
			}
			
			return EmptyIterable.instance(); 
		}
		
		@Override
		public Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema xsdSchema = this.getXsdSchema();
			return (xsdSchema == null) ? EmptyIterable.<String>instance() : xsdSchema.getNamespaceProposals(filter);
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_ELEMENT_DESC;
		}
		
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
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
						messages.add(getUnresolveSchemaComponentMessage(astRoot));
					}
				}
			}
			else {
				XsdSchema xsdSchema = this.getXsdSchema();
				if (xsdSchema != null) {
					if (xsdSchema.getElementDeclaration(getNamespace(), getName()) == null) {
						messages.add(getUnresolveSchemaComponentMessage(astRoot));
					}
				}
			}
		}
	}
	
	
	protected class QNameAnnotationProxy 
			extends AbstractJavaQName.AbstractQNameAnnotationProxy {
		
		@Override
		protected QNameAnnotation getAnnotation(boolean createIfNull) {
			return GenericJavaElementFactoryMethod.this.getXmlElementDeclAnnotation();
		}
	}
	
	
	protected class XmlElementDeclSubstitutionHeadQName
			extends AbstractJavaQName {
		
		protected XmlElementDeclSubstitutionHeadQName(JavaContextNode parent) {
			super(parent, new SubstitutionHeadQNameAnnotationProxy());
		}
		
		
		@Override
		protected JaxbPackage getJaxbPackage() {
			return GenericJavaElementFactoryMethod.this.getRegistry().getJaxbClass().getJaxbPackage();
		}
		
		@Override
		public String getDefaultName() {
			return null;
		}
		
		@Override
		public String getDefaultNamespace() {
			JaxbPackage jaxbPackage = this.getJaxbPackage();
			return (jaxbPackage == null) ? null : jaxbPackage.getNamespace();
		}
		
		@Override
		public Iterable<String> getNameProposals(Filter<String> filter) {
			String fqScope = GenericJavaElementFactoryMethod.this.getFullyQualifiedScope();
			JaxbTypeMapping scopeTypeMapping = 
					GenericJavaElementFactoryMethod.this.getJaxbProject().getContextRoot().getTypeMapping(fqScope);
			if (scopeTypeMapping != null) {
				XsdTypeDefinition xsdType = scopeTypeMapping.getXsdTypeDefinition();
				if (xsdType != null) {
					return xsdType.getElementNameProposals(getNamespace(), filter);
				}
			}
			
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				return xsdSchema.getElementNameProposals(getNamespace(), filter);
			}
			
			return EmptyIterable.instance(); 
		}
		
		@Override
		public Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema xsdSchema = this.getXsdSchema();
			return (xsdSchema == null) ? EmptyIterable.<String>instance() : xsdSchema.getNamespaceProposals(filter);
		}
		
		@Override
		public String getReferencedComponentTypeDescription() {
			return JptJaxbCoreMessages.SUBSTITUTION_HEAD_DESC;
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// need to ignore the unspecified (null) case
			if ("".equals(getName())) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.QNAME__MISSING_NAME,
								new String[] { getReferencedComponentTypeDescription() },
								this,
								getNameTextRange(astRoot)));
			}
			else if (! StringTools.stringIsEmpty(getName())) {
				if (Tools.valuesAreEqual(getName(), GenericJavaElementFactoryMethod.this.getQName().getName())) {
					messages.add(
							DefaultValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									JaxbValidationMessages.XML_ELEMENT_DECL__SUBST_HEAD_NAME_EQUALS_NAME,
									this,
									getNameTextRange(astRoot)));
				}
			}
		}
	
		@Override
		protected void validateReference(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			XsdSchema xsdSchema = this.getXsdSchema();
			if (xsdSchema != null) {
				if (xsdSchema.getElementDeclaration(getNamespace(), getName()) == null) {
					messages.add(getUnresolveSchemaComponentMessage(astRoot));
				}
			}
			
			for (JaxbElementFactoryMethod elementDecl : GenericJavaElementFactoryMethod.this.getRegistry().getElementFactoryMethods()) {
				if (Tools.valuesAreEqual(getName(), elementDecl.getSubstitutionHeadQName().getName())
						&& Tools.valuesAreEqual(getNamespace(), elementDecl.getSubstitutionHeadQName().getNamespace())) {
					return;
				}
			}
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ELEMENT_DECL__SUBST_HEAD_NO_MATCHING_ELEMENT_DECL,
							new String[] { getNamespace(), getName() },
							this,
							getValidationTextRange(astRoot)));
		}
	}
	
	
	protected class SubstitutionHeadQNameAnnotationProxy 
			implements AbstractJavaQName.AnnotationProxy {
		
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
		
		public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? false : annotation.substitutionHeadNamespaceTouches(pos, astRoot);
		}
		
		public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? null : annotation.getSubstitutionHeadNamespaceTextRange(astRoot);
		}
		
		public String getName() {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return annotation == null ? null : annotation.getSubstitutionHeadName();
		}
		
		public void setName(String newSpecifiedName) {
			getAnnotation().setSubstitutionHeadName(newSpecifiedName);
		}
		
		public boolean nameTouches(int pos, CompilationUnit astRoot) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? false : annotation.substitutionHeadNameTouches(pos, astRoot);
		}
		
		public TextRange getNameTextRange(CompilationUnit astRoot) {
			XmlElementDeclAnnotation annotation = getAnnotation();
			return (annotation == null) ? null : annotation.getSubstitutionHeadNameTextRange(astRoot);
		}
	}
}
