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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPackageInfo
		extends AbstractJavaContextNode
		implements JaxbPackageInfo {

	protected final JavaResourcePackage resourcePackage;

	protected final JavaXmlSchema xmlSchema;
	
	protected XmlAccessType accessType;
	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder specifiedAccessOrder;

	protected final ContextListContainer<XmlSchemaType, XmlSchemaTypeAnnotation> xmlSchemaTypeContainer;

	protected final ContextListContainer<XmlJavaTypeAdapter, XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapterContainer;

	public GenericJavaPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage) {
		super(parent);
		this.resourcePackage = resourcePackage;
		this.xmlSchema = getFactory().buildJavaXmlSchema(this);
		initAccessType();
		this.specifiedAccessOrder = getResourceAccessOrder();
		this.xmlSchemaTypeContainer = buildXmlSchemaTypeContainer();
		this.xmlJavaTypeAdapterContainer = buildXmlJavaTypeAdapterContainer();
	}
	
	
	public JaxbPackage getJaxbPackage() {
		return (JaxbPackage) getParent();
	}
	

	// **************** AbstractJaxbNode impl *********************************
	
	@Override
	public IResource getResource() {
		return this.resourcePackage.getFile();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlSchema.synchronizeWithResourceModel();
		syncAccessType();
		setSpecifiedAccessOrder_(this.getResourceAccessOrder());
		syncXmlSchemaTypes();
		syncXmlJavaTypeAdapters();
	}

	@Override
	public void update() {
		super.update();
		this.xmlSchema.update();
		updateAccessType();
		updateXmlSchemaTypes();
		updateXmlJavaTypeAdapters();
	}


	// ********** JaxbPackageInfo implementation **********

	public JavaResourcePackage getResourcePackage() {
		return this.resourcePackage;
	}
	
	
	// ********** xml schema **********
	
	public JavaXmlSchema getXmlSchema() {
		return this.xmlSchema;
	}
	
	
	// ********** access type **********
	
	public XmlAccessType getAccessType() {
		return this.accessType;
	}
	
	protected void setAccessType_(XmlAccessType accessType) {
		XmlAccessType old = this.accessType;
		this.accessType = accessType;
		firePropertyChanged(ACCESS_TYPE_PROPERTY, old, accessType);
	}
	
	public XmlAccessType getDefaultAccessType() {
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType access) {
		getAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
		setSpecifiedAccessType_(access);
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) this.resourcePackage.getNonNullAnnotation(JAXB.XML_ACCESSOR_TYPE);
	}
	
	protected void initAccessType() {
		XmlAccessType specified = getResourceAccessType();
		XmlAccessType actual = (specified != null) ? specified : getDefaultAccessType();
		this.specifiedAccessType = specified;
		this.accessType = actual;
	}
	
	protected void syncAccessType() {
		setSpecifiedAccessType_(getResourceAccessType());
	}
	
	protected void updateAccessType() {
		XmlAccessType actual = (this.specifiedAccessType != null) ? 
				this.specifiedAccessType 
				: getDefaultAccessType();
		setAccessType_(actual);
	}
	
	
	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : getDefaultAccessOrder();
	}

	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		getAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
		setSpecifiedAccessOrder_(accessOrder);
	}

	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	public XmlAccessOrder getDefaultAccessOrder() {
		return XmlAccessOrder.UNDEFINED;
	}
	
	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(getAccessorOrderAnnotation().getValue());
	}

	protected XmlAccessorOrderAnnotation getAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) this.resourcePackage.getNonNullAnnotation(JAXB.XML_ACCESSOR_ORDER);
	}


	// ********** xml schema types **********

	public ListIterable<XmlSchemaType> getXmlSchemaTypes() {
		return this.xmlSchemaTypeContainer.getContextElements();
	}

	public int getXmlSchemaTypesSize() {
		return this.xmlSchemaTypeContainer.getContextElementsSize();
	}

	public XmlSchemaType addXmlSchemaType(int index) {
		XmlSchemaTypeAnnotation annotation = (XmlSchemaTypeAnnotation) this.resourcePackage.addAnnotation(index, JAXB.XML_SCHEMA_TYPE);
		return this.xmlSchemaTypeContainer.addContextElement(index, annotation);
	}

	public void removeXmlSchemaType(XmlSchemaType xmlSchemaType) {
		removeXmlSchemaType(this.xmlSchemaTypeContainer.indexOfContextElement(xmlSchemaType));
	}

	public void removeXmlSchemaType(int index) {
		this.resourcePackage.removeAnnotation(index, JAXB.XML_SCHEMA_TYPE);
		this.xmlSchemaTypeContainer.removeContextElement(index);
	}

	public void moveXmlSchemaType(int targetIndex, int sourceIndex) {
		this.resourcePackage.moveAnnotation(targetIndex, sourceIndex, JAXB.XML_SCHEMA_TYPE);
		this.xmlSchemaTypeContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected XmlSchemaType buildXmlSchemaType(XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		return new GenericJavaPackageXmlSchemaType(this, xmlSchemaTypeAnnotation);
	}

	protected void syncXmlSchemaTypes() {
		this.xmlSchemaTypeContainer.synchronizeWithResourceModel();
	}

	protected void updateXmlSchemaTypes() {
		this.xmlSchemaTypeContainer.update();
	}

	protected ListIterable<XmlSchemaTypeAnnotation> getXmlSchemaTypeAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, XmlSchemaTypeAnnotation>(
				this.resourcePackage.getAnnotations(JAXB.XML_SCHEMA_TYPE));
	}

	protected ContextListContainer<XmlSchemaType, XmlSchemaTypeAnnotation> buildXmlSchemaTypeContainer() {
		XmlSchemaTypeContainer container = new XmlSchemaTypeContainer();
		container.initialize();
		return container;
	}


	// ********** xml java type adapters **********

	public ListIterable<XmlJavaTypeAdapter> getXmlJavaTypeAdapters() {
		return this.xmlJavaTypeAdapterContainer.getContextElements();
	}

	public int getXmlJavaTypeAdaptersSize() {
		return this.xmlJavaTypeAdapterContainer.getContextElementsSize();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter(int index) {
		XmlJavaTypeAdapterAnnotation annotation = (XmlJavaTypeAdapterAnnotation) this.resourcePackage.addAnnotation(index, JAXB.XML_JAVA_TYPE_ADAPTER);
		return this.xmlJavaTypeAdapterContainer.addContextElement(index, annotation);
	}

	public void removeXmlJavaTypeAdapter(XmlJavaTypeAdapter xmlSchemaType) {
		this.removeXmlJavaTypeAdapter(this.xmlJavaTypeAdapterContainer.indexOfContextElement(xmlSchemaType));
	}

	public void removeXmlJavaTypeAdapter(int index) {
		this.resourcePackage.removeAnnotation(index, JAXB.XML_JAVA_TYPE_ADAPTER);
		this.xmlJavaTypeAdapterContainer.removeContextElement(index);
	}

	public void moveXmlJavaTypeAdapter(int targetIndex, int sourceIndex) {
		this.resourcePackage.moveAnnotation(targetIndex, sourceIndex, JAXB.XML_JAVA_TYPE_ADAPTER);
		this.xmlJavaTypeAdapterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaPackageXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	protected void updateXmlJavaTypeAdapters() {
		this.xmlJavaTypeAdapterContainer.update();
	}

	protected void syncXmlJavaTypeAdapters() {
		this.xmlJavaTypeAdapterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJavaTypeAdapterAnnotation> getXmlJavaTypeAdapterAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, XmlJavaTypeAdapterAnnotation>(
				this.resourcePackage.getAnnotations(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	protected ContextListContainer<XmlJavaTypeAdapter, XmlJavaTypeAdapterAnnotation> buildXmlJavaTypeAdapterContainer() {
		XmlJavaTypeAdapterContainer container = new XmlJavaTypeAdapterContainer();
		container.initialize();
		return container;
	}
	
	public XmlJavaTypeAdapter getXmlJavaTypeAdapter(String boundTypeName) {
		for (XmlJavaTypeAdapter adapter : getXmlJavaTypeAdapters()) {
			if (boundTypeName.equals(adapter.getFullyQualifiedType())) {
				return adapter;
			}
		}
		return null;
	}


	// ***** misc *****

	public String getNamespaceForPrefix(String prefix) {
		if (this.xmlSchema != null) {
			for (XmlNs xmlns : this.xmlSchema.getXmlNsPrefixes()) {
				if (ObjectTools.equals(xmlns.getPrefix(), prefix)) {
					return xmlns.getNamespaceURI();
				}
			}
		}
		return null;
	}
	
	public String getPrefixForNamespace(String namespace) {
		if (this.xmlSchema != null) {
			for (XmlNs xmlns : this.xmlSchema.getXmlNsPrefixes()) {
				if (ObjectTools.equals(xmlns.getNamespaceURI(), namespace)) {
					return xmlns.getPrefix();
				}
			}
		}
		return null;
	}
	
	
	// ***** content assist ******
	
	//This doesn't actually work yet because of JDT bug (bugs.eclipse.org/326610)
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		
		getJaxbProject().getSchemaLibrary().refreshSchema(getJaxbPackage().getNamespace());
		
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlSchema.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}		
		for (XmlSchemaType xmlSchemaType : getXmlSchemaTypes()) {
			result = xmlSchemaType.getCompletionProposals(pos);
			if (!IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	// ***** validation *****

	@Override
	public TextRange getValidationTextRange() {
		return this.resourcePackage.getNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		IFile file = this.resourcePackage.getFile();
		// 'file' will be null if the type is "external" and binary;
		// the file will be in a different project if the type is "external" and source;
		// the type will be binary if it is in a JAR in the current project
		if ((file != null) 
				&& file.getProject().equals(getJaxbProject().getProject()) 
				&& (this.resourcePackage instanceof SourceModel)) {

			this.xmlSchema.validate(messages, reporter);
			
			for (XmlSchemaType schemaType : getXmlSchemaTypes()) {
				schemaType.validate(messages, reporter);
			}
			
			for (XmlJavaTypeAdapter adapter : getXmlJavaTypeAdapters()) {
				adapter.validate(messages, reporter);
			}
		}
	}


	/**
	 * xml schema type container
	 */
	protected class XmlSchemaTypeContainer
		extends ContextListContainer<XmlSchemaType, XmlSchemaTypeAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return XML_SCHEMA_TYPES_LIST;
		}
		@Override
		protected XmlSchemaType buildContextElement(XmlSchemaTypeAnnotation resourceElement) {
			return GenericJavaPackageInfo.this.buildXmlSchemaType(resourceElement);
		}
		@Override
		protected ListIterable<XmlSchemaTypeAnnotation> getResourceElements() {
			return GenericJavaPackageInfo.this.getXmlSchemaTypeAnnotations();
		}
		@Override
		protected XmlSchemaTypeAnnotation getResourceElement(XmlSchemaType contextElement) {
			return contextElement.getXmlSchemaTypeAnnotation();
		}
	}

	/**
	 * xml java type adapter container
	 */
	protected class XmlJavaTypeAdapterContainer
			extends ContextListContainer<XmlJavaTypeAdapter, XmlJavaTypeAdapterAnnotation> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return XML_JAVA_TYPE_ADAPTERS_LIST;
		}
		
		@Override
		protected XmlJavaTypeAdapter buildContextElement(XmlJavaTypeAdapterAnnotation resourceElement) {
			return GenericJavaPackageInfo.this.buildXmlJavaTypeAdapter(resourceElement);
		}
		
		@Override
		protected ListIterable<XmlJavaTypeAdapterAnnotation> getResourceElements() {
			return GenericJavaPackageInfo.this.getXmlJavaTypeAdapterAnnotations();
		}
		
		@Override
		protected XmlJavaTypeAdapterAnnotation getResourceElement(XmlJavaTypeAdapter contextElement) {
			return contextElement.getAnnotation();
		}
	}
}
