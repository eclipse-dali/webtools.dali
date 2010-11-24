/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

/**
 * Use a JAXB factory to build any core (e.g. {@link JaxbProject})
 * model object or any Java (e.g. {@link JavaEntity}), ORM (e.g.
 * {@link EntityMappings}), or persistence (e.g. {@link PersistenceUnit})
 * context model objects.
 * <p>
 * Assumes a base JAXB project context structure 
 * corresponding to the JAXB spec:
 * <pre>
 *     RootContext
 *      |- persistence.xml
 *          |- persistence unit(s)
 *               |- mapping file(s)  (e.g. orm.xml)
 *               |   |- persistent type mapping(s)  (e.g. Entity)
 *               |       |- persistent attribute mapping(s)  (e.g. Basic)
 *               |- persistent type mapping(s)
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
 * @version 3.0
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
	JaxbFile buildJaxbFile(JaxbProject jaxbProject, IFile file, IContentType contentType, JpaResourceModel resourceModel);
	
	
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
	
	JaxbPersistentClass buildPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType);
	
	XmlSchema buildJavaXmlSchema(JaxbPackageInfo parent);
	
	XmlSchemaType buildJavaXmlSchemaType(JaxbContextNode parent, XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation);
	
	XmlJavaTypeAdapter buildJavaXmlJavaTypeAdapter(JaxbContextNode parent, XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation);
	
	XmlNs buildJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation);

	XmlRootElement buildJavaXmlRootElement(JaxbPersistentClass parent, XmlRootElementAnnotation xmlRootElementAnnotation);
}
