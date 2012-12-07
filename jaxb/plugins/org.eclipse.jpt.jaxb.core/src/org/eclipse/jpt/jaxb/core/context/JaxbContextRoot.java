/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;

/**
 * Root of the JAXB context model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
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
	
	
	// ***** java types *****
	
	public final static String JAVA_TYPES_COLLECTION = "javaTypes"; //$NON-NLS-1$
	
	/**
	 * Return the set of all java types within this context root.
	 */
	Iterable<JavaType> getJavaTypes();
	
	/**
	 * Return the java type with the given (fully qualified) name
	 */
	JavaType getJavaType(String typeName);
	
	int getJavaTypesSize();
	
	/**
	 * Return the set of java types that are in the given package
	 */
	Iterable<JavaType> getJavaTypes(JaxbPackage jaxbPackage);
	
	/**
	 * The set of java classes.  These may be explicitly or implicitly included.
	 */
	Iterable<JavaClass> getJavaClasses();
	
	/**
	 * Return the set of java classes that are in the given package
	 */
	Iterable<JavaClass> getJavaClasses(JaxbPackage jaxbPackage);
	
	/**
	 * The set of java enums.  These may be explicitly or implicitly included.
	 */
	Iterable<JavaEnum> getJavaEnums();
	
	/**
	 * Return the set of jaxb enums that are in the given package
	 */
	Iterable<JavaEnum> getJavaEnums(JaxbPackage jaxbPackage);
	
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
}
