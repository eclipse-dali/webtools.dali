/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.jaxbindex;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;

public interface JaxbIndexResource
		extends JptResourceModel {
	
	/**
	 * The content type for <code>jaxb.index</code> files.
	 */
	IContentType CONTENT_TYPE = JptJaxbCorePlugin.instance().getContentType("jaxbIndex"); //$NON-NLS-1$

	/**
	 * The resource type for <code>jaxb.index</code> files.
	 */
	JptResourceType RESOURCE_TYPE = ContentTypeTools.getResourceType(CONTENT_TYPE);

	String getPackageName();
	
	Iterable<String> getFullyQualifiedClassNames();
	Transformer<JaxbIndexResource, Iterable<String>> CLASS_NAMES_TRANSFORMER = new ClassNamesTransformer();
	class ClassNamesTransformer
		extends TransformerAdapter<JaxbIndexResource, Iterable<String>>
	{
		@Override
		public Iterable<String> transform(JaxbIndexResource jaxbIndexResource) {
			return jaxbIndexResource.getFullyQualifiedClassNames();
		}
	}
}
