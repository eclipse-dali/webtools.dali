/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;

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
	Transformer<JaxbPackage, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<JaxbPackage, String>
	{
		@Override
		public String transform(JaxbPackage table) {
			return table.getName();
		}
	}
	
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
	 * Return the {@link XmlRegistry} for this package if there is one.
	 * Return *one* if there are more than one (which is an error case at any rate)
	 */
	XmlRegistry getRegistry();
	
	/**
	 * Return the {@link XsdSchema} associated with this package, if there is one, null otherwise
	 */
	XsdSchema getXsdSchema();
}
