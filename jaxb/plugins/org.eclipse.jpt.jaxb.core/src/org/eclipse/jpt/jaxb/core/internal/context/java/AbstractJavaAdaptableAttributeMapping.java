/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptableMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaAdaptableAttributeMapping<A extends Annotation>
		extends AbstractJavaAttributeMapping<A>
		implements XmlAdaptableMapping {
	
	protected XmlJavaTypeAdapter xmlJavaTypeAdapter;
	
	
	public AbstractJavaAdaptableAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		initializeXmlJavaTypeAdapter();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlJavaTypeAdapter();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlJavaTypeAdapter();
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
		XmlJavaTypeAdapterAnnotation annotation = 
				(XmlJavaTypeAdapterAnnotation) getJavaResourceAttribute().addAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		XmlJavaTypeAdapter xmlJavaTypeAdapter = buildXmlJavaTypeAdapter(annotation);
		setXmlJavaTypeAdapter_(xmlJavaTypeAdapter);
		return xmlJavaTypeAdapter;
	}
	
	public void removeXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter == null) {
			throw new IllegalStateException();
		}
		getJavaResourceAttribute().removeAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		setXmlJavaTypeAdapter_(null);
	}
	
	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}
	
	protected XmlJavaTypeAdapterAnnotation getXmlJavaTypeAdapterAnnotation() {
		return (XmlJavaTypeAdapterAnnotation) getJavaResourceAttribute().getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
	}
	
	protected void initializeXmlJavaTypeAdapter() {
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
	
	
	// ***** XmlAdapter *****
	
	public XmlAdapter getXmlAdapter() {
		// see if there is an xml adapter specified on this attribute
		if (this.xmlJavaTypeAdapter != null) {
			return this.xmlJavaTypeAdapter.getXmlAdapter();
		}
		
		String boundTypeName = getBoundTypeName();
		
		// see if there is an xml adapter specified on the type
		JaxbType boundType = getContextRoot().getType(boundTypeName);
		if (boundType != null) {
			XmlJavaTypeAdapter xmlJavaTypeAdapter = boundType.getXmlJavaTypeAdapter();
			if (xmlJavaTypeAdapter != null) {
				return xmlJavaTypeAdapter.getXmlAdapter();
			}
		}
		
		// see if there is an xml adapter on the package
		JaxbPackageInfo pkgInfo = getJaxbClassMapping().getJaxbType().getJaxbPackage().getPackageInfo();
		if (pkgInfo != null) {
			XmlJavaTypeAdapter xmlJavaTypeAdapter = pkgInfo.getXmlJavaTypeAdapter(boundTypeName);
			if (xmlJavaTypeAdapter != null) {
				return xmlJavaTypeAdapter.getXmlAdapter();
			}
		}
		
		// see if there is an xml adapter on the *type's* package
		JavaResourceAbstractType resourceType = getJaxbProject().getJavaResourceType(boundTypeName);
		if (resourceType != null) {
			JaxbPackage pkg = getContextRoot().getPackage(resourceType.getPackageName());
			if (pkg != null) {
				pkgInfo = pkg.getPackageInfo();
				if (pkgInfo != null) {
					XmlJavaTypeAdapter xmlJavaTypeAdapter = pkgInfo.getXmlJavaTypeAdapter(boundTypeName);
					if (xmlJavaTypeAdapter != null) {
						return xmlJavaTypeAdapter.getXmlAdapter();
					}
				}
			}
		}
		
		return null;
	}
	
	
	// ***** misc *****
	
	@Override
	public String getValueTypeName() {
		XmlAdapter xmlAdapter = getXmlAdapter();
		return (xmlAdapter == null) ? super.getValueTypeName() : xmlAdapter.getValueType();
	}
	
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.xmlJavaTypeAdapter != null) {
			this.xmlJavaTypeAdapter.validate(messages, reporter, astRoot);
		}
	}
}
