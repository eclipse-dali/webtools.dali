/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

/**
 * A table label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * the value returned by the {@link #imageTransformer image transformer} for
 * the element's image.
 * 
 * @parm <E> the type of the objects passed to the label provider
 */
public class PluggableTableLabelProvider<E>
	extends PluggableTextTableLabelProvider<E>
{
	private volatile Transformer<E, Image> imageTransformer;


	/**
	 * Construct a label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * <code>null</code> for its image.
	 * @see #setImageTransformer(ImageTransformer)
	 */
	public PluggableTableLabelProvider() {
		this(DefaultImageTransformer.<E>instance(), DefaultTextTransformer.<E>instance());
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified transformer for an element's text and
	 * <code>null</code> for its image.
	 * @see #setImageTransformer(ImageTransformer)
	 */
	public PluggableTableLabelProvider(TextTransformer<E> textTransformer) {
		this(DefaultImageTransformer.<E>instance(), textTransformer);
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified text transformer for an element's text and
	 * the value returned by the specified image transformer for
	 * the element's image.
	 */
	public PluggableTableLabelProvider(ImageTransformer<E> imageTransformer, TextTransformer<E> textTransformer) {
		super(textTransformer);
		if (imageTransformer == null) {
			throw new NullPointerException();
		}
		this.imageTransformer = imageTransformer;
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * {@link ImageTransformer image transformer}'s generic type argument {@code <E>}.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Image getColumnImage(Object element, int columnIndex) {
		return this.imageTransformer.transform((E) element, columnIndex);
	}

	public void setImageTransformer(ImageTransformer<E> imageTransformer) {
		if (imageTransformer == null) {
			throw new NullPointerException();
		}
		this.imageTransformer = imageTransformer;
	}


	// ********** transformers **********

	public interface ImageTransformer<E>
		extends Transformer<E, Image>
	{
		Image transform(E element, int columnIndex);
	}

	public static class DefaultImageTransformer<E>
		implements ImageTransformer<E>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final ImageTransformer<?> INSTANCE = new DefaultImageTransformer();

		@SuppressWarnings("unchecked")
		public static <R> ImageTransformer<R> instance() {
			return (ImageTransformer<R>) INSTANCE;
		}

		// ensure single instance
		private DefaultImageTransformer() {
			super();
		}

		public Image transform(E element, int columnIndex) {
			return null;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}

		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
