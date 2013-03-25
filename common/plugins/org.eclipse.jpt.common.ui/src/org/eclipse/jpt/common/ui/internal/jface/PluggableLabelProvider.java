/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * the value returned by the {@link #imageTransformer image transformer} for
 * the element's image.
 * 
 * @param <E> the type of the objects passed to the label provider
 */
public class PluggableLabelProvider<E>
	extends PluggableTextLabelProvider<E>
{
	private volatile Transformer<E, Image> imageTransformer;


	/**
	 * Construct a label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * <code>null</code> for its image.
	 * @see #setImageTransformer(Transformer)
	 * @see org.eclipse.jface.viewers.LabelProvider LabelProvider
	 */
	public PluggableLabelProvider() {
		this(TransformerTools.<E, Image>nullOutputTransformer(), TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified transformer for an element's text and
	 * <code>null</code> for its image.
	 * @see #setImageTransformer(Transformer)
	 */
	public PluggableLabelProvider(Transformer<E, String> textTransformer) {
		this(TransformerTools.<E, Image>nullOutputTransformer(), textTransformer);
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified text transformer for an element's text and
	 * the value returned by the specified image transformer for
	 * the element's image.
	 */
	public PluggableLabelProvider(Transformer<E, Image> imageTransformer, Transformer<E, String> textTransformer) {
		super(textTransformer);
		if (imageTransformer == null) {
			throw new NullPointerException();
		}
		this.imageTransformer = imageTransformer;
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * image {@link Transformer transformer}'s generic type argument {@code <T1>}.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Image getImage(Object element) {
		return this.imageTransformer.transform((E) element);
	}

	public void setImageTransformer(Transformer<E, Image> imageTransformer) {
		if (imageTransformer == null) {
			throw new NullPointerException();
		}
		this.imageTransformer = imageTransformer;
	}
}
