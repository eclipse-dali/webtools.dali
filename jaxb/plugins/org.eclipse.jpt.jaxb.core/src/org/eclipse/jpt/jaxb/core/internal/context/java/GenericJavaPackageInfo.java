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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
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

	protected final XmlSchema xmlSchema;

	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder specifiedAccessOrder;

	protected final ContextListContainer<XmlSchemaType, XmlSchemaTypeAnnotation> xmlSchemaTypeContainer;

	protected final ContextListContainer<XmlJavaTypeAdapter, XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapterContainer;

	public GenericJavaPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage) {
		super(parent);
		this.resourcePackage = resourcePackage;
		this.xmlSchema = getFactory().buildJavaXmlSchema(this);
		this.specifiedAccessType = getResourceAccessType();
		this.specifiedAccessOrder = getResourceAccessOrder();
		this.xmlSchemaTypeContainer = this.buildXmlSchemaTypeContainer();
		this.xmlJavaTypeAdapterContainer = this.buildXmlJavaTypeAdapterContainer();
	}
	
	@Override
	public JaxbPackage getParent() {
		return (JaxbPackage) super.getParent();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getParent();
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
		this.setSpecifiedAccessType_(this.getResourceAccessType());
		this.setSpecifiedAccessOrder_(this.getResourceAccessOrder());
		this.syncXmlSchemaTypes();
		this.syncXmlJavaTypeAdapters();
	}

	@Override
	public void update() {
		super.update();
		this.xmlSchema.update();
		this.updateXmlSchemaTypes();
		this.updateXmlJavaTypeAdapters();
	}


	// ********** JaxbPackageInfo implementation **********

	public JavaResourcePackage getResourcePackage() {
		return this.resourcePackage;
	}

	// ********** xml schema **********

	public XmlSchema getXmlSchema() {
		return this.xmlSchema;
	}

	// ********** access type **********

	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.getDefaultAccessType();
	}

	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType access) {
		this.getAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
		this.setSpecifiedAccessType_(access);
	}

	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}

	public XmlAccessType getDefaultAccessType() {
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(this.getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) this.resourcePackage.getNonNullAnnotation(JAXB.XML_ACCESSOR_TYPE);
	}


	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.getDefaultAccessOrder();
	}

	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		this.getAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
		this.setSpecifiedAccessOrder_(accessOrder);
	}

	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		this.firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	public XmlAccessOrder getDefaultAccessOrder() {
		return XmlAccessOrder.UNDEFINED;
	}
	
	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(this.getAccessorOrderAnnotation().getValue());
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
		this.removeXmlSchemaType(this.xmlSchemaTypeContainer.indexOfContextElement(xmlSchemaType));
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


	// **************** misc **************************************************
	
	protected CompilationUnit buildASTRoot() {
		return this.resourcePackage.getJavaResourceCompilationUnit().buildASTRoot();
	}
	
	// **************** content assist ****************************************
	
	//This doesn't actually work yet because of JDT bug (bugs.eclipse.org/326610)
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		getJaxbProject().getSchemaLibrary().refreshSchema(getJaxbPackage().getNamespace());
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		result = this.xmlSchema.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}		
		for (XmlSchemaType xmlSchemaType : this.getXmlSchemaTypes()) {
			result = xmlSchemaType.getJavaCompletionProposals(pos, filter, astRoot);
			if (!CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	// **************** validation ********************************************

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourcePackage.getNameTextRange(astRoot);
	}
	
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
				&& (this.resourcePackage instanceof SourceNode)) {
			// build the AST root here to pass down
			this.validate(messages, reporter, this.buildASTRoot());
		}
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.xmlSchema.validate(messages, reporter, astRoot);
		
		for (XmlSchemaType schemaType : getXmlSchemaTypes()) {
			schemaType.validate(messages, reporter, astRoot);
		}
		
		for (XmlJavaTypeAdapter adapter : getXmlJavaTypeAdapters()) {
			adapter.validate(messages, reporter, astRoot);
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
