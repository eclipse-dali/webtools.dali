/*******************************************************************************
 *  Copyright (c) 2007, 2010 Oracle.
 *  All rights reserved.  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

@SuppressWarnings("nls")
public class JptUiIcons
{
    /**
	 * Creates and returns a new SWT image that is a grayed out version of the image 
	 * corresponding  to the passed in key. Stores this gray image in the JptUiPlugin 
	 * ImageRegistry with -gray appended to the key.
	 * Clients of this method should not dispose of the image.
	 * 
	 * @return a new grayed out image
	 */
	public static Image ghost(String key) {
		Image existingImage = JptUiPlugin.getImage(key + "-gray");
		if (existingImage != null) {
			return existingImage;
		}

		Image grayImage = buildGhostImage(key);
		JptUiPlugin.instance().getImageRegistry().put(key + "-gray", grayImage);
		return grayImage;
	}
	
	private static Image buildGhostImage(String key) {
		Image originalImage = JptUiPlugin.getImage(key);
		Color lightGray = new Color(originalImage.getDevice(), 223, 223, 223);
		ImageData imageData = ImageUtilities.createShadedImage(originalImage, lightGray);

		Image shadedImage = new Image(originalImage.getDevice(), imageData);
		Image grayImage = new Image(originalImage.getDevice(), shadedImage, SWT.IMAGE_GRAY);

		lightGray.dispose();
		shadedImage.dispose();
		return grayImage;
	}	


	// **************** General JPA icons **************************************
	
	public static final String JPA_CONTENT = "full/obj16/jpa-content";
	
	public static final String JPA_FILE = "full/obj16/jpa-file";
	
	public static final String JAR_FILE = "full/obj16/jpa-jar-file";
	
	public static final String WARNING = "full/obj16/warning";
	
	
	// **************** Wizard icons *******************************************
	
	public static final String JPA_WIZ_BANNER = "full/wizban/jpa_facet_wizban";
	
	public static final String ENTITY_WIZ_BANNER = "full/wizban/new_entity_wizban";
	
	public static final String JPA_FILE_WIZ_BANNER = "full/wizban/new_jpa_file_wizban";
	
	
	// **************** Persistence icons **************************************

	public static final String PERSISTENCE = "full/obj16/persistence";

	public static final String PERSISTENCE_UNIT = "full/obj16/persistence-unit";

	public static final String MAPPING_FILE_REF = "full/obj16/jpa-file";

	public static final String CLASS_REF = "full/obj16/null-type-mapping";
	
	public static final String JAR_FILE_REF = "full/obj16/jpa-jar-file";


	// **************** Orm icons **********************************************

	public static final String ENTITY_MAPPINGS = "full/obj16/entity-mappings";


	// **************** Orm/Java common icons **********************************
	
	public static final String ENTITY = "full/obj16/entity";
	
	public static final String EMBEDDABLE = "full/obj16/embeddable";
	
	public static final String MAPPED_SUPERCLASS = "full/obj16/mapped-superclass";
	
	public static final String NULL_TYPE_MAPPING = "full/obj16/null-type-mapping";
	
	public static final String ID = "full/obj16/id";
	
	public static final String EMBEDDED_ID = "full/obj16/embedded-id";
	
	public static final String BASIC = "full/obj16/basic";
	
	public static final String VERSION = "full/obj16/version";
	
	public static final String MANY_TO_ONE = "full/obj16/many-to-one";
	
	public static final String ONE_TO_MANY = "full/obj16/one-to-many";
	
	public static final String ONE_TO_ONE = "full/obj16/one-to-one";
	
	public static final String MANY_TO_MANY = "full/obj16/many-to-many";
	
	public static final String ELEMENT_COLLECTION = "full/obj16/element-collection";
	
	public static final String EMBEDDED = "full/obj16/embedded";
	
	public static final String TRANSIENT = "full/obj16/transient";
	
	public static final String NULL_ATTRIBUTE_MAPPING = "full/obj16/null-attribute-mapping";
}
