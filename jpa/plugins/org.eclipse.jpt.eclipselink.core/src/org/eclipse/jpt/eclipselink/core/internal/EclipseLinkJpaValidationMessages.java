/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

@SuppressWarnings("nls")
public interface EclipseLinkJpaValidationMessages {

	public static final String BUNDLE_NAME = "eclipselink_jpa_validation";

	public static final String CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED = "CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED";

	public static final String CONVERTER_CLASS_IMPLEMENTS_CONVERTER = "CONVERTER_CLASS_IMPLEMENTS_CONVERTER";
	
	public static final String CONVERTER_DUPLICATE_NAME = "CONVERTER_DUPLICATE_NAME";
	
	public static final String CONVERTER_NAME_UNDEFINED = "CONVERTER_NAME_UNDEFINED";

	public static final String CONVERTER_CLASS_EXISTS = "CONVERTER_CLASS_EXISTS";
	
	public static final String CONVERTER_CLASS_DEFINED = "CONVERTER_CLASS_DEFINED";

	public static final String CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER = "CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER";
	
	public static final String ID_MAPPING_UNRESOLVED_CONVERTER_NAME = "ID_MAPPING_UNRESOLVED_CONVERTER_NAME";

	public static final String MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE = "MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE";

	public static final String PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER = "PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER";
	
	public static final String PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING = "PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING";

	public static final String PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED = "PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED";

	public static final String BASIC_COLLECTION_MAPPING_DEPRECATED = "BASIC_COLLECTION_MAPPING_DEPRECATED";
	public static final String BASIC_MAP_MAPPING_DEPRECATED = "BASIC_MAP_MAPPING_DEPRECATED";

	public static final String TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC = "TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC";

}
