/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

/**
 * EclipseLink Java-related stuff (annotations etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 */
@SuppressWarnings("nls")
public interface EclipseLink {

	// EclipseLink package
	String PACKAGE = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	String PACKAGE_ = PACKAGE + ".";


	// ********** API **********

	// EclispeLink annotations
	String BASIC_COLLECTION = PACKAGE_ + "BasicCollection";
	String BASIC_MAP = PACKAGE_ + "BasicMap";
	String CACHE = PACKAGE_ + "Cache";
		String CACHE__SHARED = "shared";
		String CACHE__EXPIRY = "expiry";
		String CACHE__EXPIRY_TIME_OF_DAY = "expiryTimeOfDay";
		String CACHE__TYPE = "type";
		String CACHE__SIZE = "size";
		String CACHE__ALWAYS_REFRESH = "alwaysRefresh";
		String CACHE__REFRESH_ONLY_IF_NEWER = "refreshOnlyIfNewer";
		String CACHE__DISABLE_HITS = "disableHits";
		String CACHE__COORDINATION_TYPE = "coordinationType";
	String CHANGE_TRACKING = PACKAGE_ + "ChangeTracking";
		String CHANGE_TRACKING__VALUE = "value";
	String CONVERSION_VALUE = PACKAGE_ + "ConversionValue";
		String CONVERSION_VALUE__DATA_VALUE = "dataValue";
		String CONVERSION_VALUE__OBJECT_VALUE = "objectValue";
	String CONVERT = PACKAGE_ + "Convert";
		String CONVERT__VALUE = "value";
	String CONVERTER = PACKAGE_ + "Converter";
		String CONVERTER__NAME = "name";
		String CONVERTER__CONVERTER_CLASS = "converterClass";
	String CUSTOMIZER = PACKAGE_ + "Customizer";
		String CUSTOMIZER__VALUE = "value";
	String EXISTENCE_CHECKING = PACKAGE_ + "ExistenceChecking";
		String EXISTENCE_CHECKING__VALUE = "value";
	String JOIN_FETCH = PACKAGE_ + "JoinFetch";
		String JOIN_FETCH__VALUE = "value";
	String MUTABLE = PACKAGE_ + "Mutable";
		String MUTABLE__VALUE = "value";
	String OBJECT_TYPE_CONVERTER = PACKAGE_ + "ObjectTypeConverter";
		String OBJECT_TYPE_CONVERTER__NAME = "name";
		String OBJECT_TYPE_CONVERTER__DATA_TYPE = "dataType";
		String OBJECT_TYPE_CONVERTER__OBJECT_TYPE = "objectType";
		String OBJECT_TYPE_CONVERTER__CONVERSION_VALUES = "conversionValues";
		String OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE = "defaultObjectValue";
	String PRIMARY_KEY = PACKAGE_ + "PrimaryKey";
		String PRIMARY_KEY__VALIDATION = "validation";
		String PRIMARY_KEY__CACHE_KEY_TYPE = "cacheKeyType";
		String PRIMARY_KEY__COLUMNS = "columns";
	String PRIVATE_OWNED = PACKAGE_ + "PrivateOwned";
	String READ_ONLY = PACKAGE_ + "ReadOnly";
	String READ_TRANSFORMER = PACKAGE_ + "ReadTransformer";
		String READ_TRANSFORMER__TRANSFORMER_CLASS = "transformerClass";
		String READ_TRANSFORMER__METHOD = "method";
	String STRUCT_CONVERTER = PACKAGE_ + "StructConverter";
		String STRUCT_CONVERTER__NAME = "name";
		String STRUCT_CONVERTER__CONVERTER = "converter";
	String TIME_OF_DAY = PACKAGE_ + "TimeOfDay";
		String TIME_OF_DAY__HOUR = "hour";
		String TIME_OF_DAY__MINUTE = "minute";
		String TIME_OF_DAY__SECOND = "second";
		String TIME_OF_DAY__MILLISECOND = "millisecond";
	String TRANSFORMATION = PACKAGE_ + "Transformation";
		String TRANSFORMATION__FETCH = "fetch";
		String TRANSFORMATION__OPTIONAL = "optional";
	String TYPE_CONVERTER = PACKAGE_ + "TypeConverter";
		String TYPE_CONVERTER__NAME = "name";
		String TYPE_CONVERTER__DATA_TYPE = "dataType";
		String TYPE_CONVERTER__OBJECT_TYPE = "objectType";
	String VARIABLE_ONE_TO_ONE = PACKAGE_ + "VariableOneToOne";
	String WRITE_TRANSFORMER = PACKAGE_ + "WriteTransformer";
		String WRITE_TRANSFORMER__TRANSFORMER_CLASS = "transformerClass";
		String WRITE_TRANSFORMER__METHOD = "method";
		String WRITE_TRANSFORMER__COLUMN = "column";
	String WRITE_TRANSFORMERS = PACKAGE_ + "WriteTransformers";
		String WRITE_TRANSFORMERS__VALUE = "value";

	// EclipseLink enums
	String CACHE_COORDINATION_TYPE = PACKAGE_ + "CacheCoordinationType";
		String CACHE_COORDINATION_TYPE_ = CACHE_COORDINATION_TYPE + ".";
		String CACHE_COORDINATION_TYPE__SEND_OBJECT_CHANGES = CACHE_COORDINATION_TYPE_ + "SEND_OBJECT_CHANGES";
		String CACHE_COORDINATION_TYPE__INVALIDATE_CHANGED_OBJECTS = CACHE_COORDINATION_TYPE_ + "INVALIDATE_CHANGED_OBJECTS";
		String CACHE_COORDINATION_TYPE__SEND_NEW_OBJECTS_WITH_CHANGES = CACHE_COORDINATION_TYPE_ + "SEND_NEW_OBJECTS_WITH_CHANGES";
		String CACHE_COORDINATION_TYPE__NONE = CACHE_COORDINATION_TYPE_ + "NONE";
	String CACHE_TYPE = PACKAGE_ + "CacheType";
		String CACHE_TYPE_ = CACHE_TYPE + ".";
		String CACHE_TYPE__FULL = CACHE_TYPE_ + "FULL";
		String CACHE_TYPE__WEAK = CACHE_TYPE_ + "WEAK";
		String CACHE_TYPE__SOFT = CACHE_TYPE_ + "SOFT";
		String CACHE_TYPE__SOFT_WEAK = CACHE_TYPE_ + "SOFT_WEAK";
		String CACHE_TYPE__HARD_WEAK = CACHE_TYPE_ + "HARD_WEAK";
		String CACHE_TYPE__CACHE = CACHE_TYPE_ + "CACHE";
		String CACHE_TYPE__NONE = CACHE_TYPE_ + "NONE";
	String CHANGE_TRACKING_TYPE = PACKAGE_ + "ChangeTrackingType";
		String CHANGE_TRACKING_TYPE_ = CHANGE_TRACKING_TYPE + ".";
		String CHANGE_TRACKING_TYPE__ATTRIBUTE = CHANGE_TRACKING_TYPE_ + "ATTRIBUTE";
		String CHANGE_TRACKING_TYPE__OBJECT = CHANGE_TRACKING_TYPE_ + "OBJECT";
		String CHANGE_TRACKING_TYPE__DEFERRED = CHANGE_TRACKING_TYPE_ + "DEFERRED";
		String CHANGE_TRACKING_TYPE__AUTO = CHANGE_TRACKING_TYPE_ + "AUTO";
	String EXISTENCE_TYPE = PACKAGE_ + "ExistenceType";
		String EXISTENCE_TYPE_ = EXISTENCE_TYPE + ".";
		String EXISTENCE_TYPE__CHECK_CACHE = EXISTENCE_TYPE_ + "CHECK_CACHE";
		String EXISTENCE_TYPE__CHECK_DATABASE = EXISTENCE_TYPE_ + "CHECK_DATABASE";
		String EXISTENCE_TYPE__ASSUME_EXISTENCE = EXISTENCE_TYPE_ + "ASSUME_EXISTENCE";
		String EXISTENCE_TYPE__ASSUME_NON_EXISTENCE = EXISTENCE_TYPE_ + "ASSUME_NON_EXISTENCE";
	String JOIN_FETCH_TYPE = PACKAGE_ + "JoinFetchType";
		String JOIN_FETCH_TYPE_ = JOIN_FETCH_TYPE + ".";
		String JOIN_FETCH_TYPE__INNER = JOIN_FETCH_TYPE_ + "INNER";
		String JOIN_FETCH_TYPE__OUTER = JOIN_FETCH_TYPE_ + "OUTER";


	// EclispeLink 2.0 annotations
	String MAP_KEY_CONVERT = PACKAGE_ + "MapKeyConvert";
		String MAP_KEY_CONVERT__VALUE = "value";


	// EclispeLink 2.1 annotations
	String CLASS_EXTRACTOR = PACKAGE_ + "ClassExtractor";
		String CLASS_EXTRACTOR__VALUE = "value";


	// EclispeLink 2.2 annotations
	String CACHE__ISOLATION = "isolation";
	String CONVERTERS = PACKAGE_ + "Converters";
		String CONVERTERS__VALUE = "value";
	String OBJECT_TYPE_CONVERTERS = PACKAGE_ + "ObjectTypeConverters";
		String OBJECT_TYPE_CONVERTERS__VALUE = "value";
	String STRUCT_CONVERTERS = PACKAGE_ + "StructConverters";
		String STRUCT_CONVERTERS__VALUE = "value";
	String TYPE_CONVERTERS = PACKAGE_ + "TypeConverters";
		String TYPE_CONVERTERS__VALUE = "value";

	// EclipseLink 2.2 enums
	String CACHE_ISOLATION_TYPE = "org.eclipse.persistence.config.CacheIsolationType";
		String CACHE_ISOLATION_TYPE_ = CACHE_ISOLATION_TYPE + ".";
		String CACHE_ISOLATION_TYPE__SHARED = CACHE_ISOLATION_TYPE_ + "SHARED";
		String CACHE_ISOLATION_TYPE__PROTECTED = CACHE_ISOLATION_TYPE_ + "PROTECTED";
		String CACHE_ISOLATION_TYPE__ISOLATED = CACHE_ISOLATION_TYPE_ + "ISOLATED";


	// EclispeLink 2.3 annotations

	String ARRAY = PACKAGE_ + "Array";

	String MULTITENANT = PACKAGE_ + "Multitenant";
		String MULTITENANT__VALUE = "value";

	String MULTITENANT_TYPE = PACKAGE_ + "MultitenantType";
		String MULTITENANT_TYPE_ = MULTITENANT_TYPE + ".";
		String MULTITENANT_TYPE__SINGLE_TABLE = MULTITENANT_TYPE_ + "SINGLE_TABLE";
		String MULTITENANT_TYPE__TABLE_PER_TENANT = MULTITENANT_TYPE_ + "TABLE_PER_TENANT";
		String MULTITENANT_TYPE__VPD = MULTITENANT_TYPE_ + "VPD";

	String STRUCTURE = PACKAGE_ + "Structure";

	String TENANT_DISCRIMINATOR_COLUMN = PACKAGE_ + "TenantDiscriminatorColumn";
		String TENANT_DISCRIMINATOR_COLUMN__NAME = "name";
		String TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY = "contextProperty";
		String TENANT_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = "discriminatorType";
		String TENANT_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String TENANT_DISCRIMINATOR_COLUMN__LENGTH = "length";
		String TENANT_DISCRIMINATOR_COLUMN__TABLE = "table";
		String TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY = "primaryKey";

	String TENANT_DISCRIMINATOR_COLUMNS = PACKAGE_ + "TenantDiscriminatorColumns";
		String TENANT_DISCRIMINATOR_COLUMNS__VALUE = "value";


	// EclispeLink 2.4 annotations
	String MULTITENANT__INCLUDE_CRITERIA = "includeCriteria";
	String UUID_GENERATOR = PACKAGE_ + "UuidGenerator";
		String UUID_GENERATOR__NAME = "name";
}
