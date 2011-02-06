/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.osgi.util.NLS;

/**
 * The localized strings used by the mapping panes.
 *
 * @version 2.3
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EclipseLinkUiDetailsMessages extends NLS {

	public static String EclipseLinkBasicCollectionMappingUiProvider_label;
	public static String EclipseLinkBasicCollectionMappingUiProvider_linkLabel;
	
	public static String EclipseLinkBasicMapMappingUiProvider_label;
	public static String EclipseLinkBasicMapMappingUiProvider_linkLabel;
	
	public static String EclipseLinkTransformationMappingUiProvider_label;
	public static String EclipseLinkTransformationMappingUiProvider_linkLabel;

	public static String EclipseLinkVariableOneToOneMappingUiProvider_label;
	public static String EclipseLinkVariableOneToOneMappingUiProvider_linkLabel;

	public static String EclipseLinkCacheTypeComposite_label;
	public static String EclipseLinkCacheTypeComposite_full;
	public static String EclipseLinkCacheTypeComposite_weak;
	public static String EclipseLinkCacheTypeComposite_soft;
	public static String EclipseLinkCacheTypeComposite_soft_weak;
	public static String EclipseLinkCacheTypeComposite_hard_weak;
	public static String EclipseLinkCacheTypeComposite_cache;
	public static String EclipseLinkCacheTypeComposite_none;
	public static String EclipseLinkTypeMappingComposite_advanced;
	public static String EclipseLinkTypeMappingComposite_caching;
	public static String EclipseLinkTypeMappingComposite_converters;
	
	public static String EclipseLinkCacheSizeComposite_size;
	public static String EclipseLinkCacheCoordinationTypeComposite_label;
	public static String EclipseLinkCacheCoordinationTypeComposite_send_object_changes;
	public static String EclipseLinkCacheCoordinationTypeComposite_invalidate_changed_objects;
	public static String EclipseLinkCacheCoordinationTypeComposite_send_new_objects_with_changes;
	public static String EclipseLinkCacheCoordinationTypeComposite_none;

	public static String EclipseLinkCachingComposite_sharedLabelDefault;
	public static String EclipseLinkCachingComposite_sharedLabel;
	public static String EclipseLinkCachingComposite_advanced;
	public static String EclipseLinkAlwaysRefreshComposite_alwaysRefreshDefault;
	public static String EclipseLinkAlwaysRefreshComposite_alwaysRefreshLabel;
	public static String EclipseLinkRefreshOnlyIfNewerComposite_refreshOnlyIfNewerDefault;
	public static String EclipseLinkRefreshOnlyIfNewerComposite_refreshOnlyIfNewerLabel;
	public static String EclipseLinkDisableHitsComposite_disableHitsDefault;
	public static String EclipseLinkDisableHitsComposite_disableHitsLabel;
	
	public static String EclipseLinkChangeTrackingComposite_label;
	public static String EclipseLinkChangeTrackingComposite_attribute;
	public static String EclipseLinkChangeTrackingComposite_object;
	public static String EclipseLinkChangeTrackingComposite_deferred;
	public static String EclipseLinkChangeTrackingComposite_auto;
	
	public static String EclipseLinkConvertComposite_converterNameLabel;
	public static String EclipseLinkConvertComposite_defineConverterSection;
	public static String EclipseLinkConvertComposite_default;
	public static String EclipseLinkConvertComposite_custom;
	public static String EclipseLinkConvertComposite_type;
	public static String EclipseLinkConvertComposite_objectType;
	public static String EclipseLinkConvertComposite_struct;
	
	public static String EclipseLinkConverterComposite_nameTextLabel;
	public static String EclipseLinkConverterComposite_classLabel;

	public static String EclipseLinkConvertersComposite_customConverter;
	public static String EclipseLinkConvertersComposite_objectTypeConverter;
	public static String EclipseLinkConvertersComposite_structConverter;
	public static String EclipseLinkConvertersComposite_typeConverter;
	
	public static String EclipseLinkCustomizerComposite_classLabel;
		
	public static String TypeSection_converted;
	
	public static String EclipseLinkExistenceCheckingComposite_label;
	public static String JavaEclipseLinkExistenceCheckingComposite_check_cache;
	public static String JavaEclipseLinkExistenceCheckingComposite_check_database;
	public static String JavaEclipseLinkExistenceCheckingComposite_assume_existence;
	public static String JavaEclipseLinkExistenceCheckingComposite_assume_non_existence;
	public static String OrmEclipseLinkExistenceCheckingComposite_check_cache;
	public static String OrmEclipseLinkExistenceCheckingComposite_check_database;
	public static String OrmEclipseLinkExistenceCheckingComposite_assume_existence;
	public static String OrmEclipseLinkExistenceCheckingComposite_assume_non_existence;
	
	public static String EclipseLinkExpiryComposite_expirySection;
	public static String EclipseLinkExpiryComposite_noExpiry;
	public static String EclipseLinkExpiryComposite_timeToLiveExpiry;
	public static String EclipseLinkExpiryComposite_timeToLiveExpiryExpireAfter;
	public static String EclipseLinkExpiryComposite_timeToLiveExpiryMilliseconds;
	public static String EclipseLinkExpiryComposite_dailyExpiry;
	public static String EclipseLinkExpiryComposite_timeOfDayExpiryExpireAt;
	
	public static String EclipseLinkJoinFetchComposite_label;
	public static String EclipseLinkJoinFetchComposite_inner;
	public static String EclipseLinkJoinFetchComposite_outer;	
	
	public static String EclipseLinkMutableComposite_mutableLabel;
	public static String EclipseLinkMutableComposite_mutableLabelDefault;
	public static String EclipseLinkMutableComposite_true;
	public static String EclipseLinkMutableComposite_false;
	
	public static String EclipseLinkObjectTypeConverterComposite_dataTypeLabel;
	public static String EclipseLinkObjectTypeConverterComposite_objectTypeLabel;
	public static String EclipseLinkObjectTypeConverterComposite_conversionValueEdit;
	public static String EclipseLinkObjectTypeConverterComposite_conversionValuesDataValueColumn;
	public static String EclipseLinkObjectTypeConverterComposite_conversionValuesObjectValueColumn;
	public static String EclipseLinkObjectTypeConverterComposite_defaultObjectValueLabel;
	public static String EclipseLinkObjectTypeConverterComposite_conversionValuesGroupTitle;
	
	public static String EclipseLinkConversionValueDialog_addConversionValue;
	public static String EclipseLinkConversionValueDialog_editConversionValue;
	public static String EclipseLinkConversionValueDialog_addConversionValueDescriptionTitle;
	public static String EclipseLinkConversionValueDialog_editConversionValueDescriptionTitle;
	public static String EclipseLinkConversionValueDialog_addConversionValueDescription;
	public static String EclipseLinkConversionValueDialog_editConversionValueDescription;
	
	public static String EclipseLinkConversionValueDialog_dataValue;
	public static String EclipseLinkConversionValueDialog_objectValue;
	public static String EclipseLinkConversionValueStateObject_dataValueMustBeSpecified;
	public static String EclipseLinkConversionValueStateObject_objectValueMustBeSpecified;
	public static String EclipseLinkConversionValueStateObject_dataValueAlreadyExists;
	
	public static String EclipseLinkPrivateOwnedComposite_privateOwnedLabel;
	
	public static String EclipseLinkReadOnlyComposite_readOnlyLabel;
	public static String EclipseLinkReadOnlyComposite_readOnlyWithDefault;
	
	public static String EclipseLinkTypeConverterComposite_dataTypeLabel;
	public static String EclipseLinkTypeConverterComposite_objectTypeLabel;

	public static String DefaultEclipseLinkOneToOneMappingUiProvider_label;
	public static String DefaultEclipseLinkVariableOneToOneMappingUiProvider_label;
	public static String DefaultEclipseLinkOneToManyMappingUiProvider_label;
	public static String DefaultEclipseLinkOneToOneMappingUiProvider_linkLabel;
	public static String DefaultEclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	public static String DefaultEclipseLinkOneToManyMappingUiProvider_linkLabel;
	
	public static String EclipseLinkConverterDialog_name;
	public static String EclipseLinkConverterDialog_converterType;
	public static String EclipseLinkConverterDialog_addConverter;
	public static String EclipseLinkConverterDialog_addConverterDescriptionTitle;
	public static String EclipseLinkConverterDialog_addConverterDescription;
	public static String EclipseLinkConverterStateObject_nameExists;
	public static String EclipseLinkConverterStateObject_nameMustBeSpecified;
	public static String EclipseLinkConverterStateObject_typeMustBeSpecified;
	
	static {
		NLS.initializeMessages("eclipselink_ui_details", EclipseLinkUiDetailsMessages.class);
	}

	private EclipseLinkUiDetailsMessages() {
		throw new UnsupportedOperationException();
	}
}