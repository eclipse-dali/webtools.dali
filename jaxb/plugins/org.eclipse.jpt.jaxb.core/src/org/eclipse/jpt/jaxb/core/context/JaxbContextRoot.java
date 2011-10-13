/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Root of the JAXB context model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.0
 */
public interface JaxbContextRoot
		extends JaxbContextNode {
	
	// ***** packages *****
	
	static String PACKAGES_COLLECTION = "packages"; //$NON-NLS-1$
	
	/**
	 * The set of packages.  Includes any package with any interesting JAXB content.
	 */
	Iterable<JaxbPackage> getPackages();
	
	/**
	 * Return the package with the given name
	 */
	JaxbPackage getPackage(String packageName);
	
	int getPackagesSize();
	
	
	// ***** jaxb types *****
	
	public final static String TYPES_COLLECTION = "types"; //$NON-NLS-1$
	
	/**
	 * Return the set of all JAXB types within this context root.
	 */
	Iterable<JaxbType> getTypes();
	
	/**
	 * Return the type with the given (fully qualified) type name
	 */
	JaxbType getType(String typeName);
	
	int getTypesSize();
	
	/**
	 * Return the set of types that are in the given package
	 */
	Iterable<JaxbType> getTypes(JaxbPackage jaxbPackage);
	
	/**
	 * The set of jaxb classes.  These may be explicitly or implicitly included.
	 */
	Iterable<JaxbClass> getClasses();
	
	/**
	 * Return the set of jaxb classes that are in the given package
	 */
	Iterable<JaxbClass> getClasses(JaxbPackage jaxbPackage);
	
	/**
	 * The set of jaxb enums.  These may be explicitly or implicitly included.
	 */
	Iterable<JaxbEnum> getEnums();
	
	/**
	 * Return the set of jaxb enums that are in the given package
	 */
	Iterable<JaxbEnum> getEnums(JaxbPackage jaxbPackage);
	
	/**
	 * Return the set of {@link XmlRegistry}(ie)s that are in the given package
	 */
	Iterable<XmlRegistry> getXmlRegistries(JaxbPackage jaxbPackage);
	
	/**
	 * Return the {@link JaxbTypeMapping} for the given (fully qualified) type name
	 */
	JaxbTypeMapping getTypeMapping(String typeName);
	
	/**
	 * Return the {@link JaxbClassMapping} for the given (fully qualified) type name
	 */
	JaxbClassMapping getClassMapping(String typeName);
	
	
	// ***** validation *****
	
	/**
	 * Add validation messages to the specified list.
	 */
	public void validate(List<IMessage> messages, IReporter reporter);
}
