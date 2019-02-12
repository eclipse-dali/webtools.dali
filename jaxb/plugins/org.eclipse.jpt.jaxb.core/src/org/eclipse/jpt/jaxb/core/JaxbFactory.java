/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
 * @version 3.3
 * @since 3.0
 */
public interface JaxbFactory  {
	
	// ***** Core model *****
	
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
	
	
	// ***** Non-resource-specific context nodes *****
	
	/**
	 * Build a (/an updated) root context node to be associated with the given 
	 * JAXB project.
	 * The root context node will be built once, but updated many times.
	 * @see JaxbProject#update(org.eclipse.core.runtime.IProgressMonitor)
	 */
	JaxbContextRoot buildContextRoot(JaxbProject jaxbProject);
	
	JaxbPackage buildPackage(JaxbContextRoot parent, String packageName);
	
	
	// ***** Java context nodes *****
	
	JaxbPackageInfo buildPackageInfo(JaxbPackage parent, JavaResourcePackage resourcePackage);
	
	JavaXmlSchema buildJavaXmlSchema(JaxbPackageInfo parent);
	
	XmlNs buildJavaXmlNs(JavaXmlSchema parent, XmlNsAnnotation xmlNsAnnotation);
	
	JavaClass buildJavaClass(JaxbContextRoot parent, JavaResourceType resourceType);
	
	JavaEnum buildJavaEnum(JaxbContextRoot parent, JavaResourceEnum resourceEnum);
	
	JavaClassMapping buildJavaClassMapping(JavaClass parent);
	
	JavaEnumMapping buildJavaEnumMapping(JavaEnum parent);
	
	XmlRegistry buildXmlRegistry(JavaClass parent);
	
	JaxbElementFactoryMethod buildJavaElementFactoryMethod(XmlRegistry parent, JavaResourceMethod resourceMethod);
	
	XmlRootElement buildJavaXmlRootElement(JavaTypeMapping parent, XmlRootElementAnnotation xmlRootElementAnnotation);
	
	JavaPersistentAttribute buildJavaPersistentAttribute(JavaClassMapping parent, Accessor accessor);
	
	JavaPersistentAttribute buildJavaPersistentField(JaxbClassMapping parent, JavaResourceField resourceField);
	
	JavaPersistentAttribute buildJavaPersistentProperty(JaxbClassMapping parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);
	
	JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent);
	
	JavaXmlAnyAttributeMapping buildJavaXmlAnyAttributeMapping(JavaPersistentAttribute parent);
	
	JavaXmlAnyElementMapping buildJavaXmlAnyElementMapping(JavaPersistentAttribute parent);
	
	JavaXmlAttributeMapping buildJavaXmlAttributeMapping(JavaPersistentAttribute parent);
	
	JavaXmlElementMapping buildJavaXmlElementMapping(JavaPersistentAttribute parent);
	
	JavaXmlElementRefMapping buildJavaXmlElementRefMapping(JavaPersistentAttribute parent);
	
	JavaXmlElementRefsMapping buildJavaXmlElementRefsMapping(JavaPersistentAttribute parent);
	
	JavaXmlElementsMapping buildJavaXmlElementsMapping(JavaPersistentAttribute parent);
	
	JavaAttributeMapping buildJavaXmlTransientMapping(JavaPersistentAttribute parent);
	
	JavaXmlValueMapping buildJavaXmlValueMapping(JavaPersistentAttribute parent);
	
	JaxbEnumConstant buildJavaEnumConstant(JavaEnumMapping parent, JavaResourceEnumConstant resourceEnumConstant);
}
