/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

/**
 * JAXB Java-related stuff (annotations etc.)
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
@SuppressWarnings("nls")
public interface JAXB {

	// JAXB package
	String PACKAGE = "javax.xml.bind.annotation";
	String PACKAGE_ = PACKAGE + '.';


	// ********** API **********

	// JAXB annotations
	String XML_TYPE = PACKAGE_ + "XmlType";
		String XML_TYPE__FACTORY_CLASS = "factoryClass";
		String XML_TYPE__FACTORY_METHOD = "factoryMethod";
		String XML_TYPE__NAME = "name";
		String XML_TYPE__NAMESPACE = "namespace";
		String XML_TYPE__PROP_ORDER = "propOrder";

}
