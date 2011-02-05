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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
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

	protected final XmlSchemaTypeContainer xmlSchemaTypeContainer;

	protected final XmlJavaTypeAdapterContainer xmlJavaTypeAdapterContainer;

	public GenericJavaPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage) {
		super(parent);
		this.resourcePackage = resourcePackage;
		this.xmlSchema = getFactory().buildJavaXmlSchema(this);
		this.specifiedAccessType = getResourceAccessType();
		this.specifiedAccessOrder = getResourceAccessOrder();
		this.xmlSchemaTypeContainer = new XmlSchemaTypeContainer();
		this.xmlJavaTypeAdapterContainer = new XmlJavaTypeAdapterContainer();
	}

	@Override
	public JaxbPackage getParent() {
		return (JaxbPackage) super.getParent();
	}


	// **************** AbstractJaxbNode impl *********************************
	
	@Override
	public IResource getResource() {
		return this.resourcePackage.getFile();
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.xmlSchema.synchronizeWithResourceModel();
		this.setSpecifiedAccessType_(this.getResourceAccessType());
		this.setSpecifiedAccessOrder_(this.getResourceAccessOrder());
		this.syncXmlSchemaTypes();
		this.syncXmlJavaTypeAdapters();
	}

	public void update() {
		this.xmlSchema.update();
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
		return (XmlAccessorTypeAnnotation) this.resourcePackage.getNonNullAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
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
		return (XmlAccessorOrderAnnotation) this.resourcePackage.getNonNullAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
	}


	// ********** xml schema types **********

	public ListIterable<XmlSchemaType> getXmlSchemaTypes() {
		return this.xmlSchemaTypeContainer.getContextElements();
	}

	public int getXmlSchemaTypesSize() {
		return this.xmlSchemaTypeContainer.getContextElementsSize();
	}

	public XmlSchemaType addXmlSchemaType(int index) {
		XmlSchemaTypeAnnotation annotation = (XmlSchemaTypeAnnotation) this.resourcePackage.addAnnotation(index, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		return this.xmlSchemaTypeContainer.addContextElement(index, annotation);
	}

	public void removeXmlSchemaType(XmlSchemaType xmlSchemaType) {
		this.removeXmlSchemaType(this.xmlSchemaTypeContainer.indexOfContextElement(xmlSchemaType));
	}

	public void removeXmlSchemaType(int index) {
		this.resourcePackage.removeAnnotation(index, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		this.xmlSchemaTypeContainer.removeContextElement(index);
	}

	public void moveXmlSchemaType(int targetIndex, int sourceIndex) {
		this.resourcePackage.moveAnnotation(targetIndex, sourceIndex, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		this.xmlSchemaTypeContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected XmlSchemaType buildXmlSchemaType(XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation) {
		return new GenericJavaPackageXmlSchemaType(this, xmlSchemaTypeAnnotation);
	}

	protected void syncXmlSchemaTypes() {
		this.xmlSchemaTypeContainer.synchronizeWithResourceModel();
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<XmlSchemaTypeAnnotation> getXmlSchemaTypeAnnotations() {
		return (ListIterable<XmlSchemaTypeAnnotation>) this.resourcePackage.getAnnotations(XmlSchemaTypeAnnotation.ANNOTATION_NAME);
	}


	// ********** xml java type adapters **********

	public ListIterable<XmlJavaTypeAdapter> getXmlJavaTypeAdapters() {
		return this.xmlJavaTypeAdapterContainer.getContextElements();
	}

	public int getXmlJavaTypeAdaptersSize() {
		return this.xmlJavaTypeAdapterContainer.getContextElementsSize();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter(int index) {
		XmlJavaTypeAdapterAnnotation annotation = (XmlJavaTypeAdapterAnnotation) this.resourcePackage.addAnnotation(index, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		return this.xmlJavaTypeAdapterContainer.addContextElement(index, annotation);
	}

	public void removeXmlJavaTypeAdapter(XmlJavaTypeAdapter xmlSchemaType) {
		this.removeXmlJavaTypeAdapter(this.xmlJavaTypeAdapterContainer.indexOfContextElement(xmlSchemaType));
	}

	public void removeXmlJavaTypeAdapter(int index) {
		this.resourcePackage.removeAnnotation(index, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		this.xmlJavaTypeAdapterContainer.removeContextElement(index);
	}

	public void moveXmlJavaTypeAdapter(int targetIndex, int sourceIndex) {
		this.resourcePackage.moveAnnotation(targetIndex, sourceIndex, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		this.xmlJavaTypeAdapterContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaPackageXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	protected void syncXmlJavaTypeAdapters() {
		this.xmlJavaTypeAdapterContainer.synchronizeWithResourceModel();
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<XmlJavaTypeAdapterAnnotation> getXmlJavaTypeAdapterAnnotations() {
		return (ListIterable<XmlJavaTypeAdapterAnnotation>) this.resourcePackage.getAnnotations(XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
	}


	// **************** misc **************************************************
	
	protected CompilationUnit buildASTRoot() {
		return this.resourcePackage.getJavaResourceCompilationUnit().buildASTRoot();
	}
	
	// **************** content assist ****************************************
	
	//This doesn't actually work yet because of JDT bug (bugs.eclipse.org/326610)
	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
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
			return contextElement.getResourceXmlSchemaType();
		}
	}

	/**
	 * xml java type adapter container
	 */
	protected class XmlJavaTypeAdapterContainer
		extends ContextListContainer<XmlJavaTypeAdapter, XmlJavaTypeAdapterAnnotation>
	{
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
			return contextElement.getResourceXmlJavaTypeAdapter();
		}
	}
}
