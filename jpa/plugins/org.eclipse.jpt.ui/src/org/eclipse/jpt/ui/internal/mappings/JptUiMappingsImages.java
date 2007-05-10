/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings;

import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.swt.graphics.Image;

public class JptUiMappingsImages 
{
	public final static String BASIC = "full/obj16/basic";
	
	public final static String EMBEDDABLE = "full/obj16/embeddable";
	
	public final static String EMBEDDED = "full/obj16/embedded";
	
	public final static String EMBEDDED_ID = "full/obj16/embedded-id";
	
	public final static String ENTITY = "full/obj16/entity";
	
	public final static String ENTITY_MAPPINGS = "full/obj16/entity-mappings";
	
	public final static String ID = "full/obj16/id";
	
	public final static String NULL_ATTRIBUTE_MAPPING = "full/obj16/null-attribute-mapping";
	
	public final static String MANY_TO_MANY = "full/obj16/many-to-many";
	
	public final static String MANY_TO_ONE = "full/obj16/many-to-one";
	
	public final static String MAPPED_SUPERCLASS = "full/obj16/mapped-superclass";
	
	public final static String NULL_TYPE_MAPPING = "full/obj16/null-type-mapping";
	
	public final static String ONE_TO_MANY = "full/obj16/one-to-many";
	
	public final static String ONE_TO_ONE = "full/obj16/one-to-one";
	
	public final static String TEMPORAL = "full/obj16/temporal";
	
	public final static String TRANSIENT = "full/obj16/transient";
	
	public final static String VERSION = "full/obj16/version";
	
	
	public static Image getImage(String imageLocator) {
		return JptUiPlugin.getPlugin().getImageDescriptor(imageLocator).createImage();
	}
	
	
	private JptUiMappingsImages() {
		throw new UnsupportedOperationException();
	}
}
