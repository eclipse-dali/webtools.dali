/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProject.Config;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentField;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentProperty;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.context.JaxbTransientClass;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.internal.context.GenericContextRoot;
import org.eclipse.jpt.jaxb.core.internal.context.GenericPackage;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaEnumConstant;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPackageInfo;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentClass;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentEnum;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentField;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentProperty;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaRegistry;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaTransientClass;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlNs;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlRootElement;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlSchema;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlTransientMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlValueMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public abstract class AbstractJaxbFactory
		implements JaxbFactory {
	
	protected AbstractJaxbFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	public JaxbProject buildJaxbProject(Config config) {
		return new GenericJaxbProject(config);
	}
	
	public JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JptResourceModel resourceModel) {
		return new GenericJaxbFile(jaxbProject, file, contentType, resourceModel);
	}
	
	
	// ********** Non-resource-specific context nodes **********
	
	public JaxbContextRoot buildContextRoot(JaxbProject parent) {
		return new GenericContextRoot(parent);
	}
	
	public JaxbPackage buildPackage(JaxbContextRoot parent, String packageName) {
		return new GenericPackage(parent, packageName);
	}
	
	
	// ********** Java context nodes **********
	
	public JaxbPackageInfo buildJavaPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage) {
		return new GenericJavaPackageInfo(parent, resourcePackage);
	}
	
	public JaxbRegistry buildRegistry(JaxbContextRoot parent, JavaResourceType resourceType) {
		return new GenericJavaRegistry(parent, resourceType);
	}
	
	public JaxbTransientClass buildJavaTransientClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		return new GenericJavaTransientClass(parent, resourceType);
	}
	
	public JaxbPersistentClass buildJavaPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		return new GenericJavaPersistentClass(parent, resourceType);
	}
	
	public JaxbPersistentEnum buildJavaPersistentEnum(JaxbContextRoot parent, JavaResourceEnum resourceEnum) {
		return new GenericJavaPersistentEnum(parent, resourceEnum);
	}
	
	public XmlSchema buildJavaXmlSchema(JaxbPackageInfo parent) {
		return new GenericJavaXmlSchema(parent);
	}
	
	public JaxbEnumConstant buildJavaEnumConstant(JaxbPersistentEnum parent, JavaResourceEnumConstant resourceEnumConstant) {
		return new GenericJavaEnumConstant(parent, resourceEnumConstant);
	}
	
	public XmlNs buildJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
		return new GenericJavaXmlNs(parent, xmlNsAnnotation);
	}

	public XmlRootElement buildJavaXmlRootElement(JaxbPersistentType parent, XmlRootElementAnnotation xmlRootElementAnnotation) {
		return new GenericJavaXmlRootElement(parent, xmlRootElementAnnotation);
	}

	public JaxbElementFactoryMethod buildJavaElementFactoryMethod(JaxbRegistry parent, JavaResourceMethod resourceMethod) {
		return new GenericJavaElementFactoryMethod(parent, resourceMethod);
	}

	public JaxbPersistentField buildJavaPersistentField(JaxbPersistentClass parent, JavaResourceField resourceField) {
		return new GenericJavaPersistentField(parent, resourceField);
	}

	public JaxbPersistentProperty buildJavaPersistentProperty(JaxbPersistentClass parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return new GenericJavaPersistentProperty(parent, resourceGetter, resourceSetter);
	}

	public JaxbAttributeMapping buildJavaNullAttributeMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaNullAttributeMapping(parent);
	}

	public JaxbAttributeMapping buildJavaXmlTransientMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlTransientMapping(parent);
	}

	public XmlAnyAttributeMapping buildJavaXmlAnyAttributeMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlAnyAttributeMapping(parent);
	}

	public XmlAnyElementMapping buildJavaXmlAnyElementMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlAnyElementMapping(parent);
	}

	public XmlAttributeMapping buildJavaXmlAttributeMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlAttributeMapping(parent);
	}

	public XmlElementMapping buildJavaXmlElementMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlElementMapping(parent);
	}

	public XmlValueMapping buildJavaXmlValueMapping(JaxbPersistentAttribute parent) {
		return new GenericJavaXmlValueMapping(parent);
	}
}
