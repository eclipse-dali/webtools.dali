/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaType
		extends AbstractJavaContextNode
		implements JaxbType {
	
	protected final JavaResourceAbstractType resourceType;
	
	protected JaxbTypeMapping mapping;
	
	protected boolean defaultMapped = false;
	
	protected XmlJavaTypeAdapter xmlJavaTypeAdapter;
	
	
	protected AbstractJavaType(JaxbContextNode parent, JavaResourceAbstractType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		initMapping();
		initXmlJavaTypeAdapter();
	}
	
	
	// ***** overrides *****
	
	@Override
	public IResource getResource() {
		return this.resourceType.getFile();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncMapping();
		syncXmlJavaTypeAdapter();
	}
	
	@Override
	public void update() {
		super.update();
		updateMapping();
		updateXmlJavaTypeAdapter();
	}
	
	
	// ***** mapping *****
	
	public JaxbTypeMapping getMapping() {
		return this.mapping;
	}
	
	protected void setMapping_(JaxbTypeMapping newMapping) {
		JaxbTypeMapping old = this.mapping;
		this.mapping = newMapping;
		firePropertyChanged(MAPPING_PROPERTY, old, newMapping);
	}
	
	protected abstract JaxbTypeMapping buildMapping();
	
	protected void initMapping() {
		if (isDefaultMapped() || isSpecifiedMapped()) {
			this.mapping = buildMapping();
		}
	}
	
	protected void syncMapping() {
		if (this.mapping != null) {
			this.mapping.synchronizeWithResourceModel();
		}
	}
	
	protected void updateMapping() {
		boolean isMapped = isDefaultMapped() || isSpecifiedMapped();
		if (isMapped) {
			if (this.mapping != null) {
				this.mapping.update();
			}
			else {
				setMapping_(buildMapping());
			}
		}
		else if (this.mapping != null) {
			setMapping_(null);
		}
	}
	
	/*
	 * Return true if the annotations on this type indicate it should have a mapping
	 */
	protected boolean isSpecifiedMapped() {
		return getXmlTypeAnnotation() != null
				|| getXmlRootElementAnnotation() != null
				|| getXmlTransientAnnotation() != null;
	}
	
	protected XmlTypeAnnotation getXmlTypeAnnotation() {
		return (XmlTypeAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_TYPE);
	}
	
	protected XmlRootElementAnnotation getXmlRootElementAnnotation() {
		return (XmlRootElementAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_ROOT_ELEMENT);
	}
	
	protected XmlTransientAnnotation getXmlTransientAnnotation() {
		return (XmlTransientAnnotation) getJavaResourceType().getAnnotation(JAXB.XML_TRANSIENT);
	}
	
	
	// ***** default mapped *****
	
	public boolean isDefaultMapped() {
		return this.defaultMapped;
	}
	
	public void setDefaultMapped(boolean newValue) {
		boolean old = this.defaultMapped;
		this.defaultMapped = newValue;
		firePropertyChanged(DEFAULT_MAPPED_PROPERTY, old, newValue);
	}
	
	
	// ***** XmlJavaTypeAdapter *****
	
	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlJavaTypeAdapter;
	}
	
	protected void setXmlJavaTypeAdapter_(XmlJavaTypeAdapter xmlJavaTypeAdapter) {
		XmlJavaTypeAdapter oldXmlJavaTypeAdapter = this.xmlJavaTypeAdapter;
		this.xmlJavaTypeAdapter = xmlJavaTypeAdapter;
		firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldXmlJavaTypeAdapter, xmlJavaTypeAdapter);
	}
	
	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter != null) {
			throw new IllegalStateException();
		}
		XmlJavaTypeAdapterAnnotation annotation 
				= (XmlJavaTypeAdapterAnnotation) getJavaResourceType().addAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		XmlJavaTypeAdapter xmlJavaTypeAdapter = buildXmlJavaTypeAdapter(annotation);
		setXmlJavaTypeAdapter_(xmlJavaTypeAdapter);
		return xmlJavaTypeAdapter;
	}
	
	public void removeXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter == null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().removeAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		setXmlJavaTypeAdapter_(null);
	}
	
	protected XmlJavaTypeAdapterAnnotation getXmlJavaTypeAdapterAnnotation() {
		return (XmlJavaTypeAdapterAnnotation) getJavaResourceType().getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
	}
	
	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaTypeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}
	
	protected void initXmlJavaTypeAdapter() {
		XmlJavaTypeAdapterAnnotation annotation = getXmlJavaTypeAdapterAnnotation();
		if (annotation != null) {
			this.xmlJavaTypeAdapter = buildXmlJavaTypeAdapter(annotation);
		}
	}
	
	protected void syncXmlJavaTypeAdapter() {
		XmlJavaTypeAdapterAnnotation annotation = getXmlJavaTypeAdapterAnnotation();
		if (annotation != null) {
			if (this.xmlJavaTypeAdapter != null) {
				this.xmlJavaTypeAdapter.synchronizeWithResourceModel();
			}
			else {
				setXmlJavaTypeAdapter_(buildXmlJavaTypeAdapter(annotation));
			}
		}
		else {
			setXmlJavaTypeAdapter_(null);
		}
	}
	
	protected void updateXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter != null) {
			this.xmlJavaTypeAdapter.update();
		}
	}
	
	
	// ***** JaxbType misc *****
	
	public JavaResourceAbstractType getJavaResourceType() {
		return this.resourceType;
	}
	
	public String getSimpleName() {
		return this.resourceType.getName();
	}
	
	public String getTypeQualifiedName() {
		String packageName = getPackageName();
		return (packageName.length() == 0) ? getFullyQualifiedName() : getFullyQualifiedName().substring(packageName.length() + 1);
	}
	
	public String getFullyQualifiedName() {
		return this.resourceType.getQualifiedName();
	}
	
	public String getPackageName() {
		return this.resourceType.getPackageName();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getContextRoot().getPackage(getPackageName());
	}
	
	public Iterable<String> getReferencedXmlTypeNames() {
		if (this.mapping != null) {
			return this.mapping.getReferencedXmlTypeNames();
		}
		return EmptyIterable.instance();
	}
	
	
	// ***** misc *****
	
	protected CompilationUnit buildASTRoot() {
		return this.resourceType.getJavaResourceCompilationUnit().buildASTRoot();
	}
	
	
	// ***** validation *****
	
	/**
	 * Override as needed
	 */
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getJavaResourceType().getNameTextRange(astRoot);
	}
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		// TODO temporary hack since we don't know yet where to put
		// any messages for types in another project
		IFile file = this.resourceType.getFile();
		// 'file' will be null if the type is "external" and binary;
		// the file will be in a different project if the type is "external" and source;
		// the type will be binary if it is in a JAR in the current project
		if ((file != null) 
				&& file.getProject().equals(getJaxbProject().getProject()) 
				&& (this.resourceType instanceof SourceNode)) {
			// build the AST root here to pass down
			this.validate(messages, reporter, this.buildASTRoot());
		}
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.mapping != null) {
			this.mapping.validate(messages, reporter, astRoot);
		}
		
		if (this.xmlJavaTypeAdapter != null) {
			this.xmlJavaTypeAdapter.validate(messages, reporter, astRoot);
		}
	}
}
