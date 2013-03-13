/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Specifies the file content type and version for Dali resources.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JptResourceTypeManager
 * @version 3.3
 * @since 2.3
 */
public interface JptResourceType
	extends ContentTypeReference, Comparable<JptResourceType>
{
	/**
	 * Return the resource type's manager.
	 */
	JptResourceTypeManager getManager();

	/**
	 * Return the resource type's extension-supplied ID.
	 * This is unique among all the resource types.
	 */
	String getId();

	/**
	 * Return the resource type's version.
	 * @see org.eclipse.jpt.common.utility.internal.VersionComparator
	 */
	String getVersion();

	Transformer<JptResourceType, String> VERSION_TRANSFORMER = new VersionTransformer();
	class VersionTransformer
		extends TransformerAdapter<JptResourceType, String>
	{
		@Override
		public String transform(JptResourceType resourceType) {
			return resourceType.getVersion();
		}
	}

	/**
	 * Version used when version cannot be determined or when there is no
	 * sense of version relevant to JPA (e.g. Java). An indeterminate version
	 * is compatible only with another indeterminate version; while all
	 * specified versions are compatible with an indeterminate version.
	 */
	String UNDETERMINED_VERSION = "<undetermined>"; //$NON-NLS-1$

	/**
	 * Return the resource type's base types.
	 * @see #isKindOf(JptResourceType)
	 */
	Iterable<JptResourceType> getBaseTypes();

	/**
	 * Return whether either of the following is <code>true</code>:
	 * <li>Both of the following are <code>true</code>:<ul>
	 *     <li>the resource type's content type
	 *         {@link IContentType#isKindOf(IContentType) is a kind of} the
	 *         specified resource type's content type
	 *     <li>the resource type's version is compatible with the
	 *         specified resource type's version
	 *         (i.e. the resource type's version is greater than or
	 *         equal to the specified resource type's version);
	 *         if the resource type's version is {@link #UNDETERMINED_VERSION
	 *         indeterminate}, it is compatible only if the specified resource
	 *         type's version is also indeterminate
	 *     </ul>
	 * <li>Any one of the resource type's {@link #getBaseTypes() base types}
	 *     {@link #isKindOf(JptResourceType) is a kind of} the specified
	 *     resource type
	 * <ul>
	 */
	boolean isKindOf(JptResourceType resourceType);

	/**
	 * Return the ID of the plug-in that contributed the resource type.
	 */
	String getPluginId();
}
