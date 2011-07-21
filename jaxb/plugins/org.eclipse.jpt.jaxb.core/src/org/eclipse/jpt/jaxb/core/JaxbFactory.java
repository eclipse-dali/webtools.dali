/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.context.Accessor;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.context.JaxbTransientClass;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementsMapping;
import org.eclipse.jpt.jaxb.core.context.XmlMixedMapping;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

/**
 * Use a JAXB factory to build any core (e.g. {@link JaxbProject})
 * model object or any Java (e.g. {@link XmlType}) context model objects
 * <p>
 * Assumes a base JAXB project context structure 
 * corresponding to the JAXB spec:
 * <pre>
 *     RootContext
 *      |- jaxb packages/types
 *          |- jaxb attributes/methods
 * </pre>
 *   ... and associated objects.
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.jaxb.core.internal.jaxb21.GenericJaxb_2_1_Factory
 * 
 * @version 3.1
 * @since 3.0
 */
public interface JaxbFactory  {
	
	// ********** Core Model **********
	
	/**
	 * Construct a JaxbProject for the specified config, to be
	 * added to the specified JAXB project. Return null if unable to create
	 * the JAXB file (e.g. the content type is unrecognized).
	 */
	JaxbProject buildJaxbProject(JaxbProject.Config config);
	
	/**
	 * Construct a JAXB file for the specified JAXB project, file, content type,
	 * and resource model.
	 */
	JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JptResourceModel resourceModel);
	
	
	// ********** Non-resource-specific context nodes **********
	
	/**
	 * Build a (/an updated) root context node to be associated with the given 
	 * JAXB project.
	 * The root context node will be built once, but updated many times.
	 * @see JaxbProject#update(org.eclipse.core.runtime.IProgressMonitor)
	 */
	JaxbContextRoot buildContextRoot(JaxbProject jaxbProject);
	
	JaxbPackage buildPackage(JaxbContextRoot parent, String packageName);
	
	
	// ********** Java context nodes **********
	
	JaxbPackageInfo buildJavaPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage);
	
	JaxbRegistry buildRegistry(JaxbContextRoot parent, JavaResourceType resourceType);

	JaxbTransientClass buildJavaTransientClass(JaxbContextRoot parent, JavaResourceType resourceType);
	
	JaxbPersistentClass buildJavaPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType);

	JaxbPersistentEnum buildJavaPersistentEnum(JaxbContextRoot parent, JavaResourceEnum resourceEnum);

	XmlSchema buildJavaXmlSchema(JaxbPackageInfo parent);
	
	XmlNs buildJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation);

	XmlRootElement buildJavaXmlRootElement(JaxbPersistentType parent, XmlRootElementAnnotation xmlRootElementAnnotation);

	JaxbEnumConstant buildJavaEnumConstant(JaxbPersistentEnum parent, JavaResourceEnumConstant resourceEnumConstant);

	JaxbElementFactoryMethod buildJavaElementFactoryMethod(JaxbRegistry parent, JavaResourceMethod resourceMethod);

	JaxbPersistentAttribute buildJavaPersistentAttribute(JaxbPersistentClass parent, Accessor accessor);

	JaxbPersistentAttribute buildJavaPersistentField(JaxbPersistentClass parent, JavaResourceField resourceField);

	JaxbPersistentAttribute buildJavaPersistentProperty(JaxbPersistentClass parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);

	JaxbAttributeMapping buildJavaNullAttributeMapping(JaxbPersistentAttribute parent);

	XmlAnyAttributeMapping buildJavaXmlAnyAttributeMapping(JaxbPersistentAttribute parent);

	XmlAnyElementMapping buildJavaXmlAnyElementMapping(JaxbPersistentAttribute parent);

	XmlAttributeMapping buildJavaXmlAttributeMapping(JaxbPersistentAttribute parent);

	XmlElementMapping buildJavaXmlElementMapping(JaxbPersistentAttribute parent);
	
	XmlElementRefMapping buildJavaXmlElementRefMapping(JaxbPersistentAttribute parent);
	
	XmlElementRefsMapping buildJavaXmlElementRefsMapping(JaxbPersistentAttribute parent);
	
	XmlElementsMapping buildJavaXmlElementsMapping(JaxbPersistentAttribute parent);
	
	XmlMixedMapping buildJavaXmlMixedMapping(JaxbPersistentAttribute parent);
	
	JaxbAttributeMapping buildJavaXmlTransientMapping(JaxbPersistentAttribute parent);
	
	XmlValueMapping buildJavaXmlValueMapping(JaxbPersistentAttribute parent);
}
