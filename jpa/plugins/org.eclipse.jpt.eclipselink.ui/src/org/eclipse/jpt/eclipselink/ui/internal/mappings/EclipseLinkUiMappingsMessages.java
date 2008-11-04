/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings;

import org.eclipse.osgi.util.NLS;

/**
 * The localized strings used by the mapping panes.
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EclipseLinkUiMappingsMessages extends NLS {

	public static String CacheTypeComposite_label;
	public static String CacheTypeComposite_full;
	public static String CacheTypeComposite_weak;
	public static String CacheTypeComposite_soft;
	public static String CacheTypeComposite_soft_weak;
	public static String CacheTypeComposite_hard_weak;
	public static String CacheTypeComposite_cache;
	public static String CacheTypeComposite_none;
	public static String EclipseLinkTypeMappingComposite_advanced;
	public static String EclipseLinkTypeMappingComposite_caching;
	public static String EclipseLinkTypeMappingComposite_converters;
	
	public static String CacheSizeComposite_size;
	public static String CacheCoordinationTypeComposite_label;
	public static String CacheCoordinationTypeComposite_send_object_changes;
	public static String CacheCoordinationTypeComposite_invalidate_changed_objects;
	public static String CacheCoordinationTypeComposite_send_new_objects_with_changes;
	public static String CacheCoordinationTypeComposite_none;

	public static String CachingComposite_sharedLabelDefault;
	public static String CachingComposite_sharedLabel;
	public static String CachingComposite_advanced;
	public static String AlwaysRefreshComposite_alwaysRefreshDefault;
	public static String AlwaysRefreshComposite_alwaysRefreshLabel;
	public static String RefreshOnlyIfNewerComposite_refreshOnlyIfNewerDefault;
	public static String RefreshOnlyIfNewerComposite_refreshOnlyIfNewerLabel;
	public static String DisableHitsComposite_disableHitsDefault;
	public static String DisableHitsComposite_disableHitsLabel;
	
	public static String ChangeTrackingComposite_label;
	public static String ChangeTrackingComposite_attribute;
	public static String ChangeTrackingComposite_object;
	public static String ChangeTrackingComposite_deferred;
	public static String ChangeTrackingComposite_auto;
	
	public static String ConvertComposite_converterNameLabel;
	public static String ConvertComposite_defineConverterSection;
	public static String ConvertComposite_default;
	public static String ConvertComposite_custom;
	public static String ConvertComposite_type;
	public static String ConvertComposite_objectType;
	public static String ConvertComposite_struct;
	
	public static String ConverterComposite_nameTextLabel;
	public static String ConverterComposite_classLabel;

	public static String ConvertersComposite_converter;
	public static String ConvertersComposite_objectTypeConverter;
	public static String ConvertersComposite_structConverter;
	public static String ConvertersComposite_typeConverter;
	
	public static String CustomizerComposite_classLabel;
		
	public static String TypeSection_converted;
	
	public static String ExistenceCheckingComposite_label;
	public static String JavaExistenceCheckingComposite_check_cache;
	public static String JavaExistenceCheckingComposite_check_database;
	public static String JavaExistenceCheckingComposite_assume_existence;
	public static String JavaExistenceCheckingComposite_assume_non_existence;
	public static String OrmExistenceCheckingComposite_check_cache;
	public static String OrmExistenceCheckingComposite_check_database;
	public static String OrmExistenceCheckingComposite_assume_existence;
	public static String OrmExistenceCheckingComposite_assume_non_existence;
	
	public static String ExpiryComposite_expirySection;
	public static String ExpiryComposite_noExpiry;
	public static String ExpiryComposite_timeToLiveExpiry;
	public static String ExpiryComposite_timeToLiveExpiryExpireAfter;
	public static String ExpiryComposite_timeToLiveExpiryMilliseconds;
	public static String ExpiryComposite_dailyExpiry;
	public static String ExpiryComposite_timeOfDayExpiryExpireAt;
	
	public static String JoinFetchComposite_label;
	public static String JoinFetchComposite_inner;
	public static String JoinFetchComposite_outer;	
	
	public static String MutableComposite_mutableLabel;
	public static String MutableComposite_mutableLabelDefault;
	public static String MutableComposite_true;
	public static String MutableComposite_false;
	
	public static String ObjectTypeConverterComposite_dataTypeLabel;
	public static String ObjectTypeConverterComposite_objectTypeLabel;
	public static String ObjectTypeConverterComposite_conversionValueEdit;
	public static String ObjectTypeConverterComposite_conversionValuesDataValueColumn;
	public static String ObjectTypeConverterComposite_conversionValuesObjectValueColumn;
	public static String ObjectTypeConverterComposite_defaultObjectValueLabel;
	
	public static String ConversionValueDialog_addConversionValue;
	public static String ConversionValueDialog_editConversionValue;
	public static String ConversionValueDialog_addConversionValueDescriptionTitle;
	public static String ConversionValueDialog_editConversionValueDescriptionTitle;
	public static String ConversionValueDialog_addConversionValueDescription;
	public static String ConversionValueDialog_editConversionValueDescription;
	
	public static String ConversionValueDialog_dataValue;
	public static String ConversionValueDialog_objectValue;
	public static String ConversionValueStateObject_dataValueMustBeSpecified;
	public static String ConversionValueStateObject_objectValueMustBeSpecified;
	public static String ConversionValueStateObject_dataValueAlreadyExists;
	
	public static String PrivateOwnedComposite_privateOwnedLabel;
	
	public static String ReadOnlyComposite_readOnlyLabel;
	public static String ReadOnlyComposite_readOnlyWithDefault;
	
	public static String TypeConverterComposite_dataTypeLabel;
	public static String TypeConverterComposite_objectTypeLabel;

	public static String DefaultOneToOneMappingUiProvider_label;
	public static String DefaultOneToManyMappingUiProvider_label;
	public static String DefaultOneToOneMappingUiProvider_linkLabel;
	public static String DefaultOneToManyMappingUiProvider_linkLabel;
	
	public static String ConvertersComposite_Label;
	public static String EclipseLinkConverterDialog_name;
	public static String EclipseLinkConverterDialog_converterType;
	public static String EclipseLinkConverterDialog_addConverter;
	public static String EclipseLinkConverterDialog_addConverterDescriptionTitle;
	public static String EclipseLinkConverterDialog_addConverterDescription;
	public static String EclipseLinkConverterStateObject_nameMustBeSpecified;
	public static String EclipseLinkConverterStateObject_typeMustBeSpecified;
	
	static {
		NLS.initializeMessages("eclipselink_ui_mappings", EclipseLinkUiMappingsMessages.class);
	}

	private EclipseLinkUiMappingsMessages() {
		throw new UnsupportedOperationException();
	}
}