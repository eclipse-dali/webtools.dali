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

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * <code>null</code> for its image.
 * 
 * @see org.eclipse.jface.viewers.LabelProvider LabelProvider
 * 
 * @param <E> the type of the objects passed to the label provider
 */
public class PluggableTextLabelProvider<E>
	extends BaseLabelProvider
	implements ILabelProvider
{
	private volatile Transformer<E, String> textTransformer;


	/**
	 * Construct a label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * <code>null</code> for its image.
	 * @see #setTextTransformer(Transformer)
	 * @see org.eclipse.jface.viewers.LabelProvider LabelProvider
	 */
	public PluggableTextLabelProvider() {
		this(TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified transformer for an element's text and
	 * <code>null</code> for its image.
	 */
	public PluggableTextLabelProvider(Transformer<E, String> textTransformer) {
		super();
		if (textTransformer == null) {
			throw new NullPointerException();
		}
		this.textTransformer = textTransformer;
	}

	/**
	 * Return <code>null</code>.
	 */
	public Image getImage(Object element) {
		return null;
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * text {@link Transformer transformer}'s generic type argument {@code <T1>}.
	 */
	@SuppressWarnings("unchecked")
	public String getText(Object element) {
		return this.textTransformer.transform((E) element);
	}

	public void setTextTransformer(Transformer<E, String> textTransformer) {
		if (textTransformer == null) {
			throw new NullPointerException();
		}
		this.textTransformer = textTransformer;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
