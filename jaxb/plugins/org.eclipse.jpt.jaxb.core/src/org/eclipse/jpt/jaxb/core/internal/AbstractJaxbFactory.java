/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProject.Config;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.context.GenericContextRoot;
import org.eclipse.jpt.jaxb.core.internal.context.GenericPackage;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPackageInfo;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaPersistentClass;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlNs;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlRootElement;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlSchema;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

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
	
	public JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JpaResourceModel resourceModel) {
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
	
	public JaxbPersistentClass buildPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		return new GenericJavaPersistentClass(parent, resourceType);
	}
	
	public XmlSchema buildJavaXmlSchema(JaxbPackageInfo parent) {
		return new GenericJavaXmlSchema(parent);
	}
	
	public XmlSchemaType buildJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation resourceXmlSchemaType) {
		return new GenericJavaXmlSchemaType(parent, resourceXmlSchemaType);
	}
	
	public XmlJavaTypeAdapter buildJavaXmlJavaTypeAdapter(JaxbContextNode parent, XmlJavaTypeAdapterAnnotation resourceXmlJavaTypeAdapter) {
		return new GenericJavaXmlJavaTypeAdapter(parent, resourceXmlJavaTypeAdapter);
	}
	
	public XmlNs buildJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
		return new GenericJavaXmlNs(parent, xmlNsAnnotation);
	}

	public XmlRootElement buildJavaXmlRootElement(JaxbPersistentClass parent, XmlRootElementAnnotation xmlRootElementAnnotation) {
		return new GenericJavaXmlRootElement(parent, xmlRootElementAnnotation);
	}
}
