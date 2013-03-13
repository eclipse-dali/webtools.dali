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
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

/**
 * A table label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * <code>null</code> for its image.
 * 
 * @see org.eclipse.jface.viewers.LabelProvider LabelProvider
 * 
 * @param <E> the type of the objects passed to the label provider
 */
public class PluggableTextTableLabelProvider<E>
	extends BaseLabelProvider
	implements ITableLabelProvider
{
	private volatile TextTransformer<E> textTransformer;


	/**
	 * Construct a label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * <code>null</code> for its image.
	 * @see #setTextTransformer(TextTransformer)
	 */
	public PluggableTextTableLabelProvider() {
		this(DefaultTextTransformer.<E>instance());
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified transformer for an element's text and
	 * <code>null</code> for its image.
	 */
	public PluggableTextTableLabelProvider(TextTransformer<E> textTransformer) {
		super();
		if (textTransformer == null) {
			throw new NullPointerException();
		}
		this.textTransformer = textTransformer;
	}

	/**
	 * Return <code>null</code>.
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * {@link TextTransformer text transformer}'s generic type argument {@code <E>}.
	 */
	@SuppressWarnings("unchecked")
	public String getColumnText(Object element, int columnIndex) {
		return this.textTransformer.transform((E) element, columnIndex);
	}

	public void setTextTransformer(TextTransformer<E> textTransformer) {
		if (textTransformer == null) {
			throw new NullPointerException();
		}
		this.textTransformer = textTransformer;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** transformers **********

	public interface Transformer<T1, T2> {
		T2 transform(T1 element, int columnIndex);
	}

	public interface TextTransformer<E>
		extends Transformer<E, String>
	{
		String transform(E element, int columnIndex);
	}

	public static class DefaultTextTransformer<E>
		implements TextTransformer<E>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final TextTransformer<?> INSTANCE = new DefaultTextTransformer();

		@SuppressWarnings("unchecked")
		public static <R> TextTransformer<R> instance() {
			return (TextTransformer<R>) INSTANCE;
		}

		// ensure single instance
		private DefaultTextTransformer() {
			super();
		}

		public String transform(E element, int columnIndex) {
			return (element == null) ? null : element.toString();
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
