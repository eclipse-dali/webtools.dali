/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * the value returned by the
 * {@link #imageDescriptorTransformer image descriptor transformer} to
 * retrieve the element's image from a {@link ResourceManager resource manager}.
 * The label provider uses a local {@link ResourceManager}
 * to allocate the label images. This resource manager will be disposed
 * when the label provider is disposed.
 * 
 * @param <E> the type of the objects passed to the label provider
 */
public class ResourceManagerLabelProvider<E>
	extends PluggableTextLabelProvider<E>
{
	private volatile Transformer<E, ImageDescriptor> imageDescriptorTransformer;
	private final ResourceManager resourceManager;


	/**
	 * Construct a label provider that uses the
	 * {@link JFaceResources#getResources() default JFace resource manager}
	 * to allocate the label images.
	 * @see #setImageDescriptorTransformer(Transformer)
	 * @see #setTextTransformer(Transformer)
	 */
	public ResourceManagerLabelProvider() {
		this(JFaceResources.getResources());
	}

	/**
	 * Construct a label provider that uses the specified resource manager to
	 * allocate the label images.
	 * @see #setImageDescriptorTransformer(Transformer)
	 * @see #setTextTransformer(Transformer)
	 */
	public ResourceManagerLabelProvider(ResourceManager resourceManager) {
		this(TransformerTools.<E, ImageDescriptor>nullOutputTransformer(), resourceManager);
	}

	/**
	 * Construct a label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * the value returned by the specified image descriptor transformer to
	 * retrieve the element's image from the specified resource manager.
	 * @see #setTextTransformer(Transformer)
	 */
	public ResourceManagerLabelProvider(Transformer<E, ImageDescriptor> imageDescriptorTransformer, ResourceManager resourceManager) {
		this(imageDescriptorTransformer, TransformerTools.<E>objectToStringTransformer(), resourceManager);
	}

	/**
	 * Construct a label provider that returns the value returned by the
	 * specified text transformer for an element's text and
	 * the value returned by the specified image descriptor transformer to
	 * retrieve the element's image from the specified resource manager.
	 */
	public ResourceManagerLabelProvider(Transformer<E, ImageDescriptor> imageDescriptorTransformer, Transformer<E, String> textTransformer, ResourceManager resourceManager) {
		super(textTransformer);
		if (imageDescriptorTransformer == null) {
			throw new NullPointerException();
		}
		this.imageDescriptorTransformer = TransformerTools.nullCheck(imageDescriptorTransformer);
		if (resourceManager == null) {
			throw new NullPointerException();
		}
		this.resourceManager = new LocalResourceManager(resourceManager);
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * image {@link Transformer transformer}'s generic type argument {@code <T1>}.
	 */
	@Override
	public Image getImage(Object element) {
		ImageDescriptor imageDescriptor = this.getImageDescriptor(element);
		return (imageDescriptor == null) ? null : this.resourceManager.createImage(imageDescriptor);
	}

	@SuppressWarnings("unchecked")
	private ImageDescriptor getImageDescriptor(Object element) {
		return this.imageDescriptorTransformer.transform((E) element);
	}

	public void setImageDescriptorTransformer(Transformer<E, ImageDescriptor> imageDescriptorTransformer) {
		if (imageDescriptorTransformer == null) {
			throw new NullPointerException();
		}
		this.imageDescriptorTransformer = imageDescriptorTransformer;
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}
}
