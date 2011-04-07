/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPackage
		extends JaxbContextNode {
	
	/**
	 * The package name.
	 * This is unchanging in that, if a package name changes, a new JaxbPackage is created.
	 */
	String getName();
	
	/**
	 * The optional package-info
	 */
	JaxbPackageInfo getPackageInfo();
	public final static String PACKAGE_INFO_PROPERTY = "package-info"; //$NON-NLS-1$
	
	/**
	 * Return the namespace associated with this package, default or specified
	 */
	String getNamespace();
	
	/**
	 * Return the attribute form default associated with this package
	 */
	XmlNsForm getAttributeFormDefault();
	
	/**
	 * Return the element form default associated with this package
	 */
	XmlNsForm getElementFormDefault();
	
	/**
	 * Return whether this package has no useful information.
	 * Useful information includes:
	 * 	- annotated package-info.java
	 *  - jaxb.index
	 *  - object factory
	 */
	boolean isEmpty();
	
	/**
	 * Return the {@link XsdSchema} associated with this package, if there is one, null otherwise
	 */
	XsdSchema getXsdSchema();
	
	
	// **************** validation ********************************************
	
	/**
	 * Add validation messages to the specified list.
	 */
	public void validate(List<IMessage> messages, IReporter reporter);
}
