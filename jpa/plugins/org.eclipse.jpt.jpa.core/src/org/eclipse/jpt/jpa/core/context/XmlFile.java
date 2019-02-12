/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Collection;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;

/**
 * Context representation of any JPA XML file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface XmlFile
	extends JpaContextModel
{
	/**
	 * Return the XML file's root element.
	 * This may be <code>null</code>.
	 */
	Root getRoot();

	/**
	 * String constant associated with changes to the root property.
	 */
	String ROOT_PROPERTY = "root"; //$NON-NLS-1$

	/**
	 * Return the resource model object
	 */
	JptXmlResource getXmlResource();

	/**
	 * Return whether the XML file's version is the latest supported by its
	 * JPA platform.
	 */
	boolean isLatestSupportedVersion();

	/**
	 * Return whether the XML file is a generic mapping file
	 */
	boolean isGenericMappingFile();


	/**
	 * Add the appropriate root structure nodes to the collection that
	 * correspond to the given JPA file.
	 * @see JpaFile#getRootStructureNodes()
	 */
	void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes);


	// ********** XML file root element **********

	/**
	 * Common interface for the root element for a JPA XML file
	 * (e.g. <code>persistence</code>, <code>entity-mappings</code>).
	 */
	interface Root
		extends JpaStructureNode
	{
		// combine interfaces
	}


	/**
	 * Common implementations.
	 */
	class XmlFile_ {
		/**
		 * @see #isLatestSupportedVersion()
		 */
		public static boolean isLatestSupportedVersion(XmlFile xmlFile) {
			String xmlFileVersion = xmlFile.getXmlResource().getVersion();
			String latestVersion = xmlFile.getJpaProject().getJpaPlatform().getMostRecentSupportedResourceType(xmlFile.getXmlResource().getContentType()).getVersion();
			return ObjectTools.equals(xmlFileVersion, latestVersion);
		}

		/**
		 * @see #isGenericMappingFile()
		 */
		public static boolean isGenericMappingFile(XmlFile xmlFile) {
			IContentType contentType = xmlFile.getXmlResource().getContentType();
			return ObjectTools.equals(contentType, XmlEntityMappings.CONTENT_TYPE);
		}

		private XmlFile_() {
			super();
			throw new UnsupportedOperationException();
		}
	}
}
