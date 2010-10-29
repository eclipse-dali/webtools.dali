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

/**
 * Root of the Dali context model.
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
public interface JaxbRootContextNode
	extends JaxbContextNode
{

	Iterable<JaxbPackage> getPackages();

	int getPackagesSize();

		/**
		 * String constant associated with changes to the packages property
		 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
		 */
		public final static String PACKAGES_COLLECTION = "packages"; //$NON-NLS-1$


//	// ********** validation **********
//
//	/**
//	 * Add validation messages to the specified list.
//	 */
//	public void validate(List<IMessage> messages, IReporter reporter);

}
