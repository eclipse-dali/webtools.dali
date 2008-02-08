/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class JptUiIcons
{
	public static Image ghost(Image image) {
		Color lightgray = new Color(image.getDevice(), 223, 223, 223);
		ImageData imageData = ImageUtilities.createShadedImage(image, lightgray);
		return new Image(image.getDevice(), new Image(image.getDevice(), imageData), SWT.IMAGE_GRAY);
	}
	
	
	// **************** General JPA icons **************************************
	
	public static final String JPA_WIZ_BANNER = "full/wizban/jpa_facet_wizban"; //$NON-NLS-1$
	
	public static final String JPA_CONTENT = "full/obj16/jpa-content";  //$NON-NLS-1$
	
	public static final String JPA_FILE = "full/obj16/jpa-file";  //$NON-NLS-1$
	
	
	// **************** Persistence icons **************************************
	
	public static final String PERSISTENCE = "full/obj16/persistence";  //$NON-NLS-1$
	
	public static final String PERSISTENCE_UNIT = "full/obj16/persistence-unit";  //$NON-NLS-1$
	
	public static final String MAPPING_FILE_REF = "full/obj16/jpa-file";  //$NON-NLS-1$
	
	public static final String CLASS_REF = "full/obj16/null-type-mapping";  //$NON-NLS-1$
	
	
	// **************** Orm icons **********************************************
	
	public static final String ENTITY_MAPPINGS = "full/obj16/entity-mappings";  //$NON-NLS-1$
	
	
	// **************** Orm/Java common icons **********************************
	
	public static final String ENTITY = "full/obj16/entity";  //$NON-NLS-1$
	
	public static final String EMBEDDABLE = "full/obj16/embeddable";  //$NON-NLS-1$
	
	public static final String MAPPED_SUPERCLASS = "full/obj16/mapped-superclass";  //$NON-NLS-1$
	
	public static final String NULL_TYPE_MAPPING = "full/obj16/null-type-mapping";  //$NON-NLS-1$
	
	public static final String BASIC = "full/obj16/basic";  //$NON-NLS-1$
	
	public static final String VERSION = "full/obj16/version";  //$NON-NLS-1$
	
	public static final String ID = "full/obj16/id";  //$NON-NLS-1$
	
	public static final String EMBEDDED_ID = "full/obj16/embedded-id";  //$NON-NLS-1$
	
	public static final String EMBEDDED = "full/obj16/embedded";  //$NON-NLS-1$
	
	public static final String ONE_TO_ONE = "full/obj16/one-to-one";  //$NON-NLS-1$
	
	public static final String ONE_TO_MANY = "full/obj16/one-to-many";  //$NON-NLS-1$
	
	public static final String MANY_TO_ONE = "full/obj16/many-to-one";  //$NON-NLS-1$
	
	public static final String MANY_TO_MANY = "full/obj16/many-to-many";  //$NON-NLS-1$
	
	public static final String TRANSIENT = "full/obj16/transient";  //$NON-NLS-1$
	
	public static final String NULL_ATTRIBUTE_MAPPING = "full/obj16/null-attribute-mapping";  //$NON-NLS-1$
}
