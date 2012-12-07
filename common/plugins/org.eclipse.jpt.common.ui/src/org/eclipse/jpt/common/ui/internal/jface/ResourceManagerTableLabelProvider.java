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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

/**
 * A table label provider that returns the value returned by the
 * {@link #textTransformer text transformer} for an element's text and
 * the value returned by the
 * {@link #imageDescriptorTransformer image descriptor transformer} to
 * retrieve the element's image from a {@link ResourceManager resource manager}.
 * The label provider uses a local {@link ResourceManager}
 * to allocate the label images. This resource manager will be disposed
 * when the label provider is disposed.
 * 
 * @parm <E> the type of the objects passed to the label provider
 */
public class ResourceManagerTableLabelProvider<E>
	extends PluggableTextTableLabelProvider<E>
{
	private volatile ImageDescriptorTransformer<E> imageDescriptorTransformer;
	private final ResourceManager resourceManager;


	/**
	 * Construct a table label provider that uses the
	 * {@link JFaceResources#getResources() default JFace resource manager}
	 * to allocate the label images.
	 * @see #setImageDescriptorTransformer(ImageDescriptorTransformer)
	 * @see #setTextTransformer(TextTransformer)
	 */
	public ResourceManagerTableLabelProvider() {
		this(JFaceResources.getResources());
	}

	/**
	 * Construct a table label provider that uses the specified resource manager to
	 * allocate the label images.
	 * @see #setImageDescriptorTransformer(ImageDescriptorTransformer)
	 * @see #setTextTransformer(TextTransformer)
	 */
	public ResourceManagerTableLabelProvider(ResourceManager resourceManager) {
		this(DefaultImageDescriptorTransformer.<E>instance(), resourceManager);
	}

	/**
	 * Construct a table label provider that returns an element's
	 * {@link Object#toString() toString()} value for its text and
	 * the value returned by the specified image descriptor transformer to
	 * retrieve the element's image from the specified resource manager.
	 * @see #setTextTransformer(TextTransformer)
	 */
	public ResourceManagerTableLabelProvider(ImageDescriptorTransformer<E> imageDescriptorTransformer, ResourceManager resourceManager) {
		this(imageDescriptorTransformer, DefaultTextTransformer.<E>instance(), resourceManager);
	}

	/**
	 * Construct a table label provider that returns the value returned by the
	 * specified text transformer for an element's text and
	 * the value returned by the specified image descriptor transformer to
	 * retrieve the element's image from the specified resource manager.
	 */
	public ResourceManagerTableLabelProvider(ImageDescriptorTransformer<E> imageDescriptorTransformer, TextTransformer<E> textTransformer, ResourceManager resourceManager) {
		super(textTransformer);
		if ((imageDescriptorTransformer == null) || (resourceManager == null)) {
			throw new NullPointerException();
		}
		this.imageDescriptorTransformer = imageDescriptorTransformer;
		this.resourceManager = new LocalResourceManager(resourceManager);
	}

	/**
	 * @exception ClassCastException if the element is not the same type as the
	 * {@link ImageDescriptorTransformer image descriptor transformer}'s
	 * generic type argument {@code <T1>}.
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		ImageDescriptor imageDescriptor = this.getColumnImageDescriptor(element, columnIndex);
		return (imageDescriptor == null) ? null : this.resourceManager.createImage(imageDescriptor);
	}

	@SuppressWarnings("unchecked")
	private ImageDescriptor getColumnImageDescriptor(Object element, int columnIndex) {
		return this.imageDescriptorTransformer.transform((E) element, columnIndex);
	}

	public void setImageDescriptorTransformer(ImageDescriptorTransformer<E> imageDescriptorTransformer) {
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


	// ********** transformers **********

	public interface ImageDescriptorTransformer<E>
		extends Transformer<E, ImageDescriptor>
	{
		ImageDescriptor transform(E element, int columnIndex);
	}

	public static class DefaultImageDescriptorTransformer<E>
		implements ImageDescriptorTransformer<E>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final ImageDescriptorTransformer<?> INSTANCE = new DefaultImageDescriptorTransformer();

		@SuppressWarnings("unchecked")
		public static <R> ImageDescriptorTransformer<R> instance() {
			return (ImageDescriptorTransformer<R>) INSTANCE;
		}

		// ensure single instance
		private DefaultImageDescriptorTransformer() {
			super();
		}

		public ImageDescriptor transform(E element, int columnIndex) {
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
