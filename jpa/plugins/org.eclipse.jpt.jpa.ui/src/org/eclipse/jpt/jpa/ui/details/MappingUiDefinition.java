/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A mapping UI definition provides the {@link #getLabel() label} and
 * {@link #getImageDescriptor() image descriptor} for the
 * (type or attribute) mapping type indicated by {@link #getKey()}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface MappingUiDefinition<M, T> {

	/**
	 * Return a key corresponding to the mapping's key.
	 * 
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition#getKey()
	 */
	String getKey();

	/**
	 * Return a string corresponding to the mapping type.
	 */
	String getLabel();

	Transformer<MappingUiDefinition, String> LABEL_TRANSFORMER = new LabelTransformer();
	class LabelTransformer
		extends AbstractTransformer<MappingUiDefinition, String>
	{
		@Override
		public String transform_(MappingUiDefinition def) {
			return def.getLabel();
		}
	}

	/**
	 * Return a string that corresponds to the mapping type and can be used
	 * in the mapping change link label.
	 */
	String getLinkLabel();

	/**
	 * Return an image descriptor corresponding to the mapping type.
	 */
	ImageDescriptor getImageDescriptor();

	Transformer<MappingUiDefinition, ImageDescriptor> IMAGE_DESCRIPTOR_TRANSFORMER = new ImageDescriptorTransformer();
	class ImageDescriptorTransformer
		extends AbstractTransformer<MappingUiDefinition, ImageDescriptor>
	{
		@Override
		public ImageDescriptor transform_(MappingUiDefinition def) {
			return def.getImageDescriptor();
		}
	}

	/**
	 * Return whether the mapping type represented by this definition is enabled for the given
	 * mappable object.  This is almost always true.
	 */
	// TODO bjv remove this; either
	// - delegate to whatever controls the list of UI definitions so it can be overridden by EclipseLink
	// or
	// - delegate to the model definitions (if we think there are more extends that need this...)
	boolean isEnabledFor(M mappableObject);
}
