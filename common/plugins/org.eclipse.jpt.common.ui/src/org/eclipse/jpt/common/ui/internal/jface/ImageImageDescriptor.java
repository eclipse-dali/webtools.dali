/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * Image descriptor for an image.
 */
public class ImageImageDescriptor extends ImageDescriptor 
{
	
	private Image fImage;
	
	/**
	 * Constructor for ImagImageDescriptor.
	 */
	public ImageImageDescriptor(Image image) {
		super();
		this.fImage = image;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj != null) && getClass().equals(obj.getClass()) && this.fImage.equals(((ImageImageDescriptor) obj).fImage);
	}
	
	@Override
	public ImageData getImageData() {
		return this.fImage.getImageData();
	}
	
	@Override
	public int hashCode() {
		return this.fImage.hashCode();
	}
}
