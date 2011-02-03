/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbContainmentMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlList;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JaxbContainmentAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class GenericJavaContainmentMapping<A extends JaxbContainmentAnnotation>
	extends AbstractJavaAttributeMapping<A>
	implements JaxbContainmentMapping
{

	protected String specifiedName;

	protected Boolean specifiedRequired;

	protected String specifiedNamespace;

	protected final XmlAdaptable xmlAdaptable;

	protected XmlSchemaType xmlSchemaType;

	protected XmlList xmlList;

	public GenericJavaContainmentMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.specifiedName = buildSpecifiedName();
		this.specifiedNamespace = buildSpecifiedNamespace();
		this.specifiedRequired = buildSpecifiedRequired();
		this.xmlAdaptable = buildXmlAdaptable();
		this.initializeXmlSchemaType();
		this.initializeXmlList();			
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		setSpecifiedNamespace_(buildSpecifiedNamespace());
		setSpecifiedRequired_(buildSpecifiedRequired());
		this.xmlAdaptable.synchronizeWithResourceModel();
		this.syncXmlSchemaType();
		this.syncXmlList();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
		this.updateXmlSchemaType();
		this.updateXmlList();
	}

	//************ XmlAttribute.name ***************
	public String getName() {
		return this.getSpecifiedName() == null ? this.getDefaultName() : getSpecifiedName();
	}

	public String getDefaultName() {
		return getJavaResourceAttribute().getName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		this.getAnnotationForUpdate().setName(name);
		this.setSpecifiedName_(name);
	}

	protected  void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getName();
	}


	//************ required ***************

	public boolean isRequired() {
		return (this.getSpecifiedRequired() == null) ? this.isDefaultRequired() : this.getSpecifiedRequired().booleanValue();
	}

	public boolean isDefaultRequired() {
		return DEFAULT_REQUIRED;
	}

	public Boolean getSpecifiedRequired() {
		return this.specifiedRequired;
	}

	public void setSpecifiedRequired(Boolean newSpecifiedRequired) {
		this.getAnnotationForUpdate().setRequired(newSpecifiedRequired);
		this.setSpecifiedRequired_(newSpecifiedRequired);
	}

	protected void setSpecifiedRequired_(Boolean newSpecifiedRequired) {
		Boolean oldRequired = this.specifiedRequired;
		this.specifiedRequired = newSpecifiedRequired;
		firePropertyChanged(SPECIFIED_REQUIRED_PROPERTY, oldRequired, newSpecifiedRequired);
	}

	protected Boolean buildSpecifiedRequired() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getRequired();
	}


	//************ XmlAttribute.namespace ***************

	public String getNamespace() {
		return getSpecifiedNamespace() == null ? getDefaultNamespace() : getSpecifiedNamespace();
	}

	public String getDefaultNamespace() {
		return getPersistentAttribute().getParent().getNamespace();
	}

	public String getSpecifiedNamespace() {
		return this.specifiedNamespace;
	}

	public void setSpecifiedNamespace(String newSpecifiedNamespace) {
		this.getAnnotationForUpdate().setNamespace(newSpecifiedNamespace);
		this.setSpecifiedNamespace_(newSpecifiedNamespace);
	}

	protected void setSpecifiedNamespace_(String newSpecifiedNamespace) {
		String oldNamespace = this.specifiedNamespace;
		this.specifiedNamespace = newSpecifiedNamespace;
		firePropertyChanged(SPECIFIED_NAMESPACE_PROPERTY, oldNamespace, newSpecifiedNamespace);
	}

	protected String buildSpecifiedNamespace() {
		return getMappingAnnotation() == null ? null : getMappingAnnotation().getNamespace();
	}


	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaContainmentMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaContainmentMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}

	//****************** XmlSchemaType *********************

	public XmlSchemaType getXmlSchemaType() {
		return this.xmlSchemaType;
	}

	protected void setXmlSchemaType_(XmlSchemaType xmlSchemaType) {
		XmlSchemaType oldXmlSchemaType = this.xmlSchemaType;
		this.xmlSchemaType = xmlSchemaType;
		this.firePropertyChanged(XML_SCHEMA_TYPE, oldXmlSchemaType, xmlSchemaType);
	}

	public boolean hasXmlSchemaType() {
		return this.xmlSchemaType != null;
	}

	public XmlSchemaType addXmlSchemaType() {
		if (this.xmlSchemaType != null) {
			throw new IllegalStateException();
		}
		XmlSchemaTypeAnnotation annotation = (XmlSchemaTypeAnnotation) this.getJavaResourceAttribute().addAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);

		XmlSchemaType xmlJavaTypeAdapter = this.buildXmlSchemaType(annotation);
		this.setXmlSchemaType_(xmlJavaTypeAdapter);
		return xmlJavaTypeAdapter;
	}

	public void removeXmlSchemaType() {
		if (this.xmlSchemaType == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		this.setXmlSchemaType_(null);
	}

	protected XmlSchemaType buildXmlSchemaType(XmlSchemaTypeAnnotation annotation) {
		return new GenericJavaContainmentMappingXmlSchemaType(this, annotation);
	}

	protected XmlSchemaTypeAnnotation getXmlSchemaTypeAnnotation() {
		return (XmlSchemaTypeAnnotation) this.getJavaResourceAttribute().getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
	}

	protected void initializeXmlSchemaType() {
		XmlSchemaTypeAnnotation annotation = this.getXmlSchemaTypeAnnotation();
		if (annotation != null) {
			this.xmlSchemaType = this.buildXmlSchemaType(annotation);
		}		
	}

	protected void updateXmlSchemaType() {
		if (this.xmlSchemaType != null) {
			this.xmlSchemaType.update();
		}
	}

	protected void syncXmlSchemaType() {
		XmlSchemaTypeAnnotation annotation = this.getXmlSchemaTypeAnnotation();
		if (annotation != null) {
			if (this.getXmlSchemaType() != null) {
				this.getXmlSchemaType().synchronizeWithResourceModel();
			}
			else {
				this.setXmlSchemaType_(this.buildXmlSchemaType(annotation));
			}
		}
		else {
			this.setXmlSchemaType_(null);
		}
	}

	//************  XmlList ***************

	public XmlList getXmlList() {
		return this.xmlList;
	}

	public XmlList addXmlList() {
		if (this.xmlList != null) {
			throw new IllegalStateException();
		}
		XmlListAnnotation annotation = (XmlListAnnotation) this.getJavaResourceAttribute().addAnnotation(XmlListAnnotation.ANNOTATION_NAME);

		XmlList xmlList = this.buildXmlList(annotation);
		this.setXmlList_(xmlList);
		return xmlList;
	}

	protected XmlList buildXmlList(XmlListAnnotation xmlListAnnotation) {
		return new GenericJavaXmlList(this, xmlListAnnotation);
	}

	public void removeXmlList() {
		if (this.xmlList == null) {
			throw new IllegalStateException();
		}
		this.getJavaResourceAttribute().removeAnnotation(XmlListAnnotation.ANNOTATION_NAME);
		this.setXmlList_(null);
	}

	protected void initializeXmlList() {
		XmlListAnnotation annotation = this.getXmlListAnnotation();
		if (annotation != null) {
			this.xmlList = this.buildXmlList(annotation);
		}
	}

	protected XmlListAnnotation getXmlListAnnotation() {
		return (XmlListAnnotation) this.getJavaResourceAttribute().getAnnotation(XmlListAnnotation.ANNOTATION_NAME);
	}

	protected void syncXmlList() {
		XmlListAnnotation annotation = this.getXmlListAnnotation();
		if (annotation != null) {
			if (this.getXmlList() != null) {
				this.getXmlList().synchronizeWithResourceModel();
			}
			else {
				this.setXmlList_(this.buildXmlList(annotation));
			}
		}
		else {
			this.setXmlList_(null);
		}
	}

	protected void updateXmlList() {
		if (this.getXmlList() != null) {
			this.getXmlList().update();
		}
	}

	protected void setXmlList_(XmlList xmlList) {
		XmlList oldXmlList = this.xmlList;
		this.xmlList = xmlList;
		firePropertyChanged(XML_LIST_PROPERTY, oldXmlList, xmlList);
	}


	// **************** content assist **************

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlSchemaType != null) {
			result = this.xmlSchemaType.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.xmlAdaptable.validate(messages, reporter, astRoot);
		if (this.xmlSchemaType != null) {
			this.xmlSchemaType.validate(messages, reporter, astRoot);
		}
		if (this.xmlList != null) {
			this.xmlList.validate(messages, reporter, astRoot);
		}
	}
}
