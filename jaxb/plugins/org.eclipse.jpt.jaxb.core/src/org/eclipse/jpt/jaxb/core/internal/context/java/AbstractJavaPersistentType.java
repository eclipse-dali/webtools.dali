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

import java.beans.Introspector;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.JaxbSchemaComponentRef;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.SchemaComponentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaPersistentType
		extends AbstractJavaType
		implements JaxbPersistentType {
	
	protected final JaxbSchemaComponentRef schemaTypeRef;
	
	protected String factoryClass;
	
	protected String factoryMethod;
	
	protected final PropOrderContainer propOrderContainer;
	
	protected XmlRootElement rootElement;
	
	
	public AbstractJavaPersistentType(JaxbContextRoot parent, JavaResourceAbstractType resourceType) {
		super(parent, resourceType);
		this.schemaTypeRef = buildSchemaTypeRef(); 
		this.factoryClass = this.getResourceFactoryClass();
		this.factoryMethod = this.getResourceFactoryMethod();
		this.propOrderContainer = new PropOrderContainer();
		this.rootElement = this.buildRootElement();
	}
	
	
	protected JaxbSchemaComponentRef buildSchemaTypeRef() {
		return new PersistentTypeSchemaTypeRef(this);
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) super.getParent();
	}
	
	protected JaxbPackageInfo getPackageInfo() {
		JaxbPackage jaxbPackage = getParent().getPackage(this.getPackageName());
		return jaxbPackage == null ? null : jaxbPackage.getPackageInfo();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.schemaTypeRef.synchronizeWithResourceModel();
		this.setFactoryClass_(getResourceFactoryClass());
		this.setFactoryMethod_(getResourceFactoryMethod());
		this.syncPropOrder();
		this.syncRootElement();
	}
	
	@Override
	public void update() {
		super.update();
		this.schemaTypeRef.update();
	}
	
	
	// ********** xml type annotation **********
	
	protected XmlTypeAnnotation getXmlTypeAnnotation() {
		return (XmlTypeAnnotation) this.getJavaResourceType().getNonNullAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
	}
	
	
	// ***** schema type ref *****
	
	public JaxbSchemaComponentRef getSchemaTypeRef() {
		return this.schemaTypeRef;
	}
	
	
	// ********** factory class **********
	
	public String getFactoryClass() {
		return this.factoryClass;
	}
	
	public void setFactoryClass(String factoryClass) {
		this.getXmlTypeAnnotation().setFactoryClass(factoryClass);
		this.setFactoryClass_(factoryClass);	
	}
	
	protected void setFactoryClass_(String factoryClass) {
		String old = this.factoryClass;
		this.factoryClass = factoryClass;
		this.firePropertyChanged(FACTORY_CLASS_PROPERTY, old, factoryClass);
	}
	
	protected String getResourceFactoryClass() {
		return this.getXmlTypeAnnotation().getFactoryClass();
	}
	
	
	// ********** factory method **********
	
	public String getFactoryMethod() {
		return this.factoryMethod;
	}
	
	public void setFactoryMethod(String factoryMethod) {
		this.getXmlTypeAnnotation().setFactoryMethod(factoryMethod);
		this.setFactoryMethod_(factoryMethod);	
	}
	
	protected void setFactoryMethod_(String factoryMethod) {
		String old = this.factoryMethod;
		this.factoryMethod = factoryMethod;
		this.firePropertyChanged(FACTORY_METHOD_PROPERTY, old, factoryMethod);
	}
	
	protected String getResourceFactoryMethod() {
		return this.getXmlTypeAnnotation().getFactoryMethod();
	}
	
	
	// ********** prop order **********
	
	public ListIterable<String> getPropOrder() {
		return this.propOrderContainer.getContextElements();
	}
	
	public int getPropOrderSize() {
		return this.propOrderContainer.getContextElementsSize();
	}
	
	public void addProp(int index, String prop) {
		getXmlTypeAnnotation().addProp(index, prop);
		this.propOrderContainer.addContextElement(index, prop);
	}
	
	public void removeProp(String prop) {
		this.removeProp(this.propOrderContainer.indexOfContextElement(prop));
	}
	
	public void removeProp(int index) {
		this.getXmlTypeAnnotation().removeProp(index);
		this.propOrderContainer.removeContextElement(index);
	}
	
	public void moveProp(int targetIndex, int sourceIndex) {
		this.getXmlTypeAnnotation().moveProp(targetIndex, sourceIndex);
		this.propOrderContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected void syncPropOrder() {
		this.propOrderContainer.synchronizeWithResourceModel();
	}
	
	protected ListIterable<String> getResourcePropOrder() {
		return this.getXmlTypeAnnotation().getPropOrder();
	}
	
	
	// *************** root element *********************
	
	public XmlRootElement getRootElement() {
		return this.rootElement;
	}
	
	public boolean isRootElement() {
		return this.rootElement != null;
	}
	
	public XmlRootElement setRootElement(String name) {
		if (name == null) {
			this.getJavaResourceType().removeAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
			this.setRootElement_(null);
			return null;
		}
		XmlRootElementAnnotation resourceRootElement = (XmlRootElementAnnotation) getJavaResourceType().addAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		resourceRootElement.setName(name);
		XmlRootElement contextRootElement = this.buildRootElement(resourceRootElement);
		this.setRootElement_(contextRootElement);
		return contextRootElement;
	}
	
	protected void setRootElement_(XmlRootElement rootElement) {
		XmlRootElement old = this.rootElement;
		this.rootElement = rootElement;
		this.firePropertyChanged(ROOT_ELEMENT, old, rootElement);
	}
	
	protected XmlRootElement buildRootElement() {
		XmlRootElementAnnotation resourceRootElement = this.getRootElementAnnotation();
		return resourceRootElement == null ? null : this.buildRootElement(resourceRootElement);
	}
	
	protected XmlRootElement buildRootElement(XmlRootElementAnnotation resourceRootElement) {
		return getFactory().buildJavaXmlRootElement(this, resourceRootElement);
	}
	
	protected void syncRootElement() {
		XmlRootElementAnnotation resourceRootElement = this.getRootElementAnnotation();
		if (resourceRootElement != null) {
			if (this.rootElement != null) {
				this.rootElement.synchronizeWithResourceModel();
			}
			else {
				this.setRootElement_(this.buildRootElement(resourceRootElement));
			}
		}
		else if (this.rootElement != null) {
			this.setRootElement_(null);
		}
	}
	
	protected XmlRootElementAnnotation getRootElementAnnotation() {
		return (XmlRootElementAnnotation) this.getJavaResourceType().getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
	}
	
	
	// **************** content assist ****************************************
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		getJaxbProject().getSchemaLibrary().refreshSchema(getJaxbPackage().getNamespace());
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.schemaTypeRef.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.rootElement != null) {
			result = this.rootElement.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getXmlTypeAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : super.getValidationTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.schemaTypeRef.validate(messages, reporter, astRoot);
		
		if (this.rootElement != null) {
			this.rootElement.validate(messages, reporter, astRoot);
		}
	}
	
	
	// **************** misc **************************************************
	
	public XsdTypeDefinition getXsdTypeDefinition() {
		XsdSchema xsdSchema = getJaxbPackage().getXsdSchema();
		return (xsdSchema == null) ? null : xsdSchema.getTypeDefinition(this.schemaTypeRef.getNamespace(), this.schemaTypeRef.getName());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.getFullyQualifiedName());
	}
	
	
	protected class PersistentTypeSchemaTypeRef
			extends AbstractJavaSchemaComponentRef {
		
		protected PersistentTypeSchemaTypeRef(JavaContextNode parent) {
			super(parent);
		}
		
		
		@Override
		protected SchemaComponentRefAnnotation getAnnotation(boolean createIfNull) {
			// never null
			return AbstractJavaPersistentType.this.getXmlTypeAnnotation();
		}
		
		@Override
		public String getDefaultNamespace() {
			return AbstractJavaPersistentType.this.getJaxbPackage().getNamespace();
		}
		
		@Override
		public String getDefaultName() {
			return Introspector.decapitalize(AbstractJavaPersistentType.this.getSimpleName());
		}
		
		@Override
		protected Iterable<String> getNamespaceProposals(Filter<String> filter) {
			XsdSchema schema = AbstractJavaPersistentType.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getNamespaceProposals(filter);
		}
		
		@Override
		protected Iterable<String> getNameProposals(Filter<String> filter) {
			XsdSchema schema = AbstractJavaPersistentType.this.getJaxbPackage().getXsdSchema();
			if (schema == null) {
				return EmptyIterable.instance();
			}
			return schema.getTypeNameProposals(getNamespace(), filter);
		}
		
		@Override
		public String getSchemaComponentTypeDescription() {
			return JptJaxbCoreMessages.XML_TYPE_DESC;
		}
		
		@Override
		protected void validateName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// do not call super... - it is not an error if the name is ""
			// if name is absent (""), namespace cannot be different from package namespace
			if ("".equals(getName()) 
					&& ! StringTools.stringsAreEqual(
							getNamespace(), 
							AbstractJavaPersistentType.this.getJaxbPackage().getNamespace())) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_TYPE_UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE,
						this,
						getXmlTypeAnnotation().getNamespaceTextRange(astRoot)));
			}
		}
		
		@Override
		protected void validateSchemaComponentRef(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
			// if name is not absent (""), type must be from schema associated with this package
			String name = getName();
			String namespace = getNamespace();
			
			if (! StringTools.stringIsEmpty(name)) {
				XsdSchema schema = AbstractJavaPersistentType.this.getJaxbPackage().getXsdSchema();
				
				if (schema != null) {
					XsdTypeDefinition schemaType = schema.getTypeDefinition(namespace, name);
					if (schemaType == null) {
						messages.add(getUnresolveSchemaComponentMessage(astRoot));
					}
				}
			}
		}
	}
	
	
	/**
	 * xml prop order container
	 */
	protected class PropOrderContainer
			extends ListContainer<String, String> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return PROP_ORDER_LIST;
		}
		
		@Override
		protected String buildContextElement(String resourceElement) {
			return resourceElement;
		}
		
		@Override
		protected ListIterable<String> getResourceElements() {
			return AbstractJavaPersistentType.this.getResourcePropOrder();
		}
		
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}
