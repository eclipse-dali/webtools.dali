/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.PersistentType;

/**
 * JPA 2.0 project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaProject2_0
	extends JpaProject, MetamodelGenerator
{

	// ********** Canonical Metamodel **********

	/**
	 * ID string used when generatesMetamodel property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String GENERATES_METAMODEL_PROPERTY = "generatesMetamodel"; //$NON-NLS-1$

	/**
	 * Return whether the JPA project will generate a Canonical Metamodel
	 * automatically.
	 */
	boolean generatesMetamodel();

	/**
	 * Set whether the JPA project will generate a Canonical Metamodel
	 * automatically.
	 */
	void setGeneratesMetamodel(boolean generatesMetamodel);

	/**
	 * ID string used when metamodelSourceFolderName property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	String METAMODEL_SOURCE_FOLDER_NAME_PROPERTY = "metamodelSourceFolderName"; //$NON-NLS-1$

	/**
	 * Return the name of the folder that holds the generated Canonical
	 * Metamodel.
	 */
	String getMetamodelSourceFolderName();

	/**
	 * Set the name of the folder that holds the generated Canonical
	 * Metamodel.
	 */
	void setMetamodelSourceFolderName(String metamodelSourceFolderName);

	/**
	 * Return the package fragment root that holds the generated Canonical
	 * Metamodel.
	 */
	IPackageFragmentRoot getMetamodelPackageFragmentRoot();

	/**
	 * Synchronize the source for the metamodel class corresponding to the
	 * specified persistent type.
	 */
	void synchronizeMetamodel(PersistentType persistentType);


	// ********** construction config **********

	/**
	 * The settings used to construct a JPA 2.0 project.
	 */
	interface Config extends JpaProject.Config {

		/**
		 * Return whether the new JPA project is to generate a Canonical
		 * Metamodel.
		 */
		boolean generatesMetamodel();

		/**
		 * Return the name of the folder that holds the generated Canonical
		 * Metamodel.
		 */
		String getMetamodelSourceFolderName();

	}

}
