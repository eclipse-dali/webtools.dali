/*******************************************************************************
 * Copyright (c) 2006, 2012 IBM Corporation and others.
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.util.Arrays;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * An overlay image descriptor is an image descriptor that can be used
 * to overlay decoration images on to the 4 corner quadrants of a base image.
 * The four quadrants are:<ul>
 * <li>{@link IDecoration#TOP_LEFT}
 * <li>{@link IDecoration#TOP_RIGHT}
 * <li>{@link IDecoration#BOTTOM_LEFT}
 * <li>{@link IDecoration#BOTTOM_RIGHT}
 * </ul>
 * Additionally, the overlay can be used to provide an underlay corresponding
 * to {@link IDecoration#UNDERLAY}.
 * Alternatively, the base image can be replaced by specifying an image
 * corresponding to {@link IDecoration#REPLACE}.
 *
 * @see IDecoration
 * @see org.eclipse.jface.viewers.DecorationOverlayIcon DecorationOverlayIcon
 */
public class OverlayImageDescriptor
	extends CompositeImageDescriptor
{
	private ImageDescriptor base;

	private ImageDescriptor[] overlays;

	private Point size;


	/**
	 * Construct the overlay image descriptor for the specified base image,
	 * overlaid by the image descriptors in the specified array.
	 * The indices of the array correspond to the values
	 * of the 6 overlay constants defined on {@link IDecoration}:<ol start=0>
	 * <li>{@link IDecoration#TOP_LEFT}
	 * <li>{@link IDecoration#TOP_RIGHT}
	 * <li>{@link IDecoration#BOTTOM_LEFT}
	 * <li>{@link IDecoration#BOTTOM_RIGHT}
	 * <li>{@link IDecoration#UNDERLAY}
	 * <li>{@link IDecoration#REPLACE}
	 * </ol>
	 * The resulting image will have the same size as the specified base image.
	 */
	public OverlayImageDescriptor(ImageDescriptor base, ImageDescriptor[] overlays) {
		this(base, overlays, new Point(base.getImageData().width, base.getImageData().height));
	}

	/**
	 * Construct the overlay image descriptor for the specified base image,
	 * overlaid in the specified quadrant by the specified overlay image
	 * descriptor.
	 * The specified quandrant must correspond to on of the values
	 * of the 6 overlay constants defined on {@link IDecoration}:<ol start=0>
	 * <li>{@link IDecoration#TOP_LEFT}
	 * <li>{@link IDecoration#TOP_RIGHT}
	 * <li>{@link IDecoration#BOTTOM_LEFT}
	 * <li>{@link IDecoration#BOTTOM_RIGHT}
	 * <li>{@link IDecoration#UNDERLAY}
	 * <li>{@link IDecoration#REPLACE}
	 * </ol>
	 * The resulting image will have the same size as the specified base image.
	 */
	public OverlayImageDescriptor(ImageDescriptor base, ImageDescriptor overlay, int quadrant) {
		this(base, convertToArray(overlay, quadrant));
	}

	private static ImageDescriptor[] convertToArray(ImageDescriptor overlay, int quadrant) {
		ImageDescriptor[] overlays = new ImageDescriptor[] { null, null, null, null, null, null };
		overlays[quadrant] = overlay;
		return overlays;
	}

	/**
	 * Construct the overlay image descriptor for the specified base image,
	 * overlaid by the image descriptors in the specified array.
	 * The indices of the array correspond to the values
	 * of the 6 overlay constants defined on {@link IDecoration}:<ol start=0>
	 * <li>{@link IDecoration#TOP_LEFT}
	 * <li>{@link IDecoration#TOP_RIGHT}
	 * <li>{@link IDecoration#BOTTOM_LEFT}
	 * <li>{@link IDecoration#BOTTOM_RIGHT}
	 * <li>{@link IDecoration#UNDERLAY}
	 * <li>{@link IDecoration#REPLACE}
	 * </ol>
	 * The resulting image will have the specified size.
	 */
	public OverlayImageDescriptor(ImageDescriptor base, ImageDescriptor[] overlays, Point size) {
		super();
		if ((base == null) || (overlays == null) || (size == null)) {
			throw new NullPointerException();
		}
		this.base = base;
		this.overlays = overlays;
		this.size = size;
	}

	/**
	 * Draw the underlay image first, followed by either the base image or the
	 * replacement image, followed by the overlay images in the order indicated
	 * by the overlay constants defined on {@link IDecoration}.
	 */
	@Override
	protected void drawCompositeImage(int width, int height) {
		if (this.overlays.length > IDecoration.UNDERLAY) {
			ImageDescriptor underlay = this.overlays[IDecoration.UNDERLAY];
			if (underlay != null) {
				this.drawImage(underlay.getImageData(), 0, 0);
			}
		}

		ImageDescriptor temp = this.base;
		if (this.overlays.length > IDecoration.REPLACE) {
			ImageDescriptor replace = this.overlays[IDecoration.REPLACE];
			if (replace != null) {
				temp = replace;
			}
		}
		this.drawImage(this.convertToImageData(temp), 0, 0);

		for (int i = 0; i < this.overlays.length; i++) {
			ImageDescriptor overlay = this.overlays[i];
			if (overlay == null) {
				continue;
			}
			ImageData overlayData = this.convertToImageData(overlay);
			switch (i) {
				case IDecoration.TOP_LEFT:
					this.drawImage(overlayData, 0, 0);
					break;
				case IDecoration.TOP_RIGHT:
					this.drawImage(overlayData, (this.size.x - overlayData.width), 0);
					break;
				case IDecoration.BOTTOM_LEFT:
					this.drawImage(overlayData, 0, (this.size.y - overlayData.height));
					break;
				case IDecoration.BOTTOM_RIGHT:
					this.drawImage(overlayData, (this.size.x - overlayData.width), (this.size.y - overlayData.height));
					break;
				default:
					// NOP
			}
		}
	}

	private ImageData convertToImageData(ImageDescriptor imageDescriptor) {
		ImageData imageData = imageDescriptor.getImageData();
		return (imageData != null) ? imageData : ImageDescriptor.getMissingImageDescriptor().getImageData();
	}

	@Override
	protected Point getSize() {
		return this.size;
	}

	@Override
	protected int getTransparentPixel() {
		return this.base.getImageData().transparentPixel;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof OverlayImageDescriptor)) {
			return false;
		}
		OverlayImageDescriptor other = (OverlayImageDescriptor) o;
		return this.base.equals(other.base)
				&& Arrays.equals(this.overlays, other.overlays)
				&& this.size.equals(other.size);
	}

	@Override
	public int hashCode() {
		int code = this.base.hashCode();
		for (ImageDescriptor overlay : this.overlays) {
			if (overlay != null) {
				code ^= overlay.hashCode();
			}
		}
		return code ^= this.size.hashCode();
	}
}
