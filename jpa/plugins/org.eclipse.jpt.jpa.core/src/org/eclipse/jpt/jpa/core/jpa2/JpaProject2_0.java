/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA 2.0 project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaProject2_0
	extends JpaProject
{
	// ********** JPA facet **********

	/**
	 * The JPA 2.0 project facet version string.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_VERSION_STRING = "2.0"; //$NON-NLS-1$

	/**
	 * The JPA 2.0 project facet version.
	 */
	IProjectFacetVersion FACET_VERSION = FACET.getVersion(FACET_VERSION_STRING);


	// ********** Canonical Metamodel **********

	/**
	 * ID string used when metamodelSourceFolderName property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String METAMODEL_SOURCE_FOLDER_NAME_PROPERTY = "metamodelSourceFolderName"; //$NON-NLS-1$

	/**
	 * Return the name of the folder that holds the generated Canonical
	 * Metamodel.
	 */
	String getMetamodelSourceFolderName();
		Transformer<JpaProject2_0, String> METAMODEL_SOURCE_FOLDER_NAME_TRANSFORMER = new MetamodelSourceFolderNameTransformer();
	class MetamodelSourceFolderNameTransformer
		extends TransformerAdapter<JpaProject2_0, String>
	{
		@Override
		public String transform(JpaProject2_0 jpaProject) {
			return jpaProject.getMetamodelSourceFolderName();
		}
	}

	/**
	 * Set the name of the folder that holds the generated Canonical
	 * Metamodel.
	 */
	void setMetamodelSourceFolderName(String metamodelSourceFolderName);
		BiClosure<JpaProject2_0, String> SET_METAMODEL_SOURCE_FOLDER_NAME_CLOSURE = new SetMetamodelSourceFolderNameClosure();
	class SetMetamodelSourceFolderNameClosure
		extends BiClosureAdapter<JpaProject2_0, String>
	{
		@Override
		public void execute(JpaProject2_0 jpaProject, String metamodelSourceFolderName) {
			jpaProject.setMetamodelSourceFolderName(metamodelSourceFolderName);
		}
	}

	/**
	 * Return the package fragment root that holds the generated Canonical
	 * Metamodel.
	 */
	IPackageFragmentRoot getMetamodelPackageFragmentRoot();

	/**
	 * Return a list of the names of the Java source folders. (These
	 * can be used to hold the generated Canonical Metamodel.)
	 */
	Iterable<String> getJavaSourceFolderNames();

	/**
	 * Return the JPA project's generated metamodel Java resource
	 * top-level types.
	 * @see JpaMetamodelSynchronizer2_0.MetamodelTools#isGeneratedMetamodelTopLevelType(JavaResourceAbstractType, IPackageFragmentRoot)
	 */
	Iterable<JavaResourceAbstractType> getGeneratedMetamodelTopLevelTypes();

	/**
	 * Return the top-level generated metamodel Java resource persistent type
	 * in the specified file. Return null if any of the following is true:<ul>
	 * <li>the file is not a Java source file
	 * <li>the top-level Java class is not annotated with the appropriate
	 *     <code>javax.annotation.Generated</code> annotation
	 * <li>neither the top-level Java class nor any of its nested classes
	 *     is annotated with the appropriate
	 *     <code>javax.persistence.metamodel.StaticMetamodel</code> annotation
	 * <ul>
	 */
	JavaResourceAbstractType getGeneratedMetamodelTopLevelType(IFile file);


	// ********** construction config **********

	/**
	 * The settings used to construct a JPA 2.0 project.
	 */
	interface Config
		extends JpaProject.Config
	{
		/**
		 * Return the name of the folder that holds the generated Canonical
		 * Metamodel. Return null if the Canonical Metamodel is not to be
		 * generated.
		 */
		String getMetamodelSourceFolderName();
	}
}
