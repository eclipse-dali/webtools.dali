/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.Accessor;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlElementsMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlSchema;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlValueMapping;
import org.eclipse.jpt.jaxb.core.internal.context.GenericContextRoot;
import org.eclipse.jpt.jaxb.core.internal.context.GenericPackage;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaClassMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaEnumConstant;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaEnumMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbClass;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbEnum;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPackageInfo;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementsMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlNs;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlRegistry;
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
	
	
	// ***** Java context nodes *****
	
	public JaxbPackageInfo buildPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage) {
		return new GenericJavaPackageInfo(parent, resourcePackage);
	}
	
	public JavaXmlSchema buildJavaXmlSchema(JaxbPackageInfo parent) {
		return new GenericJavaXmlSchema(parent);
	}
	
	public XmlNs buildJavaXmlNs(JavaXmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
		return new GenericJavaXmlNs(parent, xmlNsAnnotation);
	}
	
	public JavaClass buildJavaClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		return new GenericJavaJaxbClass(parent, resourceType);
	}
	
	public JavaEnum buildJavaEnum(JaxbContextRoot parent, JavaResourceEnum resourceEnum) {
		return new GenericJavaJaxbEnum(parent, resourceEnum);
	}
	
	public JavaClassMapping buildJavaClassMapping(JavaClass parent) {
		return new GenericJavaClassMapping(parent);
	}
	
	public JavaEnumMapping buildJavaEnumMapping(JavaEnum parent) {
		return new GenericJavaEnumMapping(parent);
	}
	
	public XmlRegistry buildXmlRegistry(JavaClass parent) {
		return new GenericJavaXmlRegistry(parent);
	}
	
	public JaxbElementFactoryMethod buildJavaElementFactoryMethod(XmlRegistry parent, JavaResourceMethod resourceMethod) {
		return new GenericJavaElementFactoryMethod(parent, resourceMethod);
	}
	
	public XmlRootElement buildJavaXmlRootElement(JavaTypeMapping parent, XmlRootElementAnnotation xmlRootElementAnnotation) {
		return new GenericJavaXmlRootElement(parent, xmlRootElementAnnotation);
	}
	
	public JavaPersistentAttribute buildJavaPersistentAttribute(JavaClassMapping parent, Accessor accessor) {
		return new GenericJavaPersistentAttribute(parent, accessor);
	}
	
	public JavaPersistentAttribute buildJavaPersistentField(JaxbClassMapping parent, JavaResourceField resourceField) {
		return GenericJavaPersistentAttribute.buildPersistentField(parent, resourceField);
	}
	
	public JavaPersistentAttribute buildJavaPersistentProperty(JaxbClassMapping parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return GenericJavaPersistentAttribute.buildPersistentProperty(parent, resourceGetter, resourceSetter);
	}
	
	public JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent) {
		return new GenericJavaNullAttributeMapping(parent);
	}
	
	public JavaAttributeMapping buildJavaXmlTransientMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlTransientMapping(parent);
	}
	
	public JavaXmlAnyAttributeMapping buildJavaXmlAnyAttributeMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlAnyAttributeMapping(parent);
	}
	
	public JavaXmlAnyElementMapping buildJavaXmlAnyElementMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlAnyElementMapping(parent);
	}
	
	public JavaXmlAttributeMapping buildJavaXmlAttributeMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlAttributeMapping(parent);
	}
	
	public JavaXmlElementMapping buildJavaXmlElementMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlElementMapping(parent);
	}
	
	public JavaXmlElementRefMapping buildJavaXmlElementRefMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlElementRefMapping(parent);
	}
	
	public JavaXmlElementRefsMapping buildJavaXmlElementRefsMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlElementRefsMapping(parent);
	}
	
	public JavaXmlElementsMapping buildJavaXmlElementsMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlElementsMapping(parent);
	}
	
	public JavaXmlValueMapping buildJavaXmlValueMapping(JavaPersistentAttribute parent) {
		return new GenericJavaXmlValueMapping(parent);
	}
	
	public JaxbEnumConstant buildJavaEnumConstant(JavaEnumMapping parent, JavaResourceEnumConstant resourceEnumConstant) {
		return new GenericJavaEnumConstant(parent, resourceEnumConstant);
	}
}
