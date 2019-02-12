/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests;

import java.lang.reflect.Field;
import junit.framework.TestCase;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;

/**
 * This test case will verify that a class's constants
 * <p>
 * Construct the test case with the class that defines the image descriptors.
 */
@SuppressWarnings("nls")
public class ImageDescriptorTest
	extends TestCase
{
	private final Class<?> clazz;


	public ImageDescriptorTest(Class<?> clazz) {
		super(buildName(clazz));
		this.clazz = clazz;
	}

	private static String buildName(Class<?> clazz) {
		return ImageDescriptorTest.class.getSimpleName() + ": " + clazz.getName();
	}

	@Override
	protected void runTest() throws Throwable {
		ResourceManager resourceManager = this.buildResourceManager();
		try {
			this.runTest(resourceManager);
		} finally {
			resourceManager.dispose();
		}
	}

	protected void runTest(ResourceManager resourceManager) throws Throwable {
		for (Field field : this.clazz.getFields()) {
			Object value = field.get(null);
			if (value instanceof ImageDescriptor) {
				ImageDescriptor descriptor = (ImageDescriptor) value;
				try {
					Image image = resourceManager.createImage(descriptor);
					assertNotNull(image);
				} catch (RuntimeException ex) {
					fail("Problem loading image for ImageDescriptor defined in static field '"
							+ this.clazz.getSimpleName() + '.' + field.getName() + "': " + descriptor + " - " + ex);
				}
			} else {
				// ignore non-image descriptors (e.g. strings);
				// fields should probably not be null...
				if (value == null) {
					fail("The static field '"
							+ this.clazz.getSimpleName() + '.' + field.getName() + "' is null.");
				}
			}
		}
	}

	protected ResourceManager buildResourceManager() {
		return new LocalResourceManager(this.getParentResourceManager());
	}

	private ResourceManager getParentResourceManager() {
		return JFaceResources.getResources();
	}
}
